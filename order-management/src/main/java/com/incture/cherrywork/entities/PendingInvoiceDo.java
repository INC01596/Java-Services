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
@Data // for auto generation of getters and setters
@Table(name = "PENDING_INVOICES")
public class PendingInvoiceDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 321649381504164687L;

	@Id
	@Column(name = "ACC_DOCUMENT_NO")
	private String accountingDocument;

	@Column(name = "CUST_ID")
	private String custId;

	@Column(name = "DOC_TYPE")
	private String documentType;

	@Column(name = "BILLING_DOC_NUM")
	private String billingDocumentNo;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "AMT_LOCAL_CURRENCY")
	private BigDecimal amountInLocalCurrency;

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "PENDING_AMOUNT")
	private BigDecimal pendingAmount;

	@Temporal(TemporalType.DATE)
	@Column(name = "POSTING_DATE")
	private Date postingDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "DOC_DATE")
	private Date documentDate;

	@Column(name = "INVOICE_NUMBER")
	private String invoiceNumber;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "BILL_DATE")
	private Date billDate;
	
	@Column(name = "SOLD_TO_PARTY")
	private String soldToParty;
	
	@Column(name = "PAYER")
	private String payer;
	
	@Column(name = "NET_VALUE")
	private BigDecimal netValue;
	
	@Column(name = "TAX_AMOUNT")
	private BigDecimal taxAmount;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "pendingInvoice", orphanRemoval = true)
	private List<PendingInvoiceItemDo> invoiceItemList;
	
	
	@Override
	public Object getPrimaryKey() {
		return accountingDocument;
	}

}
