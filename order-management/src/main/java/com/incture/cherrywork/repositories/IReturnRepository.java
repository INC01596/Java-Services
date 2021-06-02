package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


import com.incture.cherrywork.entities.ReturnItemDo;
import com.incture.cherrywork.entities.ReturnItemPrimaryKey;

public interface IReturnRepository extends JpaRepository<ReturnItemDo, ReturnItemPrimaryKey>,
QuerydslPredicateExecutor<ReturnItemDo>
{
	
}
