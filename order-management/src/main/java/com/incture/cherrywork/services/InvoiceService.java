package com.incture.cherrywork.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.ResponseDto;

@Service("InvoiceServices")
@Transactional
public class InvoiceService implements InvoiceServicesLocal {

	@Autowired
	private HciInvoiceDetailServiceLocal hciInvoiceService;
	
	@Autowired
	private PendingInvoiceDao pendingInvoiceDao;
	
	@Override
	public ResponseDto savePendingInvoices() {

		ResponseDto response = new ResponseDto();

		try {
			pendingInvoiceDao.savePendingInvoices(hciInvoiceService.getAllOpenInvocesFromRFC());
			response.setMessage("success");
			response.setStatus(true);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
		}

		return response;
	}
}