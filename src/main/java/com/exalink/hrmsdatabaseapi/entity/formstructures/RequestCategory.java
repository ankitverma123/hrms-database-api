package com.exalink.hrmsdatabaseapi.entity.formstructures;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.exalink.hrmsdatabaseapi.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ankitkverma
 *
 */
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
@Entity
@Table(name = "DF_REQUESTCATEGORY")
@Data
@EqualsAndHashCode(callSuper=false)
public class RequestCategory extends BaseEntity{
	
	@Column(name = "requestCategory")
	private String requestCategory;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "queryService")
	private String queryService;
	
	@Column(name = "submissionService")
	private String submissionService;
	
	@OneToOne(mappedBy = "requestCategoryId")
	private Layout layout;

}
