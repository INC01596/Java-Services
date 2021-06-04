
package com.incture.cherrywork.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.incture.cherrywork.dtos.EccResponseOutputDto;
import com.incture.cherrywork.dtos.ExchangeHeaderDto;
import com.incture.cherrywork.dtos.ExchangeItemDto;
import com.incture.cherrywork.dtos.ExchangeOrder;
import com.incture.cherrywork.dtos.ODataBatchItem;
import com.incture.cherrywork.dtos.ODataBatchPayload;
import com.incture.cherrywork.dtos.OrderConditionDto;
import com.incture.cherrywork.dtos.OrderHdrToOrderCondition;
import com.incture.cherrywork.dtos.OrderHdrToOrderItem;
import com.incture.cherrywork.dtos.OrderHdrToOrderPartner;
import com.incture.cherrywork.dtos.OrderHdrToOrderPartnerDto;
import com.incture.cherrywork.dtos.ReturnFilterDto;
import com.incture.cherrywork.dtos.ReturnItemDto;
import com.incture.cherrywork.dtos.ReturnOrder;
import com.incture.cherrywork.dtos.ReturnOrderRequestPojo;
import com.incture.cherrywork.dtos.ReturnOrderResponsePojo;
import com.incture.cherrywork.dtos.ReturnRequestHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.dtos.SmsSendingDto;
import com.incture.cherrywork.entities.Address;
import com.incture.cherrywork.entities.Attachment;
import com.incture.cherrywork.entities.ExchangeHeader;
import com.incture.cherrywork.entities.ExchangeItem;
import com.incture.cherrywork.entities.ReturnItem;
import com.incture.cherrywork.entities.ReturnRequestHeader;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.entities.SalesOrderItem;
import com.incture.cherrywork.exceptions.NoRecordFoundException;
import com.incture.cherrywork.repositories.IAddressRepository;
import com.incture.cherrywork.repositories.IAttachmentRepository;
import com.incture.cherrywork.repositories.IExchangeHeaderRepository;
import com.incture.cherrywork.repositories.IExchangeItemRepository;
import com.incture.cherrywork.repositories.IReturnRequestHeaderRepository;
import com.incture.cherrywork.repositories.IReturnRequestItemRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.repositories.ServicesUtils;
import com.incture.cherrywork.util.HelperClass;
import com.incture.cherrywork.util.MailAlertUtil;
import com.incture.cherrywork.util.ReturnExchangeConstants;
import com.incture.cherrywork.util.SequenceNumberGen;
import com.incture.cherrywork.util.ServicesUtil;

@Service
@SuppressWarnings("unchecked")
@Transactional
public class ReturnRequestHeaderService implements IReturnRequestHeaderService {

	@Autowired
	private IReturnRequestHeaderRepository returnHeaderRepo;

	@Autowired
	private IAttachmentRepository attachmentRepo;

	@Autowired
	private IAddressRepository addressRepo;

	@Autowired
	private IReturnRequestItemRepository returnItemRepo;

	@Autowired
	private IExchangeHeaderRepository exchangeHeaderRepo;

	private SequenceNumberGen sequenceNumberGen;

	@Autowired
	private IExchangeItemRepository exchangeItemRepo;

	@Autowired
	private SalesOrderOdataServices odataServices;

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings({ "unused", "deprecation" })
	@Override
	public ResponseEntity<Object> saveAsDraft(ReturnOrderRequestPojo requestData) {
		ReturnRequestHeader returnRequestHeaderDo = null;
		List<ReturnItem> returnItemListDo = null;
		ExchangeHeader exchangeHeaderDo = null;
		ExchangeOrder exchangeOrder = null;
		String returnReqNum = null;
		String exchangeReqNum = null;
		List<ExchangeItem> exchangeItemListDo = null;
		List<Address> savedListAdress = null;
		String fileDownloadUri = null;

		// Counters for items
		AtomicInteger counterForReturnItemNum = new AtomicInteger(1);
		AtomicInteger counterForExchangeItemNum = new AtomicInteger(1);
		try {

			if (requestData.getReturns().getReturnReqNum() != null
					&& !requestData.getReturns().getReturnReqNum().isEmpty()) {
				returnReqNum = requestData.getReturns().getReturnReqNum();

				System.err.println("saved");
				if (requestData.getExchange() != null) {
					System.err.println("exchange");
					exchangeReqNum = requestData.getExchange().getExchangeReqNum();
					exchangeOrder = requestData.getExchange();
				}
			} else {
				// Setting the sequence to header.
				if (requestData.getExchange() != null) {
					returnReqNum = "XCR-" + ServicesUtil.randomId(); // ServicesUtil.randomId();
					exchangeReqNum = returnReqNum + "/" + ServicesUtil.randomId();
					requestData.getExchange().setExchangeReqNum(exchangeReqNum);

				} else {
					returnReqNum = "CR-" + ServicesUtil.randomId();
				}
				requestData.getReturns().setReturnReqNum(returnReqNum);
			}

			// logger.error("returnReqNum : " + returnReqNum);
			System.err.println("returnReqNum : " + returnReqNum);
			// > Start : Return Header
			returnRequestHeaderDo = ObjectMapperUtils.map(requestData.getReturns(), ReturnRequestHeader.class);
			// Saving data to DB.
			if (returnRequestHeaderDo.getCreatedAt() != null && returnRequestHeaderDo.getCreatedAt().equals("")) {
				String date = LocalDateTime.now(ZoneId.of("GMT+05:50")).toString();
				returnRequestHeaderDo.setCreatedAt(new Date(date));

			} else {
				returnRequestHeaderDo.setUpdatedAt("" + LocalDateTime.now(ZoneId.of("GMT+08:00")));
			}

			// save attachement
			if (requestData.getReturns().getAttachment() != null
					&& !requestData.getReturns().getAttachment().isEmpty()) {
				List<Attachment> listSaveAttachement = requestData.getReturns().getAttachment();
				String returnRequestNum = returnReqNum;
				listSaveAttachement.stream().forEach(r -> {
					r.setReturnReqNum(returnRequestNum);
				});
				List<Attachment> listAttachment = attachmentRepo.saveAll(listSaveAttachement);
				// logger.error("> savedReturnAttachment : " + listAttachment);
				System.err.println("> savedReturnAttachment :  " + listAttachment);

				fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/Attachment/downloadFileByReturnReqNum/").path(returnRequestNum).toUriString();

				returnRequestHeaderDo.setDocumentUrl(fileDownloadUri);
			}
			if (requestData.getReturns().getDocVersion() == null
					|| requestData.getReturns().getDocVersion().isEmpty()) {
				returnRequestHeaderDo.setDocVersion("DRAFT");
			} else {
				returnRequestHeaderDo.setDocVersion(requestData.getReturns().getDocVersion());
			}
			returnRequestHeaderDo.setCreationStatus(false);

			if (requestData.getReturns().getMessage() == null || requestData.getReturns().getMessage().isEmpty()) {
				returnRequestHeaderDo.setMessage("SAVED AS DRAFT");
			} else {
				returnRequestHeaderDo.setMessage(requestData.getReturns().getMessage());
			}

			ReturnRequestHeader savedReturnHeader = returnHeaderRepo.save(returnRequestHeaderDo);
			// logger.error("> savedReturnHeader : " + savedReturnHeader);
			System.err.println("> savedReturnHeader : " + savedReturnHeader);
			// saving data for adress
			if (requestData.getReturns().getAddress() != null && !requestData.getReturns().getAddress().isEmpty()) {
				List<Address> listAdress = requestData.getReturns().getAddress();
				String returnRequestNum = returnReqNum;
				listAdress.stream().forEach(r -> {
					r.setReturnReqNum(returnRequestNum);
				});
				// saved data to db
				savedListAdress = addressRepo.saveAll(listAdress);
				// logger.error("> savedReturnAddressr : " + savedListAdress);
				System.err.println("> savedReturnAddressr : " + savedListAdress);
			}
			// Assigning request no to items
			if (requestData.getReturns().getItems() != null && !requestData.getReturns().getItems().isEmpty()) {
				String returnRequestNum = returnReqNum;
				List<ReturnItem> listOfReturnItem = requestData.getReturns().getItems();
				listOfReturnItem.stream().forEach(r -> {
					r.setReturnReqNum(returnRequestNum);
					// r.setReturnReqItemid(requestData.getReturns().getItems));
				});
				returnItemListDo = ObjectMapperUtils.mapAll(listOfReturnItem, ReturnItem.class);
				// Save return items to DB.
				List<ReturnItem> savedReturnItem = returnItemRepo.saveAll(returnItemListDo);
				// logger.error("> savedReturnItem : " + savedReturnItem);
				System.err.println("> savedReturnItem :  " + savedReturnItem);
				// > End : Return Items
			}

			// > Start : Exchange Header
			// Getting the Sequence No.
			if (requestData.getExchange() != null) {
				exchangeHeaderDo = ObjectMapperUtils.map(requestData.getExchange(), ExchangeHeader.class);
				// Setting the sequence No.
				// exchangeHeaderDo.setExchangeReqNum(returnReqNum + "/" +
				// exchangeReqNum);
				exchangeHeaderDo.setExchangeReqNum(exchangeReqNum);
				exchangeHeaderDo.setReturnReqNum(returnReqNum);
				if (fileDownloadUri != null) {
					exchangeHeaderDo.setDocumentUrl(fileDownloadUri);
				}
				if (requestData.getExchange().getDocVersion() == null
						|| requestData.getExchange().getDocVersion().isEmpty()) {
					exchangeHeaderDo.setDocVersion("DRAFT");
				} else {
					exchangeHeaderDo.setDocVersion(requestData.getExchange().getDocVersion());
				}
				exchangeHeaderDo.setCreationStatus(false);

				if (requestData.getExchange().getMessage() == null
						|| requestData.getExchange().getMessage().isEmpty()) {
					exchangeHeaderDo.setMessage("SAVED AS DRAFT");
				} else {
					exchangeHeaderDo.setMessage(requestData.getExchange().getMessage());
				}

				// Saving Exchange Header
				ExchangeHeader savedExchangeHeader = exchangeHeaderRepo.save(exchangeHeaderDo);
				// logger.error("> savedExchangeHeader " + savedExchangeHeader);
				System.err.println("> savedExchangeHeader  " + savedExchangeHeader);

				// > End : Exchange Header
				// > Start : Exchange Item
				if (requestData.getExchange().getItems() != null && !requestData.getExchange().getItems().isEmpty()) {

					List<ExchangeItem> listExchangeItem = requestData.getExchange().getItems();
					String returnRequestNum = returnReqNum;
					String exReturnReqNum = exchangeHeaderDo.getExchangeReqNum();
					// Setting custom IDs to exchange items.
					listExchangeItem.stream().forEach(e -> {
						e.setReturnReqNum(returnRequestNum);
						e.setExchangeReqNum(exReturnReqNum);
						// e.setExchangeReqItemid(Integer.toString(counterForExchangeItemNum.getAndIncrement()));
					});

					exchangeItemListDo = ObjectMapperUtils.mapAll(listExchangeItem, ExchangeItem.class);

					// Saving the exchange items to DB
					List<ExchangeItem> savedExchangeItems = exchangeItemRepo.saveAll(exchangeItemListDo);
					// logger.error("> savedExchangeItems : " +
					// savedExchangeItems);
					System.err.println("> savedExchangeItems :  " + savedExchangeItems);
				}
			}
			return ResponseEntity.status(HttpStatus.OK)
					.header("message", "Draft " + returnReqNum + " saved successfully ")
					.body("Draft " + returnReqNum + " saved successfully ");
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", "Failed " + e).body(e);
		}
	}

