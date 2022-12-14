package com.incture.cherrywork.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.incture.cherrywork.sales.constants.EnOrderActionStatus;

import lombok.Data;

@Entity
@Table(name = "SalesOrderHeader")
@Data
public class SalesOrderHeader {

	@Column(name = "ClientSpecific", precision = 3, scale = 0)
	private Integer clientSpecific;

	@Id
	@Column(name = "S4DocumentId", length = 50)
	private String s4DocumentId;

	@Column(name = "DocumentCategory", length = 5)
	private String documentCategory;

	@Column(name = "DocumentType", length = 5)
	private String documentType;

	@Column(name = "SalesOrg", length = 10)
	private String salesOrg;

	@Column(name = "DistributionChannel", length = 10)
	private String distributionChannel;

	@Column(name = "Division", length = 10)
	private String division;

	@Column(name = "SalesOffice", length = 10)
	private String salesOffice;

	@Column(name = "SalesGroup", length = 10)
	private String salesGroup;

	@Column(name = "SoldToParty", length = 10)
	private String soldToParty;

	@Column(name = "ShipToParty", length = 10)
	private String shipToParty;

	@Column(name = "CustomerPoNum", length = 10)
	private String customerPoNum;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "CustomerPoDate")
	private Date customerPoDate;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "RequestDeliveryDate")
	private Date requestDeliveryDate;

	@Column(name = "ShippingType", length = 5)
	private String shippingType;

	@Column(name = "TotalSoQuantity", precision = 13, scale = 2)
	private BigDecimal totalSoQuantity;

	@Column(name = "NetValue")
	private String netValue;

	@Column(name = "DeliveredQuantity", precision = 13, scale = 2)
	private BigDecimal deliveredQuantity;

	@Column(name = "OutstandingQuantity", length = 15)
	private String outstandingQuantity;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
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

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "LastUpdatedOn")
	private Date lastUpdatedOn;

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
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

	@Column(name = "SALES_HEADER_ID", length = 255)
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

	// sandeep
	@Column(name = "DOCUMENT_PROCESS_STATUS")
	private EnOrderActionStatus documentProcessStatus;

	public EnOrderActionStatus getDocumentProcessStatus() {
		return documentProcessStatus;
	}

	public void setDocumentProcessStatus(EnOrderActionStatus documentProcessStatus) {
		this.documentProcessStatus = documentProcessStatus;
	}

	@Column(name = "OVER_DELIVERY_TOLERANCE", length = 50)
	private String overDeliveryTolerance;

	public String getOverDeliveryTolerance() {
		return overDeliveryTolerance;
	}

	public void setOverDeliveryTolerance(String overDeliveryTolerance) {
		this.overDeliveryTolerance = overDeliveryTolerance;
	}

	@Column(name = "UNDER_DELIVERY_TOLERANCE", length = 50)
	private String underDeliveryTolerance;

	public String getUnderDeliveryTolerance() {
		return underDeliveryTolerance;
	}

	public void setUnderDeliveryTolerance(String underDeliveryTolerance) {
		this.underDeliveryTolerance = underDeliveryTolerance;
	}

	@Column(name = "EMAIL_ID", length = 100)
	private String emailId;

	@Column(name = "INCO_TERMS1", length = 20)
	private String incoTerms1;

	@Column(name = "INCO_TERMS2", length = 70)
	private String incoTerms2;

	public String getIncoTerms1() {
		return incoTerms1;
	}

	public void setIncoTerms1(String incoTerms1) {
		this.incoTerms1 = incoTerms1;
	}

	public String getIncoTerms2() {
		return incoTerms2;
	}

	public void setIncoTerms2(String incoTerms2) {
		this.incoTerms2 = incoTerms2;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	@Column(name = "PAYMENT_TERMS", length = 20)
	private String paymentTerms;

	@Column(name = "WEIGHT", length = 2)
	private String weight;

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	@Column(name = "WORKFLOW_ID", length = 10) // check the data type in future
	private String workflowID;

	public String getWorkflowID() {
		return workflowID;
	}

	public void setWorkflowID(String workflowID) {
		this.workflowID = workflowID;
	}

	@Column(name = "CUSTOMER_NAME")
	private String customerName;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "REFERENCE_DOCUMENT")
	private String referenceDocument;

	public String getReferenceDocument() {
		return referenceDocument;
	}

	public void setReferenceDocument(String referenceDocument) {
		this.referenceDocument = referenceDocument;

	}

	@Column(name = "BLOCKED")
	private Boolean blocked;

	public Boolean getBlocked() {
		return blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	@Column(name = "OBD_STATUS")
	private String obdStatus;

	@Column(name = "PGI_STATUS")
	private String pgiStatus;

	@Column(name = "INVOICE_STATUS")
	private String invoiceStatus;

	public String getObdStatus() {
		return obdStatus;
	}

	public void setObdStatus(String obdStatus) {
		this.obdStatus = obdStatus;
	}

	public String getPgiStatus() {
		return pgiStatus;
	}

	public void setPgiStatus(String pgiStatus) {
		this.pgiStatus = pgiStatus;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	@Column(name = "OUT_BOUND_ORDER_ID", length = 30)
	private String obdId;

	public String getObdId() {
		return obdId;
	}

	public void setObdId(String obdId) {
		this.obdId = obdId;
	}

	@Column(name = "PGI_ID", length = 30)
	private String pgiId;

	public String getPgiId() {
		return pgiId;
	}

	public void setPgiId(String pgiId) {
		this.pgiId = pgiId;
	}

	@Column(name = "INV_ID", length = 30)
	private String invId;

	public String getInvId() {
		return invId;
	}

	public void setInvId(String invId) {
		this.invId = invId;
	}

	@Column(name = "AMOUNT")
	private String amount;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "SalesOrderHeader [clientSpecific=" + clientSpecific + ", s4DocumentId=" + s4DocumentId
				+ ", documentCategory=" + documentCategory + ", documentType=" + documentType + ", salesOrg=" + salesOrg
				+ ", distributionChannel=" + distributionChannel + ", division=" + division + ", salesOffice="
				+ salesOffice + ", salesGroup=" + salesGroup + ", soldToParty=" + soldToParty + ", shipToParty="
				+ shipToParty + ", customerPoNum=" + customerPoNum + ", customerPoDate=" + customerPoDate
				+ ", requestDeliveryDate=" + requestDeliveryDate + ", shippingType=" + shippingType
				+ ", totalSoQuantity=" + totalSoQuantity + ", netValue=" + netValue + ", deliveredQuantity="
				+ deliveredQuantity + ", outstandingQuantity=" + outstandingQuantity + ", createdDate=" + createdDate
				+ ", paymentChqDetail=" + paymentChqDetail + ", overallDocumentStatus=" + overallDocumentStatus
				+ ", deliveryStatus=" + deliveryStatus + ", deliveryTolerance=" + deliveryTolerance + ", colorCoding="
				+ colorCoding + ", comments=" + comments + ", bankName=" + bankName + ", projectName=" + projectName
				+ ", poTypeField=" + poTypeField + ", pieceGuarantee=" + pieceGuarantee + ", acknowledgementStatus="
				+ acknowledgementStatus + ", updateIndicator=" + updateIndicator + ", lastUpdatedOn=" + lastUpdatedOn
				+ ", syncStatus=" + syncStatus + ", createdBy=" + createdBy + ", createdOn=" + createdOn
				+ ", lastChangedBy=" + lastChangedBy + ", lastChangedOn=" + lastChangedOn + ", salesOrderItemList="
				+ salesOrderItemList + ", postingDate=" + postingDate + ", postingError=" + postingError
				+ ", postedFrom=" + postedFrom + ", postingStatus=" + postingStatus + ", salesHeaderId=" + salesHeaderId
				+ ", plant=" + plant + ", documentProcessStatus=" + documentProcessStatus + ", overDeliveryTolerance="
				+ overDeliveryTolerance + ", underDeliveryTolerance=" + underDeliveryTolerance + ", emailId=" + emailId
				+ ", incoTerms1=" + incoTerms1 + ", incoTerms2=" + incoTerms2 + ", paymentTerms=" + paymentTerms
				+ ", weight=" + weight + ", workflowID=" + workflowID + ", customerName=" + customerName
				+ ", referenceDocument=" + referenceDocument + ", blocked=" + blocked + ", obdStatus=" + obdStatus
				+ ", pgiStatus=" + pgiStatus + ", invoiceStatus=" + invoiceStatus + ", obdId=" + obdId + ", pgiId="
				+ pgiId + ", invId=" + invId + ", amount=" + amount + "]";
	}

}
