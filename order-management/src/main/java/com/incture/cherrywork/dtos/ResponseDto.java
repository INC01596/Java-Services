package com.incture.cherrywork.dtos;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseDto {
	private boolean status;
	private Integer statusCode;
	private String message;
	private Object data;
	private int totalCount;

}

