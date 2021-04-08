package com.incture.cherrywork.repositories;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.dtos.SalesOrderLookUpDto;
import com.incture.cherrywork.entities.LookUp;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.entities.SalesOrderItem;
import com.incture.cherrywork.sales.constants.EnLookUp;

@SuppressWarnings("unused")
@Transactional
@Repository
public class ISalesOrderItemCustomRepositoryImpl implements ISalesOrderItemCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	
	@SuppressWarnings("unchecked")
	public List<SalesOrderLookUpDto> getPaymentTerms() {
		try {
			String strQuery = "select m from LookUp m where m.lookupType = ?1";
			Query query = entityManager.createQuery(strQuery);
			query.setParameter(1, EnLookUp.PAYMENT);
			List<LookUp> listLookUp = query.getResultList();
			List<SalesOrderLookUpDto> listSalesOrderLookUpDto = new ArrayList<SalesOrderLookUpDto>();
			for(LookUp lookUp:listLookUp){
				listSalesOrderLookUpDto.add(ObjectMapperUtils.map(lookUp, SalesOrderLookUpDto.class));
			}
			//logger.debug("Lookup query- " + strQuery);
			return listSalesOrderLookUpDto;
		} catch (Exception e) {          //Replace Exception by ResultFault
			//logger.debug("LookupDao.getPaymentTerms():" + e.getMessage());
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<SalesOrderLookUpDto> getIncoTerms() {
		try {
			String strQuery = "select m from LookUp m where m.lookupType = ?1";
			Query query = entityManager.createQuery(strQuery);
			query.setParameter(1, EnLookUp.INCOTERMS);
			List<LookUp> listLookUp = query.getResultList();
			List<SalesOrderLookUpDto> listSalesOrderLookUpDto = new ArrayList<SalesOrderLookUpDto>();
			for(LookUp lookUp:listLookUp){
				listSalesOrderLookUpDto.add(ObjectMapperUtils.map(lookUp, SalesOrderLookUpDto.class));
			}
			//logger.debug("Lookup query- " + strQuery);
			return listSalesOrderLookUpDto;
		} catch (Exception e) {          //Replace Exception by ResultFault
			//logger.debug("LookupDao.getPaymentTerms():" + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<SalesOrderLookUpDto> getQualityTest() {
		try {
			String strQuery = "select m from LookUp m where m.lookupType = ?1";
			Query query = entityManager.createQuery(strQuery);
			query.setParameter(1, EnLookUp.QUALITYTEST);
			List<LookUp> listLookUp = query.getResultList();
			List<SalesOrderLookUpDto> listSalesOrderLookUpDto = new ArrayList<SalesOrderLookUpDto>();
			for(LookUp lookUp:listLookUp){
				listSalesOrderLookUpDto.add(ObjectMapperUtils.map(lookUp, SalesOrderLookUpDto.class));
			}
			//logger.debug("Lookup query- " + strQuery);
			return listSalesOrderLookUpDto;
		} catch (Exception e) {          //Replace Exception by ResultFault
			//logger.debug("LookupDao.getPaymentTerms():" + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<SalesOrderLookUpDto> getDeliveryTolerance() {
		try {
			String strQuery = "select m from LookUp m where m.lookupType = ?1";
			Query query = entityManager.createQuery(strQuery);
			query.setParameter(1, EnLookUp.DELIVERYTOLERANCE);
			List<LookUp> listLookUp = query.getResultList();
			List<SalesOrderLookUpDto> listSalesOrderLookUpDto = new ArrayList<SalesOrderLookUpDto>();
			for(LookUp lookUp:listLookUp){
				listSalesOrderLookUpDto.add(ObjectMapperUtils.map(lookUp, SalesOrderLookUpDto.class));
			}
			//logger.debug("Lookup query- " + strQuery);
			return listSalesOrderLookUpDto;
		} catch (Exception e) {          //Replace Exception by ResultFault
			//logger.debug("LookupDao.getPaymentTerms():" + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<SalesOrderLookUpDto> getDistributionChannel() {
		try {
			String strQuery = "select m from LookUp m where m.lookupType = ?1";
			Query query = entityManager.createQuery(strQuery);
			query.setParameter(1, EnLookUp.DISTRIBUTIONCHANNEL);
			List<LookUp> listLookUp = query.getResultList();
			List<SalesOrderLookUpDto> listSalesOrderLookUpDto = new ArrayList<SalesOrderLookUpDto>();
			for(LookUp lookUp:listLookUp){
				listSalesOrderLookUpDto.add(ObjectMapperUtils.map(lookUp, SalesOrderLookUpDto.class));
			}
			//logger.debug("Lookup query- " + strQuery);
			return listSalesOrderLookUpDto;
		} catch (Exception e) {          //Replace Exception by ResultFault
			//logger.debug("LookupDao.getPaymentTerms():" + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public String getLookupValue(String key) {
		//logger.debug("[LookUpDao][getLookupValue] Started : " + key);
		String value = null;
		try {
			String strQuery = "select m.description from LookUp m where m.key =: key ";
			Query query = entityManager.createQuery(strQuery);
			query.setParameter("key", key);
			List<String >values = query.getResultList();
			value = values.get(0);
		} catch (Exception e) {
			//logger.error("[LookUpDao][getLookupValue] Exception :" + e.getMessage());
			e.printStackTrace();
		}
		return value;
	}


}
