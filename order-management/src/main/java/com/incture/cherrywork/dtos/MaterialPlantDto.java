package com.incture.cherrywork.dtos;


public class MaterialPlantDto {

	private String materialDescription;
	
	private String plant;
	private String itemNumber;

	
	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getMaterialDescription() {
		return materialDescription;
	}

	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	@Override
	public String toString() {
		return "MaterialPlantDto [materialDescription=" + materialDescription + ", plant=" + plant + ", itemNumber="
				+ itemNumber + "]";
	}
}


