package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name="CustomerContact")
public class CustomerContact {
	
	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="custName")
	private String custName;
	
	@Column(name="custPhone")
	private String custPhone;
	
	@Column(name="customerAddress")
	private String customerAddress;
	
	@Column(name="custEmail")
	private String custEmail;

}
