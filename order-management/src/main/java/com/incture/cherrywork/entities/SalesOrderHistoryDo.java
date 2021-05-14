package com.incture.cherrywork.entities;

import java.util.Date;
import java.util.UUID;

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

@Table(name = "SALES_ORDER_HISTORY")
@Data
public class SalesOrderHistoryDo implements BaseDo {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SERIAL_ID", length = 100, nullable = false)
	private String serialId = UUID.randomUUID().toString();

	@Column(name = "SALES_ORDER_NUM")
	private String salesDocNum;

	@Column(name = "SALES_ITEM_NUM")
	private String salesItemNum;

	@Column(name = "VERSION")
	private Integer version;

	@Column(name = "BATCH_NUM")
	private String batchNum;

	@Column(name = "ORDERED_QTY_SALES")
	private Double orderedQtySales;

	@Column(name = "SALES_UNIT")
	private String salesUnit;

	@Column(name = "ITEM_BILLING_BLOCK")
	private String itemBillingBlock;

	@Column(name = "ITEM_DLV_BLOCK")
	private String itemDlvBlock;

	@Column(name = "PLANT")
	private String plant;

	@Column(name = "STORAGELOC")
	private String storageLoc;

	@Column(name = "NETPRICE")
	private Double netPrice;

	@Column(name = "NET_WORTH")
	private Double netWorth;

	@Column(name = "REASON_REJECTION")
	private String reasonOfRejection;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_ON")
	private Date updatedOn;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	

	public SalesOrderHistoryDo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SalesOrderHistoryDo(String serialId, String salesDocNum, String salesItemNum, Integer version,
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

	@Override
	public Object getPrimaryKey() {

		return serialId;
		
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
	

}