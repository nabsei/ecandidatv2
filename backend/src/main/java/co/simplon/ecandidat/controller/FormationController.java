package co.simplon.ecandidat.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.ecandidat.entity.FormationEntity;
import co.simplon.ecandidat.service.FormationService;
import co.simplon.ecandidat.service.MontpellierFormationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/formations")
public class FormationController {

    private final FormationService formationService;
    private final MontpellierFormationService montpellierFormationService;

    public FormationController(FormationService formationService, MontpellierFormationService montpellierFormationService) {
        this.formationService = formationService;
        this.montpellierFormationService = montpellierFormationService;
    }

    @GetMapping
    public ResponseEntity<List<FormationEntity>> getAllFormations() {
        List<FormationEntity> formations = montpellierFormationService.fetchFormationsFromMontpellier();
        return ResponseEntity.ok(formations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormationEntity> getFormationById(@PathVariable Long id) {
        return formationService.getFormationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ufr/{ufr}")
    public ResponseEntity<List<FormationEntity>> getFormationsByUfr(@PathVariable String ufr) {
        List<FormationEntity> formations = formationService.getFormationsByUfr(ufr);
        return ResponseEntity.ok(formations);
    }

    @GetMapping("/montpellier")
    public ResponseEntity<List<FormationEntity>> getFormationsFromMontpellier() {
        List<FormationEntity> formations = montpellierFormationService.fetchFormationsFromMontpellier();
        return ResponseEntity.ok(formations);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FormationEntity> createFormation(@Valid @RequestBody FormationEntity formation) {
        FormationEntity createdFormation = formationService.createFormation(formation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFormation);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FormationEntity> updateFormation(
            @PathVariable Long id,
            @Valid @RequestBody FormationEntity formation) {
        return formationService.updateFormation(id, formation)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        if (formationService.deleteFormation(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
