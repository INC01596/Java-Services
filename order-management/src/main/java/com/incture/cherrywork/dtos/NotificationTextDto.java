package com.incture.cherrywork.dtos;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

public class NotificationTextDto extends BaseDto  {

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
	}

	@Override
	public Boolean getValidForUsage() {
		return Boolean.TRUE;
	}
	
	private Integer sNo;
	private Boolean emailRequired;
	private String notificationDesc;
	private String notificationText;
	private String notificationTypeId;
	private String source;
	private String target;
	private String title;
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
		return "NotificationTextDto [sNo=" + sNo + ", emailRequired=" + emailRequired + ", notificationDesc="
				+ notificationDesc + ", notificationText=" + notificationText + ", notificationTypeId="
				+ notificationTypeId + ", source=" + source + ", target=" + target + ", title=" + title
				+ ", triggerPoint=" + triggerPoint + "]";
	}
}
