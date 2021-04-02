package com.incture.cherrywork.dtos;

public class SalesOrderMaterialListDto {
	
	
	private String materialNumber;

	public String getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}

	@Override
	public String toString() {
		return "SalesOrderMaterialListDto [materialNumber=" + materialNumber + "]";
	}


}
