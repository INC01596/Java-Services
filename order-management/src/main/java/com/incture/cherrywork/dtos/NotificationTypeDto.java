package com.incture.cherrywork.dtos;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

public class NotificationTypeDto extends BaseDto  {

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
	}

	@Override
	public Boolean getValidForUsage() {
		return Boolean.TRUE;
	}
	
	private String notificationTypeId;
	private String notificationType;
	public String getNotificationTypeId() {
		return notificationTypeId;
	}

	public void setNotificationTypeId(String notificationTypeId) {
		this.notificationTypeId = notificationTypeId;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	@Override
	public String toString() {
		return "NotificationTypeDto [notificationTypeId=" + notificationTypeId + ", notificationType="
				+ notificationType + "]";
	}
}
