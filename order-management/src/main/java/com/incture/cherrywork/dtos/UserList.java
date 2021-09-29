package com.incture.cherrywork.dtos;



import java.util.List;

import lombok.Data;

public @Data class UserList {

	private String itemsPerPage;
	private String totalResults;
	private String startIndex;

	private List<UserInfo> Resources;

}
