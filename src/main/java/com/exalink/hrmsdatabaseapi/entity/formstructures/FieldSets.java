package com.exalink.hrmsdatabaseapi.entity.formstructures;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.exalink.hrmsdatabaseapi.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name = "DF_FIELDSET")
@Data
@EqualsAndHashCode(callSuper=false)
public class FieldSets extends BaseEntity{

	@Column(name = "name")
	private String name;
	
	@Column(name = "cssClass")
	private String cssClass;
	
	@Column(name = "type")
	private String type;
	
	@ManyToOne(targetEntity=Layout.class)
	@JsonIgnore
	@JoinColumn(name="layoutId")
	private Layout layoutId;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "fieldSetId")
	private Set<Element> elements = null;
	
}
