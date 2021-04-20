package com.incture.cherrywork.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "NOTIFICATION_TEXT")
public class NotificationText implements SalesOrderBase, Serializable {

private static final long serialVersionUID = 1L;
	
	@Override
	public Object getPrimaryKey() {
		return sNo;
	}
	
	@Id
	@Column(name = "S_NO")
	private Integer sNo;
	
	@Column(name = "EMAIL_REQUIRED")
	private Boolean emailRequired;
	
	@Column(name = "NOTIFICATION_DESC", length = 200)
	private String notificationDesc;
	
	@Column(name = "NOTIFICATION_MESSAGE", length = 500)
	private String notificationText;
	
	@Column(name = "NOTIFICATION_TYPE_ID", length = 5)
	private String notificationTypeId;
	
	@Column(name = "SOURCE", length = 5)
	private String source;
	
	@Column(name = "TARGET", length = 5)
	private String target;
	
	@Column(name = "TITLE", length = 500)
	private String title;
	
	@Column(name = "TRIGGER_POINT", length = 25)
	private String triggerPoint;

	public Integer getsNo() {
		return sNo;
	}

	public void setsNo(Integer sNo) {
		this.sNo = sNo;
	}

	public Boolean getEmailRequired() {
		return emailRequired;
	}

	public void setEmailRequired(Boolean emailRequired) {
		this.emailRequired = emailRequired;
	}

	public String getNotificationDesc() {
		return notificationDesc;
	}

	public void setNotificationDesc(String notificationDesc) {
		this.notificationDesc = notificationDesc;
	}

	public String getNotificationText() {
		return notificationText;
	}

	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
	}

	public String getNotificationTypeId() {
		return notificationTypeId;
	}

	public void setNotificationTypeId(String notificationTypeId) {
		this.notificationTypeId = notificationTypeId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTriggerPoint() {
		return triggerPoint;
	}

	public void setTriggerPoint(String triggerPoint) {
		this.triggerPoint = triggerPoint;
	}

	@Override
	public String toString() {
		return "NotificationTextDo [sNo=" + sNo + ", emailRequired=" + emailRequired + ", notificationDesc="
				+ notificationDesc + ", notificationText=" + notificationText + ", notificationTypeId="
				+ notificationTypeId + ", source=" + source + ", target=" + target + ", title=" + title
				+ ", triggerPoint=" + triggerPoint + "]";
	}
}
