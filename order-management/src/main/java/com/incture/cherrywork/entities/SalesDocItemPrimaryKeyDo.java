package com.incture.cherrywork.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
public @Data class SalesDocItemPrimaryKeyDo implements Serializable {

	public SalesDocItemPrimaryKeyDo(String salesItemId, SalesDocHeaderDo salesDocHeader2) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "SALES_ORDER_ITEM_NUM", length = 100)
	private String salesItemOrderNo;

	@JsonBackReference("task_list-task")
	@ManyToOne
	@JoinColumn(name = "SALES_ORDER_NUM", nullable = false, referencedColumnName = "SALES_ORDER_NUM")
	private SalesDocHeaderDo salesDocHeader;

	public String getSalesItemOrderNo() {
		return salesItemOrderNo;
	}

	public void setSalesItemOrderNo(String salesItemOrderNo) {
		this.salesItemOrderNo = salesItemOrderNo;
	}

	public SalesDocHeaderDo getSalesDocHeader() {
		return salesDocHeader;
	}

	public void setSalesDocHeader(SalesDocHeaderDo salesDocHeader) {
		this.salesDocHeader = salesDocHeader;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}

