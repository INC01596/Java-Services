package com.incture.cherrywork.dtos;

import javax.persistence.Column;

import lombok.Data;

@Data
public class CustomerContactDto {

	
	private String id;
	
	private String custName;
	
	private String custPhone;
	
	private String customerAddress;
	
	private String custEmail;

}
