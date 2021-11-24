package com.incture.cherrywork.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import com.incture.cherrywork.dtos.BankNamesDto;
=======
import com.incture.cherrywork.dao.RejectionReasonDaoLocal;
>>>>>>> refs/remotes/origin/master
import com.incture.cherrywork.dtos.RejectionReasonDto;
import com.incture.cherrywork.dtos.ResponseDto;
import com.incture.cherrywork.entities.BankNamesDo;
import com.incture.cherrywork.entities.RejectionReasonDo;
import com.incture.cherrywork.repositories.BankRepo;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.repositories.RejectionRepo;
import com.incture.cherrywork.repositories.ServicesUtils;

@Service("InvoiceServices")
@Transactional
public class InvoiceService implements InvoiceServicesLocal {

	@Autowired
	private HciInvoiceDetailServiceLocal hciInvoiceService;
	
	@Autowired
	private BankRepo bankRepo;
	
	@Autowired
	private PendingInvoiceDao pendingInvoiceDao;
	
	@Autowired
	private RejectionRepo rejRepo;
	
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
<<<<<<< HEAD
	public ResponseEntity<Object> saveBank(BankNamesDto dto) {
	try {
		System.err.println("hey");
		
		return ResponseEntity.ok().body(bankRepo.save(ObjectMapperUtils.map(dto, BankNamesDo.class)));	}
	catch(Exception e)
	{
		e.printStackTrace();
		return null;
	}
	}

	@Override
	public ResponseEntity<Object> getBankDetails() {
		return ResponseEntity.ok().body(bankRepo.findAll());
	}

	@Override
	public ResponseEntity<Object> getListOfReasonCode() {
		try{
			return ResponseEntity.ok().body(rejRepo.findAll());
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}

	@Override
	public ResponseEntity<Object> saveRejectionReason(RejectionReasonDto dto) {
		try{
		if(!ServicesUtils.isEmpty(dto))
		{
			RejectionReasonDo rDo=ObjectMapperUtils.map(dto, RejectionReasonDo.class);
			return ResponseEntity.ok().body(rejRepo.save(rDo));
		}
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return null;
		
	}

	@Override
	public ResponseEntity<Object> deleteRejectionReason(RejectionReasonDto dto) {
		try{
		if(!ServicesUtils.isEmpty(dto))
		{
			RejectionReasonDo rDo=ObjectMapperUtils.map(dto, RejectionReasonDo.class);
			rejRepo.delete(rDo);
			return ResponseEntity.ok().body("DeletedSuccessfully");
		}
		}catch(Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return null;
		
}
=======
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
>>>>>>> refs/remotes/origin/master
}