package com.exalink.hrmsdatabaseapi.repository.dashboard;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.dashboard.Dashboard;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface IDashboardRepository extends JpaRepository<Dashboard, Long>{
	Optional<Dashboard> findByName(String name);
}
