package com.incture.cherrywork.dtos;

import java.math.BigDecimal;
import java.util.List;

public class SalesOrderSearchHeaderDto {

	private String materialDescription;
	private String material;
	private String standard;
	private String size;
	private String sectionGrade;
	private BigDecimal kgPerMeter;
	private String length;
	private String otherLength;
	private Boolean container;
	private List<String> plant;
	
	public String getMaterialDescription() {
		return materialDescription;
	}
	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getSectionGrade() {
		return sectionGrade;
	}
	public void setSectionGrade(String sectionGrade) {
		this.sectionGrade = sectionGrade;
	}
	public BigDecimal getKgPerMeter() {
		return kgPerMeter;
	}
	public void setKgPerMeter(BigDecimal kgPerMeter) {
		this.kgPerMeter = kgPerMeter;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	
	public String getOtherLength() {
		return otherLength;
	}
	public void setOtherLength(String otherLength) {
		this.otherLength = otherLength;
	}
	public Boolean getContainer() {
		return container;
	}
	public void setContainer(Boolean container) {
		this.container = container;
	}
	public List<String> getPlant() {
		return plant;
	}
	public void setPlant(List<String> plant) {
		this.plant = plant;
	}
	@Override
	public String toString() {
		return "SearchHeaderDto [materialDescription=" + materialDescription + ", material=" + material + ", standard="
				+ standard + ", size=" + size + ", sectionGrade=" + sectionGrade + ", kgPerMeter=" + kgPerMeter
				+ ", length=" + length + ", otherLength=" + otherLength + ", container=" + container + ", plant="
				+ plant + "]";
	}

	
	
	
}
