package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.NotificationDetail;
import com.incture.cherrywork.entities.SalesOrderHeader;

@Repository
public interface INotificationDetailRepository extends JpaRepository<NotificationDetail, String>,
		QuerydslPredicateExecutor<NotificationDetail>, INotificationDetailCustomRepository {

}
