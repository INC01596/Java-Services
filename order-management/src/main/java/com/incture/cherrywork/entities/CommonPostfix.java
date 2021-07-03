package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@MappedSuperclass
@JsonIgnoreProperties({"createdAt", "updatedBy", "updatedAt"})
public abstract class CommonPostfix {
	
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_AT")
	private long createdAt;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATED_AT")
	private long updatedAt;
}
