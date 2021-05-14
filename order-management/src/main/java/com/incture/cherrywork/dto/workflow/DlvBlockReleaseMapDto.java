package com.incture.cherrywork.dto.workflow;

import java.util.List;

import com.incture.cherrywork.dtos.BaseDto;
import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public @Data class DlvBlockReleaseMapDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String releaseMapId;

	private String countryCode;

	private String dlvBlockCode;

	private Boolean specialClient = false;

	private Boolean headerLevel = false;

	private Boolean itemLevel = false;

	private Boolean display = false;

	private Boolean edit = false;
	
	private List<String> clientMapDtoList;
	
	

	public String getReleaseMapId() {
		return releaseMapId;
	}

	public void setReleaseMapId(String releaseMapId) {
		this.releaseMapId = releaseMapId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getDlvBlockCode() {
		return dlvBlockCode;
	}

	public void setDlvBlockCode(String dlvBlockCode) {
		this.dlvBlockCode = dlvBlockCode;
	}

	public Boolean getSpecialClient() {
		return specialClient;
	}

	public void setSpecialClient(Boolean specialClient) {
		this.specialClient = specialClient;
	}

	public Boolean getHeaderLevel() {
		return headerLevel;
	}

	public void setHeaderLevel(Boolean headerLevel) {
		this.headerLevel = headerLevel;
	}

	public Boolean getItemLevel() {
		return itemLevel;
	}

	public void setItemLevel(Boolean itemLevel) {
		this.itemLevel = itemLevel;
	}

	public Boolean getDisplay() {
		return display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

	public Boolean getEdit() {
		return edit;
	}

	public void setEdit(Boolean edit) {
		this.edit = edit;
	}

	public List<String> getClientMapDtoList() {
		return clientMapDtoList;
	}

	public void setClientMapDtoList(List<String> clientMapDtoList) {
		this.clientMapDtoList = clientMapDtoList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		throw new UnsupportedOperationException();
	}
}
