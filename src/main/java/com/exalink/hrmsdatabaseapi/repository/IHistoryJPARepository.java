package com.exalink.hrmsdatabaseapi.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.History;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface IHistoryJPARepository extends JpaRepository<History, UUID>{
	List<History> findByEntityIdAndClassName(String entityId, String className);
}
