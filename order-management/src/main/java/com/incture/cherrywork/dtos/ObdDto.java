package com.incture.cherrywork.dtos;

import java.util.Date;
import java.util.List;

import com.incture.cherrywork.sales.constants.EnOrderActionStatus;

public class ObdDto {

	private int pageNo;
	private String documentType;
<<<<<<< HEAD
	// private EnOrderActionStatus documentProcessStatus;
=======
	private String obdStatus;
>>>>>>> 7d779a97118c12d1811378be9f7c83fdeaf836f0
	private String salesHeaderId;
	private String obdId;
	private List<String> stpId;
	private Date createdDateFrom;
	private Date createdDateTo;
	private String shipToParty;
<<<<<<< HEAD
	private String obdStatus;
	private String pgiStatus;
    private List<String>invoiceStatus ;

	public List<String> getStpId() {
		return stpId;
	}

=======
	private String pgiStatus;
	private List<String>invoiceStatus ;
	
	
	
	public String getPgiStatus() {
		return pgiStatus;
	}
	public void setPgiStatus(String pgiStatus) {
		this.pgiStatus = pgiStatus;
	}
	public List<String> getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(List<String> invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	public List<String> getStpId() {
		return stpId;
	}
	public String getObdStatus() {
		return obdStatus;
	}
	public void setObdStatus(String obdStatus) {
		this.obdStatus = obdStatus;
	}
>>>>>>> 7d779a97118c12d1811378be9f7c83fdeaf836f0
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
<<<<<<< HEAD

=======
	
>>>>>>> 7d779a97118c12d1811378be9f7c83fdeaf836f0
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

=======
	
	public String getObdId() {
		return obdId;
	}
>>>>>>> 7d779a97118c12d1811378be9f7c83fdeaf836f0
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
<<<<<<< HEAD

	public String getObdStatus() {
		return obdStatus;
	}

	public void setObdStatus(String obdStatus) {
		this.obdStatus = obdStatus;
=======
	@Override
	public String toString() {
		return "ObdDto [pageNo=" + pageNo + ", documentType=" + documentType + ", obdStatus=" + obdStatus
				+ ", salesHeaderId=" + salesHeaderId + ", obdId=" + obdId + ", stpId=" + stpId + ", createdDateFrom="
				+ createdDateFrom + ", createdDateTo=" + createdDateTo + ", shipToParty=" + shipToParty + "]";
>>>>>>> 7d779a97118c12d1811378be9f7c83fdeaf836f0
	}
	
	

	public String getPgiStatus() {
		return pgiStatus;
	}

	public void setPgiStatus(String pgiStatus) {
		this.pgiStatus = pgiStatus;
	}

	public List<String> getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(List<String> invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	@Override
	public String toString() {
		return "ObdDto [pageNo=" + pageNo + ", documentType=" + documentType + ", salesHeaderId=" + salesHeaderId
				+ ", obdId=" + obdId + ", stpId=" + stpId + ", createdDateFrom=" + createdDateFrom + ", createdDateTo="
				+ createdDateTo + ", shipToParty=" + shipToParty + ", obdStatus=" + obdStatus + "]";
	}

}
