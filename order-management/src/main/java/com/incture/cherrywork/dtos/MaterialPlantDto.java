package com.incture.cherrywork.dtos;


public class MaterialPlantDto {

	private String materialDescription;
	
	private String plant;

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
		return "MaterialPlantDto [materialDescription=" + materialDescription + ", plant=" + plant + "]";
	}
}


