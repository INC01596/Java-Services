package com.incture.cherrywork.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dao.ReturnItemDao;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.ReturnItemDto;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.HelperClass;




@Transactional
@Service
public class ReturnItemServiceImpl implements ReturnItemService {

	@Autowired
	private ReturnItemDao returnItemRepo;

	@Override
	public ResponseEntity saveOrUpdateReturnItem(ReturnItemDto returnItemDto) {
		try {
			// Checking Primary Key here
			if (!HelperClass.checkString(returnItemDto.getReturnReqNum())) {
				String msg = returnItemRepo.saveOrUpdateReturnItem(returnItemDto);
				if (msg == null) {
					return new ResponseEntity("", HttpStatus.BAD_REQUEST, "CREATION_FAILED", ResponseStatus.FAILED);
				}
				return new ResponseEntity(returnItemDto, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Return Request Id, Return Item id fields are mandatory", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity listAllReturnItems() {
		try {
			List<ReturnItemDto> list = returnItemRepo.listAllReturnItems();
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
	public ResponseEntity getReturnItemById(String returnReqNum, String returnReqItemid) {
		try {
			// Checking Primary Key here
			if (!HelperClass.checkString(returnReqNum) && returnReqItemid != null) {
				ReturnItemDto returnItemDto = returnItemRepo.getReturnItemById(returnReqNum, returnReqItemid);
				if (returnItemDto != null) {
					return new ResponseEntity(returnItemDto, HttpStatus.ACCEPTED,
							"Return Item is found for Key : " + returnReqNum + " and " + returnReqItemid,
							ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT,
							"Return Item is not available for Key : " + returnReqNum + " and " + returnReqItemid,
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Return Request Id, Return Item id fields are mandatory", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity deleteReturnItemById(String returnReqNum, String returnReqItemid) {
		try {
			if (!HelperClass.checkString(returnReqNum) && returnReqItemid != null) {
				String msg = returnItemRepo.deleteReturnItemById(returnReqNum, returnReqItemid);
				if (msg != null) {
					return new ResponseEntity("", HttpStatus.ACCEPTED, msg, ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED",
							ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST,
						"Return Request Id, Return Item id fields are mandatory", ResponseStatus.FAILED);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

}

