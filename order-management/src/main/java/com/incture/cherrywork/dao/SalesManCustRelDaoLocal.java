package com.incture.cherrywork.dao;

import java.util.List;

import com.incture.cherrywork.dtos.SalesOrgDetailsDto;
import com.incture.cherrywork.dtos.SalesRepCustDto;
import com.incture.cherrywork.entities.SalesManCustRelDo;


public interface SalesManCustRelDaoLocal {

	public String getControllingArea(String salesRepId, String custId);

	//public List<String> saveAllExcelData(List<SalesRepCustDto> salesCustList);
	public List<String> saveAllExcelData(List<SalesRepCustDto> salesCustList);

	public List<SalesRepCustDto> getCustList(String salesRep);

	public List<String> getAllCustomerList();

	public SalesRepCustDto getApproversBySalesRep(String salesRep);

	public List<String> getAllPrimaryCustomer(String salesRep);

	public List<SalesRepCustDto> getControllingAreasByCustList(List<String> custList, String salesRep);

	public List<SalesManCustRelDo> getSalesPerson(String managerCode);

	public SalesOrgDetailsDto getSalesOrgDetails(String salesRep, String customerId);

	public List<SalesManCustRelDo> getSalesCustRelDtoByCustList(List<String> custList);

	public List<SalesRepCustDto> getSalesCustRelDtoByCustListAndSalesRep(String salesRep, List<String> custList);

	public List<SalesRepCustDto> getCorrespondingCustomer(String salesRep);

	public List<Object[]> getAreaRegionTeritory(String managerCode);

	public Long getCountOfRecords();

	public String getManagerOfsalesRep(String salesRep);

	public void deleteExcelrows(List<String> primaryIdList);

	public SalesOrgDetailsDto getSalesOrgDetail(String customerNumber);

//	public List<SalesRepCustDto> getCustList(String salesRep);

}