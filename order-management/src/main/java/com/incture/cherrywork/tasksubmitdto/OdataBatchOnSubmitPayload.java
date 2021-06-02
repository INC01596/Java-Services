package com.incture.cherrywork.tasksubmitdto;



import com.incture.cherrywork.dto.workflow.OrderToItems;

import lombok.Data;

@Data
public class OdataBatchOnSubmitPayload {
	private String createdBy;
	private String docNumber;
	private String purchNo;
	private String dlvBlock;
	private String hdrText;
	private String approve_Rej;
	public OdataBatchOnSubmitPayload() {
		super();
		// TODO Auto-generated constructor stub
	}
	private OrderToItems orderToItems;
	
	public OdataBatchOnSubmitPayload(String createdBy, String docNumber, String purchNo, String dlvBlock,
			String hdrText, String approve_Rej, OrderToItems orderToItems) {
		super();
		this.createdBy = createdBy;
		this.docNumber = docNumber;
		this.purchNo = purchNo;
		this.dlvBlock = dlvBlock;
		this.hdrText = hdrText;
		this.approve_Rej = approve_Rej;
		this.orderToItems = orderToItems;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	public String getPurchNo() {
		return purchNo;
	}
	public void setPurchNo(String purchNo) {
		this.purchNo = purchNo;
	}
	public String getDlvBlock() {
		return dlvBlock;
	}
	public void setDlvBlock(String dlvBlock) {
		this.dlvBlock = dlvBlock;
	}
	public String getHdrText() {
		return hdrText;
	}
	public void setHdrText(String hdrText) {
		this.hdrText = hdrText;
	}
	public String getApprove_Rej() {
		return approve_Rej;
	}
	public void setApprove_Rej(String approve_Rej) {
		this.approve_Rej = approve_Rej;
	}
	public OrderToItems getOrderToItems() {
		return orderToItems;
	}
	public void setOrderToItems(OrderToItems orderToItems) {
		this.orderToItems = orderToItems;
	}
	

}