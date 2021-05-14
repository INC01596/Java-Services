package com.incture.cherrywork.dao;

import java.util.List;

import com.incture.cherrywork.dtos.ExchangeItemDto;
import com.incture.cherrywork.exceptions.ExecutionFault;


public interface ExchangeItemDao {

	public String saveOrUpdateExchangeItem(ExchangeItemDto exchangeItemDto) throws ExecutionFault;

	public List<ExchangeItemDto> listAllExchangeItems();

	public ExchangeItemDto getExchangeItemById(String returnReqNum, String exchangeReqItemid, String exchangeReqNum);

	public String deleteExchangeItemById(String returnReqNum, String exchangeReqItemid, String exchangeReqNum)
			throws ExecutionFault;

}
