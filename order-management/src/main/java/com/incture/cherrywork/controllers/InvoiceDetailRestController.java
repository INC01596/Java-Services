package com.incture.cherrywork.controllers;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.incture.cherrywork.dtos.BankNamesDto;
import com.incture.cherrywork.dtos.BillingApprovalDto;
import com.incture.cherrywork.dtos.CustomerDto;
import com.incture.cherrywork.dtos.PendingInvoiceDto;
import com.incture.cherrywork.dtos.RejectionReasonDto;
import com.incture.cherrywork.dtos.ResponseDto;
import com.incture.cherrywork.dtos.TransactionDto;
import com.incture.cherrywork.services.CustomerServiceLocal;
import com.incture.cherrywork.services.EbillingStatusServiceLocal;
import com.incture.cherrywork.services.HciInvoiceDetailServiceLocal;
import com.incture.cherrywork.services.InvoiceServicesLocal;
import com.incture.cherrywork.services.TransactionServicesLocal;


@RestController
@RequestMapping(value = "api/v1/invoice", produces = "application/json")
public class InvoiceDetailRestController {
	
	
	
	@Autowired
	private InvoiceServicesLocal invoiceService;
	
	@Autowired
	private CustomerServiceLocal custService;

	@Autowired
	private HciInvoiceDetailServiceLocal services;

	@Autowired
	private TransactionServicesLocal transactionService;

    @Autowired
	private EbillingStatusServiceLocal ebillingStatusService;

    @Autowired
	private HciInvoiceDetailServiceLocal hciInvoiceService;

	@RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
	public ResponseDto getInvoices(@PathVariable String customerId) {
		return services.getInvoices(customerId);
	}

	@RequestMapping(value = "/getInvoiceDetails/{invoiceNumber}", method = RequestMethod.GET)
	public ResponseDto getInvoiceDetails(@PathVariable String invoiceNumber) {
		return services.getInvoiceDetails(invoiceNumber);
	}


	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> createTransaction(@RequestBody TransactionDto dto) {
		System.err.println("heyController");
		return transactionService.createTransaction(dto);
	}
	
	@RequestMapping("/addCustomer")
	public ResponseEntity<Object>addCustomer(@RequestBody CustomerDto dto)
	{
		return custService.addCustomer(dto);
	}


	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	public ResponseDto updateStatus(@RequestBody BillingApprovalDto dto) {
		return ebillingStatusService.updateStatus(dto);
	}

	@RequestMapping(value = "/updateStatusBulk", method = RequestMethod.POST)
	public ResponseDto updateStatusBulk(@RequestBody List<BillingApprovalDto> dtoList) {
        System.err.println("dtoList "+dtoList);
		ResponseDto responseDto = new ResponseDto();

		responseDto.setStatus(Boolean.TRUE);

		List<ResponseDto> list = new ArrayList<>();

		for (BillingApprovalDto dto : dtoList) {
			
			System.err.println("dto "+dto);

			list.add(ebillingStatusService.updateStatus(dto));
		}

		for (ResponseDto dto : list) {

			if (!dto.isStatus()) {
				responseDto.setStatus(Boolean.FALSE);
				break;
			}
		}

		return responseDto;
	}

	@RequestMapping(value = "/getTrackingDetails/{transactionId}", method = RequestMethod.GET)
	public ResponseDto getTrackingDetails(@PathVariable String transactionId) {
		return ebillingStatusService.getTrackingDetails(transactionId);
	}

	@RequestMapping(value = "/getPendingApprovals/{pendingWith}", method = RequestMethod.GET)
	public ResponseDto getPendingApprovals(@PathVariable String pendingWith) {
	
		return ebillingStatusService.getPendingApprovals(pendingWith);
	}


	@RequestMapping(value = "/getBankDetails", method = RequestMethod.GET)
	public ResponseEntity<Object> getBankDetails() {
		return invoiceService.getBankDetails();
	}

	@RequestMapping(value = "/saveBankNames", method = RequestMethod.POST)
	public ResponseEntity<Object> saveBankNames(@RequestBody BankNamesDto dto) {
		System.err.println("hey "+dto);
		return invoiceService.saveBank(dto);
	}


	@RequestMapping(value = "/rejectionReason", method = RequestMethod.GET)
    public ResponseEntity<Object> getListOfReasonCode() {
		return invoiceService.getListOfReasonCode();
	}

	
	@RequestMapping(value = "/saveRejectionReason", method = RequestMethod.POST)
	 public ResponseEntity<Object> saveRejectionReason(@RequestBody RejectionReasonDto dto) {
		return invoiceService.saveRejectionReason(dto);
	}

	@RequestMapping(value = "/deleteRejectionReason", method = RequestMethod.POST)
	public ResponseEntity<Object> deleteRejectionReason(@RequestBody RejectionReasonDto dto) {

		return invoiceService.deleteRejectionReason(dto);
	}
    
	@RequestMapping(value = "/getAllOpenInvoices", method = RequestMethod.GET)
	public List<PendingInvoiceDto> getAllopenInvoices() {
		return hciInvoiceService.getAllOpenInvoices();
	}

	@RequestMapping(value = "/saveAllOpenInvoicesToHana", method = RequestMethod.GET)
	public ResponseDto saveAllopenInvoices() {
		return invoiceService.savePendingInvoices(); 
	}


}
