package com.incture.cherrywork.dtos;



import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class DestinationDto {

	private String name;
	private String description;
	private String type;
	private String url;
	private String authentication;
	private String proxytype;
	private String tokenServiceURL;
	private String clientId;
	private String clientSecret;
	private String tokenServiceURLType;
	private String userName;
	private String password;

}
