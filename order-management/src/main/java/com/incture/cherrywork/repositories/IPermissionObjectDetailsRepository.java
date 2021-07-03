package com.incture.cherrywork.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incture.cherrywork.entities.PermissionObjectDetailsDo;

public interface IPermissionObjectDetailsRepository extends JpaRepository<PermissionObjectDetailsDo, String> {

	List<PermissionObjectDetailsDo> findByPermissionObjectGuidIn(Set<String> permissionObjectGuidList);
}
