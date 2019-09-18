package com.exalink.hrmsdatabaseapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.offer.OfferDeclineCategory;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface IOfferDeclineRepository extends JpaRepository<OfferDeclineCategory, Long>{
	Optional<OfferDeclineCategory> findByCategory(String category);
}
