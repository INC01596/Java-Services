package com.incture.cherrywork.controllers;



import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.incture.cherrywork.dtos.BankNamesDto;
import com.incture.cherrywork.dtos.PendingInvoiceDto;
import com.incture.cherrywork.dtos.RejectionReasonDto;
import com.incture.cherrywork.dtos.ResponseDto;
import com.incture.cherrywork.dtos.TransactionDto;
import com.incture.cherrywork.services.HciInvoiceDetailServiceLocal;
import com.incture.cherrywork.services.InvoiceServicesLocal;
import com.incture.cherrywork.services.TransactionServicesLocal;


@RestController
@RequestMapping(value = "rest/invoice", produces = "application/json")
public class InvoiceDetailRestController {
	@Autowired
	private InvoiceServicesLocal invoiceService;
//
//	@Autowired
//	private EcmDocumentServiceLocal ecmDocumentService;

	@Autowired
	private HciInvoiceDetailServiceLocal services;

	@Autowired
	private TransactionServicesLocal transactionService;

//	@Autowired
//	private HciInvoicePrintDetailsServiceLocal hciPrintService;
//
//	@Autowired
//	private EbillingStatusServiceLocal ebillingStatusService;
//
//	@Autowired
//	private PrintBillingServiceLocal printService;
//
//	@Autowired
//	private AttahcmentServiceLocal attachmentService;
//
//	
//
//	@Autowired
//	private PrintInvoiceServiceLocal printInvoice;
//
	@Autowired
	private HciInvoiceDetailServiceLocal hciInvoiceService;
//	
//	@Autowired
//	private UserServiceLocal userServiceLocal;

	@RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
	public ResponseDto getInvoices(@PathVariable String customerId) {
		return services.getInvoices(customerId);
	}

