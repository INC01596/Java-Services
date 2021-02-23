package com.incture.cherrywork.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.entities.SalesOrderItem;
import com.incture.cherrywork.repositories.ISalesOrderHeaderRepository;
import com.incture.cherrywork.repositories.ISalesOrderItemRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.repositories.SalesOrderItemPredicateBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
@Transactional
public class SalesOrderItemService implements ISalesOrderItemService{

@Autowired
private ISalesOrderHeaderRepository salesOrderHeaderRepository;
@Autowired
private ISalesOrderItemRepository salesOrderItemRepository;

@Override
public ResponseEntity<Object> create(String s4DocumentId, SalesOrderItemDto salesOrderItemDto) { 
Optional<SalesOrderHeader> optionalSalesOrderHeader = salesOrderHeaderRepository.findById(s4DocumentId);
if(!optionalSalesOrderHeader.isPresent()) { 
return ResponseEntity.notFound().build();
}
SalesOrderItem salesOrderItem = ObjectMapperUtils.map(salesOrderItemDto, SalesOrderItem.class);
salesOrderItem.setSalesOrderHeader(optionalSalesOrderHeader.get());
SalesOrderItem savedSalesOrderItem = salesOrderItemRepository.save(salesOrderItem);
URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("id").toUri();
		return ResponseEntity.created(location).body(ObjectMapperUtils.map(savedSalesOrderItem, SalesOrderItemDto.class));
}
@Override
public ResponseEntity<Object> read(String s4DocumentId, String salesItemId) { 
Optional<SalesOrderItem> optionalSalesOrderItem = salesOrderItemRepository.findById(salesItemId);
if(!optionalSalesOrderItem.isPresent()) { 
return ResponseEntity.notFound().build();
}
return ResponseEntity.ok().body(ObjectMapperUtils.map(optionalSalesOrderItem.get(),SalesOrderItemDto.class));
}
@Override
public ResponseEntity<Object> update(String s4DocumentId, String salesItemId, SalesOrderItemDto salesOrderItemDto){
Optional<SalesOrderHeader> optionalSalesOrderHeader = salesOrderHeaderRepository.findById(s4DocumentId);
if(!optionalSalesOrderHeader.isPresent()) { 
return ResponseEntity.notFound().build();
}
Optional<SalesOrderItem> optionalSalesOrderItem = salesOrderItemRepository.findById(salesItemId);
if(!optionalSalesOrderItem.isPresent()) { 
return ResponseEntity.notFound().build();
}
SalesOrderItem salesOrderItem = ObjectMapperUtils.map(salesOrderItemDto, SalesOrderItem.class);
salesOrderItem.setSalesItemId(optionalSalesOrderItem.get().getSalesItemId());
SalesOrderItem updatedSalesOrderItem = salesOrderItemRepository.save(salesOrderItem);
		return ResponseEntity.ok().body(ObjectMapperUtils.map(updatedSalesOrderItem, SalesOrderItemDto.class));
}
@Override
public ResponseEntity<Object> delete(String s4DocumentId, String salesItemId) { 
Optional<SalesOrderItem> optionalSalesOrderItem = salesOrderItemRepository.findById(salesItemId);
if(!optionalSalesOrderItem.isPresent()) { 
return ResponseEntity.notFound().build();
}
salesOrderItemRepository.delete(optionalSalesOrderItem.get());
return ResponseEntity.ok().body(null);
}
@Override
public ResponseEntity<Object> readAll(String search) {
SalesOrderItemPredicateBuilder builder = new SalesOrderItemPredicateBuilder();
if (search != null) {
Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
Matcher matcher = pattern.matcher(search + ",");
while (matcher.find()) {
builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
}
}
BooleanExpression exp = builder.build();
List<SalesOrderItem> salesOrderItems = (List<SalesOrderItem>) salesOrderItemRepository.findAll(exp);
Object t = ObjectMapperUtils.mapAll(salesOrderItems, SalesOrderItemDto.class);
return ResponseEntity.ok().body(t);     
}
}