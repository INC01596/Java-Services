package com.incture.cherrywork.odata.dto;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OdataMaterialListNewDto {
	@Expose
	@SerializedName("results")
	private List<OdataMaterialNewDto> results;

	public List<OdataMaterialNewDto> getResults() {
		return results;
	}

	public void setResults(List<OdataMaterialNewDto> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "OdataMaterialListDto [results=" + results + "]";
	}
}
