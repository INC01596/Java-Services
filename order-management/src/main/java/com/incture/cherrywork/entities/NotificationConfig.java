package com.incture.cherrywork.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NOTIFICATION_CONFIG")
public class NotificationConfig implements SalesOrderBase, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Object getPrimaryKey() {
		return sNo;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "S_NO")
	private Integer sNo;

	@Column(name = "EMAIL_ID", length = 50)
	private String emailId;

	@Column(name = "NOTIFICATION_TYPE_ID", length = 20)
	private String notificationTypeId;

	@Column(name = "ALERT")
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
		return "NotificationConfigDo [sNo=" + sNo + ", emailId=" + emailId + ", notificationTypeId="
				+ notificationTypeId + ", alert=" + alert + "]";
	}
}
