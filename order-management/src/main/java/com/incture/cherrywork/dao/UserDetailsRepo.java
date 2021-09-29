package com.incture.cherrywork.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.UserDetailsDo;






@Repository
public interface UserDetailsRepo  extends JpaRepository<UserDetailsDo, String>{
	
	List<UserDetailsDo> findByUserIdAndApplicationId(String userId,String applicationId);
	
	
	// Each user  will have different permission object but a single personalisation Object for the applicationTab
	 @Query("SELECT DISTINCT u.variantId FROM UserDetailsDo u WHERE u.userId=?1 AND u.applicationId=?2")
	  String findDistinctVariantId(String userId,String applicationId);
	
}
