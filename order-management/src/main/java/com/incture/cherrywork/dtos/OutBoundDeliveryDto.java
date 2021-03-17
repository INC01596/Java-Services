package com.incture.cherrywork.dtos;

import javax.persistence.Column;

public class OutBoundDeliveryDto {
	
	private String obdNumber;
	private String soNumber; //Vbeln 
	 private String  soItemNumber;//Kunag
	private String  deliveryQuantity; //Btgew
	private String itemUnit; // Lgnum
	private String ternr;// to check if ODB or PGI or INVOICE
	private String documenStatus;
	private String responseMessage;
	
	public String getTernr() {
		return ternr;
	}
	public void setTernr(String ternr) {
		this.ternr = ternr;
	}
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
	public String getDocumenStatus() {
		return documenStatus;
	}
	public void setDocumenStatus(String documenStatus) {
		this.documenStatus = documenStatus;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getObdNumber() {
		return obdNumber;
	}
	public void setObdNumber(String obdNumber) {
		this.obdNumber = obdNumber;
	}


}
