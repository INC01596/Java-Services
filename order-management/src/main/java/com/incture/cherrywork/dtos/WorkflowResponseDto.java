package com.incture.cherrywork.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowResponseDto {
	
	
private String id;
private String definitionId;
private String definitionVersion;
private String startedAt;
private String startedBy;
private String completedAt;
private String status;
private String businessKey;
private String subject;

public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getDefinitionId() {
	return definitionId;
}
public void setDefinitionId(String definitionId) {
	this.definitionId = definitionId;
}
public String getDefinitionVersion() {
	return definitionVersion;
}
public void setDefinitionVersion(String definitionVersion) {
	this.definitionVersion = definitionVersion;
}
public String getStartedAt() {
	return startedAt;
}
public void setStartedAt(String startedAt) {
	this.startedAt = startedAt;
}
public String getStartedBy() {
	return startedBy;
}
public void setStartedBy(String startedBy) {
	this.startedBy = startedBy;
}
public String getCompletedAt() {
	return completedAt;
}
public void setCompletedAt(String completedAt) {
	this.completedAt = completedAt;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getBusinessKey() {
	return businessKey;
}
public void setBusinessKey(String businessKey) {
	this.businessKey = businessKey;
}
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}

}