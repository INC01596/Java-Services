package com.incture.cherrywork.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.incture.cherrywork.dtos.OdataOutBoudDeliveryInputDto;
import com.incture.cherrywork.dtos.OutBoundDeliveryDto;
import com.incture.cherrywork.entities.OutBoundDelivery;
import com.incture.cherrywork.repositories.IOutBoundDeliveryRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.repositories.OutBoundDeliveryPredicateBuilder;
import com.incture.cherrywork.util.ComConstants;
import com.incture.cherrywork.util.DestinationReaderUtil;
import com.incture.cherrywork.util.HelperClass;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
@Transactional
public class OutBoundDeliveryService implements IOutBoundDeliveryService {
	
	@Autowired
	private IOutBoundDeliveryRepository repo;
	
	
	
	@Override
	public ResponseEntity<?> createOutBoundDeliveryOnSubmit(OutBoundDeliveryDto inputDto) throws URISyntaxException, IOException{
		// call destination 
		Map<String, Object> destinationInfo = DestinationReaderUtil
				.getDestination(ComConstants.ODATA_CONSUMING_UPDATE_IN_ECC_DESTINATION_NAME);
		//set Url
		String url = destinationInfo.get("URL")
				+ "/sap/opu/odata/sap/Z_SALESORDER_STATUS_SRV/likpSet";
		// form payload into a string entity 
		
		String entity = formInputEntity(inputDto);
		
		// call odata method 
		JSONObject json = HelperClass.consumingOdataService(url, entity, "POST", destinationInfo);
		
		System.err.println("odata output "+ json);
		
		return new ResponseEntity<String>(json.toString(),HttpStatus.OK);
	}
	
	   public String formInputEntity(OutBoundDeliveryDto inputDto){
		
		OdataOutBoudDeliveryInputDto  odataInputOutBound = new OdataOutBoudDeliveryInputDto();
		
		odataInputOutBound.setVbeln(inputDto.getSoNumber());
		odataInputOutBound.setKunag(inputDto.getSoItemNumber());
		odataInputOutBound.setBtgew(inputDto.getDeliveryQuantity());
		odataInputOutBound.setLgnum(inputDto.getItemUnit());
		
		return odataInputOutBound.toString();
	}

@Override
public ResponseEntity<Object> create(OutBoundDeliveryDto outBoundDeliveryDto) {
		OutBoundDelivery outBoundDelivery = ObjectMapperUtils.map(outBoundDeliveryDto, OutBoundDelivery.class);
		OutBoundDelivery savedOutBoundDelivery = repo.save(outBoundDelivery);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("id").toUri();
				return ResponseEntity.created(location).body(ObjectMapperUtils.map(savedOutBoundDelivery, OutBoundDeliveryDto.class));
	}

@Override
public ResponseEntity<Object> read(String obdNumber) {
		Optional<OutBoundDelivery> optionalOutBoundDelivery = repo.findById(obdNumber);
		if(!optionalOutBoundDelivery.isPresent()) { 
		return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(ObjectMapperUtils.map(optionalOutBoundDelivery.get(),OutBoundDeliveryDto.class));
	}

@Override
public ResponseEntity<Object> update(String obdNumber, OutBoundDeliveryDto outBoundDeliveryDto) {
		Optional<OutBoundDelivery> optionalOutBoundDelivery = repo.findById(obdNumber);
		if(!optionalOutBoundDelivery.isPresent()) { 
		return ResponseEntity.notFound().build();
		}
		OutBoundDelivery outBoundDelivery = ObjectMapperUtils.map(outBoundDeliveryDto, OutBoundDelivery.class);
		outBoundDelivery.setObdNumber(optionalOutBoundDelivery.get().getObdNumber());
		OutBoundDelivery updatedOutBoundDelivery = repo.save(outBoundDelivery);
				return ResponseEntity.ok().body(ObjectMapperUtils.map(updatedOutBoundDelivery, OutBoundDeliveryDto.class));
	}

@Override
public ResponseEntity<Object> delete(String obdNumber) {
		Optional<OutBoundDelivery> optionalOutBoundDelivery = repo.findById(obdNumber);
		if(!optionalOutBoundDelivery.isPresent()) { 
		return ResponseEntity.notFound().build();
		}
		repo.delete(optionalOutBoundDelivery.get());
		return ResponseEntity.ok().body(null);	}

@Override
public ResponseEntity<Object> readAll(String search) {
		

OutBoundDeliveryPredicateBuilder builder = new OutBoundDeliveryPredicateBuilder();
if (search != null) {
Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
Matcher matcher = pattern.matcher(search + ",");
while (matcher.find()) {
builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
}
}
BooleanExpression exp = builder.build();
List<OutBoundDelivery> outBoundDelivery = (List<OutBoundDelivery>) repo.findAll(exp);
Object t = ObjectMapperUtils.mapAll(outBoundDelivery, OutBoundDeliveryDto.class);
return ResponseEntity.ok().body(t);     
}
	
	
}
