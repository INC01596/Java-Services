package com.incture.cherrywork.entities;





import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;




import lombok.Data;


@Entity
@Table(name = "RETURN_ITEM")
public @Data class ReturnItemDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private ReturnItemPrimaryKey key;

	@Column(name = "REF_DOC_NUM", length = 10)
	private String refDocNum;

	@Column(name = "REF_DOC_ITEM", length = 6)
	private String refDocItem;

	@Column(name = "HAS_EXCHANGE")
	private Boolean hasExchange;

	@Column(name = "MATERIAL", length = 18)
	private String material;

	@Column(name = "SHORT_TEXT", length = 40)
	private String shortText;

	@Column(name = "QUANTITY_SALES", precision = 3)
	private Double quantitySales;

	@Column(name = "SALES_UNIT", length = 3)
	private String salesUnit;

	@Column(name = "NET_PRICE", precision = 3)
	private Double netPrice;

	@Column(name = "NET_WORTH", precision = 3)
	private Double netWorth;

	@Column(name = "HIGHER_LEVEL", length = 6)
	private String higherLevel;

	@Column(name = "BATCH", length = 10)
	private String batch;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIRY_DATE")
	private Date expiryDate;

	@Column(name = "STORAGE_LOCATION", length = 4)
	private String storageLocation;

	@Column(name = "REASON_OF_RETURN", length = 3)
	private String reasonOfReturn;
	
	

	public ReturnItemDo() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getPrimaryKey() {
		return key;
	}

	public ReturnItemPrimaryKey getKey() {
		return key;
	}

	public void setKey(ReturnItemPrimaryKey key) {
		this.key = key;
	}

	public ReturnItemDo(ReturnItemPrimaryKey key, String refDocNum, String refDocItem, Boolean hasExchange,
			String material, String shortText, Double quantitySales, String salesUnit, Double netPrice, Double netWorth,
			String higherLevel, String batch, Date expiryDate, String storageLocation, String reasonOfReturn) {
		super();
		this.key = key;
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

}
