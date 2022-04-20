package com.incture.cherrywork.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.CustomerAddressDto;
import com.incture.cherrywork.dtos.CustomerContactDto;
import com.incture.cherrywork.dtos.Response;
import com.incture.cherrywork.dtos.SalesVisitAttachmentDto;
import com.incture.cherrywork.dtos.SalesVisitFilterDto;
import com.incture.cherrywork.dtos.VisitPlanDto;
import com.incture.cherrywork.entities.CustomerAddress;
import com.incture.cherrywork.entities.CustomerContact;
import com.incture.cherrywork.entities.SalesVisit;
import com.incture.cherrywork.entities.SalesVisitAttachment;
import com.incture.cherrywork.repositories.ISalesVisitPlannerRepository;
import com.incture.cherrywork.repositories.JdbcRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.ServicesUtil;

@Service("SalesVisitPlannerServiceImpl")
@Transactional
public class SalesVisitPlannerServiceImpl implements ISalesVisitPlannerService {

	@Autowired
	private ISalesVisitPlannerRepository salesVisitRepository;

	@PersistenceContext
	private EntityManager entityManager;

	Logger logger = LoggerFactory.getLogger(SalesVisitPlannerServiceImpl.class);

	@Autowired
	JdbcRepository jdbcRepository;

	public ResponseEntity<Response> createVisit(VisitPlanDto dto) {

		Long tempVisitId = jdbcRepository.getVisitSequence();
		StringBuilder visitId = new StringBuilder();
		SimpleDateFormat monthYear = new SimpleDateFormat("MMyy");
		Date dateNow = new Date();
		logger.info("[SalesVisitPlannerServiceImpl][createVisit] tempVisitId: " + tempVisitId);
		SalesVisit visit = ObjectMapperUtils.map(dto, SalesVisit.class);
		visitId.append("D-");
		visitId.append(dto.getCustCode());
		visitId.append(monthYear.format(dateNow));
		visitId.append(tempVisitId);
		visit.setVisitId(visitId.toString());
		visit.setVisitCretedAt(new Date());

		// logger.info("[SalesVisitPlannerServiceImpl][createVisit] visit: " +
		// visit);
		visit.setAttachment(processAttachment(dto, visit));
		// logger.info("[SalesVisitPlannerServiceImpl][createVisit] attachment:
		// " + visit.getAttachment());
		visit.setCustAddress(processCustomerAddress(dto, visit));
		// logger.info("[SalesVisitPlannerServiceImpl][createVisit] address: " +
		// visit.getCustAddress());
		visit.setCustomerContact(processCustomerContact(dto, visit));
		// logger.info("[SalesVisitPlannerServiceImpl][createVisit] contact: " +
		// visit.getCustomerContact());
		// logger.info("[SalesVisitPlannerServiceImpl][createVisit] visit after:
		// " + visit);
		salesVisitRepository.save(visit);
		return ResponseEntity.status(HttpStatus.OK)
				.header("message", "Visit created and task triggered for further level approval.").body(null);

	}

	public List<SalesVisitAttachment> processAttachment(VisitPlanDto visit, SalesVisit visitDo) {
		List<SalesVisitAttachment> attachList = new ArrayList<>();

		for (SalesVisitAttachmentDto attachDto : visit.getAttachment()) {
			SalesVisitAttachment attach = ObjectMapperUtils.map(attachDto, SalesVisitAttachment.class);
			// SalesVisitAttachmentPk key = new
			// SalesVisitAttachmentPk(ServicesUtil.randomId(), visitDo);
			// attach.setSalesVisitAttachmentKey(key);
			attach.setSalesVisit(visitDo);
			if (attach.getAttachmentId() == null || ServicesUtil.isEmpty(attach.getAttachmentId()))
				attach.setAttachmentId(ServicesUtil.randomId());
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
			if (contact.getCustomerContactId() == null || ServicesUtil.isEmpty(contact.getCustomerContactId()))
				contact.setCustomerContactId(ServicesUtil.randomId());
			contactList.add(contact);
		}
		return contactList;

	}

	public ResponseEntity<Response> updateVisit(VisitPlanDto dto) {

		logger.info("[SalesVisitPlannerServiceImpl][createVisit] VisitId: " + dto.getVisitId());
		SalesVisit visit = ObjectMapperUtils.map(dto, SalesVisit.class);
		visit.setAttachment(processAttachment(dto, visit));
		visit.setCustAddress(processCustomerAddress(dto, visit));
		visit.setCustomerContact(processCustomerContact(dto, visit));
		if (visit.getVisitId() == null || ServicesUtil.isEmpty(visit.getVisitId())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Response.builder().data(dto).statusCode(HttpStatus.BAD_REQUEST).status(ResponseStatus.FAILED)
							.message("Visit Id is required").build());

		}
		salesVisitRepository.save(visit);

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

}
