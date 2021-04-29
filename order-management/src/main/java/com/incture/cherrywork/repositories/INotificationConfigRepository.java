package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.incture.cherrywork.entities.NotificationConfig;
import com.incture.cherrywork.entities.NotificationDetail;
import com.incture.cherrywork.entities.SalesOrderHeader;

public interface INotificationConfigRepository extends JpaRepository<NotificationConfig, String>,
		QuerydslPredicateExecutor<NotificationConfig>, INotificationConfigCustomRepository {

}
