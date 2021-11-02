package com.incture.cherrywork.entities;



import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data // for auto generation of getters and setters
@Table(name = "PENDING_INVOICES_ITEMS")
public class PendingInvoiceItemDo  implements BaseDo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7441177709522677786L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PEND_INV_ITM_ID")
	private Long pendInvItmPrimaryId;
	
	@Column(name="ITEM_NUMBER")
	private String itemNumber;
	
	@Column(name="MAT_ID")
	private String matID;
	
	@Column(name="DESC")
	private String description;
	
	@Column(name="HIGH_ITEM")
	private BigDecimal highItem;
	
	@Column(name="BILLED_QTY")
	private BigDecimal billedQty;
	
	@Column(name="SALES_UNIT")
	private String salesUnit;
	
	@Column(name="NET_PRICE")
	private BigDecimal netPrice;
	
	@Column(name="TAX_AMOUNT")
	private BigDecimal taxAmount;
	
	@Column(name="SALES_DOC")
	private String salesDoc;
	
	@Column(name="ITEM_CAT")
	private String itemCateg;
	
	@Column(name="FREE_GOOD_IND")
	private String freegoodInd;
	
	@Column(name="INVOICE_NUMBER")
	private String invoiceNumber;
	
	@ManyToOne
	@JoinColumn(name = "ACC_DOCUMENT_NO", nullable = false, updatable = false)
	private PendingInvoiceDo pendingInvoice;

	@Override
	public Object getPrimaryKey() {	
		return pendInvItmPrimaryId;
	}

}

