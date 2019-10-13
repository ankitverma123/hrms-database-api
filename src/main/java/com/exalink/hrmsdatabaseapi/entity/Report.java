package com.exalink.hrmsdatabaseapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.exalink.hrmsfabric.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ankitkverma
 *
 */
@Entity
@Table(name="REPORTS")
@Data
@EqualsAndHashCode(callSuper=false)
public class Report extends BaseEntity{

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
