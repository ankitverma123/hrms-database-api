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
@Table(name="ONBOARD_STATUS")
@Data
@EqualsAndHashCode(callSuper=false)
public class OnboardStatus extends BaseEntity{

	@Column(name = "onboardStatus")
	private String onboardStatus;
	
	@Column(name = "isActive")
	private Boolean isActive = true;
}
