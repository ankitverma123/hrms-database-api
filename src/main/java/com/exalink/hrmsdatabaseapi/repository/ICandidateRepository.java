package com.exalink.hrmsdatabaseapi.repository;

import java.util.Optional;
import java.util.UUID;

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
	
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Candidate c WHERE c.emailAddress = :emailAddress and c.id <> :candidateId")
    boolean existsByEmailAddressForSomeOtherUser(@Param("emailAddress") String emailAddress, @Param("candidateId") UUID candidateId);
	
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Candidate c WHERE c.primaryContact = :primaryContact or c.secondaryContact = :secondaryContact")
    boolean existsByPrimaryOrSecondaryContact(@Param("primaryContact") String primaryContact, @Param("secondaryContact") String secondaryContact);
	
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Candidate c WHERE c.primaryContact = :primaryContact and c.id <> :candidateId")
    boolean existsByPrimaryContactForSomeOtherUser(@Param("primaryContact") String primaryContact, @Param("candidateId") UUID candidateId);
	
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Candidate c WHERE c.secondaryContact = :secondaryContact and c.id <> :candidateId")
    boolean existsBySecondaryContactForSomeOtherUser(@Param("secondaryContact") String secondaryContact, @Param("candidateId") UUID candidateId);
	
	Optional<Candidate> findById(UUID id);
}
