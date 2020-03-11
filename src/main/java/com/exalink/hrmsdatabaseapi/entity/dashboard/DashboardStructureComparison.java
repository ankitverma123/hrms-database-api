package com.exalink.hrmsdatabaseapi.entity.dashboard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.exalink.hrmsdatabaseapi.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="DASHBOARD_COMPARISON")
@Data
@EqualsAndHashCode(callSuper=false)
public class DashboardStructureComparison extends BaseEntity{
	
	@Column(name = "entity")
	private String entity;
	
	@Column(name = "field")
	private String field;
	
	@Column(name = "mappedWith")
	private String mappedWith;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private DashboardStruture structure;
	
}
