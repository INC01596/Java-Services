package com.incture.cherrywork.services;

<<<<<<< HEAD
import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.BankNamesDto;
=======
>>>>>>> refs/remotes/origin/master
import com.incture.cherrywork.dtos.RejectionReasonDto;
import com.incture.cherrywork.dtos.ResponseDto;

public interface InvoiceServicesLocal {
	
	public ResponseDto savePendingInvoices();
<<<<<<< HEAD
	public ResponseEntity<Object> getListOfReasonCode();

	public ResponseEntity<Object> saveBank(BankNamesDto dto);

	public ResponseEntity<Object> getBankDetails();
	
	ResponseEntity<Object> saveRejectionReason(RejectionReasonDto dto);

	ResponseEntity<Object> deleteRejectionReason(RejectionReasonDto dto);

		
=======
	public ResponseDto getListOfReasonCode();
	ResponseDto saveRejectionReason(RejectionReasonDto dto);
	ResponseDto deleteRejectionReason(RejectionReasonDto dto);
>>>>>>> refs/remotes/origin/master
}
