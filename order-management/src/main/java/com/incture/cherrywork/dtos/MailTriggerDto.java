package com.incture.cherrywork.dtos;


import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailTriggerDto {
	private String application;
	private String process;
	private String entityName;
	private String emailDefinitionId;
	private String templateName;
	private String subject;
	private String emailBody;
	private String attachment;
	private List<String>toList;
	private List<String>ccList;
	private List<String>bccList;
	private Map<String,Object>subjectVariables;
	private Map<String,Object>contentVariables;

	


}
