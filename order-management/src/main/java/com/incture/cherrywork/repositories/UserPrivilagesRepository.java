package com.incture.cherrywork.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.UserPrivilagesDo;


@Repository
public interface UserPrivilagesRepository extends JpaRepository<UserPrivilagesDo, String> {

	List<UserPrivilagesDo> findByUserGuidAndDomainCode(String userGuid, String domainCode);

	UserPrivilagesDo findByUserGuidAndDomainCodeAndPermissionObjectGuid(String userGuid, String domainCode,
			String permissionObjectGuid);

	List<UserPrivilagesDo> findByUserGuidAndDomainCodeAndPermissionObjectGuidIn(String userGuid, String domainCode,
			List<String> permissionObjectGuidList);

	List<UserPrivilagesDo> findByUserGuid(String userGuid);

	List<UserPrivilagesDo> findByPermissionObjectGuidIn(List<String> permissionObjectGuidList);

	List<UserPrivilagesDo> findByPermissionObjectGuid(String permissionObjectGuid);

}
