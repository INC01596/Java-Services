package com.incture.cherrywork.workflow.entities;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.incture.cherrywork.entities.BaseDo;
import com.incture.cherrywork.entities.new_workflow.SalesOrderTaskStatusPrimaryKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SALES_ORDER_TASK_STATUS")
public @Data class SalesOrderTaskStatusesDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private SalesOrderTaskStatusPrimaryKey key;

	@Column(name = "TASK_STATUS", length = 20)
	private String taskStatus;

	@Column(name = "SALES_ORDER_NUM", length = 100)
	private String salesOrderNum;

	@Override
	public Object getPrimaryKey() {
		return key;
	}

	public SalesOrderTaskStatusPrimaryKey getKey() {
		return key;
	}

	public void setKey(SalesOrderTaskStatusPrimaryKey key) {
		this.key = key;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getSalesOrderNum() {
		return salesOrderNum;
	}

	public void setSalesOrderNum(String salesOrderNum) {
		this.salesOrderNum = salesOrderNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

