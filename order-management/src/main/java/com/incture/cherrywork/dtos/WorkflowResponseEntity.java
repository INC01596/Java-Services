package com.incture.cherrywork.dtos;

import java.util.Map;

import org.json.JSONArray;
import org.springframework.http.HttpStatus;

import com.incture.cherrywork.sales.constants.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkflowResponseEntity {
	
	private Object data;
	private int  responseStatusCode;
	private String message;
	private ResponseStatus status;
	private Map<String,Object> responseMessage;
	private JSONArray responseJson;
	private String id;
	private HttpStatus httpStatus;
	
	
	public Object getData() {
		return data;
	}
	public WorkflowResponseEntity(Object data, int responseStatusCode, String message, ResponseStatus status,
			Map<String, Object> responseMessage, JSONArray responseJson, String id) {
		super();
		this.data = data;
		this.responseStatusCode = responseStatusCode;
		this.message = message;
		this.status = status;
		this.responseMessage = responseMessage;
		this.responseJson = responseJson;
		this.id = id;
	}
	
	
	
	public WorkflowResponseEntity(int responseStatusCode, String message, ResponseStatus status,
			Map<String, Object> responseMessage) {
		super();
		this.responseStatusCode = responseStatusCode;
		this.message = message;
		this.status = status;
		this.responseMessage = responseMessage;
	}
	
	public WorkflowResponseEntity(String id, HttpStatus httpStatus, String message){
		this.id = id;
		this.httpStatus = httpStatus;
		this.message = message;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public int getResponseStatusCode() {
		return responseStatusCode;
	}
	public void setResponseStatusCode(int responseStatusCode) {
		this.responseStatusCode = responseStatusCode;
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
	public Map<String, Object> getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(Map<String, Object> responseMessage) {
		this.responseMessage = responseMessage;
	}
	public JSONArray getResponseJson() {
		return responseJson;
	}
	public void setResponseJson(JSONArray responseJson) {
		this.responseJson = responseJson;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "WorkflowResponseEntity [data=" + data + ", responseStatusCode=" + responseStatusCode + ", message="
				+ message + ", status=" + status + ", responseMessage=" + responseMessage + ", responseJson="
				+ responseJson + ", id=" + id + "]";
	}

}
