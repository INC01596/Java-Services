package com.incture.cherrywork.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.ApproveVisitDto;
import com.incture.cherrywork.dtos.InboxVisitListDto;
import com.incture.cherrywork.dtos.Response;
import com.incture.cherrywork.dtos.SalesVisitAttachmentDto;
import com.incture.cherrywork.dtos.SalesVisitFilterDto;
import com.incture.cherrywork.dtos.VisitActivityDto;
import com.incture.cherrywork.dtos.VisitPlanDto;
import com.incture.cherrywork.entities.Attachment;
import com.incture.cherrywork.entities.SalesVisitAttachment;
import com.incture.cherrywork.repositories.ISalesVisitAttachmentRepository;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.services.ISalesVisitPlannerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "SalesVisitPlannerController", tags = { "Sales Visit Planner Controller" })
@RequestMapping("/api/v1/salesVisitPlanner")
public class SalesVisitPlannerController {

	@Autowired
	private ISalesVisitPlannerService salesVisitPlannerService;

	@Autowired
	private ISalesVisitAttachmentRepository salesVisitAttachmentRepository;

	@PostMapping("/createVisit")
	@ApiOperation(value = "Create a visit Dataset")
	public ResponseEntity<Response> create(@RequestBody VisitPlanDto dto) {
		return salesVisitPlannerService.createVisit(dto);
	}

	@GetMapping("/getVisitById/{visitId}/")
	@ApiOperation(value = "Read Visit Dataset")
	public ResponseEntity<Response> read(@PathVariable String visitId) {
		return salesVisitPlannerService.getVisitById(visitId);
	}

	@PutMapping("/updateVisit")
	@ApiOperation(value = "Update Visit Dataset")
	public ResponseEntity<Response> update(@RequestBody VisitPlanDto dto) {
		return salesVisitPlannerService.updateVisit(dto);
	}

	@DeleteMapping("/deletVisit/{visitId}")
	@ApiOperation(value = "Delete Visit Dataset")
	public ResponseEntity<Response> delete(@PathVariable String visitId) {
		return salesVisitPlannerService.deleteVisit(visitId);
	}

	@GetMapping("/getAllVisit")
	@ApiOperation(value = "List all Visists ")
	public ResponseEntity<Response> readAll() {
		return salesVisitPlannerService.getAllVisit();
	}

	@PostMapping("/filterVisit")
	@ApiOperation(value = "Filter the visists on various param")
	public ResponseEntity<Response> filter(@RequestBody SalesVisitFilterDto dto) {

		return salesVisitPlannerService.filter(dto);

	}

	@PostMapping(path = "/updateVisitWfTaskStatus", produces = "application/json")
	public ResponseEntity<Response> updateVisitWfTaskStatus(@RequestBody VisitPlanDto dto)
			throws UnsupportedOperationException, IOException, URISyntaxException {

		return salesVisitPlannerService.updateVisitWfTaskStatus(dto);

	}

	@PostMapping("/getInboxTaskList")
	@ApiOperation(value = "Provide list of visit available for manager to approve")
	public ResponseEntity<Response> getTaskListInInbox(@RequestBody InboxVisitListDto dto) {
		return salesVisitPlannerService.getTaskListInInbox(dto);
	}

	@PostMapping("/submitTask")
	@ApiOperation(value = "Approve or reject thetask.")
	public ResponseEntity<Response> submitTask(@RequestBody ApproveVisitDto dto) {
		return salesVisitPlannerService.submitTask(dto);
	}

	@PostMapping("/postVisitActivity")
	@ApiOperation(value = "Persist activity on a visit")
	public ResponseEntity<Response> postVisitActivity(@RequestBody VisitActivityDto dto) {
		return salesVisitPlannerService.postVisitActivity(dto);
	}

	@GetMapping("/getVisitActivities/{visitId}")
	@ApiOperation(value = "List all Visist Activities")
	public ResponseEntity<Response> getVisitActivities(@PathVariable String visitId) {
		return salesVisitPlannerService.getVisitActivities(visitId);
	}

	@GetMapping("/notifySalesRepAndMan/{salesRepEmail}/{visitId}")
	public ResponseEntity<Response> notifySalesRepAndMan(@PathVariable String salesRepEmail,
			@PathVariable String visitId) {
		salesVisitPlannerService.notifySalesRepAndManager(salesRepEmail, visitId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(Response.builder().statusCode(HttpStatus.OK).status(ResponseStatus.SUCCESS).build());

	}

	@GetMapping("/downloadFile/{attachmentId}")
	@ApiOperation(value = "Will download a single file by id")
	public ResponseEntity<Resource> downloadFile(@PathVariable(name = "attachmentId") String attachmentId) {
		// Load file from database
		SalesVisitAttachment dbFile = salesVisitAttachmentRepository.findByAttachmentId(attachmentId);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getAttachmentName() + "\"")
				.body(new ByteArrayResource(dbFile.getData()));
	}

	@GetMapping(value = "/downloadFileByVititId/{visitId}", produces = "application/zip")
	@ApiOperation(value = "downlod attachments in a zip")
	public byte[] downloadFileByReturnReqNum(@PathVariable(name = "visitId") String visitId) throws IOException {
		// Load file from database
		List<SalesVisitAttachment> attachList = salesVisitAttachmentRepository.findByVisitId(visitId);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		for (int i = 0; i < attachList.size(); i++) {
			ZipEntry entry = new ZipEntry(visitId + "-" + attachList.get(i).getAttachmentName());
			entry.setSize(attachList.get(i).getData().length);
			zos.putNextEntry(entry);
			zos.write(attachList.get(i).getData());
		}
		zos.closeEntry();
		zos.close();
		return baos.toByteArray();

	}

	@PostMapping("/uploadFile")
	@ApiOperation(value = "upload a single file")
	public ResponseEntity<Response> uploadFile(@RequestBody SalesVisitAttachmentDto attachDto) {
		return salesVisitPlannerService.uploadFile(attachDto);
	}

	@DeleteMapping("/deleteByAttachmentId/{attachmentId}")
	@ApiOperation(value = "upload a single file")
	public ResponseEntity<Response> deleteDoc(@PathVariable String attachmentId) {
		return salesVisitPlannerService.deleteDoc(attachmentId);
	}

}
