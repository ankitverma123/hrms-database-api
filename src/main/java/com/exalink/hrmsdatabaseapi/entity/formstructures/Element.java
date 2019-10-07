package com.exalink.hrmsdatabaseapi.entity.formstructures;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.exalink.hrmsdatabaseapi.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.EqualsAndHashCode;

/**
 * @author ankitkverma
 *
 */
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
@Entity
@Table(name = "DF_ELEMENT")
@EqualsAndHashCode(callSuper=false)
public class Element  extends BaseEntity{
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "label")
	private String label;
	
	@Column(name = "showLabel")
	private Boolean showLabel;
	
	@Column(name = "controlType")
	private String controlType;
	
	@Column(name = "uiSequence")
	private Long uiSequence;
	
	@Column(name = "required")
	private Boolean required;
	
	@Column(name = "defaultValue")
	private String defaultValue;
	
	@Column(name = "apiQuery")
	private String apiQuery;
	
	@Column(name = "visibility")
	private Boolean visibility;
	
	@Column(name = "specialNote")
	private String specialNote;
	
	@Column(name = "isDependent")
	private Boolean isDependent;
	
	@Column(name = "dependentOn")
	private String dependentOn;
	
	@ManyToOne(targetEntity=Layout.class)
	@JsonIgnore
	@JoinColumn(name="layoutId")
	private Layout layoutId;

	@ManyToOne(targetEntity=FieldSets.class)
	@JsonIgnore
	@JoinColumn(name="fieldSetId")
	private FieldSets fieldSetId;
	
}
