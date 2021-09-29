package com.incture.cherrywork.dtos;



import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmailDefinitionDto {

	private String emailDefinitionId;

	private String application;
	private String applicationDesc;

	private String process;

	private String processDesc;

	private String entity;
	private String entityDesc;

	private String name;

	private String subject;

	private String content;

	private String signature;

	private String fromAddress;

	private String status;

	private Boolean bccAllowed;

	private String createdBy;

	private Date createdOn;

	private String updatedBy;

	private Date updatedOn;

	private List<EmailDefinitionProcessMappingDto> processList;

}
