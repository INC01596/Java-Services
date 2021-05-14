package com.incture.cherrywork.dtos;

import lombok.Data;

@Data
public class SchedulerTimeDto {
	
	String startDate;
	String endDate;
	String startTime;
	String endTime;
	
	
	public SchedulerTimeDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SchedulerTimeDto(String startDate, String endDate, String startTime, String endTime) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
