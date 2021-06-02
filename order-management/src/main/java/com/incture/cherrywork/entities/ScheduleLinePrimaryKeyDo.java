package com.incture.cherrywork.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
public @Data class ScheduleLinePrimaryKeyDo implements Serializable {
	public ScheduleLinePrimaryKeyDo(String scheduleLineId, SalesDocItemDo salesDocItemDo) {
		// TODO Auto-generated constructor stub
	}

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Column(name = "SCHEDULE_LINE_ID", length = 100)
	private String scheduleLineNum;

	@JsonBackReference("task-soItem")
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "SALES_ORDER_NUM", nullable = false, referencedColumnName = "SALES_ORDER_NUM"),
			@JoinColumn(name = "SALES_ORDER_ITEM_NUM", nullable = false, referencedColumnName = "SALES_ORDER_ITEM_NUM") })
	private SalesDocItemDo soItem;

	public String getScheduleLineNum() {
		return scheduleLineNum;
	}

	public void setScheduleLineNum(String scheduleLineNum) {
		this.scheduleLineNum = scheduleLineNum;
	}

	public SalesDocItemDo getSoItem() {
		return soItem;
	}

	public void setSoItem(SalesDocItemDo soItem) {
		this.soItem = soItem;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
