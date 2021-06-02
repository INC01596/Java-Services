package com.incture.cherrywork.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public @Data class ExchangeItemPk implements Serializable {

	private static final long serialVersionUID = 3875994822103092792L;
	private String exchangeReqNum;
	private String exchangeReqItemid;
	private String returnReqNum;
	
	
	
	

}
