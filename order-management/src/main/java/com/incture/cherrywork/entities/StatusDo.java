package com.incture.cherrywork.entities;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name = "BILLING_STATUS")
public class StatusDo implements BaseDo {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "STATUS_ID")
	private Long statusId;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	@Column(name = "APPROVER_COMMENTS")
	private String approverComments;

	@Column(name = "PENDING_WITH")
	private String pendingWith;

	@Column(name = "STATUS")
	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transactionId", nullable = false)
	private TransactionDo transaction;

	@Column(name = "REJECTION_RES")
	private String rejectionReason;
	
	@Column(name = "APPROVER_NAME")
	private String approverName;

	@Override
	public Object getPrimaryKey() {
		return statusId;
	}

}

