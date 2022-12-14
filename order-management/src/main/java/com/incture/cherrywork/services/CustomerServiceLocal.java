package com.incture.cherrywork.services;



import java.util.List;

import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.CustomerDto;
import com.incture.cherrywork.dtos.ResponseDto;
import com.incture.cherrywork.entities.CustomerDo;



public interface CustomerServiceLocal {

	public void updateCustomer(CustomerDto customerDto);

	public void deleteCustomer(CustomerDto customerDto);

	public CustomerDto getCustomer(CustomerDto customerDto);

	public List<CustomerDto> getAllCustomers();

	public void saveAllCustomerFromRfcToHanaDb(List<CustomerDo> customerDoList);

	public List<String> getAllCustomer();

	public List<CustomerDto> getAllCustomerDetails(List<String> customerList);

	//public List<String> getAllDistictShipToCustomerList();

	public Boolean getCrFlag(String customerId);

	public List<String> getAllSoldCustomer();

	public ResponseEntity<Object> addCustomer(CustomerDto dto);

	List<CustomerDo> getAllCustomerDetailsFromRFC(List<String> customerList);

	ResponseDto saveAllCustomerFromRfcToHanaDb();

}