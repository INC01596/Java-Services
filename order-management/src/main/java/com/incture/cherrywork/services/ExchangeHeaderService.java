package com.incture.cherrywork.services;

import com.incture.cherrywork.dtos.ExchangeHeaderDto;
import com.incture.cherrywork.dtos.ResponseEntity;

public interface ExchangeHeaderService {

	public ResponseEntity saveOrUpdateExchangeHeader(ExchangeHeaderDto exchangeHeaderDto);

	public ResponseEntity listAllExchangeHeaders();

	public ResponseEntity getExchangeHeaderById(String exchangeReqNum, String returnReqNum);

	public ResponseEntity deleteExchangeHeaderById(String exchangeReqNum, String returnReqNum);

}
