package com.incture.cherrywork.odata.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OdataMaterialStartNewDto {
	@Expose
	@SerializedName("d")
	private OdataMaterialListNewDto d;

	public OdataMaterialListNewDto getD() {
		return d;
	}

	public void setD(OdataMaterialListNewDto d) {
		this.d = d;
	}

	@Override
	public String toString() {
		return "OdataMaterialStartDto [d=" + d + "]";
	}
}
