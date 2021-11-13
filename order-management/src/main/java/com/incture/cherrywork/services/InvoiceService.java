package com.incture.cherrywork.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dao.RejectionReasonDaoLocal;
import com.incture.cherrywork.dtos.RejectionReasonDto;
import com.incture.cherrywork.dtos.ResponseDto;

@Service("InvoiceServices")
@Transactional
public class InvoiceService implements InvoiceServicesLocal {

	@Autowired
	private HciInvoiceDetailServiceLocal hciInvoiceService;
	
	@Autowired
	private PendingInvoiceDao pendingInvoiceDao;
	
	@Autowired
	private RejectionReasonDaoLocal rejectionDao;
	
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

	@Override
	public ResponseDto getListOfReasonCode() {

		ResponseDto response = new ResponseDto();

		try {
			response.setData(rejectionDao.getListOfRejectionReason());
			response.setMessage("success");
			response.setStatus(true);

		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
		}

		return response;
	}
	
	@Override
	public ResponseDto saveRejectionReason(RejectionReasonDto dto) {

		ResponseDto response = new ResponseDto();

		try {

			rejectionDao.saveRejectionReason(dto);
			response.setMessage("Successfully saved ");
			response.setStatus(true);

		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
		}

		return response;
	}
	
	@Override
	public ResponseDto deleteRejectionReason(RejectionReasonDto dto) {

		ResponseDto response = new ResponseDto();

		try {
			rejectionDao.deleteRejectionReason(dto);
			response.setMessage("Successfully deleted ");
			response.setStatus(true);

		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(false);
		}

		return response;
	}
}