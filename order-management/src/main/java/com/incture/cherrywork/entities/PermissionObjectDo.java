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
@Table(name = "PERMISSION_OBJECT_GUID")
@EqualsAndHashCode(callSuper = false)
public class PermissionObjectDo extends CommonPostfix {

	@Id
	@Column(name = "PERMISSION_OBJECT_GUID_NUM")
	private String permissionObjectGuid = UUID.randomUUID().toString();

	@Column(name = "DOMAIN_CODE")
	private String domainCode;

	@Column(name = "PERMISSION_OBJECT_TEXT")
	private String permissionObjectText;

}
