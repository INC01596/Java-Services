package com.incture.cherrywork.dtos;

import java.time.LocalDateTime;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = false)
public @Data class SchedulerTableDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6817163152358352346L;

	private String id;

	private String loggedMessage;

	private String timeStamp;
	
	private LocalDateTime istTimeStamp;
	
	private String indianTime;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoggedMessage() {
		return loggedMessage;
	}

	public void setLoggedMessage(String loggedMessage) {
		this.loggedMessage = loggedMessage;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public LocalDateTime getIstTimeStamp() {
		return istTimeStamp;
	}

	public void setIstTimeStamp(LocalDateTime istTimeStamp) {
		this.istTimeStamp = istTimeStamp;
	}

	public String getIndianTime() {
		return indianTime;
	}

	public void setIndianTime(String indianTime) {
		this.indianTime = indianTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SchedulerTableDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SchedulerTableDto(String loggedMessage, String timeStamp) {
		this.loggedMessage = loggedMessage;
		this.timeStamp = timeStamp;
	}

	public SchedulerTableDto(String loggedMessage, String timeStamp,LocalDateTime istTimeStamp ) {
		this.loggedMessage = loggedMessage;
		this.timeStamp = timeStamp;
		this.istTimeStamp=istTimeStamp;
	}

	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {

	}

}