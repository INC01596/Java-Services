package com.incture.cherrywork.dtos;

import java.util.List;

import lombok.Data;

@Data
public class EccResponseOutputDto {
	
	private String statusCode;
	
	private String message ;
	
	
	private List<ReturnOrderResponsePojo> returnResponsePojo;
	

}
