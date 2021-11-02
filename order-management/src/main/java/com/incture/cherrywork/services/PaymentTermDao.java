package com.incture.cherrywork.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.PaymentTermDo;
import com.incture.cherrywork.repositories.PaymentRepo;
import com.incture.cherrywork.util.ServicesUtil;

@Service
@Transactional
public class PaymentTermDao implements PaymentTermDaoLocal {

	//private static final Logger logger = LoggerFactory.getLogger(PaymentTermDao.class);
@Autowired
private PaymentRepo repo;
	
	@Override
	public ResponseEntity<Object> getTerm(String custId) {

		String term = "";
     try{
		if (custId != null) {

		return ResponseEntity.ok().body(repo.findById(custId));
		}
		else return null;

		//return term;

	}catch(Exception e){
		 e.printStackTrace();
		 return null;}
}

	@Override
	public void saveTerms(List<PaymentTermDo> paymentTermDoList) {

	
		try {
    if(!ServicesUtil.isEmpty(paymentTermDoList))
    {
    	for(PaymentTermDo d:paymentTermDoList){
    
			repo.save(d);
    	}}
			

		} catch (Exception e) {

			//logger.error("[PaymentTermDao][saveTerms]:" + e.getMessage());
		}

	}
}

