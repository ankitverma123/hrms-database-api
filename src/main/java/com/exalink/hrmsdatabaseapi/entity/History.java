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
@Table(name="History")
@Data
@EqualsAndHashCode(callSuper=false)
public class History extends BaseEntity{
	
	@Column(name = "entityId")
	private String entityId;
	
	@Column(name = "className")
	private String className;
	
	@Column(name = "action")
	private String action;
	
	@Column(name = "role")
	private String role;
	
	@Type(type="org.hibernate.type.TextType")
	@Column(name="comment",length=Integer.MAX_VALUE)
	@Lob
	private String comment;
}
