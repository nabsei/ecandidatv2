package co.simplon.ecandidat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.ecandidat.entity.CandidatureEntity;
import co.simplon.ecandidat.entity.UserEntity;

@Repository
public interface CandidatureRepository extends JpaRepository<CandidatureEntity, Long> {

    List<CandidatureEntity> findByUser(UserEntity user);

    List<CandidatureEntity> findByStatus(String status);
}
