package com.incture.cherrywork.dtos;

import java.util.List;

import lombok.Data;

@Data
public class CreateReturnOrderMessage {
	
	String message;
	
	
	String returnReqNum;
	
	List<String> sapReturnOrderNum;
	
	List<String> sapExchangeOrderNum;

}
