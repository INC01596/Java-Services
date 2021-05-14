package com.incture.cherrywork.entities;

import java.util.Date;
import java.util.UUID;

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
@Table(name = "COMMENT")
public @Data class CommentDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "COMMENT_ID", length = 100)
	private String commentId = UUID.randomUUID().toString();

	@Column(name = "COMMENT")
	private String comments;

	@Column(name = "UPDATED_BY", length = 20)
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COMMENT_TIME")
	private Date commentTime;

	// DOC number on which we write Search
	@Column(name = "REF_DOC_NUM", length = 100)
	private String refDocNum;

	// @JsonBackReference("task-comments")
	// @ManyToOne
	// @JoinColumn(name = "REQUEST_ID", nullable = false, referencedColumnName =
	// "REQUEST_ID")
	// private RequestMasterDo reqMaster;

	@Override
	public Object getPrimaryKey() {
		return commentId;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public String getRefDocNum() {
		return refDocNum;
	}

	public void setRefDocNum(String refDocNum) {
		this.refDocNum = refDocNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
