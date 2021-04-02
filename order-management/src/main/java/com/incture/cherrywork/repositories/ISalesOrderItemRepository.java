package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.incture.cherrywork.entities.SalesOrderItem;

public interface ISalesOrderItemRepository extends JpaRepository<SalesOrderItem, String>,
		QuerydslPredicateExecutor<SalesOrderItem>, ISalesOrderItemCustomRepository {
}
