package com.incture.cherrywork.dtos;

import lombok.Data;

@Data
public class ReturnOrderRequestPojo {	
	private ReturnOrder returns;
	private ExchangeOrder exchange;
	public ReturnOrder getReturns() {
		return returns;
	}
	public void setReturns(ReturnOrder returns) {
		this.returns = returns;
	}
	public ExchangeOrder getExchange() {
		return exchange;
	}
	public void setExchange(ExchangeOrder exchange) {
		this.exchange = exchange;
	}		
	

}

