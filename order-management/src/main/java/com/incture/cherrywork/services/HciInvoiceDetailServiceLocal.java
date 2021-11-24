package com.incture.cherrywork.services;


import java.io.IOException;
import java.util.List;

import com.incture.cherrywork.dtos.PendingInvoiceDto;
import com.incture.cherrywork.dtos.ResponseDto;
import com.incture.cherrywork.entities.PendingInvoiceDo;



public interface HciInvoiceDetailServiceLocal {

	ResponseDto getInvoices(String customerId);

	ResponseDto getInvoiceDetails(String invoiceNumber);

	List<PendingInvoiceDto> getAllOpenInvoices();

	List<PendingInvoiceDo> getAllOpenInvocesFromRFC() throws IOException;

}
