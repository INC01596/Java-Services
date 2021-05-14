package com.incture.cherrywork.entities.workflow;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.incture.cherrywork.entities.BaseDo;

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

}
