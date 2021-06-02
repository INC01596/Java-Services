package com.incture.cherrywork.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.ReturnItem;
import com.incture.cherrywork.entities.ReturnItemPk;

@Repository
public interface IReturnRequestItemRepository extends JpaRepository<ReturnItem, ReturnItemPk>{
	
	
	@Query("from ReturnItem  where returnReqNum =?1")
	List<ReturnItem> findByReturnReqNum(String returnReqNum);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "delete from return_item rr where rr.RETURN_REQ_NUM =?1", nativeQuery = true)
	int  deleteByReturnReqNum(String returnReqNum);
	
	@Query("select i from ReturnItem i join  ReturnRequestHeader h on i.returnReqNum = h.returnReqNum where h.requestedBy=?1 ")
	List<ReturnItem>  getByRequestedByReturnReqNum(String requestedBy);
	
	ReturnItem findByReturnReqItemidAndReturnReqNum(String returnReqItemid, String returnReqNum);
	
	ReturnItem findByRefDocItemAndReturnReqNumAndRefDocNum( String refDocItem, String returnReqNum , String refDocNum);



}
