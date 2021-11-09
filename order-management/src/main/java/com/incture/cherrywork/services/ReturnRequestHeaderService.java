
package com.incture.cherrywork.services;

import java.io.IOException;

import java.io.InputStream;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.incture.cherrywork.dto.new_workflow.SalesOrderItemStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderLevelStatusDto;
import com.incture.cherrywork.dto.new_workflow.SalesOrderTaskStatusDto;
import com.incture.cherrywork.dto.workflow.OrderToItems;
import com.incture.cherrywork.dtos.CreateReturnOrderMessage;
import com.incture.cherrywork.dtos.DecisionSetAndLevelDto;
import com.incture.cherrywork.dtos.DecisionSetAndLevelKeyDto;
import com.incture.cherrywork.dtos.DefDto;
import com.incture.cherrywork.dtos.EccResponseOutputDto;
import com.incture.cherrywork.dtos.ExchangeHeaderDto;
import com.incture.cherrywork.dtos.ExchangeHeaderFromOrderDto;
import com.incture.cherrywork.dtos.ExchangeItemDto;
import com.incture.cherrywork.dtos.ExchangeOrder;
import com.incture.cherrywork.dtos.FilterOnReturnHeaderDto;
import com.incture.cherrywork.dtos.ItemDataInReturnOrderDto;
import com.incture.cherrywork.dtos.MailTriggerDto;
import com.incture.cherrywork.dtos.ODataBatchItem;
import com.incture.cherrywork.dtos.ODataBatchPayload;
import com.incture.cherrywork.dtos.OdataBatchOnsubmitItem;
import com.incture.cherrywork.dtos.OrderConditionDto;
import com.incture.cherrywork.dtos.OrderHdrToOrderCondition;
import com.incture.cherrywork.dtos.OrderHdrToOrderItem;
import com.incture.cherrywork.dtos.OrderHdrToOrderPartner;
import com.incture.cherrywork.dtos.OrderHdrToOrderPartnerDto;

import com.incture.cherrywork.dtos.Response;
import com.incture.cherrywork.dtos.ReturnFilterDto;
import com.incture.cherrywork.dtos.ReturnItemDto;
import com.incture.cherrywork.dtos.ReturnOrder;
import com.incture.cherrywork.dtos.ReturnOrderDto;
import com.incture.cherrywork.dtos.ReturnOrderRequestPojo;
import com.incture.cherrywork.dtos.ReturnOrderResponsePojo;
import com.incture.cherrywork.dtos.ReturnRequestHeaderDto;
import com.incture.cherrywork.dtos.SalesDocHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.dtos.SmsSendingDto;
import com.incture.cherrywork.dtos.WorkflowTaskOutputDto;
import com.incture.cherrywork.entities.Address;
import com.incture.cherrywork.entities.Attachment;
import com.incture.cherrywork.entities.ExchangeHeader;
import com.incture.cherrywork.entities.ExchangeItem;
import com.incture.cherrywork.entities.ReturnItem;
import com.incture.cherrywork.entities.ReturnRequestHeader;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.entities.SalesOrderItem;
import com.incture.cherrywork.entities.new_workflow.SalesOrderLevelStatusDo;
import com.incture.cherrywork.exceptions.NoRecordFoundException;

import com.incture.cherrywork.pagination.Content;
import com.incture.cherrywork.pagination.Root;
import com.incture.cherrywork.repositories.IAddressRepository;
import com.incture.cherrywork.repositories.IAttachmentRepository;
import com.incture.cherrywork.repositories.IExchangeHeaderRepository;
import com.incture.cherrywork.repositories.IExchangeItemRepository;
import com.incture.cherrywork.repositories.INotificationConfigRepository;
import com.incture.cherrywork.repositories.IReturnRequestHeaderRepository;

import com.incture.cherrywork.repositories.IReturnRequestHeaderRepositoryNew;
import com.incture.cherrywork.repositories.IReturnRequestItemRepository;
import com.incture.cherrywork.repositories.ISalesOrderHeaderRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.repositories.ServicesUtils;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.tasksubmitdto.OdataBatchOnSubmitPayload;
import com.incture.cherrywork.util.AppErrorMsgConstants;
import com.incture.cherrywork.util.ComConstants;
import com.incture.cherrywork.util.DacMappingConstants;
import com.incture.cherrywork.util.DateUtils;
import com.incture.cherrywork.util.DestinationReaderUtil;
import com.incture.cherrywork.util.HelperClass;
import com.incture.cherrywork.util.MailAlertUtil;
import com.incture.cherrywork.util.NativeSqlResultMapping;
import com.incture.cherrywork.util.ODataBatchUtil;
import com.incture.cherrywork.util.ReturnExchangeConstants;
import com.incture.cherrywork.util.SequenceNumberGen;
import com.incture.cherrywork.util.ServicesUtil;
import com.incture.cherrywork.workflow.entities.SalesOrderTaskStatusDo;
import com.incture.cherrywork.workflow.repositories.ISalesOrderLevelStatusRepository;
import com.incture.cherrywork.workflow.services.TriggerImeDestinationService;

@Service
@SuppressWarnings("unchecked")
@Transactional
public class ReturnRequestHeaderService implements IReturnRequestHeaderService {

	@Autowired
	private IReturnRequestHeaderRepository returnHeaderRepo;

	@Autowired
	private EmailDefinitionService emailDefinitionService;
	
	@Autowired
	private IAttachmentRepository attachmentRepo;

	@Autowired
	private IAddressRepository addressRepo;

	@Autowired
	private IReturnRequestItemRepository returnItemRepo;

	@Autowired
	private ISalesOrderHeaderRepository salesOrderHeaderRepository;

	@Autowired
	private ISalesOrderLevelStatusRepository salesOrderLevelStatusRepository;

	@Autowired
	private IExchangeHeaderRepository exchangeHeaderRepo;
	
	@Autowired
	private INotificationConfigRepository notificationConfigRepository;
	
	@Autowired
	private NotificationDetailService notificationDetailService;


	// @Autowired
	// private IDataAccessService dataAccessService;

	private SequenceNumberGen sequenceNumberGen;

	@Autowired
	private IExchangeItemRepository exchangeItemRepo;

	@Autowired
	private SalesOrderOdataServices odataServices;
	@Autowired
	private IReturnRequestHeaderRepositoryNew repo;

	@Autowired
	private IUsersDetailService userDetailsServices;

