package com.exalink.hrmsdatabaseapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.exalink.hrmsfabric.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="FILE_TRACKING")
@Data
@EqualsAndHashCode(callSuper=false)
public class FileTracking extends BaseEntity{
	
	@Column(name = "fileName")
	private String fileName;
	
	@Column(name = "documentUUID")
	private String documentUUID;
	
	@Column(name = "fileConsumeState")
	private String fileConsumeState;
	
	@Column(name = "failureReason")
	private String failureReason;
	
}
