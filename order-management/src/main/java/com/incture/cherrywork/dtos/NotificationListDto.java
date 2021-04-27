package com.incture.cherrywork.dtos;

import java.util.List;

import com.incture.cherrywork.dtos.NotificationDetailDto;

public class NotificationListDto {

	private List<NotificationDetailDto> notificationList;
	private int count;
	
	public List<NotificationDetailDto> getNotificationList() {
		return notificationList;
	}
	public void setNotificationList(List<NotificationDetailDto> notificationList) {
		this.notificationList = notificationList;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return "NotificationListDto [notificationList=" + notificationList + ", count=" + count + "]";
	}
}