	@Autowired
	private TriggerImeDestinationService triggerImeService;

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
				int deleteExchange = deleteExchangeByReturnReqNum(returnReqNum, exchangeReqNum);
			} else {
				// Setting the sequence to header.
				if (requestData.getExchange() != null) {
					// sequenceNumberGen = SequenceNumberGen.getInstance();
					// Session session = entityManager.unwrap(Session.class);
					// System.err.println("session : " + session);
					// String tempId = sequenceNumberGen
					// .getNextSeqNumber(ReturnExchangeConstants.RETURN_SEQ_EXCHANGE_PREFIX,
					// 8, session);
					// System.err.println("returnReqNum " + tempId);
					returnReqNum = "XCR-" + ServicesUtil.randomId();
					// returnReqNum = "XCR-" + ServicesUtil.randomId(); //
					// ServicesUtil.randomId();
					exchangeReqNum = returnReqNum + "/" + ServicesUtil.randomId();
					requestData.getExchange().setExchangeReqNum(exchangeReqNum);

				} else {
					// sequenceNumberGen = SequenceNumberGen.getInstance();
					// Session session = entityManager.unwrap(Session.class);
					// System.err.println("session : " + session);
					// String tempId =
					// sequenceNumberGen.getNextSeqNumber(ReturnExchangeConstants.RETURN_SEQ_PREFIX,
					// 8,
					// session);
					// System.err.println("returnReqNum " + tempId);
					returnReqNum = "CR-" + ServicesUtil.randomId();
					// returnReqNum = "CR-" + ServicesUtil.randomId();
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
			try {

				String notificationTypeId = "N07";
				String createdByDesc = null;
				String soldToParty = null;
				if(requestData.getReturns() != null){
					createdByDesc = requestData.getReturns().getCreatedByDesc();
					soldToParty = requestData.getReturns().getSoldToParty();
				}
				else{
//					String createdByDesc = requestData.getExchange().getCreatedByDesc();
					soldToParty = requestData.getExchange().getSoldToParty();
				}
				if (notificationConfigRepository.checkAlertForUser(createdByDesc,
						notificationTypeId)) {
					notificationDetailService.saveNotification(createdByDesc,
							soldToParty, null, "01", "01", notificationTypeId, "Start", false);
				}
				
				
				try{
					MailTriggerDto mDto=new MailTriggerDto();
					mDto.setApplication("COM");
					List<String>mlist=new ArrayList<>();
					HashMap<String,Object>m=new HashMap();
					mDto.setEntityName("COM_Returns");
					mDto.setProcess("Draft Return");
					
					m.put("Return_Id", requestData.getReturns().getReturnReqNum());
					m.put("Created_By",requestData.getReturns().getCreatedBy());
					m.put("Customer_Name", requestData.getReturns().getCustomerPo());
					m.put("Created_Date", requestData.getReturns().getCreatedAt());
						
					
					
					mlist.add(requestData.getReturns().getRequestorEmail());
					
					System.err.println(mDto.getToList());
					DefDto defDto=new DefDto();
					defDto.setApplication(mDto.getApplication());
					defDto.setEntityName(mDto.getEntityName());
					defDto.setProcess(mDto.getProcess());
					mDto.setEmailDefinitionId(emailDefinitionService.getDefId(defDto));
					mDto.setContentVariables(m);
					
					emailDefinitionService.triggerMail(mDto); 
					
					
				}catch (Exception e) {
					System.err.println("Exception while saving as draft: " + e.getMessage());
					e.printStackTrace();

			}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", e.getMessage()).body(e);
		}
		ResponseEntity<Object> res1 = ResponseEntity.status(HttpStatus.OK).header("message", "Success")
				.body(returnReqNum);
		Response responseObj = new Response(returnReqNum, HttpStatus.OK, "SUCCESS", ResponseStatus.CREATED);
		return ResponseEntity.status(HttpStatus.OK).header("message", "Draft saved successfully ").body(responseObj);
	}

	@Override
	public ResponseEntity<Object> createReturnRequest(ReturnOrderRequestPojo requestData) {

		System.err.println(">>>>> Start :: createReturnRequest");
		ResponseEntity<Object> res1 = null;
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
			if (requestData.getReturns() != null) {
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
					// logger.error("> savedReturnAddressr : " +
					// savedListAdress);
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
					// logger.error("> savedReturnAttachment : " +
					// listAttachment);
					System.err.println("> savedReturnAttachment :  " + listAttachment);
					// share point upload zip file

					// File sharePointfile =
					// convertByteArrayToFile(returnReqNum,
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
				if(requestData.getExchange() != null ){
					
				if (requestData.getExchange().getAttachment() != null
						&& !requestData.getExchange().getAttachment().isEmpty()) {
					List<Attachment> listSaveAttachement = requestData.getExchange().getAttachment();
					String returnRequestNum = returnReqNum;
					listSaveAttachement.stream().forEach(r -> {
						r.setReturnReqNum(returnRequestNum);
					});
					List<Attachment> listAttachment = attachmentRepo.saveAll(listSaveAttachement);
					// logger.error("> savedReturnAttachment : " +
					// listAttachment);
					System.err.println("> savedReturnAttachment :  " + listAttachment);
					// share point upload zip file

					// File sharePointfile =
					// convertByteArrayToFile(returnReqNum,
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

						// Need to work later, exceptions not handled like seq
						// in
						// headers
					});

					returnItemListDo = ObjectMapperUtils.mapAll(listOfReturnItem, ReturnItem.class);

					// Save return items to DB.
					List<ReturnItem> savedReturnItem = returnItemRepo.saveAll(returnItemListDo);
					System.err.println("> savedReturnItem :  " + savedReturnItem);
					// > End : Return Items
				}

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
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", "Error in saving data")
					.body(e.getMessage());
		}

		System.err.println("Returns and exchange details saved in HANA.");

		// Process to post data in ECC
		String response = null;

		try {

			System.err.println("create return order second thread >>>>>");
			// creating a new thread

			response = null;
			EccResponseOutputDto responseObject = null;

			SmsSendingDto smsSending = null;

			Map<String, List<ReturnItem>> splittedItemGroupMap = null;

			Map<String, List<OrderConditionDto>> splittedOrderConditionGroupMsp = null;
			returnItemListDo = null;

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
				System.err.println(e.getMessage());
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", "Technical Error")
						.body(response);
			}
			System.err.println(">odata response " + response);
			// Parsing the OData response
			try {
				responseObject = parseODataResponse(response);
			} catch (JsonProcessingException e) {
				//
				System.err.println(e.getMessage());
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.header("message", "Error while Parsing ECC response").body(responseObject.getMessage());

			} catch (IOException e) {
				//
				System.err.println(e.getMessage());
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.header("message", "Error while Parsing ECC response").body(responseObject.getMessage());
			}
			System.err.println("> responseObject : " + responseObject);
			// Updating the DB
			if (responseObject.getStatusCode().contains("201") && responseObject.getReturnResponsePojo() != null) {
				List<ReturnItem> returnItemList = new ArrayList<>();

				res1 = ResponseEntity.status(HttpStatus.OK).header("message", responseObject.getMessage())
						.body(returnReqNum);
				for (ReturnOrderResponsePojo returnResponsePojo : responseObject.getReturnResponsePojo()) {
					List<ODataBatchItem> itemList = returnResponsePojo.getOrderHdrToOrderItem().getResults();
					returnItemListDo = ObjectMapperUtils.mapAll(itemList, ReturnItem.class);
					List<ReturnItem> listReturnUpdate = new ArrayList<>(1);
					if (!returnResponsePojo.getName().contains("/")) {
						System.err.println("check contains XCR " + returnResponsePojo.getCustomerPo());
						System.err.println("check cintains CR " + returnResponsePojo.getCustomerPo());
						for (int i = 0; i < returnItemListDo.size(); i++) {
							// add salesDocument to existing data
							ReturnItem existingReturnItem = returnItemRepo.findByRefDocItemAndReturnReqNumAndRefDocNum(
									itemList.get(i).getRefDocIt(), returnResponsePojo.getName(),
									itemList.get(i).getRefDoc());
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
						message = "Request for Return of Item No." + requestData.getReturns().getReturnReqNum() + " ("
								+ requestData.getReturns().getSoldToParty() + " + "
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
				try{
					MailTriggerDto mDto=new MailTriggerDto();
					mDto.setApplication("COM");
					List<String>mlist=new ArrayList<>();
					HashMap<String,Object>m=new HashMap();
					mDto.setEntityName("COM_Returns");
					mDto.setProcess("Create Return");
					m.put("Return_Id", requestData.getReturns().getReturnReqNum());
					m.put("Created_By",requestData.getReturns().getCreatedBy());
					m.put("Customer_Name", requestData.getReturns().getCustomerPo());
					m.put("Created_Date", requestData.getReturns().getCreatedAt());
					
					
					mlist.add(requestData.getReturns().getRequestorEmail());
					
					System.err.println(mDto.getToList());
					DefDto defDto=new DefDto();
					defDto.setApplication(mDto.getApplication());
					defDto.setEntityName(mDto.getEntityName());
					defDto.setProcess(mDto.getProcess());
					mDto.setEmailDefinitionId(emailDefinitionService.getDefId(defDto));
					mDto.setContentVariables(m);
					emailDefinitionService.triggerMail(mDto); 
					
					
				}catch (Exception e) {
					System.err.println("Exception while saving as draft: " + e.getMessage());
					e.printStackTrace();

			}
				try {
					String notificationTypeId = "N07";
					String createdByDesc = null;
					String soldToParty = null;
					if(requestData.getReturns() != null){
						createdByDesc = requestData.getReturns().getCreatedByDesc();
						soldToParty = requestData.getReturns().getSoldToParty();
					}
					else{
//						String createdByDesc = requestData.getExchange().getCreatedByDesc();
						soldToParty = requestData.getExchange().getSoldToParty();
					}
					if (notificationConfigRepository.checkAlertForUser(createdByDesc,
							notificationTypeId))
						notificationDetailService.saveNotification(createdByDesc,
						soldToParty, requestData.getReturns().getReturnReqNum(), "All", "All",
								notificationTypeId, "Created", false);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				ReturnRequestHeader returnHeaderDo = returnHeaderRepo
						.findByReturnReqNum(requestData.getReturns().getReturnReqNum());
				returnHeaderDo.setMessage(responseObject.getMessage().toString());
				returnHeaderDo.setCreationStatus(false);
				returnHeaderDo.setDocVersion("ERROR");
				returnHeaderRepo.save(returnHeaderDo);
				if (requestData.getReturns().getReturnReqNum().contains("X")) {
					ExchangeHeader exchangeHeaderUpdateDo = exchangeHeaderRepo.findByReturnReqNumAndExchangeReqNum(
							requestData.getReturns().getReturnReqNum(), requestData.getExchange().getExchangeReqNum());
					exchangeHeaderUpdateDo.setMessage(responseObject.getMessage());
					exchangeHeaderUpdateDo.setCreationStatus(false);
					exchangeHeaderUpdateDo.setDocVersion("ERROR");
					exchangeHeaderRepo.save(exchangeHeaderUpdateDo);
				}
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", "Technical Error")
						.body(responseObject.getMessage());
			}

			// end of thread

		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("message", "Technical Error")
					.body(e.getMessage());

		}
		Response responseObj = new Response(returnReqNum, HttpStatus.OK, "SUCCESS", ResponseStatus.CREATED);
		return ResponseEntity.status(HttpStatus.OK).header("message", "ReturnOrder is being proccessed")
				.body(responseObj);

	}

	public int deleteExchangeByReturnReqNum(String returnreqNum, String exchangeReqNum) {

		int deleted = 0;

		System.err.println("[in deleteExchangeByReturnReqNum] returnreqNum " + returnreqNum + " and exchangeReqNum "
				+ exchangeReqNum);
		if (exchangeReqNum != null && !exchangeReqNum.isEmpty()) {
			int result1 = exchangeItemRepo.deleteByReturnRegNum(returnreqNum);
			System.err.println("result1 " + result1);
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

		// if (requestData.getCustomerPo() == null ||
		// requestData.getCustomerPo().isEmpty()) {
		// batchItem.setCustomerPo(returnReqNum);
		//
		// } else {
		// batchItem.setCustomerPo(requestData.getCustomerPo());
		// }
		// batchItem.setHdrDelBlk("01");

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
				item.setItemDelBlk("01");

				item.setRefDoc(data.getRefDocNum());
				if (data.getBatch() != null)
					item.setBatch(data.getBatch());
				else
					item.setBatch("");
				item.setSalesDocument("");
				item.setSalesUnit(data.getReturnUom());
				item.setRefDocIt(data.getRefDocItem());
				item.setReqQty("" + data.getReturnQty());
				// item.setUnitPrice("" + data.getUnitPriceCc());

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

		// if (exchangeData.getCustomerPo() == null ||
		// exchangeData.getCustomerPo().isEmpty()) {
		// batchItem.setCustomerPo(exchangeReqNum);
		//
		// } else {
		// batchItem.setCustomerPo(exchangeData.getCustomerPo());
		// }
		// batchItem.setHdrDelBlk("01");
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

				// item.setItemDelBlk("01");
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

		ReturnRequestHeaderDto returnRequestHeaderDto = new ReturnRequestHeaderDto();
		try {
			if (!HelperClass.checkString(returnReqNum)) {

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
						List<Attachment>attachment1=new ArrayList<Attachment>();
						List<Attachment>attachment2=new ArrayList<Attachment>();
						for(Attachment a:attachmentDo)
						{
						if(a.getType().equals("R"))
						{
							attachment1.add(a);
						}
						else
							attachment2.add(a);
						}
						returnRequestHeaderDto.setListAttachementDo(attachment1);
						returnRequestHeaderDto.getExchangeDto().setListAttachementDo(attachment2);
					}
					
						
					

					List<Address> adressList = addressRepo.findByReturnReqNum(returnReqNum);
					if (adressList != null && !adressList.isEmpty()) {
						// returnRequestHeaderDto.setListAddressDto(ObjectMapperUtils.mapAll(adressList,AddresDto.class));
						returnRequestHeaderDto.setListAddressDo(adressList);
					}

					// return
					// ResponseEntity.status(HttpStatus.OK).header("message",
					// "OK").body(returnRequestHeaderDto);
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).header("message", "Data Not Found")
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

		return ResponseEntity.status(HttpStatus.OK).header("message", "OK").body(returnRequestHeaderDto);
	}

	@Override
	public ResponseEntity<Object> listAllReturnReqHeaders(ReturnFilterDto dto) {
		try {
			Pageable pageable = PageRequest.of(dto.getPageNo() - 1, 10);
			Page<ReturnRequestHeader> p = repo.findAll(dto, pageable); // Talk
																		// with
																		// Sandeep
			System.err.println("hell1");
			System.err.println(p);
			return ResponseEntity.ok().body(p);

		}

		catch (Exception e) {
			return null;

		}
	}

	@SuppressWarnings("null")
	@Override
	public ResponseEntity<Object> getReturnOrderCreationMessage(String returnReqNum) {
		ReturnRequestHeader returnRequest = null;
		List<ReturnItem> returnItem = null;
		List<ExchangeItem> exchangeItem = null;
		List<String> uniquesapcreatedExchangeOrderNum = null;

		List<String> uniquesapcreatedReturnOrderNum = null;

		CreateReturnOrderMessage createReturnOrderMessage = null;

		returnRequest = returnHeaderRepo.findByReturnReqNum(returnReqNum);
		if (returnRequest == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("message", "This order has no return value.")
					.body(null);

		if (returnRequest.isCreationStatus()) {

			returnItem = returnItemRepo.findByReturnReqNum(returnReqNum);

			List<String> sapCreatedReturnOrderNum = new ArrayList<String>();
			returnItem.stream().forEach(s -> {
				if (!s.getSapReturnOrderNum().isEmpty()) {

					System.err.println("sapreturnOrderNum " + s.getSapReturnOrderNum());
					sapCreatedReturnOrderNum.add(s.getSapReturnOrderNum());
				}

			});

			System.err.println("check" + returnReqNum.contains("XCR"));
			if (returnReqNum.contains("X")) {
				exchangeItem = exchangeItemRepo.findByReturnRegNum(returnReqNum);
				List<String> sapCreatedExchangeOrderNum = new ArrayList<String>();
				exchangeItem.stream().forEach(s -> {
					if (!s.getSapSalesOrderNum().isEmpty() && s.getSapSalesOrderNum() != null) {

						System.err.println("sapreturnOrderNum " + s.getSapSalesOrderNum());
						sapCreatedExchangeOrderNum.add(s.getSapSalesOrderNum());
					}
				});
				if (!sapCreatedExchangeOrderNum.isEmpty() && sapCreatedExchangeOrderNum != null)
					uniquesapcreatedExchangeOrderNum = new ArrayList<String>();
				uniquesapcreatedExchangeOrderNum = sapCreatedExchangeOrderNum.stream().distinct()
						.collect(Collectors.toList());
			}

			if (!sapCreatedReturnOrderNum.isEmpty() && sapCreatedReturnOrderNum != null) {
				uniquesapcreatedReturnOrderNum = new ArrayList<String>();
				uniquesapcreatedReturnOrderNum = sapCreatedReturnOrderNum.stream().distinct()
						.collect(Collectors.toList());

				createReturnOrderMessage = new CreateReturnOrderMessage();

				createReturnOrderMessage.setReturnReqNum(returnReqNum);

				createReturnOrderMessage.setMessage(returnRequest.getMessage());

				createReturnOrderMessage.setSapReturnOrderNum(uniquesapcreatedReturnOrderNum);
				if (uniquesapcreatedExchangeOrderNum != null && !uniquesapcreatedExchangeOrderNum.isEmpty()) {
					createReturnOrderMessage.setSapExchangeOrderNum(uniquesapcreatedExchangeOrderNum);
				}
			}

		} else {
			createReturnOrderMessage = new CreateReturnOrderMessage();

			createReturnOrderMessage.setReturnReqNum(returnReqNum);
			createReturnOrderMessage.setMessage(returnRequest.getMessage());
			// createReturnOrderMessage.setReturnErrorMessage(returnRequest.getMessage());
			// createReturnOrderMessage.setExchangeErrorMessage(exchangeRequest.getMessage());
			System.err.println("createReturnOrderMessage " + createReturnOrderMessage.toString());

			return ResponseEntity.status(HttpStatus.OK).header("message", "Not Created Yet")
					.body(createReturnOrderMessage);
		}
		return ResponseEntity.status(HttpStatus.OK).header("message", "OK").body(createReturnOrderMessage);

	}

	@Override
	public ResponseEntity<Object> listAllReturn(ReturnFilterDto dto) {

		Pageable pageable = PageRequest.of(dto.getPageNo(), 10);
		List<String> l1 = new ArrayList<>();
		String s = null;
		if (!ServicesUtils.isEmpty(dto.getShipToParty())) {
			s = dto.getShipToParty().substring(8);
		}

		System.err.println(s);

		if (!ServicesUtils.isEmpty(dto.getCustomerId())) {

			for (int i = 0; i < dto.getCustomerId().size(); i++) {
				l1.add(dto.getCustomerId().get(i).substring(8));
			}
			
			
		}

		List<ReturnRequestHeader> list = new ArrayList<>();
		String STP = ServicesUtil.listToString(l1);
		System.err.println(STP);
		StringBuffer headerQuery = new StringBuffer("select r from ReturnRequestHeader r where Flag is null");

		if (!ServicesUtils.isEmpty(dto.getReturnReqNumber())) {
			headerQuery.append(" and r.returnReqNum=:req");
		}

		if (!ServicesUtils.isEmpty(dto.getCreatedDateFrom()) && !ServicesUtils.isEmpty(dto.getCreatedDateTo())) {
			headerQuery.append(" and r.createdAt between :stDate and :endDate");
		}

		if (!ServicesUtils.isEmpty(dto.getOrderReason())) {
			headerQuery.append(" and r.orderReason=:reason");
		}

		if (!ServicesUtils.isEmpty(dto.getDocVersion())) {
			headerQuery.append(" and r.docVersion=:version");
		}

		if (!ServicesUtils.isEmpty(dto.getShipToParty())) {
			headerQuery.append(" and r.shipToParty=:STP");
		}

		if (!ServicesUtils.isEmpty(dto.getRequestedBy())) {
			headerQuery.append(" and r.requestedBy=:request");
		}

		if (!ServicesUtils.isEmpty(dto.getCustomerId())) {
			headerQuery.append(" and r.soldToParty in (" + STP + ") ");
		}

		if (!ServicesUtils.isEmpty(dto.getDistributionChannel())) {
			headerQuery.append(" and r.distributionChannel=:channel");
		}

		if (!ServicesUtils.isEmpty(dto.getDivision())) {
			headerQuery.append(" and r.division=:division");
		}

		if (!ServicesUtils.isEmpty(dto.getSalesOrg())) {
			headerQuery.append(" and r.salesOrg=:salesOrg");
		}
		headerQuery.append(" order by createdAt desc");

		Query hq = entityManager.createQuery(headerQuery.toString());

		if (!ServicesUtils.isEmpty(dto.getReturnReqNumber())) {
			hq.setParameter("req", dto.getReturnReqNumber());
		}

		if (!ServicesUtils.isEmpty(dto.getOrderReason())) {
			hq.setParameter("reason", dto.getOrderReason());
		}

		if (!ServicesUtils.isEmpty(dto.getDocVersion())) {
			hq.setParameter("version", dto.getDocVersion());
		}

		if (!ServicesUtils.isEmpty(dto.getCreatedDateFrom())) {
			hq.setParameter("stDate", dto.getCreatedDateFrom());

		}
		if (!ServicesUtils.isEmpty(dto.getCreatedDateTo())) {
			hq.setParameter("endDate", dto.getCreatedDateTo());
		}

		if (!ServicesUtils.isEmpty(dto.getShipToParty())) {
			hq.setParameter("STP", s);
		}

		if (!ServicesUtils.isEmpty(dto.getRequestedBy())) {
			hq.setParameter("request", dto.getRequestedBy());
		}

		if (!ServicesUtils.isEmpty(dto.getDistributionChannel())) {
			hq.setParameter("channel", dto.getDistributionChannel());

		}
		if (!ServicesUtils.isEmpty(dto.getDivision())) {
			hq.setParameter("division", dto.getDivision());

		}
		if (!ServicesUtils.isEmpty(dto.getSalesOrg())) {
			hq.setParameter("salesOrg", dto.getSalesOrg());
		}
		list = hq.getResultList();

		if (list.size() > 0 && list.size() < 10) {

			Root root = new Root();

			root.setContent(ObjectMapperUtils.mapAll(list, Content.class));
			root.setFirst(true);
			root.setLast(true);
			root.setTotalElements(list.size());
			root.setNumberOfElements(list.size());
			root.setTotalPages(1);
			root.setEmpty(false);
			root.setNumber(1);
			root.setSize(10);
			return ResponseEntity.ok().body(root);
		} else {

			int start = (pageable.getPageNumber() - 1) * pageable.getPageSize();
			int end = (start + pageable.getPageSize()) > list.size() ? list.size()
					: (pageable.getPageSize() * pageable.getPageNumber());
			Page<ReturnRequestHeader> page = new PageImpl<>(list.subList(start, end), pageable, list.size());

			return ResponseEntity.ok().body(page);
		}
	}

	@Override
	public ResponseEntity<Object> findAll(int pageNo) {

		Pageable pageable = PageRequest.of(pageNo - 1, 10);
		Page<ReturnRequestHeader> list = returnHeaderRepo.findAll(pageable);
		return ResponseEntity.ok().body(list);

	}

	@Override
	public ResponseEntity<Object> fetchReturnHeaderListForUserWhichHasTasksForNewDac(FilterOnReturnHeaderDto filterDto,
			Integer indexNum, Integer count) {

		try {
			if (indexNum != null && count != null && indexNum != 0 && count != 0) {

				if (!HelperClass.checkString(filterDto.getLoginInUserId())
						&& !HelperClass.checkString(filterDto.getProjectCode())) {

					// Arrays.stream(s.split("@")).collect(Collectors.toList())
					ResponseEntity<Object> responseFromDac = userDetailsServices
							.findAllRightsForUserInDomain(filterDto.getLoginInUserId(), filterDto.getProjectCode());

					// logger.error("responseFromDac : " + responseFromDac);
					System.err.println("responseFromDac : " + responseFromDac);

					if (responseFromDac.getStatusCode().value() == HttpStatus.OK.value()) {

						Map<String, String> mapOfAttributeValues = (Map<String, String>) responseFromDac.getBody();

						Map<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
						map.putAll(mapOfAttributeValues);
						map.values().removeIf(Objects::isNull);

						// logger.error("mapOfAttributeValues : " + map);
						System.err.println("mapOfAttributeValues : " + map);

						// return true when user have all rights
						// return false when any some of the rights
						Boolean flagForAllRights = checkForUserAllRightsNewDac(map);

						// return true when all fields are empty
						// return false when any one of the field have some data
						Boolean flagForEmptyInput = checkForEmptyInputData(filterDto);

						// logger.error("flagForAllRights : " +
						// flagForAllRights);
						System.err.println("flagForAllRights : " + flagForAllRights);
						// logger.error("flagForEmptyInput : " +
						// flagForEmptyInput);
						System.err.println("flagForEmptyInput : " + flagForEmptyInput);

						ResponseEntity<Object> responseFromSapApi = HelperClass
								.fetchUserTasksForApprovalWorkflowOfReturnsForNewDac(filterDto.getLoginInUserId(),
										filterDto, map, flagForAllRights, flagForEmptyInput);

						if (responseFromSapApi.getStatusCodeValue() == HttpStatus.OK.value()) {

							List<WorkflowTaskOutputDto> listOfWorkflowTasks = (List<WorkflowTaskOutputDto>) responseFromSapApi
									.getBody();

							// logger.error("listOfWorkflowTasks : " +
							// listOfWorkflowTasks);
							System.err.println("listOfWorkflowTasks : " + listOfWorkflowTasks);

							Map<String, String> mapOfDistinctCustomerPoWithRoType = listOfWorkflowTasks.stream()
									.collect(Collectors.toMap(task -> task.getCustomerPo().split("/")[0],
											WorkflowTaskOutputDto::getRoType, (oldValue, newValue) -> newValue));

							// logger.error("mapOfDistinctCustomerPoWithRoType :
							// " + mapOfDistinctCustomerPoWithRoType);
							System.err.println(
									"mapOfDistinctCustomerPoWithRoType : " + mapOfDistinctCustomerPoWithRoType);

							List<SalesDocHeaderDto> returnOrderList1 = fetchSalesDocHeaderDtoListFromCustomerPoNumbersForNewDac(
									mapOfDistinctCustomerPoWithRoType.keySet().stream().collect(Collectors.toList()),
									map, flagForAllRights, flagForEmptyInput);
							List<SalesDocHeaderDto> returnOrderList = new ArrayList<SalesDocHeaderDto>();
							List<String> customerPoList = new ArrayList<String>();
							for (SalesDocHeaderDto dto : returnOrderList1) {
								if (!customerPoList.contains(dto.getCustomerPo())) {
									String createdBy = salesOrderHeaderRepository
											.findBySalesHeaderId(dto.getSalesOrderNum(), "OR");
									if (createdBy != null)
										dto.setCreatedBy(createdBy);
									else {
										String returnReqNum = returnHeaderRepo.findReturnReqNum(dto.getSalesOrderNum());
										if (returnReqNum != null)
											createdBy = returnHeaderRepo.findCreatedBy(returnReqNum);
										if (createdBy != null)
											dto.setCreatedBy(createdBy);
									}
									returnOrderList.add(dto);
									customerPoList.add(dto.getCustomerPo());
								}

							}

							if (!returnOrderList.isEmpty()) {
								int min = (indexNum * count) - count;
								int max = (indexNum * count);

								if (min < returnOrderList.size()) {
									if (max > returnOrderList.size()) {
										max = returnOrderList.size();
									}

									return new ResponseEntity<>(
											returnOrderList.stream().skip(min).limit(max).collect(Collectors.toList()),
											HttpStatus.OK);

								} else {
									return new ResponseEntity<>(
											AppErrorMsgConstants.DATA_NOT_FOUND
													+ " Select different Index number or Change the count number.",
											HttpStatus.OK);

								}
							} else {
								return new ResponseEntity<>("No tasks are available with Customer Po for user : "
										+ filterDto.getLoginInUserId(), HttpStatus.OK);

							}
						} else {
							return responseFromSapApi;
						}
					} else {
						return responseFromDac;
					}

				} else {

					return new ResponseEntity<>(
							AppErrorMsgConstants.INVALID_INPUT + "Please provide login in user id and project code",
							HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<>(AppErrorMsgConstants.INVALID_INPUT + " Provide valid index num and count.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(AppErrorMsgConstants.EXCEPTION_POST_MSG + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private List<SalesDocHeaderDto> fetchSalesDocHeaderDtoListFromCustomerPoNumbersForNewDac(
			List<String> customerPoListPaginated, Map<String, String> map, Boolean flagForAllRights,
			Boolean flagForEmptyInput) {

		List<SalesDocHeaderDto> listOfReturnOrders = null;

		if (!flagForAllRights && flagForEmptyInput && map.containsKey(DacMappingConstants.SOLD_TO_PARTY)
				&& !map.get(DacMappingConstants.SOLD_TO_PARTY).contains("*")) {
			System.err.println(
					"[return header service][fetchSalesDocHeaderDtoListFromCustomerPoNumbersForNewDac] in if part;");

			listOfReturnOrders = returnHeaderRepo.fetchSalesOrdersFromCustomerPoList(customerPoListPaginated).stream()
					.distinct().collect(Collectors.toList());

			// logger.error("In some customer rights : " + listOfReturnOrders);
			System.err.println("In some customer rights : " + listOfReturnOrders);

			List<String> soldToPartyValues = Arrays.stream(map.get(DacMappingConstants.SOLD_TO_PARTY).split("@"))
					.collect(Collectors.toList());

			Iterator<SalesDocHeaderDto> iterator = listOfReturnOrders.iterator();

			while (iterator.hasNext()) {
				SalesDocHeaderDto salesDocHeaderDto = iterator.next();
				if (soldToPartyValues.stream()
						.anyMatch(soldToParty -> soldToParty.equals(salesDocHeaderDto.getSoldToParty()))) {
					iterator.remove();
				} else {
					Double amount = returnHeaderRepo.findTotalAmountOnCustomerPo(salesDocHeaderDto.getReturnReqNum());
					System.err.println("amount: " + amount);
					salesDocHeaderDto.setTotalRoAmount(amount.toString());
				}
			}

			// logger.error("In some customer rights : " + listOfReturnOrders);
			System.err.println("In some customer rights : " + listOfReturnOrders);

		} else {
			System.err.println(
					"[return header service][fetchSalesDocHeaderDtoListFromCustomerPoNumbersForNewDac] else part");
			try {
				listOfReturnOrders = returnHeaderRepo.fetchSalesOrdersFromCustomerPoList(customerPoListPaginated)
						.stream().distinct().peek(returnOrder -> {
							returnOrder.setReturnReqNum(returnOrder.getCustomerPo());
							returnOrder.setTotalRoAmount(returnHeaderRepo
									.findTotalAmountOnCustomerPo(returnOrder.getSalesOrderNum()).toString());
						}).collect(Collectors.toList());
			} catch (Exception e) {
				System.err.println("Exception occured while procerssing listOfReturnOrders: " + e.getMessage());
				e.printStackTrace();
			}

			// logger.error("Default : " + listOfReturnOrders);
			System.err.println("Default : " + listOfReturnOrders);

		}

		// logger.error("Before sorting : " + listOfReturnOrders);
		System.err.println("Before sorting : " + listOfReturnOrders);
		//
		// listOfReturnOrders.sort(Comparator.comparing(SalesDocHeaderDto::getCreatedOn,
		// Comparator.nullsLast((d1, d2) -> d2.compareTo(d1))));
		//
		// // logger.error("After sorting : " + listOfReturnOrders);
		// System.err.println("After sorting : " + listOfReturnOrders);

		return listOfReturnOrders;
	}

	private boolean checkForUserAllRightsNewDac(Map<String, String> mapOfAttributeValues) {

		Boolean flagForAllRights = false;

		if (((mapOfAttributeValues.containsKey(DacMappingConstants.SALES_ORG))
				? mapOfAttributeValues.get(DacMappingConstants.SALES_ORG).contains("*") : false)
				&& ((mapOfAttributeValues.containsKey(DacMappingConstants.DISTRIBUTION_CHANNEL))
						? mapOfAttributeValues.get(DacMappingConstants.DISTRIBUTION_CHANNEL).contains("*") : false)
				&& ((mapOfAttributeValues.containsKey(DacMappingConstants.DIVISION))
						? mapOfAttributeValues.get(DacMappingConstants.DIVISION).contains("*") : false)
				&& ((mapOfAttributeValues.containsKey(DacMappingConstants.ORDER_TYPE))
						? mapOfAttributeValues.get(DacMappingConstants.ORDER_TYPE).contains("*") : false)
				&& ((mapOfAttributeValues.containsKey(DacMappingConstants.SOLD_TO_PARTY))
						? mapOfAttributeValues.get(DacMappingConstants.SOLD_TO_PARTY).contains("*") : false)) {
			flagForAllRights = true;
		}
		return flagForAllRights;
	}

	private boolean checkForEmptyInputData(FilterOnReturnHeaderDto filterDetailDto) {
		Boolean checkForEmptyInput = false;
		if ((filterDetailDto.getSalesOrgList() == null || filterDetailDto.getSalesOrgList().isEmpty())
				&& (filterDetailDto.getDistributionChannelList() == null
						|| filterDetailDto.getDistributionChannelList().isEmpty())
				&& (filterDetailDto.getDivisionList() == null || filterDetailDto.getDivisionList().isEmpty())
				&& (filterDetailDto.getOrderTypeList() == null || filterDetailDto.getOrderTypeList().isEmpty())
				&& (filterDetailDto.getSoldToPartyList() == null || filterDetailDto.getSoldToPartyList().isEmpty())
				&& (filterDetailDto.getReturnReasonList() == null || filterDetailDto.getReturnReasonList().isEmpty())
				&& (filterDetailDto.getShipToPartyList() == null || filterDetailDto.getShipToPartyList().isEmpty())
				&& (filterDetailDto.getHeaderDeliveryBlockList() == null
						|| filterDetailDto.getHeaderDeliveryBlockList().isEmpty())
				&& HelperClass.checkString(filterDetailDto.getReturnType())
				&& HelperClass.checkString(filterDetailDto.getCustomerPo())
				&& HelperClass.checkString(filterDetailDto.getOrderNumber())) {
			checkForEmptyInput = true;
		}
		return checkForEmptyInput;
	}

	@Override
	public ResponseEntity<Object> fetchItemDataInReturnOrderHavingTaskDtoListForNewDac(String userId, String customerPo,
			String projectCode) {
		try {
			if (!HelperClass.checkString(userId) && !HelperClass.checkString(customerPo)
					&& !HelperClass.checkString(projectCode)) {

				// Arrays.stream(s.split("@")).collect(Collectors.toList())
				ResponseEntity<Object> responseFromDac = userDetailsServices.findAllRightsForUserInDomain(userId,
						projectCode);

				// logger.error("responseFromDac : " + responseFromDac);
				System.err.println("responseFromDac : " + responseFromDac);

				if (responseFromDac.getStatusCode().value() == HttpStatus.OK.value()) {

					Map<String, String> mapOfAttributeValues = (Map<String, String>) responseFromDac.getBody();

					Map<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
					map.putAll(mapOfAttributeValues);
					map.values().removeIf(Objects::isNull);

					// logger.error("mapOfAttributeValues : " + map);
					System.err.println("mapOfAttributeValues : " + map);

					// return true when user have all rights
					// return false when any some of the rights
					Boolean flagForAllRightsItemLevel = checkForUserAllRightsItemLevelForNewDac(map);
					// logger.error("flagForAllRightsItemLevel : " +
					// flagForAllRightsItemLevel);
					System.err.println("flagForAllRightsItemLevel : " + flagForAllRightsItemLevel);

					ReturnOrderDto returnOrderDto = new ReturnOrderDto();

					Map<String, List<String>> mapOfCustomerPoWithSalesOrderNumList = returnHeaderRepo
							.fetchSalesOrdersFromCustomerPo(customerPo).stream()
							.collect(Collectors.groupingBy(SalesOrderDto::getCustomerPo,
									Collectors.mapping(SalesOrderDto::getSalesOrderNum, Collectors.toList())));

					// logger.error("mapOfCustomerPoWithSalesOrderNumList : " +
					// mapOfCustomerPoWithSalesOrderNumList);
					System.err
							.println("mapOfCustomerPoWithSalesOrderNumList : " + mapOfCustomerPoWithSalesOrderNumList);

					if (!mapOfCustomerPoWithSalesOrderNumList.isEmpty()) {

						mapOfCustomerPoWithSalesOrderNumList.forEach((customerPoFromDb, salesOrderNumList) -> {

							if (!customerPoFromDb.contains("CE")) {

								SalesDocHeaderDto returnOrderDataFromDb = returnHeaderRepo
										.fetchSalesOrdersFromCustomerPoList(
												Stream.of(customerPoFromDb).collect(Collectors.toList()))
										.stream().distinct().collect(Collectors.toList()).get(0);
								// logger.error("returnOrderDataFromDb : " +
								// returnOrderDataFromDb);
								System.err.println("returnOrderDataFromDb : " + returnOrderDataFromDb);
								returnOrderDto.setReturnReqNum(customerPoFromDb);
								returnOrderDto.setSalesOrderNumList(salesOrderNumList);
								returnOrderDto.setOrderType(returnOrderDataFromDb.getOrderType());
								returnOrderDto.setReturnRemark(returnOrderDataFromDb.getRequestRemark());
								returnOrderDto.setOrderReasonText(returnOrderDataFromDb.getReturnReasonText());
								returnOrderDto.setOrderTypeText(returnOrderDataFromDb.getOrderTypeText());
								returnOrderDto.setDistributionChannel(returnOrderDataFromDb.getDistributionChannel());
								returnOrderDto.setDivision(returnOrderDataFromDb.getDivision());
								returnOrderDto.setSalesOrg(returnOrderDataFromDb.getSalesOrg());
								returnOrderDto.setShipToParty(returnOrderDataFromDb.getShipToParty());
								returnOrderDto.setShipToPartyText(returnOrderDataFromDb.getShipToPartyText());
								returnOrderDto.setSoldToParty(returnOrderDataFromDb.getSoldToParty());
								returnOrderDto.setSoldToPartyText(returnOrderDataFromDb.getSoldToPartyText());
								returnOrderDto.setDocCurrency(returnOrderDataFromDb.getDocCurrency());
								returnOrderDto.setHeaderDeliveryBlock(returnOrderDataFromDb.getDeliveryBlockCode());
								returnOrderDto.setHeaderDeliveryBlockText(returnOrderDataFromDb.getDeliveryBlockText());

								List<ItemDataInReturnOrderDto> list_item = returnHeaderRepo
										.fetchItemDataInReturnOrderHavingTaskDtoListForNewDac(userId, salesOrderNumList,
												map, flagForAllRightsItemLevel);
								// int index = 0;
								// for (ItemDataInReturnOrderDto item :
								// list_item) {
								// if (ServicesUtils.isPresent(index,
								// item.getOrderNum(), item.getMaterialNum(),
								// list_item)) {
								// list_item.remove(item);
								// System.err.println("duplicate removed");
								// }
								//
								// index++;
								// }
								returnOrderDto.setReturnItemsList((list_item.stream().peek(item -> {
									item.setReturnReqNum(customerPoFromDb);
									item.setItemStagingStatus(ComConstants.MAP_TO_PRINT_ITEM_STATUS
											.get(item.getVisiblity() + item.getItemStatus()));
								}).collect(Collectors.toList())));
							} else {

								ExchangeHeaderFromOrderDto exchangeHeaderDto = new ExchangeHeaderFromOrderDto();

								SalesDocHeaderDto returnOrderDataFromDb = returnHeaderRepo
										.fetchSalesOrdersFromCustomerPoList(
												Stream.of(customerPoFromDb).collect(Collectors.toList()))
										.stream().distinct().collect(Collectors.toList()).get(0);
								// logger.error("exchangeOrderDataFromDb : " +
								// returnOrderDataFromDb);
								System.err.println("exchangeOrderDataFromDb : " + returnOrderDataFromDb);

								exchangeHeaderDto.setExchangeHeaderNum(customerPoFromDb);
								exchangeHeaderDto.setSalesOrderNum(salesOrderNumList.get(0));
								exchangeHeaderDto.setOrderType(returnOrderDataFromDb.getOrderType());
								exchangeHeaderDto.setOrderTypeText(returnOrderDataFromDb.getOrderTypeText());
								exchangeHeaderDto.setReturnRemark(returnOrderDataFromDb.getRequestRemark());
								exchangeHeaderDto.setOrderReasonText(returnOrderDataFromDb.getReturnReasonText());
								exchangeHeaderDto
										.setDistributionChannel(returnOrderDataFromDb.getDistributionChannel());
								exchangeHeaderDto
										.setHeaderDeliveryBlockText(returnOrderDataFromDb.getHeaderDeliveryBlockText());

								exchangeHeaderDto.setDivision(returnOrderDataFromDb.getDivision());
								exchangeHeaderDto.setSalesOrg(returnOrderDataFromDb.getSalesOrg());
								exchangeHeaderDto.setShipToParty(returnOrderDataFromDb.getShipToParty());
								exchangeHeaderDto.setShipToPartyText(returnOrderDataFromDb.getShipToPartyText());
								exchangeHeaderDto.setSoldToParty(returnOrderDataFromDb.getSoldToParty());
								exchangeHeaderDto.setSoldToPartyText(returnOrderDataFromDb.getSoldToPartyText());
								exchangeHeaderDto.setDocCurrency(returnOrderDataFromDb.getDocCurrency());
								exchangeHeaderDto
										.setHeaderDeliveryBlock(returnOrderDataFromDb.getHeaderDeliveryBlockCode());
								exchangeHeaderDto
										.setHeaderDeliveryBlockText(returnOrderDataFromDb.getHeaderDeliveryBlockText());

								exchangeHeaderDto
										.setExchangeItemsList(new CopyOnWriteArrayList<>(returnHeaderRepo
												.fetchItemDataInReturnOrderHavingTaskDtoListForNewDac(userId,
														salesOrderNumList, map, flagForAllRightsItemLevel)
												.stream().peek(item -> {
													item.setExchReqNum(customerPoFromDb);
													item.setReturnReqNum(customerPo);
													item.setItemStagingStatus(ComConstants.MAP_TO_PRINT_ITEM_STATUS
															.get(item.getVisiblity() + item.getItemStatus()));
												}).collect(Collectors.toList())));

								returnOrderDto.setExchangeHeaderDto(exchangeHeaderDto);
							}

						});
						System.err.println("returnOrderDto : " + returnOrderDto);
						return new ResponseEntity<>(returnOrderDto, HttpStatus.OK);
					} else {
						return new ResponseEntity<>(AppErrorMsgConstants.DATA_NOT_FOUND, HttpStatus.NO_CONTENT);
					}
				} else {
					return responseFromDac;
				}
			} else {
				return new ResponseEntity<>(
						AppErrorMsgConstants.INVALID_INPUT + " Provide customer po num, project code and user id.",
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(AppErrorMsgConstants.EXCEPTION_POST_MSG + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private boolean checkForUserAllRightsItemLevelForNewDac(Map<String, String> mapOfAttributeValues) {

		Boolean flagForAllRights = false;

		if (((mapOfAttributeValues.containsKey(DacMappingConstants.MATERIAL_GROUP))
				? mapOfAttributeValues.get(DacMappingConstants.MATERIAL_GROUP).contains("*") : false)
				&& ((mapOfAttributeValues.containsKey(DacMappingConstants.MATERIAL_GROUP_4))
						? mapOfAttributeValues.get(DacMappingConstants.MATERIAL_GROUP_4).contains("*") : false)
				&& ((mapOfAttributeValues.containsKey(DacMappingConstants.MATERIAL))
						? mapOfAttributeValues.get(DacMappingConstants.MATERIAL).contains("*") : false)) {
			flagForAllRights = true;
		}
		return flagForAllRights;
	}

	@Override
	public ResponseEntity<Object> returnApprovalOnSubmit(ReturnOrderDto returnOrderDto) {

		try {

			// logger.error("Current item list : \n" +
			// returnOrderDto.getReturnItemsList());
			System.err.println("Current item list : \n" + returnOrderDto.getReturnItemsList());

			// Unique list of decision set, level id and approver type
			Map<DecisionSetAndLevelKeyDto, DecisionSetAndLevelDto> uniqueDecisionSetAndLevelIdMap = returnOrderDto
					.getReturnItemsList().stream()
					.map(item -> new DecisionSetAndLevelDto(item.getDecisionSetId(), item.getLevel(),
							item.getApproverType()))
					.collect(Collectors.toMap(DecisionSetAndLevelDto::getKey, ds -> ds,
							(oldValue, newValue) -> oldValue, LinkedHashMap::new));

			// logger.error("uniqueDecisionSetAndLevelIdMap : " +
			// uniqueDecisionSetAndLevelIdMap);
			System.err.println("uniqueDecisionSetAndLevelIdMap : " + uniqueDecisionSetAndLevelIdMap);

			// Map of current task id and fetching task status data
			ConcurrentHashMap<String, SalesOrderTaskStatusDto> mapOfTaskIdWithTaskStatusData = new ConcurrentHashMap<>();

			// Unique list of level serial id
			CopyOnWriteArrayList<String> uniqueLevelSerialIdList = new CopyOnWriteArrayList<>();

			// Update the ECC
			// Update Item Status for Approve case only
			// (Restriction UI will send only which user has taken onsubmit
			// action only A&R otherwise item data we will not add)
			for (Iterator<ItemDataInReturnOrderDto> iterator = returnOrderDto.getReturnItemsList().iterator(); iterator
					.hasNext();) {

				ItemDataInReturnOrderDto item = iterator.next();

				// Fetch the status of item in order to send the data to ECC
				Integer currentItemStatus = returnHeaderRepo.getItemStatus(item.getItemSerialId());
				// logger.error("currentItemStatus of item : " +
				// item.getItemSerialId() + " = " + currentItemStatus);
				System.err.println("currentItemStatus of item : " + item.getItemSerialId() + " = " + currentItemStatus);

				if (currentItemStatus.equals(ComConstants.BLOCKED)) {

					Integer resultFromUpdatingItemStatus = 0;
					if (item.getAcceptOrReject().equalsIgnoreCase("A")) {
						resultFromUpdatingItemStatus = returnHeaderRepo.updateItemStatusAndVisibility(
								item.getItemSerialId(), ComConstants.ITEM_APPROVE, ComConstants.VISIBLITY_INACTIVE);
					} else if (item.getAcceptOrReject().equalsIgnoreCase("R")) {
						resultFromUpdatingItemStatus = returnHeaderRepo.updateItemStatusAndVisibility(
								item.getItemSerialId(), ComConstants.ITEM_REJECT, ComConstants.VISIBLITY_INACTIVE);
					}
					// logger.error("count of resultFromUpdatingItemStatus : " +
					// resultFromUpdatingItemStatus);
					System.err.println("count of resultFromUpdatingItemStatus : " + resultFromUpdatingItemStatus);
				} else {

					iterator.remove();
					// logger.error("User already taken action on it so removing
					// item");
					System.err.println("User already taken action on it so removing item");
				}
			}

			// logger.error("Updated item list : \n" +
			// returnOrderDto.getReturnItemsList());
			System.err.println("Updated item list : \n" + returnOrderDto.getReturnItemsList());

			if (returnOrderDto.getReturnItemsList() != null && !returnOrderDto.getReturnItemsList().isEmpty()) {

				// Check if there exist any item to move to ECC
				uniqueDecisionSetAndLevelIdMap.values().stream().forEach(uniqueDecisionSetAndLevelId -> {

					// creating next level id from current level
					String nextLevel = "L" + (Integer
							.parseInt(String.valueOf(uniqueDecisionSetAndLevelId.getKey().getLevel().charAt(1))) + 1);

					SalesOrderLevelStatusDto nextLevelStatusDto = null;
					try {

						// checking next level dto
						SalesOrderLevelStatusDo levelDo = returnHeaderRepo
								.fetchLevelStatusDtoFromDecisionSetAndLevelRepository(
										uniqueDecisionSetAndLevelId.getKey().getDecisionSetId(), nextLevel)
								.get();

						if (levelDo != null)
							nextLevelStatusDto = ObjectMapperUtils.map(levelDo, SalesOrderLevelStatusDto.class);
						uniqueDecisionSetAndLevelId.setNextLevelStatusDto(nextLevelStatusDto);

						// logger.error("nextLevelStatusDto : " +
						// nextLevelStatusDto);
						System.err.println("nextLevelStatusDto : " + nextLevelStatusDto);

					} catch (Exception e) {
						// logger.error("Current level is the last level");
						System.err.println("Current level is the last level");
						e.printStackTrace();
					}

					// next level dto is null means current level is last level
					if (nextLevelStatusDto == null) {

						// mark flag for decision set and level that
						// current level is the last level
						uniqueDecisionSetAndLevelId.setLastLevel(true);
					}

					// logger.error("uniqueDecisionSetAndLevelIdMap : " +
					// uniqueDecisionSetAndLevelIdMap);
					System.err.println("uniqueDecisionSetAndLevelIdMap : " + uniqueDecisionSetAndLevelIdMap);

					if (uniqueDecisionSetAndLevelId.getApproverType().equalsIgnoreCase("AND")) {

						// Calculating cumulative status now here
						ResponseEntity<?> responseForItemToCalulateCumulativeStatus = getAllTasksFromDecisionSetAndLevelAndEvaluteCumulativeItemStatus(
								uniqueDecisionSetAndLevelId.getKey().getDecisionSetId(),
								uniqueDecisionSetAndLevelId.getKey().getLevel());

						// logger.error("responseForItemToCalulateCumulativeStatus
						// : "
						// + responseForItemToCalulateCumulativeStatus);
						System.err.println("responseForItemToCalulateCumulativeStatus : "
								+ responseForItemToCalulateCumulativeStatus);

						if (responseForItemToCalulateCumulativeStatus.getStatusCodeValue() == HttpStatus.OK.value()) {

							Map<String, Integer> mapToCalculate = (Map<String, Integer>) responseForItemToCalulateCumulativeStatus
									.getBody();
							uniqueDecisionSetAndLevelId.setMapOfCumulativeItemsStatus(mapToCalculate);
							// logger.error("Cumulative status for each items :
							// " + mapToCalculate);
							System.err.println("Cumulative status for each items : " + mapToCalculate);

						}
					}

				});

				// commenting call to ecc
				// ResponseEntity<?> responseFromECC = new ResponseEntity<>("",
				// HttpStatus.OK);
				ResponseEntity<?> responseFromECC = postSalesOrderToEcc(returnOrderDto, uniqueDecisionSetAndLevelIdMap);

				System.err.println("2716 responseFromECC : " + responseFromECC);

				if (responseFromECC.getStatusCodeValue() == HttpStatus.OK.value()) {

					MailTriggerDto mDto=new MailTriggerDto();
					mDto.setApplication("COM");
					mDto.setEntityName("COM_Approvals");
					List<String>mlist=new ArrayList<>();
//					mlist.add("Sandeep.k@incture.com");
					mlist.add("nischal.jadhav@incture.com");
					mDto.setToList(mlist);
					List<String>ccList=new ArrayList<>();
					ccList.add("nischal.jadhav@gmail.com");
					mDto.setCcList(ccList);
					HashMap<String,Object>m=new HashMap();
					m.put("Created_By",returnOrderDto.getLoggedInUserPid());
					m.put("Request_Id", returnOrderDto.getReturnReqNum());
					m.put("Customer_Name", returnOrderDto.getShipToPartyText());
					m.put("Created_Date", "");
					
					
						
						
						
						
						
					
					
					
					// Update the Hana tables
					// Update Item status
					returnOrderDto.getReturnItemsList().stream().forEach(item -> {
						// Populate task status map with task status as not
						// completed
						if (!mapOfTaskIdWithTaskStatusData.containsKey(item.getTaskStatusSerialId())) {

							List<SalesOrderTaskStatusDo> taskStatusDoList = returnHeaderRepo
									.getTaskStatusDataFromTaskSerialIdRepository(ComConstants.TASK_COMPLETE,
											item.getTaskStatusSerialId());

							List<SalesOrderTaskStatusDto> taskStatusDtoList = new ArrayList<SalesOrderTaskStatusDto>();
							if (taskStatusDoList != null) {
								taskStatusDtoList = ObjectMapperUtils.mapAll(taskStatusDoList,
										SalesOrderTaskStatusDto.class);
								System.err.println("taskStatusDtoList" + taskStatusDtoList.toString());
							}
							if (taskStatusDtoList != null && !taskStatusDtoList.isEmpty()) {
								mapOfTaskIdWithTaskStatusData.put(item.getTaskStatusSerialId(),
										taskStatusDtoList.get(0));
							}

						}

						if (item.getAcceptOrReject().equalsIgnoreCase("A")) {
							mDto.setProcess("Approve Request");
							m.put("status", "accepted");
							// Fetch next level item status for all the action
							// item creating next level id from current level
							String nextLevel = "L" + (Integer.parseInt(String.valueOf(item.getLevel().charAt(1))) + 1);

							List<SalesOrderItemStatusDto> itemStatusDtoListForNextLevel = getItemStatusDataUsingDecisionSetAndLevelAndItemNo(
									item.getDecisionSetId(), nextLevel, item.getOrderItemNum());

							if (itemStatusDtoListForNextLevel != null && !itemStatusDtoListForNextLevel.isEmpty()) {
								itemStatusDtoListForNextLevel.stream().forEach(nextLevelItemDto -> {
									// In case of Approve on current level next
									// level visibility as ACTIVE
									returnHeaderRepo.updateItemStatusAndVisibility(
											nextLevelItemDto.getItemStatusSerialId(), ComConstants.BLOCKED,
											ComConstants.VISIBLITY_ACTIVE);
								});
							}

						} else if (item.getAcceptOrReject().equalsIgnoreCase("R")) {

							// In case of Reject on current level, Fetch all the
							// item status data in other levels and tasks where
							// item status as 9, update status data as IR and
							// visibility as IIR
							mDto.setProcess("Reject Request");
							m.put("status", "rejected");
							System.err.println("updating ItemStatusAndVisibilityForIIRCase");
							returnHeaderRepo.updateItemStatusAndVisibilityForIIRCase(ComConstants.ITEM_INDIRECT_REJECT,
									ComConstants.VISIBLITY_INACTIVE_INDIRECT_REJECT, item.getDecisionSetId(),
									item.getOrderItemNum(), item.getTaskId());
						}
//						mlist.add(item.get);
						
				
//						DefDto defDto=new DefDto();
//					defDto.setApplication(mDto.getApplication());
//					defDto.setEntityName(mDto.getEntityName());
//						defDto.setProcess(mDto.getProcess());
//						mDto.setEmailDefinitionId(emailDefinitionService.getDefId(defDto));
						mDto.setContentVariables(m);
						emailDefinitionService.triggerMailforApprovals(mDto); 

					});

					// logger.error("mapOfTaskIdWithTaskStatusData : " +
					// mapOfTaskIdWithTaskStatusData);
					System.err.println("mapOfTaskIdWithTaskStatusData : " + mapOfTaskIdWithTaskStatusData);

					// Update task status and close workflow task
					mapOfTaskIdWithTaskStatusData.entrySet().stream().forEach(entry -> {

						if (!uniqueLevelSerialIdList.contains(entry.getValue().getLevelStatusSerialId())) {
							uniqueLevelSerialIdList.add(entry.getValue().getLevelStatusSerialId());
						}

						List<SalesOrderItemStatusDto> itemStatusDtoList = getItemStatusDataItemStatusAsBlockedFromTaskSerialId(
								entry.getKey());

						if (itemStatusDtoList.isEmpty() || itemStatusDtoList == null) {

							// Update task status as Completed and close
							// workflow task

							// Call workflow task closing API
							ResponseEntity<?> responseFromWorkflowClosingTaskApi = null;
							try {
								responseFromWorkflowClosingTaskApi = updateTaskCompletionBasedOnWorkflowInstanceId(
										entry.getValue().getTaskId());

								// logger.error(
								// "responseFromWorkflowClosingTaskApi : " +
								// responseFromWorkflowClosingTaskApi);
								System.err.println(
										"responseFromWorkflowClosingTaskApi : " + responseFromWorkflowClosingTaskApi);

								if (responseFromWorkflowClosingTaskApi.getStatusCodeValue() == HttpStatus.OK.value()) {
									// Updating task status when we taken action
									// in all items
									returnHeaderRepo.updateTaskStatus(entry.getKey(), ComConstants.TASK_COMPLETE,
											returnOrderDto.getLoggedInUserPid());
								}

							} catch (Exception e) {
								// logger.error("update task in workflow failed
								// due to : " + e);
								System.err.println("update task in workflow failed due to : " + e);
								// logger.error(
								// "responseFromWorkflowClosingTaskApi : " +
								// responseFromWorkflowClosingTaskApi);
								System.err.println(
										"responseFromWorkflowClosingTaskApi : " + responseFromWorkflowClosingTaskApi);
								return;
							}

						} else {

							// Update task status as In progress
							returnHeaderRepo.updateTaskStatus(entry.getKey(), ComConstants.TASK_IN_PROGRESS, "");

						}

					});

					// logger.error("uniqueLevelSerialIdList : " +
					// uniqueLevelSerialIdList);
					System.err.println("uniqueLevelSerialIdList : " + uniqueLevelSerialIdList);

					// Update level status

					uniqueLevelSerialIdList.stream().forEach(levelSerialId -> {

						List<SalesOrderTaskStatusDo> taskStatusDoList = returnHeaderRepo
								.getAllTaskFromLevelSerialIdRepository(ComConstants.TASK_COMPLETE, levelSerialId);
						List<SalesOrderTaskStatusDto> taskStatusDtoList = null;

						if (taskStatusDoList != null)
							taskStatusDtoList = ObjectMapperUtils.mapAll(taskStatusDoList,
									SalesOrderTaskStatusDto.class);
						if (taskStatusDtoList.isEmpty() || taskStatusDtoList == null) {

							// Update level status when all tasks are completed
							returnHeaderRepo.updateLevelStatus(levelSerialId, ComConstants.LEVEL_COMPLETE);

						} else {

							// Update Level status as In progress
							returnHeaderRepo.updateLevelStatus(levelSerialId, ComConstants.LEVEL_IN_PROGRESS);

						}

					});

					// Trigger IME
					// Check if there exist any item to call trigger ime
					uniqueDecisionSetAndLevelIdMap.values().stream().forEach(uniqueDecisionSetAndLevelId -> {

						SalesOrderLevelStatusDto currentLevelStatusDto = null;
						try {

							// fetching current level data dto

							SalesOrderLevelStatusDo levelDo = returnHeaderRepo
									.fetchLevelStatusDtoFromDecisionSetAndLevelRepository(
											uniqueDecisionSetAndLevelId.getKey().getDecisionSetId(),
											uniqueDecisionSetAndLevelId.getKey().getLevel())
									.get();

							if (levelDo != null)
								currentLevelStatusDto = ObjectMapperUtils.map(levelDo, SalesOrderLevelStatusDto.class);

							// logger.error("currentLevelStatusDto : " +
							// currentLevelStatusDto);
							System.err.println("currentLevelStatusDto : " + currentLevelStatusDto);

						} catch (Exception e) {
							// logger.error("Current level does not exist : " +
							// e);
							System.err.println("Current level does not exist : " + e);
							return;
						}

						// cumulatively approved or not
						// Calculating cumulative status now here
						ResponseEntity<?> responseForItemToCalulateCumulativeStatus = getAllTasksFromDecisionSetAndLevelAndEvaluteCumulativeItemStatus(
								uniqueDecisionSetAndLevelId.getKey().getDecisionSetId(),
								uniqueDecisionSetAndLevelId.getKey().getLevel());

						// logger.error("responseForItemToCalulateCumulativeStatus
						// : "
						// + responseForItemToCalulateCumulativeStatus);
						System.err.println("responseForItemToCalulateCumulativeStatus : "
								+ responseForItemToCalulateCumulativeStatus);

						if (responseForItemToCalulateCumulativeStatus.getStatusCodeValue() == HttpStatus.OK.value()) {

							Map<String, Integer> mapToCalculate = (Map<String, Integer>) responseForItemToCalulateCumulativeStatus
									.getBody();

							// logger.error("Cumulative status for each items :
							// " + mapToCalculate);
							System.err.println("Cumulative status for each items : " + mapToCalculate);

							if (!uniqueDecisionSetAndLevelId.getLastLevel() && !mapToCalculate.isEmpty()
									&& mapToCalculate.containsValue(ComConstants.ITEM_APPROVE)
									&& uniqueDecisionSetAndLevelId.getNextLevelStatusDto() != null
									&& uniqueDecisionSetAndLevelId.getNextLevelStatusDto().getLevelStatus()
											.equals(ComConstants.LEVEL_NEW)) {
								System.err.println("Level is new");

								// logger.error("Level triggered at current
								// level is on progress on "
								// + LocalDateTime.now(ZoneId.systemDefault()));
								System.err.println("Level triggered at current level is on progress on "
										+ LocalDateTime.now(ZoneId.systemDefault()));
								// Call Trigger ime method
								triggerImeService.triggerIme(uniqueDecisionSetAndLevelId.getKey().getDecisionSetId());

							} else if (currentLevelStatusDto.getLevelStatus().equals(ComConstants.LEVEL_IN_PROGRESS)) {

								// CHECK ITEM STATUS OF ALL TASKS FROM ALL
								// UPCOMING LEVELS AND IF NO ITEM IS IN 'B',
								// TRIGGER IME
								List<SalesOrderItemStatusDto> listOfBlockedItems = getItemStatusDataUsingDecisionSetForBlockedItems(
										uniqueDecisionSetAndLevelId.getKey().getDecisionSetId());

								// logger.error("Upcoming item status for
								// blocked items : " + listOfBlockedItems);
								System.err.println("Upcoming item status for blocked items : " + listOfBlockedItems);

								if (listOfBlockedItems.isEmpty()) {
									// no item found for upcoming levels as
									// items status of Blocked
									// logger.error("Level triggered at current
									// level is on progress on "
									// +
									// LocalDateTime.now(ZoneId.systemDefault()));
									System.err.println("list of blocked item is empty");
									System.err.println("Level triggered at current level is on progress on "
											+ LocalDateTime.now(ZoneId.systemDefault()));
									// Call Trigger ime method
									triggerImeService
											.triggerIme(uniqueDecisionSetAndLevelId.getKey().getDecisionSetId());

								}

							}

							// if all items have been rejected and also if it's
							// the last level
							else if (currentLevelStatusDto.getLevelStatus().equals(ComConstants.LEVEL_COMPLETE)) {
								// logger.error("current level completed,
								// triggering IME");
								System.err.println("current level completed, triggering IME");

								// Current level is not a last level
								if (!uniqueDecisionSetAndLevelId.getLastLevel()) {
									// condition check for cases where the
									// current level is complete and IME needs
									// to be triggered when next level is in NEW
									// status
									if (uniqueDecisionSetAndLevelId.getNextLevelStatusDto().getLevelStatus()
											.equals(ComConstants.LEVEL_NEW)) {

										try {
											// logger.error("Level triggered at
											// current level is on progress on "
											// +
											// LocalDateTime.now(ZoneId.systemDefault()));
											System.err.println("level status is new");
											System.err.println("Level triggered at current level is on progress on "
													+ LocalDateTime.now(ZoneId.systemDefault()));
											// call trigger ime method
											triggerImeService.triggerIme(
													uniqueDecisionSetAndLevelId.getKey().getDecisionSetId());

										} catch (Exception e) {
											e.printStackTrace();
											return;
										}

									} else if (uniqueDecisionSetAndLevelId.getNextLevelStatusDto().getLevelStatus()
											.equals(ComConstants.LEVEL_IN_PROGRESS)) {

										// CHECK ITEM STATUS OF ALL TASKS FROM
										// ALL UPCOMING LEVELS AND IF NO ITEM IS
										// IN 'B', TRIGGER IME
										List<SalesOrderItemStatusDto> listOfBlockedItems = getItemStatusDataUsingDecisionSetForBlockedItems(
												uniqueDecisionSetAndLevelId.getKey().getDecisionSetId());

										// logger.error("Upcoming item status
										// for blocked items : " +
										// listOfBlockedItems);
										System.err.println(
												"Upcoming item status for blocked items : " + listOfBlockedItems);

										if (listOfBlockedItems.isEmpty()) {
											// no item found for upcoming levels
											// as items status of Blocked
											// logger.error("Level triggered at
											// current level is on progress on "
											// +
											// LocalDateTime.now(ZoneId.systemDefault()));
											System.err.println("list of blocked items is empty");
											System.err.println("Level triggered at current level is on progress on "
													+ LocalDateTime.now(ZoneId.systemDefault()));
											// Call Trigger ime method
											triggerImeService.triggerIme(
													uniqueDecisionSetAndLevelId.getKey().getDecisionSetId());

										}

									}
									// current level is the last level
								} else if (uniqueDecisionSetAndLevelId.getLastLevel()) {
									try {
										// logger.error("Level triggered at
										// current level is on progress on "
										// +
										// LocalDateTime.now(ZoneId.systemDefault()));
										System.err.println("in get last level");
										System.err.println("Level triggered at current level is on progress on "
												+ LocalDateTime.now(ZoneId.systemDefault()));
										// call trigger ime method
										triggerImeService
												.triggerIme(uniqueDecisionSetAndLevelId.getKey().getDecisionSetId());

									} catch (Exception e) {
										e.printStackTrace();
										return;
									}
								}
							}
						}
					});

					return new ResponseEntity<>("Submit is processing.", HttpStatus.OK);
				} else {
					returnOrderDto.getReturnItemsList().stream().forEach(item -> {
						Integer resultFromUpdatingItemStatus = returnHeaderRepo.updateItemStatusAndVisibility(
								item.getItemSerialId(), ComConstants.BLOCKED, ComConstants.VISIBLITY_ACTIVE);
						// logger.error("count of resultFromUpdatingItemStatus :
						// " + resultFromUpdatingItemStatus);
						System.err.println("count of resultFromUpdatingItemStatus : " + resultFromUpdatingItemStatus);
					});
					return new ResponseEntity<>(
							"Technical Error occured. Please try again or contact your system administrator",
							HttpStatus.CONFLICT);

					// return new ResponseEntity<>(
					// new ResponseForSubmitApproval("Technical issue occur,
					// Please try to submit again.",
					// responseFromECC.getBody().toString()),
					// HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<>("All items have already taken action, Please refresh!!",
						HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(AppErrorMsgConstants.EXCEPTION_POST_MSG + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<Object> postSalesOrderToEcc(ReturnOrderDto returnOrderDto,
			Map<DecisionSetAndLevelKeyDto, DecisionSetAndLevelDto> uniqueDecisionSetAndLevelIdMap) {

		Map<String, List<ItemDataInReturnOrderDto>> splitItemOnOrderNumber = null;
		List<OdataBatchOnSubmitPayload> oDataPayloadList = null;
		String response = null;
		// logger.error("uniqueDecisionSetAndLevelIdMap" +
		// uniqueDecisionSetAndLevelIdMap);
		System.err.println("uniqueDecisionSetAndLevelIdMap" + uniqueDecisionSetAndLevelIdMap);
		try {
			// split logic based on the order number in return item order
			splitItemOnOrderNumber = splitReturnOrderItemOnOrderNumber(returnOrderDto);
			System.err.println("splitItemOnOrderNumber: " + splitItemOnOrderNumber);

			// call the method to form the payload
			if (splitItemOnOrderNumber.size() > 0) {
				oDataPayloadList = new ArrayList<>(splitItemOnOrderNumber.size());
				for (Map.Entry<String, List<ItemDataInReturnOrderDto>> entry : splitItemOnOrderNumber.entrySet()) {

					List<ItemDataInReturnOrderDto> mapItemList = entry.getValue();

					OdataBatchOnSubmitPayload batchItem = assignBatchDataOnSubmitReturnOrder(returnOrderDto,
							mapItemList, entry.getKey(), uniqueDecisionSetAndLevelIdMap);
					oDataPayloadList.add(batchItem);
				}
			}
			// call the http call to post the value
			response = ODataBatchUtil.BULK_INSERT_ON_SUBMIT_DATA(oDataPayloadList,
					ComConstants.RETURN_REQUEST_APPROVAL_BATCH_ON_SUBMIT,
					ComConstants.RETURN_REQUEST_APPROVAL_BATCH_ON_SUBMIT_TAG);

			// logger.error(">odata response " + response);
			System.err.println(">odata response " + response);
			// Parsing the OData response
			EccResponseOutputDto eccResponseOnSubmit = parseOdataResponseOnsubmit(response);
			// logger.error("> responseObject : " + responseObject);

			if (eccResponseOnSubmit.getStatusCode().equals(String.valueOf(HttpStatus.CREATED.value()))) {

				return new ResponseEntity<>(eccResponseOnSubmit, HttpStatus.OK);

			} else {

				return new ResponseEntity<>(eccResponseOnSubmit, HttpStatus.CONFLICT);
			}

		} catch (Exception e) {
			System.err.println("Exception in postSalesOrderToEcc: " + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>("Failed " + e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private Map<String, List<ItemDataInReturnOrderDto>> splitReturnOrderItemOnOrderNumber(ReturnOrderDto requestData) {

		// logger.error("/nSplit logic started >>>>>>>>>>>");
		System.err.println("Split logic started >>>>>>>>>>>");

		if (requestData.getReturnItemsList().size() == 0) {
			throw new NoRecordFoundException(AppErrorMsgConstants.RETURN_ITEM_NOT_FOUND);
		}
		Map<String, List<ItemDataInReturnOrderDto>> returnItemsgroup = new HashMap<String, List<ItemDataInReturnOrderDto>>();

		System.err.println("returnItemsgroup: " + returnItemsgroup);
		// Splitting started for InvoiceRef Item
		for (ItemDataInReturnOrderDto returnItem : requestData.getReturnItemsList()) {
			String salesOrderNum = returnItem.getOrderNum();

			// logger.error("return Item : " + returnItem.toString());
			System.err.println("return Item : " + returnItem.toString());

			List<ItemDataInReturnOrderDto> list;

			if (returnItemsgroup.containsKey(salesOrderNum)) { // refNum already
																// exists
				// in map
				// logger.error("Ref. No. " + salesOrderNum + " found in map.");
				System.err.println("Ref. No. " + salesOrderNum + " found in map.");
				// list = returnItemsgroup.get(refNum);
				boolean isAdded = returnItemsgroup.get(salesOrderNum).add(returnItem);

				// logger.error("Item added to existing list : " + isAdded);
				System.err.println("Item added to existing list : " + isAdded);

			} else {
				list = new ArrayList<>(1);
				list.add(returnItem);
				returnItemsgroup.put(salesOrderNum, list);
			}
		}

		// logger.error("/n <<<<<<<<<< End of Split logic.");
		System.err.println(" <<<<<<<<<< End of Split logic.");

		return returnItemsgroup;
	}
	// Return order approval method

	@Override
	public ResponseEntity<Object> getAllTasksFromDecisionSetAndLevelAndEvaluteCumulativeItemStatus(String decisionSetId,
			String levelNum) {

		try {
			if (!HelperClass.checkString(decisionSetId) && !HelperClass.checkString(levelNum)) {
				List<SalesOrderTaskStatusDto> list = returnHeaderRepo.getAllTaskFromDecisionSetAndLevel(decisionSetId,
						levelNum);

				if (list != null && !list.isEmpty()) {
					for (SalesOrderTaskStatusDto taskDto : list) {

						List<SalesOrderItemStatusDto> itemDtoList = returnHeaderRepo
								.getItemStatusDataUsingTaskSerialId(taskDto.getTaskStatusSerialId());
						taskDto.setItemStatusList(itemDtoList);

					}

					Map<String, List<Integer>> mapToCalculate = new HashMap<>();

					for (SalesOrderTaskStatusDto salesOrderTaskStatusDto : list) {

						for (SalesOrderItemStatusDto salesOrderItemStatusDto : salesOrderTaskStatusDto
								.getItemStatusList()) {

							if (mapToCalculate.containsKey(salesOrderItemStatusDto.getSalesOrderItemNum())) {

								List<Integer> itemStatusNumList = mapToCalculate
										.get(salesOrderItemStatusDto.getSalesOrderItemNum());
								itemStatusNumList.add(salesOrderItemStatusDto.getItemStatus());
								mapToCalculate.put(salesOrderItemStatusDto.getSalesOrderItemNum(), itemStatusNumList);
							} else {

								List<Integer> itemStatusNumList = new ArrayList<>();
								itemStatusNumList.add(salesOrderItemStatusDto.getItemStatus());

								mapToCalculate.put(salesOrderItemStatusDto.getSalesOrderItemNum(), itemStatusNumList);

							}

						}

					}

					Map<String, Integer> mapToCalculateCumulativeStatus = new HashMap<>();

					for (String itemNum : mapToCalculate.keySet()) {
						List<Integer> itemStatusNumList = mapToCalculate.get(itemNum);
						int flagToCountApproveCase = 0, flagToCountDisplayOnlyCase = 0;
						for (Integer itemStatus : itemStatusNumList) {

							if (itemStatus == ComConstants.ITEM_REJECT
									|| itemStatus == ComConstants.ITEM_INDIRECT_REJECT) {
								mapToCalculateCumulativeStatus.put(itemNum, ComConstants.ITEM_REJECT);
								break;
							} else if (itemStatus == ComConstants.ITEM_APPROVE) {
								flagToCountApproveCase++;
								if (flagToCountApproveCase == itemStatusNumList.size()) {
									mapToCalculateCumulativeStatus.put(itemNum, ComConstants.ITEM_APPROVE);
								}
							} else if (itemStatus == ComConstants.DISPLAY_ONLY_ITEM) {
								flagToCountDisplayOnlyCase++;
								if (flagToCountDisplayOnlyCase == itemStatusNumList.size()) {
									mapToCalculateCumulativeStatus.put(itemNum, ComConstants.DISPLAY_ONLY_ITEM);
								}

							} else if (itemStatus == ComConstants.REJECTED_FROM_ECC) {
								mapToCalculateCumulativeStatus.put(itemNum, ComConstants.REJECTED_FROM_ECC);
								break;
							} else {
								mapToCalculateCumulativeStatus.put(itemNum, ComConstants.BLOCKED);
							}

						}

					}

					return new ResponseEntity<>(mapToCalculateCumulativeStatus, HttpStatus.OK);
				} else {
					return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
				}
			} else {
				return new ResponseEntity<>("Decision set and Level Num fields are mandatory", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Exception " + e, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private OdataBatchOnSubmitPayload assignBatchDataOnSubmitReturnOrder(ReturnOrderDto returnOrderDto,
			List<ItemDataInReturnOrderDto> mapItemList, String orderNum,
			Map<DecisionSetAndLevelKeyDto, DecisionSetAndLevelDto> uniqueDecisionSetAndLevelIdMap) {
		// logger.error("> Start :: ODataBatchPayload method.");
		System.err.println("> Start :: ODataBatchPayload method.");

		// batch Payload for the onsubmit for header
		// ODataBatchPayload batchItem = new ODataBatchPayload();
		OdataBatchOnSubmitPayload batchHeaderData = new OdataBatchOnSubmitPayload();
		int countRejectedItem = 0;
		batchHeaderData.setCreatedBy(returnOrderDto.getRequesterName());
		batchHeaderData.setDocNumber(orderNum);
		// batchHeaderData.setDlvBlock(returnOrderDto.getHeaderDeliveryBlock());
		batchHeaderData.setDlvBlock("");
		batchHeaderData.setPurchNo("SUBMIT");
		batchHeaderData.setHdrText(generateEccText(returnOrderDto.getLoggedInUserId()));

		// batch dto Payload for the onsubmit for item
		List<OdataBatchOnsubmitItem> returnItemList = new ArrayList<>(mapItemList.size());
		OdataBatchOnsubmitItem item;
		System.err.println("mapItemList in odata payload method: " + mapItemList);
		for (ItemDataInReturnOrderDto itemData : mapItemList) {

			if (uniqueDecisionSetAndLevelIdMap
					.containsKey(new DecisionSetAndLevelKeyDto(itemData.getDecisionSetId(), itemData.getLevel()))) {

				item = new OdataBatchOnsubmitItem();

				item.setDocNumber(itemData.getOrderNum());
				item.setCreatedBy(returnOrderDto.getRequesterName());
				item.setItmNumber(itemData.getOrderItemNum());
				item.setMaterial(itemData.getMaterialNum());
				item.setReaForRe(itemData.getReasonForRejection());

				if (itemData.getAcceptOrReject().equalsIgnoreCase("A")) {

					if (uniqueDecisionSetAndLevelIdMap
							.get(new DecisionSetAndLevelKeyDto(itemData.getDecisionSetId(), itemData.getLevel()))
							.getLastLevel()) {
						if (uniqueDecisionSetAndLevelIdMap
								.get(new DecisionSetAndLevelKeyDto(itemData.getDecisionSetId(), itemData.getLevel()))
								.getApproverType().equalsIgnoreCase("OR")) {
							item.setApprove_Rej(itemData.getAcceptOrReject());
							item.setItemText(generateEccText(returnOrderDto.getLoggedInUserId()));

							// logger.error("Removing IDB in sales doc item on :
							// " + orderNum + " "
							// + itemData.getOrderItemNum());
							System.err.println("Removing IDB in sales doc item on : " + orderNum + "  "
									+ itemData.getOrderItemNum());
							returnHeaderRepo.removeBlockFromItem(orderNum, itemData.getOrderItemNum());

							returnItemList.add(item);
						} else if (uniqueDecisionSetAndLevelIdMap
								.get(new DecisionSetAndLevelKeyDto(itemData.getDecisionSetId(), itemData.getLevel()))
								.getApproverType().equalsIgnoreCase("AND")
								&& uniqueDecisionSetAndLevelIdMap
										.get(new DecisionSetAndLevelKeyDto(itemData.getDecisionSetId(),
												itemData.getLevel()))
										.getMapOfCumulativeItemsStatus().containsKey(itemData.getOrderItemNum())
								&& uniqueDecisionSetAndLevelIdMap
										.get(new DecisionSetAndLevelKeyDto(itemData.getDecisionSetId(),
												itemData.getLevel()))
										.getMapOfCumulativeItemsStatus().get(itemData.getOrderItemNum())
										.equals(ComConstants.ITEM_APPROVE)) {
							item.setApprove_Rej(itemData.getAcceptOrReject());
							item.setItemText(generateEccText(returnOrderDto.getLoggedInUserId()));

							// logger.error("Removing IDB in sales doc item on :
							// " + orderNum + " "
							// + itemData.getOrderItemNum());
							System.err.println("Removing IDB in sales doc item on : " + orderNum + "  "
									+ itemData.getOrderItemNum());
							returnHeaderRepo.removeBlockFromItem(orderNum, itemData.getOrderItemNum());

							returnItemList.add(item);
						} else {
							item.setApprove_Rej("PA");
							item.setItemText(itemData.getLevel() + ", approved by " + returnOrderDto.getLoggedInUserId()
									+ " on " + new DateUtils().dateFormatForECC);
							returnItemList.add(item);

						}
					} else {
						item.setApprove_Rej("PA");
						item.setItemText(itemData.getLevel() + ", approved by " + returnOrderDto.getLoggedInUserId()
								+ " on " + new DateUtils().dateFormatForECC);
						returnItemList.add(item);

					}

				} else if (itemData.getAcceptOrReject().equalsIgnoreCase("R")) {
					++countRejectedItem;
					item.setApprove_Rej(itemData.getAcceptOrReject());
					item.setItemText(generateEccText(returnOrderDto.getLoggedInUserId()));

					// logger.error("Adding reason of rejection in sales doc
					// item on : " + orderNum + " "
					// + itemData.getOrderItemNum() + " " +
					// itemData.getReasonForRejection());
					System.err.println("Adding reason of rejection in sales doc item on : " + orderNum + "  "
							+ itemData.getOrderItemNum() + "  " + itemData.getReasonForRejection());
					returnHeaderRepo.updateReasonOfRejectionInSalesOrder(orderNum, itemData.getOrderItemNum(),
							itemData.getReasonForRejection(), itemData.getReasonForRejectionText());
					returnItemList.add(item);

				}
			}
		}

		if (countRejectedItem == mapItemList.size()) {
			batchHeaderData.setApprove_Rej("R");
		} else {
			batchHeaderData.setApprove_Rej("A");
		}

		OrderToItems orderItem = new OrderToItems(returnItemList);

		batchHeaderData.setOrderToItems(orderItem);

		// logger.error("> Batch Item : ");
		System.err.println("> Batch Item : ");
		// logger.error("> Stop :: ODataBatchPayload method.");
		System.err.println("> Stop :: ODataBatchPayload method.");

		return batchHeaderData;

	}

	public String generateEccText(String loggedInUserName) {
		System.err.println("[generateEccText] loggedInUserName: " + loggedInUserName);
		try {
			loggedInUserName += (" on " + new DateUtils().dateFormatForECC);
		} catch (Exception e) {
			System.err.println("Exception in [generateEccText]: " + e.getMessage());
			e.printStackTrace();
		}
		return loggedInUserName;
	}

	public EccResponseOutputDto parseOdataResponseOnsubmit(String response)
			throws JsonMappingException, JsonProcessingException {
		EccResponseOutputDto eccOutput = new EccResponseOutputDto();

		if (response.contains("binary")) {

			int indexOfStatus = response.indexOf("binary");
			// logger.error("indexOfStatus" + indexOfStatus);
			System.err.println("indexOfStatus" + indexOfStatus);
			System.err.println();
			String statusCode = response.substring(indexOfStatus, indexOfStatus + 20);
			eccOutput.setStatusCode(statusCode.split("\\s+")[1]);
			// logger.error("statusCode =" + statusCode);
			System.err.println("statusCode =" + statusCode);

			if (!response.contains("error")) {
				eccOutput.setMessage("Success");

			} else {
				String[] splittedResponse = response.split("dataserviceversion: 1.0");
				String errorResponse = splittedResponse[1];
				String errorResponse2 = splittedResponse[0];
				// logger.error("errorResponse" + errorResponse);
				System.err.println("errorResponse" + errorResponse);
				// logger.error("errorResponse2 =" + errorResponse2);
				System.err.println("errorResponse2 =" + errorResponse2);
				JSONObject obj = new JSONObject(errorResponse);
				JSONObject object = obj.getJSONObject("error").getJSONObject("innererror");
				JSONArray errorArray = object.getJSONArray("errordetails");
				StringBuffer buffer = new StringBuffer();
				if (errorArray != null && !errorArray.isEmpty()) {
					for (int i = 0; i < errorArray.length(); i++) {
						JSONObject jsonResponse = (JSONObject) errorArray.get(i);
						String message = jsonResponse.get("message").toString();
						// logger.error("message =" + message);
						System.err.println("message =" + message);
						buffer.append(message);
						if (i != errorArray.length() - 1) {

							buffer.append(" and ");
						}
					}
				} else {

					buffer.append(obj.getJSONObject("error").getJSONObject("message").get("value").toString());
				}
				String errorMessage = buffer.toString();
				// logger.error("errorMessage " + errorMessage);
				System.err.println("errorMessage " + errorMessage);
				eccOutput.setMessage(errorMessage);

			}
		} else {

			eccOutput.setMessage("failed due to excpetion in ecc" + response);
			eccOutput.setStatusCode("500");
		}
		return eccOutput;
	}

	// pass taskId from the SalesOrderTaskStatusDo table
	@Override
	public ResponseEntity<Object> updateTaskCompletionBasedOnWorkflowInstanceId(String taskId) {
		// get all the taskInstanceId by taskid
		try {
			ResponseEntity<Object> responseTaskInstanceId = getInstanceIdByWorkflowInstanceId(taskId);

			// logger.error("responseTaskInstanceId " + responseTaskInstanceId);
			System.err.println("responseTaskInstanceId " + responseTaskInstanceId);

			if (responseTaskInstanceId.getStatusCodeValue() == 200) {

				String taskInstaceId = (String) responseTaskInstanceId.getBody();

				// update all the taskInstance to completed by taskInstanceId

				ResponseEntity<?> response = HelperClass.completeTaskInWorkflowUsingOauthClient(taskInstaceId);
				// logger.error("responseUpdate :" + response);
				System.err.println("responseUpdate :" + response);
				return new ResponseEntity<Object>(response.toString(), HttpStatus.OK);

			} else {

				return new ResponseEntity<Object>(responseTaskInstanceId.getBody().toString(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {

			return new ResponseEntity<Object>("Faild " + e, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	@SuppressWarnings({ "resource", "deprecation", "unused" })
	public ResponseEntity<Object> getInstanceIdByWorkflowInstanceId(String workflowInstaceId) {
		Map<String, String> mapOfTaskId = new HashMap<String, String>();
		HttpClient client = null;
		InputStream instream = null;
		// logger.error("line 542 inside get id");
		if (!workflowInstaceId.isEmpty()) {
			try {
				client = new DefaultHttpClient();
				// Map<String, Object> map = DestinationReaderUtil
				// .getDestination(DkshApplicationConstants.WORKFLOW_TRIGGER_ID);

				String jwToken = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApi();

				// String userpass = (String) map.get("User") + ":" + (String)
				// map.get("Password");
				// String encoding =
				// javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());

				String url = ComConstants.WORKFLOW_REST_BASE_URL;
				String trimmed = url.substring(8);
				URIBuilder builder = new URIBuilder();
				builder.setScheme("https").setHost(trimmed).setPath("/v1/task-instances")
						.setParameter("workflowInstanceId", workflowInstaceId);
				URI uri = builder.build();
				HttpGet request = new HttpGet(uri);
				request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwToken);
				HttpResponse httpresponse = null;
				String result = "";
				httpresponse = client.execute(request);
				HttpEntity entity = httpresponse.getEntity();
				if (entity != null) {
					instream = entity.getContent();
					result = ServicesUtil.convertStreamToString(instream);
					JSONArray responseResult = new JSONArray(result);
					if (httpresponse.getStatusLine().getStatusCode() != 500) {
						if (httpresponse.getStatusLine().getStatusCode() != 429) {
							if (httpresponse.getStatusLine().getStatusCode() == 200) {
								JSONObject responsejson = (JSONObject) responseResult.get(0);
								String taskInstanceId = responsejson.getString("id");
								if (taskInstanceId != null) {

									return new ResponseEntity<Object>(taskInstanceId, HttpStatus.OK);
								}
							}

						} else {

							throw new RuntimeException(
									"Too many requests. You’ve reached the usage limits that are configured for your tenant. Reduce the number of requests");
						}
					} else {

						throw new RuntimeException(
								"Internal server error. The operation you requested led to an error during execution. ");
					}
				}

			} catch (Exception e) {
				return new ResponseEntity<Object>("failed due to  " + e, HttpStatus.INTERNAL_SERVER_ERROR);
			} finally {
				try {
					if (instream != null) {
						instream.close();
					}
				}

				catch (Exception e) {
					throw new RuntimeException("Failed to close instream " + e);
				}
			}

		}
		return new ResponseEntity<Object>("failed ", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	public List<SalesOrderItemStatusDto> getItemStatusDataUsingDecisionSetAndLevelAndItemNo(String decisionSet,
			String level, String itemNo) {

		return entityManager.createNativeQuery("select i.* "
				+ "from so_level_status l join so_task_status t on l.LEVEL_STATUS_SERIAL_ID = t.LEVEL_STATUS_SERIAL_ID "
				+ "join so_item_status i on i.TASK_STATUS_SERIAL_ID = t.TASK_STATUS_SERIAL_ID " + "where l.level = '"
				+ level + "' and l.decision_set_id = '" + decisionSet + "'and i.so_item_num = '" + itemNo + "'",
				NativeSqlResultMapping.SALES_DOC_ITEM_STATUS_DATA).getResultList();

	}

	public List<SalesOrderItemStatusDto> getItemStatusDataItemStatusAsBlockedFromTaskSerialId(String taskSerialId) {

		return entityManager.createNativeQuery(
				"select item.* from so_item_status item where item.item_status = '" + ComConstants.BLOCKED
						+ "' and item.TASK_STATUS_SERIAL_ID = '" + taskSerialId + "'",
				NativeSqlResultMapping.SALES_DOC_ITEM_STATUS_DATA).getResultList();
	}

	public List<SalesOrderItemStatusDto> getItemStatusDataUsingDecisionSetForBlockedItems(String decisionSetId) {

		return entityManager
				.createNativeQuery("select i.* "
						+ "from so_level_status l join so_task_status t on l.LEVEL_STATUS_SERIAL_ID = t.LEVEL_STATUS_SERIAL_ID "
						+ "join so_item_status i on i.TASK_STATUS_SERIAL_ID = t.TASK_STATUS_SERIAL_ID "
						+ "where l.decision_set_id = \'" + decisionSetId + "\'and i.item_status = \'"
						+ ComConstants.BLOCKED + "\'", NativeSqlResultMapping.SALES_DOC_ITEM_STATUS_DATA)
				.getResultList();

	}

}