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
@Table(name = "PERMISSION_OBJECT_DETAILS_GUID")
@EqualsAndHashCode(callSuper = false)
public class PermissionObjectDetailsDo extends CommonPostfix {

	@Id
	@Column(name = "PERMISSION_OBJECT_DETAILS_GUID_NUM")
	private String permissionObjectDetailsGuid = UUID.randomUUID().toString();

	@Column(name = "PERMISSION_OBJECT_GUID_NUM")
	private String permissionObjectGuid;

	@Column(name = "ATTRIBUTE_DETAILS_GUID_NUM")
	private String attributeDetailsGuid;

	@Column(name = "ATTRIBUTE_VALUE")
	private String attributeValue;

	@Column(name = "ATTRIBUTE_TEXT")
	private String attributeText;
}
