package com.incture.cherrywork.entities;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "REQUEST_MASTER")
public @Data class RequestMasterDo implements BaseDo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "REQUEST_ID", length = 20)
	private String requestId;

	// @JsonBackReference("task-comments")
	// @OneToMany(mappedBy = "reqMaster", cascade = CascadeType.ALL, fetch =
	// FetchType.LAZY) // mandatory
	// private List<CommentDo> commentsList;

	// @JsonBackReference("task-attachments")
	// @OneToMany(mappedBy = "reqMaster", cascade = CascadeType.ALL, fetch =
	// FetchType.LAZY) // mandatory
	// private List<AttachmentDo> attachmentList;

	@Column(name = "REQUESTED_BY", length = 12)
	private String requestedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REQUEST_DATE")
	private Date requestDate;

	// @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY,
	// orphanRemoval = true, mappedBy = "reqMaster")
	// private SalesDocHeaderDo reqMaster;

	@Column(name = "REQUEST_CATEGORY", length = 4)
	private String requestCategory;

	@Column(name = "REQUEST_TYPE", length = 2)
	private String requestType;

	@Column(name = "REQUEST_STATUS_CODE", length = 2)
	private String requestStatusCode;

	@Column(name = "REFERENCE_DOC_TYPE", length = 4)
	private String referenceDocType;

	@Column(name = "REF_DOC_NUM", length = 10)
	private String refDocNum;

	@Column(name = "REQUEST_SHORT_TEXT", length = 40)
	private String requestShortText;

	@Override
	public Object getPrimaryKey() {
		return requestId;
		
		
	}

	public RequestMasterDo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestMasterDo(String requestId, String requestedBy, Date requestDate, String requestCategory,
			String requestType, String requestStatusCode, String referenceDocType, String refDocNum,
			String requestShortText) {
		super();
		this.requestId = requestId;
		this.requestedBy = requestedBy;
		this.requestDate = requestDate;
		this.requestCategory = requestCategory;
		this.requestType = requestType;
		this.requestStatusCode = requestStatusCode;
		this.referenceDocType = referenceDocType;
		this.refDocNum = refDocNum;
		this.requestShortText = requestShortText;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

