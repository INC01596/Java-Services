package com.incture.cherrywork.odata.dto;



import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OdataSchHeaderListDto {

	@Expose
	@SerializedName("results")
	private List<OdataSchHeaderDto> results;

	public List<OdataSchHeaderDto> getResults() {
		return results;
	}

	public void setResults(List<OdataSchHeaderDto> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "OdataSchHeaderListDto [results=" + results + "]";
	}
}

