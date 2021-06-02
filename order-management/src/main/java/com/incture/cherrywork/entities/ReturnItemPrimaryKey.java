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
public @Data class ReturnItemPrimaryKey implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "RETURN_REQUEST_ITEM_ID", nullable = false, length = 20)
	private String returnReqItemid;

	@JsonBackReference("task-Request_Item")
	@ManyToOne
	@JoinColumn(name = "RETURN_REQ_NUM", nullable = false, referencedColumnName = "RETURN_REQ_NUM")
	private ReturnRequestHeader returnReqHeaderDo;

	public String getReturnReqItemid() {
		return returnReqItemid;
	}

	public void setReturnReqItemid(String returnReqItemid) {
		this.returnReqItemid = returnReqItemid;
	}

	public ReturnRequestHeader getReturnReqHeaderDo() {
		return returnReqHeaderDo;
	}

	public void setReturnReqHeaderDo(ReturnRequestHeader returnReqHeaderDo) {
		this.returnReqHeaderDo = returnReqHeaderDo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReturnItemPrimaryKey(String returnReqItemid,ReturnRequestHeader returnRequestHeaderDo) {
		super();
		this.returnReqItemid = returnReqItemid;
		this.returnReqHeaderDo = returnRequestHeaderDo;
	}

}

