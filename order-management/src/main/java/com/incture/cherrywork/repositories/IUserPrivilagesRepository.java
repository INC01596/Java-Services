package com.incture.cherrywork.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incture.cherrywork.entities.UserPrivilagesDo;

public interface IUserPrivilagesRepository extends JpaRepository<UserPrivilagesDo, String>{
	
	

	List<UserPrivilagesDo> findByUserGuidAndDomainCode(String userGuid, String domainCode);
}
