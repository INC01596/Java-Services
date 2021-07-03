package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.incture.cherrywork.entities.ReturnItem;

import com.incture.cherrywork.entities.ReturnItemPrimaryKey;

public interface IReturnRepository extends JpaRepository<ReturnItem, ReturnItemPrimaryKey>,
QuerydslPredicateExecutor<ReturnItem>
{
	
}
