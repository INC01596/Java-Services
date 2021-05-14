package com.incture.cherrywork.Odat.Dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@EqualsAndHashCode(callSuper = false)
public class SalesOrderItemOdataSaveDto {
	
	public static final long serialVersionUID = 1L;

	private String docNumber;
	private String itmNumber;
	private String material;
	private String batch;
	private String createdBy;
	
	//added extra for edit/save service as per shared by 
	private String reqQty;
	private String salesUnit;
	private String netPrice;
	private String stgeLoc;
	public String getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
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
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getReqQty() {
		return reqQty;
	}
	public void setReqQty(String reqQty) {
		this.reqQty = reqQty;
	}
	public String getSalesUnit() {
		return salesUnit;
	}
	public void setSalesUnit(String salesUnit) {
		this.salesUnit = salesUnit;
	}
	public String getNetPrice() {
		return netPrice;
	}
	public void setNetPrice(String netPrice) {
		this.netPrice = netPrice;
	}
	public String getStgeLoc() {
		return stgeLoc;
	}
	public void setStgeLoc(String stgeLoc) {
		this.stgeLoc = stgeLoc;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
