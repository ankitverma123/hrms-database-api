package com.exalink.hrmsdatabaseapi.entity.candidate;

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
@Table(name="FINANCIAL_YEAR")
@Data
@EqualsAndHashCode(callSuper=false)
public class FinancialYear extends BaseEntity{

	@Column(name = "financialYear")
	private String financialYear;
	
	@Column(name = "isActive")
	private Boolean isActive = true;
}
