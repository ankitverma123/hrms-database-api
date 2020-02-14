package com.exalink.hrmsdatabaseapi.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.array.StringArrayType;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ankitkverma
 *
 */

@TypeDefs({
    @TypeDef(
        name = "string-array",
        typeClass = StringArrayType.class
    )
})

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator",
		parameters = {
			@Parameter(
				name = "uuid_gen_strategy_class",
				value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
			)
		}
	)
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createdAt")
	@JsonIgnore
    @CreatedDate
	private Date createdAt = new Date();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updatedAt")
	@JsonIgnore
    @CreatedDate
	private Date updatedAt = new Date();
	
}
