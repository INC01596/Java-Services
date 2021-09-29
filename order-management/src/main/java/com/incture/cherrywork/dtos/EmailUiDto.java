package com.incture.cherrywork.dtos;


import java.util.Date;
import java.util.List;


import lombok.Data;

@Data
public class EmailUiDto {
	private String emailDefinitionId;
	private String name;
	private String application;
	private String applicationDesc;

	private String process;
	private String processDesc;

	private String entity;
	private String entityDesc;

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
	private String attachment;
	private List<String> toList;
	private List<String> bccList;
	private List<String> ccList;
	private List<EmailDefinitionProcessMappingDto> processList;

}
