package com.incture.cherrywork.dao;



import java.util.List;

import com.incture.cherrywork.dtos.SalesDocItemDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderInput;
import com.incture.cherrywork.exceptions.ExecutionFault;



public interface SalesDocItemDao {

	public String saveOrUpdateSalesDocItem(SalesDocItemDto salesDocItemDto) throws ExecutionFault;

	public List<SalesDocItemDto> listAllSalesDocItems();

	public SalesDocItemDto getSalesDocItemById(String salesItemId, String salesHeaderId);

	public String deleteSalesDocItemById(String salesItemId, String salesHeaderId) throws ExecutionFault;

	public List<SalesDocItemDto> listOfItemsInSalesOrder(String salesHeaderId);

	public List<SalesDocItemDto> listOfItemsFromMultiItemId(SalesOrderHeaderInput soInput);

	public String updateSalesDocItemWithDecisionSet(String decisionSet, String salesItemId, String salesHeaderId)
			throws ExecutionFault;

	public List<SalesDocItemDto> getSalesDocItemsByDecisionSetId(String decisionSetId);

	public List<String> getItemListByDataSet(String dataSet);

	public String saveOrUpdateSalesDocItemForDS(SalesDocItemDto salesDocItemDto) throws ExecutionFault;

	public List<String> getDSBySalesHeaderID(String salesHeaderId);  
	
	

}
