package com.incture.cherrywork.dao;



import java.util.List;

import com.incture.cherrywork.dtos.FilterDto;
import com.incture.cherrywork.dtos.SalesDocHeaderDto;
import com.incture.cherrywork.entities.SalesDocHeaderDo;
import com.incture.cherrywork.exceptions.ExecutionFault;


public interface SalesDocHeaderDao {

	public String saveOrUpdateSalesDocHeader(SalesDocHeaderDto salesDocHeaderDto) throws ExecutionFault;

	public List<SalesDocHeaderDto> listAllSalesDocHeaders();

	public SalesDocHeaderDto getSalesDocHeaderById(String salesHeaderOrderId);

	public SalesDocHeaderDto getSalesDocHeaderByIdWithOutFlag(String salesHeaderOrderId);

	public List<SalesDocHeaderDto> getSalesDocHeadersWithItems(List<String> salesOrderNumList);

	public String deleteSalesDocHeaderById(String salesHeaderOrderId) throws ExecutionFault;

	public List<SalesDocHeaderDto> listAllSalesDocHeaderWithoutItems();

	public List<String> filteredSalesDocHeader(FilterDto filterData) throws ExecutionFault;

	public String getRequestIdWithSoHeader(String salesHeaderOrderId);

	public SalesDocHeaderDto getSalesDocHeaderWithoutItemsById(String salesHeaderOrderId);

	public Integer testQuery() throws ExecutionFault;

	public List<String> createQueryforRights(FilterDto filterData);

	public SalesDocHeaderDto getSalesDocHeaderByIdSession(String salesOrderNum);

	public String saveSalesDocHeader(SalesDocHeaderDto salesDocHeaderDto) throws ExecutionFault;

	public String updateSalesDocHeader(SalesDocHeaderDto salesDocHeaderDto) throws ExecutionFault;
	public SalesDocHeaderDto exportDto(SalesDocHeaderDo entity);
	public SalesDocHeaderDo importDto(SalesDocHeaderDto fromDto);

}
