package com.incture.cherrywork.services;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.incture.cherrywork.dtos.OutBoundDeliveryItemDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.entities.OutBoundDelivery;
import com.incture.cherrywork.entities.OutBoundDeliveryItem;
import com.incture.cherrywork.repositories.IOutBoundDeliveryItemRepository;
import com.incture.cherrywork.repositories.IOutBoundDeliveryRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;

@Service
@Transactional
public class OutBoundDeliveryItemService implements IOutBoundDeliveryItemService{
	
	@Autowired
	private IOutBoundDeliveryRepository repo;
	
	@Autowired
	private IOutBoundDeliveryItemRepository repoItem;

	@Override
	public ResponseEntity<Object> create(String obdNumber, OutBoundDeliveryItemDto outBoundDeliveryItemDto) {
		
		Optional<OutBoundDelivery> optionalOutBoundDelivery = repo.findById(obdNumber);
		if(!optionalOutBoundDelivery.isPresent()) { 
		return ResponseEntity.notFound().build();
		}
		OutBoundDeliveryItem outBoundDeliveryItem = ObjectMapperUtils.map(outBoundDeliveryItemDto, OutBoundDeliveryItem.class);
		outBoundDeliveryItem.setOutBoundDelivery(optionalOutBoundDelivery.get());
		OutBoundDeliveryItem savedOutBoundDeliveryItem = repoItem.save(outBoundDeliveryItem);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("id").toUri();
				return ResponseEntity.created(location).body(ObjectMapperUtils.map(savedOutBoundDeliveryItem, SalesOrderItemDto.class));
	}

	@Override
	public ResponseEntity<Object> read(String obdNumber, String soItemNumber) {
		Optional<OutBoundDeliveryItem> optionalOutBoundDeliveryItem = repoItem.findById(soItemNumber);
		if(!optionalOutBoundDeliveryItem.isPresent()) { 
		return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(ObjectMapperUtils.map(optionalOutBoundDeliveryItem.get(),OutBoundDeliveryItemDto.class));	}

	@Override
	public ResponseEntity<Object> update(String obdNumber, String soItemNumber,
			OutBoundDeliveryItemDto outBoundDeliveryItemDto) {
		Optional<OutBoundDelivery> optionalSalesOrderHeader = repo.findById(obdNumber);
		if(!optionalSalesOrderHeader.isPresent()) { 
		return ResponseEntity.notFound().build();
		}
		Optional<OutBoundDeliveryItem> optionalSalesOrderItem = repoItem.findById(soItemNumber);
		if(!optionalSalesOrderItem.isPresent()) { 
		return ResponseEntity.notFound().build();
		}
		OutBoundDeliveryItem salesOrderItem = ObjectMapperUtils.map(outBoundDeliveryItemDto, OutBoundDeliveryItem.class);
		salesOrderItem.setSoItemNumber(optionalSalesOrderItem.get().getSoItemNumber());
		OutBoundDeliveryItem updatedSalesOrderItem = repoItem.save(salesOrderItem);
				return ResponseEntity.ok().body(ObjectMapperUtils.map(updatedSalesOrderItem, OutBoundDeliveryItemDto.class));
	}

	@Override
	public ResponseEntity<Object> delete(String obdNumber, String soItemNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Object> readAll(String search) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
