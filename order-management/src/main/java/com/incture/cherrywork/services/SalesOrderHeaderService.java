package com.incture.cherrywork.services;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.hibernate.Session;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.gson.Gson;

import com.incture.cherrywork.dtos.HeaderDetailUIDto;
import com.incture.cherrywork.dtos.HeaderIdDto;
import com.incture.cherrywork.dtos.InvoDto;
import com.incture.cherrywork.dtos.ObdDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.dtos.SalesOrderOdataHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderOdataLineItemDto;
import com.incture.cherrywork.dtos.SalesOrderSearchHeaderDto;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.entities.SalesOrderItem;
import com.incture.cherrywork.entities.SequenceNumber;
import com.incture.cherrywork.repositories.INotificationConfigRepository;
import com.incture.cherrywork.repositories.ISalesOrderHeaderRepository;
import com.incture.cherrywork.repositories.ISalesOrderHeaderRepositoryNew;
import com.incture.cherrywork.repositories.ISalesOrderItemRepository;

import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.repositories.SalesOrderHeaderPredicateBuilder;
import com.incture.cherrywork.repositories.ServicesUtils;
import com.incture.cherrywork.sales.constants.EnOrderActionStatus;
import com.incture.cherrywork.util.SequenceNumberGen;

import com.incture.cherrywork.util.ServicesUtil;

import com.incture.cherrywork.util.ServicesUtil;

import com.incture.cherrywork.util.ServicesUtil;

import com.querydsl.core.types.dsl.BooleanExpression;

@SuppressWarnings("unused")
@Service
@Transactional
public class SalesOrderHeaderService implements ISalesOrderHeaderService {
	public static final Logger logger = LoggerFactory.getLogger(SalesOrderHeaderService.class);

	@Autowired
	private ISalesOrderHeaderRepository salesOrderHeaderRepository;

	@Autowired
	private ISalesOrderItemRepository salesOrderItemRepository;

	@Autowired
	private SalesOrderOdataServices odataServices;

	@Autowired
	private INotificationConfigRepository notificationConfigRepository;

	@Autowired
	private NotificationDetailService notificationDetailService;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ISalesOrderHeaderRepositoryNew repo;

	private SequenceNumberGen sequenceNumberGen;

	@Override
	public ResponseEntity<Object> create(SalesOrderHeaderDto salesOrderHeaderDto) {
		SalesOrderHeader salesOrderHeader = ObjectMapperUtils.map(salesOrderHeaderDto, SalesOrderHeader.class);
		SalesOrderHeader savedSalesOrderHeader = salesOrderHeaderRepository.save(salesOrderHeader);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("id").toUri();
		System.err.println("Hi");
		return ResponseEntity.created(location)
				.body(ObjectMapperUtils.map(savedSalesOrderHeader, SalesOrderHeaderDto.class));
	}

