//package com.incture.cherrywork.services;
//
//
//
//
//
//import java.util.List;
//import java.util.Optional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.incture.cherrywork.dtos.ReturnFilterDto;
//import com.incture.cherrywork.entities.ReturnRequestHeader;
//import com.incture.cherrywork.repositories.IReturnRequestHeaderRepository;
//import com.incture.cherrywork.repositories.IReturnRequestHeaderRepositoryNew;
//import com.incture.cherrywork.repositories.ServicesUtils;
//
//
//
//
//
//@Service
//@Transactional
//public class ReturnRequestHeaderServiceImpl implements ReturnRequestHeaderService {
//
//	@Autowired
//	private IReturnRequestHeaderRepository repo;
//	
//	
//
//     
//	
//	    @Override
//		public ResponseEntity<Object>findAll(int pageNo)
//		{
//			
//		//String str="from ReturnRequestHeader";
//		//Query q=em.createQuery(str);
//	    	Pageable pageable=PageRequest.of(pageNo-1,10);
//		Page<ReturnRequestHeader>list=repo.findAll(pageable);
//		return ResponseEntity.ok().body(list);
//			
//		}
//
//
//
//
//
//
//	@Override
//	public ResponseEntity<Object> listAllReturn(ReturnFilterDto dto) {
//		if(!ServicesUtils.isEmpty(dto.getReturnReqNumber()))
//	{
//		
//	      List<ReturnRequestHeader>list=repo.findAll1(dto.getReturnReqNumber());
//		  return ResponseEntity.ok().body(list);
//	}
//		if(!ServicesUtils.isEmpty(dto.getDivision()))
//		{
//			List<ReturnRequestHeader>list=repo.findAll(dto.getDivision());
//			return ResponseEntity.ok().body(list);
//		}
//		return null;
//	}
//
//
//
//
//
//
//	
//	public ResponseEntity<Object> listAllReturnReqHeaders() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//
//
//
//
//
//
//	
//	
//}
