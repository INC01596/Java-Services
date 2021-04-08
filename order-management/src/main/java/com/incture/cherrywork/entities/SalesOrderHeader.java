package com.incture.cherrywork.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.incture.cherrywork.sales.constants.EnOrderActionStatus;

import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.Id;

@Entity
@Table(name = "SalesOrderHeader")
public class SalesOrderHeader {

	@Column(name = "ClientSpecific", precision = 3, scale = 0)
	private Integer clientSpecific;

	public Integer getClientSpecific() {
		return clientSpecific;
	}

	public void setClientSpecific(Integer clientSpecific) {
		this.clientSpecific = clientSpecific;
	}

	@Id
	@Column(name = "S4DocumentId", length = 10)
	private String s4DocumentId;

	public String getS4DocumentId() {
		return s4DocumentId;
	}

	public void setS4DocumentId(String s4DocumentId) {
		this.s4DocumentId = s4DocumentId;
	}

	@Column(name = "DocumentCategory", length = 5)
	private String documentCategory;

	public String getDocumentCategory() {
		return documentCategory;
	}

	public void setDocumentCategory(String documentCategory) {
		this.documentCategory = documentCategory;
	}

	@Column(name = "DocumentType", length = 5)
	private String documentType;

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	@Column(name = "SalesOrg", length = 10)
	private String salesOrg;

