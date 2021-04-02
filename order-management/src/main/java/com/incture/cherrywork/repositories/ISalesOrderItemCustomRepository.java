package com.incture.cherrywork.repositories;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.dtos.SalesOrderLookUpDto;

@Repository
public interface ISalesOrderItemCustomRepository {
	List<SalesOrderLookUpDto> getPaymentTerms();
	List<SalesOrderLookUpDto> getIncoTerms();
	List<SalesOrderLookUpDto> getQualityTest();
	List<SalesOrderLookUpDto> getDeliveryTolerance();
	List<SalesOrderLookUpDto> getDistributionChannel();
	String getLookupValue(String key);
	

}
