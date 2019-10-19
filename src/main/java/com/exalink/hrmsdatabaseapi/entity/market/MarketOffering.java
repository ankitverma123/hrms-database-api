package com.exalink.hrmsdatabaseapi.entity.market;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.exalink.hrmsfabric.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="MARKET_OFFERING")
@Getter
@Setter
public class MarketOffering extends BaseEntity{
	
	@Column(name = "market")
	private String market;
	
	@Column(name = "isActive")
	private Boolean isActive = true;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "marketOffering")
	@JsonBackReference
	private Set<SubBusinessLine> subBusinessLine = null;
	
}
