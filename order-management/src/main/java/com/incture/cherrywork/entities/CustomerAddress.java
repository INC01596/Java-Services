package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@Entity
@Table(name = "CustomerAddress")
public class CustomerAddress {

	@Id
	@Column(name = "customerAddressId")
	private String customerAddressId;

	@Getter(value = AccessLevel.NONE)
	@JsonIgnore
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "visit_Id", nullable = false, referencedColumnName = "visit_Id")
	private SalesVisit salesVisit;

	// @Id
	// private CustomerAddressPk customerAddressKey;

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

}
