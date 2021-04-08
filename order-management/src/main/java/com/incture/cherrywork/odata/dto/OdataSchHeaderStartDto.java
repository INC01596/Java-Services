package com.incture.cherrywork.odata.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OdataSchHeaderStartDto {


	@Expose
	@SerializedName("d")
	private OdataSchHeaderListDto d;

	public OdataSchHeaderListDto getD() {
		return d;
	}

	public void setD(OdataSchHeaderListDto d) {
		this.d = d;
	}

	@Override
	public String toString() {
		return "OdataSchHeaderStartDto [d=" + d + "]";
}
}
