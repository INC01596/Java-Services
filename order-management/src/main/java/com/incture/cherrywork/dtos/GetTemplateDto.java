package com.incture.cherrywork.dtos;

import lombok.Getter;
import lombok.Setter;


public class GetTemplateDto {
   String emailDefinitionId;
   String application;
   String process;
   String entityName;
   Integer pageIndex;
   Integer limit;
   String searchString;
public String getEmailDefinitionId() {
	return emailDefinitionId;
}
public void setEmailDefinitionId(String emailDefinitionId) {
	this.emailDefinitionId = emailDefinitionId;
}
public String getApplication() {
	return application;
}
public void setApplication(String application) {
	this.application = application;
}
public String getProcess() {
	return process;
}
public void setProcess(String process) {
	this.process = process;
}
public String getEntityName() {
	return entityName;
}
public void setEntityName(String entityName) {
	this.entityName = entityName;
}
public Integer getPageIndex() {
	return pageIndex;
}
public void setPageIndex(Integer pageIndex) {
	this.pageIndex = pageIndex;
}
public Integer getLimit() {
	return limit;
}
public void setLimit(Integer limit) {
	this.limit = limit;
}
public String getSearchString() {
	return searchString;
}
public void setSearchString(String searchString) {
	this.searchString = searchString;
}
   
   
}
