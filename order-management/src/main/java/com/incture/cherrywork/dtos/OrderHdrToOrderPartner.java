package com.incture.cherrywork.dtos;

import java.util.List;

import lombok.Data;

@Data
public class OrderHdrToOrderPartner {
	
	List<OrderHdrToOrderPartnerDto> results;
	
	public OrderHdrToOrderPartner(List<OrderHdrToOrderPartnerDto> orderHdrToOrderPartnerDtoList) {
		super();
		this.results = orderHdrToOrderPartnerDtoList;
	}

	public OrderHdrToOrderPartner() {
		super();
	}

}
