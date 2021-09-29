package com.incture.cherrywork.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class EmailDefinitionProcessMappingDoPk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "EMAIL_DEFINITION_ID", columnDefinition = "NVARCHAR(36)")
	private String emailDefinitionId;

	@Column(name = "PROCESS", columnDefinition = "NVARCHAR(100)")
	private String process;
}
