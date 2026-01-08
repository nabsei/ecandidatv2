package co.simplon.ecandidat.service;

import co.simplon.ecandidat.dto.MontpellierApiResponse;
import co.simplon.ecandidat.dto.MontpellierFormationDto;
import co.simplon.ecandidat.entity.FormationEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MontpellierFormationService {

    private static final Logger log = LoggerFactory.getLogger(MontpellierFormationService.class);
    private static final String API_URL = "https://data.enseignementsup-recherche.gouv.fr/api/explore/v2.1/catalog/datasets/fr-esr-principaux-diplomes-et-formations-prepares-etablissements-publics/records";

    private final RestTemplate restTemplate;
    private final Map<String, String> disciplineToUfrMapping;

    public MontpellierFormationService() {
        this.restTemplate = new RestTemplate();
        this.disciplineToUfrMapping = initializeDisciplineMapping();
    }

    private Map<String, String> initializeDisciplineMapping() {
        Map<String, String> mapping = new HashMap<>();

        mapping.put("DSA", "UFR Droit et Sciences Politiques");
        mapping.put("Droit", "UFR Droit et Sciences Politiques");
        mapping.put("Sciences juridiques", "UFR Droit et Sciences Politiques");
        mapping.put("AES", "UFR Économie et Gestion");
        mapping.put("Économie", "UFR Économie et Gestion");
        mapping.put("Gestion", "UFR Économie et Gestion");
        mapping.put("Sciences de gestion", "UFR Économie et Gestion");

        mapping.put("SI", "UFR Sciences");
        mapping.put("Sciences", "UFR Sciences");
        mapping.put("Informatique", "UFR Informatique");
        mapping.put("Mathématiques", "UFR Sciences");
        mapping.put("Physique", "UFR Sciences");
        mapping.put("Chimie", "UFR Sciences");
        mapping.put("Biologie", "UFR Sciences");
        mapping.put("Sciences de la vie", "UFR Sciences");
        mapping.put("Sciences de la terre", "UFR Sciences");

        mapping.put("LLSH", "UFR Lettres et Sciences Humaines");
        mapping.put("Lettres", "UFR Lettres et Sciences Humaines");
        mapping.put("Langues", "UFR Lettres et Sciences Humaines");
        mapping.put("Histoire", "UFR Lettres et Sciences Humaines");
        mapping.put("Géographie", "UFR Lettres et Sciences Humaines");
        mapping.put("Philosophie", "UFR Lettres et Sciences Humaines");
        mapping.put("Sciences de l'éducation", "UFR Lettres et Sciences Humaines");
        mapping.put("Psychologie", "UFR Lettres et Sciences Humaines");
        mapping.put("Sociologie", "UFR Lettres et Sciences Humaines");
        mapping.put("Arts", "UFR Lettres et Sciences Humaines");

        mapping.put("Santé", "UFR Santé");
        mapping.put("Médecine", "UFR Santé");
        mapping.put("Pharmacie", "UFR Santé");
        mapping.put("Sciences de la santé", "UFR Santé");
        mapping.put("Odontologie", "UFR Santé");

        mapping.put("STAPS", "UFR Sciences");

        return mapping;
    }

    public List<FormationEntity> fetchFormationsFromMontpellier() {
        try {
            log.info("Fetching formations from Montpellier API...");

            List<MontpellierFormationDto> allResults = new ArrayList<>();
            int limit = 100;
            int offset = 0;
            int totalCount = 0;

            do {
                String url = API_URL +
                    "?where=(diplome_lib=\"Autres licences\" or diplome_lib=\"Masters hors enseignement\" or diplome_lib=\"Doctorat\") and etablissement_compos_lib=\"Université de Montpellier\"" +
                    "&limit=" + limit +
                    "&offset=" + offset;

                MontpellierApiResponse response = restTemplate.getForObject(url, MontpellierApiResponse.class);

                if (response == null || response.getResults() == null) {
                    log.warn("No data received from API at offset {}", offset);
                    break;
                }

                totalCount = response.getTotalCount() != null ? response.getTotalCount() : 0;
                allResults.addAll(response.getResults());

                log.info("Fetched {} formations (offset: {}, total in API: {})",
                    response.getResults().size(), offset, totalCount);

                offset += limit;

                if (response.getResults().size() < limit || allResults.size() >= totalCount) {
                    break;
                }

            } while (true);

            log.info("Received total of {} formations from API", allResults.size());

            Map<String, MontpellierFormationDto> uniqueFormations = allResults.stream()
                    .filter(dto -> dto.getLibelleIntitule1() != null && !dto.getLibelleIntitule1().isEmpty())
                    .collect(Collectors.toMap(
                            dto -> dto.getLibelleIntitule1() + "_" + dto.getNiveau(),
                            dto -> dto,
                            (existing, replacement) -> {
                                String existingYear = existing.getAnneeUniversitaire();
                                String replacementYear = replacement.getAnneeUniversitaire();
                                if (replacementYear != null && replacementYear.compareTo(existingYear != null ? existingYear : "") > 0) {
                                    return replacement;
                                }
                                return existing;
                            }
                    ));

            log.info("Processing {} unique formations after deduplication", uniqueFormations.size());

            List<FormationEntity> formations = uniqueFormations.values().stream()
                    .map(this::transformToFormationEntity)
                    .collect(Collectors.toList());

            log.info("Successfully transformed {} formations", formations.size());

            return formations;

        } catch (Exception e) {
            log.error("Error fetching formations from Montpellier API", e);
            return new ArrayList<>();
        }
    }

    private FormationEntity transformToFormationEntity(MontpellierFormationDto dto) {
        String name = dto.getLibelleIntitule1();
        String level = mapLevel(dto.getNiveau(), dto.getDegetu());
        String ufr = mapDisciplineToUfr(dto.getGdDiscipline(), dto.getSectDisciplinaire(), dto.getDiscipline());
        String applicationDate = mapApplicationDate(dto.getAnneeUniversitaire());

        return new FormationEntity(name, level, ufr, applicationDate);
    }

    private String mapLevel(String niveau, Integer degetu) {
        if (degetu != null) {
            if (degetu == 1) return "Licence 1";
            if (degetu == 2) return "Licence 2";
            if (degetu == 3) return "Licence 3";
            if (degetu == 4) return "Master 1";
            if (degetu == 5) return "Master 2";
            if (degetu == 6) return "Doctorat 1";
            if (degetu == 7) return "Doctorat 2";
            if (degetu == 8) return "Doctorat 3";
        }

        if ("01".equals(niveau)) return "Master 1";
        if ("02".equals(niveau)) return "Master 2";

        return "Formation";
    }

    private String mapDisciplineToUfr(String gdDiscipline, String sectDisciplinaire, String discipline) {
        if (sectDisciplinaire != null && disciplineToUfrMapping.containsKey(sectDisciplinaire)) {
            return disciplineToUfrMapping.get(sectDisciplinaire);
        }

        if (discipline != null && disciplineToUfrMapping.containsKey(discipline)) {
            return disciplineToUfrMapping.get(discipline);
        }

        if (gdDiscipline != null && disciplineToUfrMapping.containsKey(gdDiscipline)) {
            return disciplineToUfrMapping.get(gdDiscipline);
        }

        String allDisciplines = (gdDiscipline != null ? gdDiscipline : "") + " " +
                (sectDisciplinaire != null ? sectDisciplinaire : "") + " " +
                (discipline != null ? discipline : "");

        if (allDisciplines.toLowerCase().contains("droit") || allDisciplines.toLowerCase().contains("juridique")) {
            return "UFR Droit et Sciences Politiques";
        }
        if (allDisciplines.toLowerCase().contains("économie") || allDisciplines.toLowerCase().contains("gestion")) {
            return "UFR Économie et Gestion";
        }
        if (allDisciplines.toLowerCase().contains("informatique")) {
            return "UFR Informatique";
        }
        if (allDisciplines.toLowerCase().contains("santé") || allDisciplines.toLowerCase().contains("médecine")) {
            return "UFR Santé";
        }
        if (allDisciplines.toLowerCase().contains("lettre") || allDisciplines.toLowerCase().contains("humaine")) {
            return "UFR Lettres et Sciences Humaines";
        }

        return "UFR Sciences";
    }

    private String mapApplicationDate(String anneeUniversitaire) {
        if (anneeUniversitaire != null && !anneeUniversitaire.isEmpty()) {
            return "Candidatures 2025-2026";
        }
        return "À définir";
    }
}
