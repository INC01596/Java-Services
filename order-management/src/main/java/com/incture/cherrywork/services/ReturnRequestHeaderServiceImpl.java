package com.incture.cherrywork.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dao.ReturnRequestHeaderDaoImpl;
import com.incture.cherrywork.dtos.ExchangeItemDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.ReturnItemDto;
import com.incture.cherrywork.dtos.ReturnRequestHeaderDto;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.HelperClass;
import com.incture.cherrywork.util.SequenceNumberGen;



@Service
@Transactional
public class ReturnRequestHeaderServiceImpl implements ReturnRequestHeaderService {

	@Autowired
	private ReturnRequestHeaderDaoImpl returnRequestHeaderRepo;

	
	private SequenceNumberGen seqNumGenRepo;

	@Override
	public ResponseEntity saveOrUpdateReturnReqHeader(ReturnRequestHeaderDto returnRequestHeaderDto) {
		try {

			validatingReturnRequestDtoData(returnRequestHeaderDto);

			String msg = returnRequestHeaderRepo.saveOrUpdateReturnReqHeader(returnRequestHeaderDto);
			if (msg == null) {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "CREATION_FAILED", ResponseStatus.FAILED);
			}
			return new ResponseEntity(returnRequestHeaderDto, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);

		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity listAllReturnReqHeaders() {
		try {
			List<ReturnRequestHeaderDto> list = returnRequestHeaderRepo.listAllReturnReqHeaders();
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

	@Override
	public ResponseEntity getReturnReqHeaderById(String returnReqNum) {
		try {
			if (!HelperClass.checkString(returnReqNum)) {
				ReturnRequestHeaderDto returnRequestHeaderDto = returnRequestHeaderRepo
						.getReturnReqHeaderById(returnReqNum);
				if (returnRequestHeaderDto != null) {
					return new ResponseEntity(returnRequestHeaderDto, HttpStatus.ACCEPTED,
							"Return Req Header is found for Key : " + returnReqNum, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT,
							"Return Req Header is not available for Key : " + returnReqNum, ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Return Request Num field are mandatory",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity deleteReturnReqHeaderById(String returnReqNum) {
		try {
			if (!HelperClass.checkString(returnReqNum)) {
				String msg = returnRequestHeaderRepo.deleteReturnReqHeaderById(returnReqNum);
				if (msg != null) {
					return new ResponseEntity("", HttpStatus.ACCEPTED, msg, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED",
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Return Request Num field are mandatory",
						ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	private void validatingReturnRequestDtoData(ReturnRequestHeaderDto returnRequestHeaderDto) {
		if (returnRequestHeaderDto.getReturnReqNum() == null) {
			returnRequestHeaderDto
					.setReturnReqNum(seqNumGenRepo.getNextSeqNumber("RR_", 6, returnRequestHeaderRepo.getSession()));
			if (!returnRequestHeaderDto.getReturnItemList().isEmpty()) {
				for (ReturnItemDto returnItemDto : returnRequestHeaderDto.getReturnItemList()) {
					returnItemDto.setReturnReqItemId(
							seqNumGenRepo.getNextSeqNumber("RI_", 6, returnRequestHeaderRepo.getSession()));
					returnItemDto.setReturnReqNum(returnRequestHeaderDto.getReturnReqNum());
				}
			}

			if (returnRequestHeaderDto.getExchangeHeaderDto() != null) {
				returnRequestHeaderDto.getExchangeHeaderDto().setExchangeReqNum(
						seqNumGenRepo.getNextSeqNumber("ER_", 6, returnRequestHeaderRepo.getSession()));
				returnRequestHeaderDto.getExchangeHeaderDto().setReturnReqNum(returnRequestHeaderDto.getReturnReqNum());
				if (!returnRequestHeaderDto.getExchangeHeaderDto().getExchangeItemList().isEmpty()) {
					for (ExchangeItemDto exchangeItemDto : returnRequestHeaderDto.getExchangeHeaderDto()
							.getExchangeItemList()) {
						exchangeItemDto.setExchangeReqItemId(
								seqNumGenRepo.getNextSeqNumber("EI_", 6, returnRequestHeaderRepo.getSession()));
						exchangeItemDto.setReturnReqNum(returnRequestHeaderDto.getReturnReqNum());
						exchangeItemDto
								.setExchangeReqNum(returnRequestHeaderDto.getExchangeHeaderDto().getExchangeReqNum());
					}
				}
			}
		}
	}
}
