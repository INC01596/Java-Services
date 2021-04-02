package com.incture.cherrywork.dtos;

import java.util.List;

public class SalesOrderDropDownDto {

	private List<SalesOrderLookUpDto> paymentTerms;
	private List<SalesOrderLookUpDto> incoTerms;
	private List<SalesOrderLookUpDto> qualityTest;
	private List<SalesOrderLookUpDto> deliveryTolerance;
	private List<SalesOrderLookUpDto> distributionChannel;

	public List<SalesOrderLookUpDto> getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(List<SalesOrderLookUpDto> paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public List<SalesOrderLookUpDto> getIncoTerms() {
		return incoTerms;
	}

	public void setIncoTerms(List<SalesOrderLookUpDto> incoTerms) {
		this.incoTerms = incoTerms;
	}

	public List<SalesOrderLookUpDto> getQualityTest() {
		return qualityTest;
	}

	public void setQualityTest(List<SalesOrderLookUpDto> qualityTest) {
		this.qualityTest = qualityTest;
	}

	public List<SalesOrderLookUpDto> getDeliveryTolerance() {
		return deliveryTolerance;
	}

	public void setDeliveryTolerance(List<SalesOrderLookUpDto> deliveryTolerance) {
		this.deliveryTolerance = deliveryTolerance;
	}

	public List<SalesOrderLookUpDto> getDistributionChannel() {
		return distributionChannel;
	}

	public void setDistributionChannel(List<SalesOrderLookUpDto> distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	@Override
	public String toString() {
		return "DropDownDto [paymentTerms=" + paymentTerms + ", incoTerms=" + incoTerms + ", qualityTest=" + qualityTest
				+ ", deliveryTolerance=" + deliveryTolerance + ", distributionChannel=" + distributionChannel + "]";
	}

}
