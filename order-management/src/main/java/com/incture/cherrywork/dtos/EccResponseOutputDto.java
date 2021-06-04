package com.incture.cherrywork.dtos;

import java.util.List;

import lombok.Data;

@Data
public class EccResponseOutputDto {
	
	private String statusCode;
	
	private String message ;
	
	
	private List<ReturnOrderResponsePojo> returnResponsePojo;


	public String getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public List<ReturnOrderResponsePojo> getReturnResponsePojo() {
		return returnResponsePojo;
	}


	public void setReturnResponsePojo(List<ReturnOrderResponsePojo> returnResponsePojo) {
		this.returnResponsePojo = returnResponsePojo;
	}
	

}
