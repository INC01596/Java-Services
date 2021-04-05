package com.incture.cherrywork.odata.dto;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OdataSchItemListDto {

	@Expose
	@SerializedName("results")
	private List<OdataSchItemDto> results;

	public List<OdataSchItemDto> getResults() {
		return results;
	}

	public void setResults(List<OdataSchItemDto> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "OdataSchItemListDto [results=" + results + "]";
	}
}
