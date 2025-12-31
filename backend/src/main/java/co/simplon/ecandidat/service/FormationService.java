package co.simplon.ecandidat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.simplon.ecandidat.entity.FormationEntity;
import co.simplon.ecandidat.repository.FormationRepository;

@Service
public class FormationService {

    private final FormationRepository formationRepository;

    public FormationService(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    public List<FormationEntity> getAllFormations() {
        return formationRepository.findAll();
    }

    public Optional<FormationEntity> getFormationById(Long id) {
        return formationRepository.findById(id);
    }

    public List<FormationEntity> getFormationsByUfr(String ufr) {
        return formationRepository.findByUfr(ufr);
    }

    public FormationEntity createFormation(FormationEntity formation) {
        return formationRepository.save(formation);
    }

    public Optional<FormationEntity> updateFormation(Long id, FormationEntity formationDetails) {
        return formationRepository.findById(id).map(formation -> {
            formation.setName(formationDetails.getName());
            formation.setLevel(formationDetails.getLevel());
            formation.setUfr(formationDetails.getUfr());
            formation.setApplicationDate(formationDetails.getApplicationDate());
            return formationRepository.save(formation);
        });
    }

    public boolean deleteFormation(Long id) {
        if (formationRepository.existsById(id)) {
            formationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
