package com.exalink.hrmsdatabaseapi.entity.formstructures;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name = "DF_LAYOUT")
@Data
@EqualsAndHashCode(callSuper=false)
public class Layout extends BaseEntity{
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "httpRequest")
	private String httpRequest;
	
	@Column(name = "shortDescription")
	private String shortDescription;
	
	@Column(name = "longDescription")
	private String longDescription;
	
	@Column(name = "note")
	private String note;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true, name="requestCategoryId")
    private RequestCategory requestCategoryId;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "layoutId")
	private Set<FieldSets> fieldSets;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "layoutId")
	private Set<Element> elements;

}
