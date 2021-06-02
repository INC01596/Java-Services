package com.incture.cherrywork.dtos;

import java.util.List;

import com.incture.cherrywork.sales.constants.EnOrderActionStatus;
import com.incture.cherrywork.sales.constants.EnOverallDocumentStatus;
import com.incture.cherrywork.sales.constants.EnPaymentChequeStatus;
import com.incture.cherrywork.sales.constants.EnUpdateIndicator;

import java.util.ArrayList;

import java.util.Date;
import java.math.BigDecimal;

public class SalesOrderHeaderDto {

	private Integer clientSpecific;

	public Integer getClientSpecific() {
		return clientSpecific;
	}

	public void setClientSpecific(Integer clientSpecific) {
		this.clientSpecific = clientSpecific;
	}

	private String s4DocumentId;

	public String getS4DocumentId() {
		return s4DocumentId;
	}

	public void setS4DocumentId(String s4DocumentId) {
		this.s4DocumentId = s4DocumentId;
	}

	private String documentCategory;

	public String getDocumentCategory() {
		return documentCategory;
	}

	public void setDocumentCategory(String documentCategory) {
		this.documentCategory = documentCategory;
	}

	private String documentType;

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	private String salesOrg;

	public String getSalesOrg() {
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

	private String distributionChannel;

	public String getDistributionChannel() {
		return distributionChannel;
	}

	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	private String division;

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	private String salesOffice;

	public String getSalesOffice() {
		return salesOffice;
	}

	public void setSalesOffice(String salesOffice) {
		this.salesOffice = salesOffice;
	}

	private String salesGroup;

	public String getSalesGroup() {
		return salesGroup;
	}

	public void setSalesGroup(String salesGroup) {
		this.salesGroup = salesGroup;
	}

	private String soldToParty;

	public String getSoldToParty() {
		return soldToParty;
	}

	public void setSoldToParty(String soldToParty) {
		this.soldToParty = soldToParty;
	}

	private String shipToParty;

	public String getShipToParty() {
		return shipToParty;
	}

	public void setShipToParty(String shipToParty) {
		this.shipToParty = shipToParty;
	}

	private String customerPoNum;

	public String getCustomerPoNum() {
		return customerPoNum;
	}

	public void setCustomerPoNum(String customerPoNum) {
		this.customerPoNum = customerPoNum;
	}

	private Date customerPODate;

	public Date getCustomerPODate() {
		return customerPODate;
	}

	public void setCustomerPODate(Date customerPODate) {
		this.customerPODate = customerPODate;
	}

	private Date requestDeliveryDate;

	public Date getRequestDeliveryDate() {
		return requestDeliveryDate;
	}

	public void setRequestDeliveryDate(Date requestDeliveryDate) {
		this.requestDeliveryDate = requestDeliveryDate;
	}

	private String shippingType;

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	private BigDecimal totalSoQuantity;

	public BigDecimal getTotalSoQuantity() {
		return totalSoQuantity;
	}

	public void setTotalSoQuantity(BigDecimal totalSoQuantity) {
		this.totalSoQuantity = totalSoQuantity;
	}

	private String netValue;

	public String getNetValue() {
		return netValue;
	}

	public void setNetValue(String netValue) {
		this.netValue = netValue;
	}

	private BigDecimal deliveredQuantity;

	public BigDecimal getDeliveredQuantity() {
		return deliveredQuantity;
	}

	public void setDeliveredQuantity(BigDecimal deliveredQuantity) {
		this.deliveredQuantity = deliveredQuantity;
	}

	private String outstandingQuantity;

	public String getOutstandingQuantity() {
		return outstandingQuantity;
	}

	public void setOutstandingQuantity(String outstandingQuantity) {
		this.outstandingQuantity = outstandingQuantity;
	}

	private Date createdDate;

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	private String paymentChqDetail;

	public String getPaymentChqDetail() {
		return paymentChqDetail;
	}

	public void setPaymentChqDetail(String paymentChqDetail) {
		this.paymentChqDetail = paymentChqDetail;
	}

	private String overallDocumentStatus;

	public String getOverallDocumentStatus() {
		return overallDocumentStatus;
	}

	public void setOverallDocumentStatus(String overallDocumentStatus) {
		this.overallDocumentStatus = overallDocumentStatus;
	}

	private EnOverallDocumentStatus overallDocumentStatus1;
	
	
	
	public EnOverallDocumentStatus getOverallDocumentStatus1() {
		return overallDocumentStatus1;
	}

	public void setOverallDocumentStatus1(EnOverallDocumentStatus overallDocumentStatus1) {
		this.overallDocumentStatus1 = overallDocumentStatus1;
	}

	private String deliveryStatus;

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	private String deliveryTolerance;

	public String getDeliveryTolerance() {
		return deliveryTolerance;
	}

	public void setDeliveryTolerance(String deliveryTolerance) {
		this.deliveryTolerance = deliveryTolerance;
	}

	private String colorCoding;

	public String getColorCoding() {
		return colorCoding;
	}

	public void setColorCoding(String colorCoding) {
		this.colorCoding = colorCoding;
	}

	private String comments;

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	private String bankName;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	private String projectName;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	private String poTypeField;

	public String getPoTypeField() {
		return poTypeField;
	}

	public void setPoTypeField(String poTypeField) {
		this.poTypeField = poTypeField;
	}

	private Boolean pieceGuarantee;

	public Boolean getPieceGuarantee() {
		return pieceGuarantee;
	}

	public void setPieceGuarantee(Boolean pieceGuarantee) {
		this.pieceGuarantee = pieceGuarantee;
	}

	private Boolean acknowledgementStatus;

	public Boolean getAcknowledgementStatus() {
		return acknowledgementStatus;
	}

	public void setAcknowledgementStatus(Boolean acknowledgementStatus) {
		this.acknowledgementStatus = acknowledgementStatus;
	}

	private String updateIndicator;
	private EnUpdateIndicator updateIndicator1;
	

	public EnUpdateIndicator getUpdateIndicator1() {
		return updateIndicator1;
	}

	public void setUpdateIndicator1(EnUpdateIndicator updateIndicator1) {
		this.updateIndicator1 = updateIndicator1;
	}

	public String getUpdateIndicator() {
		return updateIndicator;
	}

	public void setUpdateIndicator(String updateIndicator) {
		this.updateIndicator = updateIndicator;
	}

	private Date lastUpdatedOn;

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	private Date syncStatus;

	public Date getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Date syncStatus) {
		this.syncStatus = syncStatus;
	}

