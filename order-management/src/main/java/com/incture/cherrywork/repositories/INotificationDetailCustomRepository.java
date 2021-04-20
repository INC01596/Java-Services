package com.incture.cherrywork.repositories;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.incture.cherrywork.dtos.NotificationListDto;

@Repository
public interface INotificationDetailCustomRepository {
	NotificationListDto getNotification(String userId);
	void markSeen(String notificationId);
	void markAllSeen(String userId);
	void clearNotification(String userId);

}
