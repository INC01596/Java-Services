package com.incture.cherrywork.services;



import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dao.BaseDao;
import com.incture.cherrywork.dtos.CustomerDto;
import com.incture.cherrywork.entities.CustomerDo;



@Repository("CustomerDao")
public class CustomerDao extends BaseDao<CustomerDo, CustomerDto> implements CustomerDaoLocal {

	// private static final Logger logger =
	// LoggerFactory.getLogger(CustomerDao.class);

	@Override
	public CustomerDo importDto(CustomerDto dto) {

		CustomerDo entity = new CustomerDo();

		if (dto != null) {

			entity.setCustId(dto.getCustId());
			entity.setCustName(dto.getCustName());
			entity.setCustCity(dto.getCustCity());
			entity.setCustCountry(dto.getCustCountry());
			entity.setCustPostalCode(dto.getCustPostalCode());
			entity.setCustCategory(dto.getCustCategory());
			entity.setCustCreditLimit(dto.getCustCreditLimit());
			entity.setSpCustId(dto.getSpCustId());
			entity.setSpName(dto.getSpName());
			entity.setSpCity(dto.getSpCity());
			entity.setSpPostalCode(dto.getSpPostalCode());

			entity.setCrFlag(dto.getCrFlag());
			entity.setPhoneNumber(dto.getPhoneNumber());
			entity.setTeleExtension(dto.getTeleExtension());
			entity.setTelFax(dto.getTelFax());
			entity.setFaxExtension(dto.getFaxExtension());
			entity.setEmail(dto.getEmail());
			entity.setSpPhoneNumber(dto.getSpPhoneNumber());
			entity.setSpTeleExtension(dto.getSpTeleExtension());
			entity.setSpTelFax(dto.getSpTelFax());
			entity.setSpFaxExtension(dto.getSpFaxExtension());
			entity.setSpEmail(dto.getSpEmail());

		}

		return entity;
	}

	@Override
	public CustomerDto exportDto(CustomerDo entity) {

		CustomerDto dto = new CustomerDto();

		if (entity != null) {

			dto.setCustId(entity.getCustId());
			dto.setCustName(entity.getCustName());
			dto.setCustCity(entity.getCustCity());
			dto.setCustCountry(entity.getCustCountry());
			dto.setCustPostalCode(entity.getCustPostalCode());
			dto.setCustCategory(entity.getCustCategory());
			dto.setCustCreditLimit(entity.getCustCreditLimit());
			dto.setSpCustId(entity.getSpCustId());
			dto.setSpName(entity.getSpName());
			dto.setSpCity(entity.getSpCity());
			dto.setSpPostalCode(entity.getSpPostalCode());

			dto.setCrFlag(entity.getCrFlag());
			dto.setPhoneNumber(entity.getPhoneNumber());
			dto.setTeleExtension(entity.getTeleExtension());
			dto.setTelFax(entity.getTelFax());
			dto.setFaxExtension(entity.getFaxExtension());
			dto.setEmail(entity.getEmail());
			dto.setSpPhoneNumber(entity.getSpPhoneNumber());
			dto.setSpTeleExtension(entity.getSpTeleExtension());
			dto.setSpTelFax(entity.getSpTelFax());
			dto.setSpFaxExtension(entity.getSpFaxExtension());
			dto.setSpEmail(entity.getSpEmail());

		}

		return dto;
	}

	@Override
	public void updateCustomer(CustomerDto customerDto) {

		getSession().update(importDto(customerDto));

	}

	@Override
	public void deleteCustomer(CustomerDto customerDto) {

		getSession().delete(importDto(customerDto));

	}

	@Override
	public CustomerDto 
	getCustomer(CustomerDto customerDto) {
		CustomerDo entity = (CustomerDo) getSession().get(CustomerDo.class, customerDto.getCustId());
		return exportDto(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDto> getAllCustomers() {

		List<CustomerDto> listDto = new ArrayList<>();

		Query q = getSession().createQuery("from CustomerDo");
		List<CustomerDo> listDo = q.list();
		for (CustomerDo e1 : listDo) {
			listDto.add(exportDto(e1));
		}
		return listDto;
	}

	@Override
	public void saveAllCustomerFromRfcToHanaDb(List<CustomerDo> customerDoList) {

		int counter = 0;

		for (CustomerDo customerDo : customerDoList) {

			counter++;

			getSession().saveOrUpdate(customerDo);

			if (counter > 0 && counter % 50 == 0) {

				getSession().flush();
				getSession().clear();

			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllCustomer() {

		List<String> listOfCustId = new ArrayList<>();

		Query q = getSession().createQuery("Select c.custId from CustomerDo c");

		listOfCustId = q.list();

		return listOfCustId;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllSoldCustomer() {

		List<String> listOfCustId = new ArrayList<>();

		Query q = getSession().createQuery("Select  distinct spCustId from CustomerDo");

		listOfCustId = q.list();

		return listOfCustId;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDto> getAllCustomerDetails(List<String> customerList) {

		List<CustomerDto> customerDtoList = new ArrayList<>();
		List<CustomerDo> customerDoList;

		String hql = "from CustomerDo where custId in (:custList) ";

		Query q = getSession().createQuery(hql);
		q.setParameterList("custList", customerList);
		customerDoList = q.list();

		for (CustomerDo c : customerDoList) {

			customerDtoList.add(exportDto(c));

		}

		return customerDtoList;
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllDistictShipToCustomerList() {

		List<String> customerList = new ArrayList<>();

		String hql = "select distinct spCustId from CustomerDo ";

		Query q = getSession().createQuery(hql);

		customerList = q.list();

		return customerList;

	}*/

	// get crFlag from Customer table
	@Override
	public Boolean getCrFlag(String customerId) {

		Boolean crFlag;

		String hql = "select crFlag from CustomerDo where custId=:custId ";

		Query q = getSession().createQuery(hql);
		q.setParameter("custId", customerId);

		crFlag = (Boolean) q.uniqueResult();

		return crFlag;

	}

	

	
	@Override
	public List<CustomerDo> importList(List<CustomerDto> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerDto> exportList(List<CustomerDo> list) {
		// TODO Auto-generated method stub
		return null;
	}
}
