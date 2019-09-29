package com.exalink.hrmsdatabaseapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.Report;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface IReportRepository extends JpaRepository<Report, Long> {
	
	Optional<Report> findByReportName(String name);
	
}
