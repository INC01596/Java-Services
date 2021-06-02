package com.incture.cherrywork.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.SalesDocItemDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderInput;



public interface SalesDocItemService {

	public ResponseEntity saveOrUpdateSalesDocItem(SalesDocItemDto salesDocItemDto);

	public ResponseEntity removeItemDeliveryBlockFromSalesDocItem(String salesHeaderId, String salesItemId);

	public ResponseEntity addReasonOfRejectionFromSalesDocItem(String salesHeaderId, String salesItemId,
			String reasonOfRejection);

	public ResponseEntity listAllSalesDocItems();

	public ResponseEntity getSalesDocItemById(String salesItemId, String salesHeaderId);

	public ResponseEntity deleteSalesDocItemById(String salesItemId, String salesHeaderId);

	public ResponseEntity getSalesDocItemsBySalesHeaderId(String salesHeaderId);

	public ResponseEntity getSalesDocItemsFromSalesOrderInputs(SalesOrderHeaderInput soInput);

	public ResponseEntity getInputDtoDataSet(SalesOrderHeaderInput soInput);

	public ResponseEntity updateSalesDocItemWithDecisionSet(String decisionSet, String salesItemId,
			String salesHeaderId);

	public ResponseEntity triggerImePostSoDsCompletion(String salesOrderNumber, String decisionSetId)
			throws URISyntaxException, IOException;

	public ResponseEntity checkForNextLevelTrigger(String dataSet, String level);

	public ResponseEntity updateLevelStatusAbandond(List<SalesOrderLevelStatusDto> saleOrderLevelStatusDoList);

	public ResponseEntity saveOrUpdateSalesDocItemUsingMerge(SalesDocItemDto salesDocItemDto);

}

