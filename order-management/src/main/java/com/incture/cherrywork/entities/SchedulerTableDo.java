package com.incture.cherrywork.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "SCHEDULER_TABLE")
@Entity
public @Data class SchedulerTableDo implements BaseDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3919410725998743380L;

	@Id
	@Column(name = "ID", length = 100, nullable = false)
	private String id = UUID.randomUUID().toString();

	@Column(name = "LOGGEDMESSAGE", length = 100000)
	private String loggedMessage;

	@Column(name = "TIMESTAMP")
	private String timeStamp;
	
	//@Temporal(TemporalType.DATE)
	@Column(name = "IST_TIMESTAMP")
	private LocalDateTime istTimeStamp;

	public SchedulerTableDo(String id, String loggedMessage, String timeStamp, LocalDateTime istTimeStamp) {
		super();
		this.id = id;
		this.loggedMessage = loggedMessage;
		this.timeStamp = timeStamp;
		this.istTimeStamp = istTimeStamp;
	}

	public SchedulerTableDo() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getPrimaryKey() {
		return id;
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
