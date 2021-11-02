package com.incture.cherrywork.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefDto {

	private String application;
	private String process;
	private String entityName;
	@Override
	public String toString() {
		return "DefDto [application=" + application + ", process=" + process + ", entityName=" + entityName + "]";
	}
}
