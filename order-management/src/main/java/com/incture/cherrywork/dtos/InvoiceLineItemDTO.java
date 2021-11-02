package com.incture.cherrywork.dtos;




public class InvoiceLineItemDTO {
	private String custId;
	private String accountingDocument;
	private String documentType;
	private String billingDocumentNo;
	private String currency;
	private String amountInLocalCurrency;
	private String amount;
	private String pendingAmount;
	private String postingDate;
	private String documentDate;
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getAccountingDocument() {
		return accountingDocument;
	}
	public void setAccountingDocument(String accountingDocument) {
		this.accountingDocument = accountingDocument;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getBillingDocumentNo() {
		return billingDocumentNo;
	}
	public void setBillingDocumentNo(String billingDocumentNo) {
		this.billingDocumentNo = billingDocumentNo;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
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
	public String getPendingAmount() {
		return pendingAmount;
	}
	public void setPendingAmount(String pendingAmount) {
		this.pendingAmount = pendingAmount;
	}
	public String getPostingDate() {
		return postingDate;
	}
	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}
	public String getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(String documentDate) {
		this.documentDate = documentDate;
	}
	@Override
	public String toString() {
		return "InvoiceLineItemDTO [custId=" + custId + ", accountingDocument=" + accountingDocument + ", documentType="
				+ documentType + ", billingDocumentNo=" + billingDocumentNo + ", currency=" + currency
				+ ", amountInLocalCurrency=" + amountInLocalCurrency + ", amount=" + amount + ", pendingAmount="
				+ pendingAmount + ", postingDate=" + postingDate + ", documentDate=" + documentDate + "]";
	}
}
