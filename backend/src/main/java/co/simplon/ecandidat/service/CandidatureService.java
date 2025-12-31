package co.simplon.ecandidat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.simplon.ecandidat.entity.CandidatureEntity;
import co.simplon.ecandidat.entity.UserEntity;
import co.simplon.ecandidat.repository.CandidatureRepository;

@Service
public class CandidatureService {

    private final CandidatureRepository candidatureRepository;

    public CandidatureService(CandidatureRepository candidatureRepository) {
        this.candidatureRepository = candidatureRepository;
    }

    public List<CandidatureEntity> getAllCandidatures() {
        return candidatureRepository.findAll();
    }

    public Optional<CandidatureEntity> getCandidatureById(Long id) {
        return candidatureRepository.findById(id);
    }

    public List<CandidatureEntity> getCandidaturesByUser(UserEntity user) {
        return candidatureRepository.findByUser(user);
    }

    public List<CandidatureEntity> getCandidaturesByStatus(String status) {
        return candidatureRepository.findByStatus(status);
    }

    public CandidatureEntity createCandidature(CandidatureEntity candidature) {
        return candidatureRepository.save(candidature);
    }

    public Optional<CandidatureEntity> updateCandidature(Long id, CandidatureEntity candidatureDetails) {
        return candidatureRepository.findById(id).map(candidature -> {
            candidature.setNationality(candidatureDetails.getNationality());
            candidature.setIne(candidatureDetails.getIne());
            candidature.setAddress(candidatureDetails.getAddress());
            candidature.setBaccalaureate(candidatureDetails.getBaccalaureate());
            candidature.setInternalExternalCurriculum(candidatureDetails.getInternalExternalCurriculum());
            candidature.setInternships(candidatureDetails.getInternships());
            candidature.setProfessionalExperience(candidatureDetails.getProfessionalExperience());
            candidature.setFormation(candidatureDetails.getFormation());
            candidature.setCvUrl(candidatureDetails.getCvUrl());
            candidature.setStatus(candidatureDetails.getStatus());
            return candidatureRepository.save(candidature);
        });
    }

    public boolean deleteCandidature(Long id) {
        if (candidatureRepository.existsById(id)) {
            candidatureRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<CandidatureEntity> updateStatus(Long id, String status) {
        return candidatureRepository.findById(id).map(candidature -> {
            candidature.setStatus(status);
            return candidatureRepository.save(candidature);
        });
    }
}
