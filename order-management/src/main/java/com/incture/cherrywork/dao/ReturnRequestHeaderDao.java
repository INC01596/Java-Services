package com.incture.cherrywork.dao;

import java.util.List;

import com.incture.cherrywork.dtos.ReturnRequestHeaderDto;
import com.incture.cherrywork.exceptions.ExecutionFault;


public interface ReturnRequestHeaderDao {

	public String saveOrUpdateReturnReqHeader(ReturnRequestHeaderDto returnRequestHeaderDto) throws ExecutionFault;

	public List<ReturnRequestHeaderDto> listAllReturnReqHeaders();

	public ReturnRequestHeaderDto getReturnReqHeaderById(String returnReqNum);

	public String deleteReturnReqHeaderById(String returnReqNum) throws ExecutionFault;
}