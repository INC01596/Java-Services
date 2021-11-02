package com.incture.cherrywork.services;



import java.util.List;

import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.PaymentTermDo;



public interface PaymentTermDaoLocal {

	ResponseEntity getTerm(String custId);

	void saveTerms(List <PaymentTermDo> paymentTermDoList);

}