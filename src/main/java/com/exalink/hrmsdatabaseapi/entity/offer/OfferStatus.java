package com.exalink.hrmsdatabaseapi.entity.offer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.exalink.hrmsdatabaseapi.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="OFFER_STATUS")
@Data
@EqualsAndHashCode(callSuper=false)
public class OfferStatus extends BaseEntity{

	@Column(name = "status")
	private String status;
}
