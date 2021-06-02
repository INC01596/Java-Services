package com.incture.cherrywork.returns.dao;



import java.util.List;

import com.incture.cherrywork.dtos.ExchangeHeaderDto;
import com.incture.cherrywork.exceptions.ExecutionFault;



public interface ExchangeHeaderDao {

	public String saveOrUpdateExchangeHeader(ExchangeHeaderDto exchangeHeaderDto) throws ExecutionFault;

	public List<ExchangeHeaderDto> listAllExchangeHeaders();

	public ExchangeHeaderDto getExchangeHeaderById(String exchangeReqNum, String returnReqNum);

	public String deleteExchangeHeaderById(String exchangeReqNum, String returnReqNum) throws ExecutionFault;

}
