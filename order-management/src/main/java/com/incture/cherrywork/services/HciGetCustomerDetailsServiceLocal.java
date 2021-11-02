package com.incture.cherrywork.services;



import java.util.List;
import java.util.Map;

import com.incture.cherrywork.dtos.CreditLimitDto;
import com.incture.cherrywork.dtos.CustomerDto;
import com.incture.cherrywork.dtos.PaymentTermsDto;
import com.incture.cherrywork.entities.CustomerDo;



public interface HciGetCustomerDetailsServiceLocal {

	CustomerDto getCustomerDetailsFromEcc(String customerName, String creditSegment);

	//public List<CustomerDto> getCustomerAlldetails(String country);

	PaymentTermsDto getCustomerPaymentTerms(String companyCode, String customerNumber);

	/*List<ShippingAddressDto> getShippingDetailsByCustId(String customerId);*/

	List<CustomerDo> getAllCustomerDetailsFromRFC(List<String> customerList);

	Map<String, CreditLimitDto> getCreditLimitsOfCutomerList(Map<String, String> customerList);

	List<PaymentTermsDto> getCustomerPaymentTermsFromRFC(Map<String, String> companyMap);

}
