package com.incture.cherrywork.dtos;

import java.util.UUID;

public class OutBoundDeliveryItemDto {
	
	private String soItemNumber;
	private String obdNumber;
	private String material;
	private String materialDesc;
	private String plant;
	private String sloc;
	private String deliveryQty;
	private String unit;
	private String pickedQty;
	private String netPrice;

	
	public String getSoItemNumber() {
		return soItemNumber;
	}
	public void setSoItemNumber(String soItemNumber) {
		this.soItemNumber = soItemNumber;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public String getSloc() {
		return sloc;
	}
	public void setSloc(String sloc) {
		this.sloc = sloc;
	}
	public String getDeliveryQty() {
		return deliveryQty;
	}
	public void setDeliveryQty(String deliveryQty) {
		this.deliveryQty = deliveryQty;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPickedQty() {
		return pickedQty;
	}
	public void setPickedQty(String pickedQty) {
		this.pickedQty = pickedQty;
	}
	public String getNetPrice() {
		return netPrice;
	}
	public void setNetPrice(String netPrice) {
		this.netPrice = netPrice;
	}
	public String getObdNumber() {
		return obdNumber;
	}
	public void setObdNumber(String obdNumber) {
		this.obdNumber = obdNumber;
	}

}
