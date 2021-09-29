package com.incture.cherrywork.dtos;



import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class HttpResponseDto {
	private String responseData;
	private Integer statuscode;
	private Map<String, String> headers;

}
