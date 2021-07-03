package com.incture.cherrywork.entities.new_workflow;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.incture.cherrywork.entities.BaseDo;
import com.incture.cherrywork.workflow.entities.SalesOrderTaskStatusDo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SO_LEVEL_STATUS")
public @Data class SalesOrderLevelStatusDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "LEVEL_STATUS_SERIAL_ID", length = 100, nullable = false)
	private String levelStatusSerialId = UUID.randomUUID().toString();

	@JsonManagedReference("task_status-task")
	@OneToMany(mappedBy = "salesOrderLevelStatus", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // mandatory
	private List<SalesOrderTaskStatusDo> taskStatusList;

	@Column(name = "DECISION_SET_ID", length = 20)
	private String decisionSetId;

	@Column(name = "LEVEL", length = 20)
	private String level;

	@Column(name = "USER_GROUP")
	private String userGroup;

	@Column(name = "APPROVER_TYPE", length = 4)
	private String approverType;

	@Column(name = "LEVEL_STATUS")
	private Integer levelStatus;

	@Override
	public Object getPrimaryKey() {
		return levelStatusSerialId;
	}

	public String getLevelStatusSerialId() {
		return levelStatusSerialId;
	}

	public void setLevelStatusSerialId(String levelStatusSerialId) {
		this.levelStatusSerialId = levelStatusSerialId;
	}

	public List<SalesOrderTaskStatusDo> getTaskStatusList() {
		return taskStatusList;
	}

	public void setTaskStatusList(List<SalesOrderTaskStatusDo> taskStatusList) {
		this.taskStatusList = taskStatusList;
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

	public String getApproverType() {
		return approverType;
	}

	public void setApproverType(String approverType) {
		this.approverType = approverType;
	}

	public Integer getLevelStatus() {
		return levelStatus;
	}

	public void setLevelStatus(Integer levelStatus) {
		this.levelStatus = levelStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
