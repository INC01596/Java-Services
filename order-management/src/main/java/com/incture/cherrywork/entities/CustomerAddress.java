package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.ToString;

@Data
@Entity(name = "CustomerAddress")
public class CustomerAddress {

	@Id
	@Column(name = "id")
	private String customerAddressId;

	@Column(name = "custCode", length = 2)
	private String custCode;

	@Column(name = "custDesc")
	private String custDesc;

	@Column(name = "country")
	private String country;

	@Column(name = "street1")
	private String street1;

	@Column(name = "houseNumber")
	private String houseNumber;

	@Column(name = "district")
	private String district;

	@Column(name = "postalCode")
	private String postalCode;

	@Column(name = "city")
	private String city;

	@Column(name = "region")
	private String region;

	@Column(name = "street2")
	private String street2;

	@ToString.Exclude
	@JsonBackReference("sales-visit-address")
	@ManyToOne
	@JoinColumn(name = "visitId", nullable = false, referencedColumnName = "visitId")
	private SalesVisit salesVisit;

}
