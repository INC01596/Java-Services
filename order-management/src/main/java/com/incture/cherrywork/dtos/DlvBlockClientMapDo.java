package com.incture.cherrywork.dtos;

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
@Table(name = "DLV_BLOCK_CLIENT_MAP")
public @Data class DlvBlockClientMapDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CLIENT_MAP_KEY")
	private String clientKey = UUID.randomUUID().toString();

	@Column(name = "RELEASE_MAP_ID")
	private String releaseMapId;

	@Column(name = "CLIENT_ID", length = 20)
	private String clientId;

	@Override
	public Object getPrimaryKey() {
		return clientKey;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public String getReleaseMapId() {
		return releaseMapId;
	}

	public void setReleaseMapId(String releaseMapId) {
		this.releaseMapId = releaseMapId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}

