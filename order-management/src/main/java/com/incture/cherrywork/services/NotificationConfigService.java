package com.incture.cherrywork.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.NotificationConfigDto;
import com.incture.cherrywork.entities.NotificationConfig;
import com.incture.cherrywork.repositories.INotificationConfigRepository;
import com.incture.cherrywork.dtos.NotificationTypeDto;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.util.ServicesUtil;

@Service("NotificationConfigService")
@Transactional
public class NotificationConfigService {

	@Autowired
	private INotificationConfigRepository notificationConfigRepository;
	
	public ResponseEntity<Object> getNofiticationAlert(String userId){
		return notificationConfigRepository.getNofiticationAlert(userId);
	}
	
	public ResponseEntity<Object> setNotificationAlert(NotificationConfigDto configDto) {
		//Response response = new Response();
		//logger.debug("[NotificationConfigDao][setNotificationAlert] Started for data : " + configDto.toString());
		System.out.println("[NotificationConfigService][setNotificationAlert] Started for data : " + configDto.toString());
		try {
			if (!ServicesUtil.isEmpty(configDto))
				notificationConfigRepository.save(ObjectMapperUtils.map(configDto, NotificationConfig.class));
			
		} catch (Exception e) {
			//logger.error("[NotificationConfigDao][setNotificationAlert] Exception : " + e.getMessage());
			System.out.println("[NotificationConfigDao][setNotificationAlert] Exception : " + e.getMessage());
			e.printStackTrace();
			
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", "Error Fetching Enquiry")
			.body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).header("message", "Notification Config Set Successfully")
				.body(configDto);
	}

	@SuppressWarnings("unused")
	public ResponseEntity<Object> createNotificationAlert(String userId) {
		
		//logger.debug("[NotificationConfigDao][createNotificationAlert] Started for user : " + userId);
		System.out.println("[NotificationConfigService][createNotificationAlert] Started for user : " + userId);
		List<NotificationConfigDto> notificationConfigDtos = new ArrayList<>();
		NotificationConfigDto configDto = new NotificationConfigDto();
		List<NotificationTypeDto> notificationTypeDtos;
		try {
			notificationTypeDtos = notificationConfigRepository.getNotificationType();
			for (NotificationTypeDto typeDto : notificationTypeDtos) {
				configDto.setEmailId(userId);
				configDto.setNotificationTypeId(typeDto.getNotificationTypeId());
				configDto.setAlert(true);
				notificationConfigRepository.save(ObjectMapperUtils.map(configDto, NotificationConfig.class));
				notificationConfigDtos.add(configDto);
			}
			
		} catch (Exception e) {
			//logger.error("[NotificationConfigDao][createNotificationAlert] Exception : " + e.getMessage());
			System.out.println("[NotificationConfigDao][createNotificationAlert] Exception : " + e.getMessage());
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", "Error Creating Notification Config")
					.body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).header("message", "Created Notification Config for user : " + userId)
				.body(notificationConfigDtos);
	}

}
