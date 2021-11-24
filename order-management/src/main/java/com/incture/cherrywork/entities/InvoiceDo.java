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
@Data // for auto generation of getters and setters
@Table(name = "BILLING_INVOICE")
public class InvoiceDo implements BaseDo {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INVOICE_ID")
	private Long invoiceId;

	@Column(name = "SAP_INVOICE_NUMBER")
	private String sapInvoiceNumber;

	@Column(name = "INVOICE_AMOUNT")
	private Double invoiceAmount;

	@Column(name = "INVOICE_CURRENCY")
	private String invoiceCurrency;

	@Column(name = "PENDING_AMOUNT")
	private Double pendingAmount;

	@Column(name = "INVOICE_CREATED_DATE")
	@Temporal(TemporalType.DATE)
	private Date invoiceCreatedDate;

	@Column(name = "COLLECTION_DUE_DATE")
	@Temporal(TemporalType.DATE)
	private Date collectionDueDate;

	@Column(name = "CUSTOMER_ID")
	private String customerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transactionId", nullable = true)
	private TransactionDo transaction;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "DOC_TYPE")
	private String documentType;
	
	@Column(name = "IS_CREDIT_NOTE")
	private Boolean isCreditNote;

	@Override
	public Long getPrimaryKey() {
		return invoiceId;
	}

}
