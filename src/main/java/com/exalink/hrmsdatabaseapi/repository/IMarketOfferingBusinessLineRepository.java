package com.exalink.hrmsdatabaseapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.market.SubBusinessLine;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface IMarketOfferingBusinessLineRepository extends JpaRepository<SubBusinessLine, Long>{
	Optional<SubBusinessLine> findById(Long id);
	Optional<SubBusinessLine> findBySubBusinessLine(String subBusinessLine);
}
