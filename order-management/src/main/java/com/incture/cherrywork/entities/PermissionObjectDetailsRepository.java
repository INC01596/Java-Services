package com.incture.cherrywork.entities;



import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PermissionObjectDetailsRepository extends JpaRepository<PermissionObjectDetailsDo, String> {

	List<PermissionObjectDetailsDo> findByPermissionObjectGuidIn(Set<String> permissionObjectGuidList);

	List<PermissionObjectDetailsDo> findByPermissionObjectGuid(String permissionObjectGuid);

}