	private String createdBy;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	private Date createdOn;

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	private String lastChangedBy;

	public String getLastChangedBy() {
		return lastChangedBy;
	}

	public void setLastChangedBy(String lastChangedBy) {
		this.lastChangedBy = lastChangedBy;
	}

	private Date lastChangedOn;

	public Date getLastChangedOn() {
		return lastChangedOn;
	}

	public void setLastChangedOn(Date lastChangedOn) {
		this.lastChangedOn = lastChangedOn;
	}

	private List<SalesOrderItemDto> salesOrderItemList = new ArrayList<SalesOrderItemDto>();

	public List<SalesOrderItemDto> getSalesOrderItemDtoList() {
		return salesOrderItemList;
	}

	public void setSalesOrderItemDtoList(List<SalesOrderItemDto> salesOrderItemList) {
		this.salesOrderItemList = salesOrderItemList;
	}

	// Awadhesh Kumar ------------------------------------------------------

	private String salesHeaderId;

	public String getSalesHeaderId() {
		return salesHeaderId;
	}

	public void setSalesHeaderId(String salesHeaderId) {
		this.salesHeaderId = salesHeaderId;
	}

	private String plant;

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	private String netValueSA;

	public String getNetValueSA() {
		return netValueSA;
	}

	public void setNetValueSA(String netValueSA) {
		this.netValueSA = netValueSA;
	}

	private BigDecimal totalSalesOrderQuantitySA;

	public BigDecimal getTotalSalesOrderQuantitySA() {
		return totalSalesOrderQuantitySA;
	}

	public void setTotalSalesOrderQuantitySA(BigDecimal totalSalesOrderQuantitySA) {
		this.totalSalesOrderQuantitySA = totalSalesOrderQuantitySA;
	}

	private BigDecimal totalSalesOrderQuantity;

	public BigDecimal getTotalSalesOrderQuantity() {
		return totalSalesOrderQuantity;
	}

	public void setTotalSalesOrderQuantity(BigDecimal totalSalesOrderQuantity) {
		this.totalSalesOrderQuantity = totalSalesOrderQuantity;
	}

	private Boolean isOpen;

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	private String tableKey;

	public String getTableKey() {
		return tableKey;
	}

	public void setTableKey(String tableKey) {
		this.tableKey = tableKey;
	}

	private String documentCurrency;

	public String getDocumentCurrency() {
		return documentCurrency;
	}

	public void setDocumentCurrency(String documentCurrency) {
		this.documentCurrency = documentCurrency;
	}

	private String documentCurrencySA;

	public String getDocumentCurrencySA() {
		return documentCurrencySA;
	}

	public void setDocumentCurrencySA(String documentCurrencySA) {
		this.documentCurrencySA = documentCurrencySA;
	}

	private String referenceDocument;

	public String getReferenceDocument() {
		return referenceDocument;
	}

