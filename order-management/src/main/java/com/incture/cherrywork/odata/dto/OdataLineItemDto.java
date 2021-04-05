package com.incture.cherrywork.odata.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OdataLineItemDto {

	@Expose
	@SerializedName("temp_id")
	private String temp_id;
	
	@Expose
	@SerializedName("Item")
	private String Item;
	
	@Expose
	@SerializedName("itemcat")
	private String itemcat;
	
	@Expose
	@SerializedName("Material")
	private String Material;
	
	@Expose
	@SerializedName("Quantity")
	private String Quantity;
	
	@Expose
	@SerializedName("Price")
	private String Price;
	
	@Expose
	@SerializedName("Plant")
	private String Plant;
	
	@Expose
	@SerializedName("Currency")
	private String Currency;
	
	@Expose
	@SerializedName("QUnit")
	private String QUnit;
	
	@Expose
	@SerializedName("Flag")
	private String Flag;
	
	@Expose
	@SerializedName("Status")
	private String Status;
	
	@Expose
	@SerializedName("std")
	private String std;
	
	@Expose
	@SerializedName("grade")
	private String grade;
	
	@Expose
	@SerializedName("size")
	private String size;
	
	@Expose
	@SerializedName("length")
	private String length;
	
	@Expose
	@SerializedName("kgperm")
	private String kgperm;
	
	@Expose
	@SerializedName("cu_ord_qty")
	private String cu_ord_qty;
	
	@Expose
	@SerializedName("Insp32")
	private String Insp32;
	
	@Expose
	@SerializedName("ImpactTest")
	private String ImpactTest;
	
	@Expose
	@SerializedName("BendTest")
	private String BendTest;
	
	@Expose
	@SerializedName("Ultrsonic")
	private String Ultrsonic;
	
	@Expose
	@SerializedName("Hardness")
	private String Hardness;
	
	@Expose
	@SerializedName("Boron_req")
	private String Boron_req;
	
	@Expose
	@SerializedName("Ref_Doc")
	private String Ref_Doc;
	
	@Expose
	@SerializedName("Ref_Doc_it")
	private String Ref_Doc_it;
	
	@Expose
	@SerializedName("Rate")
	private String Rate;

	public String getTemp_id() {
		return temp_id;
	}

	public void setTemp_id(String temp_id) {
		this.temp_id = temp_id;
	}

	public String getItem() {
		return Item;
	}

	public void setItem(String item) {
		Item = item;
	}

	public String getMaterial() {
		return Material;
	}

	public void setMaterial(String material) {
		Material = material;
	}

	public String getQuantity() {
		return Quantity;
	}

	public void setQuantity(String quantity) {
		Quantity = quantity;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getPlant() {
		return Plant;
	}

	public void setPlant(String plant) {
		Plant = plant;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getQUnit() {
		return QUnit;
	}

	public void setQUnit(String qUnit) {
		QUnit = qUnit;
	}

	public String getFlag() {
		return Flag;
	}

	public void setFlag(String flag) {
		Flag = flag;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	
	public String getItemcat() {
		return itemcat;
	}

	public void setItemcat(String itemcat) {
		this.itemcat = itemcat;
	}

	public String getStd() {
		return std;
	}

	public void setStd(String std) {
		this.std = std;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getKgperm() {
		return kgperm;
	}

	public void setKgperm(String kgperm) {
		this.kgperm = kgperm;
	}

	public String getCu_ord_qty() {
		return cu_ord_qty;
	}

	public void setCu_ord_qty(String cu_ord_qty) {
		this.cu_ord_qty = cu_ord_qty;
	}

	public String getInsp32() {
		return Insp32;
	}

	public void setInsp32(String insp32) {
		Insp32 = insp32;
	}

	public String getImpactTest() {
		return ImpactTest;
	}

	public void setImpactTest(String impactTest) {
		ImpactTest = impactTest;
	}

	public String getBendTest() {
		return BendTest;
	}

	public void setBendTest(String bendTest) {
		BendTest = bendTest;
	}

	public String getUltrsonic() {
		return Ultrsonic;
	}

	public void setUltrsonic(String ultrsonic) {
		Ultrsonic = ultrsonic;
	}

	public String getHardness() {
		return Hardness;
	}

	public void setHardness(String hardness) {
		Hardness = hardness;
	}

	public String getBoron_req() {
		return Boron_req;
	}

	public void setBoron_req(String boron_req) {
		Boron_req = boron_req;
	}

	public String getRate() {
		return Rate;
	}

	public void setRate(String rate) {
		Rate = rate;
	}

	public String getRef_Doc() {
		return Ref_Doc;
	}

	public void setRef_Doc(String ref_Doc) {
		Ref_Doc = ref_Doc;
	}

	public String getRef_Doc_it() {
		return Ref_Doc_it;
	}

	public void setRef_Doc_it(String ref_Doc_it) {
		Ref_Doc_it = ref_Doc_it;
	}

	@Override
	public String toString() {
		return "OdataLineItemDto [temp_id=" + temp_id + ", Item=" + Item + ", itemcat=" + itemcat + ", Material="
				+ Material + ", Quantity=" + Quantity + ", Price=" + Price + ", Plant=" + Plant + ", Currency="
				+ Currency + ", QUnit=" + QUnit + ", Flag=" + Flag + ", Status=" + Status + ", std=" + std + ", grade="
				+ grade + ", size=" + size + ", length=" + length + ", kgperm=" + kgperm + ", cu_ord_qty=" + cu_ord_qty
				+ ", Insp32=" + Insp32 + ", ImpactTest=" + ImpactTest + ", BendTest=" + BendTest + ", Ultrsonic="
				+ Ultrsonic + ", Hardness=" + Hardness + ", Boron_req=" + Boron_req + ", Ref_Doc=" + Ref_Doc
				+ ", Ref_Doc_it=" + Ref_Doc_it + ", Rate=" + Rate + "]";
	}
}
