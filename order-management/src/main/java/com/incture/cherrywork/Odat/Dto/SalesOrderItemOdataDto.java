package com.incture.cherrywork.Odat.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = false)
public @Data class SalesOrderItemOdataDto {

	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;

	private String docNumber;
	private String itmNumber;
	private String reaForRe;
	private String material;
	private String createdBy;
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
	public String getReaForRe() {
		return reaForRe;
	}
	public void setReaForRe(String reaForRe) {
		this.reaForRe = reaForRe;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
