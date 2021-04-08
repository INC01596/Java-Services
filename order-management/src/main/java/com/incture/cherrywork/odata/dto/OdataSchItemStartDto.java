
package com.incture.cherrywork.odata.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OdataSchItemStartDto {

	@Expose
	@SerializedName("d")
	private OdataSchItemListDto d;

	public OdataSchItemListDto getD() {
		return d;
	}

	public void setD(OdataSchItemListDto d) {
		this.d = d;
	}

	@Override
	public String toString() {
		return "OdataSchItemStartDto [d=" + d + "]";
	}
}
