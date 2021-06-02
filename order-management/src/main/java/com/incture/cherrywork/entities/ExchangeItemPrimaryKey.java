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
public @Data class ExchangeItemPrimaryKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "EXCHANGE_REQ_ITEM_ID", nullable = false, length = 20)
	private String exchangeReqItemid;

	@JsonBackReference("task-Exchange_Item")
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "EXCHANGE_REQ_NUM", nullable = false, referencedColumnName = "EXCHANGE_REQ_NUM"),
			@JoinColumn(name = "RETURN_REQ_NUM", nullable = false, referencedColumnName = "RETURN_REQ_NUM") })
	private ExchangeHeaderDo exchangeHeaderDo;

	public String getExchangeReqItemid() {
		return exchangeReqItemid;
	}

	public void setExchangeReqItemid(String exchangeReqItemid) {
		this.exchangeReqItemid = exchangeReqItemid;
	}

	public ExchangeHeaderDo getExchangeHeaderDo() {
		return exchangeHeaderDo;
	}

	public void setExchangeHeaderDo(ExchangeHeaderDo exchangeHeaderDo) {
		this.exchangeHeaderDo = exchangeHeaderDo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ExchangeItemPrimaryKey(String exchangeReqItemid, ExchangeHeaderDo exchangeHeaderDo) {
		super();
		this.exchangeReqItemid = exchangeReqItemid;
		this.exchangeHeaderDo = exchangeHeaderDo;
	}
	
}
