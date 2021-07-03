package com.incture.cherrywork.dtos;

import java.util.Date;
import java.util.List;

import com.incture.cherrywork.sales.constants.EnOrderActionStatus;
<<<<<<< HEAD
=======


>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233

public class InvoDto {

	private int pageNo;
	private String documentType;
	private String invId;

	private EnOrderActionStatus documentProcessStatus;
	private String createdBy;
	private String salesHeaderId;
	private String obdId;

	private String invoiceStatus;
<<<<<<< HEAD

	private List<String> stpId;
	private Date createdDateFrom;
	private Date createdDateTo;
	private String shipToParty;
	public int getPageNo() {
		return pageNo;
=======
	
	public String getObdId() {
		return obdId;
>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233
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
	public String getInvId() {
		return invId;
	}
	public void setInvId(String invId) {
		this.invId = invId;
	}
<<<<<<< HEAD
=======





	private List<String> stpId;
	private Date createdDateFrom;
	private Date createdDateTo;
	private String shipToParty;


	

>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233
	public EnOrderActionStatus getDocumentProcessStatus() {
		return documentProcessStatus;
	}
	public void setDocumentProcessStatus(EnOrderActionStatus documentProcessStatus) {
		this.documentProcessStatus = documentProcessStatus;
<<<<<<< HEAD
=======
	}
	
	
	
	public String getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;

>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
<<<<<<< HEAD
=======

	public List<String> getStpId() {
		return stpId;
	}

	public void setStpId(List<String> stpId) {
		this.stpId = stpId;
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

>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233
	public String getSalesHeaderId() {
		return salesHeaderId;
	}
	public void setSalesHeaderId(String salesHeaderId) {
		this.salesHeaderId = salesHeaderId;
	}
<<<<<<< HEAD
	public String getObdId() {
		return obdId;
	}
	public void setObdId(String obdId) {
		this.obdId = obdId;
	}
	public String getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	public List<String> getStpId() {
		return stpId;
	}
	public void setStpId(List<String> stpId) {
		this.stpId = stpId;
	}
=======


	


>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233
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
<<<<<<< HEAD
	public String getShipToParty() {
		return shipToParty;
	}
	public void setShipToParty(String shipToParty) {
		this.shipToParty = shipToParty;
	}
	@Override
	public String toString() {
		return "InvoDto [pageNo=" + pageNo + ", documentType=" + documentType + ", invId=" + invId
				+ ", documentProcessStatus=" + documentProcessStatus + ", createdBy=" + createdBy + ", salesHeaderId="
				+ salesHeaderId + ", obdId=" + obdId + ", invoiceStatus=" + invoiceStatus + ", stpId=" + stpId
				+ ", createdDateFrom=" + createdDateFrom + ", createdDateTo=" + createdDateTo + ", shipToParty="
				+ shipToParty + "]";
=======


	

	


	@Override
	public String toString() {
		return "InvoDto [pageNo=" + pageNo + ", documentType=" + documentType + ", invId=" + invId + ", invoiceStatus="
				+ invoiceStatus + ", createdBy=" + createdBy + ", salesHeaderId=" + salesHeaderId + ", obdId=" + obdId
				+ ", stpId=" + stpId + ", createdDateFrom=" + createdDateFrom + ", createdDateTo=" + createdDateTo
				+ ", shipToParty=" + shipToParty + "]";
>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233
	}
	
	
<<<<<<< HEAD

	

}
=======
	}

>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233
