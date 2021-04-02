package com.incture.cherrywork.dtos;

import java.util.List;

public class SalesOrderDropDownSearchHeaderDto {

	private List<SalesOrderMaterialListDto> materialNumber;
	private List<SalesOrderStandardListDto> standard;
	private List<SalesOrderSizeListDto> size;
	private List<SalesOrderSectionGradeListDto> sectionGrade;
	private List<SalesOrderKgPerMeterListDto> kgPerMeter;
	private List<SalesOrderLengthListDto> length;

	public List<SalesOrderMaterialListDto> getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(List<SalesOrderMaterialListDto> materialNumber) {
		this.materialNumber = materialNumber;
	}

	public List<SalesOrderStandardListDto> getStandard() {
		return standard;
	}

	public void setStandard(List<SalesOrderStandardListDto> standard) {
		this.standard = standard;
	}

	public List<SalesOrderSizeListDto> getSize() {
		return size;
	}

	public void setSize(List<SalesOrderSizeListDto> size) {
		this.size = size;
	}

	public List<SalesOrderSectionGradeListDto> getSectionGrade() {
		return sectionGrade;
	}

	public void setSectionGrade(List<SalesOrderSectionGradeListDto> sectionGrade) {
		this.sectionGrade = sectionGrade;
	}

	public List<SalesOrderKgPerMeterListDto> getKgPerMeter() {
		return kgPerMeter;
	}

	public void setKgPerMeter(List<SalesOrderKgPerMeterListDto> kgPerMeter) {
		this.kgPerMeter = kgPerMeter;
	}

	public List<SalesOrderLengthListDto> getLength() {
		return length;
	}

	public void setLength(List<SalesOrderLengthListDto> length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "SalesOrderDropdownSearchHeaderDto [materialNumber=" + materialNumber + ", standard=" + standard + ", size=" + size
				+ ", sectionGrade=" + sectionGrade + ", kgPerMeter=" + kgPerMeter + ", length=" + length + "]";
	}

}
