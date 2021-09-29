package com.incture.cherrywork.dtos;



import java.util.List;

import lombok.Data;

@Data
public class UserDto {
	private String userName;
	private List<String> emails;
	private String name;
	private String id;
}
