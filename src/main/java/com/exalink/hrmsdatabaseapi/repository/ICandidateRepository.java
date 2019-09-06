package com.exalink.hrmsdatabaseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.candidate.Candidate;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface ICandidateRepository extends JpaRepository<Candidate, Long>,JpaSpecificationExecutor<Candidate>{

	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Candidate c WHERE c.emailAddress = :emailAddress")
    boolean existsByEmailAddress(@Param("emailAddress") String emailAddress);
	
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Candidate c WHERE c.primaryContact = :primaryContact or c.secondaryContact = :secondaryContact")
    boolean existsByPrimaryOrSecondaryContact(@Param("primaryContact") String primaryContact, @Param("secondaryContact") String secondaryContact);
	
}
