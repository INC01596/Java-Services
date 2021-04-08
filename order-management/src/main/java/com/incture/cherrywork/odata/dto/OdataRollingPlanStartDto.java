package com.incture.cherrywork.odata.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OdataRollingPlanStartDto {

	@Expose
	@SerializedName("d")
	private OdataRollingPlanListDto d;

	public OdataRollingPlanListDto getD() {
		return d;
	}

	public void setD(OdataRollingPlanListDto d) {
		this.d = d;
	}

	@Override
	public String toString() {
		return "RollingPlanStartDto [d=" + d + "]";
	}
}
