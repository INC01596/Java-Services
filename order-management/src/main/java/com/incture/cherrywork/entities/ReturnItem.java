<<<<<<< HEAD
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
=======
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
	private String paymentTerms;//os 
	
	
	@Column(name = "CONDITION_GROUP4")
	private String conditionGroup4;//os
	
	
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "REFERENCE_INV_DATE")
	private Date referenceInvDate;//os
	private String itemText;//os
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "PRICING_DATE")
	private Date pricingDate;// os
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "SERVICE_RENDERED_DATE")
	private Date serviceRenderedDate;//os
	public String getReturnReqItemid() {
		return returnReqItemid;
	}
	public void setReturnReqItemid(String returnReqItemid) {
		this.returnReqItemid = returnReqItemid;
	}
	public String getReturnReqNum() {
		return returnReqNum;
	}
	public void setReturnReqNum(String returnReqNum) {
		this.returnReqNum = returnReqNum;
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
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getShortText() {
		return shortText;
	}
	public void setShortText(String shortText) {
		this.shortText = shortText;
	}
	public Double getAvlReturnQty() {
		return avlReturnQty;
	}
	public void setAvlReturnQty(Double avlReturnQty) {
		this.avlReturnQty = avlReturnQty;
	}
	public String getAvlUom() {
		return avlUom;
	}
	public void setAvlUom(String avlUom) {
		this.avlUom = avlUom;
	}
	public Double getReturnQty() {
		return returnQty;
	}
	public void setReturnQty(Double returnQty) {
		this.returnQty = returnQty;
	}
	public String getReturnUom() {
		return returnUom;
	}
	public void setReturnUom(String returnUom) {
		this.returnUom = returnUom;
	}
	public Double getUnitPriceInv() {
		return unitPriceInv;
	}
	public void setUnitPriceInv(Double unitPriceInv) {
		this.unitPriceInv = unitPriceInv;
	}
	public Double getUnitPriceCc() {
		return unitPriceCc;
	}
	public void setUnitPriceCc(Double unitPriceCc) {
		this.unitPriceCc = unitPriceCc;
	}
	public Double getInvoiceTotalAmount() {
		return invoiceTotalAmount;
	}
	public void setInvoiceTotalAmount(Double invoiceTotalAmount) {
		this.invoiceTotalAmount = invoiceTotalAmount;
	}
	public String getStorageLocation() {
		return storageLocation;
	}
	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}
	public String getHigherLevel() {
		return higherLevel;
	}
	public void setHigherLevel(String higherLevel) {
		this.higherLevel = higherLevel;
	}
	public String getBillingType() {
		return billingType;
	}
	public void setBillingType(String billingType) {
		this.billingType = billingType;
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
	public String getSapReturnOrderNum() {
		return sapReturnOrderNum;
	}
	public void setSapReturnOrderNum(String sapReturnOrderNum) {
		this.sapReturnOrderNum = sapReturnOrderNum;
	}
	public String getSapReturnOrderItemNum() {
		return sapReturnOrderItemNum;
	}
	public void setSapReturnOrderItemNum(String sapReturnOrderItemNum) {
		this.sapReturnOrderItemNum = sapReturnOrderItemNum;
	}
	public String getOverallItemWorkflowStatus() {
		return overallItemWorkflowStatus;
	}
	public void setOverallItemWorkflowStatus(String overallItemWorkflowStatus) {
		this.overallItemWorkflowStatus = overallItemWorkflowStatus;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
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
	public Date getReferenceInvDate() {
		return referenceInvDate;
	}
	public void setReferenceInvDate(Date referenceInvDate) {
		this.referenceInvDate = referenceInvDate;
	}
	public String getItemText() {
		return itemText;
	}
	public void setItemText(String itemText) {
		this.itemText = itemText;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233
