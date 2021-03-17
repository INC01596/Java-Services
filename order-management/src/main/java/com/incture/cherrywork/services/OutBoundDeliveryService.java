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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.incture.cherrywork.dtos.OdataOutBoudDeliveryInputDto;
import com.incture.cherrywork.dtos.OdataOutBoudDeliveryPgiInputDto;
import com.incture.cherrywork.dtos.OutBoundDeliveryDto;
import com.incture.cherrywork.dtos.OutBoundDeliveryItemDto;
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
		
		String entity = null;
		if(inputDto.getTernr().equals("1") ){
		 entity = formInputEntityForOutBoundDeliveryDto(inputDto);
		}
		else if(inputDto.getTernr().equals("2"))
		{
		 entity = formInputEntityForOutBoundDeliveryPgiDto(inputDto);
		}
		else {
			 entity = formInputEntityForInvoiceDto(inputDto);
		}
		// call odata method 
		ResponseEntity<?> responseFromOdata = HelperClass.consumingOdataService(url, entity, "POST", destinationInfo);
		
		System.err.println("odata output "+ responseFromOdata);
		
		
		if(responseFromOdata.getStatusCodeValue()==200){
			
			OutBoundDeliveryDto outBoundDto = new OutBoundDeliveryDto();
			outBoundDto.setDocumentStatus("Success");
			outBoundDto.setResponseMessage("Created");
			outBoundDto.setObdNumber("1");
			outBoundDto.setSoNumber(inputDto.getSoNumber());
			outBoundDto.setDeliveryDate(inputDto.getDeliveryDate());
			outBoundDto.setNetAmount(inputDto.getNetAmount());
			outBoundDto.setShippingPoint(inputDto.getShippingPoint());
			outBoundDto.setSoNumber(inputDto.getSoNumber());
			outBoundDto.setOutboundDeliveryItemDto(inputDto.getOutboundDeliveryItemDto());
			OutBoundDelivery outBoundDelivery = ObjectMapperUtils.map(outBoundDto, OutBoundDelivery.class);
			OutBoundDelivery savedOutBoundDelivery = repo.save(outBoundDelivery);
			
			return new ResponseEntity<OutBoundDelivery>(savedOutBoundDelivery,HttpStatus.OK);
		}else {
			OutBoundDeliveryDto outBoundDto = new OutBoundDeliveryDto();
			outBoundDto.setDocumentStatus("Error");
			outBoundDto.setResponseMessage("Failed");
			outBoundDto.setObdNumber("1");
			outBoundDto.setSoNumber(inputDto.getSoNumber());
			outBoundDto.setDeliveryDate(inputDto.getDeliveryDate());
			outBoundDto.setNetAmount(inputDto.getNetAmount());
			outBoundDto.setShippingPoint(inputDto.getShippingPoint());
			outBoundDto.setSoNumber(inputDto.getSoNumber());
			outBoundDto.setOutboundDeliveryItemDto(inputDto.getOutboundDeliveryItemDto());
			OutBoundDelivery outBoundDelivery = ObjectMapperUtils.map(outBoundDto, OutBoundDelivery.class);
			OutBoundDelivery savedOutBoundDelivery = repo.save(outBoundDelivery);
			return new ResponseEntity<OutBoundDelivery>(savedOutBoundDelivery,HttpStatus.OK);
		}
		
		
	}
	
	   public String formInputEntityForOutBoundDeliveryDto(OutBoundDeliveryDto inputDto){
		
		OdataOutBoudDeliveryInputDto  odataInputOutBound = new OdataOutBoudDeliveryInputDto();
		
		odataInputOutBound.setVbeln(inputDto.getSoNumber());
		
		List<OutBoundDeliveryItemDto> outBoundDeliveryItemList = inputDto.getOutboundDeliveryItemDto();

		for(int i =0 ; i<outBoundDeliveryItemList.size();i++){
			
		odataInputOutBound.setKunag(outBoundDeliveryItemList.get(i).getSoItemNumber());
		odataInputOutBound.setBtgew(outBoundDeliveryItemList.get(i).getDeliveryQty());
		odataInputOutBound.setLgnum(outBoundDeliveryItemList.get(i).getUnit());
		}
		odataInputOutBound.setTernr("1");
		
		return odataInputOutBound.toString();
	}
	   
	   public String formInputEntityForOutBoundDeliveryPgiDto(OutBoundDeliveryDto inputDto){
			
			OdataOutBoudDeliveryPgiInputDto  odataInputOutBound = new OdataOutBoudDeliveryPgiInputDto();
			
			odataInputOutBound.setVbeln(inputDto.getObdNumber());
			List<OutBoundDeliveryItemDto> outBoundDeliveryItemList = inputDto.getOutboundDeliveryItemDto();
			for(int i =0 ; i<outBoundDeliveryItemList.size();i++){
				
				odataInputOutBound.setBtgew(outBoundDeliveryItemList.get(i).getDeliveryQty());
				odataInputOutBound.setGewei(outBoundDeliveryItemList.get(i).getSoItemNumber());
				odataInputOutBound.setKunag(outBoundDeliveryItemList.get(i).getPickedQty());
				odataInputOutBound.setLgnum(outBoundDeliveryItemList.get(i).getSloc());
				odataInputOutBound.setNtgew(outBoundDeliveryItemList.get(i).getPickedQty());
				odataInputOutBound.setTraid(outBoundDeliveryItemList.get(i).getMaterial());
				odataInputOutBound.setWerks(outBoundDeliveryItemList.get(i).getPlant());
			}
			
			/*private String Vbeln ;//– Delivery number
			private String Kunag ;//– Item Number
			private String Traid ;// – Material Number
			private String Werks ;// – Plant Number
			private String Btgew ;// – Delivered Quantity
			private String Ntgew ;// – Picked Quantity
			private String Gewei ;// – UOM
			private String Lgnum ;// – Storage Location
*/
			
			odataInputOutBound.setTernr("2");
			
			return odataInputOutBound.toString();
		}
	   
	   public String formInputEntityForInvoiceDto(OutBoundDeliveryDto inputDto){
			
			OdataOutBoudDeliveryPgiInputDto  odataInputOutBound = new OdataOutBoudDeliveryPgiInputDto();
			
			odataInputOutBound.setVbeln(inputDto.getObdNumber());
			
			odataInputOutBound.setTernr("3");
			
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
