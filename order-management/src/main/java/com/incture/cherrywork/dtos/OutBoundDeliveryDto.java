package com.incture.cherrywork.dtos;

import java.util.List;

public class OutBoundDeliveryDto {
	
	private String obdNumber;
	private String soNumber; //Vbeln 
	private String deliveryDate;
	private String shippingPoint;
	private String netAmount;
	private String ternr;// to check if ODB or PGI or INVOICE
	private List<OutBoundDeliveryItemDto> outboundDeliveryItemDto;
	private String documentStatus;
	private String responseMessage;
	private String pgiNumber;
	
	
	
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getShippingPoint() {
		return shippingPoint;
	}
	public void setShippingPoint(String shippingPoint) {
		this.shippingPoint = shippingPoint;
	}
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
	public String getObdNumber() {
		return obdNumber;
	}
	public void setObdNumber(String obdNumber) {
		this.obdNumber = obdNumber;
	}
	public List<OutBoundDeliveryItemDto> getOutboundDeliveryItemDto() {
		return outboundDeliveryItemDto;
	}
	public void setOutboundDeliveryItemDto(List<OutBoundDeliveryItemDto> outboundDeliveryItemDto) {
		this.outboundDeliveryItemDto = outboundDeliveryItemDto;
	}
	public String getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}
	public String getDocumentStatus() {
		return documentStatus;
	}
	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getPgiNumber() {
		return pgiNumber;
	}
	public void setPgiNumber(String pgiNumber) {
		this.pgiNumber = pgiNumber;
	}


}
