package com.exalink.hrmsdatabaseapi.entity.market;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.exalink.hrmsdatabaseapi.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="MARKET_SUBBUSINESSLINE")
@Getter
@Setter
public class SubBusinessLine extends BaseEntity{

	@Column(name = "subBusinessLine")
	private String subBusinessLine;
	
	@ManyToOne(targetEntity=MarketOffering.class)
	@JsonIgnore
	@JoinColumn(name="marketOffering", nullable=false)
	@JsonManagedReference
	private MarketOffering marketOffering;
	
}
