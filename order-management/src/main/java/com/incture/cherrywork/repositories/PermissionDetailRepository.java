package com.incture.cherrywork.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.PermissionDetail;
import com.incture.cherrywork.entities.PermissionObject;



@Repository
public interface PermissionDetailRepository extends JpaRepository<PermissionDetail, Long> {

	List<PermissionDetail> findByPermissionId(PermissionObject permissionId);

	PermissionDetail findBySlNo(Long slNo);

	void deleteBySlNo(Long slNo);
	void deleteByPermissionId(PermissionObject permissionId);

	PermissionDetail findByPermissionIdAndAttributeIdAndAttributeValue(int permissionId, String attributeId, String attributeValue);
}

