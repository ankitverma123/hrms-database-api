package com.exalink.hrmsdatabaseapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="ValidationCodes")
@Data
@EqualsAndHashCode(callSuper=false)
public class ValidationCodes extends BaseEntity{
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "meaning")
	private String meaning;
	
}
