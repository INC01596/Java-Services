package com.incture.cherrywork.dtos;

import java.util.List;

import lombok.Data;

public @Data class SmsSendingDto {

	private String mobileNum;
	private List<String> mobileNumList;
	private String message;
	private String messageType;
	private String from;
	private String report;
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public List<String> getMobileNumList() {
		return mobileNumList;
	}
	public void setMobileNumList(List<String> mobileNumList) {
		this.mobileNumList = mobileNumList;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	

}
