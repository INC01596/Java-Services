package com.incture.cherrywork.dtos;

public class ResponseDtoNew {
	
	private Object data;
	private String message;
	private Integer statusCode;
	private String status;
	private int totalCount;
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public ResponseDtoNew() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResponseDtoNew(Object data, String message, Integer statusCode, String status, int totalCount) {
		super();
		this.data = data;
		this.message = message;
		this.statusCode = statusCode;
		this.status = status;
		this.totalCount = totalCount;
	}
	@Override
	public String toString() {
		return "ResponseDtoNew [data=" + data + ", message=" + message + ", statusCode=" + statusCode + ", status="
				+ status + ", totalCount=" + totalCount + "]";
	}
	
	

}
