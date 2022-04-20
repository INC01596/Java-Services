package com.incture.cherrywork.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Embeddable
@Data
public class CustomerContactPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "customerContactId")
	private String customerContactId;

	@ToString.Exclude
	@JsonBackReference("customer-contact")
	@ManyToOne
	@JoinColumn(name = "visit_Id", nullable = false, referencedColumnName = "visit_Id")
	private SalesVisit salesVisit;

	public CustomerContactPk(String id, SalesVisit visit) {
		this.customerContactId = id;
		this.salesVisit = visit;
	}

}
