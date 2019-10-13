package com.exalink.hrmsdatabaseapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.candidate.CandidateSources;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface ICandidateSourceRepository extends JpaRepository<CandidateSources, Long>, JpaSpecificationExecutor<CandidateSources>{
	
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CandidateSources c WHERE c.id = :id")
    boolean candidateSourceExistance(@Param("id") UUID id);
	
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CandidateSources c WHERE c.candidateSource = :name")
    boolean candidateSourceExistance(@Param("name") String name);
	
    Optional<CandidateSources> findById(UUID id);
    
    Optional<CandidateSources> findByCandidateSource(String name);
    
    @Query("FROM CandidateSources c WHERE c.isActive is true")
    List<CandidateSources> listActiveCandidateSources();
    
}
