package com.incture.cherrywork.services;

import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.Response;
import com.incture.cherrywork.dtos.VisitPlanDto;

public interface ISalesVisitPlannerService {

	public ResponseEntity<Response> createVisit(VisitPlanDto dto);

	public ResponseEntity<Response> updateVisit(VisitPlanDto dto);

	public ResponseEntity<Response> deleteVisit(String visitId);

	public ResponseEntity<Response> getAllVisit();

	public ResponseEntity<Response> getVisitById(String visitId);

}
