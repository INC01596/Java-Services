package com.incture.cherrywork.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.incture.cherrywork.dtos.HeaderDetailUIDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.repositories.ISalesOrderHeaderRepository;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.repositories.SalesOrderHeaderPredicateBuilder;
import com.incture.cherrywork.util.ServicesUtil;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
@Transactional
public class SalesOrderHeaderService implements ISalesOrderHeaderService {
	public static final Logger logger = LoggerFactory.getLogger(SalesOrderHeaderService.class);
	@Autowired
	private ISalesOrderHeaderRepository salesOrderHeaderRepository;
	
	@Override
	public ResponseEntity<Object> create(SalesOrderHeaderDto salesOrderHeaderDto) {
		SalesOrderHeader salesOrderHeader = ObjectMapperUtils.map(salesOrderHeaderDto, SalesOrderHeader.class);
		SalesOrderHeader savedSalesOrderHeader = salesOrderHeaderRepository.save(salesOrderHeader);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("id").toUri();
		return ResponseEntity.created(location)
				.body(ObjectMapperUtils.map(savedSalesOrderHeader, SalesOrderHeaderDto.class));
	}

	@Override
	public ResponseEntity<Object> read(String s4DocumentId) {
		Optional<SalesOrderHeader> optionalSalesOrderHeader = salesOrderHeaderRepository.findById(s4DocumentId);
		if (!optionalSalesOrderHeader.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok()
				.body(ObjectMapperUtils.map(optionalSalesOrderHeader.get(), SalesOrderHeaderDto.class));
	}

	@Override
	public ResponseEntity<Object> update(String s4DocumentId, SalesOrderHeaderDto salesOrderHeaderDto) {
		Optional<SalesOrderHeader> optionalSalesOrderHeader = salesOrderHeaderRepository.findById(s4DocumentId);
		if (!optionalSalesOrderHeader.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		SalesOrderHeader salesOrderHeader = ObjectMapperUtils.map(salesOrderHeaderDto, SalesOrderHeader.class);
		salesOrderHeader.setS4DocumentId(optionalSalesOrderHeader.get().getS4DocumentId());
		SalesOrderHeader updatedSalesOrderHeader = salesOrderHeaderRepository.save(salesOrderHeader);
		return ResponseEntity.ok().body(ObjectMapperUtils.map(updatedSalesOrderHeader, SalesOrderHeaderDto.class));
	}

	@Override
	public ResponseEntity<Object> delete(String s4DocumentId) {
		Optional<SalesOrderHeader> optionalSalesOrderHeader = salesOrderHeaderRepository.findById(s4DocumentId);
		if (!optionalSalesOrderHeader.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		salesOrderHeaderRepository.delete(optionalSalesOrderHeader.get());
		return ResponseEntity.ok().body(null);
	}

	@Override
	public ResponseEntity<Object> readAll(String search) {
		SalesOrderHeaderPredicateBuilder builder = new SalesOrderHeaderPredicateBuilder();
		if (search != null) {
			Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
			Matcher matcher = pattern.matcher(search + ",");
			while (matcher.find()) {
				builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
			}
		}
		BooleanExpression exp = builder.build();
		List<SalesOrderHeader> salesOrderHeaders = (List<SalesOrderHeader>) salesOrderHeaderRepository.findAll(exp);
		Object t = ObjectMapperUtils.mapAll(salesOrderHeaders, SalesOrderHeaderDto.class);
		return ResponseEntity.ok().body(t);
	}

	// ----------------------------------------------------1
	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<Object> getDraftedVersion(HeaderDetailUIDto dto) {
		String jpql = " where s.documentType='" + dto.getDocumentType()
				+ "' and s.documentProcessStatus='" + dto.getDocumentProcessStatus() + "' ";
		if (!ServicesUtil.isEmpty(dto.getCreatedBy()))
			jpql = jpql + " and s.createdBy='" + dto.getCreatedBy() + "' ";
		if (!ServicesUtil.isEmpty(dto.getStpId())) {
			String STP = ServicesUtil.listToString(dto.getStpId());
			jpql = jpql + " and  s.soldToParty in (" + STP + ")";
		}
		if (!ServicesUtil.isEmpty(dto.getSalesGroup()))
			jpql = jpql + " and s.salesGroup='" + dto.getSalesGroup() + "' ";
		ArrayList<SalesOrderHeader> list = null;
		 EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
	      
	      EntityManager entitymanager = emfactory.createEntityManager( );
	      Query q=entitymanager.createQuery(jpql);
	      System.out.println(jpql);
		try {
			list = (ArrayList<SalesOrderHeader>) q.getResultList();
		} catch (Exception e) {
		}
		return ResponseEntity.ok().body(ObjectMapperUtils.map(list, ArrayList.class));
	}

	// -------------------------------------------------2
	// @Override
	// public ResponseEntity<Object> getReferenceList(HeaderDetailUIDto dto) {
	// String jsql = "";
	//
	// List<SalesOrderHeader> l = salesOrderHeaderRepository.findAll(jsql);
	// return ResponseEntity.ok().body(ObjectMapperUtils.map(l,
	// ArrayList.class));
	//
	// }
	//
	// // ------------------------------------------------------3

	// @Override
	// public ResponseEntity<Object> deleteDraftedVersion(String s4DocumentId) {
	// Optional<SalesOrderHeader> optionalSalesOrderHeader =
	// salesOrderHeaderRepository.findById(s4DocumentId);
	// if (!optionalSalesOrderHeader.isPresent()) {
	// return ResponseEntity.notFound().build();
	// }
	// salesOrderHeaderRepository.delete(optionalSalesOrderHeader.get());
	// return ResponseEntity.ok().body(null);
	// }

}
