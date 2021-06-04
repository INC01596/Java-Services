
package com.incture.cherrywork.dtos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public @Data class ReturnItemDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String returnReqItemId;

	private String returnReqNum;

	private String refDocNum;

	private String refDocItem;

	private Boolean hasExchange;

	private String material;

	private String shortText;

	private Double quantitySales;

	private String salesUnit;

	private Double netPrice;

	private Double netWorth;

	private String higherLevel;

	private String batch;

	private Date expiryDate;

	private String storageLocation;

	private String reasonOfReturn;

	public ReturnItemDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReturnItemDto(String returnReqItemId, String returnReqNum, String refDocNum, String refDocItem,
			Boolean hasExchange, String material, String shortText, Double quantitySales, String salesUnit,
			Double netPrice, Double netWorth, String higherLevel, String batch, Date expiryDate, String storageLocation,
			String reasonOfReturn) {
		super();
		this.returnReqItemId = returnReqItemId;
		this.returnReqNum = returnReqNum;
		this.refDocNum = refDocNum;
		this.refDocItem = refDocItem;
		this.hasExchange = hasExchange;
		this.material = material;
		this.shortText = shortText;
		this.quantitySales = quantitySales;
		this.salesUnit = salesUnit;
		this.netPrice = netPrice;
		this.netWorth = netWorth;
		this.higherLevel = higherLevel;
		this.batch = batch;
		this.expiryDate = expiryDate;
		this.storageLocation = storageLocation;
		this.reasonOfReturn = reasonOfReturn;
	}

	public String getReturnReqItemId() {
		return returnReqItemId;
	}

	public void setReturnReqItemId(String returnReqItemId) {
		this.returnReqItemId = returnReqItemId;
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

	public Boolean getHasExchange() {
		return hasExchange;
	}

	public void setHasExchange(Boolean hasExchange) {
		this.hasExchange = hasExchange;
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

	public Double getQuantitySales() {
		return quantitySales;
	}

	public void setQuantitySales(Double quantitySales) {
		this.quantitySales = quantitySales;
	}

	public String getSalesUnit() {
		return salesUnit;
	}

	public void setSalesUnit(String salesUnit) {
		this.salesUnit = salesUnit;
	}

	public Double getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(Double netPrice) {
		this.netPrice = netPrice;
	}

	public Double getNetWorth() {
		return netWorth;
	}

	public void setNetWorth(Double netWorth) {
		this.netWorth = netWorth;
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

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	public String getReasonOfReturn() {
		return reasonOfReturn;
	}

	public void setReasonOfReturn(String reasonOfReturn) {
		this.reasonOfReturn = reasonOfReturn;
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


