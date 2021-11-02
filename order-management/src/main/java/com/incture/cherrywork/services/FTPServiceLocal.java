package com.incture.cherrywork.services;




import java.util.List;

import com.incture.cherrywork.dtos.ResponseDto;



public interface FTPServiceLocal {

	ResponseDto getAllDetailsBySalesRep(String salesRep);

	//List<ExcelRowDataDto> getAllDetailsBySalesRepAndCustomerId(String salesRep, String customerId);

	ResponseDto getAllCustDetailsBySalesRep(String salesRep);

	ResponseDto getAllCustomerDetailsWithInvoices(String salesRep);

}
