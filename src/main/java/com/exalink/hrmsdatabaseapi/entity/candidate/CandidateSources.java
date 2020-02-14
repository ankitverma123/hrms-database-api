package com.exalink.hrmsdatabaseapi.entity.candidate;

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
@Table(name="CANDIDATE_SOURCES")
@Data
@EqualsAndHashCode(callSuper=false)
public class CandidateSources extends BaseEntity{
	
	@Column(name = "candidateSource")
	private String candidateSource;
	
	@Column(name = "isActive")
	private Boolean isActive;
}
