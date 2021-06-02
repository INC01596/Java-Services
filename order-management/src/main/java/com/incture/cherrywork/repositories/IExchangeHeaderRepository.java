package com.incture.cherrywork.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.ExchangeHeader;
import com.incture.cherrywork.entities.ExchangeHeaderPk;

@Repository
public interface IExchangeHeaderRepository extends JpaRepository<ExchangeHeader, ExchangeHeaderPk> {
	/*
	 * ExchangeHeaderDo findByexchangeReqNumAndreturnReqNum(ExchangeHeaderPkDo
	 * pkDo);
	 */
	@Query("from ExchangeHeader ee where ee.returnReqNum =?1 order by ee.createdAt")
	ExchangeHeader findByReturnReqNum(String returnReqNum);

	@Query("from ExchangeHeader where createdBy=?1")
	List<ExchangeHeader> findByCreatedBy(String createdBy);

	@Modifying(clearAutomatically = true)
	@Query(value = "delete from exchange_header ee where ee.RETURN_REQ_NUM =?1 and ee.EXCHANGE_REQ_NUM=?2", nativeQuery = true)
	int deleteByReturnReqNum(String returnReqNum, String exchangeReqNum);

	@Query("from ExchangeHeader ee where ee.returnReqNum =?1 and ee.exchangeReqNum =?2")
	ExchangeHeader findByReturnReqNumAndExchangeReqNum(String returnReqNum, String exchangeReqNum);

}