package com.incture.cherrywork.dao;
import java.util.List;

import com.incture.cherrywork.dtos.SalesOrderHistoryDto;


public interface SalesOrderHistoryDao {

	public String saveSalesOrder(SalesOrderHistoryDto salesOrderHistoryDto);

	public SalesOrderHistoryDto getSalesOrderByItemNum(String salesOrderHeaderNum, String salesOrderItemNum);

	public List<SalesOrderHistoryDto> listAllSalesOrderHistoryData();

	public List<SalesOrderHistoryDto> getSalesOrderHistoryByItemNum(String salesOrderHeaderNum,
			String salesOrderItemNum);

}

