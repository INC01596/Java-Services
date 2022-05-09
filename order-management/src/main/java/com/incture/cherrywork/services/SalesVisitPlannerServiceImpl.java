package com.incture.cherrywork.services;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incture.cherrywork.dto.workflow.SalesVisitPlannerContextDto;
import com.incture.cherrywork.dtos.ApproveVisitDto;
import com.incture.cherrywork.dtos.CustomerAddressDto;
import com.incture.cherrywork.dtos.CustomerContactDto;
import com.incture.cherrywork.dtos.InboxVisitListDto;
import com.incture.cherrywork.dtos.Response;
import com.incture.cherrywork.dtos.SalesVisitAttachmentDto;
import com.incture.cherrywork.dtos.SalesVisitFilterDto;
import com.incture.cherrywork.dtos.VisitActivitiesResponseDto;
import com.incture.cherrywork.dtos.VisitActivityDto;
import com.incture.cherrywork.dtos.VisitPlanDto;
import com.incture.cherrywork.dtos.VisitPlannerWorkflowTaskOutputDto;
import com.incture.cherrywork.entities.CustomerAddress;
import com.incture.cherrywork.entities.CustomerContact;
import com.incture.cherrywork.entities.SalesRepToManager;
import com.incture.cherrywork.entities.SalesVisit;
import com.incture.cherrywork.entities.SalesVisitAttachment;
import com.incture.cherrywork.entities.VisitActivities;
import com.incture.cherrywork.repositories.ICustomerContactRepository;
import com.incture.cherrywork.repositories.ISalesRepToManagerRepository;
import com.incture.cherrywork.repositories.ISalesVisitAttachmentRepository;
import com.incture.cherrywork.repositories.ISalesVisitPlannerRepository;
import com.incture.cherrywork.repositories.IVisitActivitiesRepository;
import com.incture.cherrywork.repositories.JdbcRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.repositories.ServicesUtils;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.DestinationReaderUtil;
import com.incture.cherrywork.util.HelperClass;
import com.incture.cherrywork.util.ReturnExchangeConstants;
import com.incture.cherrywork.util.SequenceNumberGen;
import com.incture.cherrywork.util.ServicesUtil;

import io.pivotal.cfenv.core.CfCredentials;
import io.pivotal.cfenv.jdbc.CfJdbcEnv;

@Service("SalesVisitPlannerServiceImpl")
@Transactional
public class SalesVisitPlannerServiceImpl implements ISalesVisitPlannerService {

	@Autowired
	private ISalesVisitPlannerRepository salesVisitRepository;

	@Autowired
	private IVisitActivitiesRepository visitActivitiesRepository;

	@Autowired
	private ISalesRepToManagerRepository salesRepToManagerRepository;

	@Autowired
	private ICustomerContactRepository customerContactRepository;

	@Autowired
	private ISalesVisitAttachmentRepository salesVisitAttachmentRepository;

	@PersistenceContext
	private EntityManager entityManager;

	Logger logger = LoggerFactory.getLogger(SalesVisitPlannerServiceImpl.class);

	@Autowired
	JdbcRepository jdbcRepository;

	private SequenceNumberGen sequenceNumberGen;

