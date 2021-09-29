package com.incture.cherrywork.dtos;



import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RecipientDto {

	private String emailDefinitionId;
	private String recipientType;
	private String userEmail;

}
