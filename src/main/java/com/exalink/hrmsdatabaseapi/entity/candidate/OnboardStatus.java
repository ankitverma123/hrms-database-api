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
@Table(name="ONBOARD_STATUS")
@Data
public class OnboardStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name = "status")
	private String status;
	
}
