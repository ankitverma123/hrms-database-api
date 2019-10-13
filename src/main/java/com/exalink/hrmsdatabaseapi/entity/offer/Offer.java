package com.exalink.hrmsdatabaseapi.entity.offer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.exalink.hrmsfabric.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="CANDIDATE_OFFER")
@Data
@EqualsAndHashCode(callSuper=false)
public class Offer extends BaseEntity{
	
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
