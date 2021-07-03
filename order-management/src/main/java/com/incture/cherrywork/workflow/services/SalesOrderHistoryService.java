package com.incture.cherrywork.services;

import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.SalesOrderHistoryDto;

public interface SalesOrderHistoryService {

	public ResponseEntity saveSalesOrderItem(SalesOrderHistoryDto salesOrderHistoryDto);

	public ResponseEntity listAllSalesOrders();

	public ResponseEntity listHistoryOfSalesOrderBasedOnVersion(String salesOrderHeaderNum, String salesOrderItemNum);

}
