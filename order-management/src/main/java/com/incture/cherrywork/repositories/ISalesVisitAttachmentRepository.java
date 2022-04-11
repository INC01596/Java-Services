package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.SalesVisitAttachment;

@Repository
public interface ISalesVisitAttachmentRepository extends JpaRepository<SalesVisitAttachment, String> {

}
