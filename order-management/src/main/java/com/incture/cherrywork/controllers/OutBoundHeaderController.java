package com.incture.cherrywork.controllers;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.ErrorDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.services.IOutBoundHeaderService;
import com.incture.cherrywork.services.ISalesOrderHeaderService;
import com.incture.cherrywork.services.SchedulerServices;
import com.incture.cherrywork.util.ServicesUtil;
import com.itextpdf.text.pdf.parser.clipper.Paths;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "OutBound Delivery Header", tags = { "Out Bound" })
@RequestMapping("/api/v1/OutBound")
public class OutBoundHeaderController {

	@Autowired
	private IOutBoundHeaderService outBoundHeaderService;

	@Autowired
	private SchedulerServices schedulerServices;

	@Autowired
	private ISalesOrderHeaderService salesOrderHeaderService;

	@PostMapping("/createObd")
	@ApiOperation(value = "Create Obd")
	public ResponseEntity<Object> createObd(@RequestBody SalesOrderHeaderItemDto dto) {
		return outBoundHeaderService.createObd(dto);
	}

	@PostMapping("/createPgi/{obdId}")
	@ApiOperation(value = "Create PGI")
	public ResponseEntity<Object> createPgi(@PathVariable String obdId) {
		return outBoundHeaderService.createPgi(obdId);
	}

	@PostMapping("/createInv/{pgiId}")
	@ApiOperation(value = "Create Invoice")
	public ResponseEntity<Object> createInv(@PathVariable String pgiId) {
		return outBoundHeaderService.createInv(pgiId);
	}

	@PostMapping("/headerScheduler")
	public ResponseEntity<Object> headerSch() {
		return schedulerServices.headerScheduler();
	}

	@PostMapping("/itemScheduler")
	public ResponseEntity<Object> itemSch() {
		return schedulerServices.itemScheduler();
	}

	@RequestMapping(value = "/viewInv/{invId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> viewInvoice(@PathVariable String invId) throws IOException {

		ResponseEntity<Object> res = outBoundHeaderService.getInvDetail(invId);
		if (res.getStatusCode().equals(HttpStatus.BAD_REQUEST))
			return ResponseEntity.badRequest().body(new InputStreamResource(null));
		ByteArrayInputStream bis = ServicesUtil.generatePdf((SalesOrderHeaderItemDto) res.getBody());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=Invoice_" + invId + ".pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));

	}

	@GetMapping("/downloadInv/{invId}")
	public void downloadInv(@PathVariable String invId, HttpServletResponse response) throws IOException {
		ResponseEntity<Object> res = outBoundHeaderService.getInvDetail(invId);
		if (res.getStatusCode().equals(HttpStatus.BAD_REQUEST))
			ResponseEntity.badRequest().body(new InputStreamResource(null));
		try {
			ByteArrayInputStream bis = ServicesUtil.generatePdf((SalesOrderHeaderItemDto) res.getBody());
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment;filename=Invoice_" + invId + ".pdf");
			IOUtils.copy(bis, response.getOutputStream());
			response.flushBuffer();
		} catch (Exception ex) {
			System.err
					.println("Error writing file to output stream. Filename was " + "Invoice_" + invId + ".pdf " + ex);
			throw new RuntimeException("IOError writing file to output stream");
		}

	}

	@PostMapping("/updateError")
	public String updateError(@RequestBody ErrorDto dto) {
		return salesOrderHeaderService.updateError(dto, "OBD");
	}

	@GetMapping("/mailInv/{invId}")
	public void mailService(@PathVariable String invId) {
		ResponseEntity<Object> res = outBoundHeaderService.getInvDetail(invId);
		if (res.getStatusCode().equals(HttpStatus.BAD_REQUEST))
			ResponseEntity.badRequest().body(new InputStreamResource(null));
		outBoundHeaderService.mailService((SalesOrderHeaderItemDto) res.getBody());
	}

	@GetMapping("/printInv/{invId}")
	public void printService(@PathVariable String invId) {
		ResponseEntity<Object> res = outBoundHeaderService.getInvDetail(invId);
		if (res.getStatusCode().equals(HttpStatus.BAD_REQUEST))
			ResponseEntity.badRequest().body(new InputStreamResource(null));
		outBoundHeaderService.printService((SalesOrderHeaderItemDto) res.getBody());
	}
	
	@GetMapping("/setStatusAsClosed/{obdId}")
	public int setStatusAsClosed(@PathVariable String obdId){
		return outBoundHeaderService.setStatusAsClosed(obdId);
	}
}

