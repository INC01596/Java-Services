package com.incture.cherrywork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.PendingInvoiceDo;

@Repository
public interface PendingInvoiceRepo extends JpaRepository<PendingInvoiceDo,String> {

}
