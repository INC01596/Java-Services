package com.incture.cherrywork.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;
import com.incture.cherrywork.entities.ReturnRequestHeader;
import com.incture.cherrywork.entities.new_workflow.SalesOrderItemStatusDo;
import com.incture.cherrywork.entities.new_workflow.SalesOrderLevelStatusDo;
import com.incture.cherrywork.util.ComConstants;
import com.incture.cherrywork.workflow.entities.SalesOrderTaskStatusDo;

@Repository
public interface IReturnRequestHeaderRepository
		extends JpaRepository<ReturnRequestHeader, String>, ReturnRequestFilterDetailsCustomGeneric {

	ReturnRequestHeader findByReturnReqNum(String returnReqNum);

	@Query("from ReturnRequestHeader re where re.requestedBy=?1 order by re.createdAt desc")
	List<ReturnRequestHeader> getByReturnReqNumDraft(String requestedBy);
	
	@Query("select createdBy from ReturnRequestHeader where returnReqNum=?1")
	String findCreatedBy(String returnReqNum);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from return_request re where re.RETURN_REQ_NUM =?1 ", nativeQuery = true)
	int deleteByReturnReqNum(String returnReqNum);

	@Query(value = "ALTER SEQUENCE RR RESTART WITH 1", nativeQuery = true)
	@Modifying
	void resetSequenceGeneratorForReturnRequestNum();

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update so_item_status set item_status =?2, visiblity =?3 where item_status_serial_id =?1", nativeQuery = true)
	Integer updateItemStatusAndVisibility(String itemSerialId, Integer itemStatus, Integer visibility);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update so_item_status set so_item_status.item_status =?1, so_item_status.visiblity =?2 from so_level_status "
			+ "join so_task_status on so_level_status.LEVEL_STATUS_SERIAL_ID = so_task_status.LEVEL_STATUS_SERIAL_ID "
			+ "join so_item_status  on so_item_status.TASK_STATUS_SERIAL_ID = so_task_status.TASK_STATUS_SERIAL_ID "
			+ "where so_level_status.decision_set_id = ?3 and so_item_status.so_item_num = ?4 and "
			+ "so_task_status.task_id != ?5 and so_item_status.item_status = '9' ", nativeQuery = true)
	Integer updateItemStatusAndVisibilityForIIRCase(Integer itemStatus, Integer visibility, String decisionSetId,
			String itemNum, String taskId);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update so_task_status set task_status =?2, completed_by =?3 where task_status_serial_id =?1", nativeQuery = true)
	Integer updateTaskStatus(String taskSerialId, Integer taskStatus, String completedBy);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update so_level_status set level_status =?2 where level_status_serial_id =?1", nativeQuery = true)
	Integer updateLevelStatus(String levelSerialId, Integer levelStatus);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update sales_doc_item set reason_for_rejection =?3, reason_for_rejection_text =?4 "
			+ "where sales_order_num = ?1 and sales_order_item_num =?2", nativeQuery = true)
	Integer updateReasonOfRejectionInSalesOrder(String salesOrderNum, String salesItemNum, String reasonOfRejection,
			String reasonOfRejectionText);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update schedule_line set relfordel_text = '', schline_delivery_block = '' "
			+ "where sales_order_num = ?1 and sales_order_item_num =?2", nativeQuery = true)
	Integer removeBlockFromItem(String salesOrderNum, String salesItemNum);

	@Query(value = "select creationStatus from ReturnRequestHeader re where re.returnReqNum =?1")
	boolean getReturnOrderMessage(String returnReqNum);

	@Query(value = "select item_status from so_item_status where item_status_serial_id =?1", nativeQuery = true)
	Integer getItemStatus(String itemSerialId);
	
	@Query(value="from SalesOrderLevelStatusDo where decisionSetId =?1 and level =?2")
	Optional<SalesOrderLevelStatusDo> fetchLevelStatusDtoFromDecisionSetAndLevelRepository(String decisionSetId, String level);
	
	@Query(value="from SalesOrderTaskStatusDo where taskStatus !=?1 and taskStatusSerialId =?2")
	List<SalesOrderTaskStatusDo> getTaskStatusDataFromTaskSerialIdRepository(Integer taskStatus, String taskStatusSeriallId);
	
	@Query(value="from SalesOrderTaskStatusDo where taskStatus !=?1 and salesOrderLevelStatus.levelStatusSerialId =?2")
	List<SalesOrderTaskStatusDo> getAllTaskFromLevelSerialIdRepository(Integer taskStatus, String levelStatusSeriallId);
	
	@Query("select distinct returnReqNum from ReturnItem where sapReturnOrderNum=?1")
	String findReturnReqNum(String salesOrderNum);
	
	// Sandeep Kumar Khatri

	// @Query("from ReturnRequestHeader r where r.returnReqNum=:reqNo")
	// Page<ReturnRequestHeader>findAll1(@Param("reqNo") String reqNo,Pageable
	// pageable);
	//
	// @Query("from ReturnRequestHeader r where r.division=:division")
	// Page<ReturnRequestHeader>findAllD(@Param("division") String
	// division,Pageable pageable);
	//
	// @Query("from ReturnRequestHeader r where r.salesOrg=:salesOrg")
	// Page<ReturnRequestHeader>findAllS(@Param("salesOrg")String
	// salesOrg,Pageable pageable);
	//
	// @Query("from ReturnRequestHeader r where r.customerId=:cust")
	// Page<ReturnRequestHeader>findAllC(@Param("cust")String cust,Pageable
	// pageable);
	//
	// @Query("from ReturnRequestHeader r where r.createdDate=:stDate")
	// Page<ReturnRequestHeader>findAllD(@Param("stDate") Date stDate,Pageable
	// pageable);
	//
	// @Query("from ReturnRequestHeader r where r.distributionChannel=:channel")
	// Page<ReturnRequestHeader>findAllCha(@Param("channel") String
	// channel,Pageable pageable);
	//
	//// @Query("from ReturnRequestHeader r where r.returnReason=:reason")
	//// Page<ReturnRequestHeader>findAllR(@Param("reason") String
	// reason,Pageable pageable);
	////
	//
