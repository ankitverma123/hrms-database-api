package com.exalink.hrmsdatabaseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.FileTracking;
/**
 * @author ankitkverma
 *
 */
@Repository
public interface IFileTrackingRepository extends JpaRepository<FileTracking, Long>{
	
}
