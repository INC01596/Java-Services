package com.incture.cherrywork.workflow.entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.incture.cherrywork.entities.BaseDo;
import com.incture.cherrywork.entities.new_workflow.SalesOrderItemStatusDo;
import com.incture.cherrywork.entities.new_workflow.SalesOrderLevelStatusDo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SO_TASK_STATUS")
public @Data class SalesOrderTaskStatusDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TASK_STATUS_SERIAL_ID", length = 100, nullable = false)
	private String taskStatusSerialId = UUID.randomUUID().toString();

	@JsonBackReference("task_status-task")
	@ManyToOne
	@JoinColumn(name = "LEVEL_STATUS_SERIAL_ID", nullable = false, referencedColumnName = "LEVEL_STATUS_SERIAL_ID")
	private SalesOrderLevelStatusDo salesOrderLevelStatus;

	@JsonManagedReference("item_status-task")
	@OneToMany(mappedBy = "salesOrderTaskStatus", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // mandatory
	private List<SalesOrderItemStatusDo> itemStatusList;

	@Column(name = "TASK_ID", length = 36)
	private String taskId;

	@Column(name = "TASK_STATUS")
	private Integer taskStatus;

	@Column(name = "APPROVER")
	private String approver;

	@Column(name = "COMPLETED_BY")
	private String completedBy;

	@Override
	public Object getPrimaryKey() {
		return taskStatusSerialId;
	}

	public String getTaskStatusSerialId() {
		return taskStatusSerialId;
	}

	public void setTaskStatusSerialId(String taskStatusSerialId) {
		this.taskStatusSerialId = taskStatusSerialId;
	}

	public SalesOrderLevelStatusDo getSalesOrderLevelStatus() {
		return salesOrderLevelStatus;
	}

	public void setSalesOrderLevelStatus(SalesOrderLevelStatusDo salesOrderLevelStatus) {
		this.salesOrderLevelStatus = salesOrderLevelStatus;
	}

	public List<SalesOrderItemStatusDo> getItemStatusList() {
		return itemStatusList;
	}

	public void setItemStatusList(List<SalesOrderItemStatusDo> itemStatusList) {
		this.itemStatusList = itemStatusList;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Integer getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getCompletedBy() {
		return completedBy;
	}

	public void setCompletedBy(String completedBy) {
		this.completedBy = completedBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "SalesOrderTaskStatusDo [taskStatusSerialId=" + taskStatusSerialId + ", salesOrderLevelStatus="
				+ salesOrderLevelStatus + ", itemStatusList=" + itemStatusList + ", taskId=" + taskId + ", taskStatus="
				+ taskStatus + ", approver=" + approver + ", completedBy=" + completedBy + "]";
	}

}
