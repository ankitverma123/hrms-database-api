package com.exalink.hrmsdatabaseapi.entity.candidate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="FINANCIAL_YEAR")
@Data
public class FinancialYear {

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name = "financialYear")
	private String financialYear;
	
}
