package com.incture.cherrywork.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.message.Message;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.CustomerDto;
import com.incture.cherrywork.dtos.InvoiceDto;
import com.incture.cherrywork.dtos.ResponseDto;
import com.incture.cherrywork.dtos.StatusDto;
import com.incture.cherrywork.dtos.TransactionDto;
import com.incture.cherrywork.entities.TransactionDo;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.repositories.TransactionRepo;
import com.incture.cherrywork.util.SequenceNumberGen;
import com.incture.cherrywork.util.ServicesUtil;

@Service("TransactionServices")
@Transactional
public class TransactionServices implements TransactionServicesLocal {

	@Autowired
	private TransactionRepo repo;
	
	private SequenceNumberGen sequenceNumberGen;
	
	@PersistenceContext
	private EntityManager entityManager;


	

	

	@Override
	public ResponseEntity<Object> createTransaction(TransactionDto dto) {
		

		List<InvoiceDto> invoiceDtos = new ArrayList<>();
		List<StatusDto> statusList = new ArrayList<>();
		
		TransactionDo transactionDo=new TransactionDo();

 
		try {

			
			sequenceNumberGen = SequenceNumberGen.getInstance();
			Session session = entityManager.unwrap(Session.class);
			String tranId = sequenceNumberGen.getNextSeqNumber("T", 8, session);
			dto.setTransactionId(tranId);

			
			statusList=dto.getStatusList();
			if(statusList !=null){
				for(StatusDto statusDto:statusList){
				
			statusDto.setPendingWith("AccountExecutive");
			statusDto.setStatus("INPROGRESS");
			statusDto.setTransaction(dto);
			statusDto.setUpdateDate(new Date());
			statusDto.setUpdatedBy(dto.getSalesRep());
			statusDto.setTransaction(dto);
			}
			}
			
			
			
			invoiceDtos = dto.getInvoiceList();
			if (invoiceDtos != null)
				for (InvoiceDto invDto : invoiceDtos) {
					invDto.setStatus("INPROGRESS");
					invDto.setTransaction(dto);
					

				}

			dto.setStatusList(statusList);
			dto.setInvoiceList(invoiceDtos);
			
			transactionDo=repo.save(ObjectMapperUtils.map(dto, TransactionDo.class));
             return ResponseEntity.ok().body(ObjectMapperUtils.map(transactionDo, TransactionDto.class));
			

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	
	}

	@Override
	public ResponseDto getTransaction(String transactionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseDto getTransactionsBySalesRepAndCustId(String salesRep, String customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateTransaction(TransactionDto dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTransaction(TransactionDto dto) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public ResponseDto getTransaction(String transactionId) {
//		ResponseDto response = new ResponseDto();
//
//		try {
//			response.setData(dao.getTransactionById(transactionId));
//			response.setStatusCode(HttpStatus.SC_OK);
//			response.setStatus(true);
//			response.setMessage("Success");
//		} catch (Exception e) {
//			response.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
//			response.setStatus(false);
//			response.setMessage("FAILED" + " " + e.getMessage());
//		}
//		return response;
//	}

	

	
}

