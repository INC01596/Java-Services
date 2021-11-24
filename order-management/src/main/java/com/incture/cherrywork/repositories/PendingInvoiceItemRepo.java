package com.incture.cherrywork.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.PendingInvoiceItemDo;

@Repository
public interface PendingInvoiceItemRepo extends JpaRepository<PendingInvoiceItemDo,String> {

}
