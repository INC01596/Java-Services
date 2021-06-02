package com.incture.cherrywork.dtos;

import java.util.Date;
import java.util.List;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = false)
public @Data class RequestMasterDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String requestId;

	private String requestedBy;

	private Date requestDate;

	private String requestCategory;

	private String requestType;

	private String requestStatusCode;

	private String referenceDocType;

	private String refDocNum;

	private String requestShortText;

	private List<CommentDto> commentsList;

	public RequestMasterDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestMasterDto(String requestId, String requestedBy, Date requestDate, String requestCategory,
			String requestType, String requestStatusCode, String referenceDocType, String refDocNum,
			String requestShortText, List<CommentDto> commentsList) {
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
		this.commentsList = commentsList;
	}

	// private List<AttachmentDto> attachmentList;

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

	public List<CommentDto> getCommentsList() {
		return commentsList;
	}

	public void setCommentsList(List<CommentDto> commentsList) {
		this.commentsList = commentsList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		throw new UnsupportedOperationException();
	}

}

