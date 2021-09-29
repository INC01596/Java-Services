package com.incture.cherrywork.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;


import com.incture.cherrywork.dtos.NotificationConfigDto;
import com.incture.cherrywork.entities.NotificationConfig;
import com.incture.cherrywork.entities.NotificationType;
import com.incture.cherrywork.dtos.NotificationTypeDto;


@SuppressWarnings("unused")
@Transactional
@Repository
public class INotificationConfigCustomRepositoryImpl implements INotificationConfigCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public ResponseEntity<Object> getNofiticationAlert(String userId) {
		System.out.println("[NotificationConfigRepositoryImpl][getNofiticationAlert] Started for User Id : " + userId);
		// logger.debug("[NotificationConfigDao][getNofiticationAlert] Started
		// for User Id : "+userId);
		List<NotificationConfigDto> notificationConfigDtos = new ArrayList<>();
		List<NotificationConfig> notificationConfigDos = new ArrayList<>();
		try {
			String query = "select n from NotificationConfig n where n.emailId=:userId";
			Query q = entityManager.createQuery(query);
			q.setParameter("userId", userId);
			notificationConfigDos = q.getResultList();
			for (NotificationConfig configDo : notificationConfigDos) {
				NotificationConfigDto configDto = new NotificationConfigDto();
				configDto = ObjectMapperUtils.map(configDo, NotificationConfigDto.class);
				notificationConfigDtos.add(configDto);
			}
			
			
		} catch (Exception e) {
			//logger.error("[NotificationConfigDao][getNofiticationAlert] Exception : " + e.getMessage());
			System.out.println("[NotificationConfigDao][getNofiticationAlert] Exception : " + e.getMessage());
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("message", "Error Fetching Notification Config").body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).header("message", "Notification Config Fetched Successfully")
				.body(notificationConfigDtos);
	}

	@SuppressWarnings("unchecked")
	public List<NotificationTypeDto> getNotificationType(){
		//logger.debug("[NotificationTypeDao][getNotificationType] Started");
		System.out.println("[NotificationTypeDao][getNotificationType] Started");
		List<NotificationTypeDto> notificationTypeDtos = new ArrayList<>();
		List<NotificationType> notificationTypeDos = new ArrayList<>();
		try{
			String query = "from NotificationType n";
			Query q = entityManager.createQuery(query);
			notificationTypeDos = q.getResultList();
			for(NotificationType typeDo : notificationTypeDos){
				NotificationTypeDto typeDto = new NotificationTypeDto();
				typeDto = ObjectMapperUtils.map(typeDo, NotificationTypeDto.class);
				notificationTypeDtos.add(typeDto);
			}
		}catch(Exception e){
			//logger.debug("[NotificationTypeDao][getNotificationType] Exception : "+e.getMessage());
			System.out.println("[NotificationTypeDao][getNotificationType] Exception : "+e.getMessage());
			e.printStackTrace();
		}
		return notificationTypeDtos;
	}
	
	@SuppressWarnings({ "unchecked" })
	public boolean checkAlertForUser(String userId, String notificationTypeId){
		boolean response = false;
		//logger.debug("[NotificationConfigDao][checkAlertForUser] Started for User Id : "+userId+" and Type : "+notificationTypeId);
		System.out.println("[NotificationConfigRepositoryImpl][checkAlertForUser] Started for User Id : "+userId+" and Type : "+notificationTypeId);
		NotificationConfigDto configDto = null;
		List<NotificationConfig> notificationConfigDos = new ArrayList<>();
		try{
			String query = "from NotificationConfig n where lower(n.emailId)=:userId and n.notificationTypeId=:notificationTypeId";
			Query q = entityManager.createQuery(query);
			q.setParameter("userId", userId.toLowerCase());
			q.setParameter("notificationTypeId", notificationTypeId);
			notificationConfigDos = q.getResultList();
			if(notificationConfigDos.size()>0)
				configDto = ObjectMapperUtils.map(notificationConfigDos.get(0), NotificationConfigDto.class);
			if((configDto != null)&&(configDto.getAlert()==true))
				response = true;
		}catch(Exception e){
			//logger.error("[NotificationConfigDao][checkAlertForUser] Exception : "+e.getMessage());
			System.out.println("[NotificationConfigDao][checkAlertForUser] Exception : "+e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	
}
