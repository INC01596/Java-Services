package com.incture.cherrywork.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.incture.cherrywork.dtos.ApproveVisitDto;
import com.incture.cherrywork.dtos.InboxVisitListDto;
import com.incture.cherrywork.dtos.Response;
import com.incture.cherrywork.dtos.SalesVisitAttachmentDto;
import com.incture.cherrywork.dtos.SalesVisitFilterDto;
import com.incture.cherrywork.dtos.VisitActivityDto;
import com.incture.cherrywork.dtos.VisitPlanDto;

public interface ISalesVisitPlannerService {

	public ResponseEntity<Response> createVisit(VisitPlanDto dto);

	public ResponseEntity<Response> updateVisit(VisitPlanDto dto);

	public ResponseEntity<Response> deleteVisit(String visitId);

	public ResponseEntity<Response> getAllVisit();

	public ResponseEntity<Response> getVisitById(String visitId);

	public ResponseEntity<Response> filter(SalesVisitFilterDto filteDto);

	public ResponseEntity<Response> updateVisitWfTaskStatus(VisitPlanDto dto);

	public ResponseEntity<Response> getTaskListInInbox(InboxVisitListDto dto);

	public ResponseEntity<Response> submitTask(ApproveVisitDto dto);

	public ResponseEntity<Response> postVisitActivity(VisitActivityDto dto);

	public ResponseEntity<Response> getVisitActivities(String visitId);

	public void notifySalesRepAndManager(String salesRepEmail, String visitId);

	public ResponseEntity<Response> uploadFile(SalesVisitAttachmentDto attachDto);
	
	public ResponseEntity<Response> deleteDoc(String attachmentId);

}
