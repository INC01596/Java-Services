package com.incture.cherrywork.dtos;
//<----------Sandeep Kumar---------------------------------->

import java.util.List;

import com.incture.cherrywork.sales.constants.EnOrderActionStatus;

public class HeaderDetailUIDto {

	
	private String documentType;
	private EnOrderActionStatus documentProcessStatus;
	private String createdBy;
	private Boolean isOpen;
	private List<String> stpId;
	private Boolean isCustomer;
	private List<String> plant;
	private String salesGroup;
	
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	public EnOrderActionStatus getDocumentProcessStatus() {
		return documentProcessStatus;
	}
	public void setDocumentProcessStatus(EnOrderActionStatus documentProcessStatus) {
		this.documentProcessStatus = documentProcessStatus;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Boolean getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}
	public List<String> getStpId() {
		return stpId;
	}
	public void setStpId(List<String> stpId) {
		this.stpId = stpId;
	}
	public Boolean getIsCustomer() {
		return isCustomer;
	}
	public void setIsCustomer(Boolean isCustomer) {
		this.isCustomer = isCustomer;
	}
	public List<String> getPlant() {
		return plant;
	}
	public void setPlant(List<String> plant) {
		this.plant = plant;
	}
	public String getSalesGroup() {
		return salesGroup;
	}
	public void setSalesGroup(String salesGroup) {
		this.salesGroup = salesGroup;
	}
	@Override
	public String toString() {
		return "HeaderDetailUIDto [documentType=" + documentType + ", documentProcessStatus=" + documentProcessStatus
				+ ", createdBy=" + createdBy + ", isOpen=" + isOpen + ", stpId=" + stpId + ", isCustomer=" + isCustomer
				+ ", plant=" + plant + ", salesGroup=" + salesGroup + "]";
	}
}

	

