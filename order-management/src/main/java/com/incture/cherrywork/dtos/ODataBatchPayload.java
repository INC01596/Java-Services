package com.incture.cherrywork.dtos;

import lombok.Data;

@Data
public class ODataBatchPayload {
	private String refDocCat;
	private String roType;
	private String billToParty;
	private String salesDocument;
	private String payer;
	private String docType;
	private String hdrDelBlk;
	private String refDoc;
	private String customerPo;
	private String salesOrg;
	private String refNum;
	private String distrChan;
	private String division;
	private String soldToParty;
	private String shipToParty;
	private String flagRoSo;
	private String headerText;
	private String reasonOwner;
	private String remarks;
	private String ordReason;
	private String complDlv;
	private String name;
	private String requestedBy;
	private String attachmentUrl;
	private OrderHdrToOrderItem OrderHdrToOrderItem;
	private OrderHdrToOrderCondition OrderHdrToOrderCondition;
	private OrderHdrToOrderPartner OrderHdrToOrderPartner;
	public String getRefDocCat() {
		return refDocCat;
	}
	public void setRefDocCat(String refDocCat) {
		this.refDocCat = refDocCat;
	}
	public String getRoType() {
		return roType;
	}
	public void setRoType(String roType) {
		this.roType = roType;
	}
	public String getBillToParty() {
		return billToParty;
	}
	public void setBillToParty(String billToParty) {
		this.billToParty = billToParty;
	}
	public String getSalesDocument() {
		return salesDocument;
	}
	public void setSalesDocument(String salesDocument) {
		this.salesDocument = salesDocument;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getHdrDelBlk() {
		return hdrDelBlk;
	}
	public void setHdrDelBlk(String hdrDelBlk) {
		this.hdrDelBlk = hdrDelBlk;
	}
	public String getRefDoc() {
		return refDoc;
	}
	public void setRefDoc(String refDoc) {
		this.refDoc = refDoc;
	}
	public String getCustomerPo() {
		return customerPo;
	}
	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	public String getSalesOrg() {
		return salesOrg;
	}
	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}
	public String getRefNum() {
		return refNum;
	}
	public void setRefNum(String refNum) {
		this.refNum = refNum;
	}
	public String getDistrChan() {
		return distrChan;
	}
	public void setDistrChan(String distrChan) {
		this.distrChan = distrChan;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getSoldToParty() {
		return soldToParty;
	}
	public void setSoldToParty(String soldToParty) {
		this.soldToParty = soldToParty;
	}
	public String getShipToParty() {
		return shipToParty;
	}
	public void setShipToParty(String shipToParty) {
		this.shipToParty = shipToParty;
	}
	public String getFlagRoSo() {
		return flagRoSo;
	}
	public void setFlagRoSo(String flagRoSo) {
		this.flagRoSo = flagRoSo;
	}
	public String getHeaderText() {
		return headerText;
	}
	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}
	public String getReasonOwner() {
		return reasonOwner;
	}
	public void setReasonOwner(String reasonOwner) {
		this.reasonOwner = reasonOwner;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getOrdReason() {
		return ordReason;
	}
	public void setOrdReason(String ordReason) {
		this.ordReason = ordReason;
	}
	public String getComplDlv() {
		return complDlv;
	}
	public void setComplDlv(String complDlv) {
		this.complDlv = complDlv;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public String getAttachmentUrl() {
		return attachmentUrl;
	}
	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}
	public OrderHdrToOrderItem getOrderHdrToOrderItem() {
		return OrderHdrToOrderItem;
	}
	public void setOrderHdrToOrderItem(OrderHdrToOrderItem orderHdrToOrderItem) {
		OrderHdrToOrderItem = orderHdrToOrderItem;
	}
	public OrderHdrToOrderCondition getOrderHdrToOrderCondition() {
		return OrderHdrToOrderCondition;
	}
	public void setOrderHdrToOrderCondition(OrderHdrToOrderCondition orderHdrToOrderCondition) {
		OrderHdrToOrderCondition = orderHdrToOrderCondition;
	}
	public OrderHdrToOrderPartner getOrderHdrToOrderPartner() {
		return OrderHdrToOrderPartner;
	}
	public void setOrderHdrToOrderPartner(OrderHdrToOrderPartner orderHdrToOrderPartner) {
		OrderHdrToOrderPartner = orderHdrToOrderPartner;
	}
	
	
}
