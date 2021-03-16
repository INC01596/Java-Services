package com.incture.cherrywork.dtos;



public class OutBoundDeliveryDto {
	
	
	private String soNumber; //Vbeln 
	 private String  soItemNumber;//Kunag
	private String  deliveryQuantity; //Btgew
	private String itemUnit; // Lgnum
	
	
	public String getSoNumber() {
		return soNumber;
	}
	public void setSoNumber(String soNumber) {
		this.soNumber = soNumber;
	}
	public String getSoItemNumber() {
		return soItemNumber;
	}
	public void setSoItemNumber(String soItemNumber) {
		this.soItemNumber = soItemNumber;
	}
	public String getDeliveryQuantity() {
		return deliveryQuantity;
	}
	public void setDeliveryQuantity(String deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}
	public String getItemUnit() {
		return itemUnit;
	}
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}


}
