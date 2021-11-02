package com.incture.cherrywork.entities;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name = "BILLING_TRANSACTION")
public class TransactionDo implements BaseDo {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TRANSACTION_ID")
	private String transactionId;

	@Column(name = "SALES_REP")
	private String salesRep;

	@Column(name = "CUST_Id")
	private String customerId;

	@Column(name = "CHEQUE_NUMBER")
	private String chequeNumber;

	@Column(name = "DATE_OF_PAYMENT")
	@Temporal(TemporalType.DATE)
	private Date dateOfPayment;

	@Column(name = "MODE_OF_PAYMENT")
	private String modeOfPayment;

	@Column(name = "TASEK_ALLOCATED_DATE")
	@Temporal(TemporalType.DATE)
	private Date taskAllocatedDate;

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "COMMENT")
	private String comment;

	@Column(name = "BANK_NAME")
	private String bankName;

	@Column(name = "CHEQUE_DATE")
	@Temporal(TemporalType.DATE)
	private Date chequeDate;

	@Column(name = "SALES_REP_NAME")
	private String salesRepName;

	@Column(name = "CUST_NAME")
	private String customerName;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "SALES_REP_PHONE_NO")
	private String salesRepPhoneNo;

	@Column(name = "CUST_PHONE_NO")
	private String customerPhoneNo;
	
	@Column(name = "OUTSTANDING_AMOUNT")
	private String outstandingAmount;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "transaction")
	private List<StatusDo> statusList;

//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "transaction")
//	private List<AttachmentDo> attachmentList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "transaction")
	private List<InvoiceDo> invoiceList;

	@Override
	public Object getPrimaryKey() {
		return transactionId;
	}

}
