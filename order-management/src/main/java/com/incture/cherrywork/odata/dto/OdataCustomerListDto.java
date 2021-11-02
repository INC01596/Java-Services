package com.incture.cherrywork.odata.dto;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OdataCustomerListDto {
	@Expose
	@SerializedName("results")
	private List<ODataCustomerDto> results;

	public List<ODataCustomerDto> getResults() {
		return results;
	}

	public void setResults(List<ODataCustomerDto> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "OdataCustomerListDto [results=" + results + "]";
	}
	
	
}
