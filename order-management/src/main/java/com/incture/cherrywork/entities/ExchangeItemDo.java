package com.incture.cherrywork.entities;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity

@Table(name = "EXCHANGE_ITEM")
public @Data class ExchangeItemDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private ExchangeItemPrimaryKey key;

	@Column(name = "REF_RETURN_REQ_NUM", length = 100)
	private String refReturnReqNum;

	@Column(name = "REF_RETURN_REQ_ITEM_ID", length = 100)
	private String refReturnReqItemid;

	@Column(name = "HIGHER_LEVEL_ITEM", length = 100)
	private String higherLevelItem;

	@Column(name = "SAP_MATERIAL_NUM", length = 18)
	private String sapMaterialNum;

	@Column(name = "BATCH_NUM", length = 10)
	private String batchNum;

	@Column(name = "MATERIAL_GROUP", length = 9)
	private String materialGroup;

	@Column(name = "MATERIAL_GROUP_FOR", length = 9)
	private String materialGroupFor;

	@Column(name = "SHORT_TEXT", length = 40)
	private String shortText;

	@Column(name = "ITEM_CATEGORY", length = 4)
	private String itemCategory;

	@Column(name = "ITEM_TYPE", length = 1)
	private String itemType;

	@Column(precision = 3, name = "CU_CONF_QTY_BASE")
	private Double cuConfQtyBase;

	@Column(precision = 3, name = "CU_CONF_QTY_SALES")
	private Double cuConfQtySales;

	@Column(precision = 3, name = "CU_REQ_QTY_SALES")
	private Double cuReqQtySales;

	@Column(precision = 3, name = "ORDERED_QTY_SALES")
	private Double orderedQtySales;

	@Column(name = "SALES_UNIT", length = 3)
	private String salesUnit;

	@Column(name = "BASE_UNIT", length = 3)
	private String baseUnit;

	@Column(precision = 3, name = "CONV_DEN")
	private Double convDen;

	@Column(precision = 3, name = "CONV_NUM")
	private Double convNum;

	@Column(name = "ITEM_BILLING_BLOCK", length = 2)
	private String itemBillingBlock;

	@Column(name = "ITEM_DLV_BLOCK", length = 2)
	private String itemDlvBlock;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FIRST_DELIVERY_DATE")
	private Date firstDeliveryDate;

	@Column(name = "PARTIAL_DLV", length = 1)
	private String partialDlv;

	@Column(name = "REF_DOC_NUM", length = 10)
	private String refDocNum;

	@Column(name = "REF_DOC_ITEM")
	private Integer refDocItem;

	@Column(name = "PLANT", length = 4)
	private String plant;

	@Column(name = "STORAGE_LOC", length = 4)
	private String storageLoc;

	@Column(precision = 3, name = "NET_PRICE")
	private Double netPrice;

	@Column(name = "DOC_CURRENCY", length = 4)
	private String docCurrency;

	@Column(name = "PRICING_UNIT", length = 4)
	private String pricingUnit;

	@Column(name = "COUD_UNIT", length = 3)
	private String coudUnit;

	@Column(precision = 3, name = "TAX_AMOUNT")
	private Double taxAmount;

	@Column(precision = 3, name = "NET_WORTH")
	private Double netWorth;

	@Column(name = "OVERALL_STATUS", length = 1)
	private String overallStatus;

	@Column(name = "DELIVERY_STATUS", length = 1)
	private String deliveryStatus;

	@Column(name = "REASON_FOR_REJECTION", length = 4)
	private String reasonForRejection;

	@Override
	public Object getPrimaryKey() {
		return key;
	}

	public ExchangeItemPrimaryKey getKey() {
		return key;
	}

	public void setKey(ExchangeItemPrimaryKey key) {
		this.key = key;
	}

	public String getRefReturnReqNum() {
		return refReturnReqNum;
	}

	public void setRefReturnReqNum(String refReturnReqNum) {
		this.refReturnReqNum = refReturnReqNum;
	}

	public String getRefReturnReqItemid() {
		return refReturnReqItemid;
	}

	public void setRefReturnReqItemid(String refReturnReqItemid) {
		this.refReturnReqItemid = refReturnReqItemid;
	}

	public String getHigherLevelItem() {
		return higherLevelItem;
	}

	public void setHigherLevelItem(String higherLevelItem) {
		this.higherLevelItem = higherLevelItem;
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

	public String getMaterialGroup() {
		return materialGroup;
	}

	public void setMaterialGroup(String materialGroup) {
		this.materialGroup = materialGroup;
	}

	public String getMaterialGroupFor() {
		return materialGroupFor;
	}

	public void setMaterialGroupFor(String materialGroupFor) {
		this.materialGroupFor = materialGroupFor;
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

	public Integer getRefDocItem() {
		return refDocItem;
	}

	public void setRefDocItem(Integer refDocItem) {
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

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ExchangeItemDo(ExchangeItemPrimaryKey key, String refReturnReqNum, String refReturnReqItemid,
			String higherLevelItem, String sapMaterialNum, String batchNum, String materialGroup,
			String materialGroupFor, String shortText, String itemCategory, String itemType, Double cuConfQtyBase,
			Double cuConfQtySales, Double cuReqQtySales, Double orderedQtySales, String salesUnit, String baseUnit,
			Double convDen, Double convNum, String itemBillingBlock, String itemDlvBlock, Date firstDeliveryDate,
			String partialDlv, String refDocNum, Integer refDocItem, String plant, String storageLoc, Double netPrice,
			String docCurrency, String pricingUnit, String coudUnit, Double taxAmount, Double netWorth,
			String overallStatus, String deliveryStatus, String reasonForRejection) {
		super();
		this.key = key;
		this.refReturnReqNum = refReturnReqNum;
		this.refReturnReqItemid = refReturnReqItemid;
		this.higherLevelItem = higherLevelItem;
		this.sapMaterialNum = sapMaterialNum;
		this.batchNum = batchNum;
		this.materialGroup = materialGroup;
		this.materialGroupFor = materialGroupFor;
		this.shortText = shortText;
		this.itemCategory = itemCategory;
		this.itemType = itemType;
		this.cuConfQtyBase = cuConfQtyBase;
		this.cuConfQtySales = cuConfQtySales;
		this.cuReqQtySales = cuReqQtySales;
		this.orderedQtySales = orderedQtySales;
		this.salesUnit = salesUnit;
		this.baseUnit = baseUnit;
		this.convDen = convDen;
		this.convNum = convNum;
		this.itemBillingBlock = itemBillingBlock;
		this.itemDlvBlock = itemDlvBlock;
		this.firstDeliveryDate = firstDeliveryDate;
		this.partialDlv = partialDlv;
		this.refDocNum = refDocNum;
		this.refDocItem = refDocItem;
		this.plant = plant;
		this.storageLoc = storageLoc;
		this.netPrice = netPrice;
		this.docCurrency = docCurrency;
		this.pricingUnit = pricingUnit;
		this.coudUnit = coudUnit;
		this.taxAmount = taxAmount;
		this.netWorth = netWorth;
		this.overallStatus = overallStatus;
		this.deliveryStatus = deliveryStatus;
		this.reasonForRejection = reasonForRejection;
	}

	public ExchangeItemDo() {
		// TODO Auto-generated constructor stub
	}

}

