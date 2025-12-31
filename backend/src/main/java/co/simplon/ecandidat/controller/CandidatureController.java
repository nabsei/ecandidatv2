package co.simplon.ecandidat.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.ecandidat.entity.CandidatureEntity;
import co.simplon.ecandidat.entity.UserEntity;
import co.simplon.ecandidat.service.CandidatureService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/candidatures")
public class CandidatureController {

    private final CandidatureService candidatureService;

    public CandidatureController(CandidatureService candidatureService) {
        this.candidatureService = candidatureService;
    }

    @GetMapping("/my")
    public ResponseEntity<List<CandidatureEntity>> getMyCandidatures(Authentication authentication) {
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        List<CandidatureEntity> candidatures = candidatureService.getCandidaturesByUser(currentUser);
        return ResponseEntity.ok(candidatures);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidatureEntity> getCandidatureById(@PathVariable Long id) {
        return candidatureService.getCandidatureById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CandidatureEntity> createCandidature(
            @Valid @RequestBody CandidatureEntity candidature,
            Authentication authentication) {
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        candidature.setUser(currentUser);
        candidature.setStatus("En attente");
        CandidatureEntity createdCandidature = candidatureService.createCandidature(candidature);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCandidature);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidatureEntity> updateCandidature(
            @PathVariable Long id,
            @Valid @RequestBody CandidatureEntity candidature) {
        return candidatureService.updateCandidature(id, candidature)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidature(@PathVariable Long id) {
        if (candidatureService.deleteCandidature(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CandidatureEntity>> getAllCandidatures() {
        List<CandidatureEntity> candidatures = candidatureService.getAllCandidatures();
        return ResponseEntity.ok(candidatures);
    }

    @PutMapping("/admin/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CandidatureEntity> updateStatus(
            @PathVariable Long id,
            @RequestBody String status) {
        return candidatureService.updateStatus(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
