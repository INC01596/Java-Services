//package com.incture.cherrywork.services;
//
//
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//
//import com.incture.cherrywork.dtos.ExchangeHeaderDto;
//import com.incture.cherrywork.dtos.ResponseEntity;
//import com.incture.cherrywork.returns.dao.ExchangeHeaderDao;
//import com.incture.cherrywork.sales.constants.ResponseStatus;
//import com.incture.cherrywork.util.HelperClass;
//
//
//
//@Transactional
//@Service
//public class ExchangeHeaderServiceImpl implements ExchangeHeaderService {
//
//	@Autowired
//	private ExchangeHeaderDao exchangeHeaderRepo;
//
//	@Override
//	public ResponseEntity saveOrUpdateExchangeHeader(ExchangeHeaderDto exchangeHeaderDto) {
//		try {
//			// Checking Primary Key here
//			if (!HelperClass.checkString(exchangeHeaderDto.getReturnReqNum())
//					&& !HelperClass.checkString(exchangeHeaderDto.getExchangeReqNum())) {
//				String msg = exchangeHeaderRepo.saveOrUpdateExchangeHeader(exchangeHeaderDto);
//
//				if (msg == null) {
//					return new ResponseEntity("", HttpStatus.BAD_REQUEST, "CREATION_FAILED", ResponseStatus.FAILED);
//				}
//				return new ResponseEntity(exchangeHeaderDto, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
//			} else {
//				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
//						"Return Request Id, Exchange Header id fields are mandatory", ResponseStatus.FAILED);
//			}
//		} catch (Exception e) {
//			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
//			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
//					ResponseStatus.FAILED);
//		}
//	}
//
//	@Override
//	public ResponseEntity listAllExchangeHeaders() {
//		try {
//			List<ExchangeHeaderDto> list = exchangeHeaderRepo.listAllExchangeHeaders();
//			if (list != null && !list.isEmpty()) {
//				return new ResponseEntity(list, HttpStatus.OK, "DATA_FOUND", ResponseStatus.SUCCESS);
//			} else {
//				return new ResponseEntity("", HttpStatus.NO_CONTENT, "EMPTY_LIST", ResponseStatus.FAILED);
//			}
//		} catch (Exception e) {
//			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
//			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
//					ResponseStatus.FAILED);
//		}
//	}
//
//	@Override
//	public ResponseEntity getExchangeHeaderById(String exchangeReqNum, String returnReqNum) {
//		try {
//			// Checking Primary Key here
//			if (!HelperClass.checkString(exchangeReqNum) && !HelperClass.checkString(returnReqNum)) {
//				ExchangeHeaderDto exchangeHeaderDto = exchangeHeaderRepo.getExchangeHeaderById(exchangeReqNum,
//						returnReqNum);
//				if (exchangeHeaderDto != null) {
//					return new ResponseEntity(exchangeHeaderDto, HttpStatus.ACCEPTED,
//							"Exchange Header is found for Key : " + returnReqNum + " and " + exchangeReqNum,
//							ResponseStatus.SUCCESS);
//				} else {
//					return new ResponseEntity("", HttpStatus.NO_CONTENT,
//							"Exchange Header is not available for Key : " + returnReqNum + " and " + exchangeReqNum,
//							ResponseStatus.FAILED);
//				}
//			} else {
//				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
//						"Return Request Id, Exchange Header id fields are mandatory", ResponseStatus.FAILED);
//			}
//		} catch (Exception e) {
//			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
//			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
//					ResponseStatus.FAILED);
//		}
//	}
//
//	@Override
//	public ResponseEntity deleteExchangeHeaderById(String exchangeReqNum, String returnReqNum) {
//		try {
//			// Checking Primary Key here
//			if (!HelperClass.checkString(exchangeReqNum) && !HelperClass.checkString(returnReqNum)) {
//				String msg = exchangeHeaderRepo.deleteExchangeHeaderById(exchangeReqNum, returnReqNum);
//				if (msg != null) {
//					return new ResponseEntity("", HttpStatus.ACCEPTED, msg, ResponseStatus.SUCCESS);
//				} else {
//					return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED,",
//							ResponseStatus.FAILED);
//				}
//			} else {
//				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
//						"Return Request Id, Exchange Header id fields are mandatory", ResponseStatus.FAILED);
//			}
//		} catch (Exception e) {
//			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
//			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED" + e,
//					ResponseStatus.FAILED);
//		}
//	}
//
//}
