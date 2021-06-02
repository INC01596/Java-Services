package com.incture.cherrywork.dtos;

import java.util.List;

import lombok.Data;

@Data
public class OrderHdrToOrderCondition {
	List<OrderConditionDto> results;


	public OrderHdrToOrderCondition(List<OrderConditionDto> orderConditionList) {
		super();
		this.results = orderConditionList;
	}

	public OrderHdrToOrderCondition() {
		super();
	}
}
