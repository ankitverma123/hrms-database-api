package com.exalink.hrmsdatabaseapi.entity.jobRequirement;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.exalink.hrmsdatabaseapi.entity.competency.Competency;
import com.exalink.hrmsdatabaseapi.entity.competency.SubCompetency;
import com.exalink.hrmsdatabaseapi.entity.market.MarketOffering;
import com.exalink.hrmsdatabaseapi.entity.market.SubBusinessLine;
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
@Table(name = "JobPost")
@Data
@EqualsAndHashCode(callSuper=false)
public class JobPost extends BaseEntity{
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "minExperience")
	private Double minExperience;
	
	@Column(name = "maxExperience")
	private Double maxExperience;
	
	@Column(name = "vacancies")
	private Long vacancies;
	
	@Type(type="org.hibernate.type.TextType")
	@Column(name="soleResponsibility",length=Integer.MAX_VALUE)
	@Lob
	private String soleResponsibility;
	
	@Type( type = "string-array" )
    @Column(name = "skillsRequired", columnDefinition = "text[]")
    private String[] skillsRequired;
	
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

	@Column(name = "location")
	private String location;
	
	@Type(type="org.hibernate.type.TextType")
	@Column(name="additionalComments",length=Integer.MAX_VALUE)
	@Lob
	private String additionalComments;
	
	@JsonBackReference
	@ManyToOne (cascade=CascadeType.ALL)  
	@JsonIgnore
	@JoinColumn(name="status", nullable=false)
	private JobPostStatus status;
	
	private transient String marketOfferingTitle;
	private transient String marketOfferingSubBusinessLineTitle;
	private transient String competencyTitle;
	private transient String subCompetencyTitle;
	private transient String jobPostStatus;
}
