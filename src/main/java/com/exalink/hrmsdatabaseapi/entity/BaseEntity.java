package com.exalink.hrmsdatabaseapi.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author ankitkverma
 *
 */
@MappedSuperclass
@Data
public class BaseEntity {

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createdAt")
	@JsonIgnore
    @CreatedDate
	private Date createdAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updatedAt")
	@JsonIgnore
    @CreatedDate
	private Date updatedAt;
}
