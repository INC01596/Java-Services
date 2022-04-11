package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "CustomerStock")
public class CustomerStock {

	@Id
	@Column(name = "customerStockId")
	private String customerStockId;

	@Column(name = "custCode")
	private String custCode;

	@Column(name = "custDesc")
	private String custDesc;

	@Column(name = "location")
	private String location;

	@Column(name = "InitialStock")
	private String initialStock;

	@Column(name = "currentStock")
	private String currentStock;
}
