package com.incture.cherrywork.services;

import com.incture.cherrywork.dtos.RejectionReasonDto;
import com.incture.cherrywork.dtos.ResponseDto;

public interface InvoiceServicesLocal {
	
	public ResponseDto savePendingInvoices();
	public ResponseDto getListOfReasonCode();
	ResponseDto saveRejectionReason(RejectionReasonDto dto);
	ResponseDto deleteRejectionReason(RejectionReasonDto dto);
}
