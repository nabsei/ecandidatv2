package co.simplon.ecandidat.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MontpellierFormationDto {

    @JsonProperty("libelle_intitule_1")
    private String libelleIntitule1;

    @JsonProperty("niveau")
    private String niveau;

    @JsonProperty("degetu")
    private Integer degetu;

    @JsonProperty("gd_disciscipline")
    private String gdDiscipline;

    @JsonProperty("sect_disciplinaire")
    private String sectDisciplinaire;

    @JsonProperty("discipline")
    private String discipline;

    @JsonProperty("annee_universitaire")
    private String anneeUniversitaire;

    @JsonProperty("effectif_sans_cpge")
    private Integer effectifSansCpge;

    @JsonProperty("diplome")
    private String diplome;

    @JsonProperty("diplome_lib")
    private String diplomeLib;

    public MontpellierFormationDto() {
    }

    public String getLibelleIntitule1() {
        return libelleIntitule1;
    }

    public void setLibelleIntitule1(String libelleIntitule1) {
        this.libelleIntitule1 = libelleIntitule1;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Integer getDegetu() {
        return degetu;
    }

    public void setDegetu(Integer degetu) {
        this.degetu = degetu;
    }

    public String getGdDiscipline() {
        return gdDiscipline;
    }

    public void setGdDiscipline(String gdDiscipline) {
        this.gdDiscipline = gdDiscipline;
    }

    public String getSectDisciplinaire() {
        return sectDisciplinaire;
    }

    public void setSectDisciplinaire(String sectDisciplinaire) {
        this.sectDisciplinaire = sectDisciplinaire;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getAnneeUniversitaire() {
        return anneeUniversitaire;
    }

    public void setAnneeUniversitaire(String anneeUniversitaire) {
        this.anneeUniversitaire = anneeUniversitaire;
    }

    public Integer getEffectifSansCpge() {
        return effectifSansCpge;
    }

    public void setEffectifSansCpge(Integer effectifSansCpge) {
        this.effectifSansCpge = effectifSansCpge;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getDiplomeLib() {
        return diplomeLib;
    }

    public void setDiplomeLib(String diplomeLib) {
        this.diplomeLib = diplomeLib;
    }
}
