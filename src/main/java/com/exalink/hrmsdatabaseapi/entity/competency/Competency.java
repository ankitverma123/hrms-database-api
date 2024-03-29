package com.exalink.hrmsdatabaseapi.entity.competency;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.exalink.hrmsdatabaseapi.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="COMPETENCY")
@Getter
@Setter
public class Competency extends BaseEntity{
	
	@Column(name = "competency")
	private String competency;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "competency")
	private Set<SubCompetency> subCompetency = null;
}
