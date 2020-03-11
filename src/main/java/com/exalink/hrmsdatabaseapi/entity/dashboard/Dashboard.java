package com.exalink.hrmsdatabaseapi.entity.dashboard;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.exalink.hrmsdatabaseapi.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="DASHBOARD")
@Data
@EqualsAndHashCode(callSuper=false)
public class Dashboard extends BaseEntity{
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "subTitle")
	private String subTitle;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "active")
	private Boolean active;
	
	@Column(name = "defaultChartType")
	private String defaultChartType;
	
	@Column(name = "supportedChartType")
	private String supportedChartType;
	
	@Column(name = "legendKey")
	private String legendKey;
	
	@Column(name = "xAxisKey")
	private String xAxisKey;
	
	@OneToOne(mappedBy = "dashboardId", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
	private DashboardStruture structure;
	
}
