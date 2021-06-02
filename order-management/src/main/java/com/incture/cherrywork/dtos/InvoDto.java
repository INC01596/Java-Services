package com.incture.cherrywork.dtos;

import java.util.Date;
import java.util.List;

import com.incture.cherrywork.sales.constants.EnOrderActionStatus;

public class InvoDto {

	private int pageNo;
	private String documentType;
	private String invId;
	private EnOrderActionStatus documentProcessStatus;
	private String createdBy;
	private String salesHeaderId;
	private String obdId;
	private List<String> stpId;
	private Date createdDateFrom;
	private Date createdDateTo;
	private String shipToParty;

	private String invoiceStatus;

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

	public List<String> getStpId() {
		return stpId;
	}

	public void setStpId(List<String> stpId) {
		this.stpId = stpId;
	}

	public String getInvId() {
		return invId;
	}

	public void setInvId(String invId) {
		this.invId = invId;
	}

	public String getShipToParty() {
		return shipToParty;
	}

	public void setShipToParty(String shipToParty) {
		this.shipToParty = shipToParty;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getSalesHeaderId() {
		return salesHeaderId;
	}

	public void setSalesHeaderId(String salesHeaderId) {
		this.salesHeaderId = salesHeaderId;
	}

	public String getObdId() {
		return obdId;
	}

	public void setObdId(String obdId) {
		this.obdId = obdId;
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
	

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	@Override
	public String toString() {
		return "InvoDto [pageNo=" + pageNo + ", documentType=" + documentType + ", InvoiceNo=" + invId
				+ ", documentProcessStatus=" + documentProcessStatus + ", createdBy=" + createdBy + ", salesHeaderId="
				+ salesHeaderId + ", DeliveryNo=" + obdId + ", stpId=" + stpId + ", createdDateFrom=" + createdDateFrom
				+ ", createdDateTo=" + createdDateTo + ", shipToParty=" + shipToParty +
				"]";
	}

}