	public ResponseEntity<Response> createVisit(VisitPlanDto dto) {

		Long tempVisitId = jdbcRepository.getVisitSequence();
		StringBuilder visitId = new StringBuilder();
		SimpleDateFormat monthYear = new SimpleDateFormat("MMyy");
		Date dateNow = new Date();
		logger.info("[SalesVisitPlannerServiceImpl][createVisit] tempVisitId: " + tempVisitId);
		SalesVisit visit = ObjectMapperUtils.map(dto, SalesVisit.class);
		visitId.append("V-");
		visitId.append(dto.getCustCode());
		visitId.append(monthYear.format(dateNow));
		visitId.append(tempVisitId);
		visit.setVisitId(visitId.toString());
		visit.setVisitCretedAt(new Date());
		sequenceNumberGen = SequenceNumberGen.getInstance();
		Session session = entityManager.unwrap(Session.class);
		String tempId = sequenceNumberGen.getNextSeqNumber(ReturnExchangeConstants.VISIT_PLANNER_APPROVAL_SEQ, 6,
				session);
		visit.setReturnReqNum(tempId);
		List<SalesVisitAttachmentDto> attachmentList = dto.getAttachment();
		if (!attachmentList.isEmpty()) {
			List<SalesVisitAttachment> attachList = processAttachment(attachmentList, visitId.toString());
			salesVisitAttachmentRepository.saveAll(attachList);
		}
		visit.setCustAddress(processCustomerAddress(dto, visit));
		visit.setCustomerContact(processCustomerContact(dto, visit));
		visit = salesVisitRepository.save(visit);
		if (visit.getStatus() != null && visit.getStatus().equals("Planned")) {
			triggerWorkflow(visit);
			notifySalesRepManager(visit.getSalesRepEmail(), visit.getVisitId());
			return ResponseEntity.status(HttpStatus.OK)
					.body(Response.builder().data(visit).statusCode(HttpStatus.OK).status(ResponseStatus.SUCCESS)
							.message("Visit created with id: " + visitId + " and task triggered for approval.")
							.build());
		}
		return ResponseEntity.status(HttpStatus.OK).body(Response.builder().data(visit).statusCode(HttpStatus.OK)
				.status(ResponseStatus.SUCCESS).message("Visit drafted with id: " + visitId + ".").build());
	}

	public List<SalesVisitAttachment> processAttachment(List<SalesVisitAttachmentDto> attachDtoList, String visitId) {
		List<SalesVisitAttachment> attachList = new ArrayList<>();

		for (SalesVisitAttachmentDto attachDto : attachDtoList) {
			SalesVisitAttachment attach = ObjectMapperUtils.map(attachDto, SalesVisitAttachment.class);
			// SalesVisitAttachmentPk key = new
			// SalesVisitAttachmentPk(ServicesUtil.randomId(), visitDo);
			// attach.setSalesVisitAttachmentKey(key);
			if (attach.getAttachmentId() == null || ServicesUtil.isEmpty(attach.getAttachmentId())) {
				attach.setAttachmentId(ServicesUtil.randomId());
				attach.setVisitId(visitId);
			}
			attachList.add(attach);
		}
		return attachList;
	}

	public List<CustomerAddress> processCustomerAddress(VisitPlanDto visit, SalesVisit visitDo) {
		List<CustomerAddress> addList = new ArrayList<>();

		for (CustomerAddressDto addressDto : visit.getCustAddress()) {
			CustomerAddress address = ObjectMapperUtils.map(addressDto, CustomerAddress.class);
			// CustomerAddressPk key = new
			// CustomerAddressPk(ServicesUtil.randomId(), visitDo);
			// address.setCustomerAddressKey(key);
			address.setSalesVisit(visitDo);
			if (address.getCustomerAddressId() == null || ServicesUtil.isEmpty(address.getCustomerAddressId()))
				address.setCustomerAddressId(ServicesUtil.randomId());
			addList.add(address);
		}
		return addList;

	}

	public List<CustomerContact> processCustomerContact(VisitPlanDto visit, SalesVisit visitDo) {
		List<CustomerContact> contactList = new ArrayList<>();

		for (CustomerContactDto contactDto : visit.getCustomerContact()) {
			CustomerContact contact = ObjectMapperUtils.map(contactDto, CustomerContact.class);
			// CustomerContactPk key = new
			// CustomerContactPk(ServicesUtil.randomId(), visitDo);
			// contact.setCustomerContactKey(key);
			contact.setSalesVisit(visitDo);
			// if (contact.getCustomerContactId() == null ||
			// ServicesUtil.isEmpty(contact.getCustomerContactId()))
			contact.setCustomerContactId(ServicesUtil.randomId());
			contactList.add(contact);
		}
		return contactList;

	}

