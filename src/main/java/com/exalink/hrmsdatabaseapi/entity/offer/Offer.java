package com.exalink.hrmsdatabaseapi.entity.offer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="CANDIDATE_OFFER")
@Data
public class Offer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name = "comment")
	private String comment;
	
	@JsonBackReference
	@ManyToOne (cascade=CascadeType.ALL)  
	@JsonIgnore
	@JoinColumn(name="status", nullable=false)
	private OfferStatus status;
	
	@JsonBackReference
	@ManyToOne (cascade=CascadeType.ALL)  
	@JsonIgnore
	@JoinColumn(name="declineCategory")
	private OfferDeclineCategory declineCategory;
}
