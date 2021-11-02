package com.incture.cherrywork.odata.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OdataCustomerStartDto {
	
	@Expose
	@SerializedName("d")
	private OdataCustomerListDto d;

	public OdataCustomerListDto getD() {
		return d;
	}

	public void setD(OdataCustomerListDto d) {
		this.d = d;
	}

	@Override
	public String toString() {
		return "OdataCustomerStartDto [d=" + d + "]";
	}
	
}