	public ResponseEntity<Response> updateVisit(VisitPlanDto dto) {

		logger.info("[SalesVisitPlannerServiceImpl][createVisit] VisitId: " + dto.getVisitId());
		SalesVisit oldVisit = salesVisitRepository.findByVisitId(dto.getVisitId());
		for (CustomerContact cont : oldVisit.getCustomerContact()) {
			customerContactRepository.deleteById(cont.getCustomerContactId());
		}
		SalesVisit visit = ObjectMapperUtils.map(dto, SalesVisit.class);
		List<SalesVisitAttachmentDto> attachmentList = dto.getAttachment();
		if (!attachmentList.isEmpty()) {
			List<SalesVisitAttachment> attachList = processAttachment(attachmentList, dto.getVisitId().toString());
			salesVisitAttachmentRepository.saveAll(attachList);
		}
		visit.setCustAddress(processCustomerAddress(dto, visit));
		visit.setCustomerContact(processCustomerContact(dto, visit));
		if (visit.getVisitId() == null || ServicesUtil.isEmpty(visit.getVisitId())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Response.builder().data(dto).statusCode(HttpStatus.BAD_REQUEST).status(ResponseStatus.FAILED)
							.message("Visit Id is required").build());
		}

		salesVisitRepository.save(visit);
		if ((oldVisit != null || !ServicesUtil.isEmpty(oldVisit))
				&& (oldVisit.getStatus() != null || !ServicesUtil.isEmpty(oldVisit.getStatus()))
				&& !oldVisit.getStatus().equals("Approved") && dto.getStatus().equals("Completed")) {
			notifySalesRepAndManager(dto.getSalesRepEmail(), dto.getVisitId());
		} else if ((oldVisit != null || !ServicesUtil.isEmpty(oldVisit))
				&& (oldVisit.getStatus() != null || !ServicesUtil.isEmpty(oldVisit.getStatus()))
				&& !oldVisit.getStatus().equals("Drafted") && dto.getStatus().equals("Planned")) {
			triggerWorkflow(visit);
			notifySalesRepManager(visit.getSalesRepEmail(), visit.getVisitId());
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(Response.builder().data(dto).statusCode(HttpStatus.OK).status(ResponseStatus.SUCCESS)
						.message("Visit updated and task triggered for further level approval.").build());

	}

	public ResponseEntity<Response> deleteVisit(String visitId) {

		logger.info("[SalesVisitPlannerServiceImpl][createVisit] VisitId: " + visitId);
		salesVisitRepository.deleteById(visitId);
		return ResponseEntity.status(HttpStatus.OK).body(Response.builder().data(visitId).statusCode(HttpStatus.OK)
				.status(ResponseStatus.SUCCESS).message("Visit deleted.").build());

	}

	public ResponseEntity<Response> getAllVisit() {

		return ResponseEntity.status(HttpStatus.OK)
				.body(Response.builder()
						.data(ObjectMapperUtils.mapAll(salesVisitRepository.findAll(), VisitPlanDto.class))
						.statusCode(HttpStatus.OK).status(ResponseStatus.SUCCESS).message("Visits fetched!").build());

	}

	public ResponseEntity<Response> getVisitById(String visitId) {

		logger.info("[SalesVisitPlannerServiceImpl][createVisit] VisitId: " + visitId);
		Response res = new Response();
		res.setData(ObjectMapperUtils.map(salesVisitRepository.findByVisitId(visitId), VisitPlanDto.class));
		res.setMessage("Fetched successfully!");
		res.setStatusCode(HttpStatus.OK);
		return ResponseEntity.status(HttpStatus.OK).header("message", "Visit fetched.").body(res);

	}

