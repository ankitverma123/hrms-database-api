package com.exalink.hrmsdatabaseapi.entity.market;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="MARKET_OFFERING")
@Data
public class MarketOffering {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name = "market")
	private String market;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "marketOffering")
	private Set<SubBusinessLine> subBusinessLine = null;
	
}
