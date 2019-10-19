package com.exalink.hrmsdatabaseapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.offer.OfferStatus;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface IOfferStatusRepository extends JpaRepository<OfferStatus, Long>{
	Optional<OfferStatus> findById(UUID id);
}
