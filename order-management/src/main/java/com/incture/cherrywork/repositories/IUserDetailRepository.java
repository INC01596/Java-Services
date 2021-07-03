package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.UserDetail;


@Repository
public interface IUserDetailRepository
		extends JpaRepository<UserDetail, String>, QuerydslPredicateExecutor<UserDetail>, IUserDetailCustomRepository {
	
	

}
