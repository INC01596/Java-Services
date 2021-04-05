package com.incture.cherrywork.odata.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class OdataRollingPlanListDto {

	@Expose
	@SerializedName("results")
	private List<OdataRollingPlanDto> results;

	public List<OdataRollingPlanDto> getResults() {
		return results;
	}

	public void setResults(List<OdataRollingPlanDto> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "RollingPlanListDto [results=" + results + "]";
	}
}
