package com.incture.cherrywork.dtos;
//<----------Sandeep Kumar---------------------------------->

import java.sql.Date;
import java.util.List;

import com.incture.cherrywork.sales.constants.EnOrderActionStatus;

public class HeaderDetailUIDto {

	private String salesHeaderId;
	private String documentType;
	private EnOrderActionStatus documentProcessStatus;
	private String createdBy;
	private Date createdDateFrom;
	private Date createdDateTo;
	private Boolean isOpen;
	private List<String> stpId;
	private String customer;
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}



	private Boolean isCustomer;
	private List<String> plant;
	private Date requestDeliveryDateFrom;
	private Date requestDeliveryDateTo;
	
	public String getSalesHeaderId() {
		return salesHeaderId;
	}
	public void setSalesHeaderId(String salesHeaderId) {
		this.salesHeaderId = salesHeaderId;
	}
	
	
	
	public int pageNo;
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public Date getCreatedDateFrom() {
		return createdDateFrom;
	}
	public void setCreatedDateFrom(Date createdDateFrom) {
		this.createdDateFrom = createdDateFrom;
	}
	public Date getCreatedDateTo() {
		return createdDateTo;
	}
	public void setCreatedDateTo(Date createdDateTo) {
		this.createdDateTo = createdDateTo;
	}
	public Date getRequestDeliveryDateFrom() {
		return requestDeliveryDateFrom;
	}
	public void setRequestDeliveryDateFrom(Date requestDeliveryDateFrom) {
		this.requestDeliveryDateFrom = requestDeliveryDateFrom;
	}
	public Date getRequestDeliveryDateTo() {
		return requestDeliveryDateTo;
	}
	public void setRequestDeliveryDateTo(Date requestDeliveryDateTo) {
		this.requestDeliveryDateTo = requestDeliveryDateTo;
	}
	public List<String> getPlant() {
		return plant;
	}
	public void setPlant(List<String> plant) {
		this.plant = plant;
	}
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
	
	public String getSalesGroup() {
		return salesGroup;
	}
	public void setSalesGroup(String salesGroup) {
		this.salesGroup = salesGroup;
	}
	@Override
	public String toString() {
		return "HeaderDetailUIDto [salesHeaderId=" + salesHeaderId + ", documentType=" + documentType
				+ ", documentProcessStatus=" + documentProcessStatus + ", createdBy=" + createdBy + ", createdDateFrom="
				+ createdDateFrom + ", createdDateTo=" + createdDateTo + ", isOpen=" + isOpen + ", stpId=" + stpId
				+ ", customer=" + customer + ", isCustomer=" + isCustomer + ", plant=" + plant
				+ ", requestDeliveryDateFrom=" + requestDeliveryDateFrom + ", requestDeliveryDateTo="
				+ requestDeliveryDateTo + ", pageNo=" + pageNo + ", salesGroup=" + salesGroup + "]";
	}
	
}

	

