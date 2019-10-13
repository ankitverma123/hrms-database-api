package com.exalink.hrmsdatabaseapi.entity.offer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.exalink.hrmsfabric.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="OFFER_DECLINE_CATEGORY")
@Data
@EqualsAndHashCode(callSuper=false)
public class OfferDeclineCategory extends BaseEntity{

	@Column(name = "category")
	private String category;
	
	@Column(name = "isActive")
	private Boolean isActive = true;
}
