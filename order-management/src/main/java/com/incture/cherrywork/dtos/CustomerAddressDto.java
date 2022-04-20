package com.incture.cherrywork.dtos;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.incture.cherrywork.entities.SalesVisit;

import lombok.Data;

@Data
public class CustomerAddressDto {

	private String customerAddressId;

	private SalesVisit salesVisit;

	private String custCode;

	private String custDesc;

	private String country;

	private String street1;

	private String houseNumber;

	private String district;

	private String postalCode;

	private String city;

	private String region;

	private String street2;

}
