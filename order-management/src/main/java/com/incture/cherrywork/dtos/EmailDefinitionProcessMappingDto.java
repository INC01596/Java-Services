package com.incture.cherrywork.dtos;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmailDefinitionProcessMappingDto {

	private String emailDefinitionId;

	private String process;
	private String processDesc;
}
