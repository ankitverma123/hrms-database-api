package com.exalink.hrmsdatabaseapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.competency.SubCompetency;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface ISubCompetencyRepository extends JpaRepository<SubCompetency, Long>{
	Optional<SubCompetency> findById(UUID id);
	Optional<SubCompetency> findBySubCompetency(String subCompetency);
	
	@Query("FROM SubCompetency sc where sc.competency.id = :competencyId")
	List<SubCompetency> listByCompetency(@Param("competencyId") UUID competencyId);
	
	@Query("FROM SubCompetency sc where sc.subCompetency = :subCompetency and sc.competency.competency = :competency")
	Optional<SubCompetency> findBySubCompetencyAndCompetency(@Param("subCompetency") String subCompetency, @Param("competency") String competency);
}
