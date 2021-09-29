package com.incture.cherrywork.dtos;



import lombok.Data;

public @Data class PermissionObjectWithDomainTextDto {

	private String permissionObjectGuid;

	private String permissionObjectText;

	private String domainCode;

	private String domainText;

	private String languageKey;

}
