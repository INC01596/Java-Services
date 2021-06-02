package com.incture.cherrywork.dtos;

import lombok.Data;

@Data
public class OdataBatchOnsubmitItem {

	private String docNumber;
	private String createdBy;
	private String reaForRe;
	// private String targetQty;
	private String itmNumber;
	// private String targetQu;
	// private String baseUom;
	private String material;
	// private String batch;
	// private String netValue;
	// private String currency;
	// private String matlGroup;
	// private String reqQty;
	// private String shortText;
	// private String itemCateg;
	// private String salesUnit;
	// private String itemType;
	// private String hgLvItem;
	private String itemText;
	private String approve_Rej;
	public String getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getReaForRe() {
		return reaForRe;
	}
	public void setReaForRe(String reaForRe) {
		this.reaForRe = reaForRe;
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
	public String getItemText() {
		return itemText;
	}
	public void setItemText(String itemText) {
		this.itemText = itemText;
	}
	public String getApprove_Rej() {
		return approve_Rej;
	}
	public void setApprove_Rej(String approve_Rej) {
		this.approve_Rej = approve_Rej;
	}
	
}

