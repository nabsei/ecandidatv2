package co.simplon.ecandidat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "formation")
public class FormationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la formation est obligatoire")
    @Size(max = 200, message = "Le nom ne peut pas dépasser 200 caractères")
    @Column(nullable = false, length = 200)
    private String name;

    @NotBlank(message = "Le niveau est obligatoire")
    @Size(max = 50, message = "Le niveau ne peut pas dépasser 50 caractères")
    @Column(nullable = false, length = 50)
    private String level;

    @NotBlank(message = "L'UFR est obligatoire")
    @Size(max = 100, message = "L'UFR ne peut pas dépasser 100 caractères")
    @Column(nullable = false, length = 100)
    private String ufr;

    @Column(length = 100)
    private String applicationDate;

    public FormationEntity() {
    }

    public FormationEntity(String name, String level, String ufr, String applicationDate) {
        this.name = name;
        this.level = level;
        this.ufr = ufr;
        this.applicationDate = applicationDate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUfr() {
        return this.ufr;
    }

    public void setUfr(String ufr) {
        this.ufr = ufr;
    }

    public String getApplicationDate() {
        return this.applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }
}
