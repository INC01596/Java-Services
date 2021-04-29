package com.incture.cherrywork.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NOTIFICATION_TYPE")
public class NotificationType implements SalesOrderBase, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public Object getPrimaryKey() {
		return notificationTypeId;
	}

	@Id
	@Column(name = "NOTIFICATION_TYPE_ID", length = 5, nullable = false)
	private String notificationTypeId;
	
	@Column(name = "NOTIFICATION_TYPE", length = 25, nullable = false)
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
		return "NotificationTypeDo [notificationTypeId=" + notificationTypeId + ", notificationType=" + notificationType
				+ "]";
	}
}
