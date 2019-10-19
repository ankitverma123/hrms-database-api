package com.exalink.hrmsdatabaseapi.entity.candidate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.exalink.hrmsdatabaseapi.entity.competency.Competency;
import com.exalink.hrmsdatabaseapi.entity.competency.SubCompetency;
import com.exalink.hrmsdatabaseapi.entity.market.MarketOffering;
import com.exalink.hrmsdatabaseapi.entity.market.SubBusinessLine;
import com.exalink.hrmsdatabaseapi.entity.offer.Offer;
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
@Table(name="CANDIDATE")
@Data
@EqualsAndHashCode(callSuper=false)
public class Candidate extends BaseEntity{
	
	@Column(name = "firstName")
	private String firstName;
	
	@Column(name = "middleName")
	private String middleName;
	
	@Column(name = "lastName")
	private String lastName;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "emailAddress")
	private String emailAddress;
	
	@Column(name = "totalExperience")
	private Double totalExperience;
	
	@Column(name = "relevantExperience")
	private Double relevantExperience;
	
	@Column(name = "primaryContact")
	private String primaryContact;
	
	@Column(name = "secondaryContact")
	private String secondaryContact;
	
	@Column(name = "lastCompany")
	private String lastCompany;
	
	@JsonBackReference
	@ManyToOne (cascade=CascadeType.ALL)  
	@JsonIgnore
	@JoinColumn(name="candidateSource", nullable=false)
	private CandidateSources candidateSource;
	
	@JsonBackReference
	@ManyToOne (cascade=CascadeType.ALL)  
	@JsonIgnore
	@JoinColumn(name="financialYear", nullable=false)
	private FinancialYear financialYear;
	
	@JsonBackReference
	@ManyToOne (cascade=CascadeType.ALL)  
	@JsonIgnore
	@JoinColumn(name="onboardStatus", nullable=false)
	private OnboardStatus onboardStatus;
	
	@JsonBackReference
	@ManyToOne (cascade=CascadeType.ALL)  
	@JsonIgnore
	@JoinColumn(name="marketOffering", nullable=false)
	private MarketOffering marketOffering;
	
	@JsonBackReference
	@ManyToOne (cascade=CascadeType.ALL)  
	@JsonIgnore
	@JoinColumn(name="subBusinessLine", nullable=false)
	private SubBusinessLine subBusinessLine;
	
	@JsonBackReference
	@ManyToOne (cascade=CascadeType.ALL)  
	@JsonIgnore
	@JoinColumn(name="competency", nullable=false)
	private Competency competency;
	
	@JsonBackReference
	@ManyToOne (cascade=CascadeType.ALL)  
	@JsonIgnore
	@JoinColumn(name="subCompetency", nullable=false)
	private SubCompetency subCompetency;
	
	@JsonBackReference
	@ManyToOne (cascade=CascadeType.ALL)  
	@JsonIgnore
	@JoinColumn(name="candidateOfferStatus")
	private Offer candidateOfferStatus;
	
	private transient String fullName;
	private transient String sourceName;
	private transient String onboardStatusValue;
	private transient String marketOfferingBusinessLine;
	private transient String competencyValue;
	
}
