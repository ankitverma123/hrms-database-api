package com.exalink.hrmsdatabaseapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.candidate.FinancialYear;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface IFinancialYearRepository extends JpaRepository<FinancialYear, Long>{
	Optional<FinancialYear> findById(UUID id);
	Optional<FinancialYear> findByFinancialYear(String financialYear);
}
