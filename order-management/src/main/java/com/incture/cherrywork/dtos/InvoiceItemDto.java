package com.incture.cherrywork.dtos;



import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public class InvoiceItemDto {

	private String invoiceNumber;
	private String itemNumber;
	private String matID;
	private String description;
	private BigDecimal highItem;
	private BigDecimal billedQty;
	private String salesUnit;
	private BigDecimal netPrice;
	private BigDecimal taxAmount;
	private String salesDoc;
	private String itemCateg;
	private String freegoodInd;
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getMatID() {
		return matID;
	}
	public void setMatID(String matID) {
		this.matID = matID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getHighItem() {
		return highItem;
	}
	public void setHighItem(BigDecimal highItem) {
		this.highItem = highItem;
	}
	public BigDecimal getBilledQty() {
		return billedQty;
	}
	public void setBilledQty(BigDecimal billedQty) {
		this.billedQty = billedQty;
	}
	public String getSalesUnit() {
		return salesUnit;
	}
	public void setSalesUnit(String salesUnit) {
		this.salesUnit = salesUnit;
	}
	public BigDecimal getNetPrice() {
		return netPrice;
	}
	public void setNetPrice(BigDecimal netPrice) {
		this.netPrice = netPrice;
	}
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getSalesDoc() {
		return salesDoc;
	}
	public void setSalesDoc(String salesDoc) {
		this.salesDoc = salesDoc;
	}
	public String getItemCateg() {
		return itemCateg;
	}
	public void setItemCateg(String itemCateg) {
		this.itemCateg = itemCateg;
	}
	public String getFreegoodInd() {
		return freegoodInd;
	}
	public void setFreegoodInd(String freegoodInd) {
		this.freegoodInd = freegoodInd;
	}
	
}

