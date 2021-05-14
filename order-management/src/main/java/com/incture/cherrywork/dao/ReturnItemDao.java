package com.incture.cherrywork.dao;

import java.util.List;

import com.incture.cherrywork.dtos.ReturnItemDto;
import com.incture.cherrywork.exceptions.ExecutionFault;



public interface ReturnItemDao {

	public String saveOrUpdateReturnItem(ReturnItemDto returnItemDto) throws ExecutionFault;

	public List<ReturnItemDto> listAllReturnItems();

	public ReturnItemDto getReturnItemById(String returnReqNum, String returnReqItemid);

	public String deleteReturnItemById(String returnReqNum, String returnReqItemid) throws ExecutionFault;

}