	public void setReferenceDocument(String referenceDocument) {
		this.referenceDocument = referenceDocument;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String emailId;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	private String city;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	private String destCountry;

	public String getDestCountry() {
		return destCountry;
	}

	public void setDestCountry(String destCountry) {
		this.destCountry = destCountry;
	}

	private String contactNo;

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	private String paymentTerms;

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	private String incoTerms1;

	public String getIncoTerms1() {
		return incoTerms1;
	}

	public void setIncoTerms1(String incoTerms1) {
		this.incoTerms1 = incoTerms1;
	}

	private String incoTerms2;

	public String getIncoTerms2() {
		return incoTerms2;
	}

	public void setIncoTerms2(String incoTerms2) {
		this.incoTerms2 = incoTerms2;
	}

	private String weight;

	public String getWeight() {
		return weight;
	}


		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}


	public void setWeight(String weight) {
		this.weight = weight;
	}

	private String country;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	private String orderReason;

	public String getOrderReason() {
		return orderReason;
	}

	public void setOrderReason(String orderReason) {
		this.orderReason = orderReason;
	}

	private String overDeliveryTolerance;

	public String getOverDeliveryTolerance() {
		return overDeliveryTolerance;
	}

	public void setOverDeliveryTolerance(String overDeliveryTolerance) {
		this.overDeliveryTolerance = overDeliveryTolerance;
	}

	private String underDeliveryTolerance;

	public String getUnderDeliveryTolerance() {
		return underDeliveryTolerance;
	}

	public void setUnderDeliveryTolerance(String underDeliveryTolerance) {
		this.underDeliveryTolerance = underDeliveryTolerance;
	}

	private String colorCodingDetails;

	public String getColorCodingDetails() {
		return colorCodingDetails;
	}

	public void setColorCodingDetails(String colorCodingDetails) {
		this.colorCodingDetails = colorCodingDetails;
	}

	private java.util.Date postingDate;

	public java.util.Date getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(java.util.Date postingDate) {
		this.postingDate = postingDate;
	}

	private String postingError;

	public String getPostingError() {
		return postingError;
	}

	public void setPostingError(String postingError) {
		this.postingError = postingError;
	}

	private Boolean postingStatus; // success or fail

	public Boolean getPostingStatus() {
		return postingStatus;
	}

	public void setPostingStatus(Boolean postingStatus) {
		this.postingStatus = postingStatus;
	}


	private String customerName;

	public String getCustomerName() {
		return customerName;
	}

	private String paymentStatus;
	
	
	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	private String salesOrderId;
	private String obdId;
	private String pgiId;
	private Boolean blocked;
	
	

