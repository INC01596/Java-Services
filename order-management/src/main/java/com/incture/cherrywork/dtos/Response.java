package com.incture.cherrywork.dtos;

import org.springframework.http.HttpStatus;

import com.incture.cherrywork.sales.constants.ResponseStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {

	private Object data;
	private HttpStatus statusCode;
	private String message;
	private ResponseStatus status;
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public HttpStatus getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}
	public Response(Object data, HttpStatus statusCode, String string, ResponseStatus failed) {
		super();
		this.data = data;
		this.statusCode = statusCode;
		this.message = string;
		this.status = failed;
	}
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ResponseStatus getStatus() {
		return status;
	}
	public void setStatus(ResponseStatus status) {
		this.status = status;
	}
	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}

}

