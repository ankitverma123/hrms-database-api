package com.exalink.hrmsdatabaseapi.entity.offer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="OFFER_DECLINE_CATEGORY")
@Data
public class OfferDeclineCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name = "category")
	private String category;
	
}
