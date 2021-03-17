package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "OutBoundDeliveryItem")
public class OutBoundDeliveryItem {
	
	@Id
	@Column(name = "SoItemNumber", length = 30)
	private String soItemNumber;
	@Column(name = "Material")
	private String material;
    @Column(name = "MaterialDesc")
	private String materialDesc;
    @Column(name = "Plant")
	private String plant;
    @Column(name = "Sloc")
	private String sloc;
    @Column(name = "DeliveryQty")
	private String deliveryQty;
    @Column(name = "Unit")
	private String unit;
    @Column(name = "PickedQty")
	private String pickedQty;
    @Column(name = "NetPrice")
	private String netPrice;
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "obdNumber")@JsonIgnore
    private OutBoundDelivery outBoundDelivery;
    
	public OutBoundDelivery getOutBoundDelivery() {
		return outBoundDelivery;
	}
	public void setOutBoundDelivery(OutBoundDelivery outBoundDelivery) {
		this.outBoundDelivery = outBoundDelivery;
	}
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
	

}
