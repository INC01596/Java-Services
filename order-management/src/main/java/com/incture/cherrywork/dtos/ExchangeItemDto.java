
<<<<<<< HEAD
package com.incture.cherrywork.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class ExchangeItemDto {
	private String exchangeReqNum;
	private String exchangeReqItemid;
	private String returnReqNum;
	private String refReturnReqNum;
	private String refDocNum;
	private String refDocItem;
	private String sapMaterialNum;
	private String higherLevelItem;
	private String materialGroup;
	private String materialGroup4;
	private String shortText;
	private String itemCategory;
	private String itemType;
	private String batchNum;
	private Double orderedQtySales;
	private Double cuConfQtyBase;
	private Double cuConfQtySales;
	private Double cuReqQtySales;
	private String salesUnit;
	private String baseUnit;
	private Double convDen;
	private Double convNum;
	private String itemBillingBlock;
	private String sapSalesOrderNum;
	private String sapSalesOrderItemNum;
	
	
	
	private String refDocItm;//os 
	private String material;//os
	private String unitPrice;
	private String ltp;
	private String amount;
	private String higherLevel;//OS
	private String batch;//OS
	private String serialNum;//os
	private String returnUom;//OS
	private Double returnQty;//OS
	private Double unitPriceCc;//os
	private String unitPriceInv;// add in Do
	private String invoiceTotalAmount; // add in Do
	private String returnReqItemid;
	private String plant;//os
	private Date pricingDate;
	private Date serviceRenderedDate;
	private String storageLocation;
	private Date referenceInvDate;
	private Date expiryDate;//add in do
	private String billingType;//os
	private String manualFoc;
	 private boolean itemVisibility;
	

}
=======
package com.incture.cherrywork.dtos;

import java.util.Date;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public @Data class ExchangeItemDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String exchangeReqItemId;

	private String exchangeReqNum;

	private String returnReqNum;

	private String refReturnReqNum;

	private String refReturnReqItemid;

	private String higherLevelItem;

	private String sapMaterialNum;

	private String batchNum;

	private String materialGroup;

	private String materialGroupFor;

	private String shortText;

	private String itemCategory;

	private String itemType;

	private Double cuConfQtyBase;

	private Double cuConfQtySales;

	private Double cuReqQtySales;

	private Double orderedQtySales;

	private String salesUnit;

	private String baseUnit;

	private Double convDen;

	private Double convNum;

	private String itemBillingBlock;

	private String itemDlvBlock;

	private Date firstDeliveryDate;

	private String partialDlv;

	private String refDocNum;

	private Integer refDocItem;

	private String plant;

	private String storageLoc;

	private Double netPrice;

	private String docCurrency;

	private String pricingUnit;

	private String coudUnit;

	private Double taxAmount;

	private Double netWorth;

	private String overallStatus;

	private String deliveryStatus;

	private String reasonForRejection;

	public ExchangeItemDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExchangeItemDto(String exchangeReqItemId, String exchangeReqNum, String returnReqNum, String refReturnReqNum,
			String refReturnReqItemid, String higherLevelItem, String sapMaterialNum, String batchNum,
			String materialGroup, String materialGroupFor, String shortText, String itemCategory, String itemType,
			Double cuConfQtyBase, Double cuConfQtySales, Double cuReqQtySales, Double orderedQtySales, String salesUnit,
			String baseUnit, Double convDen, Double convNum, String itemBillingBlock, String itemDlvBlock,
			Date firstDeliveryDate, String partialDlv, String refDocNum, Integer refDocItem, String plant,
			String storageLoc, Double netPrice, String docCurrency, String pricingUnit, String coudUnit,
			Double taxAmount, Double netWorth, String overallStatus, String deliveryStatus, String reasonForRejection) {
		super();
		this.exchangeReqItemId = exchangeReqItemId;
		this.exchangeReqNum = exchangeReqNum;
		this.returnReqNum = returnReqNum;
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

	public String getExchangeReqItemId() {
		return exchangeReqItemId;
	}

	public void setExchangeReqItemId(String exchangeReqItemId) {
		this.exchangeReqItemId = exchangeReqItemId;
	}

	public String getExchangeReqNum() {
		return exchangeReqNum;
	}

	public void setExchangeReqNum(String exchangeReqNum) {
		this.exchangeReqNum = exchangeReqNum;
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

	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		throw new UnsupportedOperationException();
	}

	

}

>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233
