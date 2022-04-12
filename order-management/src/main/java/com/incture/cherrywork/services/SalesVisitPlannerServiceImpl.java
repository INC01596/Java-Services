package com.incture.cherrywork.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.Response;
import com.incture.cherrywork.dtos.VisitPlanDto;
import com.incture.cherrywork.entities.SalesVisit;
import com.incture.cherrywork.repositories.ISalesVisitPlannerRepository;
import com.incture.cherrywork.repositories.JdbcRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.sales.constants.ResponseStatus;

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
		visitId.append("D-");
		visitId.append(dto.getCustCode());
		visitId.append(monthYear.format(dateNow));
		visitId.append(tempVisitId);
		dto.setVisitId(visitId.toString());
		dto.setVisitCretedAt(dateNow);
		SalesVisit visit = ObjectMapperUtils.map(dto, SalesVisit.class);
		salesVisitRepository.save(visit);
		return ResponseEntity.status(HttpStatus.OK)
				.header("message", "Visit created and task triggered for further level approval.").body(null);

	}

	public ResponseEntity<Response> updateVisit(VisitPlanDto dto) {

		logger.info("[SalesVisitPlannerServiceImpl][createVisit] VisitId: " + dto.getVisitId());
		SalesVisit visit = ObjectMapperUtils.map(dto, SalesVisit.class);
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

}
