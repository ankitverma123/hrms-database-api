package com.exalink.hrmsdatabaseapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="FILE_TRACKING")
@Data
public class FileTracking extends BaseEntity{
	
	@Column(name = "fileName")
	private String fileName;
	
	@Column(name = "fileConsumeState")
	private String fileConsumeState;
	
	@Column(name = "failureReason")
	private String failureReason;
	
}