	public Boolean getBlocked() {
		return blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	public String getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(String salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public String getObdId() {
		return obdId;
	}

	public void setObdId(String obdId) {
		this.obdId = obdId;
	}

	public String getPgiId() {
		return pgiId;
	}

	public void setPgiId(String pgiId) {
		this.pgiId = pgiId;
	}
	
	private String obdStatus;
	private String pgiStatus;
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
	
	private BigDecimal creditBlockQuantity;
	
	public BigDecimal getCreditBlockQuantity() {
		return creditBlockQuantity;
	}

	public void setCreditBlockQuantity(BigDecimal creditBlockQuantity) {
		this.creditBlockQuantity = creditBlockQuantity;
	}
	
	
	private BigDecimal onTimeDeliveredQuantity;
	public BigDecimal getOnTimeDeliveredQuantity() {
		return onTimeDeliveredQuantity;
	}

	public void setOnTimeDeliveredQuantity(BigDecimal onTimeDeliveredQuantity) {
		this.onTimeDeliveredQuantity = onTimeDeliveredQuantity;
	}

	private Integer deliveryLeadingDays;
	
	public Integer getDeliveryLeadingDays() {
		return deliveryLeadingDays;
	}

	public void setDeliveryLeadingDays(Integer deliveryLeadingDays) {
		this.deliveryLeadingDays = deliveryLeadingDays;
	}

	private Integer paymentLeadingDays;
	
	public Integer getPaymentLeadingDays() {
		return paymentLeadingDays;
	}

	public void setPaymentLeadingDays(Integer paymentLeadingDays) {
		this.paymentLeadingDays = paymentLeadingDays;
	}


	private String salesOrganization;
	
	


	public String getSalesOrganization() {
		return salesOrganization;
	}

	public void setSalesOrganization(String salesOrganization) {
		this.salesOrganization = salesOrganization;
	}
	
	private String customerPONum;
	

	public String getCustomerPONum() {
		return customerPONum;
	}

	public void setCustomerPONum(String customerPONum) {
		this.customerPONum = customerPONum;
	}
	
	private BigDecimal outstandingQuantity1;
	
	

	

	public BigDecimal getOutstandingQuantity1() {
		return outstandingQuantity1;
	}

	public void setOutstandingQuantity1(BigDecimal outstandingQuantity1) {
		this.outstandingQuantity1 = outstandingQuantity1;
	}
	
	private EnPaymentChequeStatus paymentChequeDetail;
	
	

	public EnPaymentChequeStatus getPaymentChequeDetail() {
		return paymentChequeDetail;
	}

	public void setPaymentChequeDetail(EnPaymentChequeStatus paymentChequeDetail) {
		this.paymentChequeDetail = paymentChequeDetail;
	}
	
	private Integer deliveryStatus1;
	
	

	public Integer getDeliveryStatus1() {
		return deliveryStatus1;
	}

	public void setDeliveryStatus1(Integer deliveryStatus1) {
		this.deliveryStatus1 = deliveryStatus1;
	}
	
	private Boolean syncStatus1;
	
	
	

	public Boolean getSyncStatus1() {
		return syncStatus1;
	}

	public void setSyncStatus1(Boolean syncStatus1) {
		this.syncStatus1 = syncStatus1;
	}
	
	private String invId;
	
	

	public String getInvId() {
		return invId;
	}

	public void setInvId(String invId) {
		this.invId = invId;
	}

	private String amount;
	
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	// sandeep
	private EnOrderActionStatus documentProcessStatus;

	public EnOrderActionStatus getDocumentProcessStatus() {
		return documentProcessStatus;
	}

	public void setDocumentProcessStatus(EnOrderActionStatus documentProcessStatus) {
		this.documentProcessStatus = documentProcessStatus;
	}

	// @Override
	// public String toString() {
	// return "SalesOrderHeaderDto [clientSpecific=" + clientSpecific + ",
	// s4DocumentId=" + s4DocumentId
	// + ", documentCategory=" + documentCategory + ", documentType=" +
	// documentType + ", salesOrg=" + salesOrg
	// + ", distributionChannel=" + distributionChannel + ", division=" +
	// division + ", salesOffice="
	// + salesOffice + ", salesGroup=" + salesGroup + ", soldToParty=" +
	// soldToParty + ", shipToParty="
	// + shipToParty + ", customerPoNum=" + customerPoNum + ", customerPODate="
	// + customerPODate
	// + ", requestDeliveryDate=" + requestDeliveryDate + ", shippingType=" +
	// shippingType
	// + ", totalSoQuantity=" + totalSoQuantity + ", netValue=" + netValue + ",
	// deliveredQuantity="
	// + deliveredQuantity + ", outstandingQuantity=" + outstandingQuantity + ",
	// createdDate=" + createdDate
	// + ", paymentChqDetail=" + paymentChqDetail + ", overallDocumentStatus=" +
	// overallDocumentStatus
	// + ", deliveryStatus=" + deliveryStatus + ", deliveryTolerance=" +
	// deliveryTolerance + ", colorCoding="
	// + colorCoding + ", comments=" + comments + ", bankName=" + bankName + ",
	// projectName=" + projectName
	// + ", poTypeField=" + poTypeField + ", pieceGuarantee=" + pieceGuarantee +
	// ", acknowledgementStatus="
	// + acknowledgementStatus + ", updateIndicator=" + updateIndicator + ",
	// lastUpdatedOn=" + lastUpdatedOn
	// + ", syncStatus=" + syncStatus + ", createdBy=" + createdBy + ",
	// createdOn=" + createdOn
	// + ", lastChangedBy=" + lastChangedBy + ", lastChangedOn=" + lastChangedOn
	// + ", salesOrderItemList="
	// + salesOrderItemList + ", salesHeaderId=" + salesHeaderId + ", plant=" +
	// plant + ", netValueSA="
	// + netValueSA + ", totalSalesOrderQuantitySA=" + totalSalesOrderQuantitySA
	// + ", totalSalesOrderQuantity="
	// + totalSalesOrderQuantity + ", isOpen=" + isOpen + ", tableKey=" +
	// tableKey + ", documentCurrency="
	// + documentCurrency + ", documentCurrencySA=" + documentCurrencySA + ",
	// referenceDocument="
	// + referenceDocument + ", name=" + name + ", emailId=" + emailId + ",
	// city=" + city + ", destCountry="
	// + destCountry + ", contactNo=" + contactNo + ", paymentTerms=" +
	// paymentTerms + ", incoTerms1="
	// + incoTerms1 + ", incoTerms2=" + incoTerms2 + ", weight=" + weight + ",
	// country=" + country
	// + ", orderReason=" + orderReason + ", overDeliveryTolerance=" +
	// overDeliveryTolerance
	// + ", underDeliveryTolerance=" + underDeliveryTolerance + ",
	// colorCodingDetails=" + colorCodingDetails
	// + ", postingDate=" + postingDate + ", postingError=" + postingError + ",
	// postingStatus=" + postingStatus
	// + ", documentProcessStatus=" + documentProcessStatus + "]";
	// }

	// UPDATE THE toString METHOD AFTER EACH UPDATION OF FIELDS

}