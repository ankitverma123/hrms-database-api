package com.exalink.hrmsdatabaseapi.entity.jobRequirement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.exalink.hrmsdatabaseapi.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name = "JobPostStatus")
@Data
@EqualsAndHashCode(callSuper=false)
public class JobPostStatus extends BaseEntity {
	
	@Column(name = "status")
	private String status;
	
}