	@Override
	public ResponseEntity<Object> createReturnRequest(ReturnOrderRequestPojo requestData) {

		System.err.println(">>>>> Start :: createReturnRequest");
		if ((requestData.getReturns() != null) && requestData.getReturns().getDocVersion() != null)
			if (requestData.getReturns().getDocVersion().contains("DRAFT")) {
				System.err
						.println(">>>>>>Delete Draft " + "retuernReqNum " + requestData.getReturns().getReturnReqNum());
				String exchangeReqNum = null;
				String returnReqNum = requestData.getReturns().getReturnReqNum();
				if (returnReqNum.contains("XCR") && requestData.getExchange() == null) {
					if (requestData.getReturns().getExchangeReqNum() != null
							&& !requestData.getReturns().getExchangeReqNum().isEmpty()) {
						exchangeReqNum = requestData.getReturns().getExchangeReqNum();
					}
				} else {
					if (returnReqNum.contains("XCR")) {
						exchangeReqNum = requestData.getExchange().getExchangeReqNum();
					}
				}
				int deleteExchange = deleteExchangeByReturnReqNum(returnReqNum, exchangeReqNum);
				System.err.println("deleteExhcange " + deleteExchange);

				requestData.getReturns().setReturnReqNum("");
				if (requestData.getExchange() != null) {
					requestData.getExchange().setExchangeReqNum("");

				}
			}

		MailAlertUtil mailUtil = new MailAlertUtil();

		ReturnRequestHeader savedReturnHeader = null;
		ReturnRequestHeader returnRequestHeaderDo = null;

		ExchangeHeader exchangeHeaderDo = null;
		ExchangeOrder exchangeOrder = null;
		List<ExchangeItem> exchangeItemListDo = null;
		String returnReqNum = null;
		String exchangeReqNum = null;
		List<ReturnItem> returnItemListDo = null;
		String fileDownloadUri = null;
		List<Address> savedListAdress = null;

		// Counters for items
		AtomicInteger counterForReturnItemNum = new AtomicInteger(1);
		AtomicInteger counterForExchangeItemNum = new AtomicInteger(0);

		System.err.println("> counterForReturnItemNum : " + counterForReturnItemNum);
		System.err.println("> counterForExchangeItemNum : " + counterForExchangeItemNum);

		try {
			if (requestData.getExchange() != null) {
				exchangeOrder = requestData.getExchange();
			}
			System.err.println("mappingId " + requestData.getReturns().getMappingId());
			if (requestData.getReturns().getReturnReqNum() == null
					|| requestData.getReturns().getReturnReqNum().isEmpty()) {
				if (requestData.getReturns().getMappingId() == null
						|| requestData.getReturns().getMappingId().isEmpty()) {
					requestData.getReturns().setMappingId("");
				}
				if (exchangeOrder != null) {

					sequenceNumberGen = SequenceNumberGen.getInstance();
					Session session = entityManager.unwrap(Session.class);
					System.err.println("session : " + session);
					String tempId = sequenceNumberGen
							.getNextSeqNumber(ReturnExchangeConstants.RETURN_SEQ_EXCHANGE_PREFIX, 8, session);
					System.err.println("returnReqNum " + tempId);
					returnReqNum = tempId;
					// seqService.getSequenceNoByMappingId(
					// ReturnExchangeConstants.RETURN_SEQ_EXCHANGE_PREFIX,
					// requestData.getReturns().getMappingId());

					ReturnRequestHeader returnRequestHeader = returnHeaderRepo.findByReturnReqNum(returnReqNum);

					// if (returnRequestHeader != null) {
					// returnReqNum = seqService.getSequenceNoByMappingId(
					// DkshApplicationConstants.RETURN_SEQ_EXCHANGE_PREFIX,
					// requestData.getReturns().getMappingId());
					//
					// }

					tempId = sequenceNumberGen.getNextSeqNumber(ReturnExchangeConstants.EXCHANGE_SEQ_PREFIX, 8,
							session);
					exchangeReqNum = tempId;
					// seqService.getSequenceNoByMappingId(DkshApplicationConstants.EXCHANGE_SEQ_PREFIX,
					// requestData.getReturns().getMappingId());
					requestData.getReturns().setReturnReqNum(returnReqNum);
					requestData.getExchange().setExchangeReqNum(returnReqNum + "/" + exchangeReqNum);
				} else {

					sequenceNumberGen = SequenceNumberGen.getInstance();
					Session session = entityManager.unwrap(Session.class);
					System.err.println("session : " + session);
					String tempId = sequenceNumberGen.getNextSeqNumber(ReturnExchangeConstants.RETURN_SEQ_PREFIX, 8,
							session);
					returnReqNum = tempId;
					// seqService.getSequenceNoByMappingId(DkshApplicationConstants.RETURN_SEQ_PREFIX,
					// requestData.getReturns().getMappingId());

					// ReturnRequestHeaderDo returnRequestHeader =
					// returnHeaderRepo.findByReturnReqNum(returnReqNum);
					//
					// if (returnRequestHeader != null) {
					// returnReqNum =
					// seqService.getSequenceNoByMappingId(DkshApplicationConstants.RETURN_SEQ_PREFIX,
					// requestData.getReturns().getMappingId());
					//
					// }
					requestData.getReturns().setReturnReqNum(returnReqNum);

				}
			} else {
				returnReqNum = requestData.getReturns().getReturnReqNum();
				requestData.getReturns().setReturnReqNum(returnReqNum);
				System.err.println("returnReqNum : " + returnReqNum);
				if (requestData.getExchange() != null) {
					System.err.println("save as exchange");
					if (requestData.getExchange().getExchangeReqNum() != null
							&& !requestData.getExchange().getExchangeReqNum().isEmpty()) {

						exchangeReqNum = requestData.getExchange().getExchangeReqNum();

					}
				}
			}

			// Setting the sequence to header.

			// > Start : Return Header
			returnRequestHeaderDo = ObjectMapperUtils.map(requestData.getReturns(), ReturnRequestHeader.class);
			// save address
			if (requestData.getReturns().getAddress() != null && !requestData.getReturns().getAddress().isEmpty()) {
				List<Address> listAdress = requestData.getReturns().getAddress();
				String returnRequestNum = returnReqNum;
				listAdress.stream().forEach(r -> {
					r.setReturnReqNum(returnRequestNum);
				});
				// saved data to db
				savedListAdress = addressRepo.saveAll(listAdress);
				// logger.error("> savedReturnAddressr : " + savedListAdress);
				System.err.println("> savedReturnAddressr : " + savedListAdress);
			}

			// save attachement
			if (requestData.getReturns().getAttachment() != null
					&& !requestData.getReturns().getAttachment().isEmpty()) {
				List<Attachment> listSaveAttachement = requestData.getReturns().getAttachment();
				String returnRequestNum = returnReqNum;
				listSaveAttachement.stream().forEach(r -> {
					r.setReturnReqNum(returnRequestNum);
				});
				List<Attachment> listAttachment = attachmentRepo.saveAll(listSaveAttachement);
				// logger.error("> savedReturnAttachment : " + listAttachment);
				System.err.println("> savedReturnAttachment :  " + listAttachment);
				// share point upload zip file

				// File sharePointfile = convertByteArrayToFile(returnReqNum,
				// listSaveAttachement);
				String salesOrg = returnRequestHeaderDo.getSalesOrg();
				salesOrg = salesOrg.substring(0, salesOrg.length() - 2);
				// String message = putRecordInSharePoint(sharePointfile,
				// salesOrg);
				// System.err.println("sharePoint uploaded " + message);

				fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()

						// .path("/Attachment/downloadFileByReturnReqNum/").path(returnRequestNum
						// + "&" + salesOrg)
						.path("/Attachment/downloadFileByReturnReqNum/").path(returnRequestNum).toUriString();

				returnRequestHeaderDo.setDocumentUrl(fileDownloadUri);
				requestData.getReturns().setDocumentUrl(fileDownloadUri);

			}

			// Setting the sequence to header.
			// Saving data to DB.
			returnRequestHeaderDo.setDocVersion("PROCESSING");

			savedReturnHeader = returnHeaderRepo.save(returnRequestHeaderDo);
			System.err.println("> savedReturnHeader : " + savedReturnHeader);

			// > End : Return Header

			// > Start : Return Items
			if (requestData.getReturns().getItems() != null && !requestData.getReturns().getItems().isEmpty()) {
				String reqNumber = returnReqNum;
				List<ReturnItem> listOfReturnItem = requestData.getReturns().getItems();

				// Assigning request no to items
				listOfReturnItem.stream().forEach(r -> {
					r.setReturnReqNum(reqNumber);
					// r.setReturnReqItemid(Integer.toString(counterForReturnItemNum.getAndIncrement()));

					// Need to work later, exceptions not handled like seq in
					// headers
				});

				returnItemListDo = ObjectMapperUtils.mapAll(listOfReturnItem, ReturnItem.class);

				// Save return items to DB.
				List<ReturnItem> savedReturnItem = returnItemRepo.saveAll(returnItemListDo);
				System.err.println("> savedReturnItem :  " + savedReturnItem);
				// > End : Return Items

				// > Start : Exchange Header
				if (requestData.getExchange() != null) {
					exchangeHeaderDo = ObjectMapperUtils.map(requestData.getExchange(), ExchangeHeader.class);

					// Setting the sequence No.
					exchangeHeaderDo.setExchangeReqNum(requestData.getExchange().getExchangeReqNum());
					exchangeHeaderDo.setReturnReqNum(requestData.getReturns().getReturnReqNum());
					exchangeHeaderDo.setDocumentUrl(fileDownloadUri);

					exchangeHeaderDo.setDocVersion("PROCESSING");
					if (fileDownloadUri != null) {
						exchangeHeaderDo.setDocumentUrl(fileDownloadUri);
						requestData.getExchange().setDocumentUrl(fileDownloadUri);
					}
					exchangeHeaderDo.setCreatedAt(new Date());
					/*
					 * requestData.getExchange().setExchangeReqNum(returnReqNum
					 * + "/" + exchangeReqNum);
					 */
					if (requestData.getExchange().getAddress() != null
							&& !requestData.getExchange().getAddress().isEmpty()) {
						List<Address> listAdress = requestData.getExchange().getAddress();
						String returnRequestNum = exchangeReqNum;
						listAdress.stream().forEach(r -> {
							r.setReturnReqNum(returnRequestNum);
						});
						// saved data to db
						savedListAdress = addressRepo.saveAll(listAdress);
						// logger.error("> savedReturnAddressr : " +
						// savedListAdress);
						System.err.println("> savedReturnAddressr : " + savedListAdress);
					}

					// Saving Exchange Header
					ExchangeHeader savedExchangeHeader = exchangeHeaderRepo.save(exchangeHeaderDo);
					System.err.println("> savedExchangeHeader  " + savedExchangeHeader);
					// > End : Exchange Header

					// > Start : Exchange Item
					if (!requestData.getExchange().getItems().isEmpty()
							&& requestData.getExchange().getItems() != null) {

						List<ExchangeItem> listExchangeItem = requestData.getExchange().getItems();
						String requestNumber = returnReqNum;
						String exchangeRequestNumber = exchangeHeaderDo.getExchangeReqNum();
						// Setting custom IDs to exchange items.
						listExchangeItem.stream().forEach(e -> {
							e.setReturnReqNum(requestNumber);
							e.setExchangeReqNum(exchangeRequestNumber);
							// e.setExchangeReqItemid(Integer.toString(counterForExchangeItemNum.getAndIncrement()));

						});

						exchangeItemListDo = ObjectMapperUtils.mapAll(listExchangeItem, ExchangeItem.class);

						// Saving the exchange items to DB
						List<ExchangeItem> savedExchangeItems = exchangeItemRepo.saveAll(exchangeItemListDo);
						System.err.println("> savedExchangeItems :  " + savedExchangeItems);

					}
				} // > End : Exchange Item

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.err.println("Returns and exchange details saved in HANA.");
		try {

			System.err.println("create return order second thread >>>>>");
			// creating a new thread
			Runnable r = new Runnable() {
				public void run() {

					String response = null;
					EccResponseOutputDto responseObject = null;

					SmsSendingDto smsSending = null;

					Map<String, List<ReturnItem>> splittedItemGroupMap = null;

					Map<String, List<OrderConditionDto>> splittedOrderConditionGroupMsp = null;
					List<ReturnItem> returnItemListDo = null;

					// Below lines are added to test splitting logic
					splittedItemGroupMap = splitReturnInvoice(requestData);

					splittedOrderConditionGroupMsp = splitOrderConditionInvoice(requestData);

					// to split Order condition

					printMapData(splittedItemGroupMap, splittedOrderConditionGroupMsp);
					System.err.println("> SplitterGroupMap =" + splittedItemGroupMap);

					// > Start : Odata services

					System.err.println("> Map Size item : " + splittedItemGroupMap.size());
					System.err.println("> orderconditionMap size " + splittedOrderConditionGroupMsp.size());

					List<ODataBatchPayload> oDataPayloadList = new ArrayList<>(splittedItemGroupMap.size());

					if (splittedItemGroupMap.size() > 0) {
						for (Map.Entry<String, List<ReturnItem>> entry : splittedItemGroupMap.entrySet()) {
							List<OrderConditionDto> orderCondition = null;
							List<ReturnItem> mapItemList = entry.getValue();
							if (splittedOrderConditionGroupMsp.containsKey(entry.getKey())) {
								orderCondition = splittedOrderConditionGroupMsp.get(entry.getKey());
							}
							ODataBatchPayload batchItem = assignBatchData(requestData.getReturns(),
									requestData.getReturns().getReturnReqNum(), mapItemList, orderCondition);
							oDataPayloadList.add(batchItem);
						}
					}

					// Adding exchange order to OData
					if (requestData.getExchange() != null) {
						ExchangeOrder exchange = requestData.getExchange();
						List<OrderConditionDto> orderCondition = null;
						List<ExchangeItem> mapItemList = requestData.getExchange().getItems();
						if (requestData.getExchange().getOrderCondition() != null
								&& !requestData.getExchange().getOrderCondition().isEmpty()) {
							orderCondition = requestData.getExchange().getOrderCondition();
						}
						ODataBatchPayload batchItem = assignBatchDataExchange(exchange,
								requestData.getExchange().getExchangeReqNum(), mapItemList, orderCondition);
						oDataPayloadList.add(batchItem);

					}

					System.err.println("> oDataPayloadList : " + oDataPayloadList);

					// passing data to OData call
					try {
						response = SalesOrderOdataServices.BULK_INSERT(oDataPayloadList,
								ReturnExchangeConstants.RETURN_REQUEST_BATCH_ENDPOINTS,
								ReturnExchangeConstants.RETURN_REQUEST_BATCH_TAG);
						// response =
						// odataServices.postReturnExchangeData(oDataPayloadList);
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.err.println(">odata response " + response);
					// Parsing the OData response
					try {
						responseObject = parseODataResponse(response);
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.err.println("> responseObject : " + responseObject);
					// Updating the DB
					if (responseObject.getStatusCode().contains("201")
							&& responseObject.getReturnResponsePojo() != null) {
						List<ReturnItem> returnItemList = new ArrayList<>();

						for (ReturnOrderResponsePojo returnResponsePojo : responseObject.getReturnResponsePojo()) {
							List<ODataBatchItem> itemList=new ArrayList<>() ;//= returnResponsePojo.getOrderHdrToOrderItem().getResults();
							returnItemListDo = ObjectMapperUtils.mapAll(itemList, ReturnItem.class);
							List<ReturnItem> listReturnUpdate = new ArrayList<>(1);
							if (!returnResponsePojo.getName().contains("/")) {
								System.err.println("check contains XCR " + returnResponsePojo.getCustomerPo());
								System.err.println("check cintains CR " + returnResponsePojo.getCustomerPo());
								for (int i = 0; i < returnItemListDo.size(); i++) {
									// add salesDocument to existing data
									ReturnItem existingReturnItem = returnItemRepo
											.findByRefDocItemAndReturnReqNumAndRefDocNum(itemList.get(i).getRefDocIt(),
													returnResponsePojo.getName(), itemList.get(i).getRefDoc());
									existingReturnItem.setSapReturnOrderNum(returnResponsePojo.getSalesDocument());
									// Add data to list to update
									listReturnUpdate.add(existingReturnItem);
								}

								ReturnRequestHeader returnRequestHeaderDoToUpdateMessage = returnHeaderRepo
										.findByReturnReqNum(returnResponsePojo.getName());

								returnRequestHeaderDoToUpdateMessage.setMessage("Success");
								returnRequestHeaderDoToUpdateMessage.setCreationStatus(true);
								returnRequestHeaderDoToUpdateMessage.setDocVersion("SUCCESS");

								System.err.println("check created " + returnRequestHeaderDoToUpdateMessage);
								ReturnRequestHeader createdDraftCheck = returnHeaderRepo
										.save(returnRequestHeaderDoToUpdateMessage);
								System.err.println("check created " + createdDraftCheck);
								// Update DB records
								List<ReturnItem> updatedRecord = returnItemRepo.saveAll(listReturnUpdate);

								if (updatedRecord.size() > 0) {
									System.err.println("> Record updated Successfully.");
								} else {
									System.err.println("> Oops! Something went wrong in record updates.");
								}
							} else {

								List<ExchangeItem> exchangeItems = exchangeItemRepo.findByReturnReqNumAndExchangeReqNum(
										requestData.getReturns().getReturnReqNum(), returnResponsePojo.getName());
								for (int i = 0; i < exchangeItems.size(); i++) {

									exchangeItems.get(i).setSapSalesOrderNum(returnResponsePojo.getSalesDocument());

									System.err.println("exchnage item " + exchangeItems.get(i));

								}
								ExchangeHeader returnRequestHeaderDoToUpdateMessage = exchangeHeaderRepo
										.findByReturnReqNumAndExchangeReqNum(requestData.getReturns().getReturnReqNum(),
												requestData.getExchange().getExchangeReqNum());
								returnRequestHeaderDoToUpdateMessage.setMessage("Success");
								returnRequestHeaderDoToUpdateMessage.setCreationStatus(true);
								returnRequestHeaderDoToUpdateMessage.setDocVersion("SUCCESS");
								exchangeHeaderRepo.save(returnRequestHeaderDoToUpdateMessage);
								// Update DB recordsexchangeItem ExchangeItemDo

								System.err.println("update exhcnage  item List" + exchangeItems);
								List<ExchangeItem> updatedRecord = exchangeItemRepo.saveAll(exchangeItems);

								if (updatedRecord.size() > 0) {
									System.err.println("> Record updated Successfully.");
								} else {
									System.err.println("> Oops! Something went wrong in record updates.");
								}
							}
						}

						// if (requestData.getReturns().getSmsTrigger() == true
						// && requestData.getReturns().getSmsNumberList() !=
						// null) {
						// smsSending = new SmsSendingDto();
						// smsSending.setMessage("คำร้องขอ หมายเลข " +
						// requestData.getReturns().getReturnReqNum()
						// + " (" + requestData.getReturns().getSoldToParty() +
						// " + "
						// + requestData.getReturns().getSoldToPartyDesc() + ")
						// " + "รอการอนุมัติ."
						// + "To Your return request is created by no."
						// + requestData.getReturns().getReturnReqNum() + " ("
						// + requestData.getReturns().getSoldToParty() + "+"
						// + requestData.getReturns().getSoldToPartyDesc() + ")"
						// + ".Waiting for approval");
						// smsSending.setMessageType("UNICODE");
						// smsSending.setMobileNumList(requestData.getReturns().getSmsNumberList());
						// smsSending.setReport("Y");
						// if (requestData.getReturns().getSmsFrom() != null
						// || !requestData.getReturns().getSmsFrom().isEmpty())
						// {
						// smsSending.setFrom(requestData.getReturns().getSmsFrom());
						// }
						//
						// ResponseEntity<Object> smsResponse =
						// HelperClass.sendSms(smsSending);
						// System.err.println(
						// requestData.getReturns().getReturnReqNum() + "
						// smsResponse " + smsResponse);
						// }

						if (requestData.getReturns().getEmailTrigger() == true) {

							String message = null;
							if (requestData.getReturns().getRoType().equals("01")
									|| requestData.getReturns().getRoType().equals("02")) {
								message = "Request for number " + requestData.getReturns().getReturnReqNum() + " ("
										+ requestData.getReturns().getSoldToParty() + " + "
										+ requestData.getReturns().getSoldToPartyDesc() + ") " + "Waiting for approval."
										+ "To Your return request is created by no."
										+ requestData.getReturns().getReturnReqNum() + " ("
										+ requestData.getReturns().getSoldToParty() + "+"
										+ requestData.getReturns().getSoldToPartyDesc() + ")" + ".Waiting for approval";
							} else {
								message = "Request for Return of Item No." + requestData.getReturns().getReturnReqNum()
										+ " (" + requestData.getReturns().getSoldToParty() + " + "
										+ requestData.getReturns().getSoldToPartyDesc() + ") " + "Waiting for approval."
										+ "To Your return request is created by no."
										+ requestData.getReturns().getReturnReqNum() + " ("
										+ requestData.getReturns().getSoldToParty() + "+"
										+ requestData.getReturns().getSoldToPartyDesc() + ")" + ".Waiting for approval";
							}

							String receipentId = requestData.getReturns().getRequestorEmail();
							mailUtil.sendMailAlert(receipentId, "", ReturnExchangeConstants.RETURN_REQUEST_MAIL_SUBJECT,
									message, "");
						}
					} else {
						ReturnRequestHeader returnHeaderDo = returnHeaderRepo
								.findByReturnReqNum(requestData.getReturns().getReturnReqNum());
						returnHeaderDo.setMessage(responseObject.getMessage().toString());
						returnHeaderDo.setCreationStatus(false);
						returnHeaderDo.setDocVersion("ERROR");
						returnHeaderRepo.save(returnHeaderDo);
						if (requestData.getReturns().getReturnReqNum().contains("X")) {
							ExchangeHeader exchangeHeaderUpdateDo = exchangeHeaderRepo
									.findByReturnReqNumAndExchangeReqNum(requestData.getReturns().getReturnReqNum(),
											requestData.getExchange().getExchangeReqNum());
							exchangeHeaderUpdateDo.setMessage(responseObject.getMessage());
							exchangeHeaderUpdateDo.setCreationStatus(false);
							exchangeHeaderUpdateDo.setDocVersion("ERROR");
							exchangeHeaderRepo.save(exchangeHeaderUpdateDo);
						}
					}

				}
			};
			new Thread(r).start();

			// end of thread

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK)
				.header("message", "ReturnOrder " + returnReqNum + " is being proccessed")
				.body("ReturnOrder " + returnReqNum + " is being proccessed");

	}

	public int deleteExchangeByReturnReqNum(String returnreqNum, String exchangeReqNum) {

		int deleted = 0;

		if (exchangeReqNum != null && !exchangeReqNum.isEmpty()) {
			int result1 = exchangeItemRepo.deleteByReturnRegNum(returnreqNum);
			deleted = deleted + result1;
			int result2 = exchangeHeaderRepo.deleteByReturnReqNum(returnreqNum, exchangeReqNum);
			deleted = deleted + result2;
			int result3 = returnItemRepo.deleteByReturnReqNum(returnreqNum);
			deleted = deleted + result3;
			int result4 = returnHeaderRepo.deleteByReturnReqNum(returnreqNum);
			deleted = deleted + result4;
		} else {

			int result3 = returnItemRepo.deleteByReturnReqNum(returnreqNum);
			deleted = deleted + result3;
			int result4 = returnHeaderRepo.deleteByReturnReqNum(returnreqNum);
			deleted = deleted + result4;
		}

		return deleted;
	}

	private Map<String, List<ReturnItem>> splitReturnInvoice(ReturnOrderRequestPojo requestData) {

		// logger.error("/nSplit logic started >>>>>>>>>>>");

		if (requestData.getReturns().getItems() != null && requestData.getReturns().getItems().size() == 0)
			throw new NoRecordFoundException(ReturnExchangeConstants.RETURN_ITEM_NOT_FOUND);

		Map<String, List<ReturnItem>> returnItemsgroup = new HashMap<String, List<ReturnItem>>();

		// Splitting started for InvoiceRef Item
		if (requestData.getReturns().getItems() != null)
			for (ReturnItem returnItem : requestData.getReturns().getItems()) {
				String refNum = returnItem.getRefDocNum();

				// logger.error("return Item : " + returnItem.toString());
				System.err.println("return Item : " + returnItem.toString());

				List<ReturnItem> list;

				if (returnItemsgroup.containsKey(refNum)) { // refNum already
															// exists
															// in map
					// logger.error("Ref. No. " + refNum + " found in map.");
					System.err.println("Ref. No. " + refNum + " found in map.");
					// list = returnItemsgroup.get(refNum);
					boolean isAdded = returnItemsgroup.get(refNum).add(returnItem);

					// logger.error("Item added to existing list : " + isAdded);
					System.err.println("Item added to existing list : " + isAdded);

				} else {
					list = new ArrayList<>(1);
					list.add(returnItem);
					returnItemsgroup.put(refNum, list);
				}
			}

		// logger.error("/n <<<<<<<<<< End of Split logic.");
		System.err.println("/n <<<<<<<<<< End of Split logic.");

		return returnItemsgroup;
	}

	private Map<String, List<OrderConditionDto>> splitOrderConditionInvoice(ReturnOrderRequestPojo requestData) {
		Map<String, List<OrderConditionDto>> orderConditionGroup = new HashMap<String, List<OrderConditionDto>>();

		if (requestData.getReturns().getOrderCondition() != null) {

			for (OrderConditionDto orderCondition : requestData.getReturns().getOrderCondition()) {
				String refDocNum = orderCondition.getRefDoc();

				// logger.error("order Condition of refInvoice : " +
				// orderCondition.toString());
				System.err.println("order Condition of refInvoice : " + orderCondition.toString());

				List<OrderConditionDto> list;

				if (orderConditionGroup.containsKey(refDocNum)) { // refNum
																	// already
																	// exists
					// in map
					// logger.error("Ref. No. " + refDocNum + " found in map.");
					System.err.println("Ref. No. " + refDocNum + " found in map.");
					// list = returnItemsgroup.get(refNum);
					boolean isAdded = orderConditionGroup.get(refDocNum).add(orderCondition);

					// logger.error("order Condition to existing list : " +
					// isAdded);
					System.err.println("order Condition  to existing list : " + isAdded);

				} else {
					list = new ArrayList<>(1);
					list.add(orderCondition);
					orderConditionGroup.put(refDocNum, list);
				}
			}
		}
		// logger.error("/n <<<<<<<<<< End of Split logic.");
		System.err.println("/n <<<<<<<<<< End of Split logic.");

		return orderConditionGroup;

	}

	private ODataBatchPayload assignBatchData(ReturnOrder requestData, String returnReqNum,
			List<ReturnItem> mapItemList, List<OrderConditionDto> orderCondition) {
		System.err.println("> Start :: ODataBatchPayload method.");

		ODataBatchPayload batchItem = new ODataBatchPayload();
		batchItem = ObjectMapperUtils.map(requestData, ODataBatchPayload.class);

		List<ODataBatchItem> returnItemList = new ArrayList<>(mapItemList.size());
		List<OrderConditionDto> orderConditionList = new ArrayList<>();
		List<OrderHdrToOrderPartnerDto> orderPartnerList = new ArrayList<>();
		ODataBatchItem item;
		OrderConditionDto condition;
		OrderHdrToOrderPartnerDto orderHdrToOrderPartnerDto;

		// batchItem.setRefDocCat("");
		batchItem.setRoType(requestData.getRoType() != null ? requestData.getRoType() : "");
		batchItem.setPayer(requestData.getPayer() != null ? requestData.getPayer() : "");
		batchItem.setSalesDocument("");
		batchItem.setSalesOrg(requestData.getSalesOrg());
		batchItem.setDocType(requestData.getOrderType());
		batchItem.setOrdReason(requestData.getOrderReason());
//		if (requestData.getCustomerPo() == null || requestData.getCustomerPo().isEmpty()) {
//			batchItem.setCustomerPo(returnReqNum);
//
//		} else {
//			batchItem.setCustomerPo(requestData.getCustomerPo());
//		}
		//batchItem.setHdrDelBlk("01");
		// batchItem.setRefDoc("");
		batchItem.setRefNum(requestData.getReferenceNum());
		batchItem.setDistrChan(requestData.getDistributionChannel());
		batchItem.setDivision(requestData.getDivision());
		batchItem.setSoldToParty(requestData.getSoldToParty());
		batchItem.setShipToParty(requestData.getShipToParty());
		batchItem.setFlagRoSo(requestData.getFlagRoSo());
		batchItem.setReasonOwner(requestData.getReasonOwner());
		// batchItem.setHeaderText(requestData.getRequestRemark());
		if (requestData.getRequestRemark() != null)
			batchItem.setRemarks(requestData.getRequestRemark());
		else
			batchItem.setRemarks("");
		batchItem.setBillToParty(requestData.getBillToParty());
		batchItem.setName(returnReqNum);
		if (requestData.getRequestorName() != null)
			batchItem.setRequestedBy(requestData.getRequestorName());
		else
			batchItem.setRequestedBy("");

		if (requestData.getDocumentUrl() != null) {
			batchItem.setAttachmentUrl(requestData.getDocumentUrl());
		} else
			batchItem.setAttachmentUrl("");

		if (mapItemList != null)
			for (ReturnItem data : mapItemList) {
				item = new ODataBatchItem();

				item.setHgLvItem(data.getHigherLevel());
//				item.setItemDelBlk("01");
				item.setRefDoc(data.getRefDocNum());
				if (data.getBatch() != null)
					item.setBatch(data.getBatch());
				else
					item.setBatch("");
				item.setSalesDocument("");
				item.setSalesUnit(data.getReturnUom());
				item.setRefDocIt(data.getRefDocItem());
				item.setReqQty("" + data.getReturnQty());
//				item.setUnitPrice("" + data.getUnitPriceCc());
				item.setItmNumber(data.getReturnReqItemid());
				// item.setRefDocCat("");
				// item.setTargetQty("0.00");
				item.setItemText(data.getShortText());
				if (data.getPlant() != null)
					item.setPlant(data.getPlant());
				else
					item.setPlant("");
				item.setMaterial(data.getMaterial());
				if (data.getSerialNum() != null)
					item.setSerialNum(data.getSerialNum());
				else
					item.setSerialNum("");
				if (data.getStorageLocation() != null)
					item.setStoreLoc(data.getStorageLocation());
				else
					item.setStoreLoc("");
				item.setItemText(data.getItemText());
				item.setPaymentTerms(data.getPaymentTerms());
				item.setConditionGroup4(data.getConditionGroup4());
				if (data.getPricingDate() != null) {
					item.setPricingDate("" + data.getPricingDate());
				}
				// item.setPricingDate("");
				if (data.getServiceRenderedDate() != null) {
					item.setServiceRenderedDate("" + data.getServiceRenderedDate());
				}
				// item.setServiceRenderedDate("");
				returnItemList.add(item);

			}
		if (orderCondition != null && !orderCondition.isEmpty()) {
			for (OrderConditionDto order : orderCondition) {

				condition = new OrderConditionDto();
				condition.setCalculationType(order.getCalculationType());
				condition.setCondCounter(order.getCondCounter());
				condition.setCondFlag(order.getCondFlag());
				condition.setCondPricingUnit(order.getCondPricingUnit());
				condition.setCondRate(order.getCondRate());
				condition.setCondType(order.getCondType());
				condition.setCondUnit(order.getCondUnit());
				condition.setCondUpdateFlag(order.getCondUpdateFlag());
				condition.setCurrency(order.getCurrency());
				condition.setItemNumber(order.getItemNumber());
				condition.setSalesDocument(order.getSalesDocument());
				condition.setStepNumber(order.getStepNumber());
				condition.setPlant(order.getPlant());

				orderConditionList.add(condition);

			}
		}

		if (requestData.getOrderHdrToOrderPartnerDto() != null
				&& !requestData.getOrderHdrToOrderPartnerDto().isEmpty()) {

			for (OrderHdrToOrderPartnerDto orderHdrToOrder : requestData.getOrderHdrToOrderPartnerDto()) {

				orderHdrToOrderPartnerDto = new OrderHdrToOrderPartnerDto();

				orderHdrToOrderPartnerDto.setSalesDocument(orderHdrToOrder.getSalesDocument());
				orderHdrToOrderPartnerDto.setPartnerRole(orderHdrToOrder.getPartnerRole());
				orderHdrToOrderPartnerDto.setName1(orderHdrToOrder.getName1());
				orderHdrToOrderPartnerDto.setName2(orderHdrToOrder.getName2());
				orderHdrToOrderPartnerDto.setName3(orderHdrToOrder.getName3());
				orderHdrToOrderPartnerDto.setName4(orderHdrToOrder.getName4());
				orderHdrToOrderPartnerDto.setStreet2(orderHdrToOrder.getStreet2());
				orderHdrToOrderPartnerDto.setStreet3(orderHdrToOrder.getStreet3());
				orderHdrToOrderPartnerDto.setStreet5(orderHdrToOrder.getStreet5());
				orderHdrToOrderPartnerDto.setDistrict(orderHdrToOrder.getDistrict());
				orderHdrToOrderPartnerDto.setDifferentCity(orderHdrToOrder.getDifferentCity());
				orderHdrToOrderPartnerDto.setPostalCode(orderHdrToOrder.getPostalCode());
				orderHdrToOrderPartnerDto.setCity(orderHdrToOrder.getCity());
				orderHdrToOrderPartnerDto.setRegion(orderHdrToOrder.getRegion());
				orderHdrToOrderPartnerDto.setCountry(orderHdrToOrder.getCountry());
				orderHdrToOrderPartnerDto.setTelephone(orderHdrToOrder.getTelephone());
				orderHdrToOrderPartnerDto.setMobilePhone(orderHdrToOrder.getMobilePhone());
				orderHdrToOrderPartnerDto.setTaxId(orderHdrToOrder.getTaxId());
				orderHdrToOrderPartnerDto.setBCode(orderHdrToOrder.getBCode());
				orderHdrToOrderPartnerDto.setBpNummr(orderHdrToOrder.getBpNummr());
				orderPartnerList.add(orderHdrToOrderPartnerDto);

			}

		}

		OrderHdrToOrderItem orderItem = new OrderHdrToOrderItem(returnItemList);
		if (orderCondition != null && !orderCondition.isEmpty()) {
			OrderHdrToOrderCondition orderHdrToOrderCondition = new OrderHdrToOrderCondition(orderConditionList);
			batchItem.setOrderHdrToOrderCondition(orderHdrToOrderCondition);
		}
		if (orderPartnerList != null && !orderPartnerList.isEmpty()) {

			OrderHdrToOrderPartner orderHdrToOrderPartner = new OrderHdrToOrderPartner(orderPartnerList);
			batchItem.setOrderHdrToOrderPartner(orderHdrToOrderPartner);
		}
		batchItem.setOrderHdrToOrderItem(orderItem);

		System.err.println("> Batch Item : " + batchItem);
		System.err.println("> Stop :: ODataBatchPayload method.");

		return batchItem;

	}

	private void printMapData(Map<String, List<ReturnItem>> map, Map<String, List<OrderConditionDto>> orderCondMap) {
		map.entrySet().stream().forEach(e -> System.err.println(e.getKey() + " >> " + e.getValue()));
		orderCondMap.entrySet().stream().forEach(e -> System.err.println(e.getKey() + " >> " + e.getValue()));
	}

	private ODataBatchPayload assignBatchDataExchange(ExchangeOrder exchangeData, String exchangeReqNum,
			List<ExchangeItem> mapItemList, List<OrderConditionDto> orderCondition) {
		System.err.println("> Start :: ODataBatchPayload method.");

		ODataBatchPayload batchItem = new ODataBatchPayload();
		batchItem = ObjectMapperUtils.map(exchangeData, ODataBatchPayload.class);

		List<ODataBatchItem> returnItemList = null;
		if (mapItemList != null)
			returnItemList = new ArrayList<>(mapItemList.size());
		List<OrderConditionDto> orderConditionList = new ArrayList<>();
		List<OrderHdrToOrderPartnerDto> orderPartnerList = new ArrayList<>();
		ODataBatchItem item;
		OrderConditionDto condition;
		OrderHdrToOrderPartnerDto orderHdrToOrderPartnerDto;

		// batchItem.setRefDocCat("");
		batchItem.setRoType(exchangeData.getRoType() != null ? exchangeData.getRoType() : "");
		batchItem.setPayer(exchangeData.getPayer() != null ? exchangeData.getPayer() : "");
		batchItem.setSalesDocument("");
		batchItem.setSalesOrg(exchangeData.getSalesOrg());
		batchItem.setDocType(exchangeData.getOrderType());
		if (exchangeData.getCustomerPo() == null || exchangeData.getCustomerPo().isEmpty()) {
			batchItem.setCustomerPo(exchangeReqNum);

		} else {
			batchItem.setCustomerPo(exchangeData.getCustomerPo());
		}
		batchItem.setHdrDelBlk("01");
		// batchItem.setRefDoc("");
		batchItem.setRefNum(exchangeData.getReferenceNum());
		batchItem.setDistrChan(exchangeData.getDistributionChannel());
		batchItem.setDivision(exchangeData.getDivision());
		batchItem.setSoldToParty(exchangeData.getSoldToParty());
		batchItem.setShipToParty(exchangeData.getShipToParty());
		batchItem.setFlagRoSo(exchangeData.getFlagRoSo());
		batchItem.setReasonOwner(exchangeData.getReasonOwner());
		// batchItem.setHeaderText(exchangeData.getRequestRemark());
		batchItem.setRemarks(exchangeData.getRequestRemark());
		batchItem.setBillToParty(exchangeData.getBillToParty());
		batchItem.setOrdReason(exchangeData.getOrdReason());
		batchItem.setName(exchangeReqNum);
		batchItem.setRequestedBy(exchangeData.getRequestorName());
		if (exchangeData.getDelComplete() != null && !exchangeData.getDelComplete().isEmpty()) {
			batchItem.setComplDlv(exchangeData.getDelComplete());
		}
		batchItem.setName(exchangeReqNum);
		if (exchangeData.getDocumentUrl() != null) {
			batchItem.setAttachmentUrl(exchangeData.getDocumentUrl());
		}

		if (mapItemList != null)
			for (ExchangeItem data : mapItemList) {
				item = new ODataBatchItem();

				item.setHgLvItem(data.getHigherLevel());
				item.setItemDelBlk("01");
				item.setRefDoc(data.getRefDocNum());
				item.setSalesDocument("");
				item.setSalesUnit(data.getReturnUom());
				item.setRefDocIt(data.getRefDocItem());
				item.setReqQty("" + data.getReturnQty());
				item.setUnitPrice("" + data.getUnitPriceCc());
				item.setItmNumber(data.getExchangeReqItemid());
				// item.setRefDocCat("");
				// item.setTargetQty("0.00");
				item.setItemText(data.getShortText());
				item.setPlant(data.getPlant());
				item.setMaterial(data.getMaterial());
				item.setStoreLoc(data.getStorageLocation());
				item.setSerialNum(data.getSerialNum());
				item.setBatch(data.getBatch());
				item.setPaymentTerms(data.getPaymentTerms());
				item.setConditionGroup4(data.getConditionGroup4());

				if (data.getPricingDate() != null) {
					System.err.println("check pricingDate " + data.getPricingDate());
					item.setPricingDate("" + data.getPricingDate());
				}
				// item.setPricingDate("");
				if (data.getServiceRenderedDate() != null) {
					item.setServiceRenderedDate("" + data.getServiceRenderedDate());
				}
				// item.setServiceRenderedDate("");

				returnItemList.add(item);

			}

		if (orderCondition != null && !orderCondition.isEmpty()) {
			for (OrderConditionDto order : orderCondition) {

				condition = new OrderConditionDto();
				condition.setCalculationType(order.getCalculationType());
				condition.setCondCounter(order.getCondCounter());
				condition.setCondFlag(order.getCondFlag());
				condition.setCondPricingUnit(order.getCondPricingUnit());
				condition.setCondRate(order.getCondRate());
				condition.setCondType(order.getCondType());
				condition.setCondUnit(order.getCondUnit());
				condition.setCondUpdateFlag(order.getCondUpdateFlag());
				condition.setCurrency(order.getCurrency());
				condition.setItemNumber(order.getItemNumber());
				condition.setSalesDocument(order.getSalesDocument());
				condition.setStepNumber(order.getStepNumber());
				condition.setPlant(order.getPlant());

				orderConditionList.add(condition);

			}
		}

		if (exchangeData.getOrderHdrToOrderPartnerDto() != null
				&& !exchangeData.getOrderHdrToOrderPartnerDto().isEmpty()) {

			for (OrderHdrToOrderPartnerDto orderHdrToOrder : exchangeData.getOrderHdrToOrderPartnerDto()) {

				orderHdrToOrderPartnerDto = new OrderHdrToOrderPartnerDto();

				orderHdrToOrderPartnerDto.setSalesDocument(orderHdrToOrder.getSalesDocument());
				orderHdrToOrderPartnerDto.setPartnerRole(orderHdrToOrder.getPartnerRole());
				orderHdrToOrderPartnerDto.setName1(orderHdrToOrder.getName1());
				orderHdrToOrderPartnerDto.setName2(orderHdrToOrder.getName2());
				orderHdrToOrderPartnerDto.setName3(orderHdrToOrder.getName3());
				orderHdrToOrderPartnerDto.setName4(orderHdrToOrder.getName4());
				orderHdrToOrderPartnerDto.setStreet2(orderHdrToOrder.getStreet2());
				orderHdrToOrderPartnerDto.setStreet3(orderHdrToOrder.getStreet3());
				orderHdrToOrderPartnerDto.setStreet5(orderHdrToOrder.getStreet5());
				orderHdrToOrderPartnerDto.setDistrict(orderHdrToOrder.getDistrict());
				orderHdrToOrderPartnerDto.setDifferentCity(orderHdrToOrder.getDifferentCity());
				orderHdrToOrderPartnerDto.setPostalCode(orderHdrToOrder.getPostalCode());
				orderHdrToOrderPartnerDto.setCity(orderHdrToOrder.getCity());
				orderHdrToOrderPartnerDto.setRegion(orderHdrToOrder.getRegion());
				orderHdrToOrderPartnerDto.setCountry(orderHdrToOrder.getCountry());
				orderHdrToOrderPartnerDto.setTelephone(orderHdrToOrder.getTelephone());
				orderHdrToOrderPartnerDto.setMobilePhone(orderHdrToOrder.getMobilePhone());
				orderHdrToOrderPartnerDto.setTaxId(orderHdrToOrder.getTaxId());
				orderHdrToOrderPartnerDto.setBCode(orderHdrToOrder.getBCode());
				orderHdrToOrderPartnerDto.setBpNummr(orderHdrToOrder.getBpNummr());
				orderPartnerList.add(orderHdrToOrderPartnerDto);

			}

		}

		OrderHdrToOrderItem orderItem = new OrderHdrToOrderItem(returnItemList);
		if (orderCondition != null && !orderCondition.isEmpty()) {
			OrderHdrToOrderCondition orderHdrToOrderCondition = new OrderHdrToOrderCondition(orderConditionList);
			batchItem.setOrderHdrToOrderCondition(orderHdrToOrderCondition);
		}
		if (orderPartnerList != null && !orderPartnerList.isEmpty()) {

			OrderHdrToOrderPartner orderHdrToOrderPartner = new OrderHdrToOrderPartner(orderPartnerList);
			batchItem.setOrderHdrToOrderPartner(orderHdrToOrderPartner);
		}
		batchItem.setOrderHdrToOrderItem(orderItem);

		System.err.println("> Batch Item : " + batchItem);
		System.err.println("> Stop :: ODataBatchPayload method.");

		return batchItem;

	}

	private EccResponseOutputDto parseODataResponse(String request) throws IOException {

		EccResponseOutputDto eccrepsone = new EccResponseOutputDto();
		String[] splittedRequest = request.split("\"d\":");
		System.err.println("response 1" + splittedRequest[0]);
		System.err.println("responseSize " + splittedRequest.length);
		List<String> responseStr = new ArrayList<>();
		List<ReturnOrderResponsePojo> respObjectList = new ArrayList<>();
		if (splittedRequest.length > 1) {
			for (int i = 0; i < splittedRequest.length; i++) {
				if (i > 0) {
					String[] strArr = splittedRequest[i].split("}]}}}");
					String outputStr = strArr[0] + "}]}}";
					responseStr.add(outputStr);
					ReturnOrderResponsePojo respObject = new ObjectMapper().readValue(outputStr,
							ReturnOrderResponsePojo.class);
					JSONObject obj = new JSONObject(outputStr);
					OrderHdrToOrderItem orderHdrToOrderItem = new ObjectMapper()
							.readValue(obj.get("OrderHdrToOrderItem").toString(), OrderHdrToOrderItem.class);
					respObject.setOrderHdrToOrderItem(orderHdrToOrderItem);
					respObjectList.add(respObject);
				}
			}
			eccrepsone.setReturnResponsePojo(respObjectList);
			eccrepsone.setMessage("Success");
			eccrepsone.setStatusCode("201");
			return eccrepsone;
		} else {
			String[] splittedResponse = splittedRequest[0].split("dataserviceversion: 1.0");
			String errorResponse = null;
			String errorResponse2 = null;
			JSONObject obj = null;
			StringBuffer buffer = new StringBuffer();
			if (splittedResponse.length > 1) {
				errorResponse = splittedResponse[1];
			}
			errorResponse2 = splittedResponse[0];
			System.err.println("errorResponse" + errorResponse);
			if (errorResponse == null) {
				obj = new JSONObject(errorResponse2);
			}
			obj = new JSONObject(errorResponse);
			JSONObject object = obj.getJSONObject("error").getJSONObject("innererror");

			JSONArray errorArray = object.getJSONArray("errordetails");

			if (!errorArray.isEmpty()) {
				for (int i = 0; i < errorArray.length(); i++) {
					JSONObject jsonResponse = (JSONObject) errorArray.get(i);
					String message = jsonResponse.get("message").toString();
					// logger.error("message =" + message);
					System.out.println("message: " + message);
					buffer.append(message);
					if (i != errorArray.length() - 1) {
						buffer.append(" and ");
					}
				}
			} else {
				String errorMessage = obj.getJSONObject("error").getJSONObject("message").get("value").toString();

				System.err.println("errorMessage from object" + errorMessage);
				buffer.append(errorMessage);
			}
			String errorMessage = buffer.toString();
			System.err.println("errorMessage " + errorMessage);
			eccrepsone.setMessage(errorMessage);
			eccrepsone.setStatusCode("400");
			return eccrepsone;
		}

	}

	@Override
	public ResponseEntity<Object> findByReturnReqNum(String returnReqNum) {

		try {
			if (!HelperClass.checkString(returnReqNum)) {

				ReturnRequestHeaderDto returnRequestHeaderDto = new ReturnRequestHeaderDto();
				/*
				 * List<AttachmentDo> attachmentDo = null; List<AddresDo>
				 * adressList = null;
				 */
				ReturnRequestHeader returnRequestHeaderDo = returnHeaderRepo.findByReturnReqNum(returnReqNum);

				if (returnRequestHeaderDo != null) {

					returnRequestHeaderDto = ObjectMapperUtils.map(returnRequestHeaderDo, ReturnRequestHeaderDto.class);

					List<ReturnItem> returnItemListDo = returnItemRepo.findByReturnReqNum(returnReqNum);
					if (!returnItemListDo.isEmpty() && returnItemListDo != null) {
						returnRequestHeaderDto
								.setListItemDto(ObjectMapperUtils.mapAll(returnItemListDo, ReturnItemDto.class));
					}

					ExchangeHeader exchangeheaderDo = exchangeHeaderRepo.findByReturnReqNum(returnReqNum);

					if (exchangeheaderDo != null) {
						returnRequestHeaderDto
								.setExchangeDto(ObjectMapperUtils.map(exchangeheaderDo, ExchangeHeaderDto.class));

						List<ExchangeItem> exchangeItemListDo = exchangeItemRepo.findByReturnRegNum(returnReqNum);
						if (!exchangeItemListDo.isEmpty()) {

							returnRequestHeaderDto.getExchangeDto().setListExhangeItemDto(
									ObjectMapperUtils.mapAll(exchangeItemListDo, ExchangeItemDto.class));

						}

					}
					List<Attachment> attachmentDo = attachmentRepo.findByReturnReqNum(returnReqNum);
					if (!attachmentDo.isEmpty() && attachmentDo != null) {
						// returnRequestHeaderDto.setListAttachementDto(ObjectMapperUtils.mapAll(attachmentDo,
						// AttachmentDto.class));
						returnRequestHeaderDto.setListAttachementDo(attachmentDo);
					}

					List<Address> adressList = addressRepo.findByReturnReqNum(returnReqNum);
					if (adressList != null && !adressList.isEmpty()) {
						// returnRequestHeaderDto.setListAddressDto(ObjectMapperUtils.mapAll(adressList,AddresDto.class));
						returnRequestHeaderDto.setListAddressDo(adressList);
					}
					return ResponseEntity.status(HttpStatus.OK).header("message", "OK").body(returnRequestHeaderDto);
				} else {
					return ResponseEntity.status(HttpStatus.OK).header("message", "OK")
							.body(ReturnExchangeConstants.DATA_NOT_FOUND);
				}

			} else {
				return new ResponseEntity<>(
						ReturnExchangeConstants.INVALID_INPUT + "Please provide Return Request Number.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(ReturnExchangeConstants.EXCEPTION_POST_MSG + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	  @Override
		public ResponseEntity<Object>findAll(int pageNo)
		{
			
		
	    Pageable pageable=PageRequest.of(pageNo,10);
		List<ReturnRequestHeader>list=returnHeaderRepo.findAll();
		int start = (pageable.getPageNumber() - 1) * pageable.getPageSize();
		 int end = (start + pageable.getPageSize()) > list.size() ? list.size() : (pageable.getPageSize() * pageable.getPageNumber());
			Page<ReturnRequestHeader>page= new PageImpl<>(list.subList(start, end),pageable,list.size());
		 return ResponseEntity.ok().body(page);
			
		}






	@Override
	public ResponseEntity<Object> listAllReturn(ReturnFilterDto dto) {
		
		Pageable pageable=PageRequest.of(dto.getPageNo(),10);
		List<ReturnRequestHeader> list = new ArrayList<>();
		
		StringBuffer headerQuery = new StringBuffer("select r from ReturnRequestHeader r where flag is null");
		
		if(!ServicesUtils.isEmpty(dto.getReturnReqNumber()))
		{
			headerQuery.append(" and r.returnReqNum=:req");
		}
		
		if(!ServicesUtils.isEmpty(dto.getShipToParty()))
		{
			headerQuery.append(" and r.shipToParty=:STP");
		}
		
		if(!ServicesUtils.isEmpty(dto.getRequestedBy()))
		{
			headerQuery.append(" and r.requestedBy=:request");
		}
		
		if(!ServicesUtils.isEmpty(dto.getCustomerId()))
		{
			headerQuery.append(" and r.customerId=:customer");
		}
		
		if(!ServicesUtils.isEmpty(dto.getDistributionChannel()))
		{
			headerQuery.append(" and r.distributionChannel=:channel");
		}
		
		if(!ServicesUtils.isEmpty(dto.getDivision()))
		{
			headerQuery.append(" and r.division=:divison");
		}
		
		if(!ServicesUtils.isEmpty(dto.getSalesOrg()))
		{
			headerQuery.append(" and r.salesOrg=:salesOrg");
		}
		
		Query hq = entityManager.createQuery(headerQuery.toString());
		
		if(!ServicesUtils.isEmpty(dto.getReturnReqNumber()))
		{
			hq.setParameter("req", dto.getReturnReqNumber());
		}
		
		if(!ServicesUtils.isEmpty(dto.getShipToParty()))
		{
			hq.setParameter("STP", dto.getShipToParty());
		}
		
		if(!ServicesUtils.isEmpty(dto.getRequestedBy()))
		{
			hq.setParameter("request", dto.getRequestedBy());
		}
		if(!ServicesUtils.isEmpty(dto.getCustomerId()))
		{
			hq.setParameter("customer", dto.getCustomerId());
			
		}
		if(!ServicesUtils.isEmpty(dto.getDistributionChannel()))
		{
			hq.setParameter("channel", dto.getDistributionChannel());
			
		}
		if(!ServicesUtils.isEmpty(dto.getDivision()))
		{
			hq.setParameter("division", dto.getDivision());
			
		}
		if(!ServicesUtils.isEmpty(dto.getSalesOrg()))
		{
			hq.setParameter("salesOrg", dto.getSalesOrg());
		}
		list=hq.getResultList();
		
		
		int start = (pageable.getPageNumber() - 1) * pageable.getPageSize();
		 int end = (start + pageable.getPageSize()) > list.size() ? list.size() : (pageable.getPageSize() * pageable.getPageNumber());
			Page<ReturnRequestHeader>page= new PageImpl<>(list.subList(start, end),pageable,list.size());
		 return ResponseEntity.ok().body(page);
		
		 //Only By Return RequestNUmber
//		 if(!ServicesUtils.isEmpty(dto.getReturnReqNumber()))
//	         {
//		
//	                  Page<ReturnRequestHeader>list=returnHeaderRepo.findAll1(dto.getReturnReqNumber(),pageable);
//		              return ResponseEntity.ok().body(list);
//	        }
//		 
//		 
//		 
//		 
//		 
//		 //ALl
//		 if(!ServicesUtils.isEmpty(dto.getCustomerId())&&!ServicesUtils.isEmpty(dto.getDistributionChannel())&& !ServicesUtils.isEmpty(dto.getDivision())&&!ServicesUtils.isEmpty(dto.getReturnReason())&&!ServicesUtils.isEmpty(dto.getSalesOrg())&&!ServicesUtils.isEmpty(dto.getCreatedDate()))
//		 
//		 {
//			 
//			 Page<ReturnRequestHeader>list=returnHeaderRepo.findByAll(dto.getCustomerId(),dto.getDistributionChannel(),dto.getDivision(),dto.getReturnReason(),dto.getSalesOrg(),dto.getCreatedDate(),pageable);
//				return ResponseEntity.ok().body(list);
//			 
//		 }//without createdDate
//		 else if(!ServicesUtils.isEmpty(dto.getCustomerId())&&!ServicesUtils.isEmpty(dto.getDistributionChannel())&& !ServicesUtils.isEmpty(dto.getDivision())&&!ServicesUtils.isEmpty(dto.getReturnReason())&&!ServicesUtils.isEmpty(dto.getSalesOrg()))
//			 {
//			 
//			 Page<ReturnRequestHeader>list=returnHeaderRepo.findAll5C(dto.getCustomerId(),dto.getDistributionChannel(),dto.getDivision(),dto.getReturnReason(),dto.getSalesOrg(),pageable);
//				return ResponseEntity.ok().body(list);
//			 
//			 
//			 
//			 
//		   }//without customerId
//		 else if(!ServicesUtils.isEmpty(dto.getDistributionChannel())&& !ServicesUtils.isEmpty(dto.getDivision())&&!ServicesUtils.isEmpty(dto.getReturnReason())&&!ServicesUtils.isEmpty(dto.getSalesOrg())&&!ServicesUtils.isEmpty(dto.getCreatedDate()))
//		 {
//		 
//		 Page<ReturnRequestHeader>list=returnHeaderRepo.findAll5Cu(dto.getDistributionChannel(),dto.getDivision(),dto.getReturnReason(),dto.getSalesOrg(),dto.getCreatedDate(),pageable);
//			return ResponseEntity.ok().body(list);
//		 
//		 
//		 
//		 
//	   }//without Channel
//	   else if(!ServicesUtils.isEmpty(dto.getCustomerId())&& !ServicesUtils.isEmpty(dto.getDivision())&&!ServicesUtils.isEmpty(dto.getReturnReason())&&!ServicesUtils.isEmpty(dto.getSalesOrg())&&!ServicesUtils.isEmpty(dto.getCreatedDate()))
//			 
//		 {
//			 
//			 Page<ReturnRequestHeader>list=returnHeaderRepo.findAll5Cha(dto.getCustomerId(),dto.getDivision(),dto.getReturnReason(),dto.getSalesOrg(),dto.getCreatedDate(),pageable);
//				return ResponseEntity.ok().body(list);
//			 
//				
//				
//		 }
//		 //without divison
//	    else if(!ServicesUtils.isEmpty(dto.getCustomerId())&&!ServicesUtils.isEmpty(dto.getDistributionChannel())&&!ServicesUtils.isEmpty(dto.getReturnReason())&&!ServicesUtils.isEmpty(dto.getSalesOrg())&&!ServicesUtils.isEmpty(dto.getCreatedDate()))
//		 {
//			 Page<ReturnRequestHeader>list=returnHeaderRepo.findAll5Div(dto.getCustomerId(),dto.getDistributionChannel(),dto.getReturnReason(),dto.getSalesOrg(),dto.getCreatedDate(),pageable);
//				return ResponseEntity.ok().body(list);
//		 }//without returnReason
//	    else if(!ServicesUtils.isEmpty(dto.getCustomerId())&&!ServicesUtils.isEmpty(dto.getDistributionChannel())&& !ServicesUtils.isEmpty(dto.getDivision())&&!ServicesUtils.isEmpty(dto.getSalesOrg())&&!ServicesUtils.isEmpty(dto.getCreatedDate()))
//	    { 
//	    	Page<ReturnRequestHeader>list=returnHeaderRepo.findAll5R(dto.getCustomerId(),dto.getDistributionChannel(),dto.getDivision(),dto.getSalesOrg(),dto.getCreatedDate(),pageable);
//			return ResponseEntity.ok().body(list);
//		 
//	    }//withoutSales
//	    else if(!ServicesUtils.isEmpty(dto.getCustomerId())&&!ServicesUtils.isEmpty(dto.getDistributionChannel())&& !ServicesUtils.isEmpty(dto.getDivision())&&!ServicesUtils.isEmpty(dto.getReturnReason())&&!ServicesUtils.isEmpty(dto.getCreatedDate()))
//	    {
//	    	Page<ReturnRequestHeader>list=returnHeaderRepo.findAll5S(dto.getCustomerId(),dto.getDistributionChannel(),dto.getDivision(),dto.getReturnReason(),dto.getCreatedDate(),pageable);
//			return ResponseEntity.ok().body(list);
//	    }
//		 
//		 //Divison+Channel
//		 
//		if(!ServicesUtils.isEmpty(dto.getDivision())) {
//			
//		if(!ServicesUtils.isEmpty(dto.getDistributionChannel()))
//		{
//			Page<ReturnRequestHeader>list=returnHeaderRepo.findAll(dto.getDivision(),dto.getDistributionChannel(),pageable);
//			return ResponseEntity.ok().body(list);
//		}
//		else
//		{
//			Page<ReturnRequestHeader>list=returnHeaderRepo.findAllD(dto.getDivision(),pageable);
//			return ResponseEntity.ok().body(list);
//		}
//		}
//		
//		
//		
//		//Only SalesOrg
//		if(!ServicesUtils.isEmpty(dto.getSalesOrg()))
//		{
//			Page<ReturnRequestHeader>list=returnHeaderRepo.findAllS(dto.getSalesOrg(), pageable);
//			return ResponseEntity.ok().body(list);
//		}
//		
//		
//		//Only CustomerId
//		
//		if(!ServicesUtils.isEmpty(dto.getCustomerId()))
//		{
//			Page<ReturnRequestHeader>list=returnHeaderRepo.findAllC(dto.getCustomerId(), pageable);
//			return ResponseEntity.ok().body(list);
//		}
//		
//		//Only division
//		if(!ServicesUtils.isEmpty(dto.getDivision())){
//			Page<ReturnRequestHeader>list=returnHeaderRepo.findAllD(dto.getDivision(),pageable);
//			return ResponseEntity.ok().body(list);
//		}
//		
//		//Only CreatedDate
//		if(!ServicesUtils.isEmpty(dto.getCreatedDate()))
//		{
//			Page<ReturnRequestHeader>list=returnHeaderRepo.findAllD(dto.getCreatedDate(), pageable);
//			return ResponseEntity.ok().body(list);
//		}
//		
////		if(!ServicesUtils.isEmpty(dto.getReturnReason()))
////		{
////			Page<ReturnRequestHeader>list=returnHeaderRepo.findAllR(dto.getReturnReason(), pageable);
////			return ResponseEntity.ok().body(list);
////		}
//		if(!ServicesUtils.isEmpty(dto.getDistributionChannel()))
//		{
//			Page<ReturnRequestHeader>list=returnHeaderRepo.findAllCha(dto.getDistributionChannel(), pageable);
//			return ResponseEntity.ok().body(list);
//		}
//
//		
		
	}


		
}





	

	

	  
