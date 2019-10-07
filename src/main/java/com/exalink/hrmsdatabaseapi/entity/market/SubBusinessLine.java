package com.exalink.hrmsdatabaseapi.entity.market;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="MARKET_SUBBUSINESSLINE")
@Data
@EqualsAndHashCode(callSuper=false)
public class SubBusinessLine extends BaseEntity{

	@Column(name = "subBusinessLine")
	private String subBusinessLine;
	
	@ManyToOne(targetEntity=MarketOffering.class)
	@JsonIgnore
	@JoinColumn(name="marketOffering", nullable=false)
	@JsonManagedReference
	private MarketOffering marketOffering;
	
}
