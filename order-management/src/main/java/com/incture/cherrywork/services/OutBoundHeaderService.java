package com.incture.cherrywork.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.incture.cherrywork.dtos.OdataOutBoudDeliveryInputDto;
import com.incture.cherrywork.dtos.OdataOutBoudDeliveryInvoiceInputDto;
import com.incture.cherrywork.dtos.OdataOutBoudDeliveryPgiInputDto;
import com.incture.cherrywork.dtos.OutBoundDeliveryDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.dtos.SalesOrderOdataHeaderDto;
import com.incture.cherrywork.entities.OutBoundDelivery;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.entities.SalesOrderItem;
import com.incture.cherrywork.repositories.INotificationConfigRepository;
import com.incture.cherrywork.repositories.ISalesOrderHeaderRepository;
import com.incture.cherrywork.repositories.ISalesOrderItemRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.repositories.ServicesUtils;
import com.incture.cherrywork.sales.constants.EnOrderActionStatus;
import com.incture.cherrywork.util.ComConstants;
import com.incture.cherrywork.util.DestinationReaderUtil;
import com.incture.cherrywork.util.HelperClass;
import com.incture.cherrywork.util.SequenceNumberGen;
import com.incture.cherrywork.util.ServicesUtil;

@Service
@Transactional
public class OutBoundHeaderService implements IOutBoundHeaderService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ISalesOrderHeaderRepository salesOrderHeaderRepository;

	@Autowired
	private ISalesOrderItemRepository salesOrderItemRepository;

	@Autowired
	private INotificationConfigRepository notificationConfigRepository;

	@Autowired
	private NotificationDetailService notificationDetailService;

	@Autowired
	private ISalesOrderHeaderService salesOrderHeaderService;

	@Autowired
	private SalesOrderOdataServices odataServices;

	private SequenceNumberGen sequenceNumberGen;

	public ResponseEntity<Object> createObd(SalesOrderHeaderItemDto dto) {

		ResponseEntity<Object> res = null;
		ResponseEntity<Object> res1 = null;

		if (dto.getHeaderDto().getSalesOrderId() == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.header("Message", "Please provide at least one Sales Order").body(null);

		String query = "select s.blocked from SalesOrderHeader s where s.salesHeaderId=:salesHeaderId and s.obdId is null ";
		Query q = entityManager.createQuery(query);
		q.setParameter("salesHeaderId", dto.getHeaderDto().getSalesOrderId());
		// q.setParameter("obdId", null);
		List<Boolean> blocked = q.getResultList();
		System.out.println("blocked " + blocked);
		if (blocked.size() == 0) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("Message", "The Sales Order which is being referenced does not exist").body(null);
		}
		if (!(blocked.get(0))) {
			String query1 = "select BLOCKED from SALES_ORDER_ITEM i where i.SALES_HEADER_ID =\'"
					+ dto.getHeaderDto().getSalesOrderId() + "\' and i.OUT_BOUND_ORDER_ID is null";
			Query q1 = entityManager.createNativeQuery(query1);
			// q1.setParameter("salesHeaderId",
			// dto.getHeaderDto().getSalesOrderId());

			List<Integer> blockedItem = q1.getResultList();
			System.err.println("blockedItem " + blockedItem);
			if (blockedItem.size() == 0) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.header("Message", "The Item which is being referenced does not exist").body(null);
			}
			if ((blockedItem.get(0) == 0)) {
				if (dto.getHeaderDto().getObdId() != null && dto.getHeaderDto().getS4DocumentId() == null)
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.header("Message", "Wrong Input! obdId can't be non-null with s4DocumentId as null")
							.body("Wrong Input! obdId can't be non-null with s4DocumentId as null");

				if (dto.getHeaderDto().getObdId() == null && dto.getHeaderDto().getS4DocumentId() != null)
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.header("Message", "Wrong Input! obdId can't be null with s4DocumentId as non-null")
							.body("Wrong Input! obdId can't be non-null with s4DocumentId as null");

				if (!ServicesUtils.isEmpty(dto) && dto.getHeaderDto().getObdId() == null) {
					if (!ServicesUtils.isEmpty(dto.getHeaderDto().getDocumentType())) {
						if (dto.getHeaderDto().getDocumentType().equals("OBD")) {
							if (ServicesUtils.isEmpty(dto.getHeaderDto().getObdId())) {
								sequenceNumberGen = SequenceNumberGen.getInstance();
								Session session = entityManager.unwrap(Session.class);
								System.err.println("session : " + session);
								String tempObdId = sequenceNumberGen.getNextSeqNumber("OBD", 8, session);
								System.err.println("tempObdId" + tempObdId);
								dto.getHeaderDto().setObdId(tempObdId);
								// dto.getHeaderDto().setS4DocumentId(s4DocumentId);
								dto.getHeaderDto().setDocumentProcessStatus(EnOrderActionStatus.DRAFTED);
								dto.getHeaderDto().setObdStatus("Draft");
								// sequenceNumberGen.updateRecord(new
								// SequenceNumber(dto.getHeaderDto().getDocumentType(),Integer.valueOf(tempEnquiryId).subString(tempEnquiryId.length()-2,tempEnquiryId.length)),session);
							}
						}
					}
				}

			}

			String s4DocumentId = null;
			if ((dto != null) && (dto.getHeaderDto().getS4DocumentId() == null)) {

				s4DocumentId = ServicesUtil.randomId();

				dto.getHeaderDto().setS4DocumentId(s4DocumentId);
			}
			dto.getHeaderDto().setSalesHeaderId(dto.getHeaderDto().getSalesOrderId());

			SalesOrderHeader header = ObjectMapperUtils.map(dto.getHeaderDto(), SalesOrderHeader.class);
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
				item.setOutBoundOrderId(dto.getHeaderDto().getObdId());
				item.setS4DocumentId(dto.getHeaderDto().getS4DocumentId());
				item.setSalesOrderHeader(header);
				// item.setObdStatus("Draft");
				SalesOrderItem Item = ObjectMapperUtils.map(item, SalesOrderItem.class);
				salesOrderItemRepository.save(Item);

			}

			// Collections.sort(dto.getLineItemList());
			// int i = 1;
			// for (SalesOrderItemDto item : dto.getLineItemList()) {
			// item.setLineItemNumber(String.valueOf(i));
			// i++;
			// System.out.println("line item number: " +
			// item.getLineItemNumber());
			// }
			res = salesOrderHeaderService.submitSalesOrder1(dto);
			System.out.println("draft " + dto.getDraft() + " data type " + dto.getDraft().getClass().getName());
			if (!dto.getDraft())
				if (res.getStatusCode().equals(HttpStatus.OK)) {

					// if
					// (response.getStatus().equals(HttpStatus.OK.getReasonPhrase()))
					// { System.err.println("[submitSalesOrder][odata] if case "
					// +dto.getSalesHeaderId());
					OdataOutBoudDeliveryInputDto obdDto = salesOrderHeaderRepository.getOdataReqPayloadObd(dto);

					res1 = submitOdataObd(obdDto, dto.getHeaderDto().getDocumentType());

				}

		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("id").toUri();
		if (dto.getDraft()) {
			if (res.getStatusCode().equals(HttpStatus.OK))
				return ResponseEntity.status(HttpStatus.OK).header("message", "Record submitted successfully as draft.")
						.body(res.getBody());
			else
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.header("message", "Technical Error in Submitting").body(res.getBody());
		}

		if (res.getStatusCode().equals(HttpStatus.OK) && res1.getStatusCode().equals(HttpStatus.OK))
			return ResponseEntity.status(HttpStatus.OK)
					.header("message",
							"Record submitted successfully. This is under review and you should get notified on this soon.")
					.body(res1.getBody());
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("message", "Technical Error in Submitting").body(res1.getBody());

	}

	public ResponseEntity<Object> createPgi(String obdId) {

		System.out.println("createPgi strated obdId: " + obdId);
		String query = "from SalesOrderHeader s where s.obdId=:obdId and s.obdStatus =: Created";
		Query q = entityManager.createQuery(query);
		q.setParameter("obdId", obdId);
		q.setParameter("Created", "Created");

		List<SalesOrderHeader> listheader = q.getResultList();

		System.out.println("listHeader Size: " + listheader.size());

		String query1 = "from SalesOrderItem i where i.outBoundOrderId=:obdId and i.pgiId is null";
		Query q1 = entityManager.createQuery(query1);
		q1.setParameter("obdId", obdId);

		List<SalesOrderItem> listItem = q1.getResultList();
		System.err.println("listItem size " + listItem.size()+" and listItem"+listItem);

		SalesOrderHeaderDto headerDto = ObjectMapperUtils.map(listheader.get(0), SalesOrderHeaderDto.class);

		List<SalesOrderItemDto> listItemDto = new ArrayList<SalesOrderItemDto>();

		for (SalesOrderItem item : listItem)
			listItemDto.add(ObjectMapperUtils.map(item, SalesOrderItemDto.class));
		SalesOrderHeaderItemDto dto = new SalesOrderHeaderItemDto();
		dto.setHeaderDto(headerDto);
		dto.setLineItemList(listItemDto);

		if (dto.getHeaderDto().getObdId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.header("Message", "There should be at least one OBD reference in order to create a PGI.")
					.body(null);

		}

		if (dto.getHeaderDto().getPgiId() != null && dto.getHeaderDto().getS4DocumentId() == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.header("Message", "Wrong Input! pgiId can't be non-null with s4DocumentId as null")
					.body("Wrong Input! pgiId can't be non-null with s4DocumentId as null");

		if (!ServicesUtils.isEmpty(dto) && dto.getHeaderDto().getPgiId() == null) {
			if (ServicesUtils.isEmpty(dto.getHeaderDto().getPgiId())) {
				sequenceNumberGen = SequenceNumberGen.getInstance();
				Session session = entityManager.unwrap(Session.class);
				System.err.println("session : " + session);
				String tempPgiId = sequenceNumberGen.getNextSeqNumber("PGI", 8, session);
				System.err.println("tempPgiId" + tempPgiId);
				dto.getHeaderDto().setPgiId(tempPgiId);
				// dto.getHeaderDto().setS4DocumentId(s4DocumentId);
				// dto.getHeaderDto().setDocumentProcessStatus(EnOrderActionStatus.DRAFTED);
				dto.getHeaderDto().setPgiStatus("Draft");
				// sequenceNumberGen.updateRecord(new
				// SequenceNumber(dto.getHeaderDto().getDocumentType(),Integer.valueOf(tempEnquiryId).subString(tempEnquiryId.length()-2,tempEnquiryId.length)),session);
			}

		}

		ResponseEntity<Object> res = null;
		ResponseEntity<Object> res1 = null;
		// String s4DocumentId = null;
		// if ((dto != null) && (dto.getHeaderDto().getS4DocumentId() == null))
		// {
		//
		// s4DocumentId = ServicesUtil.randomId();
		//
		// dto.getHeaderDto().setS4DocumentId(s4DocumentId);
		// }
		// dto.getHeaderDto().setSalesOrderId(dto.getHeaderDto().getSalesHeaderId());
		//
		SalesOrderHeader header = ObjectMapperUtils.map(dto.getHeaderDto(), SalesOrderHeader.class);
		salesOrderHeaderRepository.save(header);

		for (SalesOrderItemDto item : dto.getLineItemList()) {
			// if (item.getSalesItemId() == null) {
			// String salesItemId = UUID.randomUUID().toString().replaceAll("-",
			// "");
			// item.setSalesItemId(salesItemId);
			// }
			//
			// if (item.getQualityTestList().contains("3.2 INSPECTION")) {
			// item.setInspection(true);
			// }
			// if (item.getQualityTestList().contains("BEND TEST")) {
			// item.setBendTest(true);
			// }
			// if (item.getQualityTestList().contains("IMPACT TEST")) {
			// item.setImpactTest(true);
			//
			// }
			// if (item.getQualityTestList().contains("ULTRASONIC TEST")) {
			// item.setUltralightTest(true);
			//
			// }
			// if (item.getQualityTestList().contains("HARDNESS TEST")) {
			// item.setHardnessTest(true);
			//
			// }
			// if (item.getQualityTestList().contains("BORON REQUIRED")) {
			// item.setIsElementBoronRequired(true);
			//
			// }
			// item.setOutBoundOrderId(dto.getHeaderDto().getObdId());
			// item.setS4DocumentId(dto.getHeaderDto().getS4DocumentId());
			// item.setSalesOrderHeader(header);
			// item.setPgiStatus("Draft");
			item.setPgiId(dto.getHeaderDto().getPgiId());
			SalesOrderItem Item = ObjectMapperUtils.map(item, SalesOrderItem.class);
			salesOrderItemRepository.save(Item);

		}

		// Collections.sort(dto.getLineItemList());
		// int i = 1;
		// for (SalesOrderItemDto item : dto.getLineItemList()) {
		// item.setLineItemNumber(String.valueOf(i));
		// i++;
		// System.out.println("line item number: " + item.getLineItemNumber());
		// }
		res = salesOrderHeaderService.submitSalesOrder1(dto);

		if (res.getStatusCode().equals(HttpStatus.OK)) {

			// if
			// (response.getStatus().equals(HttpStatus.OK.getReasonPhrase()))
			// { System.err.println("[submitSalesOrder][odata] if case "
			// +dto.getSalesHeaderId());
			OdataOutBoudDeliveryPgiInputDto odataHeaderDto = salesOrderHeaderRepository.getOdataReqPayloadPgi(dto);
			res1 = submitOdataPgi(odataHeaderDto, dto.getHeaderDto().getDocumentType());
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

	// submitObdToEcc(obdDto,dto.getHeaderDto().getDocumentType());
	
	public ResponseEntity<Object> createInv(String pgiId){
		
		System.out.println("createPgi strated pgiId: " + pgiId);
		String query = "from SalesOrderHeader s where s.pgiId=:pgiId and s.pgiStatus =: Created";
		Query q = entityManager.createQuery(query);
		q.setParameter("obdId", pgiId);
		q.setParameter("Created", "Created");

		List<SalesOrderHeader> listheader = q.getResultList();

		System.out.println("listHeader Size: " + listheader.size());

		String query1 = "from SalesOrderItem i where i.pgiId=:pgiId and i.invId is null";
		Query q1 = entityManager.createQuery(query1);
		q1.setParameter("pgiId", pgiId);

		List<SalesOrderItem> listItem = q1.getResultList();
		System.err.println("listItem size " + listItem.size()+" and listItem"+listItem);

		SalesOrderHeaderDto headerDto = ObjectMapperUtils.map(listheader.get(0), SalesOrderHeaderDto.class);

		List<SalesOrderItemDto> listItemDto = new ArrayList<SalesOrderItemDto>();

		for (SalesOrderItem item : listItem)
			listItemDto.add(ObjectMapperUtils.map(item, SalesOrderItemDto.class));
		SalesOrderHeaderItemDto dto = new SalesOrderHeaderItemDto();
		dto.setHeaderDto(headerDto);
		dto.setLineItemList(listItemDto);

		if (dto.getHeaderDto().getPgiId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.header("Message", "There should be at least one PGI reference in order to create a Invoice.")
					.body(null);

		}

		if (dto.getHeaderDto().getInvId() != null && dto.getHeaderDto().getS4DocumentId() == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.header("Message", "Wrong Input! invId can't be non-null with s4DocumentId as null")
					.body("Wrong Input! invId can't be non-null with s4DocumentId as null");

		if (!ServicesUtils.isEmpty(dto) && dto.getHeaderDto().getInvId() == null) {
			if (ServicesUtils.isEmpty(dto.getHeaderDto().getInvId())) {
				sequenceNumberGen = SequenceNumberGen.getInstance();
				Session session = entityManager.unwrap(Session.class);
				System.err.println("session : " + session);
				String tempInvId = sequenceNumberGen.getNextSeqNumber("INV", 8, session);
				System.err.println("tempInvId" + tempInvId);
				dto.getHeaderDto().setInvId(tempInvId);
				// dto.getHeaderDto().setS4DocumentId(s4DocumentId);
				// dto.getHeaderDto().setDocumentProcessStatus(EnOrderActionStatus.DRAFTED);
				dto.getHeaderDto().setInvoiceStatus("Draft");
				// sequenceNumberGen.updateRecord(new
				// SequenceNumber(dto.getHeaderDto().getDocumentType(),Integer.valueOf(tempEnquiryId).subString(tempEnquiryId.length()-2,tempEnquiryId.length)),session);
			}

		}

		ResponseEntity<Object> res = null;
		ResponseEntity<Object> res1 = null;
		// String s4DocumentId = null;
		// if ((dto != null) && (dto.getHeaderDto().getS4DocumentId() == null))
		// {
		//
		// s4DocumentId = ServicesUtil.randomId();
		//
		// dto.getHeaderDto().setS4DocumentId(s4DocumentId);
		// }
		// dto.getHeaderDto().setSalesOrderId(dto.getHeaderDto().getSalesHeaderId());
		//
		SalesOrderHeader header = ObjectMapperUtils.map(dto.getHeaderDto(), SalesOrderHeader.class);
		salesOrderHeaderRepository.save(header);

		for (SalesOrderItemDto item : dto.getLineItemList()) {
			// if (item.getSalesItemId() == null) {
			// String salesItemId = UUID.randomUUID().toString().replaceAll("-",
			// "");
			// item.setSalesItemId(salesItemId);
			// }
			//
			// if (item.getQualityTestList().contains("3.2 INSPECTION")) {
			// item.setInspection(true);
			// }
			// if (item.getQualityTestList().contains("BEND TEST")) {
			// item.setBendTest(true);
			// }
			// if (item.getQualityTestList().contains("IMPACT TEST")) {
			// item.setImpactTest(true);
			//
			// }
			// if (item.getQualityTestList().contains("ULTRASONIC TEST")) {
			// item.setUltralightTest(true);
			//
			// }
			// if (item.getQualityTestList().contains("HARDNESS TEST")) {
			// item.setHardnessTest(true);
			//
			// }
			// if (item.getQualityTestList().contains("BORON REQUIRED")) {
			// item.setIsElementBoronRequired(true);
			//
			// }
			// item.setOutBoundOrderId(dto.getHeaderDto().getObdId());
			// item.setS4DocumentId(dto.getHeaderDto().getS4DocumentId());
			// item.setSalesOrderHeader(header);
			// item.setPgiStatus("Draft");
			item.setInvId(dto.getHeaderDto().getInvId());
			SalesOrderItem Item = ObjectMapperUtils.map(item, SalesOrderItem.class);
			salesOrderItemRepository.save(Item);

		}

		// Collections.sort(dto.getLineItemList());
		// int i = 1;
		// for (SalesOrderItemDto item : dto.getLineItemList()) {
		// item.setLineItemNumber(String.valueOf(i));
		// i++;
		// System.out.println("line item number: " + item.getLineItemNumber());
		// }
		res = salesOrderHeaderService.submitSalesOrder1(dto);

		if (res.getStatusCode().equals(HttpStatus.OK)) {

			// if
			// (response.getStatus().equals(HttpStatus.OK.getReasonPhrase()))
			// { System.err.println("[submitSalesOrder][odata] if case "
			// +dto.getSalesHeaderId());
			OdataOutBoudDeliveryInvoiceInputDto odataHeaderDto = salesOrderHeaderRepository.getOdataReqPayloadInv(dto);
			res1 = submitOdataInv(odataHeaderDto, dto.getHeaderDto().getDocumentType());
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

	@Override
	public ResponseEntity<Object> submitToEcc(SalesOrderHeaderItemDto inputDto) {
		// call destination
		Map<String, Object> destinationInfo = null;
		try {
			destinationInfo = DestinationReaderUtil
					.getDestination(ComConstants.ODATA_CONSUMING_UPDATE_IN_ECC_DESTINATION_NAME);
			System.err.println("destination info in submitToECC " + destinationInfo);
		} catch (URISyntaxException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		// set Url
		String url = destinationInfo.get("URL") + "/sap/opu/odata/sap/Z_SALESORDER_STATUS_SRV/likpSet";
		// form payload into a string entity

		String entity = null;
		OdataOutBoudDeliveryInputDto obdDto = null;
		if (inputDto.getHeaderDto().getDocumentType().equals("OBD")) {
			obdDto = salesOrderHeaderRepository.getOdataReqPayloadObd(inputDto);
		}
		// else if (inputDto.getTernr().equals("2")) {
		// entity = formInputEntityForOutBoundDeliveryPgiDto(inputDto);
		// } else {
		// entity = formInputEntityForInvoiceDto(inputDto);
		// }

		entity = obdDto.toString();
		// call odata method
		ResponseEntity<?> responseFromOdata = null;
		try {
			responseFromOdata = HelperClass.consumingOdataService(url, entity, "POST", destinationInfo);
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println("odata output " + responseFromOdata);

		JSONObject responseObject = (JSONObject) responseFromOdata.getBody();

		if (responseFromOdata.getStatusCodeValue() == 200) {
			String opdNumber = responseObject.get("message").toString();
			String pgiNumber = responseObject.get("message").toString();
			// OutBoundDeliveryDto outBoundDto = new OutBoundDeliveryDto();
			inputDto.getHeaderDto().setObdStatus("Created");
			// outBoundDto.setResponseMessage("Created");
			if (inputDto.getHeaderDto().getDocumentType().equals("OBD")) {
				inputDto.getHeaderDto().setObdId(opdNumber);
			}
			// if (inputDto.getTernr() == "2") {
			// outBoundDto.setPgiNumber(pgiNumber);
			// }
			// outBoundDto.setSoNumber(inputDto.getSoNumber());
			// outBoundDto.setDeliveryDate(inputDto.getDeliveryDate());
			// outBoundDto.setNetAmount(inputDto.getNetAmount());
			// outBoundDto.setShippingPoint(inputDto.getShippingPoint());
			// outBoundDto.setSoNumber(inputDto.getSoNumber());
			// outBoundDto.setOutboundDeliveryItemDto(inputDto.getOutboundDeliveryItemDto());
			SalesOrderHeader header = ObjectMapperUtils.map(inputDto.getHeaderDto(), SalesOrderHeader.class);
			salesOrderHeaderRepository.save(header);

			return ResponseEntity.status(HttpStatus.OK)
					.header("message",
							"Record submitted successfully. This is under review and you should get notified on this soon.")
					.body(inputDto);
		} else {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", "Technical Error")
					.body(responseFromOdata);
		}

	}

	@Async
	public ResponseEntity<Object> submitOdataObd(OdataOutBoudDeliveryInputDto odataHeaderDto, String docType) {

		System.err.println("[submitSalesOrder][submitOdata] Started : " + odataHeaderDto.toString());
		// Response response = new Response();
		String odataResponse = null;
		try {
			odataResponse = odataServices.postDataObd(odataHeaderDto, docType);
			System.out.println("odataresponse: " + odataResponse);
			JSONObject json = new JSONObject((odataResponse));
			System.err.println("[submitOdataObd][postDataObd Response] json : " + json.toString());
			if (!json.isNull("d")) {
				String obdId = null,temp_id=null,DocID_6=null,DocID_2=null;
				JSONObject d = json.getJSONObject("d");
				System.err.println("[OutBoundHeader][submitOdataObd] d : " + json.toString());

				System.err.println("OBD ID " + obdId);

				//salesOrderHeaderService.updateRecord(temp_id, DocID_6,DocID_2,docType);
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
				// salesOrderHeaderRepository.updateError(odataHeaderDto.getTemp_id(),
				// value);
				System.out.println("After Update Error! in submit odata in else" + error);

				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Message",
						"Error in submitting odata for id: " /*
																 * +
																 * odataHeaderDto
																 * .getTemp_id()
																 */).body(error);
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
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Message",
					"Error in submitting odata for id: " /*
															 * + odataHeaderDto.
															 * getTemp_id()
															 */).body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Message", "Odata Submitted Successfully")
				.body(odataResponse);
		// return response;
	}

	@Async
	public ResponseEntity<Object> submitOdataPgi(OdataOutBoudDeliveryPgiInputDto odataHeaderDto, String docType) {

		System.err.println("[submitSalesOrder][submitOdata] Started : " + odataHeaderDto.toString());
		// Response response = new Response();
		String odataResponse = null;
		try {
			odataResponse = odataServices.postDataPgi(odataHeaderDto, docType);
			System.out.println("odataresponse: " + odataResponse);
			JSONObject json = new JSONObject((odataResponse));
			System.err.println("[submitOdataObd][postDataObd Response] json : " + json.toString());
			if (!json.isNull("d")) {
				String pgiId = null;
				JSONObject d = json.getJSONObject("d");
				System.err.println("[OutBoundHeader][submitOdataObd] d : " + json.toString());

				System.err.println("PGI ID " + pgiId);

				// updateRecord(temp_id, DocID_6,
				// DocID_2,odataHeaderDto.getDocType());
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
				// salesOrderHeaderRepository.updateError(odataHeaderDto.getTemp_id(),
				// value);
				System.out.println("After Update Error! in submit odata in else" + error);

				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Message",
						"Error in submitting odata for id: " /*
																 * +
																 * odataHeaderDto
																 * .getTemp_id()
																 */).body(error);
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
					.header("Message", "Error in submitting odata for id: ").body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Message", "Odata Submitted Successfully")
				.body(odataResponse);
		// return response;
	}

	
	@Async
	public ResponseEntity<Object> submitOdataInv(OdataOutBoudDeliveryInvoiceInputDto odataHeaderDto, String docType) {

		System.err.println("[submitSalesOrder][submitOdata] Started : " + odataHeaderDto.toString());
		// Response response = new Response();
		String odataResponse = null;
		try {
			odataResponse = odataServices.postDataInv(odataHeaderDto, docType);
			System.out.println("odataresponse: " + odataResponse);
			JSONObject json = new JSONObject((odataResponse));
			System.err.println("[submitOdataObd][postDataObd Response] json : " + json.toString());
			if (!json.isNull("d")) {
				String pgiId = null;
				JSONObject d = json.getJSONObject("d");
				System.err.println("[OutBoundHeader][submitOdataObd] d : " + json.toString());

				System.err.println("PGI ID " + pgiId);

				// updateRecord(temp_id, DocID_6,
				// DocID_2,odataHeaderDto.getDocType());
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
				// salesOrderHeaderRepository.updateError(odataHeaderDto.getTemp_id(),
				// value);
				System.out.println("After Update Error! in submit odata in else" + error);

				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Message",
						"Error in submitting odata for id: " /*
																 * +
																 * odataHeaderDto
																 * .getTemp_id()
																 */).body(error);
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
					.header("Message", "Error in submitting odata for id: ").body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Message", "Odata Submitted Successfully")
				.body(odataResponse);
		// return response;
	}

}
