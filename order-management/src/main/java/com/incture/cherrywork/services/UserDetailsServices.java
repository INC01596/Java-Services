package com.incture.cherrywork.services;



import java.util.List;

import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.entities.UserDetailsDo;
import com.incture.cherrywork.entities.UsersDetailDo;




public interface UserDetailsServices {

	ResponseEntity<?> findById(String id);

	

	ResponseEntity<?> findAllidpUsersAndSyncWithHana();

	ResponseEntity<?> saveOrUpdateList(List<UsersDetailDo> model);

	ResponseEntity<?> deleteById(String id);

	ResponseEntity<?> getAll();

	ResponseEntity<?> findPermissionObjectAndAttributeDetails(String userId, String domainCode);

	ResponseEntity<?> saveAllUsersFromIdp();

	ResponseEntity<?> findAllPermissionObjects(String userPid);

	ResponseEntity<?> deleteByUserPid(String userPid);

	ResponseEntity<?> updateUser(UsersDetailDo model);

	ResponseEntity<?> findAllRightsForUserInDomain(String userPid, String domainCode);

	ResponseEntity<?> downloadExcelForAllUsersRightsInDomain(String domainCode);

	ResponseEntity<?> findAllAvailablePermObjects(String userPid);

	ResponseEntity<Object> create(UsersDetailDo dos);



	ResponseEntity<?> saveOrUpdate(UsersDetailDo model);



	ResponseEntity<?> listAll();

}
