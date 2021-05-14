package com.incture.cherrywork.util;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public @Data class ConnectClient {

	private String token;

	private List<String> cookies;
	
	
	

	public String getToken() {
		return token;
	}




	public void setToken(String token) {
		this.token = token;
	}




	public List<String> getCookies() {
		return cookies;
	}




	public void setCookies(List<String> cookies) {
		this.cookies = cookies;
	}




	public ConnectClient() {
		super();
		
	}




	public ConnectClient(String token, List<String> cookies) {
		super();
		this.token = token;
		this.cookies = cookies;
	}

}
