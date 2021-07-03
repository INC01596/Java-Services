package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


import com.incture.cherrywork.entities.UsersDetailDo;

public interface IUsersDetailRepository extends JpaRepository<UsersDetailDo, String>, QuerydslPredicateExecutor<UsersDetailDo>{
	
	UsersDetailDo findByUserId(String userId);
}