//	 @Query("from ReturnRequestHeader r where r.division=:division andr.distributionChannel=:channel")
//	 Page<ReturnRequestHeader>findAll(@Param("division")String division,@Param("channel") String channel,Pageable pageable);
	
	//
	//
	//
	// @Query("from ReturnRequestHeader r where r.customerId=:cust and
	// r.distributionChannel=:channel and r.divison=:div and
	// r.returnReason=:returnReason and r.salesOrg=:salesOrg and
	// r.createdDate=:stdate")
	// Page<ReturnRequestHeader> findByAll(@Param("cust")String cust,
	// @Param("channel")String channel,@Param("div") String
	// div,@Param("returnReason")String returnReason,@Param("salesOrg") String
	// salesOrg, @Param("stdate") Date stdate, Pageable pageable);
	//
	// //without created date rest all
	// @Query("from ReturnRequestHeader r where r.customerId=:cust and
	// r.distributionChannel=:channel and r.divison=:div and
	// r.returnReason=:returnReason and r.salesOrg=:salesOrg ")
	// Page<ReturnRequestHeader> findAll5C(@Param("cust")String cust,
	// @Param("channel")String channel,@Param("div") String
	// div,@Param("returnReason")String returnReason,@Param("salesOrg") String
	// salesOrg, Pageable pageable);
	//
	// //withoput customerId
	// @Query("from ReturnRequestHeader r where r.distributionChannel=:channel
	// and r.divison=:div and r.returnReason=:returnReason and
	// r.salesOrg=:salesOrg and r.createdDate=:stdate")
	// Page<ReturnRequestHeader> findAll5Cu(@Param("channel")String
	// channel,@Param("div") String div,@Param("returnReason")String
	// returnReason,@Param("salesOrg") String salesOrg, @Param("stdate") Date
	// stdate, Pageable pageable);
	//
	// // without distribution channel
	// @Query("from ReturnRequestHeader r where r.customerId=:cust and
	// r.divison=:div and r.returnReason=:returnReason and r.salesOrg=:salesOrg
	// and r.createdDate=:stdate")
	// Page<ReturnRequestHeader> findAll5Cha(@Param("cust")String
	// cust,@Param("div") String div,@Param("returnReason")String
	// returnReason,@Param("salesOrg") String salesOrg, @Param("stdate") Date
	// stdate, Pageable pageable);
	//
	// // withoutDiv
	// @Query("from ReturnRequestHeader r where r.customerId=:cust and
	// r.distributionChannel=:channel and r.returnReason=:returnReason and
	// r.salesOrg=:salesOrg and r.createdDate=:stdate")
	// Page<ReturnRequestHeader> findAll5Div(@Param("cust")String cust,
	// @Param("channel")String channel,@Param("returnReason")String
	// returnReason,@Param("salesOrg") String salesOrg, @Param("stdate") Date
	// stdate, Pageable pageable);
	//
	// //without returnReason
	// @Query("from ReturnRequestHeader r where r.customerId=:cust and
	// r.distributionChannel=:channel and r.divison=:div and
	// r.salesOrg=:salesOrg and r.createdDate=:stdate")
	// Page<ReturnRequestHeader> findAll5R(@Param("cust")String cust,
	// @Param("channel")String channel,@Param("div") String
	// div,@Param("salesOrg") String salesOrg, @Param("stdate") Date stdate,
	// Pageable pageable);
	//
	//
	// //without SalesOrg
	// @Query("from ReturnRequestHeader r where r.customerId=:cust and
	// r.distributionChannel=:channel and r.divison=:div and
	// r.returnReason=:returnReason and r.createdDate=:stdate")
	// Page<ReturnRequestHeader> findAll5S(@Param("cust")String cust,
	// @Param("channel")String channel,@Param("div") String
	// div,@Param("returnReason")String returnReason, @Param("stdate") Date
	// stdate, Pageable pageable);
	//
}
