package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.incture.cherrywork.entities.OutBoundDeliveryItem;

public interface IOutBoundDeliveryItemRepository extends JpaRepository<OutBoundDeliveryItem, String>,
QuerydslPredicateExecutor<OutBoundDeliveryItem>{

}
