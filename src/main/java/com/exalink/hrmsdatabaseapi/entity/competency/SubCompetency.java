package com.exalink.hrmsdatabaseapi.entity.competency;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.exalink.hrmsfabric.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="SUB_COMPETENCY")
@Data
@EqualsAndHashCode(callSuper=false)
public class SubCompetency extends BaseEntity{

	@Column(name = "subCompetency")
	private String subCompetency;
	
	@ManyToOne(targetEntity=Competency.class)
	@JsonIgnore
	@JoinColumn(name="competency", nullable=false)
	private Competency competency;
	
}
