package com.incture.cherrywork.entities.new_workflow;



import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.incture.cherrywork.entities.BaseDo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SO_ITEM_STATUS")
public @Data class SalesOrderItemStatusDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ITEM_STATUS_SERIAL_ID", length = 100, nullable = false)
	private String itemStatusSerialId = UUID.randomUUID().toString();

	@JsonBackReference("item_status-task")
	@ManyToOne
	@JoinColumn(name = "TASK_STATUS_SERIAL_ID", nullable = false, referencedColumnName = "TASK_STATUS_SERIAL_ID")
	private SalesOrderTaskStatusDo salesOrderTaskStatus;

	@Column(name = "SO_ITEM_NUM", length = 20)
	private String salesOrderItemNum;

	@Column(name = "ITEM_STATUS", length = 20)
	private Integer itemStatus;

	@Column(name = "VISIBLITY", length = 20)
	private Integer visiblity;

	@Override
	public Object getPrimaryKey() {
		return itemStatusSerialId;
	}

	public String getItemStatusSerialId() {
		return itemStatusSerialId;
	}

	public void setItemStatusSerialId(String itemStatusSerialId) {
		this.itemStatusSerialId = itemStatusSerialId;
	}

	public SalesOrderTaskStatusDo getSalesOrderTaskStatus() {
		return salesOrderTaskStatus;
	}

	public void setSalesOrderTaskStatus(SalesOrderTaskStatusDo salesOrderTaskStatus) {
		this.salesOrderTaskStatus = salesOrderTaskStatus;
	}

	public String getSalesOrderItemNum() {
		return salesOrderItemNum;
	}

	public void setSalesOrderItemNum(String salesOrderItemNum) {
		this.salesOrderItemNum = salesOrderItemNum;
	}

	public Integer getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}

	public Integer getVisiblity() {
		return visiblity;
	}

	public void setVisiblity(Integer visiblity) {
		this.visiblity = visiblity;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "SalesOrderItemStatusDo [itemStatusSerialId=" + itemStatusSerialId + ", salesOrderTaskStatus="
				+ salesOrderTaskStatus + ", salesOrderItemNum=" + salesOrderItemNum + ", itemStatus=" + itemStatus
				+ ", visiblity=" + visiblity + "]";
	}

}