package com.exalink.hrmsdatabaseapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exalink.hrmsdatabaseapi.entity.jobRequirement.JobPost;

/**
 * @author ankitkverma
 *
 */
@Repository
public interface IJobPostRepository extends JpaRepository<JobPost, Long>{

}
