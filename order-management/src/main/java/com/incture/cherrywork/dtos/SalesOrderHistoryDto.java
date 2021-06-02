package com.incture.cherrywork.dtos;

import java.util.Date;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = false)
@Data
public class SalesOrderHistoryDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String serialId;

	private String salesDocNum;

	private String salesItemNum;

	private Integer version;

	private String batchNum;

	private Double orderedQtySales;

	private String salesUnit;

	private String itemBillingBlock;

	private String itemDlvBlock;

	private String plant;

	private String storageLoc;

	private Double netPrice;

	private Double netWorth;

	private String reasonOfRejection;

	private Date updatedOn;

	private String updatedBy;

	
	
	
	public SalesOrderHistoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SalesOrderHistoryDto(String serialId, String salesDocNum, String salesItemNum, Integer version,
			String batchNum, Double orderedQtySales, String salesUnit, String itemBillingBlock, String itemDlvBlock,
			String plant, String storageLoc, Double netPrice, Double netWorth, String reasonOfRejection, Date updatedOn,
			String updatedBy) {
		super();
		this.serialId = serialId;
		this.salesDocNum = salesDocNum;
		this.salesItemNum = salesItemNum;
		this.version = version;
		this.batchNum = batchNum;
		this.orderedQtySales = orderedQtySales;
		this.salesUnit = salesUnit;
		this.itemBillingBlock = itemBillingBlock;
		this.itemDlvBlock = itemDlvBlock;
		this.plant = plant;
		this.storageLoc = storageLoc;
		this.netPrice = netPrice;
		this.netWorth = netWorth;
		this.reasonOfRejection = reasonOfRejection;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public String getSalesDocNum() {
		return salesDocNum;
	}

	public void setSalesDocNum(String salesDocNum) {
		this.salesDocNum = salesDocNum;
	}

	public String getSalesItemNum() {
		return salesItemNum;
	}

	public void setSalesItemNum(String salesItemNum) {
		this.salesItemNum = salesItemNum;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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

	public Double getNetWorth() {
		return netWorth;
	}

	public void setNetWorth(Double netWorth) {
		this.netWorth = netWorth;
	}

	public String getReasonOfRejection() {
		return reasonOfRejection;
	}

	public void setReasonOfRejection(String reasonOfRejection) {
		this.reasonOfRejection = reasonOfRejection;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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