	public ResponseEntity<Response> filter(SalesVisitFilterDto filterDto) {
		StringBuilder query = new StringBuilder();
		query.append("from SalesVisit where");
		if (filterDto.getSalesRepId() != null && !ServicesUtil.isEmpty(filterDto.getSalesRepId()))
			query.append(" salesRepId = '" + filterDto.getSalesRepId() + "' and");
		if (filterDto.getStatus() != null && !ServicesUtil.isEmpty(filterDto.getStatus()))
			query.append(" status = '" + filterDto.getStatus() + "' and");
		if (filterDto.getCustCode() != null && !ServicesUtil.isEmpty(filterDto.getCustCode()))
			query.append(" custCode = '" + filterDto.getCustCode() + "' and");
		if (filterDto.getVisitId() != null && !ServicesUtil.isEmpty(filterDto.getVisitId()))
			query.append(" visitId='" + filterDto.getVisitId() + "' and");
		if (filterDto.getVisitPlannedDateFrom() != null && !ServicesUtil.isEmpty(filterDto.getVisitPlannedDateFrom()))
			query.append(" plannedFor >= '" + filterDto.getVisitPlannedDateFrom() + "' and");
		if (filterDto.getVisitPlannedDateTo() != null && !ServicesUtil.isEmpty(filterDto.getVisitPlannedDateTo()))
			query.append(" plannedFor <= '" + filterDto.getVisitPlannedDateTo() + "' and");
		if (filterDto.getVisitCompletedAtFrom() != null && !ServicesUtil.isEmpty(filterDto.getVisitCompletedAtFrom()))
			query.append(" completedAt >= '" + filterDto.getVisitCompletedAtFrom() + "' and");
		if (filterDto.getVisitCompletedAtTo() != null && !ServicesUtil.isEmpty(filterDto.getVisitCompletedAtTo()))
			query.append(" completedAt <= '" + filterDto.getVisitCompletedAtTo() + "' and");
		if (filterDto.getVisitClosedAtFrom() != null && !ServicesUtil.isEmpty(filterDto.getVisitClosedAtFrom()))
			query.append(" closedAt >= '" + filterDto.getVisitClosedAtFrom() + "' and");
		if (filterDto.getVisitClosedAtTo() != null && !ServicesUtil.isEmpty(filterDto.getVisitClosedAtTo()))
			query.append(" closedAt <= '" + filterDto.getVisitClosedAtTo() + "' and");
		String query1 = query.toString();
		query1 = query1.substring(0, query1.length() - 3);
		Query q1 = entityManager.createQuery(query1);
		List<SalesVisit> list = q1.getResultList();
		List<VisitPlanDto> dtoList = new ArrayList<>();
		if (!list.isEmpty())
			dtoList = ObjectMapperUtils.mapAll(list, VisitPlanDto.class);
		return ResponseEntity.status(HttpStatus.OK).body(Response.builder().data(dtoList).statusCode(HttpStatus.OK)
				.status(ResponseStatus.SUCCESS).message("Visits fetched!").build());

	}

	public void triggerWorkflow(SalesVisit visit) {
		SalesVisitPlannerContextDto contextDto = new SalesVisitPlannerContextDto();
		contextDto.setDefinitionId("salesvisitworkflow.salesvisitworkflow");
		contextDto.setVisitId(visit.getVisitId());
		contextDto.setCustCode(visit.getCustCode());
		contextDto.setSalesRepId(visit.getSalesRepId());
		contextDto.setSalesRepEmail(visit.getSalesRepEmail());
		contextDto.setSalesRepName(visit.getSalesRepName());
		contextDto.setPlannedFor(visit.getPlannedFor());
		contextDto.setVisitCreatedAt(visit.getVisitCretedAt());
		contextDto.setScheduledStartTime(visit.getScheduledStartTime());
		contextDto.setScheduledEndTime(visit.getScheduledEndTime());
		triggerWorkFlowAfterSavingToHana(contextDto);
	}

