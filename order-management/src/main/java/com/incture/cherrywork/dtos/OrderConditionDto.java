package com.incture.cherrywork.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;



@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderConditionDto {
	
	private String refDoc;
	
	private String salesDocument;
	
	private String itemNumber;
	
	private String stepNumber;
	
	private String condCounter;
	
	private String condType;
	
	private String condRate;
	
	private String currency;
	
	private String condUnit;
	
	private String condPricingUnit;
	
	private String calculationType;
	
	private String condFlag;
	
	private String condUpdateFlag;
	
	private String plant;

	public String getRefDoc() {
		return refDoc;
	}

	public void setRefDoc(String refDoc) {
		this.refDoc = refDoc;
	}

	public String getSalesDocument() {
		return salesDocument;
	}

	public void setSalesDocument(String salesDocument) {
		this.salesDocument = salesDocument;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getStepNumber() {
		return stepNumber;
	}

	public void setStepNumber(String stepNumber) {
		this.stepNumber = stepNumber;
	}

	public String getCondCounter() {
		return condCounter;
	}

	public void setCondCounter(String condCounter) {
		this.condCounter = condCounter;
	}

	public String getCondType() {
		return condType;
	}

	public void setCondType(String condType) {
		this.condType = condType;
	}

	public String getCondRate() {
		return condRate;
	}

	public void setCondRate(String condRate) {
		this.condRate = condRate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCondUnit() {
		return condUnit;
	}

	public void setCondUnit(String condUnit) {
		this.condUnit = condUnit;
	}

	public String getCondPricingUnit() {
		return condPricingUnit;
	}

	public void setCondPricingUnit(String condPricingUnit) {
		this.condPricingUnit = condPricingUnit;
	}

	public String getCalculationType() {
		return calculationType;
	}

	public void setCalculationType(String calculationType) {
		this.calculationType = calculationType;
	}

	public String getCondFlag() {
		return condFlag;
	}

	public void setCondFlag(String condFlag) {
		this.condFlag = condFlag;
	}

	public String getCondUpdateFlag() {
		return condUpdateFlag;
	}

	public void setCondUpdateFlag(String condUpdateFlag) {
		this.condUpdateFlag = condUpdateFlag;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}
	
	

}
