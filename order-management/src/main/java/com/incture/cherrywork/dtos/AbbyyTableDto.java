package com.incture.cherrywork.dtos;



import java.time.LocalDateTime;

public class AbbyyTableDto {
	
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

	public AbbyyTableDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AbbyyTableDto(String id, String loggedMessage, String timeStamp, LocalDateTime istTimeStamp,
			String indianTime) {
		super();
		this.id = id;
		this.loggedMessage = loggedMessage;
		this.timeStamp = timeStamp;
		this.istTimeStamp = istTimeStamp;
		this.indianTime = indianTime;
	}

	@Override
	public String toString() {
		return "AbbyyTableDto [id=" + id + ", loggedMessage=" + loggedMessage + ", timeStamp=" + timeStamp
				+ ", istTimeStamp=" + istTimeStamp + ", indianTime=" + indianTime + "]";
	}
	
	
}
