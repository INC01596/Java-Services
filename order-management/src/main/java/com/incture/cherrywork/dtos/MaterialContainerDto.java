package com.incture.cherrywork.dtos;
//<-----------------------------------------Sandeep Kumar----------------------->



import java.util.List;

public class MaterialContainerDto {

	private String materialDescription;
	
	private boolean container;
	
	private List<String> plant;

	public String getMaterialDescription() {
		return materialDescription;
	}

	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}

	public boolean getContainer() {
		return container;
	}

	public void setContainer(boolean container) {
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
		return "MaterialContainerDto [materialDescription=" + materialDescription + ", container=" + container
				+ ", plant=" + plant + "]";
	}
}

