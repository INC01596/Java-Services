package com.incture.cherrywork.dtos;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class UserCustom {

	private List<UserAttributes> attributes;
}