	public String getSalesOrg() {
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

	@Column(name = "DistributionChannel", length = 10)
	private String distributionChannel;

	public String getDistributionChannel() {
		return distributionChannel;
	}

	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	@Column(name = "Division", length = 10)
	private String division;

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	@Column(name = "SalesOffice", length = 10)
	private String salesOffice;

	public String getSalesOffice() {
		return salesOffice;
	}

	public void setSalesOffice(String salesOffice) {
		this.salesOffice = salesOffice;
	}

	@Column(name = "SalesGroup", length = 10)
	private String salesGroup;

	public String getSalesGroup() {
		return salesGroup;
	}

	public void setSalesGroup(String salesGroup) {
		this.salesGroup = salesGroup;
	}

	@Column(name = "SoldToParty", length = 10)
	private String soldToParty;

	public String getSoldToParty() {
		return soldToParty;
	}

	public void setSoldToParty(String soldToParty) {
		this.soldToParty = soldToParty;
	}

	@Column(name = "ShipToParty", length = 10)
	private String shipToParty;

	public String getShipToParty() {
		return shipToParty;
	}

	public void setShipToParty(String shipToParty) {
		this.shipToParty = shipToParty;
	}

	@Column(name = "CustomerPoNum", length = 10)
	private String customerPoNum;

	public String getCustomerPoNum() {
		return customerPoNum;
	}

	public void setCustomerPoNum(String customerPoNum) {
		this.customerPoNum = customerPoNum;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CustomerPoDate")
	private Date customerPoDate;

	public Date getCustomerPoDate() {
		return customerPoDate;
	}

	public void setCustomerPoDate(Date customerPoDate) {
		this.customerPoDate = customerPoDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "RequestDeliveryDate")
	private Date requestDeliveryDate;

	public Date getRequestDeliveryDate() {
		return requestDeliveryDate;
	}

	public void setRequestDeliveryDate(Date requestDeliveryDate) {
		this.requestDeliveryDate = requestDeliveryDate;
	}

	@Column(name = "ShippingType", length = 5)
	private String shippingType;

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	@Column(name = "TotalSoQuantity", precision = 13, scale = 3)
	private BigDecimal totalSoQuantity;

	public BigDecimal getTotalSoQuantity() {
		return totalSoQuantity;
	}

	public void setTotalSoQuantity(BigDecimal totalSoQuantity) {
		this.totalSoQuantity = totalSoQuantity;
	}

	@Column(name = "NetValue", precision = 13, scale = 3)
	private BigDecimal netValue;

	public BigDecimal getNetValue() {
		return netValue;
	}

	public void setNetValue(BigDecimal netValue) {
		this.netValue = netValue;
	}

	@Column(name = "DeliveredQuantity", precision = 13, scale = 3)
	private BigDecimal deliveredQuantity;

	public BigDecimal getDeliveredQuantity() {
		return deliveredQuantity;
	}

	public void setDeliveredQuantity(BigDecimal deliveredQuantity) {
		this.deliveredQuantity = deliveredQuantity;
	}

	@Column(name = "OutstandingQuantity", length = 15)
	private String outstandingQuantity;

	public String getOutstandingQuantity() {
		return outstandingQuantity;
	}

	public void setOutstandingQuantity(String outstandingQuantity) {
		this.outstandingQuantity = outstandingQuantity;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CreatedDate")
	private Date createdDate;

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "PaymentChqDetail", length = 10)
	private String paymentChqDetail;

	public String getPaymentChqDetail() {
		return paymentChqDetail;
	}

	public void setPaymentChqDetail(String paymentChqDetail) {
		this.paymentChqDetail = paymentChqDetail;
	}

	@Column(name = "OverallDocumentStatus", length = 2)
	private String overallDocumentStatus;

	public String getOverallDocumentStatus() {
		return overallDocumentStatus;
	}

	public void setOverallDocumentStatus(String overallDocumentStatus) {
		this.overallDocumentStatus = overallDocumentStatus;
	}

	@Column(name = "DeliveryStatus", length = 2)
	private String deliveryStatus;

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	@Column(name = "DeliveryTolerance", length = 40)
	private String deliveryTolerance;

	public String getDeliveryTolerance() {
		return deliveryTolerance;
	}

	public void setDeliveryTolerance(String deliveryTolerance) {
		this.deliveryTolerance = deliveryTolerance;
	}

	@Column(name = "ColorCoding", length = 100)
	private String colorCoding;

	public String getColorCoding() {
		return colorCoding;
	}

	public void setColorCoding(String colorCoding) {
		this.colorCoding = colorCoding;
	}

	@Column(name = "Comments", length = 100)
	private String comments;

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name = "BankName", length = 80)
	private String bankName;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "ProjectName", length = 80)
	private String projectName;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "PoTypeField", length = 10)
	private String poTypeField;

	public String getPoTypeField() {
		return poTypeField;
	}

	public void setPoTypeField(String poTypeField) {
		this.poTypeField = poTypeField;
	}

	@Column(name = "PieceGuarantee")
	private Boolean pieceGuarantee;

	public Boolean getPieceGuarantee() {
		return pieceGuarantee;
	}

	public void setPieceGuarantee(Boolean pieceGuarantee) {
		this.pieceGuarantee = pieceGuarantee;
	}

	@Column(name = "AcknowledgementStatus")
	private Boolean acknowledgementStatus;

	public Boolean getAcknowledgementStatus() {
		return acknowledgementStatus;
	}

	public void setAcknowledgementStatus(Boolean acknowledgementStatus) {
		this.acknowledgementStatus = acknowledgementStatus;
	}

	@Column(name = "UpdateIndicator", length = 2)
	private String updateIndicator;

	public String getUpdateIndicator() {
		return updateIndicator;
	}

	public void setUpdateIndicator(String updateIndicator) {
		this.updateIndicator = updateIndicator;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LastUpdatedOn")
	private Date lastUpdatedOn;

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SyncStatus")
	private Date syncStatus;

	public Date getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Date syncStatus) {
		this.syncStatus = syncStatus;
	}

	@Column(name = "CREATED_BY", length = 200)
	private String createdBy;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_ON")
	private Date createdOn;

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "LAST_CHANGED_BY", length = 200)
	private String lastChangedBy;

	public String getLastChangedBy() {
		return lastChangedBy;
	}

	public void setLastChangedBy(String lastChangedBy) {
		this.lastChangedBy = lastChangedBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_CHANGED_ON")
	private Date lastChangedOn;

	public Date getLastChangedOn() {
		return lastChangedOn;
	}

	public void setLastChangedOn(Date lastChangedOn) {
		this.lastChangedOn = lastChangedOn;
	}

	@OneToMany(mappedBy = "salesOrderHeader", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<SalesOrderItem> salesOrderItemList = new ArrayList<>();

	public List<SalesOrderItem> getSalesOrderItemList() {
		return salesOrderItemList;
	}

	public void setSalesOrderItemList(List<SalesOrderItem> salesOrderItemList) {
		this.salesOrderItemList = salesOrderItemList;
		for (SalesOrderItem a : salesOrderItemList) {
			a.setSalesOrderHeader(this);
		}
	}
	
/*--------------------AWADHESH KUMAR------------------------------*/
	
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "POSTING_DATE", length = 8)
	private java.util.Date postingDate;

	@Column(name = "POSTING_ERROR", length = 500)
	private String postingError;

	@Column(name = "POSTED_FROM", length = 10)
	private String postedFrom;

	@Column(name = "POSTING_STATUS")
	private Boolean postingStatus;

	public java.util.Date getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(java.util.Date postingDate) {
		this.postingDate = postingDate;
	}

	public String getPostingError() {
		return postingError;
	}

	public void setPostingError(String postingError) {
		this.postingError = postingError;
	}

	public String getPostedFrom() {
		return postedFrom;
	}

	public void setPostedFrom(String postedFrom) {
		this.postedFrom = postedFrom;
	}

	public Boolean getPostingStatus() {
		return postingStatus;
	}

	public void setPostingStatus(Boolean postingStatus) {
		this.postingStatus = postingStatus;
	}
	
	@Column(name = "SALES_HEADER_ID")
	private String salesHeaderId;

	public String getSalesHeaderId() {
		return salesHeaderId;
	}

	public void setSalesHeaderId(String salesHeaderId) {
		this.salesHeaderId = salesHeaderId;
	}
	
	@Column(name = "Plant", length = 5)
	private String plant;

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	
	//sandeep
	@Column(name = "DOCUMENT_PROCESS_STATUS")
	private EnOrderActionStatus documentProcessStatus;

	public EnOrderActionStatus getDocumentProcessStatus() {
		return documentProcessStatus;
	}
	public void setDocumentProcessStatus(EnOrderActionStatus documentProcessStatus) {
		this.documentProcessStatus = documentProcessStatus;
	}

}