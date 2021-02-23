package com.incture.cherrywork.dtos;
import java.util.Date;
import java.math.BigDecimal;
public class SalesOrderHeaderDto{


private Integer clientSpecific;
public Integer getClientSpecific(){
	return clientSpecific;
}
public void setClientSpecific(Integer clientSpecific){
	this.clientSpecific = clientSpecific;
}

private String s4DocumentId;
public String getS4DocumentId(){
	return s4DocumentId;
}
public void setS4DocumentId(String s4DocumentId){
	this.s4DocumentId = s4DocumentId;
}

private String documentCategory;
public String getDocumentCategory(){
	return documentCategory;
}
public void setDocumentCategory(String documentCategory){
	this.documentCategory = documentCategory;
}

private String documentType;
public String getDocumentType(){
	return documentType;
}
public void setDocumentType(String documentType){
	this.documentType = documentType;
}

private String salesOrg;
public String getSalesOrg(){
	return salesOrg;
}
public void setSalesOrg(String salesOrg){
	this.salesOrg = salesOrg;
}

private String distributionChannel;
public String getDistributionChannel(){
	return distributionChannel;
}
public void setDistributionChannel(String distributionChannel){
	this.distributionChannel = distributionChannel;
}

private String division;
public String getDivision(){
	return division;
}
public void setDivision(String division){
	this.division = division;
}

private String salesOffice;
public String getSalesOffice(){
	return salesOffice;
}
public void setSalesOffice(String salesOffice){
	this.salesOffice = salesOffice;
}

private String salesGroup;
public String getSalesGroup(){
	return salesGroup;
}
public void setSalesGroup(String salesGroup){
	this.salesGroup = salesGroup;
}

private String soldToParty;
public String getSoldToParty(){
	return soldToParty;
}
public void setSoldToParty(String soldToParty){
	this.soldToParty = soldToParty;
}

private String shipToParty;
public String getShipToParty(){
	return shipToParty;
}
public void setShipToParty(String shipToParty){
	this.shipToParty = shipToParty;
}

private String customerPoNum;
public String getCustomerPoNum(){
	return customerPoNum;
}
public void setCustomerPoNum(String customerPoNum){
	this.customerPoNum = customerPoNum;
}

private Date customerPoDate;
public Date getCustomerPoDate(){
	return customerPoDate;
}
public void setCustomerPoDate(Date customerPoDate){
	this.customerPoDate = customerPoDate;
}

private Date requestDeliveryDate;
public Date getRequestDeliveryDate(){
	return requestDeliveryDate;
}
public void setRequestDeliveryDate(Date requestDeliveryDate){
	this.requestDeliveryDate = requestDeliveryDate;
}

private String shippingType;
public String getShippingType(){
	return shippingType;
}
public void setShippingType(String shippingType){
	this.shippingType = shippingType;
}

private BigDecimal totalSoQuantity;
public BigDecimal getTotalSoQuantity(){
	return totalSoQuantity;
}
public void setTotalSoQuantity(BigDecimal totalSoQuantity){
	this.totalSoQuantity = totalSoQuantity;
}

private BigDecimal netValue;
public BigDecimal getNetValue(){
	return netValue;
}
public void setNetValue(BigDecimal netValue){
	this.netValue = netValue;
}

private BigDecimal deliveredQuantity;
public BigDecimal getDeliveredQuantity(){
	return deliveredQuantity;
}
public void setDeliveredQuantity(BigDecimal deliveredQuantity){
	this.deliveredQuantity = deliveredQuantity;
}

private String outstandingQuantity;
public String getOutstandingQuantity(){
	return outstandingQuantity;
}
public void setOutstandingQuantity(String outstandingQuantity){
	this.outstandingQuantity = outstandingQuantity;
}

private Date createdDate;
public Date getCreatedDate(){
	return createdDate;
}
public void setCreatedDate(Date createdDate){
	this.createdDate = createdDate;
}

private String paymentChqDetail;
public String getPaymentChqDetail(){
	return paymentChqDetail;
}
public void setPaymentChqDetail(String paymentChqDetail){
	this.paymentChqDetail = paymentChqDetail;
}

private String overallDocumentStatus;
public String getOverallDocumentStatus(){
	return overallDocumentStatus;
}
public void setOverallDocumentStatus(String overallDocumentStatus){
	this.overallDocumentStatus = overallDocumentStatus;
}

private String deliveryStatus;
public String getDeliveryStatus(){
	return deliveryStatus;
}
public void setDeliveryStatus(String deliveryStatus){
	this.deliveryStatus = deliveryStatus;
}

private String deliveryTolerance;
public String getDeliveryTolerance(){
	return deliveryTolerance;
}
public void setDeliveryTolerance(String deliveryTolerance){
	this.deliveryTolerance = deliveryTolerance;
}

private String colorCoding;
public String getColorCoding(){
	return colorCoding;
}
public void setColorCoding(String colorCoding){
	this.colorCoding = colorCoding;
}

private String comments;
public String getComments(){
	return comments;
}
public void setComments(String comments){
	this.comments = comments;
}

private String bankName;
public String getBankName(){
	return bankName;
}
public void setBankName(String bankName){
	this.bankName = bankName;
}

private String projectName;
public String getProjectName(){
	return projectName;
}
public void setProjectName(String projectName){
	this.projectName = projectName;
}

private String poTypeField;
public String getPoTypeField(){
	return poTypeField;
}
public void setPoTypeField(String poTypeField){
	this.poTypeField = poTypeField;
}

private Boolean pieceGuarantee;
public Boolean getPieceGuarantee(){
	return pieceGuarantee;
}
public void setPieceGuarantee(Boolean pieceGuarantee){
	this.pieceGuarantee = pieceGuarantee;
}

private Boolean acknowledgementStatus;
public Boolean getAcknowledgementStatus(){
	return acknowledgementStatus;
}
public void setAcknowledgementStatus(Boolean acknowledgementStatus){
	this.acknowledgementStatus = acknowledgementStatus;
}

private String updateIndicator;
public String getUpdateIndicator(){
	return updateIndicator;
}
public void setUpdateIndicator(String updateIndicator){
	this.updateIndicator = updateIndicator;
}

private Date lastUpdatedOn;
public Date getLastUpdatedOn(){
	return lastUpdatedOn;
}
public void setLastUpdatedOn(Date lastUpdatedOn){
	this.lastUpdatedOn = lastUpdatedOn;
}

private Date syncStatus;
public Date getSyncStatus(){
	return syncStatus;
}
public void setSyncStatus(Date syncStatus){
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
private String changedBy;
public String getChangedBy() {
	return changedBy;
}
public void setChangedBy(String changedBy) {
	this.changedBy = changedBy;
}
private Date changedOn;
public Date getChangedOn() {
	return changedOn;
}
public void setChangedOn(Date changedOn) {
	this.changedOn = changedOn;
}
}