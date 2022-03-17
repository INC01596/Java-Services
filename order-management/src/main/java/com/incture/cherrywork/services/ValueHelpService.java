package com.incture.cherrywork.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dao.SalesManCustRelDaoLocal;
import com.incture.cherrywork.dtos.CreditLimitDto;
import com.incture.cherrywork.dtos.CustomerDto;
import com.incture.cherrywork.dtos.PaymentTermDo;
import com.incture.cherrywork.dtos.PaymentTermsDto;
import com.incture.cherrywork.dtos.PendingInvoiceDto;
import com.incture.cherrywork.dtos.ResponseDto;
import com.incture.cherrywork.dtos.SalesRepCustDto;
import com.incture.cherrywork.entities.CustomerDo;
import com.incture.cherrywork.repositories.CustomerRepo;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.util.ServicesUtil;

@Service
@Transactional
public class  ValueHelpService implements FTPServiceLocal {
	
//	@Autowired
//	private CustomerDao customerDao;
//	
	@Autowired 
	private CustomerRepo crepo;
	
	@Autowired
	private SalesManCustRelDaoLocal salesManCusRelDao;
	
//	@Autowired
//	private CustomerDaoLocal customerDaoLocal;
	
	@Autowired
	private HciGetCustomerDetailsServiceLocal hciGetCustomerDetails;

	@Autowired
	private PendingInvoiceDaoLocal pendingInvoiceDaoLocal;

	@Autowired
	private PaymentTermDaoLocal paymentTermDaoLocal;
	
	
	
