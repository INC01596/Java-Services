package com.incture.cherrywork.odata.dto;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OdataMaterialStartDto {

	@Expose
	@SerializedName("d")
	private OdataMaterialListDto d;

	public OdataMaterialListDto getD() {
		return d;
	}

	public void setD(OdataMaterialListDto d) {
		this.d = d;
	}

	@Override
	public String toString() {
		return "OdataMaterialStartDto [d=" + d + "]";
	}
}