	@SuppressWarnings("unchecked")
	public String triggerWorkFlowAfterSavingToHana(SalesVisitPlannerContextDto contextDto) {
		try {
			CfJdbcEnv cfEnv = new CfJdbcEnv();
			CfCredentials cfCredentialsWorkflow = cfEnv.findCredentialsByLabel("workflow");
			Map<String, Object> cfCredentialsMapWorkflow = cfCredentialsWorkflow.getMap();
			Map<String, Object> credentialMapWorkflowEndPoints = (Map<String, Object>) cfCredentialsMapWorkflow
					.get("endpoints");

			String url = null;
			String jwToken = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApi();
			url = (String) credentialMapWorkflowEndPoints.get("workflow_rest_url") + "/v1/workflow-instances";
			HttpPost httpPost = null;
			CloseableHttpResponse responseClient = null;
			CloseableHttpClient httpClient = null;
			httpClient = getHTTPClient();

			httpPost = new HttpPost(url);
			httpPost.addHeader("Content-type", "application/json");
			if (jwToken != null) {
				httpPost.addHeader("Authorization", "Bearer " + jwToken);
				String payload = "{\"definitionId\":\"" + contextDto.getDefinitionId() + "\",\"context\":";
				ObjectMapper obj = new ObjectMapper();
				payload += obj.writeValueAsString(contextDto) + "}";

				StringEntity workflowPayload = new StringEntity(payload);
				httpPost.setEntity(workflowPayload);
				responseClient = httpClient.execute(httpPost);

				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				InputStream inputStream = responseClient.getEntity().getContent();
				byte[] data = new byte[1024];
				int length = 0;
				while ((length = inputStream.read(data)) > 0) {
					bytes.write(data, 0, length);
				}
				String respBody = new String(bytes.toByteArray(), "UTF-8");

				httpPost.releaseConnection();
				httpClient.close();
				logger.info("[triggerWorkFlowAfterSavingToHana] after w/f trigger respBody: " + respBody);
				return respBody;
			} else {
				logger.error(
						"[triggerWorkFlowAfterSavingToHana] trigger failure due to no XSCRF-Token not gernerated ");
				return "trigger failure due to no XSCRF-Token not gernerated ";
			}

		} catch (Exception e) {
			logger.error("[triggerWorkFlowAfterSavingToHana] Exception {}" + e);
			return "Trigger FAILURE" + e.getMessage();
		}

	}

	private static CloseableHttpClient getHTTPClient() {
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		return clientBuilder.build();
	}

	public ResponseEntity<Response> updateVisitWfTaskStatus(VisitPlanDto dto) {
		SalesVisit visit = salesVisitRepository.findByVisitId(dto.getVisitId());
		visit.setTaskStatus(dto.getTaskStatus());
		salesVisitRepository.save(visit);
		return ResponseEntity.status(HttpStatus.OK).body(Response.builder().data(dto).statusCode(HttpStatus.OK)
				.status(ResponseStatus.SUCCESS).message("Task Status Updated").build());

	}

	public String getSalesRepManager(String repEMail) {
		SalesRepToManager mapping = salesRepToManagerRepository.findBySalesRepEmail(repEMail);
		if (mapping != null)
			return mapping.getManagerEmail();
		return null;
	}

