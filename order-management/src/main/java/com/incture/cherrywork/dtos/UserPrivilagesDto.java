package com.incture.cherrywork.dtos;



import java.util.List;

import lombok.Data;

public @Data class UserPrivilagesDto {

	private String userId;

	private String domainId = "cc";

	private List<String> permissionObjectGuid;

}

