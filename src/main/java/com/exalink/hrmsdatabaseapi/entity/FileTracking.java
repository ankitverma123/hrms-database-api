package com.exalink.hrmsdatabaseapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name = "fileName")
	private String fileName;
	
	@Column(name = "fileConsumeState")
	private String fileConsumeState;
	
	@Column(name = "failureReason")
	private String failureReason;
	
}
