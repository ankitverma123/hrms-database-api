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
@Table(name="Department")
@Data
@EqualsAndHashCode(callSuper=false)
public class Department extends BaseEntity{
	
	@Column(name = "name")
	private String name;
	
}