	public ResponseDto f(String salesRep) {

		List<CustomerDto> resultList = new ArrayList<>();

		CustomerDto customerDto;
		CustomerDo customerDo;
		ResponseDto responseDto = new ResponseDto();

		List<SalesRepCustDto> salesRepCustList = new ArrayList<>();

		try {
			responseDto.setStatus(true);
			responseDto.setMessage("Message.SUCCESS.toString()");

			salesRepCustList = salesManCusRelDao.getCustList(salesRep);

			for (SalesRepCustDto s : salesRepCustList) {

				// getting row from sheet based on row number

				if (!ServicesUtil.isEmpty(s.getType())) {

					if (s.getType().equalsIgnoreCase("Secondary")) {

						// SecondaryCustomer from Excel

						customerDto = new CustomerDto();

						customerDto.setCustCategory(s.getCategory());
						customerDto.setCustName(s.getCust_name());
						customerDto.setCustPostalCode(s.getRegion_code());
						customerDto.setCustCity(s.getArea_name());
						customerDto.setCustId(s.getCust_code());
						customerDto.setIsPrimary(false);
						resultList.add(customerDto);

					} else {

						// PrimaryCustomer from Excel

						customerDo = new CustomerDo();
						customerDto = new CustomerDto();
						String customerId = "";
//						c
//						String query = "from CustomerDo where custId=:custId";
//
//						Query q = customerDao.getSession().createQuery(query);
//						q.setParameter("custId", s.getCust_code());
//
//						// customerDto=customerDaoLocal.getCustomerDetailsByCustId(dataFormatter.formatCellValue(record.getCell(9)),
//						// salesRep);
//
//						customerDo = (CustomerDo) q.uniqueResult();

						customerDto =ObjectMapperUtils.map(crepo.findById(s.getCust_code()),CustomerDto.class);
						customerDto.setIsPrimary(true);
						customerId = s.getCust_code();
						customerDto.setCustCreditLimit(hciGetCustomerDetails
								.getCustomerDetailsFromEcc(customerId, "1202").getCustCreditLimit());
						resultList.add(customerDto);
					}

				}

			}

		} catch (Exception e) {

			//logger.error("[ValueHelpService][getAllDetailsBySalesRep]:::" + e.getMessage());
			responseDto.setStatus(false);
			responseDto.setMessage("Message.FAILED.");

		}

		responseDto.setData(resultList);
		return responseDto;

	}

//	@Override
//	public List<ExcelRowDataDto> getAllDetailsBySalesRepAndCustomerId(String salesRep, String customerId) {
//
//		List<ExcelRowDataDto> resultList = new ArrayList<>();
//		ExcelRowDataDto dto;
//		ResponseDto responseDto = new ResponseDto();
//
//		try {
//			responseDto.setStatus(true);
//			responseDto.setMessage(Message.SUCCESS.toString());
//
//			XSSFSheet sheet = getSheet();
//			DataFormatter dataFormatter = new DataFormatter();
//			int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
//
//			for (int i = 1; i <= rowCount; i++) {
//
//				// getting row from sheet based on row number
//				Row record = sheet.getRow(i);
//
//				if (dataFormatter.formatCellValue(record.getCell(0)).equals(salesRep)
//						&& dataFormatter.formatCellValue(record.getCell(9)).equals(customerId)) {
//
//					// adding data to dto from condition matched row
//					dto = new ExcelRowDataDto();
//					dto.setSalesRep(dataFormatter.formatCellValue(record.getCell(0)));
//					// dto.setSalesManager(dataFormatter.formatCellValue(record.getCell(1)));
//					dto.setCustomerName(dataFormatter.formatCellValue(record.getCell(2)));
//					// dto.setCustomerType(dataFormatter.formatCellValue(record.getCell(3)));
//					dto.setCustomerAddress(dataFormatter.formatCellValue(record.getCell(4)));
//
//					// dto.setPrincipalCode(dataFormatter.formatCellValue(record.getCell(5)));
//					// dto.setTerritory(dataFormatter.formatCellValue(record.getCell(6)));
//					// dto.setArea(dataFormatter.formatCellValue(record.getCell(7)));
//					// dto.setCustomerCategory(dataFormatter.formatCellValue(record.getCell(8)));
//					resultList.add(dto);
//				}
//
//			}
//
//		} catch (Exception e) {
//
//			logger.error("[ValueHelpService][getAllDetailsBySalesRepAndCustomerId]:::" + e.getMessage());
//			responseDto.setStatus(false);
//			responseDto.setMessage(Message.FAILED.toString());
//
//		}
//
//		responseDto.setData(resultList);
//		return resultList;
//
//	}
//
	@Override
	public ResponseDto getAllCustomerDetailsWithInvoices(String salesRep) {
System.err.println("Hey Service1");
		ResponseDto responseDto = new ResponseDto();

		List<CustomerDto> customerDtoList = new ArrayList<>();

		List<CustomerDto> resultCustomerDto = new ArrayList<>();

		List<PendingInvoiceDto> pendingInvoiceDtoList = new ArrayList<>();
		List<String> salesRepCustList = new ArrayList<>();

		Map<String, List<PendingInvoiceDto>> map = new HashMap<>();

		List<PendingInvoiceDto> pendingInvoiceList;

		try {

			// get customer list
			//salesRepCustList = salesManCusRelDao.getAllPrimaryCustomer(salesRep);
			salesRepCustList.add("0000000046");
			salesRepCustList.add("0000000047");
			salesRepCustList.add("0000000076");
			salesRepCustList.add("0000000077");
			salesRepCustList.add("0000000061");
			salesRepCustList.add("0000000062");
			salesRepCustList.add("0000000063");
			
			
			
			
			
			
			
			System.err.println("Hey Service2");

			if (salesRepCustList != null && !salesRepCustList.isEmpty()) {

				pendingInvoiceDtoList = pendingInvoiceDaoLocal.getPendingInvoices(salesRepCustList);

				//customerDtoList = customerDaoLocal.getAllCustomerDetails(salesRepCustList);

				if (pendingInvoiceDtoList != null && !pendingInvoiceDtoList.isEmpty() && customerDtoList != null
						&& !customerDtoList.isEmpty()) {

					for (PendingInvoiceDto p : pendingInvoiceDtoList) {

						if (map.containsKey(p.getCustId())) {

							pendingInvoiceList = map.get(p.getCustId());

							pendingInvoiceList.add(p);

							map.put(p.getCustId(), pendingInvoiceList);

						} else {

							pendingInvoiceList = new ArrayList<>();
							pendingInvoiceList.add(p);
							map.put(p.getCustId(), pendingInvoiceList);

						}

					}

					for (CustomerDto c : customerDtoList) {

						c.setPendingInvoiceDtoList(map.get(c.getCustId()));
						c.setIsPrimary(Boolean.TRUE);
						resultCustomerDto.add(c);
					}

				}
			}
			responseDto.setStatus(Boolean.TRUE);
			responseDto.setMessage("Message.SUCCESS.toString()");

		} catch (Exception e) {

			//logger.error("[ValueHelpService][getAllCustomerDetailsWithInvoices]:::" + e.getMessage());
			responseDto.setStatus(false);
			responseDto.setMessage(e.getMessage());

		}

		responseDto.setData(resultCustomerDto);

		return responseDto;

	}

