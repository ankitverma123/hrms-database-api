package com.exalink.hrmsdatabaseapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.Department;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface IDepartmentRespository extends JpaRepository<Department, Long>{
	Optional<Department> findById(UUID uuid);
	Optional<Department> findByName(String name);
}
