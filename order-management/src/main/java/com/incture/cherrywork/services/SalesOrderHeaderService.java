package com.incture.cherrywork.services;

import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.repositories.ISalesOrderHeaderRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.repositories.SalesOrderHeaderPredicateBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class SalesOrderHeaderService implements ISalesOrderHeaderService{

@Autowired
private ISalesOrderHeaderRepository salesOrderHeaderRepository;

@Override
public ResponseEntity<Object> create(SalesOrderHeaderDto salesOrderHeaderDto) { 
SalesOrderHeader salesOrderHeader = ObjectMapperUtils.map(salesOrderHeaderDto, SalesOrderHeader.class);
SalesOrderHeader savedSalesOrderHeader = salesOrderHeaderRepository.save(salesOrderHeader);
URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("id").toUri();
		return ResponseEntity.created(location).body(ObjectMapperUtils.map(savedSalesOrderHeader, SalesOrderHeaderDto.class));
}
@Override
public ResponseEntity<Object> read(String s4DocumentId) { 
Optional<SalesOrderHeader> optionalSalesOrderHeader = salesOrderHeaderRepository.findById(s4DocumentId);
if(!optionalSalesOrderHeader.isPresent()) { 
return ResponseEntity.notFound().build();
}
return ResponseEntity.ok().body(ObjectMapperUtils.map(optionalSalesOrderHeader.get(),SalesOrderHeaderDto.class));
}
@Override
public ResponseEntity<Object> update(String s4DocumentId, SalesOrderHeaderDto salesOrderHeaderDto){
Optional<SalesOrderHeader> optionalSalesOrderHeader = salesOrderHeaderRepository.findById(s4DocumentId);
if(!optionalSalesOrderHeader.isPresent()) { 
return ResponseEntity.notFound().build();
}
SalesOrderHeader salesOrderHeader = ObjectMapperUtils.map(salesOrderHeaderDto, SalesOrderHeader.class);
salesOrderHeader.setS4DocumentId(optionalSalesOrderHeader.get().getS4DocumentId());
SalesOrderHeader updatedSalesOrderHeader = salesOrderHeaderRepository.save(salesOrderHeader);
		return ResponseEntity.ok().body(ObjectMapperUtils.map(updatedSalesOrderHeader, SalesOrderHeaderDto.class));
}
@Override
public ResponseEntity<Object> delete(String s4DocumentId) { 
Optional<SalesOrderHeader> optionalSalesOrderHeader = salesOrderHeaderRepository.findById(s4DocumentId);
if(!optionalSalesOrderHeader.isPresent()) { 
return ResponseEntity.notFound().build();
}
salesOrderHeaderRepository.delete(optionalSalesOrderHeader.get());
return ResponseEntity.ok().body(null);
}
@Override
public ResponseEntity<Object> readAll(String search) {
SalesOrderHeaderPredicateBuilder builder = new SalesOrderHeaderPredicateBuilder();
if (search != null) {
Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
Matcher matcher = pattern.matcher(search + ",");
while (matcher.find()) {
builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
}
}
BooleanExpression exp = builder.build();
List<SalesOrderHeader> salesOrderHeaders = (List<SalesOrderHeader>) salesOrderHeaderRepository.findAll(exp);
Object t = ObjectMapperUtils.mapAll(salesOrderHeaders, SalesOrderHeaderDto.class);
return ResponseEntity.ok().body(t);     
}
}