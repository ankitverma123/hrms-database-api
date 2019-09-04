package com.exalink.hrmsdatabaseapi.entity.candidate;

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
@Table(name="CANDIDATE_SOURCES")
@Data
public class CandidateSources {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "isActive")
	private Boolean isActive;
}
