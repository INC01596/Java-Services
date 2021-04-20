package com.incture.cherrywork.dtos;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

public class NotificationConfigDto extends BaseDto {

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
	}

	@Override
	public Boolean getValidForUsage() {
		return Boolean.TRUE;
	}
	
	private Integer sNo;
	private String emailId;
	private String notificationTypeId;
	private Boolean alert;
	public Integer getsNo() {
		return sNo;
	}

	public void setsNo(Integer sNo) {
		this.sNo = sNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getNotificationTypeId() {
		return notificationTypeId;
	}

	public void setNotificationTypeId(String notificationTypeId) {
		this.notificationTypeId = notificationTypeId;
	}

	public Boolean getAlert() {
		return alert;
	}

	public void setAlert(Boolean alert) {
		this.alert = alert;
	}

	@Override
	public String toString() {
		return "NotificationConfigDto [sNo=" + sNo + ", emailId=" + emailId + ", notificationTypeId="
				+ notificationTypeId + ", alert=" + alert + "]";
	}
}
