package com.incture.cherrywork.repositories;



import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.PermissionObjectDo;


@Repository
public interface PermissionObjectsRepository extends JpaRepository<PermissionObjectDo, String> {

	List<PermissionObjectDo> findByPermissionObjectGuidInOrderByCreatedAtDesc(List<String> permissionObjectGuidList);

	List<PermissionObjectDo> findByPermissionObjectGuidIn(List<String> permissionObjectGuidList);

	List<PermissionObjectDo> findByPermissionObjectGuidNotIn(List<String> permissionObjectGuidList, Sort sort);

	PermissionObjectDo findByPermissionObjectText(String permissionObjectText);

	List<PermissionObjectDo> findByDomainCode(String domainCode);

}

