package com.exalink.hrmsdatabaseapi.repository.dashboard;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.dashboard.DashboardStructureComparison;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface IDashboardComparisonRepository extends JpaRepository<DashboardStructureComparison, Long>{
	@Query("FROM DashboardStructureComparison dsc WHERE dsc.structure.id = :id")
	List<DashboardStructureComparison> findByDashboardStructureID(@Param("id")UUID id);
}
