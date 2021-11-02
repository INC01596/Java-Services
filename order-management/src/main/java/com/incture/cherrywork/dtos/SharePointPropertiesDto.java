package com.incture.cherrywork.dtos;


import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("sharepoint")
public class SharePointPropertiesDto {

	private String username;
	private String password;
	private String endpointToken;
	private String endpointDomain;

}
