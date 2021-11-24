package com.incture.cherrywork.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.TransactionDo;



@Repository("TransactionDao")
public interface TransactionRepo extends JpaRepository<TransactionDo,String> {

	/*
	 * TransactionDo transactionDo; TransactionDto transactionDto;
	 */

	

	
	

	
	

}
