package com.incture.cherrywork.workflow.services;


import java.util.List;
import java.util.Map;

import com.incture.cherrywork.dtos.SalesDocItemDto;
import com.incture.cherrywork.rules.ApproverDataOutputDto;



public interface DecisionSetCreation {
	
	
	public Map<String, List<ApproverDataOutputDto>> createAndReturnApprovalMap(String requestId, String salesOrderId,
			List<SalesDocItemDto> salesDocItemDtolist, String strategy, String distributionChannel, String salesOrg,String country,String customerPo,String requestType,String requestCategory);

}
