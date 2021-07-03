
package com.incture.cherrywork.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.entities.ReturnRequestHeader;
@Repository
public interface IReturnRequestHeaderRepository extends JpaRepository<ReturnRequestHeader, String>, ReturnRequestFilterDetailsCustomGeneric {

	
	ReturnRequestHeader findByReturnReqNum(String returnReqNum);

	@Query("from ReturnRequestHeader re where re.requestedBy=?1 order by re.createdAt desc")
	List<ReturnRequestHeader> getByReturnReqNumDraft(String requestedBy);

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

	
	
	 @Query("from ReturnRequestHeader r where r.returnReqNum=:reqNo")
     List<ReturnRequestHeader>findAll1(@Param("reqNo") String reqNo);
    
     @Query("from ReturnRequestHeader r where r.division=:division")
     List<ReturnRequestHeader>findAll(@Param("division") String division);
    
     @Query("from ReturnRequestHeader r where r.division=:division and r.distributionChannel=:channel")
     List<ReturnRequestHeader>findAll(@Param("division")String division ,@Param("channel") String channel);

}
