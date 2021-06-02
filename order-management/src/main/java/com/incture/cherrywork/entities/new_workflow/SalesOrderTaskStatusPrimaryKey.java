package com.incture.cherrywork.entities.new_workflow;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
public @Data class SalesOrderTaskStatusPrimaryKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "REQUEST_ID", length = 100)
	private String requestId;

	@Column(name = "DECISION_SET_ID", length = 100)
	private String decisionSetId;

	@Column(name = "LEVEL", length = 20)
	private String level;

	

	public SalesOrderTaskStatusPrimaryKey(String requestId, String decisionSetId, String level, String userGroup) {
		super();
		this.requestId = requestId;
		this.decisionSetId = decisionSetId;
		this.level = level;
		this.userGroup = userGroup;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getDecisionSetId() {
		return decisionSetId;
	}

	public void setDecisionSetId(String decisionSetId) {
		this.decisionSetId = decisionSetId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "USER_GROUP", length = 20)
	private String userGroup;

}

