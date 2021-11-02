package com.incture.cherrywork.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.incture.cherrywork.dao.BaseDao;
import com.incture.cherrywork.dtos.SalesRepCustDto;
import com.incture.cherrywork.entities.SalesManCustRelDo;

@Repository
public class SalesManCustRelDao extends BaseDao<SalesManCustRelDo, SalesRepCustDto> implements SalesManCustRelDaoLocal {

	@PersistenceContext
	private EntityManager entityManager;

	
	
	
	@Override
public List<String> getAllPrimaryCustomer(String salesRep) {

	List<String> customerList = new ArrayList<>();

	String hql = "select sapCustCode from SalesManCustRelDo where sapSalesmanCode=:sapSalesmanCode and custType=:custType";
	 Query q=entityManager.createQuery(hql);
//	Query query = getSession().createQuery(hql);

	q.setParameter("sapSalesmanCode", salesRep);

	q.setParameter("custType", "Primary");

	customerList = q.getResultList();

	return customerList;
}

@Override
public String getControllingArea(String salesRepId, String custId) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public List<String> saveAllExcelData(List<SalesRepCustDto> salesCustList) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public List<SalesRepCustDto> getCustList(String salesRep) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public List<String> getAllCustomerList() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public SalesRepCustDto getApproversBySalesRep(String salesRep) {
	// TODO Auto-generated method stub
	return null;
}



@Override
public List<SalesRepCustDto> getControllingAreasByCustList(List<String> custList, String salesRep) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public List<SalesRepCustDto> getSalesCustRelDtoByCustListAndSalesRep(String salesRep, List<String> custList) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public List<SalesRepCustDto> getCorrespondingCustomer(String salesRep) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public List<Object[]> getAreaRegionTeritory(String managerCode) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Long getCountOfRecords() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getManagerOfsalesRep(String salesRep) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void deleteExcelrows(List<String> primaryIdList) {
	// TODO Auto-generated method stub
	
}

@Override
public SalesManCustRelDo importDto(SalesRepCustDto dto) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public SalesRepCustDto exportDto(SalesManCustRelDo entity) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public List<SalesManCustRelDo> importList(List<SalesRepCustDto> list) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public List<SalesRepCustDto> exportList(List<SalesManCustRelDo> list) {
	// TODO Auto-generated method stub
	return null;
}
}