package com.incture.cherrywork.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.incture.cherrywork.dtos.SalesOrderDropDownDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.dtos.SalesOrderLookUpDto;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.entities.SalesOrderItem;
import com.incture.cherrywork.repositories.ISalesOrderHeaderRepository;
import com.incture.cherrywork.repositories.ISalesOrderItemRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.repositories.SalesOrderItemPredicateBuilder;
import com.incture.cherrywork.repositories.ServicesUtils;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
@Transactional
public class SalesOrderItemService implements ISalesOrderItemService {

	@Autowired
	private ISalesOrderHeaderRepository salesOrderHeaderRepository;
	@Autowired
	private ISalesOrderItemRepository salesOrderItemRepository;

	@Override
	public ResponseEntity<Object> create(String s4DocumentId, SalesOrderItemDto salesOrderItemDto) {
		Optional<SalesOrderHeader> optionalSalesOrderHeader = salesOrderHeaderRepository.findById(s4DocumentId);
		if (!optionalSalesOrderHeader.isPresent()) {
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
		if (!optionalSalesOrderItem.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(ObjectMapperUtils.map(optionalSalesOrderItem.get(), SalesOrderItemDto.class));
	}

	@Override
	public ResponseEntity<Object> update(String s4DocumentId, String salesItemId, SalesOrderItemDto salesOrderItemDto) {
		Optional<SalesOrderHeader> optionalSalesOrderHeader = salesOrderHeaderRepository.findById(s4DocumentId);
		if (!optionalSalesOrderHeader.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Optional<SalesOrderItem> optionalSalesOrderItem = salesOrderItemRepository.findById(salesItemId);
		if (!optionalSalesOrderItem.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		SalesOrderItem salesOrderItem = ObjectMapperUtils.map(salesOrderItemDto, SalesOrderItem.class);
		salesOrderItem.setSalesItemId(optionalSalesOrderItem.get().getSalesItemId());
		SalesOrderItem updatedSalesOrderItem = salesOrderItemRepository.save(salesOrderItem);
		return ResponseEntity.ok().body(ObjectMapperUtils.map(updatedSalesOrderItem, SalesOrderItemDto.class));
	}

	@Override
	public ResponseEntity<Object> delete(String s4DocumentId,String salesItemId) {
		Optional<SalesOrderItem> optionalSalesOrderItem = salesOrderItemRepository.findById(salesItemId);
		if (!optionalSalesOrderItem.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		salesOrderItemRepository.delete(optionalSalesOrderItem.get());
		return ResponseEntity.ok().body(null);
	}
	@Override
	public ResponseEntity<Object> deleteItemOnly(String salesItemId) {
		Optional<SalesOrderItem> optionalSalesOrderItem = salesOrderItemRepository.findById(salesItemId);
		if (!optionalSalesOrderItem.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		salesOrderItemRepository.delete(optionalSalesOrderItem.get());
		return ResponseEntity.ok().body("Deleted Successfully");
	
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


	// Awadhesh Kumar

	public ResponseEntity<Object> addLineItem(String s4DocumentId, List<SalesOrderItemDto> salesOrderItemDtoList) {
		System.out.println("id: "+s4DocumentId);
		System.out.println(salesOrderItemDtoList.get(0)+" "+salesOrderItemDtoList.get(1));
		System.out.println(salesOrderItemDtoList.get(0).getSalesItemId()+" "+salesOrderItemDtoList.get(1).getClientSpecific());
		
		List<SalesOrderItemDto> outputList = new ArrayList<>();
		SalesOrderItem savedSalesOrderItem = null;

		System.out.println("addLineItem Started..");
		// sequenceNumberGen = SequenceNumberGen.getInstance();
		try {
			if (!ServicesUtils.isEmpty(salesOrderItemDtoList)) {

				for (SalesOrderItemDto salesOrderItemDto : salesOrderItemDtoList) {

					System.out.println("Inside first for loop..");
					if (!ServicesUtils.isEmpty(salesOrderItemDto.getQualityTestList())) {
						System.out.println("defaultQuality test is not empty");
						for (String qt : salesOrderItemDto.getQualityTestList()) {
							
							System.out.println("Inside Second for loop..");
							if (qt != null) {
								if (qt.equals("BT"))
									salesOrderItemDto.setBendTest(true);
								if (qt.equals("3.2"))
									salesOrderItemDto.setInspection(true);
								if (qt.equals("IT"))
									salesOrderItemDto.setImpactTest(true);
								if (qt.equals("UT"))
									salesOrderItemDto.setUltraSonicTest(true);
								// if (qt.equals("HT"))
								// salesOrderItemDto.setHardnessTest(true);
								// if (qt.equals("BR"))
								// salesOrderItemDto.setIsElementBoronRequired(true);
							}
						}
						String qualityTest = ServicesUtils.listToString(salesOrderItemDto.getQualityTestList());
						salesOrderItemDto.setQualityTest(qualityTest);
					}

					if (!ServicesUtils.isEmpty(salesOrderItemDto.getDefaultQualityTestList())) {
						String defaultqualityTest = "";
						for (String qt : salesOrderItemDto.getQualityTestList()) {
							if (qt != null)
								defaultqualityTest = qt + ",";
						}
						defaultqualityTest = defaultqualityTest.substring(0, defaultqualityTest.length() - 1);
						salesOrderItemDto.setDefaultQualityTest(defaultqualityTest);
					}
					// salesOrderItemDto.setSalesItemId(sequenceNumberGen.getNextSeqNumber("IT",
					// 8, getSession()));

					//System.out.println("Outside firts for loop..");
					SalesOrderItem salesOrderItem = ObjectMapperUtils.map(salesOrderItemDto, SalesOrderItem.class);
					salesOrderItemRepository.save(salesOrderItem);
					outputList.add(salesOrderItemDto);
				}
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("Message", "Error adding Material List").body(null);
		}

		return ResponseEntity.status(HttpStatus.OK).header("Message", "Material added Successfully").body(outputList);

	}

	public ResponseEntity<Object> updateLineItem(List<SalesOrderItemDto> salesOrderItemDtoList) {
		List<SalesOrderItemDto> outputList = new ArrayList<>();
		SalesOrderItem savedSalesOrderItem = null;

		// sequenceNumberGen = SequenceNumberGen.getInstance();
		try {
			if (!ServicesUtils.isEmpty(salesOrderItemDtoList)) {

				for (SalesOrderItemDto salesOrderItemDto : salesOrderItemDtoList) {

					if (!ServicesUtils.isEmpty(salesOrderItemDto.getQualityTestList())) {
						for (String qt : salesOrderItemDto.getQualityTestList()) {
							if (qt != null) {
								if (qt.equals("BT"))
									salesOrderItemDto.setBendTest(true);
								if (qt.equals("3.2"))
									salesOrderItemDto.setInspection(true);
								if (qt.equals("IT"))
									salesOrderItemDto.setImpactTest(true);
								if (qt.equals("UT"))
									salesOrderItemDto.setUltraSonicTest(true);
								// if (qt.equals("HT"))
								// salesOrderItemDto.setHardnessTest(true);
								// if (qt.equals("BR"))
								// salesOrderItemDto.setIsElementBoronRequired(true);
							}
						}
						String qualityTest = ServicesUtils.listToString(salesOrderItemDto.getQualityTestList());
						salesOrderItemDto.setQualityTest(qualityTest);
					}

					if (!ServicesUtils.isEmpty(salesOrderItemDto.getDefaultQualityTestList())) {
						String defaultqualityTest = "";
						for (String qt : salesOrderItemDto.getQualityTestList()) {
							if (qt != null)
								defaultqualityTest = qt + ",";
						}
						defaultqualityTest = defaultqualityTest.substring(0, defaultqualityTest.length() - 1);
						salesOrderItemDto.setDefaultQualityTest(defaultqualityTest);
					}
					// salesOrderItemDto.setSalesItemId(sequenceNumberGen.getNextSeqNumber("IT",
					// 8, getSession()));

					SalesOrderItem salesOrderItem = ObjectMapperUtils.map(salesOrderItemDto, SalesOrderItem.class);
					// salesOrderItem.setSalesOrderHeader(optionalSalesOrderHeader.get());
					savedSalesOrderItem = salesOrderItemRepository.save(salesOrderItem);
					outputList.add(salesOrderItemDto);
				}
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("Message", "Error updating Material List").body(null);
		}

		return ResponseEntity.status(HttpStatus.OK).header("Message", "Material updated Successfully!")
				.body(outputList);

	}
	
	
	public SalesOrderDropDownDto getLookUpValues() {
		List<SalesOrderLookUpDto> paymentTermList = new ArrayList<>();
		List<SalesOrderLookUpDto> incoTermList = new ArrayList<>();
		List<SalesOrderLookUpDto> qualityTestList = new ArrayList<>();
		List<SalesOrderLookUpDto> deliveryToleranceList = new ArrayList<>();
		List<SalesOrderLookUpDto> distributionChannelList = new ArrayList<>();

		SalesOrderDropDownDto dto = new SalesOrderDropDownDto();

		paymentTermList = salesOrderItemRepository.getPaymentTerms();
		incoTermList = salesOrderItemRepository.getIncoTerms();
		qualityTestList = salesOrderItemRepository.getQualityTest();
		deliveryToleranceList = salesOrderItemRepository.getDeliveryTolerance();
		distributionChannelList = salesOrderItemRepository.getDistributionChannel();

		dto.setPaymentTerms(paymentTermList);
		dto.setIncoTerms(incoTermList);
		dto.setQualityTest(qualityTestList);
		dto.setDeliveryTolerance(deliveryToleranceList);
		dto.setDistributionChannel(distributionChannelList);

		return dto;
	}
	
	public String getLookupValue(String key) {
		return salesOrderItemRepository.getLookupValue(key);
	}

	
	
}