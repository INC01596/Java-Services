package com.incture.cherrywork.entities.workflow;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
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

	@Column(name = "USER_GROUP", length = 20)
	private String userGroup;

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

	@Override
	public String toString() {
		return "SalesOrderTaskStatusPrimaryKey [requestId=" + requestId + ", decisionSetId=" + decisionSetId
				+ ", level=" + level + ", userGroup=" + userGroup + "]";
	}

}

