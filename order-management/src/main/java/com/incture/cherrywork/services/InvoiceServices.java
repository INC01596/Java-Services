//package com.incture.cherrywork.services;
//
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.incture.cherrywork.controllers.InvoiceServicesLocal;
//import com.incture.cherrywork.dtos.BankNamesDto;
//import com.incture.cherrywork.dtos.RejectionReasonDto;
//import com.incture.cherrywork.dtos.ResponseDto;
//import com.incture.cherrywork.entities.BankNamesDo;
//import com.incture.cherrywork.entities.SalesOrderHeader;
//import com.incture.cherrywork.repositories.IBankRepo;
//import com.incture.cherrywork.repositories.ObjectMapperUtils;
//
//
//
//@Service("InvoiceServices")
//@Transactional
//public class InvoiceServices implements InvoiceServicesLocal {
//
//	@Autowired
//	private IBankRepo bankRepo;
//
////	@Autowired
////	private PendingInvoiceDaoLocal pendingInvoiceDao;
//
//	//@Autowired
//	//private HciInvoiceDetailServiceLocal hciInvoiceService;
//
////	@Autowired
////	private RejectionReasonDaoLocal rejectionDao;
//	
//	@Override
//	public ResponseDto getListOfBankNames() {
//
//		ResponseDto response = new ResponseDto();
//
//		try {
//			List<String> bankNamesDtoList = new ArrayList<>();
//			List<BankNamesDo> entityList = bankRepo.findAll();
//
//			for (BankNamesDo entity : entityList) {
//
//				
//
//				
//
//				bankNamesDtoList.add(entity.getBankName().getBankName());
//			}
//			response.setData(bankNamesDtoList);
//			response.setMessage("success");
//			response.setStatus(true);
//
//		} catch (Exception e) {
//			response.setMessage(e.getMessage());
//			response.setStatus(false);
//		}
//
//		return response;
//	}
//
//	@Override
//	public ResponseDto getBankDetails() {
//
//		ResponseDto response = new ResponseDto();
//
//		try {
//
//			List<BankNamesDto> bankNamesDtoList = new ArrayList<>();
//
//			
//			List<BankNamesDo> entityList = bankRepo.findAll();
//
//			for (BankNamesDo entity : entityList) {
//
//				BankNamesDto dto = new BankNamesDto();
//
//				dto.setBankName(entity.getBankName().getBankName());
//				dto.setControllingArea(entity.getBankName().getControllingArea());
//
//				bankNamesDtoList.add(dto);
//
//			}
//			response.setData(bankNamesDtoList);
//			response.setMessage("success");
//			response.setStatus(true);
//
//
//
//		} catch (Exception e) {
//			response.setMessage(e.getMessage());
//			response.setStatus(false);
//		}
//
//		return response;
//	}
//
//	@Override
//	public ResponseDto saveBank(BankNamesDto dto) {
//
//		ResponseDto response = new ResponseDto();
//
//		try {
//
//			bankRepo.save(ObjectMapperUtils.map(dto, BankNamesDo.class));
//			response.setMessage("Bank Successfully saved");
//			response.setStatus(true);
//
//		} catch (Exception e) {
//			response.setMessage(e.getMessage());
//			response.setStatus(false);
//		}
//
//		return response;
//	}
//
//	@Override
//	public ResponseDto deleteBank(BankNamesDto dto) {
//
//		ResponseDto response = new ResponseDto();
//
//		try {
//
//			bankRepo.delete(ObjectMapperUtils.map(dto, BankNamesDo.class));
//
//			response.setData("Bank deleted Sucessfully");
//			response.setMessage("success");
//			response.setStatus(true);
//
//		} catch (Exception e) {
//			response.setMessage(e.getMessage());
//			response.setStatus(false);
//		}
//
//		return response;
//	}
//
//	@Override
//	public ResponseDto getListOfReasonCode() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ResponseDto savePendingInvoices() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ResponseDto saveRejectionReason(RejectionReasonDto dto) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ResponseDto deleteRejectionReason(RejectionReasonDto dto) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
////	@Override
////	public ResponseDto getListOfReasonCode() {
////
////		ResponseDto response = new ResponseDto();
////
////		try {
////			response.setData(rejectionDao.getListOfRejectionReason());
////			response.setMessage("success");
////			response.setStatus(true);
////
////		} catch (Exception e) {
////			response.setMessage(e.getMessage());
////			response.setStatus(false);
////		}
////
////		return response;
////	}
//
////	@Override
////	public ResponseDto saveRejectionReason(RejectionReasonDto dto) {
////
////		ResponseDto response = new ResponseDto();
////
////		try {
////
////			rejectionDao.saveRejectionReason(dto);
////			response.setMessage("Successfully saved ");
////			response.setStatus(true);
////
////		} catch (Exception e) {
////			response.setMessage(e.getMessage());
////			response.setStatus(false);
////		}
////
////		return response;
////	}
//
////	@Override
////	public ResponseDto deleteRejectionReason(RejectionReasonDto dto) {
////
////		ResponseDto response = new ResponseDto();
////
////		try {
////			rejectionDao.deleteRejectionReason(dto);
////			response.setMessage("Successfully deleted ");
////			response.setStatus(true);
////
////		} catch (Exception e) {
////			response.setMessage(e.getMessage());
////			response.setStatus(false);
////		}
////
////		return response;
////	}
////
////	@Override
////	public ResponseDto savePendingInvoices() {
////
////		ResponseDto response = new ResponseDto();
////
////		try {
////			pendingInvoiceDao.savePendingInvoices(hciInvoiceService.getAllOpenInvocesFromRFC());
////			response.setMessage("success");
////			response.setStatus(true);
////		} catch (Exception e) {
////			response.setMessage(e.getMessage());
////			response.setStatus(false);
////		}
////
////		return response;
////	}
////
////	
////	
//}
