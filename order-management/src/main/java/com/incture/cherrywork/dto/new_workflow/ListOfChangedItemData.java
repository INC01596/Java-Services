package com.incture.cherrywork.dto.new_workflow;


import com.incture.cherrywork.dtos.BaseDto;
import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = false)
public @Data class ListOfChangedItemData extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String salesItemOrderNo;
	private String material;
	private String materialDes;
	private Double salesQty;
	private String salesUnit;
	private String unitPrice;
	private Double amount;
	private String currency;
	private String batchNum;
	private String storageLoc;
	private String storageLocText;
	private String bonusGood;
	private String stockBlock;
	private String storageLocValue;
	private String itemDeliveryBlock;
	private String itemDeliveryBlockText;
	private String acceptOrReject;
	private String reasonForRejection;
	private String visiblity;
	private String comments;
	private String from;

	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {

	}
	public ListOfChangedItemData() {
		super();
	}

	public ListOfChangedItemData(ListOfChangedItemData item) {
		super();
		salesItemOrderNo = item.getSalesItemOrderNo();
		material = item.getMaterial();
		materialDes = item.getMaterialDes();
		salesQty = item.getSalesQty();
		salesUnit = item.getSalesUnit();
		unitPrice = item.getUnitPrice();
		amount = item.getAmount();
		currency = item.getCurrency();
		batchNum = item.getBatchNum();
		storageLoc = item.getStorageLoc();
		storageLocText = item.getStorageLocText();
		bonusGood = item.getBonusGood();
		stockBlock = item.getStockBlock();
		storageLocValue = item.getStorageLocValue();
		itemDeliveryBlock = item.getItemDeliveryBlock();
		itemDeliveryBlockText = item.getItemDeliveryBlockText();
		acceptOrReject = item.getAcceptOrReject();
		reasonForRejection = item.getReasonForRejection();
		visiblity = item.getVisiblity();
		comments = item.getComments();
		from=item.getFrom();
	}
	public String getSalesItemOrderNo() {
		return salesItemOrderNo;
	}

	public void setSalesItemOrderNo(String salesItemOrderNo) {
		this.salesItemOrderNo = salesItemOrderNo;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getMaterialDes() {
		return materialDes;
	}

	public void setMaterialDes(String materialDes) {
		this.materialDes = materialDes;
	}

	public Double getSalesQty() {
		return salesQty;
	}

	public void setSalesQty(Double salesQty) {
		this.salesQty = salesQty;
	}

	public String getSalesUnit() {
		return salesUnit;
	}

	public void setSalesUnit(String salesUnit) {
		this.salesUnit = salesUnit;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
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

	public String getBonusGood() {
		return bonusGood;
	}

	public void setBonusGood(String bonusGood) {
		this.bonusGood = bonusGood;
	}

	public String getStockBlock() {
		return stockBlock;
	}

	public void setStockBlock(String stockBlock) {
		this.stockBlock = stockBlock;
	}

	public String getStorageLocValue() {
		return storageLocValue;
	}

	public void setStorageLocValue(String storageLocValue) {
		this.storageLocValue = storageLocValue;
	}

	public String getItemDeliveryBlock() {
		return itemDeliveryBlock;
	}

	public void setItemDeliveryBlock(String itemDeliveryBlock) {
		this.itemDeliveryBlock = itemDeliveryBlock;
	}

	public String getItemDeliveryBlockText() {
		return itemDeliveryBlockText;
	}

	public void setItemDeliveryBlockText(String itemDeliveryBlockText) {
		this.itemDeliveryBlockText = itemDeliveryBlockText;
	}

	public String getAcceptOrReject() {
		return acceptOrReject;
	}

	public void setAcceptOrReject(String acceptOrReject) {
		this.acceptOrReject = acceptOrReject;
	}

	public String getReasonForRejection() {
		return reasonForRejection;
	}

	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}

	public String getVisiblity() {
		return visiblity;
	}

	public void setVisiblity(String visiblity) {
		this.visiblity = visiblity;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}

