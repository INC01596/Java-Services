package com.incture.cherrywork.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Embeddable
@Data
public class CustomerAddressPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "customerAddressId")
	private String customerAddressId;

	@JsonBackReference("address")
	@ManyToOne
	@JoinColumn(name = "visit_Id", nullable = false, referencedColumnName = "visit_Id")
	private SalesVisit salesVisit;

	public CustomerAddressPk(String id, SalesVisit visit) {
		this.customerAddressId = id;
		this.salesVisit = visit;
	}

}
