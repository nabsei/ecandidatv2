package co.simplon.ecandidat.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "candidature")
public class CandidatureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "L'utilisateur est obligatoire")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @NotBlank(message = "La nationalité est obligatoire")
    @Size(max = 100, message = "La nationalité ne peut pas dépasser 100 caractères")
    @Column(nullable = false, length = 100)
    private String nationality;

    @NotBlank(message = "L'INE est obligatoire")
    @Size(max = 50, message = "L'INE ne peut pas dépasser 50 caractères")
    @Column(nullable = false, length = 50)
    private String ine;

    @NotBlank(message = "L'adresse est obligatoire")
    @Lob
    @Column(nullable = false)
    private String address;

    @NotBlank(message = "Le baccalauréat est obligatoire")
    @Lob
    @Column(nullable = false)
    private String baccalaureate;

    @NotBlank(message = "Le cursus est obligatoire")
    @Lob
    @Column(nullable = false)
    private String internalExternalCurriculum;

    @Lob
    @Column
    private String internships;

    @Lob
    @Column
    private String professionalExperience;

    @NotNull(message = "La formation est obligatoire")
    @ManyToOne
    @JoinColumn(name = "formation_id", nullable = false)
    private FormationEntity formation;

    @Column(length = 500)
    private String cvUrl;

    @NotBlank(message = "Le statut est obligatoire")
    @Size(max = 50, message = "Le statut ne peut pas dépasser 50 caractères")
    @Column(nullable = false, length = 50)
    private String status = "En attente";

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public CandidatureEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return this.user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIne() {
        return this.ine;
    }

    public void setIne(String ine) {
        this.ine = ine;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBaccalaureate() {
        return this.baccalaureate;
    }

    public void setBaccalaureate(String baccalaureate) {
        this.baccalaureate = baccalaureate;
    }

    public String getInternalExternalCurriculum() {
        return this.internalExternalCurriculum;
    }

    public void setInternalExternalCurriculum(String internalExternalCurriculum) {
        this.internalExternalCurriculum = internalExternalCurriculum;
    }

    public String getInternships() {
        return this.internships;
    }

    public void setInternships(String internships) {
        this.internships = internships;
    }

    public String getProfessionalExperience() {
        return this.professionalExperience;
    }

    public void setProfessionalExperience(String professionalExperience) {
        this.professionalExperience = professionalExperience;
    }

    public FormationEntity getFormation() {
        return this.formation;
    }

    public void setFormation(FormationEntity formation) {
        this.formation = formation;
    }

    public String getCvUrl() {
        return this.cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
