package com.exalink.hrmsdatabaseapi.entity.market;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="MARKET_SUBBUSINESSLINE")
@Data
public class SubBusinessLine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name = "subBusinessLine")
	private String subBusinessLine;
	
	@ManyToOne(targetEntity=MarketOffering.class)
	@JsonIgnore
	@JoinColumn(name="marketOffering", nullable=false)
	@JsonManagedReference
	private MarketOffering marketOffering;
	
}
