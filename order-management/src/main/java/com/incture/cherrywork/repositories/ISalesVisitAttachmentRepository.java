package com.incture.cherrywork.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.SalesVisitAttachment;

@Repository
public interface ISalesVisitAttachmentRepository extends JpaRepository<SalesVisitAttachment, String> {

	public SalesVisitAttachment findByAttachmentId(String attachId);

	@Query(value = "from SalesVisitAttachment where visitId=?1")
	public List<SalesVisitAttachment> findByVisitId(String visitId);

	@Modifying
	@Query(value = "delete SalesVisitAttachment where visitId=?1")
	public void deleteDocsByVisitId(String visitId);
}
