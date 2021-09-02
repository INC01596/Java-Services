package com.incture.cherrywork.workflow.services;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import com.incture.cherrywork.WConstants.StatusConstants;
import com.incture.cherrywork.dao.RequestMasterDao;
import com.incture.cherrywork.dao.SalesDocHeaderDao;
import com.incture.cherrywork.dao.SalesDocItemDao;
import com.incture.cherrywork.dtos.RequestMasterDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.SalesDocHeaderDto;
import com.incture.cherrywork.dtos.SalesDocItemDto;
import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.HelperClass;


@Service
@Transactional
public class SalesDocHeaderServiceImpl implements SalesDocHeaderService {

	@Lazy
	@Autowired
	private SalesDocHeaderDao salesDocHeaderRepo;

	@Lazy
	@Autowired
	private SalesDocItemDao salesDocItemRepo;

	@Lazy
	@Autowired
	private RequestMasterDao requestMasterRepo;

	@Override
	public ResponseEntity saveSalesDocHeader(SalesDocHeaderDto salesDocHeaderDto) {
		try {
			if (!HelperClass.checkString(salesDocHeaderDto.getReqMasterId())) {

				// Getting List of Request Master from req master id from dto
				// List<RequestMasterDto> reqMasterDtoList = requestMasterRepo
				// .getRequestMasterById(salesDocHeaderDto.getReqMasterId());
				// if (reqMasterDtoList == null || reqMasterDtoList.isEmpty()) {
				//
				// // Given req master id from dto is not registered in db yet,
				// // fill it first
				// return new ResponseEntity("", HttpStatus.NO_CONTENT, "Entered
				// Request ID is not Registered Yet!!",
				// ResponseStatus.FAILED);
				// } else {
				// if (reqMasterDtoList.size() > 1) {
				//
				// // Checking data in request master using data in list
				// return new ResponseEntity("", HttpStatus.BAD_REQUEST,
				// "Entered Request ID already Registered, Use another one!!",
				// ResponseStatus.FAILED);
				// } else {
				RequestMasterDto reqMasterDto = new RequestMasterDto();
				reqMasterDto.setRequestId(salesDocHeaderDto.getReqMasterId());
				// Saving data here
				return savingSalesDocHeader(salesDocHeaderDto, Stream.of(reqMasterDto).collect(Collectors.toList()));
				// }
				// }
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Request Master ID is mandatory",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			System.err.println("[saveSalesDocHeader] exception"+e.getMessage());
			e.printStackTrace();
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity listAllSalesDocHeaders() {
		try {
			List<SalesDocHeaderDto> list = salesDocHeaderRepo.listAllSalesDocHeaders();
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity(list, HttpStatus.OK, "DATA_FOUND", ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.NO_CONTENT," EMPTY_LIST", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity getSalesDocHeaderById(String salesOrderId) {
		try {
			if (!HelperClass.checkString(salesOrderId)) {
				SalesDocHeaderDto salesDocHeaderDto = salesDocHeaderRepo.getSalesDocHeaderById(salesOrderId);
				if (salesDocHeaderDto != null) {
					return new ResponseEntity(salesDocHeaderDto, HttpStatus.ACCEPTED,
							"Sales Document Header is found for Sales Order Number : " + salesOrderId,
							ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity(null, HttpStatus.NO_CONTENT,
							"Sales Document Header is not available for Sales Order Number : " + salesOrderId,
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity(null, HttpStatus.BAD_REQUEST, "Please enter Sales Order Header ID",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity deleteSalesDocHeaderById(String salesOrderId) {
		try {
			if (!HelperClass.checkString(salesOrderId)) {
				String msg = salesDocHeaderRepo.deleteSalesDocHeaderById(salesOrderId);
				if (msg != null) {
					return new ResponseEntity("", HttpStatus.ACCEPTED, msg, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED",
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Please enter Sales Order Header ID",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity listAllSalesDocHeadersWithoutItem() {
		try {
			List<SalesDocHeaderDto> list = salesDocHeaderRepo.listAllSalesDocHeaderWithoutItems();
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity(list, HttpStatus.OK, "DATA_FOUND", ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.NO_CONTENT, "EMPTY_LIST", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	/*@Override
	public ResponseEntity filteredSalesDocHeader(FilterDto filterData) {
		try {
			if (filterData != null) {
				if (!checkString(filterData.getUserPID())) {

					if (!checkString(filterData.getSalesDocNumInitial())
							&& checkString(filterData.getSalesDocNumEnd())) {
						filterData.setSalesDocNumEnd(filterData.getSalesDocNumInitial());
					}
					// if (filterData.getInitialDate() != null &&
					// filterData.getEndDate() != null) {
					//
					// BigInteger maxOfDate = new BigInteger("86399999");
					// filterData.setEndDate(filterData.getEndDate().add(maxOfDate));

					// Long startDate =
					// filterData.getInitialDate().getTime();
					// Long convertedStartDate = startDate + 19800000l;
					// Long endDate = filterData.getEndDate().getTime();
					// Long convertedEndDate = endDate + 19800000l;
					//
					// Date sDate = new Date(convertedStartDate);
					// Date eDate = new Date(convertedEndDate);
					//
					// filterData.setInitialDate(sDate);
					// filterData.setEndDate(eDate);
					// }
					// when all fields are either null or empty
					// Comment if there is issue in data access control *start*
					if (!(checkString(filterData.getCustomerCode()) && checkString(filterData.getMaterialGroupFor())
							&& checkString(filterData.getDistributionChannel()) && checkString(filterData.getSalesOrg())
							&& checkString(filterData.getDivision()) && checkString(filterData.getMaterialGroup())
							&& checkString(filterData.getCustomerPo()))) {
						System.err.println("filteredSalesDocHeader for filtering");

						boolean checkAuthForInputData = new HelperClass().checkAuthForInputData(filterData);
						if (!checkAuthForInputData) {
							return new ResponseEntity(checkAuthForInputData, HttpStatus.ACCEPTED,
									"Login person is not authorised for input data", ResponseStatus.SUCCESS);
						}
					}
					// *end*
					List<String> salesDocHeaderDtoList = salesDocHeaderRepo.filteredSalesDocHeader(filterData);
					if (salesDocHeaderDtoList != null) {
						if (salesDocHeaderDtoList.isEmpty()) {
							return new ResponseEntity("", HttpStatus.NO_CONTENT, DATA_NOT_FOUND, ResponseStatus.FAILED);
						} else {
							return new ResponseEntity(salesDocHeaderDtoList, HttpStatus.ACCEPTED, DATA_FOUND,
									ResponseStatus.SUCCESS);
						}
					} else {
						return new ResponseEntity(false, HttpStatus.FORBIDDEN, "Login person is not authorised ",
								ResponseStatus.SUCCESS);
					}
				} else {
					return new ResponseEntity(filterData, HttpStatus.BAD_REQUEST,
							INVALID_INPUT + ", User id is not available", ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity(filterData, HttpStatus.BAD_REQUEST, INVALID_INPUT, ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_FAILED + e,
					ResponseStatus.FAILED);
		}

	}*/

	@Override
	public ResponseEntity getRequestIdBySoNum(String salesOrderId) {
		try {
			if (!HelperClass.checkString(salesOrderId)) {
				String reqId = salesDocHeaderRepo.getRequestIdWithSoHeader(salesOrderId);
				if (reqId != null) {
					return new ResponseEntity(reqId, HttpStatus.ACCEPTED, "DATA_FOUND", ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT, "DATA_NOT_FOUND", ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Please enter Sales Order Header ID",
						ResponseStatus.FAILED);
			}
		} catch (NoResultException e) {
		//	HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.NO_CONTENT,
					"Sales Document Header is not available for Sales Order Number : " + salesOrderId,
					ResponseStatus.FAILED);
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	private ResponseEntity updateSalesDocHeader(SalesDocHeaderDto salesDocHeaderDto) throws ExecutionFault {
		if (!HelperClass.checkString(salesDocHeaderDto.getSalesOrderNum())) {
			String msg = salesDocHeaderRepo.updateSalesDocHeader(salesDocHeaderDto);
			if (msg == null) {

				// Input is not valid
				return new ResponseEntity(null, HttpStatus.BAD_REQUEST, "CREATION_FAILED", ResponseStatus.FAILED);
			}
			return new ResponseEntity(salesDocHeaderDto, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
		} else {
			return new ResponseEntity(null, HttpStatus.BAD_REQUEST, "Please enter Sales Order Header ID",
					ResponseStatus.FAILED);
		}
	}

	private ResponseEntity savingSalesDocHeader(SalesDocHeaderDto salesDocHeaderDto,
			List<RequestMasterDto> reqMasterDtoList) throws ExecutionFault {
		System.err.println("savingSalesDocHeader starts..");
		if (!HelperClass.checkString(salesDocHeaderDto.getSalesOrderNum())) {
			String msg = salesDocHeaderRepo.saveSalesDocHeader(salesDocHeaderDto);
			if (msg == null) {

				// Input is not valid
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "CREATION_FAILED", ResponseStatus.FAILED);
			}

			// Assigning ref doc num to req master from so dto no
			if(reqMasterDtoList != null && reqMasterDtoList.size()>0){
			reqMasterDtoList.forEach(req -> {
				if (salesDocHeaderDto.getCustomerPo().contains("CR")) {

					req.setRequestCategory("PR");
					req.setRequestType("05");
				}

				req.setRefDocNum(salesDocHeaderDto.getSalesOrderNum());
				req.setRequestStatusCode(StatusConstants.REQUEST_NEW.toString());
			});

			// Saving ref doc id in req master here
			requestMasterRepo.saveOrUpdateRequestMaster(reqMasterDtoList.get(0));
			}

			return new ResponseEntity(salesDocHeaderDto, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
		} else {
			return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Please enter Sales Order Header ID",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity getSalesDocHeadersWithItems(List<String> salesOrderNumList) {
		try {
			if (salesOrderNumList != null && !salesOrderNumList.isEmpty()) {
				List<SalesDocHeaderDto> list = salesDocHeaderRepo.getSalesDocHeadersWithItems(salesOrderNumList);
				if (list != null && !list.isEmpty()) {
					return new ResponseEntity(list, HttpStatus.OK, "DATA_FOUND", ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT, "EMPTY_LIST", ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity(salesOrderNumList, HttpStatus.BAD_REQUEST,
						"INVALID_INPUT" + ", sales order number list is not found.", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity getSalesDocHeaderByDecisionSetIdWithItems(String salesOrderNum, String decisionSetId) {

		try {

			if (!HelperClass.checkString(salesOrderNum) && !HelperClass.checkString(decisionSetId)) {
				SalesDocHeaderDto salesDocHeaderDto = salesDocHeaderRepo
						.getSalesDocHeaderWithoutItemsById(salesOrderNum);

				if (salesDocHeaderDto != null) {

					List<SalesDocItemDto> salesDocItemDtoList = salesDocItemRepo
							.getSalesDocItemsByDecisionSetId(decisionSetId);

					if (!salesDocItemDtoList.isEmpty()) {

						salesDocHeaderDto.setSalesDocItemList(salesDocItemDtoList);
						return new ResponseEntity(salesDocHeaderDto, HttpStatus.OK, "DATA_FOUND", ResponseStatus.SUCCESS);

					} else {
						return new ResponseEntity(salesOrderNum, HttpStatus.NO_CONTENT,
								"DATA_NOT_FOUND" + " with decision set id.", ResponseStatus.FAILED);

					}

				} else {
					return new ResponseEntity(salesOrderNum, HttpStatus.NO_CONTENT,
							"DATA_NOT_FOUND "+ " with sales order num.", ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"INVALID_INPUT" + ", sales order number and decision set id fields are empty.",
						ResponseStatus.FAILED);
			}

		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}

	}

	@Override
	public ResponseEntity getSalesDocHeadersWithoutItems(String salesOrderNum) {
		System.err.println("getSalesDocHeadersWithoutItems starts..");
		try {
//			return new ResponseEntity(null, HttpStatus.NO_CONTENT,
//					"Sales Document Header is not available for Sales Order Number : " + salesOrderNum,
//					ResponseStatus.FAILED);
			//Commented by Awadhesh Kumar
			if (!HelperClass.checkString(salesOrderNum)) {
				SalesDocHeaderDto salesDocHeaderDto = salesDocHeaderRepo
						.getSalesDocHeaderWithoutItemsById(salesOrderNum);
				if (salesDocHeaderDto != null) {
					return new ResponseEntity(salesDocHeaderDto, HttpStatus.ACCEPTED,
							"Sales Document Header is found for Sales Order Number : " + salesOrderNum,
							ResponseStatus.SUCCESS);
				} else {
					System.err.println("salesDocHeaderDto is null ");
					return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR,
							"Sales Document Header is not available for Sales Order Number : " + salesOrderNum,
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity(null, HttpStatus.BAD_REQUEST, "Please enter Sales Order Header ID",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity updateSalesDocHeaderForSchedular(SalesDocHeaderDto salesDocHeaderDto) {
		try {
			if (!HelperClass.checkString(salesDocHeaderDto.getReqMasterId())) {

				// Get data from db to update details in current sales order dto
				SalesDocHeaderDto salesDocHeaderDtoFromDb = salesDocHeaderRepo
						.getSalesDocHeaderById(salesDocHeaderDto.getSalesOrderNum());

				if (salesDocHeaderDtoFromDb != null
						&& !HelperClass.checkString(salesDocHeaderDtoFromDb.getReqMasterId())) {

					// saving fields from hana to header dto of scheduler
					salesDocHeaderDto.setReqMasterId(salesDocHeaderDtoFromDb.getReqMasterId());
					salesDocHeaderDto.setDecisionSetId(salesDocHeaderDtoFromDb.getDecisionSetId());

					// saving decision set id for each items from hana db
					if (salesDocHeaderDto.getSalesDocItemList() != null
							&& !salesDocHeaderDto.getSalesDocItemList().isEmpty()) {

						salesDocHeaderDto.getSalesDocItemList().forEach(item -> {

							if (salesDocHeaderDtoFromDb.getSalesDocItemList() != null
									&& !salesDocHeaderDtoFromDb.getSalesDocItemList().isEmpty()) {

								salesDocHeaderDtoFromDb.getSalesDocItemList().forEach(itemInDb -> {
									if (item.getSalesHeaderNo().equals(itemInDb.getSalesHeaderNo())
											&& item.getSalesItemOrderNo().equals(itemInDb.getSalesItemOrderNo())) {
										item.setDecisionSetId(itemInDb.getDecisionSetId());
									}
								});

							}
						});
					}

					// Saving data here
					return updateSalesDocHeader(salesDocHeaderDto);
				} else {
					return new ResponseEntity(null, HttpStatus.OK, "Data not found in Hana", ResponseStatus.SUCCESS);
				}
			} else {
				return new ResponseEntity(null, HttpStatus.BAD_REQUEST, "Request Master ID is mandatory",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

}
