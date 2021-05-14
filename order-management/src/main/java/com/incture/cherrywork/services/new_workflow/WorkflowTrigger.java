package com.incture.cherrywork.services.new_workflow;

import com.incture.cherrywork.dtos.WorkflowResponseEntity;

public interface  WorkflowTrigger {
		
		public WorkflowResponseEntity AprrovalWorkflowTrigger(String salesOrderNo,String requestId,String approver,String level,String dataSet);
		
		public WorkflowResponseEntity DecisionSetWorkflowTrigger(String salesOrderNo, String requestId, String keydataSet,
				String approverDtoList, double threshold, double decisionSetAmount, String headerBlocReas,
				String soCreatedECC, String country, String customerPo, String requestType, String requestCategory,
				String salesOrderType, String soldToParty, String shipToParty, String division, String distributionChannel,
				String salesOrg,String returnReason);
		
		
}