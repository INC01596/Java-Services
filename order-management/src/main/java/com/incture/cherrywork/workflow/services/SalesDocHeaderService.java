package com.incture.cherrywork.workflow.services;

import java.util.List;

import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.SalesDocHeaderDto;



public interface SalesDocHeaderService {

	public ResponseEntity saveSalesDocHeader(SalesDocHeaderDto salesDocHeaderDto);
	
	public ResponseEntity updateSalesDocHeaderForSchedular(SalesDocHeaderDto salesDocHeaderDto);

	public ResponseEntity listAllSalesDocHeaders();

	public ResponseEntity getSalesDocHeaderById(String salesOrderId);

	public ResponseEntity deleteSalesDocHeaderById(String salesOrderId);

	public ResponseEntity listAllSalesDocHeadersWithoutItem();

	//public ResponseEntity filteredSalesDocHeader(FilterDto filterData);

	public ResponseEntity getSalesDocHeadersWithItems(List<String> salesOrderNumList);

	public ResponseEntity getSalesDocHeadersWithoutItems(String salesOrderNum);

	public ResponseEntity getRequestIdBySoNum(String salesOrderId);
	
	public ResponseEntity getSalesDocHeaderByDecisionSetIdWithItems(String salesOrderNum, String decisionSetId);


}

