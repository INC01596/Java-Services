package com.incture.cherrywork.odata.dto;



import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OdataMaterialListDto {

	@Expose
	@SerializedName("results")
	private List<OdataMaterialDto> results;

	public List<OdataMaterialDto> getResults() {
		return results;
	}

	public void setResults(List<OdataMaterialDto> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "OdataMaterialListDto [results=" + results + "]";
	}
}

