package com.incture.cherrywork.dtos;

import java.util.Date;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;


public class NotificationDetailDto extends BaseDto {

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
	}

	@Override
	public Boolean getValidForUsage() {
		return Boolean.TRUE;
	}
	
	private String notificationId;
	private Date createdAt;
	private String notificationText;
	private String notificationTitle;
	private Boolean unread;
	private String userId;
	
	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getNotificationText() {
		return notificationText;
	}

	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
	}

	public String getNotificationTitle() {
		return notificationTitle;
	}

	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
	}

	public Boolean getUnread() {
		return unread;
	}

	public void setUnread(Boolean unread) {
		this.unread = unread;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "NotificationDetailDto [notificationId=" + notificationId + ", createdAt=" + createdAt
				+ ", notificationText=" + notificationText + ", notificationTitle=" + notificationTitle + ", unread="
				+ unread + ", userId=" + userId + "]";
	}
}
