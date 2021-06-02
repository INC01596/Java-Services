package com.incture.cherrywork.dtos;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ODataBatchItem {
	private String hgLvItem;
	private String itemDelBlk;
	private String refDoc;
	private String salesDocument;
	private String salesUnit;
	private String refDocIt;
	private String reqQty;
	private String unitPrice;
	private String itmNumber;
	//private String refDocCat;
	private String material;
	private String plant;
	//private String targetQty;
	private String itemText;
	private String batch;
	private String storeLoc;
	private String serialNum;
	private String pricingDate;
	private String serviceRenderedDate;
	private String paymentTerms;
	private String conditionGroup4;
	public String getHgLvItem() {
		return hgLvItem;
	}
	public void setHgLvItem(String hgLvItem) {
		this.hgLvItem = hgLvItem;
	}
	public String getItemDelBlk() {
		return itemDelBlk;
	}
	public void setItemDelBlk(String itemDelBlk) {
		this.itemDelBlk = itemDelBlk;
	}
	public String getRefDoc() {
		return refDoc;
	}
	public void setRefDoc(String refDoc) {
		this.refDoc = refDoc;
	}
	public String getSalesDocument() {
		return salesDocument;
	}
	public void setSalesDocument(String salesDocument) {
		this.salesDocument = salesDocument;
	}
	public String getSalesUnit() {
		return salesUnit;
	}
	public void setSalesUnit(String salesUnit) {
		this.salesUnit = salesUnit;
	}
	public String getRefDocIt() {
		return refDocIt;
	}
	public void setRefDocIt(String refDocIt) {
		this.refDocIt = refDocIt;
	}
	public String getReqQty() {
		return reqQty;
	}
	public void setReqQty(String reqQty) {
		this.reqQty = reqQty;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getItmNumber() {
		return itmNumber;
	}
	public void setItmNumber(String itmNumber) {
		this.itmNumber = itmNumber;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public String getItemText() {
		return itemText;
	}
	public void setItemText(String itemText) {
		this.itemText = itemText;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getStoreLoc() {
		return storeLoc;
	}
	public void setStoreLoc(String storeLoc) {
		this.storeLoc = storeLoc;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getPricingDate() {
		return pricingDate;
	}
	public void setPricingDate(String pricingDate) {
		this.pricingDate = pricingDate;
	}
	public String getServiceRenderedDate() {
		return serviceRenderedDate;
	}
	public void setServiceRenderedDate(String serviceRenderedDate) {
		this.serviceRenderedDate = serviceRenderedDate;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public String getConditionGroup4() {
		return conditionGroup4;
	}
	public void setConditionGroup4(String conditionGroup4) {
		this.conditionGroup4 = conditionGroup4;
	}
	
	
	
	
}
