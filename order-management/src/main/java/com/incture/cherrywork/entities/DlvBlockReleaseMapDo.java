package com.incture.cherrywork.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DLV_BLOCK_RELEASE_MAP")
public @Data class DlvBlockReleaseMapDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RELEASE_MAP_ID")
	private String releaseMapId = UUID.randomUUID().toString();

	@Column(name = "COUNTRY_CODE", length = 4)
	private String countryCode;

	@Column(name = "DLV_BLOCK_CODE", length = 4)
	private String dlvBlockCode;

	@Column(name = "SPECIAL_CLIENT")
	private Boolean specialClient;

	@Column(name = "HEADER_LEVEL")
	private Boolean headerLevel;
	

	@Column(name = "ITEM_LEVEL")
	private Boolean itemLevel;

	@Column(name = "DISPLAY_STATUS")
	private Boolean display;

	@Column(name = "EDIT_STATUS")
	private Boolean edit;

	@Override
	public Object getPrimaryKey() {
		return releaseMapId;
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