	@Override
	public ResponseDto getAllCustDetailsBySalesRep(String salesRep) {
		
		List<PaymentTermDo> paymentTermDoList = new ArrayList<>();

		List<CustomerDto> customerDtoList = new ArrayList<>();
		List<CustomerDto> customerDtoFromDb = new ArrayList<>();
		Map<String, String> customerShipMap = new HashMap<>();
		List<String> customerList = new ArrayList<>();
		Map<String, CreditLimitDto> creditLimitMap = new HashMap<>();
		List<SalesRepCustDto> salesRepCustList = new ArrayList<>();

		Map<String, String> customerSoldMap = new HashMap<>();

		Map<String, String> paymentCustExcel = new HashMap<>();

		Map<String, String> paymentCustTerms = new HashMap<>();

		Map<String, String> termsMap = new HashMap<>();

		List<PaymentTermsDto> paymentList = new ArrayList<>();

		BigDecimal dec=new BigDecimal(50000);
		CustomerDto customerDto;
		ResponseDto responseDto = new ResponseDto();

		try {

			responseDto.setStatus(true);
			responseDto.setMessage("Message.SUCCESS.toString()");

			salesRepCustList = salesManCusRelDao.getCustList(salesRep);
			System.err.println("All Customers"+salesRepCustList);

			for (SalesRepCustDto s : salesRepCustList) {

				if (!ServicesUtil.isEmpty(s.getType())) {

					if (s.getType().equalsIgnoreCase("Secondary")) {

						// SecondaryCustomer from Excel

						customerDto = new CustomerDto();

						customerDto.setCustCategory(s.getCategory());
						customerDto.setCustName(s.getCust_name());
						customerDto.setCustPostalCode(s.getRegion_code());
						customerDto.setCustCity(s.getArea_name());
						customerDto.setCustId(s.getCust_code());
						customerDto.setIsPrimary(Boolean.FALSE);
						customerDtoList.add(customerDto);

					} else {

						// PrimaryCustomer from db

						customerShipMap.put(s.getCust_code(), s.getControlling_area());
						customerList.add(s.getCust_code());
						paymentCustExcel.put(s.getCust_code(), s.getSales_organization());

					}

				}

			}
			
			System.err.println("All CustomersLis2"+customerList);

			if (customerList != null && !customerList.isEmpty()) {
                //CustomerDo customerDo=
				customerDtoFromDb = ObjectMapperUtils.mapAll(crepo.findAllById(customerList),CustomerDto.class);
			}
			System.err.println("All CustomersLis3"+customerDtoFromDb);

			if (customerDtoFromDb != null && !customerDtoFromDb.isEmpty()) {

				for (CustomerDto c : customerDtoFromDb) {

					customerSoldMap.put(c.getSpCustId(), customerShipMap.get(c.getCustId()));
					paymentCustTerms.put(c.getSpCustId(), paymentCustExcel.get(c.getCustId()));

				}
			}
			System.err.println("All CustomersLis4"+customerSoldMap);

			if (customerSoldMap != null && !customerSoldMap.isEmpty()) {
				
				
				System.err.println("All CustomersLis5");
				creditLimitMap = hciGetCustomerDetails.getCreditLimitsOfCutomerList(customerSoldMap);
				
				
				 //logger.error("[ValueHelpService][getAllCustDetailsBySalesRep]:Calling RFC credit limit service size"+ creditLimitMap.size());
				 
			}
			System.err.println("All CustomersLis5"+paymentCustTerms);

			if (paymentCustTerms != null && !paymentCustTerms.isEmpty()) {

				paymentList = hciGetCustomerDetails.getCustomerPaymentTermsFromRFC(paymentCustTerms);

				/*
				 * logger.
				 * error("[ValueHelpService][getAllCustDetailsBySalesRep]:Calling RFC  payterm service size:"
				 * + paymentList.size());
				 */

				System.err.println("All CustomersLis6"+paymentList);
				if (paymentList != null && !paymentList.isEmpty()) {

					for (PaymentTermsDto p : paymentList) {

						termsMap.put(p.getCustId(), p.getZterm());

						PaymentTermDo paymentTermDo = new PaymentTermDo();
						paymentTermDo.setCustId(p.getCustId());
						paymentTermDo.setTerm(p.getZfael());

						paymentTermDoList.add(paymentTermDo);

					}
				}

				paymentTermDaoLocal.saveTerms(paymentTermDoList);

			}

			if (customerDtoFromDb != null && !customerDtoFromDb.isEmpty() && creditLimitMap != null
					&& !creditLimitMap.isEmpty()) {

				for (CustomerDto d : customerDtoFromDb) {

					CreditLimitDto creditLimitDto = creditLimitMap.get(d.getSpCustId());

					if (creditLimitDto != null) {
                System.err.println("CreditLimit");
						d.setCustCreditLimit(
								creditLimitDto.getExposure() != null ? creditLimitDto.getExposure().toString() : null);
						d.setExposure(creditLimitDto.getCreditLimit());

					} else {

						d.setCustCreditLimit("25000");
						d.setExposure(dec);
						//d.setCustCreditLimit(null);
						//d.setExposure(null);


					}

					d.setIsPrimary(Boolean.TRUE);

					String term = termsMap.get(d.getSpCustId());
					d.setTerms(term != null ? term : "");
                   if(d.getCustCreditLimit()==null)
	               d.setCustCreditLimit("25000");
                   if(d.getExposure()==null)
	   d.setExposure(dec);
					customerDtoList.add(d);

				}
			}

		} catch (Exception e) {

			//logger.error("[ValueHelpService][getAllDetailsBySalesRep]:::" + e.getMessage());
			responseDto.setStatus(false);
			responseDto.setMessage(e.getMessage());

		}

		responseDto.setData(customerDtoList);
		return responseDto;

	}

	@Override
	public ResponseDto getAllDetailsBySalesRep(String salesRep) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
