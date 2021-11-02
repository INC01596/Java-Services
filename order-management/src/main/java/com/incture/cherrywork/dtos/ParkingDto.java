package com.incture.cherrywork.dtos;

public class ParkingDto {
	private String custId;
	private String documentNo;
	private String amountInLocalCurrency;
	private String amount;
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getAmountInLocalCurrency() {
		return amountInLocalCurrency;
	}
	public void setAmountInLocalCurrency(String amountInLocalCurrency) {
		this.amountInLocalCurrency = amountInLocalCurrency;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
}