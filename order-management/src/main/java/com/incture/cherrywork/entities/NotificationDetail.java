package com.incture.cherrywork.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "NOTIFICATION_DETAIL")
public class NotificationDetail implements SalesOrderBase, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	public Object getPrimaryKey() {
		return notificationId;
	}
	
	@Id
	@Column(name = "NOTIFICATION_ID", length = 32, nullable = false)
	private String notificationId;
	
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "CREATED_AT", nullable = false)
	private Date createdAt;
	
	@Column(name = "NOTIFICATION_TEXT", length = 500)
	private String notificationText;
	
	@Column(name = "NOTIFICATION_TITLE", length = 100)
	private String notificationTitle;
	
	@Column(name = "UNREAD")
	private Boolean unread;
	
	@Column(name = "USER_ID", length = 50)
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
		return "NotificationDetailDo [notificationId=" + notificationId + ", createdAt=" + createdAt
				+ ", notificationText=" + notificationText + ", notificationTitle=" + notificationTitle + ", unread="
				+ unread + ", userId=" + userId + "]";
	}
}
