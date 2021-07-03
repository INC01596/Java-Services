package com.incture.cherrywork.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "USER_PRIVILAGES_GUID")
@EqualsAndHashCode(callSuper = false)
public class UserPrivilagesDo extends CommonPostfix {

	@Id
	@Column(name = "USER_PRIVILAGES_GUID_NUM")
	private String userPrivilagesGuid = UUID.randomUUID().toString();

	@Column(name = "USER_GUID_NUM")
	private String userGuid;

	@Column(name = "DOMAIN_CODE")
	private String domainCode;

	@Column(name = "PERMISSION_OBJECT_GUID_NUM")
	private String permissionObjectGuid;

}
