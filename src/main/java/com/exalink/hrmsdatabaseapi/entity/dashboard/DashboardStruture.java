package com.exalink.hrmsdatabaseapi.entity.dashboard;

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
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="DASHBOARD_STRUCTURE")
@Data
@EqualsAndHashCode(callSuper=false)
public class DashboardStruture extends BaseEntity{
	
	@Column(name = "entity")
	private String entity;
	
	@Column(name = "field")
	private String field;
	
	@Column(name = "self")
	private Boolean self;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dashboardId")
    private Dashboard dashboardId;
	
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "structure", orphanRemoval = true)
	private Set<DashboardStructureComparison> comparison = null;
}
