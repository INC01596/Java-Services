package com.incture.cherrywork.services;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.dtos.SalesOrderOdataHeaderDto;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.entities.SalesOrderItem;
import com.incture.cherrywork.repositories.INotificationConfigRepository;
import com.incture.cherrywork.repositories.ISalesOrderHeaderRepository;
import com.incture.cherrywork.repositories.ISalesOrderItemRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.repositories.ServicesUtils;
import com.incture.cherrywork.sales.constants.EnOrderActionStatus;
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

	private SequenceNumberGen sequenceNumberGen;

	public ResponseEntity<Object> createObd(SalesOrderHeaderItemDto dto) {

		ResponseEntity<Object> res = null;
		ResponseEntity<Object> res1 = null;

		if (dto.getHeaderDto().getSalesOrderId() == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.header("Message", "Please provide at least one Sales Order").body(null);

		String query = "select s.blocked from SalesOrderHeader s where s.salesHeaderId=:salesHeaderId";
		Query q = entityManager.createQuery(query);
		q.setParameter("salesHeaderId", dto.getHeaderDto().getSalesOrderId());
		List<Boolean> blocked = q.getResultList();
		System.out.println("blocked "+blocked);
		if (blocked.size() == 0) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("Message", "The Sales Order which is being referenced does not exist").body(null);
		}
		if (!(blocked.get(0))) {
			String query1 = "select i.blocked from SalesOrderItem i where salesHeaderId=:salesHeaderId";
			Query q1 = entityManager.createQuery(query1);
			q1.setParameter("salesHeaderId", dto.getHeaderDto().getSalesOrderId());
			List<Boolean> blockedItem = q1.getResultList();
			if (blockedItem.size() == 0) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.header("Message", "The Item which is being referenced does not exist").body(null);
			}
			if (!(blockedItem.get(0))) {
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
				//item.setObdStatus("Draft");
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
			res = salesOrderHeaderService.submitSalesOrder1(dto);
			System.out.println("draft "+dto.getDraft()+" data type "+dto.getDraft().getClass().getName());
			if (!dto.getDraft())
				if (res.getStatusCode().equals(HttpStatus.OK)) {

					// if
					// (response.getStatus().equals(HttpStatus.OK.getReasonPhrase()))
					// { System.err.println("[submitSalesOrder][odata] if case "
					// +dto.getSalesHeaderId());
					SalesOrderOdataHeaderDto odataHeaderDto = salesOrderHeaderRepository.getOdataReqPayload(dto);
					res1 = salesOrderHeaderService.submitOdata(odataHeaderDto);
				}

		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("id").toUri();
		if(dto.getDraft()){
			if(res.getStatusCode().equals(HttpStatus.OK))
				return ResponseEntity.status(HttpStatus.OK)
						.header("message",
								"Record submitted successfully as draft.")
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

	public ResponseEntity<Object> createPgi(String obdId, String action) {

		System.out.println("createPgi strated obdId: "+obdId);
		String query = "from SalesOrderHeader s where s.obdId=:obdId and documentType=:dType";
		Query q = entityManager.createQuery(query);
		q.setParameter("obdId", obdId);
		q.setParameter("dType", "OBD");
		
		List<SalesOrderHeader> listheader = q.getResultList();
		
		System.out.println("listHeader Size: "+listheader.size() );
		
		String query1 = "from SalesOrderItem i where i.outBoundOrderId=:obdId and i.pgiId is null";
		Query q1 = entityManager.createQuery(query1);
		q1.setParameter("obdId", obdId);
		
		List<SalesOrderItem> listItem = q1.getResultList();
		
		SalesOrderHeaderDto headerDto = ObjectMapperUtils.map(listheader.get(0), SalesOrderHeaderDto.class);
		
		List<SalesOrderItemDto> listItemDto = new ArrayList<SalesOrderItemDto>();

		for(SalesOrderItem item:listItem)
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
			if (!ServicesUtils.isEmpty(dto.getHeaderDto().getDocumentType())) {
				if (dto.getHeaderDto().getDocumentType().equals("PGI")) {
					if (ServicesUtils.isEmpty(dto.getHeaderDto().getPgiId())) {
						sequenceNumberGen = SequenceNumberGen.getInstance();
						Session session = entityManager.unwrap(Session.class);
						System.err.println("session : " + session);
						String tempPgiId = sequenceNumberGen.getNextSeqNumber("PGI", 8, session);
						System.err.println("tempPgiId" + tempPgiId);
						dto.getHeaderDto().setPgiId(tempPgiId);
						// dto.getHeaderDto().setS4DocumentId(s4DocumentId);
						//dto.getHeaderDto().setDocumentProcessStatus(EnOrderActionStatus.DRAFTED);
						dto.getHeaderDto().setPgiStatus("Draft");
						// sequenceNumberGen.updateRecord(new
						// SequenceNumber(dto.getHeaderDto().getDocumentType(),Integer.valueOf(tempEnquiryId).subString(tempEnquiryId.length()-2,tempEnquiryId.length)),session);
					}
				}
			}
		}

		ResponseEntity<Object> res = null;
		ResponseEntity<Object> res1 = null;
//		String s4DocumentId = null;
//		if ((dto != null) && (dto.getHeaderDto().getS4DocumentId() == null)) {
//
//			s4DocumentId = ServicesUtil.randomId();
//
//			dto.getHeaderDto().setS4DocumentId(s4DocumentId);
//		}
//		dto.getHeaderDto().setSalesOrderId(dto.getHeaderDto().getSalesHeaderId());
//
		SalesOrderHeader header = ObjectMapperUtils.map(dto.getHeaderDto(), SalesOrderHeader.class);
		salesOrderHeaderRepository.save(header);

		for (SalesOrderItemDto item : dto.getLineItemList()) {
//			if (item.getSalesItemId() == null) {
//				String salesItemId = UUID.randomUUID().toString().replaceAll("-", "");
//				item.setSalesItemId(salesItemId);
//			}
//
//			if (item.getQualityTestList().contains("3.2 INSPECTION")) {
//				item.setInspection(true);
//			}
//			if (item.getQualityTestList().contains("BEND TEST")) {
//				item.setBendTest(true);
//			}
//			if (item.getQualityTestList().contains("IMPACT TEST")) {
//				item.setImpactTest(true);
//
//			}
//			if (item.getQualityTestList().contains("ULTRASONIC TEST")) {
//				item.setUltralightTest(true);
//
//			}
//			if (item.getQualityTestList().contains("HARDNESS TEST")) {
//				item.setHardnessTest(true);
//
//			}
//			if (item.getQualityTestList().contains("BORON REQUIRED")) {
//				item.setIsElementBoronRequired(true);
//
//			}
//			item.setOutBoundOrderId(dto.getHeaderDto().getObdId());
//			item.setS4DocumentId(dto.getHeaderDto().getS4DocumentId());
//			item.setSalesOrderHeader(header);
			//item.setPgiStatus("Draft");
			item.setPgiId(dto.getHeaderDto().getPgiId());
			SalesOrderItem Item = ObjectMapperUtils.map(item, SalesOrderItem.class);
			salesOrderItemRepository.save(Item);

		}

//		Collections.sort(dto.getLineItemList());
//		int i = 1;
//		for (SalesOrderItemDto item : dto.getLineItemList()) {
//			item.setLineItemNumber(String.valueOf(i));
//			i++;
//			System.out.println("line item number: " + item.getLineItemNumber());
//		}
		res = salesOrderHeaderService.submitSalesOrder1(dto);
		
		if (!action.equals("draft"))
			if (res.getStatusCode().equals(HttpStatus.OK)) {

				// if
				// (response.getStatus().equals(HttpStatus.OK.getReasonPhrase()))
				// { System.err.println("[submitSalesOrder][odata] if case "
				// +dto.getSalesHeaderId());
				SalesOrderOdataHeaderDto odataHeaderDto = salesOrderHeaderRepository.getOdataReqPayload(dto);
				res1 = salesOrderHeaderService.submitOdata(odataHeaderDto);
			}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("id").toUri();
		
		if(action.equals("draft"
				)){
			if(res.getStatusCode().equals(HttpStatus.OK))
				return ResponseEntity.status(HttpStatus.OK)
						.header("message",
								"Record submitted successfully as draft.")
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
}
