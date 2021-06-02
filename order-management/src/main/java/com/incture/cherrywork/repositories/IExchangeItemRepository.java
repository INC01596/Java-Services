package com.incture.cherrywork.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.ExchangeItem;
import com.incture.cherrywork.entities.ExchangeItemPk;

@Repository
public interface IExchangeItemRepository extends JpaRepository<ExchangeItem, ExchangeItemPk> {

	/*ExchangeItemDo findByexchangeReqNumAndexchangeReqItemidAndreturnReqNum(ExchangeItemPkDo pkDo);*/

	@Query("from ExchangeItem  where returnReqNum =?1")
	List<ExchangeItem> findByReturnRegNum(String returnReqNum);
	
	
	List<ExchangeItem> findByReturnReqNumAndExchangeReqNum(String returnReqNum,String exchangeReqNum);
	
	@Modifying(clearAutomatically = true)
	//@Query(value ="delete from exchange_item ee where ee.RETURN_REQ_NUM =?1 and ee.EXCHANGE_REQ_NUM=?2", nativeQuery = true)
	@Query(value ="delete from exchange_item ee where ee.RETURN_REQ_NUM =?1", nativeQuery = true)
	int deleteByReturnRegNum(String returnReqNum);
	
	@Query("select i from ExchangeItem i join  ExchangeHeader h on i.returnReqNum = h.returnReqNum where h.createdBy=?1 ")
	List<ExchangeItem>  getExchangeByRequestedByReturnReqNum(String createdBy);
	
	
	ExchangeItem findByExchangeReqNumAndExchangeReqItemid(String exchangeReqNum,String exchangeReqItemid );

	ExchangeItem findByExchangeReqNumAndRefDocItemAndRefDocNum(String exchangeReqNum,String refDocItem,String refDocNum);
}
