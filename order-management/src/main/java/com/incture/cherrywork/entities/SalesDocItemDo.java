package com.incture.cherrywork.entities;

import java.math.BigInteger;
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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SALES_DOC_ITEM")
public @Data class SalesDocItemDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private SalesDocItemPrimaryKeyDo salesDocItemKey;

	@JsonBackReference("task-soItem")
	@OneToMany(mappedBy = "scheduleLineKey.soItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // mandatory
	private List<ScheduleLineDo> scheduleLineList;

	@Column(name = "SAP_MATERIAL_NUM", length = 18)
	private String sapMaterialNum;

	@Column(name = "BATCH_NUM", length = 10)
	private String batchNum;

	@Column(name = "SPL_PRICE")
	private String splPrice;

	@Column(name = "MATERIAL_GROUP", length = 20)
	private String materialGroup;

	@Column(name = "SHORT_TEXT", length = 1000)
	private String shortText;

	@Column(name = "ITEM_CATEGORY", length = 20)
	private String itemCategory;

	@Column(name = "ITEM_TYPE", length = 20)
	private String itemType;

	@Column(name = "OLD_MAT_CODE", length = 20)
	private String oldMatCode;

	@Column(name = "SALES_UNIT", length = 20)
	private String salesUnit;

	@Column(name = "ITEM_BILLING_BLOCK", length = 20)
	private String itemBillingBlock;

	@Column(name = "ITEM_DLV_BLOCK", length = 100)
	private String itemDlvBlock;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FIRST_DELIVERY_DATE")
	private Date firstDeliveryDate;

	@Column(name = "PARTIAL_DLV", length = 20)
	private String partialDlv;

	@Column(name = "REF_DOC_NUM", length = 20)
	private String refDocNum;

	@Column(name = "REF_DOC_ITEM")
	private String refDocItem;

	@Column(name = "PLANT", length = 20)
	private String plant;

	@Column(name = "STORAGE_LOC", length = 20)
	private String storageLoc;

	@Column(precision = 3, name = "NET_PRICE")
	private Double netPrice;

	@Column(name = "DOC_CURRENCY", length = 20)
	private String docCurrency;

	@Column(name = "PRICING_UNIT", length = 20)
	private String pricingUnit;

	@Column(name = "COUD_UNIT", length = 20)
	private String coudUnit;

	@Column(precision = 3, name = "NET_WORTH")
	private Double netWorth;

	@Column(name = "OVERALL_STATUS", length = 20)
	private String overallStatus;

	@Column(name = "DELIVERY_STATUS", length = 20)
	private String deliveryStatus;

	@Column(name = "REASON_FOR_REJECTION", length = 255)
	private String reasonForRejection;

	@Column(name = "REASON_FOR_REJECTION_TEXT")
	private String reasonForRejectionText;

	@Column(name = "MATERIAL_GROUP_FOR", length = 20)
	private String materialGroupFor;

	@Column(name = "BASE_UNIT", length = 20)
	private String baseUnit;

	@Column(name = "HIGHER_LEVEL_ITEM", length = 100)
	private String higherLevelItem;

	@Column(precision = 3, name = "TAX_AMOUNT")
	private Double taxAmount;

	@Column(precision = 3, name = "CONV_DEN")
	private Double convDen;

	@Column(precision = 3, name = "CONV_NUM")
	private Double convNum;

	@Column(precision = 3, name = "CU_CONF_QTY_BASE")
	private Double cuConfQtyBase;

	@Column(precision = 3, name = "CU_CONF_QTY_SALES")
	private Double cuConfQtySales;

	@Column(precision = 3, name = "CU_REQ_QTY_SALES")
	private Double cuReqQtySales;

	@Column(precision = 3, name = "ORDERED_QTY_SALES")
	private Double orderedQtySales;

	@Column(name = "DECISION_SET_ID", length = 100)
	private String decisionSetId;

	@Column(name = "ITEM_STAGING_STATUS", length = 20)
	private String itemStagingStatus;

	@Column(name = "ITEM_CATEG_TEXT", length = 100)
	private String itemCategText;

	@Column(name = "SALES_AREA")
	private String salesArea;

	@Column(name = "SALES_TEAM")
	private String salesTeam;

	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;

	@Column(name = "MATERIAL_EXPIRY_DATE")
	private BigInteger matExpiryDate;

	@Override
	public Object getPrimaryKey() {
		return salesDocItemKey;
	}

	public SalesDocItemPrimaryKeyDo getSalesDocItemKey() {
		return salesDocItemKey;
	}

	public void setSalesDocItemKey(SalesDocItemPrimaryKeyDo salesDocItemKey) {
		this.salesDocItemKey = salesDocItemKey;
	}

	public List<ScheduleLineDo> getScheduleLineList() {
		return scheduleLineList;
	}

	public void setScheduleLineList(List<ScheduleLineDo> scheduleLineList) {
		this.scheduleLineList = scheduleLineList;
	}

	public String getSapMaterialNum() {
		return sapMaterialNum;
	}

	public void setSapMaterialNum(String sapMaterialNum) {
		this.sapMaterialNum = sapMaterialNum;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public String getSplPrice() {
		return splPrice;
	}

	public void setSplPrice(String splPrice) {
		this.splPrice = splPrice;
	}

	public String getMaterialGroup() {
		return materialGroup;
	}

	public void setMaterialGroup(String materialGroup) {
		this.materialGroup = materialGroup;
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

	public String getOldMatCode() {
		return oldMatCode;
	}

	public void setOldMatCode(String oldMatCode) {
		this.oldMatCode = oldMatCode;
	}

	public String getSalesUnit() {
		return salesUnit;
	}

	public void setSalesUnit(String salesUnit) {
		this.salesUnit = salesUnit;
	}

	public String getItemBillingBlock() {
		return itemBillingBlock;
	}

	public void setItemBillingBlock(String itemBillingBlock) {
		this.itemBillingBlock = itemBillingBlock;
	}

	public String getItemDlvBlock() {
		return itemDlvBlock;
	}

	public void setItemDlvBlock(String itemDlvBlock) {
		this.itemDlvBlock = itemDlvBlock;
	}

	public Date getFirstDeliveryDate() {
		return firstDeliveryDate;
	}

	public void setFirstDeliveryDate(Date firstDeliveryDate) {
		this.firstDeliveryDate = firstDeliveryDate;
	}

	public String getPartialDlv() {
		return partialDlv;
	}

	public void setPartialDlv(String partialDlv) {
		this.partialDlv = partialDlv;
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

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getStorageLoc() {
		return storageLoc;
	}

	public void setStorageLoc(String storageLoc) {
		this.storageLoc = storageLoc;
	}

	public Double getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(Double netPrice) {
		this.netPrice = netPrice;
	}

	public String getDocCurrency() {
		return docCurrency;
	}

	public void setDocCurrency(String docCurrency) {
		this.docCurrency = docCurrency;
	}

	public String getPricingUnit() {
		return pricingUnit;
	}

	public void setPricingUnit(String pricingUnit) {
		this.pricingUnit = pricingUnit;
	}

	public String getCoudUnit() {
		return coudUnit;
	}

	public void setCoudUnit(String coudUnit) {
		this.coudUnit = coudUnit;
	}

	public Double getNetWorth() {
		return netWorth;
	}

	public void setNetWorth(Double netWorth) {
		this.netWorth = netWorth;
	}

	public String getOverallStatus() {
		return overallStatus;
	}

	public void setOverallStatus(String overallStatus) {
		this.overallStatus = overallStatus;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getReasonForRejection() {
		return reasonForRejection;
	}

	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}

	public String getReasonForRejectionText() {
		return reasonForRejectionText;
	}

	public void setReasonForRejectionText(String reasonForRejectionText) {
		this.reasonForRejectionText = reasonForRejectionText;
	}

	public String getMaterialGroupFor() {
		return materialGroupFor;
	}

	public void setMaterialGroupFor(String materialGroupFor) {
		this.materialGroupFor = materialGroupFor;
	}

	public String getBaseUnit() {
		return baseUnit;
	}

	public void setBaseUnit(String baseUnit) {
		this.baseUnit = baseUnit;
	}

	public String getHigherLevelItem() {
		return higherLevelItem;
	}

	public void setHigherLevelItem(String higherLevelItem) {
		this.higherLevelItem = higherLevelItem;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
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

	public Double getOrderedQtySales() {
		return orderedQtySales;
	}

	public void setOrderedQtySales(Double orderedQtySales) {
		this.orderedQtySales = orderedQtySales;
	}

	public String getDecisionSetId() {
		return decisionSetId;
	}

	public void setDecisionSetId(String decisionSetId) {
		this.decisionSetId = decisionSetId;
	}

	public String getItemStagingStatus() {
		return itemStagingStatus;
	}

	public void setItemStagingStatus(String itemStagingStatus) {
		this.itemStagingStatus = itemStagingStatus;
	}

	public String getItemCategText() {
		return itemCategText;
	}

	public void setItemCategText(String itemCategText) {
		this.itemCategText = itemCategText;
	}

	public String getSalesArea() {
		return salesArea;
	}

	public void setSalesArea(String salesArea) {
		this.salesArea = salesArea;
	}

	public String getSalesTeam() {
		return salesTeam;
	}

	public void setSalesTeam(String salesTeam) {
		this.salesTeam = salesTeam;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public BigInteger getMatExpiryDate() {
		return matExpiryDate;
	}

	public void setMatExpiryDate(BigInteger matExpiryDate) {
		this.matExpiryDate = matExpiryDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

