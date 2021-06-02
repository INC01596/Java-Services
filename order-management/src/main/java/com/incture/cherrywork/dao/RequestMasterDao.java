package com.incture.cherrywork.dao;


import java.util.List;

import com.incture.cherrywork.dtos.RequestMasterDto;
import com.incture.cherrywork.exceptions.ExecutionFault;



public interface RequestMasterDao {

	public String saveOrUpdateRequestMaster(RequestMasterDto reqMasterDto) throws ExecutionFault;

	public List<RequestMasterDto> listAllRequests();

	public List<RequestMasterDto> getRequestMasterById(String reqId);

	public String deleteRequestMasterById(String reqId) throws ExecutionFault;

	public String deleteRequestMasterByRefDocNum(String salesOrderNum) throws ExecutionFault;

	public RequestMasterDto getRequestMasterByRefDocNumber(String refDocNumebr);

	public String saveOrUpdateRequestMasterStatus(RequestMasterDto reqMasterDto) throws ExecutionFault;

	public String UpdateRequestMaster(RequestMasterDto reqMasterDto) throws ExecutionFault;

}
