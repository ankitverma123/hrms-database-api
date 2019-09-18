package com.exalink.hrmsdatabaseapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.market.MarketOffering;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface IMarketOfferingRepository extends JpaRepository<MarketOffering, Long>{
	Optional<MarketOffering> findByMarket(String market);
}
