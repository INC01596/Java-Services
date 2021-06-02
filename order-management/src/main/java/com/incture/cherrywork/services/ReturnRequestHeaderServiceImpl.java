package com.incture.cherrywork.services;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dtos.ReturnFilterDto;
import com.incture.cherrywork.entities.ReturnRequestHeader;

 
import com.incture.cherrywork.repositories.IReturnRequestHeaderRepositoryNew;




@Service
@Transactional
public class ReturnRequestHeaderServiceImpl implements ReturnRequestHeaderService {

	@Autowired
	private IReturnRequestHeaderRepositoryNew repo;
	
	

	

	@Override
	public ResponseEntity<Object> listAllReturnReqHeaders(ReturnFilterDto dto) {
		try {
			Pageable pageable=PageRequest.of(dto.getPageNo()-1,10);
			Page <ReturnRequestHeader> p=repo.findAll(dto,pageable);
			System.err.println("hell1");
			System.err.println(p);
			return ResponseEntity.ok().body(p);
			
		} 
			
		 catch (Exception e) {
			return null;		
			
			
			
	}
}

//	@Override
//	public ResponseEntity<Object> getReturnReqHeaderById(String returnReqNum) {
//		try {
//			if (!HelperClass.checkString(returnReqNum)) {
//				ReturnRequestHeaderDto returnRequestHeaderDto = returnRequestHeaderRepo
//						.getReturnReqHeaderById(returnReqNum);
//				if (returnRequestHeaderDto != null) {
//					return new ResponseEntity(returnRequestHeaderDto, HttpStatus.ACCEPTED,
//							"Return Req Header is found for Key : " + returnReqNum, ResponseStatus.SUCCESS);
//				} else {
//					return new ResponseEntity("", HttpStatus.NO_CONTENT,
//							"Return Req Header is not available for Key : " + returnReqNum, ResponseStatus.FAILED);
//				}
//			} else {
//				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Return Request Num field are mandatory",
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
//	public ResponseEntity deleteReturnReqHeaderById(String returnReqNum) {
//		try {
//			if (!HelperClass.checkString(returnReqNum)) {
//				String msg = Repo.deleteReturnReqHeaderById(returnReqNum);
//				if (msg != null) {
//					return new ResponseEntity("", HttpStatus.ACCEPTED, msg, ResponseStatus.SUCCESS);
//				} else {
//					return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED",
//							ResponseStatus.FAILED);
//				}
//			} else {
//				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Return Request Num field are mandatory",
//						ResponseStatus.FAILED);
//			}
//		} catch (Exception e) {
//			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
//			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
//					ResponseStatus.FAILED);
//		}
//	}
//
//	

	
	
}
