package com.incture.cherrywork.dtos;



import java.util.List;

import com.incture.cherrywork.entities.PermissionObjectDetailsDo;

import lombok.Data;

public @Data class PermissionObjectDto {

	private String permissionObjectGuid;

	private String domainCode;

	private String permissionObjectText;

	private List<PermissionObjectDetailsDo> listOfPermissionObjectDetails;

}
