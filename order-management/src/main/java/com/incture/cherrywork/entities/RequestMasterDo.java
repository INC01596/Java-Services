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
@NoArgsConstructor
@AllArgsConstructor
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

}
