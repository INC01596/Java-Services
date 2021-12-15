package com.incture.cherrywork.services;


import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.BankNamesDto;

import com.incture.cherrywork.dtos.RejectionReasonDto;
import com.incture.cherrywork.dtos.ResponseDto;

public interface InvoiceServicesLocal {
	
	public ResponseDto savePendingInvoices();

	public ResponseEntity<Object> getListOfReasonCode();

	public ResponseEntity<Object> saveBank(BankNamesDto dto);

	public ResponseEntity<Object> getBankDetails();
	
	public ResponseEntity<Object> saveRejectionReason(RejectionReasonDto dto);

	public ResponseEntity<Object> deleteRejectionReason(RejectionReasonDto dto);

		

	

}
