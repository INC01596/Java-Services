package com.incture.cherrywork.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.FilterDetailDto;
import com.incture.cherrywork.dtos.SupportUserFunctionTaskItemDataDto;
import com.incture.cherrywork.entities.ExchangeItem;
import com.incture.cherrywork.entities.ReturnItem;
import com.incture.cherrywork.entities.ReturnRequestHeader;


@Repository
public interface ReturnRequestFilterDetailsCustomGeneric {

	List<ReturnItem> filterDetails(FilterDetailDto filterDetailDto,
			Map<String, List<String>> mapForInclusionAttributes, Map<String, List<String>> mapForExclusionAttributes,
			Boolean flagForAllRights, Boolean flagForEmptyInput);

//	List<SalesDocHeaderDto> fetchSalesOrdersFromCustomerPoList(List<String> customerPoList);
//
//	List<SalesDocHeaderDto> fetchSalesOrdersFromSalesOrderNum(String salesOrderNum);
//
//	List<String> fetchSalesOrderNumsFromOrderCreationDate(String startDate, String endDate);
//
//	Double findTotalAmountOnCustomerPo(String customerPo);
//
//	List<ItemDataInReturnOrderDto> fetchItemDataInReturnOrderHavingTaskDtoList(String userId,
//			List<String> salesOrderNumList, Map<String, List<String>> mapForInclusionAttributes,
//			Map<String, List<String>> mapForExclusionAttributes, Boolean flagForAllRightsItemLevel);
//
//	List<ItemDataInReturnOrderDto> fetchItemDataInReturnOrderHavingTaskDtoListForNewDac(String userId,
//			List<String> salesOrderNumList, Map<String, String> mapOfAttributeValues,
//			Boolean flagForAllRightsItemLevel);
//
//	List<SalesOrderDto> fetchSalesOrdersFromCustomerPo(String customerPo);
//
//	List<ItemStatusTrackingLevelMsgDto> fetchSalesOrderItemsLevelStatusFromOnSubmit(List<String> decisionSetIdList,
//			List<String> levelIdList, List<String> itemNumList);
//
//	List<SalesOrderLevelStatusDto> fetchLevelStatusDtoFromDecisionSetAndLevelList(String decisionSetId, String levelId);
//
//	SalesOrderLevelStatusDto fetchLevelStatusDtoFromDecisionSetAndLevel(String decisionSetId, String levelId);
//
//	List<SalesOrderTaskStatusDto> getAllTaskFromLevelSerialId(String levelSerialId);
//
//	List<String> getAllTaskIdsFromCompletedByList(List<String> completedByList);
//
//	List<SalesOrderTaskStatusDto> getAllTaskFromDecisionSetAndLevel(String decisionSet, String level);
//
//	List<SalesOrderTaskStatusDto> getTaskStatusDataFromTaskSerialId(String taskSerialStatusId);
//
//	List<SalesOrderTaskStatusDto> getTaskStatusDataFromTaskId(String taskId);
//
//	List<SalesOrderItemStatusDto> getItemStatusDataUsingTaskSerialId(String taskSerialId);
//
//	List<SalesOrderItemStatusDto> getItemStatusDataItemStatusAsBlockedFromTaskSerialId(String taskSerialId);
//
//	List<SalesOrderItemStatusDto> getItemStatusDataUsingDecisionSetAndLevelAndItemNo(String decisionSet, String level,
//			String itemNo);
//
//	List<SalesOrderItemStatusDto> getItemStatusDataUsingDecisionSetForBlockedItems(String decisionSetId);

	List<ReturnRequestHeader> getReturnHeaderDataFromReturnNumList(List<String> returnReqNumList);

	List<ReturnRequestHeader> getFilterDetails(String query);

//	List<SupportUserFunctionTaskItemDataDto> fetchItemDataFromItemNumList(List<String> itemNumList, String orderNum);

	public List<ReturnRequestHeader> fetchReturnOrderForCreatReturnNewDac(String userId,
			Map<String, String> mapOfAttributeValues, Boolean flagForAllRightsItemLevel);

	public List<ReturnItem> fetchReturnOrderForCreatReturnItemNewDac(String returnReqNum,
			Map<String, String> mapOfAttributeValues, Boolean flagForAllRightsItemLevel);

	public List<ExchangeItem> fetchReturnOrderForCreatExchangeItemNewDac(String returnReqNum,
			Map<String, String> mapOfAttributeValues, Boolean flagForAllRightsItemLevel);

	public List<ReturnRequestHeader> fetchReturnOrderForCreatReturnFilterNewDac(String userId,
			Map<String, String> mapOfAttributeValues, Boolean flagForAllRightsItemLevel, String retunRequestNum);

}
