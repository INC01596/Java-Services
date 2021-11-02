package com.incture.cherrywork.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.message.Message;
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
import com.incture.cherrywork.util.ServicesUtil;

@Service("TransactionServices")
@Transactional
public class TransactionServices implements TransactionServicesLocal {

	@Autowired
	private TransactionRepo repo;

	@Autowired
	private CustomerDaoLocal customerDaoLocal;

	//@Autowired
	//private UserServiceLocal userServiceLocal;

	

//	@Autowired
//	private PrintBillingServiceLocal printBillingServiceLocal;

	
	//@Autowired
	//private IdpServiceLocal idpServiceLocal;

	//private static final Logger logger = LoggerFactory.getLogger(TransactionServices.class);
	
	/*
	 * SequenceNumberGen sequenceNumberGen; String transactionId;
	 */

	@Override
	public ResponseEntity<Object> createTransaction(TransactionDto dto) {

		//ResponseDto response = new ResponseDto();

		Map<String, String> settingMap = new HashMap<>();

		try {

			/*
			 * sequenceNumberGen = SequenceNumberGen.getInstance();
			 * transactionId = sequenceNumberGen.getNextSeqNumber("T", 10,
			 * sessionFactory.getCurrentSession());
			 * 
			 * Long count = dao.checkTransactionId(transactionId);
			 * 
			 * if (count != 0) {
			 * 
			 * while (true) {
			 * 
			 * transactionId = sequenceNumberGen.getNextSeqNumber("T", 10,
			 * sessionFactory.getCurrentSession());
			 * 
			 * count = dao.checkTransactionId(transactionId);
			 * 
			 * if (count == 0) { break; }
			 * 
			 * }
			 * 
			 * }
			 * 
			 * dto.setTransactionId(transactionId);
			 */

			// End

			dto.setTransactionId("T" + ServicesUtil.getLoggedInUser(dto.getSalesRep()) + System.currentTimeMillis());

			// Start

			List<InvoiceDto> invoiceDtos = new ArrayList<>();
			List<StatusDto> statusList = new ArrayList<>();

			StatusDto statusDto = new StatusDto();
			statusDto.setPendingWith("AccountExecutive");
			statusDto.setStatus("INPROGRESS");
			statusDto.setTransaction(dto);
			statusDto.setUpdateDate(new Date());
			statusDto.setUpdatedBy(dto.getSalesRep());
			statusList.add(statusDto);

			invoiceDtos = dto.getInvoiceList();
			if (invoiceDtos != null)
				for (InvoiceDto invDto : invoiceDtos) {
					invDto.setStatus("INPROGRESS");
				}

			dto.setStatusList(statusList);
			dto.setInvoiceList(invoiceDtos);
			TransactionDo transactionDo=new TransactionDo();
			transactionDo=repo.save(ObjectMapperUtils.map(dto, TransactionDo.class));
             return ResponseEntity.ok().body(ObjectMapperUtils.map(transactionDo, TransactionDto.class));
			//response.setData(dao.createTransaction(dto));

//			CustomerDto customerDto = new CustomerDto();
//
//			customerDto.setCustId(dto.getCustomerId());
//
//			customerDto = customerDaoLocal.getCustomer(customerDto);

//			settingMap = buildMail("pending with", "AccountExecutive", dto.getTransactionId(), "", customerDto, dto);
//
//			notifyMail.sendMailCloud(settingMap.get("recipient"), settingMap.get("subject"),
//					settingMap.get("mailBody"));
//
//			sendMailToSalesRep(dto, customerDto);

			//List<String> userList = idpServiceLocal.getUserIdListByRole("AccountExecutive");

			

//			response.setStatusCode(200);
//			response.setStatus(true);
//			response.setMessage("SUCCESS");

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

