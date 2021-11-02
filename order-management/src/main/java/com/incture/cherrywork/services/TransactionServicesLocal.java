package com.incture.cherrywork.services;

import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.CustomerDto;
import com.incture.cherrywork.dtos.ResponseDto;
import com.incture.cherrywork.dtos.TransactionDto;

public interface TransactionServicesLocal {

	public ResponseEntity<Object> createTransaction(TransactionDto dto);

	public ResponseDto getTransaction(String transactionId);

	public ResponseDto getTransactionsBySalesRepAndCustId(String salesRep, String customerId);
	
	void updateTransaction(TransactionDto dto);

	void deleteTransaction(TransactionDto dto);

	//BillingPrintDto bulidPayloadForPDF(TransactionDto dto, CustomerDto customerDto);

}
