package com.incture.cherrywork.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OutBoundDelivery")
public class OutBoundDelivery {
	
	
	public String getSoNumberSeries() {
		return soNumberSeries;
	}

	public void setSoNumberSeries(String soNumberSeries) {
		this.soNumberSeries = soNumberSeries;
	}

	public String getPgiNumber() {
		return pgiNumber;
	}

	public void setPgiNumber(String pgiNumber) {
		this.pgiNumber = pgiNumber;
	}

	@Id
	@Column(name = "SoNumberSeries")
	private String soNumberSeries = UUID.randomUUID().toString();
	
	@Column(name = "ObdNumber")
	private String obdNumber;
	
	@Column(name = "SoNumber")
	private String soNumber; //Vbeln 
	
	@Column(name = "SoItemNumber")
	 private String  soItemNumber;//Kunag
	 
	@Column(name = "DeliveryQuantity")
	private String  deliveryQuantity; //Btgew
	
	@Column(name = "ItemUnit")
	private String itemUnit; // Lgnum
	
	@Column(name = "Terner")
	private String terner;
	
	@Column(name = "DocumentStatus")
	private String documenStatus;
	
	@Column(name = "ResonseMessage")
	private String responseMessage;
	
	@Column(name = "PgiNumber")
	private String pgiNumber;
	
	
	
	
	

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
