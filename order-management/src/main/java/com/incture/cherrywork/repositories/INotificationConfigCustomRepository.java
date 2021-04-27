package com.incture.cherrywork.repositories;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.NotificationConfigDto;
import com.incture.cherrywork.dtos.NotificationTypeDto;

@Repository
public interface INotificationConfigCustomRepository {
	
	ResponseEntity<Object> getNofiticationAlert(String userId);
	List<NotificationTypeDto> getNotificationType();
	boolean checkAlertForUser(String userId, String notificationTypeId);

}
