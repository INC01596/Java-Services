package com.incture.cherrywork.dtos;

//<------------------------------Sandeep Kumar------------------------------------>

public class HeaderIdDto {

	private String salesHeaderId;
	private String s4DocumentId;
	
	public String getsalesHeaderId() {
		return salesHeaderId;
	}
	public void setsalesHeaderId(String salesHeaderId) {
		this.salesHeaderId = salesHeaderId;
	}
	public String gets4DocumentId() {
		return s4DocumentId;
	}
	public void sets4DocumentId(String s4DocumentId) {
		this.s4DocumentId = s4DocumentId;
	}
	
	@Override
	public String toString() {
		return "HeaderIdDto [salesHeaderId=" + salesHeaderId + ", s4DocumentId=" + s4DocumentId + "]";
	}
}


