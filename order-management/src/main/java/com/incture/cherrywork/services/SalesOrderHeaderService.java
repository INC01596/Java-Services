package com.incture.cherrywork.services;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.gson.Gson;
import com.incture.cherrywork.dtos.HeaderDetailUIDto;
import com.incture.cherrywork.dtos.HeaderIdDto;

import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.dtos.SalesOrderOdataHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderOdataLineItemDto;
import com.incture.cherrywork.dtos.SalesOrderSearchHeaderDto;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.entities.SalesOrderItem;
import com.incture.cherrywork.repositories.ISalesOrderHeaderRepository;
import com.incture.cherrywork.repositories.ISalesOrderHeaderRepositoryNew;
import com.incture.cherrywork.repositories.ISalesOrderItemRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.repositories.SalesOrderHeaderPredicateBuilder;
import com.incture.cherrywork.repositories.ServicesUtils;
import com.incture.cherrywork.sales.constants.EnOrderActionStatus;
import com.incture.cherrywork.util.SequenceNumberGen;
import com.incture.cherrywork.util.ServicesUtil;
import com.querydsl.core.types.dsl.BooleanExpression;



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

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ISalesOrderHeaderRepositoryNew repo;
	
	@Autowired
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
		public ResponseEntity<Object> getManageService(HeaderDetailUIDto dto) {
		try{
			List<SalesOrderHeader> l=repo.getManageService(dto);


        Object t = ObjectMapperUtils.mapAll(l, SalesOrderHeaderDto.class);


			
			return ResponseEntity.ok().body(t);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("EXCEPTION FOUND", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> deleteDraftedVersion(String val) {
		try {
			List<Integer> l = repo.deleteDraftedVersion(val);
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

		/*
		 * if (!ServicesUtil.isEmpty(dto)) { if
		 * (!ServicesUtil.isEmpty(dto.getDocumentType())) { if
		 * (dto.getDocumentType().equals("IN")) { if
		 * (ServicesUtil.isEmpty(dto.getSalesHeaderId())) { sequenceNumberGen=
		 * SequenceNumberGen.getInstance(); Session
		 * s=aSession.getSessionFactory().getCurrentSession();
		 * System.err.println("session : "+ s); String tempEnquiryId =
		 * sequenceNumberGen.getNextSeqNumber("IN", 8, s);
		 * System.err.println("tempId" + tempEnquiryId);
		 * dto.setSalesHeaderId(tempEnquiryId);
		 * dto.setDocumentProcessStatus(EnOrderActionStatus.DRAFTED); } } else
		 * if (dto.getDocumentType().equalsIgnoreCase("QT")) { if
		 * (ServicesUtil.isEmpty(dto.getSalesHeaderId())) { sequenceNumberGen=
		 * SequenceNumberGen.getInstance(); Session
		 * s=aSession.getSessionFactory().getCurrentSession();
		 * System.err.println("session : "+ s); String tempQuotationId =
		 * sequenceNumberGen.getNextSeqNumber("QT", 8,s);
		 * System.err.println("tempQuotationId" + tempQuotationId);
		 * dto.setSalesHeaderId(tempQuotationId);
		 * dto.setDocumentProcessStatus(EnOrderActionStatus.DRAFTED); } } else
		 * if (dto.getDocumentType().equalsIgnoreCase("OR")) { if
		 * (ServicesUtil.isEmpty(dto.getSalesHeaderId())) { sequenceNumberGen=
		 * SequenceNumberGen.getInstance(); Session
		 * s=aSession.getSessionFactory().getCurrentSession();
		 * System.err.println("session : "+ s); String tempOrderId =
		 * sequenceNumberGen.getNextSeqNumber("OR", 8, s);
		 * System.err.println("tempOrderId" + tempOrderId);
		 * dto.setSalesHeaderId(tempOrderId);
		 * dto.setDocumentProcessStatus(EnOrderActionStatus.DRAFTED); } } } }
<<<<<<< HEAD
		 */

		

	

		
		
		      /*   
			
			
	            if (!ServicesUtil.isEmpty(dto)) {
				if (!ServicesUtil.isEmpty(dto.getDocumentType())) {
					if (dto.getDocumentType().equals("IN")) {
						if (ServicesUtil.isEmpty(dto.getSalesHeaderId())) {
							sequenceNumberGen= SequenceNumberGen.getInstance();
							Session s=aSession.getSessionFactory().getCurrentSession();
							System.err.println("session : "+ s);
							String tempEnquiryId = sequenceNumberGen.getNextSeqNumber("IN", 8, s);
							System.err.println("tempId" + tempEnquiryId);
							dto.setSalesHeaderId(tempEnquiryId);
							dto.setDocumentProcessStatus(EnOrderActionStatus.DRAFTED);
						}
					} else if (dto.getDocumentType().equalsIgnoreCase("QT")) {
						if (ServicesUtil.isEmpty(dto.getSalesHeaderId())) {
							sequenceNumberGen= SequenceNumberGen.getInstance();
							Session s=aSession.getSessionFactory().getCurrentSession();
							System.err.println("session : "+ s);
						String tempQuotationId = sequenceNumberGen.getNextSeqNumber("QT", 8,s);
						System.err.println("tempQuotationId" + tempQuotationId);
							dto.setSalesHeaderId(tempQuotationId);
							dto.setDocumentProcessStatus(EnOrderActionStatus.DRAFTED);
						}
					} else if (dto.getDocumentType().equalsIgnoreCase("OR")) {
						if (ServicesUtil.isEmpty(dto.getSalesHeaderId())) {
							sequenceNumberGen= SequenceNumberGen.getInstance();
							Session s=aSession.getSessionFactory().getCurrentSession();
							System.err.println("session : "+ s);
							String tempOrderId = sequenceNumberGen.getNextSeqNumber("OR", 8, s);
							System.err.println("tempOrderId" + tempOrderId);
							dto.setSalesHeaderId(tempOrderId);
							
						}
					}
				}
				} */
		
			String s4=UUID.randomUUID().toString().replace("-", "");
			s4 = s4.length() > 10 ? s4.substring(0, 9) : s4;
		    dto.setS4DocumentId(s4);
		    String s2=UUID.randomUUID().toString().replace("-", "");
			s2 = s2.length() > 10 ? s2.substring(0, 9) : s2;
		    dto.setSalesHeaderId(s2);
		    dto.setDocumentProcessStatus(EnOrderActionStatus.DRAFTED);
			 SalesOrderHeader  salesOrderHeader = ObjectMapperUtils.map(dto, SalesOrderHeader.class);
	         SalesOrderHeader savedSalesOrderHeader = salesOrderHeaderRepository.save(salesOrderHeader);
				List<SalesOrderItemDto> l=new ArrayList<>();
				l= dto.getSalesOrderItemDtoList(); 
				for(SalesOrderItemDto d:l)
					
				{
					String s=UUID.randomUUID().toString().replace("-", "");
					s = s.length() > 10 ? s.substring(0, 9) : s;
					d.setSalesItemId(s);
                   d.setSalesOrderHeader(savedSalesOrderHeader);
					SalesOrderItem salesOrderItem=ObjectMapperUtils.map(d, SalesOrderItem.class);
					salesOrderItem.setSalesHeaderId(dto.getSalesHeaderId());
					salesOrderItemRepository.save(salesOrderItem);
				}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("id").toUri();
		return ResponseEntity.ok().body(ObjectMapperUtils.map(savedSalesOrderHeader, SalesOrderHeaderDto.class));

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
		System.err.println("sso1 started " + dto.toString());
		// logger.debug("[SalesHeaderDao][submitSalesOrder] Started : " +
		// dto.toString());
		// Response response = new Response();
		// Session session = null;
		// Transaction tx = null;
		try {
			String queryString = "select distinct(i.plant) from SalesOrderItem i where i.salesHeaderId=:salesHeaderId";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("salesHeaderId", dto.getSalesHeaderId());
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

			String iq = "select s.salesItemId from SalesOrderItem s where s.salesHeaderId=:salesHeaderId order by s.salesItemId asc";
			Query query1 = entityManager.createQuery(iq);
			query1.setParameter("salesHeaderId", dto.getSalesHeaderId());
			List<String> listItem = query1.getResultList();

			System.out.println("lineitem "+listItem);
			if (listItem != null && listItem.size() > 0) {
				int i = 1;
				for (String item : listItem) {

					System.err.println(" item "+item);
					String updateQ = "update SALES_ORDER_ITEM set LINE_ITEM_NUMBER=" + i + " where SALES_ITEM_ID='" + item + "'";
					Query query2 = entityManager.createNativeQuery(updateQ);
					query2.executeUpdate();
					

					i++;
				}
			}
			if(listItem == null || listItem.size()==0)
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
			// String userResponse =
			// odataServices.usersBySoldToParty((dto.getSoldToParty()));
			// List<String> listUser = getUserDetailsBySTP((userResponse));
			// for (String email : listUser) {
			/*
			 * if (notificationConfigDao.checkAlertForUser(dto.getCreatedBy(),
			 * notificationTypeId)) {
			 */ // Dependency oon notification
			// String odataResponse = odataServices.usersByEmail(email);
			// List<UserDto> listUserDto =
			// getUserDetailsByEmail(odataResponse);
			// for (UserDto userDto : listUserDto) {
			// if (!(userDto.getPersonaId().equals("01") &&
			// notificationTypeId.equals("QT"))) {
			// if (userDto.getPersonaId().equals("01")) {
			// if (userDto.getUserId().equals(dto.getSoldToParty()))
			/*
			 * notificationDetailService.saveNotification(dto.getCreatedBy(),
			 * dto.getSoldToParty(), null, "01", "01",
			 */ // Dependency oon notification
			/* notificationTypeId, "Start", true); */
			// } else {
			// notificationDetailService.saveNotification(userDto,
			// dto.getSoldToParty(), null,
			// userDto.getPersonaId(), userDto.getPersonaId(),
			// notificationTypeId,
			// "Start",
			// true);
			// }
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
	public void updateRecord(String temp_id, String docID_6, String docID_2) {
		// logger.debug("[SalesHeaderDao][updateRecord] Start : " + temp_id + "
		// : " + docID_6 + " : " + docID_2);

		System.out.println("Inside Update Record!");

		try {
			if (!ServicesUtils.isEmpty(docID_6)) {

				String hString = "select s from SalesOrderHeader s where s.salesHeaderId='" + temp_id
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
				salesHeaderDto.setS4DocumentId(docID_6);
				// salesHeaderDto.setDocumentProcessStatus(EnOrderActionStatus.CREATED);

				// Posting error update
				// Date d = new Date();
				// long t = d.getTime();
				salesHeaderDto.setPostingDate(new Date());
				salesHeaderDto.setPostingError(null);
				salesHeaderDto.setPostingStatus(true);
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
					// String odataResponse1 =
					// odataServices.usersBySoldToParty(salesHeaderDto.getSoldToParty());
					// List<String> listUsers =
					// getUserDetailsBySTP(odataResponse1);
					// for (String user : listUsers) {

					// NOTIFICATION DEPENDENCY --AWADHESH
					// KUMAR----------------------------
					/*
					 * if
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

					/*
					 * NOTIFICATION DEPENDENCY --AWADHESH
					 * KUMAR----------------------------
					 * notificationDetailService.saveNotification(salesHeaderDto
					 * .getCreatedBy(), salesHeaderDto.getSoldToParty(),
					 * salesHeaderDto.getS4DocumentId(), "All", "All",
					 * notificationTypeId, "Created", true);
					 */

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
					 * KUMAR---------------------------- if
					 * (salesHeaderDto.getDocumentType().equals("OR"))
					 * notificationDetailService.saveNotification(salesHeaderDto
					 * .getCreatedBy(), salesHeaderDto.getSoldToParty(),
					 * salesHeaderDto.getS4DocumentId(), "01", "01", "N04",
					 * "Acknowledge", true);
					 */
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
				String lString = "select s from SalesOrderItem s where s.salesHeaderId=:temp_id and s.plant='4321'";
				Query lq = entityManager.createQuery(lString);
				lq.setParameter("temp_id", temp_id);
				List<SalesOrderItem> listSalesOrderItem = lq.getResultList();
				// session.flush();
				// session.clear();
				// tx.commit();

				for (SalesOrderItem salesItemDetailsDo : listSalesOrderItem) {
					SalesOrderItemDto salesItemDetailsDto = new SalesOrderItemDto();
					salesItemDetailsDto = ObjectMapperUtils.map(salesItemDetailsDo, SalesOrderItemDto.class);
					salesItemDetailsDto.setS4DocumentId(docID_6);
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
				String hString = "select s from SalesOrderHeader s where s.salesHeaderId='" + temp_id
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
				salesHeaderDto.setS4DocumentId(docID_2);

				/*
				 * Awadhesh Kumar Enum has to be created
				 * salesHeaderDto.setDocumentProcessStatus(EnOrderActionStatus.
				 * CREATED);
				 */

				// Posting error update
				// Date d = new Date();
				// long t = d.getTime();

				salesHeaderDto.setPostingDate(new Date());
				salesHeaderDto.setPostingError(null);
				salesHeaderDto.setPostingStatus(true);
				salesHeaderDto.setDocumentProcessStatus(EnOrderActionStatus.CREATED);
				
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

					/*
					 * NOTIFICATION DEPENDENCY --AWADHESH
					 * KUMAR----------------------------
					 * notificationDetailService.saveNotification(salesHeaderDto
					 * .getCreatedBy(), salesHeaderDto.getSoldToParty(),
					 * salesHeaderDto.getS4DocumentId(), "All", "All",
					 * notificationTypeId, "Created", true);
					 */
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
					 * KUMAR---------------------------- if
					 * (salesHeaderDto.getDocumentType().equals("OR"))
					 * notificationDetailService.saveNotification(salesHeaderDto
					 * .getCreatedBy(), salesHeaderDto.getSoldToParty(),
					 * salesHeaderDto.getS4DocumentId(), "01", "01", "N04",
					 * "Acknowledge", true);
					 */
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
				String lString = "select s from SalesOrderItem s where s.salesHeaderId=:temp_id and s.plant='CODD'";
				Query lq = entityManager.createQuery(lString);
				lq.setParameter("temp_id", temp_id);
				List<SalesOrderItem> listSalesItemDetails = lq.getResultList();
				// session.flush();
				// session.clear();
				// tx.commit();

				for (SalesOrderItem salesItemDetails : listSalesItemDetails) {
					SalesOrderItemDto salesItemDetailsDto = new SalesOrderItemDto();
					salesItemDetailsDto = ObjectMapperUtils.map(salesItemDetails, SalesOrderItemDto.class);
					salesItemDetailsDto.setS4DocumentId(docID_2);
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
				updateRecord(temp_id, DocID_6, DocID_2);
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
						.header("Message", "Error in submitting odata for id: " + odataHeaderDto.getTemp_id()).body(error);
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

	@Override
	public ResponseEntity<Object> submitSalesOrder(SalesOrderHeaderItemDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
