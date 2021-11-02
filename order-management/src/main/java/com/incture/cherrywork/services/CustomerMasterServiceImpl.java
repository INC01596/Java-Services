package com.incture.cherrywork.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.CustomerMasterFilterDto;
import com.incture.cherrywork.dtos.Response;
import com.incture.cherrywork.dtos.ResponseDtoNew;
import com.incture.cherrywork.entities.CustomerMasterEntity;
import com.incture.cherrywork.entities.RequestMasterDo;
import com.incture.cherrywork.odata.dto.ODataCustomerDto;
import com.incture.cherrywork.repositories.ICustomerMasterRepo;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.sales.constants.ResponseStatus;

@Service("CustomerMasterServiceImpl")
@Transactional
public class CustomerMasterServiceImpl implements CustomerMasterService{
	
	@Autowired
	private ICustomerMasterRepo iCustomerMasterRepo;
	
	@Autowired
	private SessionFactory sessionfactory;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Response saveInDb(ODataCustomerDto data) {
		try{
			CustomerMasterEntity toSave = ObjectMapperUtils.map(data, CustomerMasterEntity.class);
			System.err.println("[Step 6 -Inside CustomerMasterServiceImpl][saveInDb] toSave : " + toSave);
			CustomerMasterEntity savedData = iCustomerMasterRepo.save(toSave);
			if (savedData == null) {
				return new Response(null, HttpStatus.BAD_REQUEST, "CREATION_FAILED", ResponseStatus.FAILED);
			}
			String msg = "Successfully updated in hana";
			return new Response(savedData, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
			return new Response("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseDtoNew getCustomerDetailsWithFullAccess(CustomerMasterFilterDto filterData) {
		ResponseDtoNew res = new ResponseDtoNew();
		int flag = 0;
		try{
			String sql = "SELECT c FROM CustomerMasterEntity c ";
//			List<CustomerMasterEntity> data = iCustomerMasterRepo.filterCustomerByFullAccess(filterData.getCustomerNum());
//			res.setData(data);
			if(filterData.getCustomerNum() != null || filterData.getCustomerName() != null || 
					(filterData.getDistributionChannel() != null && filterData.getDistributionChannel().size() != 0) || 
					(filterData.getDivision() !=null && filterData.getDivision().size() !=0) || 
					(filterData.getSalesOrg() !=null && filterData.getSalesOrg().size() !=0)){
				sql = sql + "WHERE ";
			}
			if(filterData.getCustomerNum() != null){
				String customerNumber = "'"+filterData.getCustomerNum()+"'";
				sql = sql + "c.custCode = " +customerNumber;
				flag = 1;
			}
			if(filterData.getCustomerName() != null){
				String customerName = "'" + filterData.getCustomerName() + "'";
				if(flag == 1){
					sql = sql + " AND c.nameInEN = "+customerName;
				}else{
					sql = sql + " c.nameInEN = "+customerName;
					flag = 1;
				}
			}
			if(filterData.getDistributionChannel() != null && filterData.getDistributionChannel().size() != 0){
				String distributionChannel = join(filterData.getDistributionChannel());
				if(flag == 1){
					sql = sql + " AND c.channel IN ("+distributionChannel+")";
				}else{
					sql = sql + " c.channel IN ("+distributionChannel+")";
					flag = 1;
				}
				
			}
			if(filterData.getDivision() !=null && filterData.getDivision().size() !=0){
				String division = join(filterData.getDivision());
				if(flag == 1){
					sql = sql + " AND c.division IN (" + division + ")";
				}else{
					sql = sql + " c.division IN (" + division + ")";
					flag = 1;
				}
			}
			if(filterData.getSalesOrg() !=null && filterData.getSalesOrg().size() !=0){
				String salesOrg = join(filterData.getSalesOrg());
				if(flag == 1){
					sql = sql + " AND c.salesOrg IN (" + salesOrg + ")";
				}else{
					sql = sql + " c.salesOrg IN (" + salesOrg + ")";
				}
			}
//			String sql = "SELECT c FROM CustomerMasterEntity c WHERE c.custCode = " +customerNumber + "AND c.channel IN ('CO')" ;
			System.err.println(sql);
			Query q1 = entityManager.createQuery(sql);
			List<CustomerMasterEntity> data = q1.getResultList();
			res.setData(data);
			if(data == null){
				res.setMessage("No Data Found !!");
			}else{
				res.setMessage("Success");
			}
			res.setStatusCode(200);
			res.setStatus("OK");
			return res;
		}catch(Exception e){
			res.setData(null);
			res.setStatusCode(500);
			res.setMessage("Internal server error");
			e.printStackTrace();
		}
		return res;
	}
	
	public String join(List<String> namesList) {
	    return String.join(",", namesList
	            .stream()
	            .map(name -> ("'" + name + "'"))
	            .collect(Collectors.toList()));
	}

	@Override
	public ResponseDtoNew getCustomerDetailsWithDacAccess(CustomerMasterFilterDto filterData) {
		
		ResponseDtoNew res = new ResponseDtoNew();
		int flag = 0;
		try{
			String sql = "SELECT c FROM CustomerMasterEntity c WHERE ";
//			List<CustomerMasterEntity> data = iCustomerMasterRepo.filterCustomerByFullAccess(filterData.getCustomerNum());
//			res.setData(data);
			
			if(filterData.getCustomerNum() != null){
				String customerNumber = "'"+filterData.getCustomerNum()+"'";
				sql = sql + "c.custCode = " +customerNumber;
				flag = 1;
			}
			if(filterData.getCustomerName() != null){
				String customerName = "'" + filterData.getCustomerName() + "'";
				if(flag == 1){
					sql = sql + " AND c.nameInEN = "+customerName;
				}else{
					sql = sql + " c.nameInEN = "+customerName;
					flag = 1;
				}
			}
			if(filterData.getDistributionChannel() != null && filterData.getDistributionChannel().size() != 0){
				String distributionChannel = join(filterData.getDistributionChannel());
				if(flag == 1){
					sql = sql + " AND c.channel IN ("+distributionChannel+")";
				}else{
					sql = sql + " c.channel IN ("+distributionChannel+")";
					flag = 1;
				}
				
			}
			if(filterData.getDivision() !=null && filterData.getDivision().size() !=0){
				String division = join(filterData.getDivision());
				if(flag == 1){
					sql = sql + " AND c.division IN (" + division + ")";
				}else{
					sql = sql + " c.division IN (" + division + ")";
					flag = 1;
				}
			}
			if(filterData.getSalesOrg() !=null && filterData.getSalesOrg().size() !=0){
				String salesOrg = join(filterData.getSalesOrg());
				if(flag == 1){
					sql = sql + " AND c.salesOrg IN (" + salesOrg + ")";
				}else{
					sql = sql + " c.salesOrg IN (" + salesOrg + ")";
					flag = 1;
				}
			}
			if(filterData.getDac() !=null && filterData.getDac().size() !=0){
				String Dac = join(filterData.getDac());
				if(flag == 1){
					sql = sql + " AND c.custCode IN (" + Dac + ")";
				}else{
					sql = sql + " c.custCode IN (" + Dac + ")";
				}
			}
//			String sql = "SELECT c FROM CustomerMasterEntity c WHERE c.custCode = " +customerNumber + "AND c.channel IN ('CO')" ;
			System.err.println(sql);
			Query q1 = entityManager.createQuery(sql);
			List<CustomerMasterEntity> data = q1.getResultList();
			res.setData(data);
			if(data == null || data.size() <= 0){
				res.setMessage("No Data Found !!");
			}else{
				res.setMessage("Success");
			}
			res.setStatusCode(200);
			res.setStatus("OK");
			return res;
		}catch(Exception e){
			res.setData(null);
			res.setStatusCode(500);
			res.setMessage("Internal server error");
			e.printStackTrace();
		}
		return res;
	}
	

}
