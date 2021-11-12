package com.incture.cherrywork.dao;

import java.util.List;

import com.incture.cherrywork.dtos.TransactionDto;



/**
 * @author Polireddy.M
 *
 */
public interface TransactionDaoLocal {

	String createTransaction(TransactionDto dto);

	void updateTransaction(TransactionDto dto);

	void deleteTransaction(TransactionDto dto);

	TransactionDto getTransactionById(String transactionId);

	List<TransactionDto> getTransactionsBySalesRepAndCustId(String salesRep, String customerId);

	Long checkTransactionId(String transactionId);

}