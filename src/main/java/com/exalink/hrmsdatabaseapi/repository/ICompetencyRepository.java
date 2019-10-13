package com.exalink.hrmsdatabaseapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.competency.Competency;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface ICompetencyRepository extends JpaRepository<Competency, Long>{
	Optional<Competency> findById(UUID uuid);
}
