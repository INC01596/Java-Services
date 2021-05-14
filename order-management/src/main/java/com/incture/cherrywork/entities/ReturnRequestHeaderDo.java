package com.incture.cherrywork.entities;



import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RETURN_REQUEST")
public @Data class ReturnRequestHeaderDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RETURN_REQ_NUM", length = 20)
	private String returnReqNum;

	@JsonManagedReference("task-Request_Item")
	@OneToMany(mappedBy = "key.returnReqHeaderDo", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // mandatory
	private List<ReturnItemDo> returnItemList;

	@JsonManagedReference("task-exchangeHeader")
	@OneToOne(mappedBy = "key.returnRequestHeaderDo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ExchangeHeaderDo exchangeHeader;

	@Column(name = "WORKFLOW_INSTANCE", length = 40)
	private String workflowInstance;

	@Column(name = "REQUESTED_BY", length = 20)
	private String requestedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REQUEST_DATE")
	private Date requestDate;

	@Column(name = "REQUEST_CATEGORY", length = 3)
	private String requestCategory;

	@Column(name = "REQUEST_TYPE", length = 10)
	private String requestType;

	@Column(name = "REQUEST_STATUS_CODE", length = 2)
	private String requestStatusCode;

	@Column(name = "REQUEST_DOC_TYPE", length = 3)
	private String referenceDocType;

	@Column(name = "REF_DOC_NUM", length = 10)
	private String refDocNum;

	@Column(name = "REQUEST_SHORT_TEXT", length = 40)
	private String requestShortText;

	@Column(name = "ORDER_REASON", length = 3)
	private String orderReason;

	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	// @JoinColumn(name = "RETURN_REQ_NUM", nullable = false,
	// referencedColumnName = "REF_DOC_NUM")
	// private List<CommentDo> commentDoList;

	@Override
	public Object getPrimaryKey() {
		return returnReqNum;
	}

	public String getReturnReqNum() {
		return returnReqNum;
	}

	public void setReturnReqNum(String returnReqNum) {
		this.returnReqNum = returnReqNum;
	}

	public List<ReturnItemDo> getReturnItemList() {
		return returnItemList;
	}

	public void setReturnItemList(List<ReturnItemDo> returnItemList) {
		this.returnItemList = returnItemList;
	}

	public ExchangeHeaderDo getExchangeHeader() {
		return exchangeHeader;
	}

	public void setExchangeHeader(ExchangeHeaderDo exchangeHeader) {
		this.exchangeHeader = exchangeHeader;
	}

	public String getWorkflowInstance() {
		return workflowInstance;
	}

	public void setWorkflowInstance(String workflowInstance) {
		this.workflowInstance = workflowInstance;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getRequestCategory() {
		return requestCategory;
	}

	public void setRequestCategory(String requestCategory) {
		this.requestCategory = requestCategory;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRequestStatusCode() {
		return requestStatusCode;
	}

	public void setRequestStatusCode(String requestStatusCode) {
		this.requestStatusCode = requestStatusCode;
	}

	public String getReferenceDocType() {
		return referenceDocType;
	}

	public void setReferenceDocType(String referenceDocType) {
		this.referenceDocType = referenceDocType;
	}

	public String getRefDocNum() {
		return refDocNum;
	}

	public void setRefDocNum(String refDocNum) {
		this.refDocNum = refDocNum;
	}

	public String getRequestShortText() {
		return requestShortText;
	}

	public void setRequestShortText(String requestShortText) {
		this.requestShortText = requestShortText;
	}

	public String getOrderReason() {
		return orderReason;
	}

	public void setOrderReason(String orderReason) {
		this.orderReason = orderReason;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

