package com.incture.cherrywork.dtos;

public class SalesOrderSectionGradeListDto {
	
	String sectionGrade;
	String sectionGradeDescription;
	public String getSectionGrade() {
		return sectionGrade;
	}
	public void setSectionGrade(String sectionGrade) {
		this.sectionGrade = sectionGrade;
	}
	public String getSectionGradeDescription() {
		return sectionGradeDescription;
	}
	public void setSectionGradeDescription(String sectionGradeDescription) {
		this.sectionGradeDescription = sectionGradeDescription;
	}
	
	@Override
	public String toString() {
		return "SalesOrderSectionGradeListDto [sectionGrade=" + sectionGrade + ", sectionGradeDescription="
				+ sectionGradeDescription + "]";
	}

}
