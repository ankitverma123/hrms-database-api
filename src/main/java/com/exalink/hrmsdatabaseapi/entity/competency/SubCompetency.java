package com.exalink.hrmsdatabaseapi.entity.competency;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="SUB_COMPETENCY")
@Data
public class SubCompetency {

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name = "subCompetency")
	private String subCompetency;
	
	@ManyToOne(targetEntity=Competency.class)
	@JsonIgnore
	@JoinColumn(name="competency", nullable=false)
	private Competency competency;
	
}
