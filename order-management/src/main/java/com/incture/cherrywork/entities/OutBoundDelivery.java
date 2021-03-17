package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OutBoundDelivery")
public class OutBoundDelivery {
	
	@Id
	@Column(name = "obdNumber")
	private String obdNumber;
	
	@Column(name = "soNumber")
	private String soNumber; //Vbeln 
	
	@Column(name = "soItemNumber")
	 private String  soItemNumber;//Kunag
	 
	@Column(name = "deliveryQuantity")
	private String  deliveryQuantity; //Btgew
	
	@Column(name = "itemUnit")
	private String itemUnit; // Lgnum
	
	@Column(name = "terner")
	private String terner;
	
	@Column(name = "documentStatus")
	private String documenStatus;
	
	@Column(name = "resonseMessage")
	private String responseMessage;
	
	public String getTerner() {
		return terner;
	}

	public void setTerner(String terner) {
		this.terner = terner;
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
