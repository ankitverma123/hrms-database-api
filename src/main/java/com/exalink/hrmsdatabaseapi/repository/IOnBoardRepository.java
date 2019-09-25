package com.exalink.hrmsdatabaseapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.candidate.OnboardStatus;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface IOnBoardRepository extends JpaRepository<OnboardStatus, Long>{
	
	Optional<OnboardStatus> findById(Long id);
	Optional<OnboardStatus> findByOnboardStatus(String status);
	
}
