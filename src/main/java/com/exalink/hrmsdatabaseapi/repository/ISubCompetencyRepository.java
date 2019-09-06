package com.exalink.hrmsdatabaseapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.competency.SubCompetency;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface ISubCompetencyRepository extends JpaRepository<SubCompetency, Long>{
	Optional<SubCompetency> findById(Long id);
	Optional<SubCompetency> findBySubCompetency(String subCompetency);
}
