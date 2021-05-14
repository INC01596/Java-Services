package com.incture.cherrywork.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable

public @Data class ExchangeHeaderPrimaryKey implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Column(name = "EXCHANGE_REQ_NUM", length = 20)
	private String exchangeReqNum;

	@JsonManagedReference("task-exchangeHeader")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "RETURN_REQ_NUM", nullable = false, referencedColumnName = "RETURN_REQ_NUM")
	private ReturnRequestHeaderDo returnRequestHeaderDo;

	public ExchangeHeaderPrimaryKey(String exchangeReqNum, ReturnRequestHeaderDo returnRequestHeaderDo) {
		super();
		this.exchangeReqNum = exchangeReqNum;
		this.returnRequestHeaderDo = returnRequestHeaderDo;
	}

	public String getExchangeReqNum() {
		return exchangeReqNum;
	}

	public void setExchangeReqNum(String exchangeReqNum) {
		this.exchangeReqNum = exchangeReqNum;
	}

	public ReturnRequestHeaderDo getReturnRequestHeaderDo() {
		return returnRequestHeaderDo;
	}

	public void setReturnRequestHeaderDo(ReturnRequestHeaderDo returnRequestHeaderDo) {
		this.returnRequestHeaderDo = returnRequestHeaderDo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