	@Override
	public ResponseEntity<Object> read(String s4DocumentId) {
		Optional<SalesOrderHeader> optionalSalesOrderHeader = salesOrderHeaderRepository.findById(s4DocumentId);
		if (!optionalSalesOrderHeader.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok()
				.body(ObjectMapperUtils.map(optionalSalesOrderHeader.get(), SalesOrderHeaderDto.class));
	}

	@Override
	public ResponseEntity<Object> update(String s4DocumentId, SalesOrderHeaderDto salesOrderHeaderDto) {
		Optional<SalesOrderHeader> optionalSalesOrderHeader = salesOrderHeaderRepository.findById(s4DocumentId);
		if (!optionalSalesOrderHeader.isPresent()) {
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
		if (!optionalSalesOrderHeader.isPresent()) {
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

	// Sandeep

	@Override
	public ResponseEntity<Object> getHeaderById(HeaderIdDto dto) {
		try {
			SalesOrderHeaderItemDto result = repo.getHeaderById(dto);

			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}

	@Override
	public ResponseEntity<Object> getManageServiceObd(ObdDto dto) {
		
		Pageable pageable=PageRequest.of(dto.getPageNo()-1,10);
			Page<SalesOrderHeader> p=repo.getManageServiceObd(dto,pageable);
            return ResponseEntity.ok().body(p);
		
	}
	
	@Override
	public ResponseEntity<Object> getManageServiceInvo(InvoDto dto) {
		
		Pageable pageable=PageRequest.of(dto.getPageNo()-1,10);
			Page<SalesOrderHeader> p=repo.getManageServiceInvo(dto,pageable);
            return ResponseEntity.ok().body(p);
		
	}

	
    @Override
    public ResponseEntity<Object> getManageService(HeaderDetailUIDto dto){
    	
	Pageable pageable=PageRequest.of(dto.getPageNo()-1,10);
	Page<SalesOrderHeader> p=repo.getManageService(dto,pageable);
	return  ResponseEntity.ok().body(p);
	
<<<<<<< HEAD
=======
	
	
}


	/*
	 * @Override public Page<SalesOrderHeader> getManage(HeaderDetailUIDto
	 * dto,Pageable pageable) { try { Page<SalesOrderHeader> l =
	 * repo.getManageService(dto); return l;
	 * 
	 * } catch (Exception e) { e.printStackTrace(); return null; } }
	 */


	@Override
	public ResponseEntity<Object> getHeader(String stp) {

		try {
			List<String> l = repo.getHeader(stp);
			HeaderIdDto dto = new HeaderIdDto();
			for (String d : l) {
				dto.setsalesHeaderId(d);
				repo.deleteDraftedVersion(dto);
			}
			return new ResponseEntity<>(l, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("EXCEPTION FOUND", HttpStatus.INTERNAL_SERVER_ERROR);
		}
>>>>>>> refs/remotes/origin/master
	}


	

	
	@Override
	public ResponseEntity<Object> deleteDraftedVersion(HeaderIdDto d) {
		try {
			List<Integer> l = repo.deleteDraftedVersion(d);
			return new ResponseEntity<>(l, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("EXCEPTION FOUND", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	

	@Override
	public ResponseEntity<Object> getReferenceList(HeaderDetailUIDto dto) {
		try {
			List<String> data = repo.getReferenceList(dto);
			return new ResponseEntity<>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("EXCEPTION FOUND", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> getDraftedVersion(HeaderDetailUIDto dto) {
		try {
			List<SalesOrderHeaderItemDto> t = repo.getDraftedVersion(dto);
			return ResponseEntity.ok().body(t);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("EXCEPTION FOUND", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<Object> save(@Valid SalesOrderHeaderDto dto) {

		if ((!ServicesUtils.isEmpty(dto) && dto.getSalesHeaderId() == null)
				|| (!ServicesUtils.isEmpty(dto) && dto.getSalesHeaderId().equals("") == true)) {

			if (dto.getDocumentType().equals("IN")) {
				if (ServicesUtils.isEmpty(dto.getSalesHeaderId())) {
					sequenceNumberGen = SequenceNumberGen.getInstance();
					Session session = entityManager.unwrap(Session.class);
					System.err.println("session : " + session);
					String tempEnquiryId = sequenceNumberGen.getNextSeqNumber("IN", 8, session);
					String s4DocumentId = sequenceNumberGen.getNextSeqNumber("IN", 10, session);
					System.err.println("tempId" + tempEnquiryId);
					dto.setSalesHeaderId(tempEnquiryId);
					// dto.getHeaderDto().setS4DocumentId(s4DocumentId);

				}
			} else if (dto.getDocumentType().equalsIgnoreCase("QT")) {
				if (ServicesUtils.isEmpty(dto.getSalesHeaderId())) {
					sequenceNumberGen = SequenceNumberGen.getInstance();
					Session session = entityManager.unwrap(Session.class);
					System.err.println("session : " + session);
					String tempQuotationId = sequenceNumberGen.getNextSeqNumber("QT", 8, session);
					String s4DocumentId = sequenceNumberGen.getNextSeqNumber("IN", 10, session);
					System.err.println("tempQuotationId" + tempQuotationId);
					dto.setSalesHeaderId(tempQuotationId);
					// dto.getHeaderDto().setS4DocumentId(s4DocumentId);

				}
			} else if (dto.getDocumentType().equalsIgnoreCase("OR")) {
				if (ServicesUtils.isEmpty(dto.getSalesHeaderId())) {
					sequenceNumberGen = SequenceNumberGen.getInstance();
					Session session = entityManager.unwrap(Session.class);
					System.err.println("session : " + session);
					String tempOrderId = sequenceNumberGen.getNextSeqNumber("OR", 8, session);
					String s4DocumentId = sequenceNumberGen.getNextSeqNumber("IN", 10, session);
					System.err.println("tempOrderId" + tempOrderId);
					dto.setSalesHeaderId(tempOrderId);
					// dto.getHeaderDto().setS4DocumentId(s4DocumentId);

				}
			}
		}

		SalesOrderHeader savedSalesOrderHeader = new SalesOrderHeader();

		if (dto.getS4DocumentId() == null || dto.getS4DocumentId().equals("") == true) {
			String s4 = UUID.randomUUID().toString().replace("-", "");
			s4 = s4.length() > 10 ? s4.substring(0, 9) : s4;
			dto.setS4DocumentId(s4);
			dto.setDocumentProcessStatus(EnOrderActionStatus.DRAFTED);
			SalesOrderHeader salesOrderHeader = ObjectMapperUtils.map(dto, SalesOrderHeader.class);
			savedSalesOrderHeader = salesOrderHeaderRepository.save(salesOrderHeader);
			List<SalesOrderItemDto> l = new ArrayList<>();
			l = dto.getSalesOrderItemDtoList();
			for (SalesOrderItemDto d : l)

			{
				String s = UUID.randomUUID().toString().replace("-", "");
				s = s.length() > 10 ? s.substring(0, 9) : s;
				d.setSalesItemId(s);
				d.setSalesOrderHeader(savedSalesOrderHeader);
				SalesOrderItem salesOrderItem = ObjectMapperUtils.map(d, SalesOrderItem.class);
				salesOrderItem.setSalesHeaderId(dto.getSalesHeaderId());
				salesOrderItemRepository.save(salesOrderItem);
			}

		} else {

			SalesOrderHeader salesOrderHeader = ObjectMapperUtils.map(dto, SalesOrderHeader.class);
			savedSalesOrderHeader = salesOrderHeaderRepository.save(salesOrderHeader);
			List<SalesOrderItemDto> l = new ArrayList<>();
			l = dto.getSalesOrderItemDtoList();

			for (SalesOrderItemDto d : l) {
				if (d.getSalesItemId() == null) {
					String s = UUID.randomUUID().toString().replace("-", "");
					s = s.length() > 10 ? s.substring(0, 9) : s;
					d.setSalesItemId(s);
				}

				d.setSalesOrderHeader(savedSalesOrderHeader);
				SalesOrderItem salesOrderItem = ObjectMapperUtils.map(d, SalesOrderItem.class);
				salesOrderItem.setSalesHeaderId(dto.getSalesHeaderId());
				salesOrderItemRepository.save(salesOrderItem);
			}
		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("id").toUri();
		return ResponseEntity.ok().body(ObjectMapperUtils.map(savedSalesOrderHeader, SalesOrderHeaderDto.class));

	}

	@Override
	public ResponseEntity<Object> submitSalesOrder(SalesOrderHeaderItemDto dto) {
		if (dto.getHeaderDto().getSalesHeaderId() != null && dto.getHeaderDto().getS4DocumentId() == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.header("Message", "Wrong Input! salesHeaderId can't be non-null with s4DocumentId as null")
					.body("Wrong Input! salesHeaderId can't be non-null with s4DocumentId as null");

		if (dto.getHeaderDto().getSalesHeaderId() == null && dto.getHeaderDto().getS4DocumentId() != null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.header("Message", "Wrong Input! salesHeaderId can't be null with s4DocumentId as non-null")
					.body("Wrong Input! salesHeaderId can't be non-null with s4DocumentId as null");

		if (!ServicesUtils.isEmpty(dto) && dto.getHeaderDto().getSalesHeaderId() == null) {
			if (!ServicesUtils.isEmpty(dto.getHeaderDto().getDocumentType())) {
				if (dto.getHeaderDto().getDocumentType().equals("IN")) {
					if (ServicesUtils.isEmpty(dto.getHeaderDto().getSalesHeaderId())) {
						sequenceNumberGen = SequenceNumberGen.getInstance();
						Session session = entityManager.unwrap(Session.class);
						System.err.println("session : " + session);
						String tempEnquiryId = sequenceNumberGen.getNextSeqNumber("IN", 8, session);
						System.err.println("tempId" + tempEnquiryId);
						dto.setSalesHeaderId(tempEnquiryId);
						// dto.getHeaderDto().setS4DocumentId(s4DocumentId);
						dto.getHeaderDto().setDocumentProcessStatus(EnOrderActionStatus.DRAFTED);
						// sequenceNumberGen.updateRecord(new
						// SequenceNumber(dto.getHeaderDto().getDocumentType(),Integer.valueOf(tempEnquiryId).subString(tempEnquiryId.length()-2,tempEnquiryId.length)),session);
					}
				} else if (dto.getHeaderDto().getDocumentType().equalsIgnoreCase("QT")) {
					if (ServicesUtils.isEmpty(dto.getSalesHeaderId())) {
						sequenceNumberGen = SequenceNumberGen.getInstance();
						Session session = entityManager.unwrap(Session.class);
						System.err.println("session : " + session);
						String tempQuotationId = sequenceNumberGen.getNextSeqNumber("QT", 8, session);
						// String s4DocumentId =
						// sequenceNumberGen.getNextSeqNumber("IN", 15,
						// session);
						System.err.println("tempQuotationId" + tempQuotationId);
						dto.setSalesHeaderId(tempQuotationId);
						// dto.getHeaderDto().setS4DocumentId(s4DocumentId);
						dto.getHeaderDto().setDocumentProcessStatus(EnOrderActionStatus.DRAFTED);
					}
				} else if (dto.getHeaderDto().getDocumentType().equalsIgnoreCase("OR")) {
					if (ServicesUtils.isEmpty(dto.getSalesHeaderId())) {
						sequenceNumberGen = SequenceNumberGen.getInstance();
						Session session = entityManager.unwrap(Session.class);
						System.err.println("session : " + session);
						String tempOrderId = sequenceNumberGen.getNextSeqNumber("OR", 8, session);
						// String s4DocumentId =
						// sequenceNumberGen.getNextSeqNumber("IN", 15,
						// session);
						System.err.println("tempOrderId" + tempOrderId);
						dto.setSalesHeaderId(tempOrderId);
						// dto.getHeaderDto().setS4DocumentId(s4DocumentId);
						dto.getHeaderDto().setDocumentProcessStatus(EnOrderActionStatus.DRAFTED);
					}
				}
			}
		}

		String s4DocumentId = null;
		if (dto.getHeaderDto().getSalesHeaderId() == null)
			dto.getHeaderDto().setSalesHeaderId(dto.getSalesHeaderId());

		if ((dto != null) && (dto.getHeaderDto().getS4DocumentId() == null)) {

			s4DocumentId = ServicesUtil.randomId();

			// UUID uuid = UUID.randomUUID();

			// s4DocumentId = Long.toString(uuid.getLeastSignificantBits(), 94);
			// //
			// s4DocumentId.replaceAll("-", ""); // s4DocumentId =
			// s4DocumentId.substring(1,s4DocumentId.length()); // s4DocumentId
			// =
			// UUID.randomUUID().toString().replaceAll("-", "");
			dto.getHeaderDto().setS4DocumentId(s4DocumentId);
		}

		System.out.println("net Value in Header: " + dto.getHeaderDto().getNetValue());
		SalesOrderHeader header = ObjectMapperUtils.map(dto.getHeaderDto(), SalesOrderHeader.class);
		System.out.println("header Do: " + header.toString());

		System.out.println("net Value in Header Do: " + header.getNetValue());

		salesOrderHeaderRepository.save(header);

		for (SalesOrderItemDto item : dto.getLineItemList()) {
			if (item.getSalesItemId() == null) {
				String salesItemId = UUID.randomUUID().toString().replaceAll("-", "");
				item.setSalesItemId(salesItemId);
			}

			if (item.getQualityTestList().contains("3.2 INSPECTION")) {
				item.setInspection(true);
			}
			if (item.getQualityTestList().contains("BEND TEST")) {
				item.setBendTest(true);
			}
			if (item.getQualityTestList().contains("IMPACT TEST")) {
				item.setImpactTest(true);

			}
			if (item.getQualityTestList().contains("ULTRASONIC TEST")) {
				item.setUltralightTest(true);

			}
			if (item.getQualityTestList().contains("HARDNESS TEST")) {
				item.setHardnessTest(true);

			}
			if (item.getQualityTestList().contains("BORON REQUIRED")) {
				item.setIsElementBoronRequired(true);

			}
			item.setSalesHeaderId(dto.getHeaderDto().getSalesHeaderId());
			item.setS4DocumentId(dto.getHeaderDto().getS4DocumentId());
			item.setSalesOrderHeader(header);
			SalesOrderItem Item = ObjectMapperUtils.map(item, SalesOrderItem.class);
			salesOrderItemRepository.save(Item);

		}

		Collections.sort(dto.getLineItemList());
		int i = 1;
		for (SalesOrderItemDto item : dto.getLineItemList()) {
			item.setLineItemNumber(String.valueOf(i));
			i++;
			System.out.println("line item number: " + item.getLineItemNumber());
		}
		ResponseEntity<Object> res = submitSalesOrder1(dto);
		ResponseEntity<Object> res1 = null;
		if (res.getStatusCode().equals(HttpStatus.OK)) {

			// if
			// (response.getStatus().equals(HttpStatus.OK.getReasonPhrase()))
			// { System.err.println("[submitSalesOrder][odata] if case "
			// +dto.getSalesHeaderId());
			SalesOrderOdataHeaderDto odataHeaderDto = salesOrderHeaderRepository.getOdataReqPayload(dto);
			res1 = submitOdata(odataHeaderDto);
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("id").toUri();
		if (res.getStatusCode().equals(HttpStatus.OK) && res1.getStatusCode().equals(HttpStatus.OK))
			return ResponseEntity.status(HttpStatus.OK)
					.header("message",
							"Record submitted successfully. This is under review and you should get notified on this soon.")
					.body(res1.getBody());
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("message", "Technical Error in Submitting").body(res1.getBody());
	}

	public ResponseEntity<Object> getSearchDropDown(SalesOrderSearchHeaderDto dto) {

		return salesOrderHeaderRepository.getSearchDropDown(dto);
	}

	public ResponseEntity<Object> getMannualSearch(SalesOrderSearchHeaderDto dto) {

		return salesOrderHeaderRepository.getMannualSearch(dto);
	}

	@SuppressWarnings("unchecked")

	@Override
	public ResponseEntity<Object> submitSalesOrder1(SalesOrderHeaderItemDto dto) {
		System.err.println("sso1 started " + dto.toString()); //
		logger.debug("[SalesHeaderDao][submitSalesOrder] Started : " + //
				dto.toString());
		// Response response = new Response();
		// Sessionsession = null;
		// Transaction tx = null;
		try {
			String queryString = null;
			Query query = null;
			if (dto.getHeaderDto().getDocumentType() == "OBD") {
				queryString = "select distinct(i.plant) from SalesOrderItem i where i.outBoundOrderId=:outBoundOrderId";
				query = entityManager.createQuery(queryString);
				query.setParameter("outBoundOrderId", dto.getHeaderDto().getObdId());
			} else if (dto.getHeaderDto().getDocumentType() == "PGI") {
				queryString = "select distinct(i.plant) from SalesOrderItem i where i.pgiId=:pgiId";
				query = entityManager.createQuery(queryString);
				query.setParameter("pgiId", dto.getHeaderDto().getPgiId());
			} else {
				queryString = "select distinct(i.plant) from SalesOrderItem i where i.salesHeaderId=:salesHeaderId";
				query = entityManager.createQuery(queryString);
				query.setParameter("salesHeaderId", dto.getHeaderDto().getSalesHeaderId());
			}
			List<String> plantIds = query.getResultList();
			System.out.println("plant id in sso1 " + plantIds);
			if (plantIds.size() != 0)
				dto.getHeaderDto().setPlant(plantIds.get(0));
			String netPrice = null;
			String currency = dto.getHeaderDto().getDocumentCurrencySA();
			BigDecimal totalQuantity = new BigDecimal(0);
			if ((dto.getHeaderDto().getPlant() != null) && (dto.getHeaderDto().getPlant().equals("4321"))) {
				netPrice = dto.getNetValueSA();
				totalQuantity = dto.getTotalSalesOrderQuantitySA();
				dto.setNetValueSA(null);
				dto.setTotalSalesOrderQuantitySA(null);
			} else if (((dto.getHeaderDto().getPlant() != null)) && dto.getHeaderDto().getPlant().equals("CODD")) {
				netPrice = dto.getHeaderDto().getNetValue();
				totalQuantity = dto.getTotalSalesOrderQuantity();
				dto.getHeaderDto().setNetValue(null);
				dto.getHeaderDto().setTotalSalesOrderQuantity(null);
			}
			if ((dto.getHeaderDto().getDocumentType() != null) && dto.getHeaderDto().getDocumentType().equals("OR")) {
				dto.getHeaderDto().setIsOpen(true);
				System.out.println("Document type OR");
			}

			salesOrderHeaderRepository.save(ObjectMapperUtils.map(dto.getHeaderDto(), SalesOrderHeader.class));
			if (plantIds.size() > 1) {
				dto.getHeaderDto().setPlant(plantIds.get(1));
				if ((dto.getHeaderDto().getPlant() != null) && dto.getHeaderDto().getPlant().equals("4321")) {
					dto.getHeaderDto().setNetValue(netPrice);
					dto.setTotalSalesOrderQuantity(totalQuantity);
					dto.setNetValueSA(null);
					dto.setTotalSalesOrderQuantitySA(null);
					dto.getHeaderDto().setDocumentCurrency("USD");
				} else if ((dto.getHeaderDto().getPlant() != null) && (dto.getHeaderDto().getPlant().equals("CODD"))) {
					dto.setNetValueSA(netPrice);
					dto.setTotalSalesOrderQuantitySA(totalQuantity);
					dto.getHeaderDto().setNetValue(null);
					dto.setTotalSalesOrderQuantity(null);
					dto.getHeaderDto().setDocumentCurrency(currency);
				}

				salesOrderHeaderRepository.save(ObjectMapperUtils.map(dto, SalesOrderHeader.class));
			}

			Query query1 = null;
			if (dto.getHeaderDto().getDocumentType() == "OBD") {
				String iq = "select s.salesItemId from SalesOrderItem s where s.outBoundOrderId=:outBoundOrderId order by s.salesItemId asc";
				query1 = entityManager.createQuery(iq);
				query1.setParameter("outBoundOrderId", dto.getHeaderDto().getObdId());
			}
			if (dto.getHeaderDto().getDocumentType() == "PGI") {
				String iq = "select s.salesItemId from SalesOrderItem s where s.pgiId=:pgiId order by s.salesItemId asc";
				query1 = entityManager.createQuery(iq);
				query1.setParameter("pgiId", dto.getHeaderDto().getPgiId());
			} else {
				String iq = "select s.salesItemId from SalesOrderItem s where s.salesHeaderId=:salesHeaderId order by s.salesItemId asc";
				query1 = entityManager.createQuery(iq);
				query1.setParameter("salesHeaderId", dto.getHeaderDto().getSalesHeaderId());
			}
			List<String> listItem = query1.getResultList();

			System.out.println("lineitem " + listItem);
			if (listItem != null && listItem.size() > 0) {
				int i = 1;
				for (String item : listItem) {

					System.err.println(" item " + item);
					String updateQ = "update SALES_ORDER_ITEM set LINE_ITEM_NUMBER=" + i + " where SALES_ITEM_ID='"
							+ item + "'";
					Query query2 = entityManager.createNativeQuery(updateQ);
					query2.executeUpdate();

					i++;
				}
			}
			if (listItem == null || listItem.size() == 0)
				System.err.println("null or zero size item list");

		} catch (Exception e) {
			// logger.error("[SalesHeaderDao][submitSalesOrder] Exception : " +
			// e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("Message", "Error Submitting In HANA DB").body(null);
		}
		try {
			String notificationTypeId = "";
			if (dto.getHeaderDto().getDocumentType().equals("IN"))
				notificationTypeId = "N01";
			else if (dto.getHeaderDto().getDocumentType().equals("QT"))
				notificationTypeId = "N02";
			else if (dto.getHeaderDto().getDocumentType().equals("OR"))
				notificationTypeId = "N03";
			else if (dto.getHeaderDto().getDocumentType().equals("OBD"))
				notificationTypeId = "N04";
			else if (dto.getHeaderDto().getDocumentType().equals("PGI"))
				notificationTypeId = "N05";
			// String userResponse =
			// //odataServices.usersBySoldToParty((dto.getSoldToParty()));
			// List<String>listUser = getUserDetailsBySTP((userResponse));
			// for (String email :listUser)
			// {
			if (notificationConfigRepository.checkAlertForUser(dto.getHeaderDto().getCreatedBy(), notificationTypeId)) {
				// Dependency oon notification
				// String odataResponse = odataServices.usersByEmail(email);
				// List<UserDto> listUserDto =
				// getUserDetailsByEmail(odataResponse);
				// for (UserDto userDto : listUserDto) {
				// if (!(userDto.getPersonaId().equals("01") &&
				// notificationTypeId.equals("QT"))) {
				// if (userDto.getPersonaId().equals("01")) {
				// if (userDto.getUserId().equals(dto.getSoldToParty()))
				notificationDetailService.saveNotification(dto.getHeaderDto().getCreatedBy(),
						dto.getHeaderDto().getSoldToParty(), null, "01", "01", notificationTypeId, "Start", true);

				// } else {
				// notificationDetailService.saveNotification(userDto,
				// dto.getSoldToParty(), null,
				// userDto.getPersonaId(), userDto.getPersonaId(),
				// notificationTypeId,
				// "Start",
				// true);
			}
			// }
			// }
			// }
			// }

		} catch (Exception e) {
			// logger.error("[SalesHeaderDao][submitSalesOrder] Exception in
			// Notification : " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("Message", "Error Submitting In HANA DB").body(null);
		}
		System.out.println("before returning from sales order1 " + dto.toString());
		return ResponseEntity.status(HttpStatus.OK).header("Message", "Submitted in HANA DB Successfully!!").body(dto);
	}

	@SuppressWarnings("unchecked")
	public void updateRecord(String temp_id, String docID_6, String docID_2, String Doc_Type) {
		// logger.debug("[SalesHeaderDao][updateRecord] Start : " + temp_id + "
		// : " + docID_6 + " : " + docID_2);

		System.out.println("Inside Update Record!");

		try {
			if (!ServicesUtils.isEmpty(docID_6)) {

				String hString = null;
				if (Doc_Type == "OBD")
					hString = "select s from SalesOrderHeader s where s.obdId='" + temp_id + "' and s.plant='4321'";
				else if (Doc_Type == "PGI")
					hString = "select s from SalesOrderHeader s where s.pgiId='" + temp_id + "' and s.plant='4321'";
				else
					hString = "select s from SalesOrderHeader s where s.salesHeaderId='" + temp_id
							+ "' and s.plant='4321'";
				Query hq = entityManager.createQuery(hString);
				List<SalesOrderHeader> listSalesHeaderDo = hq.getResultList();
				SalesOrderHeader salesHeader = null;
				if (listSalesHeaderDo.size() != 0)
					salesHeader = listSalesHeaderDo.get(0);
				SalesOrderHeaderDto salesHeaderDto = ObjectMapperUtils.map(salesHeader, SalesOrderHeaderDto.class);
				if ((salesHeaderDto.getDocumentType() != null) && salesHeaderDto.getDocumentType().equals("OR")) // Apply
					// logic for
					// open
					// orders
					salesHeaderDto.setIsOpen(true);

				if (salesHeaderDto.getDocumentType() == "IN")
					docID_6 = "IN" + docID_6;
				if (salesHeaderDto.getDocumentType() == "OR")
					docID_6 = "OR" + docID_6;
				if (salesHeaderDto.getDocumentType() == "QT")
					docID_6 = "QT" + docID_6;
				if (salesHeaderDto.getDocumentType() == "OBD")
					docID_6 = "OBD" + docID_6;
				if (salesHeaderDto.getDocumentType() == "PGI")
					docID_6 = "PGI" + docID_6;

				if (Doc_Type == "OBD")
					salesHeaderDto.setObdId(docID_6);
				else if (Doc_Type == "PGI")
					salesHeaderDto.setPgiId(docID_6);
				else
					salesHeaderDto.setSalesHeaderId(docID_6);
				if (salesHeader != null)
					salesHeaderDto.setS4DocumentId(salesHeader.getS4DocumentId());

				// salesHeaderDto.setDocumentProcessStatus(EnOrderActionStatus.CREATED);

				// Posting error update
				// Date d = new Date();
				// long t = d.getTime();
				salesHeaderDto.setPostingDate(new Date());
				salesHeaderDto.setPostingError(null);
				salesHeaderDto.setPostingStatus(true);
				if (Doc_Type == "OBD")
					salesHeaderDto.setDocumentProcessStatus(EnOrderActionStatus.OBDCREATED);
				if (Doc_Type == "PGI")
					salesHeaderDto.setDocumentProcessStatus(EnOrderActionStatus.PGICREATED);
				else
					salesHeaderDto.setDocumentProcessStatus(EnOrderActionStatus.CREATED);
				// End

				salesOrderHeaderRepository.save(ObjectMapperUtils.map(salesHeaderDto, SalesOrderHeader.class));

				// Notification for id and acknowledgement

				try {
					String notificationTypeId = "";
					if (salesHeaderDto.getDocumentType().equals("IN"))
						notificationTypeId = "N01";
					else if (salesHeaderDto.getDocumentType().equals("QT"))
						notificationTypeId = "N02";
					else if (salesHeaderDto.getDocumentType().equals("OR"))
						notificationTypeId = "N03";
					else if (salesHeaderDto.getDocumentType().equals("OBD"))
						notificationTypeId = "N04";
					else if (salesHeaderDto.getDocumentType().equals("PGI"))
						notificationTypeId = "N05";
					// String odataResponse1 =
					// odataServices.usersBySoldToParty(salesHeaderDto.getSoldToParty());
					// List<String> listUsers =
					// getUserDetailsBySTP(odataResponse1);
					// for (String user : listUsers) {

					// NOTIFICATION DEPENDENCY --AWADHESH
					// KUMAR----------------------------

					if (notificationConfigRepository.checkAlertForUser(salesHeaderDto.getCreatedBy(),
							notificationTypeId))

						// String odataResponse =
						// odataServices.usersByEmail(user.toLowerCase());
						// List<UserDto> listUserDto =
						// getUserDetailsByEmail(odataResponse);
						// for (UserDto userDto : listUserDto) {
						// if (!(userDto.getPersonaId().equals("01") &&
						// notificationTypeId.equals("QT"))) {
						// if (userDto.getPersonaId().equals("01")) {
						// if
						// (userDto.getUserId().equals(salesHeaderDto.getSoldToParty()))

						/*
						 * NOTIFICATION DEPENDENCY --AWADHESH
						 * KUMAR----------------------------
						 */
						notificationDetailService.saveNotification(salesHeaderDto.getCreatedBy(),
								salesHeaderDto.getSoldToParty(), salesHeaderDto.getS4DocumentId(), "All", "All",
								notificationTypeId, "Created", true);

					// } else {
					// notificationDetailService.saveNotification(userDto,
					// salesHeaderDto.getSoldToParty(),
					// salesHeaderDto.getS4DocumentId(),
					// "All", "All", notificationTypeId, "Created", true);
					// }
					// }
					// if (salesHeaderDto.getDocumentType().equals("OR") &&
					// userDto.getPersonaId().equals("01")
					// &&
					// userDto.getUserId().equals(salesHeaderDto.getSoldToParty()))

					/*
					 * NOTIFICATION DEPENDENCY --AWADHESH
					 * KUMAR----------------------------
					 */
					if (salesHeaderDto.getDocumentType().equals("OR"))
						notificationDetailService.saveNotification(salesHeaderDto.getCreatedBy(),
								salesHeaderDto.getSoldToParty(), salesHeaderDto.getS4DocumentId(), "01", "01", "N04",
								"Acknowledge", true);

					// }
					// }
					// }

				} catch (Exception e) {
					// logger.error("[SalesHeaderDao][updateRecord] Exception in
					// Notification : " + e.getMessage());
					e.printStackTrace();
				}

				// -----------------

				// session = sessionFactory.openSession();
				// tx = session.beginTransaction();
				String lString = null;
				if (Doc_Type == "OBD")
					lString = "select s from SalesOrderItem s where s.outBoundOrderId=:temp_id and s.plant='4321'";
				if (Doc_Type == "PGI")
					lString = "select s from SalesOrderItem s where s.pgiId=:temp_id and s.plant='4321'";
				else
					lString = "select s from SalesOrderItem s where s.salesHeaderId=:temp_id and s.plant='4321'";
				Query lq = entityManager.createQuery(lString);
				lq.setParameter("temp_id", temp_id);
				List<SalesOrderItem> listSalesOrderItem = lq.getResultList();
				// session.flush();
				// session.clear();
				// tx.commit();

				for (SalesOrderItem salesItemDetailsDo : listSalesOrderItem) {
					SalesOrderItemDto salesItemDetailsDto = new SalesOrderItemDto();
					salesItemDetailsDto = ObjectMapperUtils.map(salesItemDetailsDo, SalesOrderItemDto.class);
					salesItemDetailsDto.setS4DocumentId(salesItemDetailsDo.getSalesOrderHeader().getS4DocumentId());
					if (Doc_Type == "OBD")
						salesItemDetailsDto.setOutBoundOrderId(docID_6);
					if (Doc_Type == "PGI")
						salesItemDetailsDto.setPgiId(docID_6);
					else
						salesItemDetailsDto.setSalesHeaderId(docID_6);
					// session = sessionFactory.openSession();
					// tx = session.beginTransaction();
					salesOrderItemRepository.save(ObjectMapperUtils.map(salesItemDetailsDto, SalesOrderItem.class));
					// session.flush();
					// session.clear();
					// tx.commit();
				}

			}

			if (!ServicesUtils.isEmpty(docID_2)) {
				// session = sessionFactory.openSession();
				// tx = session.beginTransaction();
				String hString = null;
				if (Doc_Type == "OBD")
					hString = "select s from SalesOrderHeader s where s.obdId='" + temp_id + "' and s.plant='CODD'";
				if (Doc_Type == "PGI")
					hString = "select s from SalesOrderHeader s where s.pgiId='" + temp_id + "' and s.plant='CODD'";
				else
					hString = "select s from SalesOrderHeader s where s.salesHeaderId='" + temp_id
							+ "' and s.plant='CODD'";
				Query hq = entityManager.createQuery(hString);
				List<SalesOrderHeader> listSalesHeaderDo = hq.getResultList();
				// session.flush();
				// session.clear();
				// tx.commit();

				// session = sessionFactory.openSession();
				// tx = session.beginTransaction();

				SalesOrderHeader salesHeader = null;
				if (listSalesHeaderDo.size() != 0)
					salesHeader = listSalesHeaderDo.get(0);
				SalesOrderHeaderDto salesHeaderDto = ObjectMapperUtils.map(salesHeader, SalesOrderHeaderDto.class);
				if ((salesHeaderDto.getDocumentType() != null) && salesHeaderDto.getDocumentType().equals("OR"))
					salesHeaderDto.setIsOpen(true);

				if ((salesHeaderDto.getDocumentType() != null) && salesHeaderDto.getDocumentType().equals("IN"))
					docID_6 = "IN" + docID_2;
				if ((salesHeaderDto.getDocumentType() != null) && salesHeaderDto.getDocumentType().equals("OR"))
					docID_6 = "OR" + docID_2;
				if ((salesHeaderDto.getDocumentType() != null) && salesHeaderDto.getDocumentType().equals("QT"))
					docID_6 = "QT" + docID_2;
				if ((salesHeaderDto.getDocumentType() != null) && salesHeaderDto.getDocumentType().equals("OBD"))
					docID_6 = "OBD" + docID_2;
				if ((salesHeaderDto.getDocumentType() != null) && salesHeaderDto.getDocumentType().equals("PGI"))
					docID_6 = "PGI" + docID_2;

				if (salesHeader != null)
					salesHeaderDto.setS4DocumentId(salesHeader.getS4DocumentId());
				if (Doc_Type == "OBD")
					salesHeaderDto.setObdId(docID_2);
				if (Doc_Type == "PGI")
					salesHeaderDto.setPgiId(docID_2);
				else
					salesHeaderDto.setSalesHeaderId(docID_2);

				if (Doc_Type == "OBD")
					salesHeaderDto.setDocumentProcessStatus(EnOrderActionStatus.OBDCREATED);
				if (Doc_Type == "PGI")
					salesHeaderDto.setDocumentProcessStatus(EnOrderActionStatus.PGICREATED);
				else
					salesHeaderDto.setDocumentProcessStatus(EnOrderActionStatus.CREATED);

				// Posting error update
				// Date d = new Date();
				// long t = d.getTime();

				salesHeaderDto.setPostingDate(new Date());
				salesHeaderDto.setPostingError(null);
				salesHeaderDto.setPostingStatus(true);
				// salesHeaderDto.setDocumentProcessStatus(EnOrderActionStatus.CREATED);

				// End

				salesOrderHeaderRepository.save(ObjectMapperUtils.map(salesHeaderDto, SalesOrderHeader.class));
				// session.flush();
				// session.clear();
				// tx.commit();

				// Notification for id and acknowledgement

				try {
					String notificationTypeId = "";
					if ((salesHeaderDto.getDocumentType() != null) && salesHeaderDto.getDocumentType().equals("IN"))
						notificationTypeId = "N01";
					else if ((salesHeaderDto.getDocumentType() != null)
							&& salesHeaderDto.getDocumentType().equals("QT"))
						notificationTypeId = "N02";
					else if ((salesHeaderDto.getDocumentType() != null)
							&& salesHeaderDto.getDocumentType().equals("OR"))
						notificationTypeId = "N03";

					else if ((salesHeaderDto.getDocumentType() != null)
							&& salesHeaderDto.getDocumentType().equals("OBD"))
						notificationTypeId = "N04";
					else if ((salesHeaderDto.getDocumentType() != null)
							&& salesHeaderDto.getDocumentType().equals("PGI"))
						notificationTypeId = "N05";

					// String odataResponse1 =
					// odataServices.usersBySoldToParty(salesHeaderDto.getSoldToParty());
					// List<String> listUsers =
					// getUserDetailsBySTP(odataResponse1);
					// for (String user : listUsers) {

					/*
					 * NOTIFICATION DEPENDENCY --AWADHESH
					 * KUMAR---------------------------- if
					 * (notificationConfigDao.checkAlertForUser(salesHeaderDto.
					 * getCreatedBy(), notificationTypeId)) {
					 */

					// String odataResponse =
					// odataServices.usersByEmail(user.toLowerCase());
					// List<UserDto> listUserDto =
					// getUserDetailsByEmail(odataResponse);
					// for (UserDto userDto : listUserDto) {
					// if (!(userDto.getPersonaId().equals("01") &&
					// notificationTypeId.equals("QT"))) {
					// if (userDto.getPersonaId().equals("01")) {
					// if
					// (userDto.getUserId().equals(salesHeaderDto.getSoldToParty()))

					if (Doc_Type == "OBD")
						notificationDetailService.saveNotification(salesHeaderDto.getCreatedBy(),
								salesHeaderDto.getSoldToParty(), salesHeaderDto.getS4DocumentId(), "All", "All",
								notificationTypeId, "OBD Created", true);
					if (Doc_Type == "PGI")
						notificationDetailService.saveNotification(salesHeaderDto.getCreatedBy(),
								salesHeaderDto.getSoldToParty(), salesHeaderDto.getS4DocumentId(), "All", "All",
								notificationTypeId, "PGI Created", true);

					notificationDetailService.saveNotification(salesHeaderDto.getCreatedBy(),
							salesHeaderDto.getSoldToParty(), salesHeaderDto.getS4DocumentId(), "All", "All",
							notificationTypeId, "Created", true);

					// } else {
					// notificationDetailService.saveNotification(userDto,
					// salesHeaderDto.getSoldToParty(),
					// salesHeaderDto.getS4DocumentId(),
					// "All", "All", notificationTypeId, "Created", true);
					// }
					// }
					// if (salesHeaderDto.getDocumentType().equals("OR") &&
					// userDto.getPersonaId().equals("01")
					// &&
					// userDto.getUserId().equals(salesHeaderDto.getSoldToParty()))

					if (salesHeaderDto.getDocumentType().equals("OR"))
						notificationDetailService.saveNotification(salesHeaderDto.getCreatedBy(),
								salesHeaderDto.getSoldToParty(), salesHeaderDto.getS4DocumentId(), "01", "01", "N04",
								"Acknowledge", true);

					// }
					// }
					// }

				} catch (Exception e) {
					// logger.error("[SalesHeaderDao][updateRecord] Exception in
					// Notification : " + e.getMessage());
					e.printStackTrace();
				}

				// -----------------

				// session = sessionFactory.openSession();
				// tx = session.beginTransaction();
				String lString = null;
				if(Doc_Type == "OBD")
					lString = "select s from SalesOrderItem s where s.outBoundOrderId=:temp_id and s.plant='CODD'";
				if(Doc_Type == "PGI")
					lString = "select s from SalesOrderItem s where s.pgiId=:temp_id and s.plant='CODD'";
				else
					lString = "select s from SalesOrderItem s where s.salesHeaderId=:temp_id and s.plant='CODD'";
				
				Query lq = entityManager.createQuery(lString);
				lq.setParameter("temp_id", temp_id);
				List<SalesOrderItem> listSalesItemDetails = lq.getResultList();
				// session.flush();
				// session.clear();
				// tx.commit();

				for (SalesOrderItem salesItemDetails : listSalesItemDetails) {
					SalesOrderItemDto salesItemDetailsDto = new SalesOrderItemDto();
					salesItemDetailsDto = ObjectMapperUtils.map(salesItemDetails, SalesOrderItemDto.class);
					salesItemDetailsDto.setS4DocumentId(salesItemDetails.getSalesOrderHeader().getS4DocumentId());
					salesItemDetailsDto.setSalesHeaderId(docID_2);
					// session = sessionFactory.openSession();
					// tx = session.beginTransaction();
					salesOrderItemRepository.save(ObjectMapperUtils.map(salesItemDetailsDto, SalesOrderItem.class));
					// session.flush();
					// session.clear();
					// tx.commit();
				}
			}
		} catch (Exception e) {
			// logger.error("[SalesHeaderDao][updateRecord] Exceptions : " +
			// e.getMessage());
			e.printStackTrace();
		}
	}

	@Async
	public ResponseEntity<Object> submitOdata(SalesOrderOdataHeaderDto odataHeaderDto) {

		System.err.println("[submitSalesOrder][submitOdata] Started : " + odataHeaderDto.toString());
		// Response response = new Response();
		String odataResponse = null;
		try {
			odataResponse = odataServices.postData(odataHeaderDto);
			System.out.println("odataresponse: " + odataResponse);
			JSONObject json = new JSONObject((odataResponse));
			System.err.println("[submitSalesOrder][submitOdata] json : " + json.toString());
			if (!json.isNull("d")) {
				JSONObject d = json.getJSONObject("d");
				System.err.println("[submitSalesOrder][submitOdata] d : " + json.toString());
				String temp_id = null, DocID_2 = null, DocID_6 = null;
				if (!d.isNull("temp_id"))
					temp_id = d.getString("temp_id");
				if (!d.isNull("DocID_6"))
					DocID_6 = d.getString("DocID_6");
				if (!d.isNull("DocID_2"))
					DocID_2 = d.getString("DocID_2");
				System.err.println("[submitSalesOrder][submitOdata] JSON response : " + temp_id + " : " + DocID_2
						+ " : " + DocID_6);
				updateRecord(temp_id, DocID_6, DocID_2,odataHeaderDto.getDocType());
			} else {
				JSONObject error = json.getJSONObject("error");
				// logger.debug("[submitSalesOrder][submitOdata] error : " +
				// json.toString());
				System.err.println("[submitSalesOrder][submitOdata] error : " + json.toString());
				JSONObject message = error.getJSONObject("message");
				String value = message.getString("value");
				// logger.debug("[submitSalesOrder][submitOdata] error value : "
				// + value);
				System.err.println("[submitSalesOrder][submitOdata] error value : " + value);
				salesOrderHeaderRepository.updateError(odataHeaderDto.getTemp_id(), value);
				System.out.println("After Update Error! in submit odata in else" + error);

				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.header("Message", "Error in submitting odata for id: " + odataHeaderDto.getTemp_id())
						.body(error);
			}
			// response.setMessage("Odata Submitted Successfully");
			// response.setStatus(HttpStatus.OK.getReasonPhrase());
			// response.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			System.err.println("[submitSalesOrder][submitOdata] Exception : " + e.getMessage());
			e.printStackTrace();
			// response.setMessage("Error in Submitting Odata for id " +
			// odataHeaderDto.getTemp_id());
			// response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
			// response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("Message", "Error in submitting odata for id: " + odataHeaderDto.getTemp_id()).body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Message", "Odata Submitted Successfully")
				.body(odataResponse);
		// return response;
	}
	
	
//	public Response headerScheduler() {
//		return salesOrderHeaderRepository.headerScheduler();
//	}

	//Sandeep KUmar
		public static String listToString(List<String> list) {
			String response = "";
			try {
				for (String s : list) {
					response = "'" + s + "', " + response;
				}
				response = response.substring(0, response.length() - 2);
			} catch (Exception e) {
				System.err.println("[SalesHeaderDao][listToString] Exception : " + e.getMessage());
				e.printStackTrace();
			}
			return response;
		}

	

		

		

}
