package com.incture.cherrywork.dto.workflow;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.incture.cherrywork.dtos.BaseDto;
import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class SalesDocItemDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String salesItemOrderNo;

	private String salesHeaderNo;

	private String splPrice;

	private String sapMaterialNum;

	private String itemStagingStatus;

	private String sapMaterialDescription;

	private String batchNum;

	private String materialGroup;

	private String shortText;

	private String itemCategory;

	private String itemType;

	private String salesUnit;

	private String itemBillingBlock;

	private String itemDlvBlock;

	private String itemDlvBlockText;

	private String acceptOrReject;

	private Date firstDeliveryDate;

	private String partialDlv;

	private String refDocNum;

	private String refDocItem;

	private String plant;

	private String storageLoc;

	private String storageLocText;

	private String oldMatCode;

	private String storageLocValue;

	private String bonusGood;

	// needs to be calculated
	private String itemStockBlock;

	private String netPrice;

	private String docCurrency;

	private String pricingUnit;

	private String coudUnit;

	private String netWorth;

	private String overallStatus;

	private String deliveryStatus;

	private String reasonForRejection;

	private String reasonForRejectionText;

	private Integer visiblity;

	private Integer taskItemStatus;

	private String materialGroup4;

	private String baseUnit;

	private String higherLevelItem;

	private String comments;

	private Double amount;

	private Double taxAmount;

	private Double convDen;

	private Double convNum;

	private Double cuConfQtyBase;

	private Double cuConfQtySales;

	private Double cuReqQtySales;

	private Double orderedQtySales;

	private String strategy;

	private String decisionSetId;

	private String itemCategText;

	private String salesTeam;

	private String salesArea;

	private String serialNumber;

	private BigInteger matExpiryDate;

	private List<ScheduleLineDto> scheduleLineList;

	private List<String> specialClientListForItem;

	
	
	public String getSalesItemOrderNo() {
		return salesItemOrderNo;
	}


	public void setSalesItemOrderNo(String salesItemOrderNo) {
		this.salesItemOrderNo = salesItemOrderNo;
	}


	public String getSalesHeaderNo() {
		return salesHeaderNo;
	}


	public void setSalesHeaderNo(String salesHeaderNo) {
		this.salesHeaderNo = salesHeaderNo;
	}


	public String getSplPrice() {
		return splPrice;
	}


	public void setSplPrice(String splPrice) {
		this.splPrice = splPrice;
	}


	public String getSapMaterialNum() {
		return sapMaterialNum;
	}


	public void setSapMaterialNum(String sapMaterialNum) {
		this.sapMaterialNum = sapMaterialNum;
	}


	public String getItemStagingStatus() {
		return itemStagingStatus;
	}


	public void setItemStagingStatus(String itemStagingStatus) {
		this.itemStagingStatus = itemStagingStatus;
	}


	public String getSapMaterialDescription() {
		return sapMaterialDescription;
	}


	public void setSapMaterialDescription(String sapMaterialDescription) {
		this.sapMaterialDescription = sapMaterialDescription;
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


	public String getItemDlvBlockText() {
		return itemDlvBlockText;
	}


	public void setItemDlvBlockText(String itemDlvBlockText) {
		this.itemDlvBlockText = itemDlvBlockText;
	}


	public String getAcceptOrReject() {
		return acceptOrReject;
	}


	public void setAcceptOrReject(String acceptOrReject) {
		this.acceptOrReject = acceptOrReject;
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


	public String getStorageLocText() {
		return storageLocText;
	}


	public void setStorageLocText(String storageLocText) {
		this.storageLocText = storageLocText;
	}


	public String getOldMatCode() {
		return oldMatCode;
	}


	public void setOldMatCode(String oldMatCode) {
		this.oldMatCode = oldMatCode;
	}


	public String getStorageLocValue() {
		return storageLocValue;
	}


	public void setStorageLocValue(String storageLocValue) {
		this.storageLocValue = storageLocValue;
	}


	public String getBonusGood() {
		return bonusGood;
	}


	public void setBonusGood(String bonusGood) {
		this.bonusGood = bonusGood;
	}


	public String getItemStockBlock() {
		return itemStockBlock;
	}


	public void setItemStockBlock(String itemStockBlock) {
		this.itemStockBlock = itemStockBlock;
	}


	public String getNetPrice() {
		return netPrice;
	}


	public void setNetPrice(String netPrice) {
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


	public String getNetWorth() {
		return netWorth;
	}


	public void setNetWorth(String netWorth) {
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


	public Integer getVisiblity() {
		return visiblity;
	}


	public void setVisiblity(Integer visiblity) {
		this.visiblity = visiblity;
	}


	public Integer getTaskItemStatus() {
		return taskItemStatus;
	}


	public void setTaskItemStatus(Integer taskItemStatus) {
		this.taskItemStatus = taskItemStatus;
	}


	public String getMaterialGroup4() {
		return materialGroup4;
	}


	public void setMaterialGroup4(String materialGroup4) {
		this.materialGroup4 = materialGroup4;
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


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}


	public Double getAmount() {
		return amount;
	}


	public void setAmount(Double amount) {
		this.amount = amount;
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


	public String getStrategy() {
		return strategy;
	}


	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}


	public String getDecisionSetId() {
		return decisionSetId;
	}


	public void setDecisionSetId(String decisionSetId) {
		this.decisionSetId = decisionSetId;
	}


	public String getItemCategText() {
		return itemCategText;
	}


	public void setItemCategText(String itemCategText) {
		this.itemCategText = itemCategText;
	}


	public String getSalesTeam() {
		return salesTeam;
	}


	public void setSalesTeam(String salesTeam) {
		this.salesTeam = salesTeam;
	}


	public String getSalesArea() {
		return salesArea;
	}


	public void setSalesArea(String salesArea) {
		this.salesArea = salesArea;
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


	public List<ScheduleLineDto> getScheduleLineList() {
		return scheduleLineList;
	}


	public void setScheduleLineList(List<ScheduleLineDto> scheduleLineList) {
		this.scheduleLineList = scheduleLineList;
	}


	public List<String> getSpecialClientListForItem() {
		return specialClientListForItem;
	}


	public void setSpecialClientListForItem(List<String> specialClientListForItem) {
		this.specialClientListForItem = specialClientListForItem;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Boolean getValidForUsage() {
		return null;
	}

	
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		throw new UnsupportedOperationException();
	}

	public SalesDocItemDto(String salesItemOrderNo, String salesHeaderNo) {
		super();
		this.salesItemOrderNo = salesItemOrderNo;
		this.salesHeaderNo = salesHeaderNo;
	}

}
