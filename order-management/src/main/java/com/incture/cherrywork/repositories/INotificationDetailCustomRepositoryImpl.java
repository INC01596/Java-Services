package com.incture.cherrywork.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dtos.NotificationDetailDto;
import com.incture.cherrywork.dtos.NotificationListDto;
import com.incture.cherrywork.entities.NotificationDetail;

@SuppressWarnings("unused")
@Transactional
@Repository
public class INotificationDetailCustomRepositoryImpl implements INotificationDetailCustomRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public NotificationListDto getNotification(String userId) {
		//logger.debug("[NotificationDetailDao][getNotification] Started");
		System.out.println("[NotificationDetailDao][getNotification] Started");
		NotificationListDto notificationList = new NotificationListDto();
		List<NotificationDetail> notificationListEntity = new ArrayList<>();
		List<NotificationDetailDto> notificationListDto = new ArrayList<>();
		try {
			String queryStringRead = "select n from NotificationDetail n where lower(n.userId)=:userId order by n.createdAt desc";
			Query query2 = entityManager.createQuery(queryStringRead);
			query2.setParameter("userId", userId.toLowerCase());
			notificationListEntity = query2.getResultList();
			for (NotificationDetail entity : notificationListEntity) {
				NotificationDetailDto dto = new NotificationDetailDto();
				dto = ObjectMapperUtils.map(entity, NotificationDetailDto.class);
				notificationListDto.add(dto);
			}
			notificationList.setNotificationList(notificationListDto);
			notificationList.setCount(UnseenNotificationCount(userId));
		} catch (Exception e) {
			//logger.error("[NotificationDetailDao][getNotification] Exception : " + e.getMessage());
			System.out.println("[NotificationDetailDao][getNotification] Exception : " + e.getMessage());
			e.printStackTrace();
		}
		return notificationList;
	}
	
	@SuppressWarnings("unchecked")
	public int UnseenNotificationCount(String userId) {
		//logger.debug("[NotificationDetailDao][UnseenNotificationCount] Started");
		System.out.println("[NotificationDetailDao][UnseenNotificationCount] Started");
		List<NotificationDetail> notificationListEntity = new ArrayList<>();
		int count = 0;
		try {
			String queryString = "select n from NotificationDetail n where n.userId=:userId and n.unread = true";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("userId", userId);
			notificationListEntity = query.getResultList();
			count = notificationListEntity.size();
		} catch (Exception e) {
			//logger.error("[NotificationDetailDao][UnseenNotificationCount] Exception : " + e.getMessage());
			System.out.println("[NotificationDetailDao][UnseenNotificationCount] Exception : " + e.getMessage());
			e.printStackTrace();
		}
		return count;
	}

	public void markSeen(String notificationId) {
		//logger.debug("[NotificationDetailDao][markSeen] Started");
		System.out.println("[NotificationDetailDao][markSeen] Started");
		try {
			String queryString = "update NotificationDetail n set n.unread = false where n.notificationId=:notificationId";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("notificationId", notificationId);
			query.executeUpdate();
		} catch (Exception e) {
			//logger.error("[NotificationDetailDao][markSeen] Exception : " + e.getMessage());
			System.out.println("[NotificationDetailDao][markSeen] Exception : " + e.getMessage());
			e.printStackTrace();
		}
	}

	
	public void markAllSeen(String userId) {
		//logger.debug("[NotificationDetailDao][markSeen] Started");
		System.out.println("[NotificationDetailDao][markSeen] Started");
		try {
			String queryString = "update NotificationDetail n set n.unread = false where n.userId=:userId and n.unread=true";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("userId", userId);
			query.executeUpdate();
		} catch (Exception e) {
			//logger.error("[NotificationDetailDao][markSeen] Exception : " + e.getMessage());
			System.out.println("[NotificationDetailDao][markSeen] Exception : " + e.getMessage());
			e.printStackTrace();
		}
	}


	public void clearNotification(String userId) {
		//logger.debug("[NotificationDetailDao][clearNotification] Started : " + userId);
		System.out.println("[NotificationDetailDao][clearNotification] Started : " + userId);
		try {
			String queryString = "delete from NotificationDetail n where n.userId=:userId";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("userId", userId);
			query.executeUpdate();
		} catch (Exception e) {
			//logger.error("[NotificationDetailDao][clearNotification] Exception : " + e.getMessage());
			System.out.println("[NotificationDetailDao][clearNotification] Exception : " + e.getMessage());
			e.printStackTrace();
		}
	}

}
