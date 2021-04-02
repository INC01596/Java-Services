package com.incture.cherrywork.dtos;

public class SalesOrderStandardListDto {
	
	private String standard;
	private String standardDescription;
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getStandardDescription() {
		return standardDescription;
	}
	public void setStandardDescription(String standardDescription) {
		this.standardDescription = standardDescription;
	}
	
	@Override
	public String toString() {
		return "SalesOrderStandardListDto [standard=" + standard + ", standardDescription=" + standardDescription + "]";
	}

}
