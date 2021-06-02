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
	private String paymentTerms;//os 
	
	
	@Column(name = "CONDITION_GROUP4")
	private String conditionGroup4;//os


	public String getExchangeReqNum() {
		return exchangeReqNum;
	}


	public void setExchangeReqNum(String exchangeReqNum) {
		this.exchangeReqNum = exchangeReqNum;
	}


	public String getExchangeReqItemid() {
		return exchangeReqItemid;
	}


	public void setExchangeReqItemid(String exchangeReqItemid) {
		this.exchangeReqItemid = exchangeReqItemid;
	}


	public String getReturnReqNum() {
		return returnReqNum;
	}


	public void setReturnReqNum(String returnReqNum) {
		this.returnReqNum = returnReqNum;
	}


	public String getRefReturnReqNum() {
		return refReturnReqNum;
	}


	public void setRefReturnReqNum(String refReturnReqNum) {
		this.refReturnReqNum = refReturnReqNum;
	}


	public String getRefDocNum() {
		return refDocNum;
	}


	public void setRefDocNum(String refDocNum) {
		this.refDocNum = refDocNum;
	}


	public String getRefDocItem() {
		return refDocItem;
	}


	public void setRefDocItem(String refDocItem) {
		this.refDocItem = refDocItem;
	}


	public String getSapMaterialNum() {
		return sapMaterialNum;
	}


	public void setSapMaterialNum(String sapMaterialNum) {
		this.sapMaterialNum = sapMaterialNum;
	}


	public String getHigherLevelItem() {
		return higherLevelItem;
	}


	public void setHigherLevelItem(String higherLevelItem) {
		this.higherLevelItem = higherLevelItem;
	}


	public String getMaterialGroup() {
		return materialGroup;
	}


	public void setMaterialGroup(String materialGroup) {
		this.materialGroup = materialGroup;
	}


	public String getMaterialGroup4() {
		return materialGroup4;
	}


	public void setMaterialGroup4(String materialGroup4) {
		this.materialGroup4 = materialGroup4;
	}


	public String getShortText() {
		return shortText;
	}


	public void setShortText(String shortText) {
		this.shortText = shortText;
	}


	public String getItemCategory() {
		return itemCategory;
	}


	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}


	public String getItemType() {
		return itemType;
	}


	public void setItemType(String itemType) {
		this.itemType = itemType;
	}


	public String getBatchNum() {
		return batchNum;
	}


	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}


	public Double getOrderedQtySales() {
		return orderedQtySales;
	}


	public void setOrderedQtySales(Double orderedQtySales) {
		this.orderedQtySales = orderedQtySales;
	}


	public Double getCuConfQtyBase() {
		return cuConfQtyBase;
	}


	public void setCuConfQtyBase(Double cuConfQtyBase) {
		this.cuConfQtyBase = cuConfQtyBase;
	}


	public Double getCuConfQtySales() {
		return cuConfQtySales;
	}


	public void setCuConfQtySales(Double cuConfQtySales) {
		this.cuConfQtySales = cuConfQtySales;
	}


	public Double getCuReqQtySales() {
		return cuReqQtySales;
	}


	public void setCuReqQtySales(Double cuReqQtySales) {
		this.cuReqQtySales = cuReqQtySales;
	}


	public String getSalesUnit() {
		return salesUnit;
	}


	public void setSalesUnit(String salesUnit) {
		this.salesUnit = salesUnit;
	}


	public String getBaseUnit() {
		return baseUnit;
	}


	public void setBaseUnit(String baseUnit) {
		this.baseUnit = baseUnit;
	}


	public Double getConvDen() {
		return convDen;
	}


	public void setConvDen(Double convDen) {
		this.convDen = convDen;
	}


	public Double getConvNum() {
		return convNum;
	}


	public void setConvNum(Double convNum) {
		this.convNum = convNum;
	}


	public String getItemBillingBlock() {
		return itemBillingBlock;
	}


	public void setItemBillingBlock(String itemBillingBlock) {
		this.itemBillingBlock = itemBillingBlock;
	}


	public String getSapSalesOrderNum() {
		return sapSalesOrderNum;
	}


	public void setSapSalesOrderNum(String sapSalesOrderNum) {
		this.sapSalesOrderNum = sapSalesOrderNum;
	}


	public String getSapSalesOrderItemNum() {
		return sapSalesOrderItemNum;
	}


	public void setSapSalesOrderItemNum(String sapSalesOrderItemNum) {
		this.sapSalesOrderItemNum = sapSalesOrderItemNum;
	}


	public String getMaterial() {
		return material;
	}


	public void setMaterial(String material) {
		this.material = material;
	}


	public String getHigherLevel() {
		return higherLevel;
	}


	public void setHigherLevel(String higherLevel) {
		this.higherLevel = higherLevel;
	}


	public String getBatch() {
		return batch;
	}


	public void setBatch(String batch) {
		this.batch = batch;
	}


	public String getSerialNum() {
		return serialNum;
	}


	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}


	public String getReturnUom() {
		return returnUom;
	}


	public void setReturnUom(String returnUom) {
		this.returnUom = returnUom;
	}


	public Double getReturnQty() {
		return returnQty;
	}


	public void setReturnQty(Double returnQty) {
		this.returnQty = returnQty;
	}


	public Double getUnitPriceCc() {
		return unitPriceCc;
	}


	public void setUnitPriceCc(Double unitPriceCc) {
		this.unitPriceCc = unitPriceCc;
	}


	public String getUnitPriceInv() {
		return unitPriceInv;
	}


	public void setUnitPriceInv(String unitPriceInv) {
		this.unitPriceInv = unitPriceInv;
	}


	public String getInvoiceTotalAmount() {
		return invoiceTotalAmount;
	}


	public void setInvoiceTotalAmount(String invoiceTotalAmount) {
		this.invoiceTotalAmount = invoiceTotalAmount;
	}


	public String getPlant() {
		return plant;
	}


	public void setPlant(String plant) {
		this.plant = plant;
	}


	public String getStorageLocation() {
		return storageLocation;
	}


	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}


	public String getBillingType() {
		return billingType;
	}


	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}


	public Date getExpiryDate() {
		return expiryDate;
	}


	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}


	public Date getReferenceInvDate() {
		return referenceInvDate;
	}


	public void setReferenceInvDate(Date referenceInvDate) {
		this.referenceInvDate = referenceInvDate;
	}


	public Date getPricingDate() {
		return pricingDate;
	}


	public void setPricingDate(Date pricingDate) {
		this.pricingDate = pricingDate;
	}


	public Date getServiceRenderedDate() {
		return serviceRenderedDate;
	}


	public void setServiceRenderedDate(Date serviceRenderedDate) {
		this.serviceRenderedDate = serviceRenderedDate;
	}


	public String getManualFoc() {
		return manualFoc;
	}


	public void setManualFoc(String manualFoc) {
		this.manualFoc = manualFoc;
	}


	public String getPaymentTerms() {
		return paymentTerms;
	}


	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}


	public String getConditionGroup4() {
		return conditionGroup4;
	}


	public void setConditionGroup4(String conditionGroup4) {
		this.conditionGroup4 = conditionGroup4;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}
