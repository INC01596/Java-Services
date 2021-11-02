package com.incture.cherrywork.repositories;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dao.BaseDao;
import com.incture.cherrywork.dtos.InvoiceDto;
import com.incture.cherrywork.dtos.StatusDto;
import com.incture.cherrywork.dtos.TransactionDto;
import com.incture.cherrywork.entities.InvoiceDo;
import com.incture.cherrywork.entities.StatusDo;
import com.incture.cherrywork.entities.TransactionDo;
import com.incture.cherrywork.util.ServicesUtil;

@Repository("TransactionDao")
public interface TransactionRepo extends JpaRepository<TransactionDo,String> {

	/*
	 * TransactionDo transactionDo; TransactionDto transactionDto;
	 */

	

	
	

	
	

}
