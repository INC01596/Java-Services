package com.incture.cherrywork.entities;



import java.util.List;



import lombok.Data;

public @Data class AttributeDetailsDto {

	private String attributeDetailsGuid;

	private String attributeId;

	private String domainCode;

	private String attributeDesc;

	private Boolean isInclusion;

	private String aliasName;

	private List<PermissionObjectDetailsDo> permissionObjectDetailsDos;

}