	public ResponseEntity<Response> getTaskListInInbox(InboxVisitListDto dto) {
		try {
			logger.info("[getTaskListInInbox] userId: " + dto.getUserId());
			List<VisitPlanDto> dtoList = null;
			ResponseEntity<Object> responseFromSapApi = HelperClass
					.fetchUserTasksForVisitPlannerWorkflowOfReturnsForNewDac(dto.getUserId());

			if (responseFromSapApi.getStatusCodeValue() == HttpStatus.OK.value()) {

				List<VisitPlannerWorkflowTaskOutputDto> listOfWorkflowTasks = (List<VisitPlannerWorkflowTaskOutputDto>) responseFromSapApi
						.getBody();

				System.err.println("[getTaskListInInbox] listOfWorkflowTasks : " + listOfWorkflowTasks);
				List<String> visitIdList = new ArrayList<>();
				Map<String, String> map = new HashMap<>();
				for (VisitPlannerWorkflowTaskOutputDto task : listOfWorkflowTasks) {
					visitIdList.add(task.getSubject());
					map.put(task.getSubject(), task.getId());
				}
				Optional<List<SalesVisit>> visitListOpt = salesVisitRepository.findAllByVisitId(visitIdList);

				if (visitListOpt.isPresent()) {
					dtoList = ObjectMapperUtils.mapAll(visitListOpt.get(), VisitPlanDto.class);
					for (SalesVisit visit : visitListOpt.get()) {
						visit.setTaskInstanceId(map.get(visit.getVisitId()));
						salesVisitRepository.save(visit);
					}
				}

			}
			logger.info("[getTaskListInInbox] dtoList to return: " + dtoList);
			return ResponseEntity.status(HttpStatus.OK).body(Response.builder().data(dtoList).statusCode(HttpStatus.OK)
					.status(ResponseStatus.SUCCESS).message("Task list fetched.").build());

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Response.builder().data(null).statusCode(HttpStatus.OK).status(ResponseStatus.FAILED)
							.message("Exception while fetching task list." + e.getMessage()).build());

		}

	}

	public ResponseEntity<Response> submitTask(ApproveVisitDto dto) {

		if (dto.getVisitId() == null || ServicesUtil.isEmpty(dto.getVisitId())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Response.builder().statusCode(HttpStatus.BAD_REQUEST).status(ResponseStatus.SUCCESS)
							.message("visit id is mandatory").build());
		}
		SalesVisit visit = salesVisitRepository.findByVisitId(dto.getVisitId());
		visit.setApprovalStatus(dto.getApprovalStatus());
		visit.setStatus(dto.getStatus());
		salesVisitRepository.save(visit);
		ResponseEntity<?> response = HelperClass.completeTaskInWorkflowUsingOauthClient(visit.getTaskInstanceId());
		if (response.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
			notifySalesRep(visit.getSalesRepEmail(), visit.getVisitId(), dto.getStatus());
			return ResponseEntity.status(HttpStatus.OK).body(Response.builder().statusCode(HttpStatus.OK)
					.status(ResponseStatus.SUCCESS).message("Task " + dto.getStatus()).build());

		}
		logger.error("[submitTask] responseUpdate :" + response);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder().statusCode(HttpStatus.BAD_REQUEST)
				.status(ResponseStatus.SUCCESS).message(response.getBody().toString()).build());
	}

	public ResponseEntity<Response> postVisitActivity(VisitActivityDto dto) {
		if (dto.getDocId() == null || ServicesUtil.isEmpty(dto.getDocId())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Response.builder().statusCode(HttpStatus.BAD_REQUEST).status(ResponseStatus.SUCCESS)
							.message("visit id is mandatory").build());
		}

		VisitActivities visitActivity = ObjectMapperUtils.map(dto, VisitActivities.class);
		visitActivity.setActivityId(ServicesUtil.randomId());
		visitActivitiesRepository.save(visitActivity);
		return ResponseEntity.status(HttpStatus.OK).body(Response.builder().statusCode(HttpStatus.OK)
				.status(ResponseStatus.SUCCESS).message("Activity Persisted.").build());
	}

	public ResponseEntity<Response> getVisitActivities(String visitId) {
		VisitActivitiesResponseDto resDto = new VisitActivitiesResponseDto();
		Optional<List<VisitActivities>> visitActivityOpt = visitActivitiesRepository.findByVisitId(visitId);
		List<SalesVisitAttachment> attachmentList = salesVisitAttachmentRepository.findByVisitId(visitId);
		SalesVisit visit = salesVisitRepository.findByVisitId(visitId);
		resDto.setVisitId(visitId);
		List<VisitActivities> visitActivityList = null;
		if (visitActivityOpt.isPresent()) {
			visitActivityList = visitActivityOpt.get();
			List<VisitActivityDto> salesOrderList = new ArrayList<>();
			List<VisitActivityDto> returnList = new ArrayList<>();
			List<VisitActivityDto> enqList = new ArrayList<>();
			List<VisitActivityDto> qotList = new ArrayList<>();
			List<SalesVisitAttachmentDto> attachList = new ArrayList<>();
			for (SalesVisitAttachment attach : attachmentList) {
				SalesVisitAttachmentDto attachDto = new SalesVisitAttachmentDto();
				attachDto.setAttachmentId(attach.getAttachmentId());
				attachDto.setAttachmentName(attach.getAttachmentName());
				attachDto.setAttachmentSize(attach.getAttachmentSize());
				attachDto.setAttachmentType(attach.getAttachmentType());
				attachDto.setData(attach.getData());
				attachDto.setUploadedBy(attach.getUploadedBy());
				attachDto.setUploadedOn(attach.getUploadedOn());
				attachList.add(attachDto);
			}
			for (VisitActivities activity : visitActivityList) {
				VisitActivityDto activityDto = new VisitActivityDto();
				activityDto.setDocStatus(activity.getDocStatus());
				activityDto.setDocId(activity.getDocId());
				activityDto.setDocAmount(activity.getDocAmount());
				activityDto.setDocCreatedAt(activity.getDocCreatedAt());
				activityDto.setActivityId(activity.getActivityId());
				if (activity.getActivityType().equals("Order")) {
					activityDto.setActivityType("Order");
					salesOrderList.add(activityDto);
				} else if (activity.getActivityType().equals("Return")) {
					activityDto.setActivityType("Return");
					returnList.add(activityDto);
				} else if (activity.getActivityType().equals("Enquiry")) {
					activityDto.setActivityType("Enquiry");
					enqList.add(activityDto);
				} else if (activity.getActivityType().equals("Quotation")) {
					activityDto.setActivityType("Quotation");
					qotList.add(activityDto);
				}
			}
			resDto.setSalesOrderList(salesOrderList);
			resDto.setReturnOrderList(returnList);
			resDto.setEnquiryList(enqList);
			resDto.setQuotationList(qotList);
			resDto.setAttachList(attachList);
		}

		return ResponseEntity.status(HttpStatus.OK).body(Response.builder().data(resDto).statusCode(HttpStatus.OK)
				.status(ResponseStatus.SUCCESS).message("Activity Persisted.").build());

	}

	public void notifySalesRepManager(String salesRepEmail, String visitId) {
		SalesRepToManager mapping = salesRepToManagerRepository.findBySalesRepEmail(salesRepEmail);
		String receiver = null;
		if (mapping != null) {
			List<String> toList = new ArrayList<>();
			receiver = mapping.getManagerEmail();
			toList.add(receiver.toLowerCase());
			List<String> ccList = new ArrayList<>();
			String title = "Visit " + visitId + " available for Approval";
			String message = "Sales Visit with id: " + visitId
					+ " is available and waiting for approval.\nPlease take the action.\n\n\n\nThanks\nCoM Team";
			ServicesUtils.mail(toList, ccList, "User", title, message);
		}
	}

	public void notifySalesRep(String salesRepEmail, String visitId, String status) {

		String receiver = null;
		receiver = salesRepEmail;
		List<String> toList = new ArrayList<>();
		toList.add(salesRepEmail.toLowerCase());
		List<String> ccList = new ArrayList<>();
		String title = "Visit " + visitId + " has been " + status + ".";
		String message = "Sales Visit with id: " + visitId + " has been " + status.toLowerCase()
				+ ".\nPlease proceed for the visit.\n\n\n\nThanks\nCoM Team";
		ServicesUtils.mail(toList, ccList, "User", title, message);
	}

	public void notifySalesRepAndManager(String salesRepEmail, String visitId) {

		List<String> toList = new ArrayList<>();
		List<String> ccList = new ArrayList<>();
		toList.add(salesRepEmail);
		String managerEMail = getSalesRepManager(salesRepEmail);
		if (managerEMail != null)
			toList.add(managerEMail.toLowerCase());
		String title = "Visit " + visitId + " has been Completed.";
		String message = "Sales Visit with id: " + visitId + " has been Completed.\n\n\n\nThanks\nCoM Team";
		ServicesUtils.mail(toList, ccList, "User", title, message);
	}

	public ResponseEntity<Response> uploadFile(SalesVisitAttachmentDto attachDto) {
		SalesVisitAttachment attachment = ObjectMapperUtils.map(attachDto, SalesVisitAttachment.class);
		if (attachment.getAttachmentId() == null || ServicesUtil.isEmpty(attachment.getAttachmentId())) {
			attachment.setAttachmentId(ServicesUtil.randomId());
			attachment.setVisitId(attachDto.getVisitId());
		}
		salesVisitAttachmentRepository.save(attachment);
		return ResponseEntity.status(HttpStatus.OK).body(Response.builder().data(attachDto).statusCode(HttpStatus.OK)
				.status(ResponseStatus.SUCCESS).message("Uploaded!").build());

	}

	public ResponseEntity<Response> deleteDoc(String attachmentId) {
		salesVisitAttachmentRepository.deleteById(attachmentId);
		return ResponseEntity.status(HttpStatus.OK).body(Response.builder().data(attachmentId).statusCode(HttpStatus.OK)
				.status(ResponseStatus.SUCCESS).message("Deleted!").build());

	}

}
