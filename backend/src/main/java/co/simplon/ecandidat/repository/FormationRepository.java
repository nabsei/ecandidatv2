package co.simplon.ecandidat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.ecandidat.entity.FormationEntity;

@Repository
public interface FormationRepository extends JpaRepository<FormationEntity, Long> {

    List<FormationEntity> findByUfr(String ufr);
}
