package com.exalink.hrmsdatabaseapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Data;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="REPORTS")
@Data
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name = "reportName")
	private String reportName;
	
	@Type(type="org.hibernate.type.TextType")
	@Column(name="headers",length=Integer.MAX_VALUE)
	@Lob
	private String headers;
	
	@Type(type="org.hibernate.type.TextType")
	@Column(name="keys",length=Integer.MAX_VALUE)
	@Lob
	private String keys;
	
}
