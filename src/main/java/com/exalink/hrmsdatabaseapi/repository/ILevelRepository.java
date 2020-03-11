package com.exalink.hrmsdatabaseapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.Level;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface ILevelRepository extends JpaRepository<Level, Long>{
	Optional<Level> findById(UUID uuid);
	Optional<Level> findByName(String name);
}
