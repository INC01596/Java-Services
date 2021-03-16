package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.incture.cherrywork.entities.OutBoundDelivery;

public interface IOutBoundDeliveryRepository extends JpaRepository<OutBoundDelivery, String>,
QuerydslPredicateExecutor<OutBoundDelivery>{

}
