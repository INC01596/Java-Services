//package com.incture.cherrywork.services;
//
//
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import javax.persistence.NoResultException;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.incture.cherrywork.WConstants.StatusConstants;
//import com.incture.cherrywork.dao.RequestMasterDaoImpl;
//import com.incture.cherrywork.dao.SalesDocHeaderDao;
//import com.incture.cherrywork.dao.SalesDocItemDao;
//
//import com.incture.cherrywork.dtos.RequestMasterDto;
//import com.incture.cherrywork.dtos.ResponseEntity;
//import com.incture.cherrywork.dtos.SalesDocHeaderDto;
//import com.incture.cherrywork.exceptions.ExecutionFault;
//import com.incture.cherrywork.new_workflow.dao.SalesOrderLevelStatusDao;
//import com.incture.cherrywork.sales.constants.ResponseStatus;
//import com.incture.cherrywork.util.HelperClass;
//import com.incture.cherrywork.util.SequenceNumberGen;
//
//
//
//@Service
//@Transactional
//public class RequestMasterServiceImpl implements RequestMasterService {
//
//	@Lazy
//	@Autowired
//	private RequestMasterDaoImpl requestMasterRepo;
//
//	
//	
//	private SequenceNumberGen seqNumGenRepo;
//	@Lazy
//	@Autowired
//	private SalesDocItemDao salesItem;
//
//	@Lazy
//	@Autowired
//	private SalesOrderLevelStatusDao salesLevel;
//	@Lazy
//	@Autowired
//	private SalesDocHeaderDao salesDocHeaderDao;
//
//	private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	@Override
//	public ResponseEntity saveOrUpdateRequestMaster(RequestMasterDto requestMasterDto) {
//		try {
//			if (requestMasterDto.getRequestId() == null) {
//				requestMasterDto
//						.setRequestId(seqNumGenRepo.getNextSeqNumber("BSO_", 10, requestMasterRepo.getSession()));
//
//				String msg = requestMasterRepo.saveOrUpdateRequestMaster(requestMasterDto);
//				if (msg == null) {
//					return new ResponseEntity("", HttpStatus.BAD_REQUEST, "CREATION_FAILED", ResponseStatus.FAILED);
//				}
//				return new ResponseEntity(requestMasterDto, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
//			} else {
//				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
//						"Request id is auto generated not to be inserted manaually", ResponseStatus.FAILED);
//			}
//		} catch (Exception e) {
//			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
//			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
//					ResponseStatus.FAILED);
//		}
//	}
//
//	@Override
//	public ResponseEntity updateRequestMaster(RequestMasterDto requestMasterDto) {
//		try {
//			if (requestMasterDto.getRequestId() != null) {
//				String msg = requestMasterRepo.UpdateRequestMaster(requestMasterDto);
//				if (msg == null) {
//					return new ResponseEntity("", HttpStatus.BAD_REQUEST, "CREATION_FAILED", ResponseStatus.FAILED);
//				}
//				return new ResponseEntity(requestMasterDto, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
//			} else {
//				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
//						"Request id is auto generated not to be inserted manaually", ResponseStatus.FAILED);
//			}
//		} catch (Exception e) {
//			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
//			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
//					ResponseStatus.FAILED);
//		}
//	}
//
//	@Override
//	public ResponseEntity listAllRequests() {
//		try {
//			List<RequestMasterDto> list = requestMasterRepo.listAllRequests();
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
//	public ResponseEntity deleteRequestMasterById(String reqId) {
//		try {
//			if (!HelperClass.checkString(reqId)) {
//				String msg = requestMasterRepo.deleteRequestMasterById(reqId);
//				if (msg != null) {
//					return new ResponseEntity("", HttpStatus.ACCEPTED, msg, ResponseStatus.SUCCESS);
//				} else {
//					return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED",
//							ResponseStatus.FAILED);
//				}
//			} else {
//				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Return Request Id Field is mandatory",
//						ResponseStatus.FAILED);
//			}
//		} catch (Exception e) {
//			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
//			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
//					ResponseStatus.FAILED);
//		}
//	}
//
//	@Override
//	public ResponseEntity deleteRequestMasterByRefDocNum(String reqId) {
//		try {
//			if (!HelperClass.checkString(reqId)) {
//				String msg = requestMasterRepo.deleteRequestMasterByRefDocNum(reqId);
//				if (msg != null) {
//					return new ResponseEntity("", HttpStatus.ACCEPTED, msg, ResponseStatus.SUCCESS);
//				} else {
//					return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED",
//							ResponseStatus.FAILED);
//				}
//			} else {
//				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Return Request Id Field is mandatory",
//						ResponseStatus.FAILED);
//			}
//		} catch (NoResultException e) {
//			return new ResponseEntity("", HttpStatus.CONFLICT, "Return Request not found", ResponseStatus.FAILED);
//		} catch (Exception e) {
//			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
//			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
//					ResponseStatus.FAILED);
//		}
//	}
//
//	@Override
//	public ResponseEntity getRequestMasterById(String reqId) {
//		try {
//			if (!HelperClass.checkString(reqId)) {
//				List<RequestMasterDto> reqMasterDtoList = requestMasterRepo.getRequestMasterById(reqId);
//				if (reqMasterDtoList != null) {
//					return new ResponseEntity(reqMasterDtoList, HttpStatus.ACCEPTED,
//							"Request master is found for request id : " + reqId, ResponseStatus.SUCCESS);
//				} else {
//					return new ResponseEntity("", HttpStatus.NO_CONTENT,
//							"Request master is not available for request id : " + reqId, ResponseStatus.FAILED);
//				}
//			} else {
//				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Return Request Id Field is mandatory",
//						ResponseStatus.FAILED);
//			}
//		} catch (Exception e) {
//			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
//			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
//					ResponseStatus.FAILED);
//		}
//	}
//
//	// Update complete status in REQUEST_MASTER Table
//	public ResponseEntity updateStatusPostBlock(String salesHeaderId, boolean noItemBlock) {
//
//		Set<Integer> statusSet = new HashSet<>();
//		List<String> dsList = new ArrayList<String>();
//
//		String requestId = null;
//		String salesOrderIdResponse = null;
//
//		// Check if the noItemBlock
//		if (noItemBlock) {
//
//			RequestMasterDto requestMasterDto = requestMasterRepo.getRequestMasterByRefDocNumber(salesHeaderId);
//			requestMasterDto.setRequestStatusCode(StatusConstants.REQUEST_COMPLETE_NO_ITEM_BLOCK.toString());
//
//			try {
//				requestId = requestMasterRepo.saveOrUpdateRequestMasterStatus(requestMasterDto);
//			} catch (ExecutionFault e) {
//				logger.error(this.getClass() + " - updateStatusPostBlock : " + e.getMessage());
//				return new ResponseEntity(salesHeaderId, HttpStatus.NO_CONTENT," EXCEPTION_FAILED + e",
//						ResponseStatus.FAILED);
//			}
//			SalesDocHeaderDto salesDocHeaderDto = salesDocHeaderDao.getSalesDocHeaderByIdSession(salesHeaderId);
//
//			salesDocHeaderDto.setOverallStatus(StatusConstants.SO_PROCESS_COMPLETE_NO_ITEM_BLOCK.toString());
//
//			try {
//				salesOrderIdResponse = salesDocHeaderDao.saveOrUpdateSalesDocHeader(salesDocHeaderDto);
//			} catch (ExecutionFault e) {
//				logger.error(this.getClass() + " - updateStatusPostBlock : " + e.getMessage());
//				return new ResponseEntity(salesHeaderId, HttpStatus.NO_CONTENT, "EXCEPTION_FAILED + e",
//						ResponseStatus.FAILED);
//			}
//
//		} else {
//
//			// 1. fetch distinct decision sets, based on the sales order number
//			// from
//			// SALES_DOC_ITEM table
//			if (salesHeaderId != null) {
//				dsList = salesItem.getDSBySalesHeaderID(salesHeaderId);
//
//				// 2. if decision set list is not null, then fetch the distinct
//				// level statuses that belong to the decision sets that are
//				// passed
//				// as inputs
//				if (dsList != null) {
//					statusSet = salesLevel.getLevelStatusIdByDS(dsList);
//				}
//
//				// 3. if the statusSet is not null then perform below tasks
//				if (statusSet != null) {
//					if (((statusSet.size() == 1) && statusSet.contains(StatusConstants.LEVEL_COMPLETE))
//							|| (statusSet.size() == 2 && statusSet.contains(StatusConstants.LEVEL_ABANDON))) {
//						// if the status is either all 4, or a combination of 4
//						// and
//						// 17
//						// then update request master table with status
//						// completed
//
//						// 4. fetch the existing record from REQUEST_MASTER
//						// Table
//						RequestMasterDto requestMasterDto = requestMasterRepo
//								.getRequestMasterByRefDocNumber(salesHeaderId);
//
//						// 5. set the request status code
//						// if block in items, then update with REQUEST_COMPLETE
//
//						requestMasterDto.setRequestStatusCode(StatusConstants.REQUEST_COMPLETE.toString());
//
//						// 6. save the record to REQUEST_MASTER Table
//						try {
//							requestId = requestMasterRepo.saveOrUpdateRequestMasterStatus(requestMasterDto);
//						} catch (ExecutionFault e) {
//							logger.error(this.getClass() + " - updateStatusPostBlock : " + e.getMessage());
//							return new ResponseEntity(salesHeaderId, HttpStatus.NO_CONTENT, "EXCEPTION_FAILED + e",
//									ResponseStatus.FAILED);
//						}
//
//						// 7. Fetch the existing record from SALES_DOC_HEADER
//						// Table
//						SalesDocHeaderDto salesDocHeaderDto = salesDocHeaderDao
//								.getSalesDocHeaderByIdSession(salesHeaderId);
//
//						// 8. Update SALES_DOC_HEADER with status -
//						// SO_PROCESS_COMPLETE
//						salesDocHeaderDto.setOverallStatus(StatusConstants.SO_PROCESS_COMPLETE.toString());
//
//						// 9. save the record to SALES_DOC_HEADER Table
//						try {
//							salesOrderIdResponse = salesDocHeaderDao.saveOrUpdateSalesDocHeader(salesDocHeaderDto);
//						} catch (ExecutionFault e) {
//							logger.error(this.getClass() + " - updateStatusPostBlock : " + e.getMessage());
//							return new ResponseEntity(salesHeaderId, HttpStatus.NO_CONTENT, "EXCEPTION_FAILED + e",
//									ResponseStatus.FAILED);
//						}
//
//						if (requestId != null && salesOrderIdResponse != null) {
//							return new ResponseEntity(requestId, HttpStatus.ACCEPTED,
//									"Request Master Updated Successfully", ResponseStatus.SUCCESS);
//						}
//					}
//				}
//			}
//			return new ResponseEntity(salesHeaderId, HttpStatus.BAD_REQUEST, "Data Not Found, Table Not Updated",
//					ResponseStatus.FAILED);
//
//		}
//		if (requestId != null && salesOrderIdResponse != null) {
//			return new ResponseEntity(requestId, HttpStatus.ACCEPTED, "Request Master Updated Successfully",
//					ResponseStatus.SUCCESS);
//		}
//		return new ResponseEntity(salesHeaderId, HttpStatus.BAD_REQUEST, "Data Not Found, Table Not Updated",
//				ResponseStatus.FAILED);
//
//	}
//
//}
