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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ExchangeItemPk.class)
@Table(name = "EXCHANGE_ITEM")
public @Data class ExchangeItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EXCHANGE_REQ_NUM")
	private String exchangeReqNum;

	@Id
	@Column(name = "EXCHANGE_REQ_ITEM_ID")
	private String exchangeReqItemid;

	@Id
	@Column(name = "RETURN_REQ_NUM")
	private String returnReqNum;

	@Column(name = "REF_RETURN_REQ_NUM")
	private String refReturnReqNum;

	@Column(name = "REF_DOC_NUM")
	private String refDocNum;

	@Column(name = "REF_DOC_ITEM")
	private String refDocItem; // check in the ui

	@Column(name = "SAP_MATERIAL_NUM")
	private String sapMaterialNum;

	@Column(name = "HIGHER_LEVEL_ITEM")
	private String higherLevelItem;

	@Column(name = "MATERIAL_GROUP")
	private String materialGroup;

	@Column(name = "MATERIAL_GROUP_4")
	private String materialGroup4;

	@Column(name = "SHORT_TEXT")
	private String shortText;

	@Column(name = "ITEM_CATEGORY")
	private String itemCategory;

	@Column(name = "ITEM_TYPE")
	private String itemType;

	@Column(name = "BATCH_NUM")
	private String batchNum;

	@Column(precision = 3, name = "ORDERED_QTY_SALES")
	private Double orderedQtySales;

	@Column(precision = 3, name = "CU_CONF_QTY_BASE")
	private Double cuConfQtyBase;

	@Column(precision = 3, name = "CU_CONF_QTY_SALES")
	private Double cuConfQtySales;

	@Column(precision = 3, name = "CU_REQ_QTY_SALES")
	private Double cuReqQtySales;

	@Column(name = "SALES_UNIT", length = 3)
	private String salesUnit;

	@Column(name = "BASE_UNIT", length = 3)
	private String baseUnit;

	@Column(precision = 3, name = "CONV_DEN")
	private Double convDen;

	@Column(precision = 3, name = "CONV_NUM")
	private Double convNum;

	@Column(name = "ITEM_BILLING_BLOCK")
	private String itemBillingBlock;

	@Column(name = "SAP_RETURN_ORDER_NUM")
	private String sapSalesOrderNum;

	@Column(name = "SAP_RETURN_ORDER_ITEM_NUM")
	private String sapSalesOrderItemNum;

	@Column(name = "MATERIAL")
	private String material;// os

	@Column(name = "HIGHER_LEVEL")
	private String higherLevel;// OS

	@Column(name = "BATCH")
	private String batch;// OS

	@Column(name = "SERIAL_NUM")
	private String serialNum;// os

	@Column(name = "RETURN_UOM")
	private String returnUom;// OS

	@Column(name = "RETURN_QTY")
	private Double returnQty;// OS

	@Column(name = "UNIT_PRICE_CC")
	private Double unitPriceCc;// os

	@Column(name = "UNIT_PRICE_INV")
	private String unitPriceInv;// add in Do

	@Column(name = "TOTAL_NET_AMOUNT")
	private String invoiceTotalAmount; // add in Do

	@Column(name = "PLANT")
	private String plant;// os

	@Column(name = "STORAGE_LOCATION")
	private String storageLocation;
	@Column(name = "BILLING_TYPE")
	private String billingType;// os

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "EXPIRY_DATE")
	private Date expiryDate;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "REFERENCE_INV_DATE")
	private Date referenceInvDate;// os
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "PRICING_DATE")
	private Date pricingDate;// os
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "SERVICE_RENDERED_DATE")
	private Date serviceRenderedDate;// os

	@Column(name = "MANUAL_FOC")
	private String manualFoc;

	@Column(name = "PAYMENT_TERMS")
	private String paymentTerms;// os

	@Column(name = "CONDITION_GROUP4")
	private String conditionGroup4;// os

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
