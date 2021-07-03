package com.incture.cherrywork.dtos;

//<------------------------------Sandeep Kumar------------------------------------>

public class HeaderIdDto {

	private String salesHeaderId;
	private String s4DocumentId;
	public String getSalesHeaderId() {
		return salesHeaderId;
	}
	public void setSalesHeaderId(String salesHeaderId) {
		this.salesHeaderId = salesHeaderId;
	}
	public String getS4DocumentId() {
		return s4DocumentId;
	}
	public void setS4DocumentId(String s4DocumentId) {
		this.s4DocumentId = s4DocumentId;
	}
	@Override
	public String toString() {
		return "HeaderIdDto [salesHeaderId=" + salesHeaderId + ", s4DocumentId=" + s4DocumentId + "]";
	}
	

}