	@RequestMapping(value = "/getInvoiceDetails/{invoiceNumber}", method = RequestMethod.GET)
	public ResponseDto getInvoiceDetails(@PathVariable String invoiceNumber) {
		return services.getInvoiceDetails(invoiceNumber);
	}

//	@RequestMapping(value = "/getTransaction/{transactionId}", method = RequestMethod.GET)
//	public ResponseDto getTransaction(@PathVariable String transactionId) {
//		return transactionService.getTransaction(transactionId);
//	}
//
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> createTransaction(@RequestBody TransactionDto dto) {
		return transactionService.createTransaction(dto);
	}
//
//	@RequestMapping(value = "/getTransactionsBySalesRepAndCustId/{salesRep}/{customerId}", method = RequestMethod.GET)
//	public ResponseDto getTransactionsBySalesRepAndCustId(@PathVariable String salesRep,
//			@PathVariable String customerId) {
//		return transactionService.getTransactionsBySalesRepAndCustId(salesRep, customerId);
//	}
//
//	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
//	public ResponseDto updateStatus(@RequestBody BillingApprovalDto dto) {
//		return ebillingStatusService.updateStatus(dto);
//	}
//
//	@RequestMapping(value = "/updateStatusBulk", method = RequestMethod.POST)
//	public ResponseDto updateStatusBulk(@RequestBody List<BillingApprovalDto> dtoList) {
//
//		ResponseDto responseDto = new ResponseDto();
//
//		responseDto.setStatus(Boolean.TRUE);
//
//		List<ResponseDto> list = new ArrayList<>();
//
//		for (BillingApprovalDto dto : dtoList) {
//			
//			
//
//			list.add(ebillingStatusService.updateStatus(dto));
//		}
//
//		for (ResponseDto dto : list) {
//
//			if (!dto.isStatus()) {
//				responseDto.setStatus(Boolean.FALSE);
//				break;
//			}
//		}
//
//		return responseDto;
//	}
//
//	@RequestMapping(value = "/getTrackingDetails/{transactionId}", method = RequestMethod.GET)
//	public ResponseDto getTrackingDetails(@PathVariable String transactionId) {
//		return ebillingStatusService.getTrackingDetails(transactionId);
//	}
//
//	@RequestMapping(value = "/getPendingApprovals/{pendingWith}", method = RequestMethod.GET)
//	public ResponseDto getPendingApprovals(@PathVariable String pendingWith) {
//		pendingWith= "AccountExecutive";
//		return ebillingStatusService.getPendingApprovals(pendingWith);
//	}
//
//	@RequestMapping(value = "/print", method = RequestMethod.POST)
//	public ResponseDto getPrintSO(@RequestBody BillingPrintDto dto) throws FileNotFoundException {
//		return printService.printCollection(dto);
//	}
//
//	@RequestMapping(value = "/getAttachments/{transactionId}", method = RequestMethod.GET)
//	public ResponseDto getAttacments(@PathVariable String transactionId) {
//		return attachmentService.getAttachmentByTransactionId((transactionId));
//	}
//
//	@RequestMapping(value = "/getBankNames", method = RequestMethod.GET)
//	public ResponseDto getListOfBankNames() {
//		return invoiceService.getListOfBankNames();
//	}
//
//	@RequestMapping(value = "/getBankDetails", method = RequestMethod.GET)
//	public ResponseDto getBankDetails() {
//		return invoiceService.getBankDetails();
//	}
//
//	@RequestMapping(value = "/saveBankNames", method = RequestMethod.POST)
//	public ResponseDto saveBankNames(@RequestBody BankNamesDto dto) {
//		return invoiceService.saveBank(dto);
//	}
//
//	@RequestMapping(value = "/deleteBankNames", method = RequestMethod.POST)
//	public ResponseDto deleteBankNames(@RequestBody BankNamesDto dto) {
//		return invoiceService.deleteBank(dto);
//	}
//
//	@RequestMapping(value = "/rejectionReason", method = RequestMethod.GET)
//	public ResponseDto getListOfReasonCode() {
//		return invoiceService.getListOfReasonCode();
//	}
//
//	@RequestMapping(value = "/saveRejectionReason", method = RequestMethod.POST)
//	public ResponseDto saveRejectionReason(@RequestBody RejectionReasonDto dto) {
//		return invoiceService.saveRejectionReason(dto);
//	}
//
//	@RequestMapping(value = "/deleteRejectionReason", method = RequestMethod.POST)
//	public ResponseDto deleteRejectionReason(@RequestBody RejectionReasonDto dto) {
//		return invoiceService.deleteRejectionReason(dto);
//	}

//	@RequestMapping(path = "/downloadAttachment/{id}", method = RequestMethod.GET)
//	public ResponseEntity<Resource> download(@PathVariable String id) throws IOException {
//
////		Document document = ecmDocumentService.getAttachmentById(id);
//		Document document = null;S
//		InputStreamResource resource = new InputStreamResource(document.getContentStream().getStream());
//		HttpHeaders header = new HttpHeaders();
//		header.set(HttpHeaders.CONTENT_DISPOSITION,
//				"attachment; filename=" + document.getContentStream().getFileName());
//		header.setContentLength(document.getContentStream().getLength());
//		return ResponseEntity.ok().headers(header)
//				.contentType(MediaType.parseMediaType(document.getContentStream().getMimeType())).body(resource);
//	}

//	@RequestMapping(path = "/downloadBilling", method = RequestMethod.POST)
//	public ResponseEntity<Resource> download(@RequestBody BillingPrintDto dto) throws IOException {
//
//		File file = (File) printService.printCollection(dto).getData();
//		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//		HttpHeaders header = new HttpHeaders();
//		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
//		header.setContentLength(file.length());
//		return ResponseEntity.ok().headers(header).contentType(MediaType.parseMediaType("application/pdf"))
//				.body(resource);
//	}
	
	
//	@RequestMapping(path = "/download-billing-new/{id}", method = RequestMethod.GET)
//	public ResponseEntity<Resource> downloadNew(@PathVariable String id) throws IOException {
//		BillingPrintDto dto = new BillingPrintDto();
//		dto.setTransactionId(id);
//		File file = (File) printService.printCollection(dto).getData();
//		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//		HttpHeaders header = new HttpHeaders();
//		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
//		header.setContentLength(file.length());
//		return ResponseEntity.ok().headers(header).contentType(MediaType.parseMediaType("application/pdf"))
//				.body(resource);
//	}

//	@RequestMapping(path = "/downloadInvoice", method = RequestMethod.POST)
//	public ResponseEntity<Resource> downloadInvoice(@RequestBody BillingPrintDto dto) throws IOException {
//
//		File file = (File) printInvoice.printOpenInvoice(dto).getData();
//		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//		HttpHeaders header = new HttpHeaders();
//		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
//		header.setContentLength(file.length());
//		return ResponseEntity.ok().headers(header).contentType(MediaType.parseMediaType("application/pdf"))
//				.body(resource);
//	}

	@RequestMapping(value = "/getAllOpenInvoices", method = RequestMethod.GET)
	public List<PendingInvoiceDto> getAllopenInvoices() {
		return hciInvoiceService.getAllOpenInvoices();
	}

	@RequestMapping(value = "/saveAllOpenInvoicesToHana", method = RequestMethod.GET)
	public ResponseDto saveAllopenInvoices() {
		return invoiceService.savePendingInvoices(); 
	}

//	@RequestMapping(path = "/printECCCollection/{salesRep}/{customerNumber}", method = RequestMethod.GET)
//	public ResponseEntity<Resource> printECCCollection(@PathVariable String salesRep,
//			@PathVariable String customerNumber) throws IOException {
//
//		File file = (File) hciPrintService.printECCCollection(salesRep, customerNumber).getData();
//		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//		HttpHeaders header = new HttpHeaders();
//		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
//		header.setContentLength(file.length());
//		return ResponseEntity.ok().headers(header).contentType(MediaType.parseMediaType("application/pdf"))
//				.body(resource);
//	}

	//CR
//	@RequestMapping(path = "/printAggingReport/{customerNumber}", method = RequestMethod.GET)
//	public ResponseEntity<Resource> printAgingreport(@PathVariable String customerNumber) throws IOException {
//
//		String createdBy=null;
//		File file = (File) hciPrintService.printECCCollection(createdBy, customerNumber).getData();
//		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//		HttpHeaders header = new HttpHeaders();
//		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
//		header.setContentLength(file.length());
//		return ResponseEntity.ok().headers(header).contentType(MediaType.parseMediaType("application/pdf"))
//				.body(resource);
//	}
}
