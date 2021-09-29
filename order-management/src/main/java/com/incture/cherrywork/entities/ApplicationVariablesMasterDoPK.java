package com.incture.cherrywork.entities;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data

public class ApplicationVariablesMasterDoPK implements Serializable{

	private static final long serialVersionUID = 1L;
	@Column(name = "APPLICATION_NAME", columnDefinition = "NVARCHAR(100)")
	private String applicationName;
	@Column(name = "ENTITY_NAME", columnDefinition = "NVARCHAR(100)")
	private String entity;
	@Column(name = "PROCESS", columnDefinition = "NVARCHAR(100)")
	private String process;
	@Column(name = "VARIABLE", columnDefinition = "NVARCHAR(100)")
	private String variable;
	

}
