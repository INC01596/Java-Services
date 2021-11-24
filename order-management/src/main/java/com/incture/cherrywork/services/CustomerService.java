package com.incture.cherrywork.services;



import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dao.BaseDao;
import com.incture.cherrywork.dtos.CustomerDto;
import com.incture.cherrywork.dtos.ResponseDto;
import com.incture.cherrywork.entities.CustomerDo;
import com.incture.cherrywork.repositories.CustomerRepo;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.repositories.ServicesUtils;
import com.incture.cherrywork.util.HciApiConstants;
import com.incture.cherrywork.util.RestInvoker;


@Service
@Transactional
public class CustomerService implements CustomerServiceLocal {

	@Autowired
	private CustomerRepo crepo;
	
	@Autowired
	private RestInvoker restInvoker;
	
	@Autowired
	private HciGetCustomerDetailsServiceLocal hciGetCustomerDetailsServiceLocal;
	
	
	
	@Override
	public ResponseEntity<Object> addCustomer(CustomerDto dto) {
		try
		{
			if(!ServicesUtils.isEmpty(dto))
			{
				return ResponseEntity.ok().body(crepo.save(ObjectMapperUtils.map(dto, CustomerDo.class)));
			}
			else
			{
				return null;
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		}

	@Override
	public ResponseDto saveAllCustomerFromRfcToHanaDb() {

		ResponseDto responseDto = new ResponseDto();

		try {

			/*List<String> shipToCustomerList = new ArrayList<>();
			List<String> primaryCustomerList = new ArrayList<>();*/

			List<CustomerDo> customerDoList = new ArrayList<>();
			
			List<String> customerList = new ArrayList<>();

			customerList.add("0000000046");
			customerList.add("0000000047");
			customerList.add("0000000056");
			customerList.add("0000000061");
			customerList.add("0000000062");
			customerList.add("0000000063");
			customerList.add("0000000064");
			customerList.add("0000000065");
			customerList.add("0000000076");
			customerList.add("0000000077");
			customerList.add("0000000078");

			/*logger.error("HANA---CustomerCount of primary  :" + primaryCustomerList.size());

			shipToCustomerList = dao.getAllDistictShipToCustomerList();

			logger.error("HANA---CustomerCount of shipTo customers :" + shipToCustomerList.size());

			if (shipToCustomerList != null && !shipToCustomerList.isEmpty()) {

				primaryCustomerList.addAll(shipToCustomerList);

			}

			// to get unique customers from two lists

			Set<String> customerSet = new HashSet<>(primaryCustomerList);
			List<String> customerList = new ArrayList<>(customerSet);*/

			//logger.error("HANA---CustomerCount for BulkUpload :" + customerList.size());

			if (customerList != null && !customerList.isEmpty()) {

				customerDoList = hciGetCustomerDetailsServiceLocal.getAllCustomerDetailsFromRFC(customerList);

				//logger.error("HANA---CustomerCount From RFC :" + customerDoList.size());

				if (customerDoList != null && !customerDoList.isEmpty()) {

					responseDto.setData(crepo.saveAll(customerDoList));

				}

			}

			responseDto.setStatus(Boolean.TRUE);
			responseDto.setMessage("SUCCESS");

		} catch (Exception e) {
			responseDto.setStatus(Boolean.FALSE);
			responseDto.setMessage(e.getMessage());
			//logger.error("[CustomerService][saveAllCustomerFromRfcToHanaDb]" + e.getMessage());
		}

		return responseDto;

	}

	@Override
	public void saveAllCustomerFromRfcToHanaDb(List<CustomerDo> customerDoList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getAllCustomer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerDto> getAllCustomerDetails(List<String> customerList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean getCrFlag(String customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllSoldCustomer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCustomer(CustomerDto customerDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCustomer(CustomerDto customerDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CustomerDto getCustomer(CustomerDto customerDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerDto> getAllCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerDo> getAllCustomerDetailsFromRFC(List<String> customerList) {
		// TODO Auto-generated method stub
		return null;
	}


		

	
}
