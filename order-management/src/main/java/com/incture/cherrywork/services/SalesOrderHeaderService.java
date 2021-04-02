package com.incture.cherrywork.services;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
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

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.gson.Gson;
import com.incture.cherrywork.dtos.HeaderDetailUIDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.dtos.SalesOrderOdataHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderSearchHeaderDto;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.entities.SalesOrderItem;
import com.incture.cherrywork.repositories.ISalesOrderHeaderRepository;
import com.incture.cherrywork.repositories.ISalesOrderItemRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.repositories.SalesOrderHeaderPredicateBuilder;
import com.incture.cherrywork.repositories.ServicesUtils;
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
	
	@Override
	public ResponseEntity<Object> create(SalesOrderHeaderDto salesOrderHeaderDto) {
		SalesOrderHeader salesOrderHeader = ObjectMapperUtils.map(salesOrderHeaderDto, SalesOrderHeader.class);
		SalesOrderHeader savedSalesOrderHeader = salesOrderHeaderRepository.save(salesOrderHeader);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("id").toUri();
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

	// ----------------------------------------------------1
	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<Object> getDraftedVersion(HeaderDetailUIDto dto) {
		String jpql = " where s.documentType='" + dto.getDocumentType()
				+ "' and s.documentProcessStatus='" + dto.getDocumentProcessStatus() + "' ";
		if (!ServicesUtil.isEmpty(dto.getCreatedBy()))
			jpql = jpql + " and s.createdBy='" + dto.getCreatedBy() + "' ";
		if (!ServicesUtil.isEmpty(dto.getStpId())) {
			String STP = ServicesUtil.listToString(dto.getStpId());
			jpql = jpql + " and  s.soldToParty in (" + STP + ")";
		}
		if (!ServicesUtil.isEmpty(dto.getSalesGroup()))
			jpql = jpql + " and s.salesGroup='" + dto.getSalesGroup() + "' ";
		ArrayList<SalesOrderHeader> list = null;
	      Query q=entityManager.createQuery(jpql);
	      System.out.println(jpql);
		try {
			list = (ArrayList<SalesOrderHeader>) q.getResultList();
		} catch (Exception e) {
		}
		return ResponseEntity.ok().body(ObjectMapperUtils.map(list, ArrayList.class));
	}

	// -------------------------------------------------2
	// @Override
	// public ResponseEntity<Object> getReferenceList(HeaderDetailUIDto dto) {
	// String jsql = "";
	//
	// List<SalesOrderHeader> l = salesOrderHeaderRepository.findAll(jsql);
	// return ResponseEntity.ok().body(ObjectMapperUtils.map(l,
	// ArrayList.class));
	//
	// }
	//
	// // ------------------------------------------------------3

	// @Override
	// public ResponseEntity<Object> deleteDraftedVersion(String s4DocumentId) {
	// Optional<SalesOrderHeader> optionalSalesOrderHeader =
	// salesOrderHeaderRepository.findById(s4DocumentId);
	// if (!optionalSalesOrderHeader.isPresent()) {
	// return ResponseEntity.notFound().build();
	// }
	// salesOrderHeaderRepository.delete(optionalSalesOrderHeader.get());
	// return ResponseEntity.ok().body(null);
	// }
	
	/*---------------AWADHESH KUMAR----------------------*/
	
	
	@Override
	public ResponseEntity<Object> submitSalesOrder(SalesOrderHeaderDto dto) {
		
		ResponseEntity<Object> res = submitSalesOrder1(dto);
		if(res.getStatusCode().equals(HttpStatus.OK)){
			
		// if (response.getStatus().equals(HttpStatus.OK.getReasonPhrase())) {
		// System.err.println("[submitSalesOrder][odata] if case");
		 SalesOrderOdataHeaderDto odataHeaderDto = salesOrderHeaderRepository.getOdataReqPayload(dto.getSalesHeaderId());
		 submitOdata(odataHeaderDto);
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("id").toUri();
		return ResponseEntity.created(location)
				.body(null);
	}

	public ResponseEntity<Object> getSearchDropDown(SalesOrderSearchHeaderDto dto) {

		return salesOrderHeaderRepository.getSearchDropDown(dto);
	}
	
	public ResponseEntity<Object> getMannualSearch(SalesOrderSearchHeaderDto dto){
		
		return salesOrderHeaderRepository.getMannualSearch(dto);
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<Object> submitSalesOrder1(SalesOrderHeaderDto dto) {
		System.err.println(dto.toString());
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
			if(plantIds.size() != 0)
				dto.setPlant(plantIds.get(0));
			String netPrice = null;
			String currency = dto.getDocumentCurrencySA();
			BigDecimal totalQuantity = new BigDecimal(0);
			if((dto.getPlant() != null) && (dto.getPlant().equals("4321"))) {
				netPrice = dto.getNetValueSA();
				totalQuantity = dto.getTotalSalesOrderQuantitySA();
				dto.setNetValueSA(null);
				dto.setTotalSalesOrderQuantitySA(null);
			} else if (((dto.getPlant() != null)) && dto.getPlant().equals("CODD")) {
				netPrice = dto.getNetValue();
				totalQuantity = dto.getTotalSalesOrderQuantity();
				dto.setNetValue(null);
				dto.setTotalSalesOrderQuantity(null);
			}
			if ((dto.getDocumentType() != null) && dto.getDocumentType().equals("OR"))
				dto.setIsOpen(true);

			salesOrderHeaderRepository.save(ObjectMapperUtils.map(dto, SalesOrderHeader.class));
			if (plantIds.size() > 1) {
				String tableKey = UUID.randomUUID().toString().replaceAll("-", "");
				dto.setTableKey(tableKey);
				dto.setPlant(plantIds.get(1));
				if ((dto.getPlant() != null) && dto.getPlant().equals("4321")) {
					dto.setNetValue(netPrice);
					dto.setTotalSalesOrderQuantity(totalQuantity);
					dto.setNetValueSA(null);
					dto.setTotalSalesOrderQuantitySA(null);
					dto.setDocumentCurrency("USD");
				} else if ((dto.getPlant() != null) && (dto.getPlant().equals("CODD"))) {
					dto.setNetValueSA(netPrice);
					dto.setTotalSalesOrderQuantitySA(totalQuantity);
					dto.setNetValue(null);
					dto.setTotalSalesOrderQuantity(null);
					dto.setDocumentCurrency(currency);
				}

				salesOrderHeaderRepository.save(ObjectMapperUtils.map(dto, SalesOrderHeader.class));
			}

			String iq = "select s.salesItemId from SalesOrderItem s where s.salesHeaderId=:salesHeaderId order by s.salesItemId asc";
			Query query1 = entityManager.createQuery(iq);
			query1.setParameter("salesHeaderId", dto.getSalesHeaderId());
			List<String> listItem = query1.getResultList();

			if (listItem != null && listItem.size() > 0) {
				int i = 1;
				for (String item : listItem) {

					String updateQ = "update SalesOrderItem set LineItemNumber=" + i + " where SalesItemId='" + item
							+ "'";
					Query query2 = entityManager.createNativeQuery(updateQ);
					query2.executeUpdate();

					i++;
				}
			}

		} catch (Exception e) {
			// logger.error("[SalesHeaderDao][submitSalesOrder] Exception : " +
			// e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Message", "Error Submitting In HANA DB").body(null);
		}

		try {
			String notificationTypeId = "";
			if (dto.getDocumentType().equals("IN"))
				notificationTypeId = "N01";
			else if (dto.getDocumentType().equals("QT"))
				notificationTypeId = "N02";
			else if (dto.getDocumentType().equals("OR"))
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
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Message", "Error Submitting In HANA DB").body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Message", "Submitted in HANA DB Successfully!!").body(dto);
	}

	
	@SuppressWarnings("unchecked")
	public void updateRecord(String temp_id, String docID_6, String docID_2) {
		// logger.debug("[SalesHeaderDao][updateRecord] Start : " + temp_id + "
		// : " + docID_6 + " : " + docID_2);

		try {
			if (!ServicesUtils.isEmpty(docID_6)) {

				String hString = "select s from SalesOrderHeader s where s.salesHeaderId='" + temp_id
						+ "' and s.plant='4321'";
				Query hq = entityManager.createQuery(hString);
				List<SalesOrderHeader> listSalesHeaderDo = hq.getResultList();
				SalesOrderHeader salesHeader = null;
				if(listSalesHeaderDo.size() != 0)
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
				if(listSalesHeaderDo.size() != 0)
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
					else if ((salesHeaderDto.getDocumentType() != null) && salesHeaderDto.getDocumentType().equals("QT"))
						notificationTypeId = "N02";
					else if ((salesHeaderDto.getDocumentType() != null) && salesHeaderDto.getDocumentType().equals("OR"))
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
				JSONObject message = error.getJSONObject("message");
				String value = message.getString("value");
				// logger.debug("[submitSalesOrder][submitOdata] error value : "
				// + value);
				salesOrderHeaderRepository.updateError(odataHeaderDto.getTemp_id(), value);
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
		return ResponseEntity.status(HttpStatus.OK).header("Message", "Odata Submitted Successfully").body(null);
		// return response;
	}


	
	

}
