//package com.incture.cherrywork.services;
//
//
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//
//
//import com.incture.cherrywork.dtos.ReturnItemDto;
//import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
//import com.incture.cherrywork.entities.ReturnItem;
//import com.incture.cherrywork.repositories.IReturnRepository;
//import com.incture.cherrywork.repositories.ObjectMapperUtils;
//import com.incture.cherrywork.returns.dao.ReturnItemDao;
//import com.incture.cherrywork.sales.constants.ResponseStatus;
//import com.incture.cherrywork.util.HelperClass;
//
//
//
//
//@SuppressWarnings("unused")
//@Transactional
//@Service
//public class ReturnItemServiceImpl implements ReturnItemService {
//
//	@Autowired
//	private  IReturnRepository repo;
//
//	
//	@Override
//	public ResponseEntity<Object> listAllReturnItems() {
//		try {
//			System.err.println("hello");
//			List<ReturnItem> list = repo.findAll();
//			List<ReturnItemDto> list1=ObjectMapperUtils.mapAll(list, ReturnItemDto.class);
//			System.err.println("hello");
//			
//			return ResponseEntity.ok().body(list1);
//		} catch (Exception e) {
//			
//		return null;
//		}
//	}
//
//
//
////	@Override
////	public ResponseEntity getReturnItemById(String returnReqNum, String returnReqItemid) {
////		try {
////			// Checking Primary Key here
////			if (!HelperClass.checkString(returnReqNum) && returnReqItemid != null) {
////				ReturnItemDto returnItemDto = returnItemRepo.getReturnItemById(returnReqNum, returnReqItemid);
////				if (returnItemDto != null) {
////					return new ResponseEntity(returnItemDto, HttpStatus.ACCEPTED,
////							"Return Item is found for Key : " + returnReqNum + " and " + returnReqItemid,
////							ResponseStatus.SUCCESS);
////				} else {
////					return new ResponseEntity("", HttpStatus.NO_CONTENT,
////							"Return Item is not available for Key : " + returnReqNum + " and " + returnReqItemid,
////							ResponseStatus.FAILED);
////				}
////			} else {
////				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
////						"Return Request Id, Return Item id fields are mandatory", ResponseStatus.FAILED);
////			}
////		} catch (Exception e) {
////			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
////			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
////					ResponseStatus.FAILED);
////		}
////	}
//
////	@Override
////	public ResponseEntity deleteReturnItemById(String returnReqNum, String returnReqItemid) {
////		try {
////			if (!HelperClass.checkString(returnReqNum) && returnReqItemid != null) {
////				String msg = returnItemRepo.deleteReturnItemById(returnReqNum, returnReqItemid);
////				if (msg != null) {
////					return new ResponseEntity("", HttpStatus.ACCEPTED, msg, ResponseStatus.SUCCESS);
////				} else {
////					return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED",
////							ResponseStatus.FAILED);
////				}
////			} else {
////				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
////						"Return Request Id, Return Item id fields are mandatory", ResponseStatus.FAILED);
////			}
////		} catch (Exception e) {
////			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
////			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
////					ResponseStatus.FAILED);
////		}
////	}
//
//	
//
//}
//
