package com.incture.cherrywork.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CascadeType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ReturnItemPk.class)
@Table(name = "RETURN_ITEM")
public @Data class ReturnItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RETURN_REQUEST_ITEM_ID", nullable = false)
	private String returnReqItemid;

	@Id
	@Column(name = "RETURN_REQ_NUM")
	private String returnReqNum;

	@Id
	@Column(name = "REF_DOC_NUM")
	private String refDocNum;

	@Column(name = "REF_DOC_ITEM")
	private String refDocItem;

	@Column(name = "MATERIAL_GROUP")
	private String materialGroup;

	@Column(name = "MATERIAL_GROUP_4")
	private String materialGroup4;

	@Column(name = "MATERIAL")
	private String material;

	@Column(name = "SHORT_TEXT")
	private String shortText;

	@Column(name = "AVL_RETURN_QTY")
	private Double avlReturnQty;

	@Column(name = "AVL_UOM")
	private String avlUom;

	@Column(name = "RETURN_QTY")
	private Double returnQty;

	@Column(name = "RETURN_UOM")
	private String returnUom;

	@Column(name = "UNIT_PRICE_INV")
	private Double unitPriceInv;

	@Column(name = "UNIT_PRICE_CC")
	private Double unitPriceCc;

	@Column(name = "INVOICE_TOTAL_AMOUNT")
	private Double invoiceTotalAmount;

	@Column(name = "STORAGE_LOCATION")
	private String storageLocation;

	@Column(name = "HIGHER_LEVEL")
	private String higherLevel;

	@Column(name = "BILLING_TYPE")
	private String billingType;

	@Column(name = "BATCH")
	private String batch;

	@Column(name = "SERIAL_NUM")
	private String serialNum;

	@Column(name = "SAP_RETURN_ORDER_NUM")
	private String sapReturnOrderNum;

	@Column(name = "SAP_RETURN_ORDER_ITEM_NUM")
	private String sapReturnOrderItemNum;

	@Column(name = "OVERALL_ITEM_WORKFLOW_STATUS")
	private String overallItemWorkflowStatus;

	@Column(name = "PLANT")
	private String plant;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "EXPIRY_DATE")
	private Date expiryDate;

	@Column(name = "PAYMENT_TERMS")
	private String paymentTerms;// os

	@Column(name = "CONDITION_GROUP4")
	private String conditionGroup4;// os

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "REFERENCE_INV_DATE")
	private Date referenceInvDate;// os
	private String itemText;// os
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "PRICING_DATE")
	private Date pricingDate;// os
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "SERVICE_RENDERED_DATE")
	private Date serviceRenderedDate;// os

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
