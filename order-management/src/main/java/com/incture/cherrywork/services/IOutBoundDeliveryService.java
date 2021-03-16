package com.incture.cherrywork.services;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.OutBoundDeliveryDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderDto;


public interface IOutBoundDeliveryService {
	

public ResponseEntity<?> createOutBoundDeliveryOnSubmit(OutBoundDeliveryDto outBoundDeliveryDto)
			throws URISyntaxException, IOException;

public ResponseEntity<Object> create(OutBoundDeliveryDto outBoundDeliveryDto);
public ResponseEntity<Object> read(String obdNumber);
public ResponseEntity<Object> update(String obdNumber, OutBoundDeliveryDto outBoundDeliveryDto);
public ResponseEntity<Object> delete(String obdNumber);
public ResponseEntity<Object> readAll(String search);
}
