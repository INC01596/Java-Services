package com.incture.cherrywork.dtos;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.repositories.EnOperation;
import com.incture.cherrywork.sales.constants.EnLookUp;


public class SalesOrderLookUpDto extends BaseDto {

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean getValidForUsage() {
		return Boolean.TRUE;
	}

	private String key;

	private String description;
	
	private String arabicText;

	private EnLookUp lookupType;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getArabicText() {
		return arabicText;
	}

	public void setArabicText(String arabicText) {
		this.arabicText = arabicText;
	}

	public EnLookUp getLookupType() {
		return lookupType;
	}

	public void setLookupType(EnLookUp lookupType) {
		this.lookupType = lookupType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((arabicText == null) ? 0 : arabicText.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((lookupType == null) ? 0 : lookupType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalesOrderLookUpDto other = (SalesOrderLookUpDto) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (arabicText == null) {
			if (other.arabicText != null)
				return false;
		} else if (!arabicText.equals(other.arabicText))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (lookupType != other.lookupType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LookUpDto [key=" + key + ", description=" + description + ", arabicText=" + arabicText + ", lookupType="
				+ lookupType + "]";
	}
}
