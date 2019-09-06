package com.exalink.hrmsdatabaseapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.candidate.CandidateSources;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface ICandidateSourceRepository extends JpaRepository<CandidateSources, Long>{
	
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CandidateSources c WHERE c.id = :id")
    boolean candidateSourceExistance(@Param("id") Long id);
	
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CandidateSources c WHERE c.name = :name")
    boolean candidateSourceExistance(@Param("name") String name);
	
    Optional<CandidateSources> findById(Long id);
    
    Optional<CandidateSources> findByName(String name);
}
