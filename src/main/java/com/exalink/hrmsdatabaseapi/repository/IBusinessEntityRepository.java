package com.exalink.hrmsdatabaseapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.BusinessEntity;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface IBusinessEntityRepository extends JpaRepository<BusinessEntity, Long>{
	Optional<BusinessEntity> findById(UUID uuid);
	Optional<BusinessEntity> findByName(String name);
}
