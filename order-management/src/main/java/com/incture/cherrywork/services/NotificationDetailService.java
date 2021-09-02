package com.incture.cherrywork.services;

import java.util.Date;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.NotificationDetailDto;
import com.incture.cherrywork.dtos.NotificationListDto;
import com.incture.cherrywork.dtos.NotificationTextDto;
import com.incture.cherrywork.entities.NotificationDetail;
import com.incture.cherrywork.entities.NotificationText;

//import com.incture.cherrywork.notification.WebsocketDto;
//import com.incture.cherrywork.notificationWebsocket.NotificationWebSocket;
//import com.incture.cherrywork.notificationWebsocket.NotificationWebsocketDto;
import com.incture.cherrywork.repositories.INotificationDetailRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.repositories.ServicesUtils;

@Service("NotificationDetailService")
@Transactional
public class NotificationDetailService {

	@Autowired
	private INotificationDetailRepository notificationDetailRepository;

	@PersistenceContext
	private EntityManager entityManager;

	public NotificationListDto getNotification(String userId) {
		return notificationDetailRepository.getNotification(userId);
	}

	public void markSeen(String notificationId) {
		notificationDetailRepository.markSeen(notificationId);
	}

	public void markAllSeen(String userId) {
		notificationDetailRepository.markAllSeen(userId);
	}

	public void saveNotification(String email, String soldToParty, String s4DocumentId, String source, String target,
			String notificationTypeId, String triggerPoint, Boolean emailRequired) {
		saveNotification1(email, soldToParty, s4DocumentId, source, target, notificationTypeId, triggerPoint,
				emailRequired);
		// callWebsocket(email.toLowerCase());
	}

	public void clearNotification(String userId) {
		notificationDetailRepository.clearNotification(userId);
	}

	@SuppressWarnings("null")
	public void saveNotification1(String email, String soldToParty, String s4DocumentId, String source, String target,
			String notificationTypeId, String triggerPoint, Boolean emailRequired) {
		// logger.debug("[NotificationDetailDao][saveNotification] Started");
		System.out
				.println("[NotificationDetailDao][saveNotification] Started with email: " + email + " source: " + source
						+ " target: "+target + " notificationTypeId: " + notificationTypeId + " triggerPoint:" + triggerPoint);
		// Session session = sessionFactory.openSession();
		// Transaction tx = null;
		NotificationTextDto notificationTextDto = new NotificationTextDto();
		NotificationText notificationText = new NotificationText();
		NotificationDetailDto notificationDetailDto = new NotificationDetailDto();
		try {
			// tx = session.beginTransaction();
			String queryString = "from NotificationText n where n.source=:source and n.target=:target "
					+ "and n.notificationTypeId=:notificationTypeId and n.triggerPoint=:triggerPoint";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("source", source);
			query.setParameter("target", target);
			query.setParameter("notificationTypeId", notificationTypeId);
			query.setParameter("triggerPoint", triggerPoint);
			notificationText = (NotificationText) query.getSingleResult();
			notificationTextDto = ObjectMapperUtils.map(notificationText, NotificationTextDto.class);
			String message = "";
			String title = notificationTextDto.getTitle();
			if (notificationTextDto.getTriggerPoint().equals("Start")) {
				if (!notificationTextDto.getSource().equals("01"))
					message = notificationTextDto.getNotificationText() + "User" + "(" + soldToParty + ").";
				else
					message = notificationTextDto.getNotificationText();
			}

			if (notificationTextDto.getTriggerPoint().equals("Created")) {
				message = notificationTextDto.getNotificationText()+ "with id "+s4DocumentId+" for (" + soldToParty + ")";
			}

			if (notificationTextDto.getTriggerPoint().equals("Acknowledge")) {
				message = notificationTextDto.getNotificationText()+ "<Order Id> "+s4DocumentId;
			}

			notificationDetailDto.setNotificationId(UUID.randomUUID().toString().replaceAll("-", ""));
			notificationDetailDto.setCreatedAt(new Date());
			notificationDetailDto.setNotificationText(message);
			notificationDetailDto.setNotificationTitle(title);
			notificationDetailDto.setUnread(true);
			notificationDetailDto.setUserId(email.toLowerCase());
			notificationDetailRepository.save(ObjectMapperUtils.map(notificationDetailDto, NotificationDetail.class));

			if (emailRequired == true)
				ServicesUtils.mail(email.toLowerCase(), "User", title, message);
		} catch (Exception e) {

			// logger.error("[NotificationDetailDao][saveNotification] Exception
			// : " + e.getMessage());
			System.out.println("[NotificationDetailDao][saveNotification] Exception : " + e.getMessage());
			e.printStackTrace();
		}
	}

	// public void webSocketCall(String userId){
	// callWebsocket(userId);
	// }

	// public void callWebsocket(String userId){
	// NotificationListDto notificationListDto = new NotificationListDto();
	// notificationListDto =
	// notificationDetailRepository.getNotification(userId);
	// WebsocketDto websocketDto = new WebsocketDto();
	// websocketDto.setUser(userId);
	// websocketDto.setAlert(true);
	// websocketDto.setNotification(notificationListDto);
	// NotificationWebSocket webSocket = new NotificationWebSocket();
	// webSocket.getResponse(websocketDto);
	// }

}
