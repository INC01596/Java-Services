package com.incture.cherrywork.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderHdrToOrderItem {
	List<ODataBatchItem> results;


	public OrderHdrToOrderItem(List<ODataBatchItem> returnItemList) {
		super();
		this.results = returnItemList;
	}

	public OrderHdrToOrderItem() {
		super();
	}
}
