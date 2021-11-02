package com.incture.cherrywork.repositories;

import java.sql.Date;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.DocDto;
import com.incture.cherrywork.dtos.HeaderDetailUIDto;
import com.incture.cherrywork.dtos.HeaderIdDto;
import com.incture.cherrywork.dtos.InvoDto;
import com.incture.cherrywork.dtos.MaterialContainerDto;
import com.incture.cherrywork.dtos.ObdDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.dtos.TrackSOUIDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.dtos.SalesOrderMaterialMasterDto;
//import com.incture.cherrywork.dtos.SequenceDto;
import com.incture.cherrywork.entities.MaterialMaster;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.entities.SalesOrderItem;
import com.incture.cherrywork.pagination.Content;
import com.incture.cherrywork.pagination.Root;
import com.incture.cherrywork.sales.constants.EnOrderActionStatus;
import com.incture.cherrywork.sales.constants.EnUpdateIndicator;
import com.incture.cherrywork.util.ServicesUtil;

@SuppressWarnings("unused")
@Repository
public class ISalesOrderHeaderRepositoryNew {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ISalesOrderHeaderRepository repo;

	@SuppressWarnings("unchecked")
	public List<String> getHeader(String stp) {
		System.err.println(stp);
		List<String> l = new ArrayList<>();
		Query header = entityManager
				.createQuery("Select s.salesHeaderId from SalesOrderHeader s where s.soldToParty=:sold");
		header.setParameter("sold", stp);
		l = header.getResultList();
		System.err.println(l);
		return l;

	}

	// Sandeep Kumar
	public List<Integer> deleteDraftedVersion(HeaderIdDto d) {
		List<Integer> l = new ArrayList<>();
		try {
			if (d.getSalesHeaderId() == null) {
				Query header1 = entityManager.createQuery(
						"delete from SalesOrderItem s where s.salesOrderHeader.s4DocumentId=:salesHeaderId");
				header1.setParameter("salesHeaderId", d.getS4DocumentId());
				int result1 = header1.executeUpdate();
				l.add(result1);

				Query header = entityManager
						.createQuery("delete from SalesOrderHeader s where s.s4DocumentId=:salesHeaderId");
				header.setParameter("salesHeaderId", d.getS4DocumentId());
				int result2 = header.executeUpdate();
				if (result2 != 0)
					result2 = 1;
				l.add(result2);
			} else {
				Query header1 = entityManager
						.createQuery("delete from SalesOrderItem s where s.salesHeaderId=:salesHeaderId");
				header1.setParameter("salesHeaderId", d.getSalesHeaderId());
				int result1 = header1.executeUpdate();
				l.add(result1);

				Query header = entityManager
						.createQuery("delete from SalesOrderHeader s where s.salesHeaderId=:salesHeaderId");
				header.setParameter("salesHeaderId", d.getSalesHeaderId());
				int result2 = header.executeUpdate();
				if (result2 != 0)
					result2 = 1;
				l.add(result2);
			}
		} catch (Exception e) {
			System.err.println("try found exception");
			e.printStackTrace();
		}

		return l;
	}

	// Sandeep Kumar Get Created Enquiry or Quotation
	@SuppressWarnings("unchecked")
	public List<String> getReferenceList(HeaderDetailUIDto dto) {

		List<String> list = new ArrayList<>();
		StringBuffer query = new StringBuffer(
				"select s.salesHeaderId from SalesOrderHeader s where s.documentType=:documentType and s.documentProcessStatus="
						+ EnOrderActionStatus.CREATED.ordinal() + " ");
		if (!ServicesUtil.isEmpty(dto.getCreatedBy()))
			query.append("and s.createdBy=:createdBy ");
		if (!ServicesUtil.isEmpty(dto.getStpId())) {
			String STP = listToString(dto.getStpId());
			query.append("and s.soldToParty in (" + STP + ") ");
		}
		if (!ServicesUtil.isEmpty(dto.getSalesGroup()))
			query.append("and s.salesGroup=:salesGroup ");
		query.append("order by s.s4DocumentId desc");
		Query q = entityManager.createQuery(query.toString());
		q.setParameter("documentType", dto.getDocumentType());
		if (!ServicesUtil.isEmpty(dto.getCreatedBy()))
			q.setParameter("createdBy", dto.getCreatedBy());
		if (!ServicesUtil.isEmpty(dto.getSalesGroup()))
			q.setParameter("salesGroup", dto.getSalesGroup());
		list = q.getResultList();
		return list;

	}

	// Sandeep Kumar<--------To get List of all Orders
	// ,quotation,enquiry--------------------------------->
	/*
	 * @SuppressWarnings({ "unchecked", "deprecation" }) public
	 * List<SalesOrderHeader> getManageService(HeaderDetailUIDto dto) {
	 * List<SalesOrderHeader> headerEntityList = new ArrayList<>();
	 * 
	 * try { StringBuffer headerQuery = new StringBuffer(
	 * "select s from SalesOrderHeader s where s.documentType=:documentType");
	 * 
	 * if (!ServicesUtil.isEmpty(dto.getSalesHeaderId()))
	 * 
	 * { headerQuery.append(" and s.salesHeaderId=:salesHeaderId"); } if
	 * (!ServicesUtil.isEmpty(dto.getCreatedBy()))
	 * headerQuery.append(" and s.createdBy=:createdBy"); if
	 * (dto.getDocumentProcessStatus() == null) {
	 * headerQuery.append(" and s.documentProcessStatus in (" +
	 * EnOrderActionStatus.CREATED.ordinal() + "," +
	 * EnOrderActionStatus.CANCELLED.ordinal() + "," +
	 * EnOrderActionStatus.DRAFTED.ordinal() + "," +
	 * EnOrderActionStatus.OPEN.ordinal() + ")"); } else
	 * headerQuery.append(" and s.documentProcessStatus = " +
	 * dto.getDocumentProcessStatus().ordinal() + "");
	 * 
	 * if (!ServicesUtil.isEmpty(dto.getCreatedDateFrom()) &&
	 * !ServicesUtil.isEmpty(dto.getCreatedDateTo())) { //
	 * 
	 * headerQuery.append(" and s.createdDate between :stDate and :enDate "); }
	 * 
	 * if (!ServicesUtil.isEmpty(dto.getRequestDeliveryDateFrom()) &&
	 * !ServicesUtil.isEmpty(dto.getRequestDeliveryDateTo())) {
	 * 
	 * headerQuery.
	 * append(" and s.requestDeliveryDate between :dstDate and :denDate "); }
	 * 
	 * if (!ServicesUtil.isEmpty(dto.getStpId())) { String STP =
	 * listToString(dto.getStpId());
	 * headerQuery.append(" and s.soldToParty in (" + STP + ")"); }
	 * 
	 * if (!ServicesUtil.isEmpty(dto.getSalesGroup()))
	 * headerQuery.append(" and s.salesGroup=:salesGroup");
	 * 
	 * headerQuery.append(" order by s.createdDate desc");
	 * 
	 * Query hq = entityManager.createQuery(headerQuery.toString());
	 * 
	 * if (!ServicesUtil.isEmpty(dto.getCreatedBy()))
	 * hq.setParameter("createdBy", dto.getCreatedBy());
	 * 
	 * hq.setParameter("documentType", dto.getDocumentType());
	 * 
	 * if (!ServicesUtil.isEmpty(dto.getSalesHeaderId()))
	 * 
	 * { hq.setParameter("salesHeaderId", dto.getSalesHeaderId()); }
	 * 
	 * if (!ServicesUtil.isEmpty(dto.getSalesGroup()))
	 * hq.setParameter("salesGroup", dto.getSalesGroup());
	 * 
	 * if (!ServicesUtil.isEmpty(dto.getCreatedDateFrom()) &&
	 * !ServicesUtil.isEmpty(dto.getCreatedDateTo())) { //
	 * 
	 * hq.setParameter("stDate", dto.getCreatedDateFrom());
	 * hq.setParameter("enDate", dto.getCreatedDateTo()); }
	 * 
	 * if (!ServicesUtil.isEmpty(dto.getRequestDeliveryDateFrom()) &&
	 * !ServicesUtil.isEmpty(dto.getRequestDeliveryDateTo())) {
	 * 
	 * hq.setParameter("dstDate", dto.getRequestDeliveryDateFrom());
	 * hq.setParameter("denDate", dto.getRequestDeliveryDateTo());
	 * 
	 * }
	 * 
	 * System.err.println(headerQuery); headerEntityList = hq.getResultList();
	 * System.err.println("1"); System.err.println(headerEntityList.toString());
	 * } catch (Exception e) { System.err.println("Exception Error");
	 * e.printStackTrace(); }
	 * 
	 * return headerEntityList; }
	 */
	// public Page<SalesOrderHeader> getManageServiceObd(ObdDto dto, Pageable
	// pageable) {
	// if (!ServicesUtils.isEmpty(dto.getObdId())) {
	// System.err.println("Only OBD");
	// return repo.findAllD(dto.getObdId(), pageable);
	// }
	//
	// if (!ServicesUtil.isEmpty(dto.getSalesHeaderId())) {
	// System.err.println("Only salesHeaderId");
	// return repo.findAllS1(dto.getSalesHeaderId(), dto.getDocumentType(),
	// pageable);
	// }
	//
	// if (!ServicesUtils.isEmpty(dto.getPgiStatus()) &&
	// !ServicesUtils.isEmpty(dto.getInvoiceStatus())) {
	//
	// System.err.println("OBD+PGI+INVOICE");
	// return repo.findAllOPI(dto.getDocumentType(), dto.getInvoiceStatus(),
	// pageable);
	//
	// }
	//
	// if (!ServicesUtils.isEmpty(dto.getStpId())) {
	// if (!ServicesUtil.isEmpty(dto.getObdStatus()) &&
	// !ServicesUtil.isEmpty(dto.getCreatedDateFrom())) {
	//
	// System.err.println("Only cust+type+status+created");
	// return repo.findAllObd1(dto.getDocumentType(), dto.getStpId(),
	// dto.getObdStatus(),
	// dto.getCreatedDateFrom(), dto.getCreatedDateTo(), pageable);
	//
	// } else if (!ServicesUtil.isEmpty(dto.getObdStatus())) {
	// System.err.println("Only cust+type+status");
	// return repo.findAllObd2(dto.getDocumentType(), dto.getStpId(),
	// dto.getObdStatus(), pageable);
	// } else if (!ServicesUtil.isEmpty(dto.getCreatedDateFrom())) {
	// System.err.println("Only cust+type+created");
	// return repo.findAll12(dto.getDocumentType(), dto.getStpId(),
	// dto.getCreatedDateFrom(),
	// dto.getCreatedDateTo(), pageable);
	//
	// } else {
	// System.err.println("only type+cust");
	// return repo.findAll123(dto.getDocumentType(), dto.getStpId(), pageable);
	// }
	//
	// }
	//
	// if (!ServicesUtil.isEmpty(dto.getObdStatus())) {
	// if (!ServicesUtil.isEmpty(dto.getCreatedDateFrom()) &&
	// !ServicesUtil.isEmpty(dto.getShipToParty())) {
	// System.err.println("status+request+documentType+creat");
	// return repo.findAllObd6(dto.getDocumentType(), dto.getObdStatus(),
	// dto.getCreatedDateFrom(),
	// dto.getCreatedDateTo(), dto.getShipToParty(), pageable);
	// } else if (!ServicesUtil.isEmpty(dto.getShipToParty()) &&
	// ServicesUtil.isEmpty(dto.getCreatedDateFrom())) {
	// System.err.println("status+request+documentType");
	// return repo.findAllObd5(dto.getDocumentType(), dto.getObdStatus(),
	// dto.getShipToParty(), pageable);
	// } else if (!ServicesUtil.isEmpty(dto.getCreatedDateFrom()) &&
	// ServicesUtil.isEmpty(dto.getShipToParty())) {
	// // status+created
	// System.err.println("status+created+documentType");
	// return repo.findAllObd4(dto.getDocumentType(), dto.getObdStatus(),
	// dto.getCreatedDateFrom(),
	// dto.getCreatedDateTo(), pageable);
	// } else {
	// // status only
	// System.err.println("Only status+documentType");
	// return repo.findAllObd3(dto.getDocumentType(), dto.getObdStatus(),
	// pageable);
	// }
	//
	// } else if (!ServicesUtil.isEmpty(dto.getCreatedDateFrom())) {
	//
	// if (!ServicesUtil.isEmpty(dto.getShipToParty())) {
	// System.err.println("Only createdDate+documentType+shipToParty");
	//
	// return repo.findAll(dto.getDocumentType(), dto.getCreatedDateFrom(),
	// dto.getCreatedDateTo(),
	// dto.getShipToParty(), pageable);
	// } else {
	// System.err.println("Only createdDate+documentType");
	// return repo.findAll(dto.getDocumentType(), dto.getCreatedDateFrom(),
	// dto.getCreatedDateTo(), pageable);
	// }
	//
	// } else if (!ServicesUtil.isEmpty(dto.getShipToParty())) {
	// System.err.println("Only doctype+delivery");
	//
	// return repo.findAll1(dto.getDocumentType(), dto.getShipToParty(),
	// pageable);
	//
	// } else {
	// System.err.println("Only documentType");
	// return repo.findAll(dto.getDocumentType(), pageable);
	//
	// }
	// }

public ResponseEntity<Object> getManageServiceObd(ObdDto dto, Pageable pageable) {
		
		String STP=null;
		
	     if(!ServicesUtils.isEmpty(dto.getStpId()))
	     {
		    STP = listToString(dto.getStpId());
	     }
	     
	     
	     String invo=null;
			
	     if(!ServicesUtils.isEmpty(dto.getInvoiceStatus()))
	     {
		    invo = listToString(dto.getInvoiceStatus());
	     }
		 StringBuffer str=new StringBuffer("select s from SalesOrderHeader s where s.documentType=:docType");
		   
		   if(!ServicesUtils.isEmpty(dto.getSalesHeaderId()))
		   {
			   str.append(" and s.salesHeaderId=:id");
		   }
		   if(!ServicesUtils.isEmpty(dto.getCreatedDateFrom())&&!ServicesUtils.isEmpty(dto.getCreatedDateTo()))
		   {
			   str.append(" and s.createdDate between :stdate and :enddate");
		   }
		   
		   
		   if(!ServicesUtils.isEmpty(dto.getStpId()))
		   {
			   str.append(" and s.soldToParty in (" + STP + ") ");
		   }
		   
		   if(!ServicesUtils.isEmpty(dto.getObdId()))
		   {
		   	str.append(" and s.obdId=:obdId");
		   }
		  
		   
		   if(!ServicesUtils.isEmpty(dto.getObdStatus()))
		   {
			   str.append(" and s.obdStatus=:obdStatus");
		   } 
		   
		   
		   if(!ServicesUtils.isEmpty(dto.getPgiStatus()))
		   {
			   str.append(" and s.pgiStatus=:pgiStatus");
		   } 
		   
		   if(!ServicesUtils.isEmpty(dto.getShipToParty()))
		   {
			   str.append(" and s.shipToParty=:shipToParty");
		   } 
		   
		   
		   
		   
		   if(!ServicesUtils.isEmpty(dto.getInvoiceStatus()))
		   {
			   str.append(" and s.invoiceStatus in (" + invo + ") ");
		   }
		   str.append("  order by createdDate desc");
		   Query q=entityManager.createQuery(str.toString());
		   
		   
		   if(!ServicesUtils.isEmpty(dto.getDocumentType()))
		   {
			   q.setParameter("docType",dto.getDocumentType());
		   }
		   if(!ServicesUtils.isEmpty(dto.getObdId()))
		   {
			   q.setParameter("obdId",dto.getObdId());
		   }
		   if(!ServicesUtils.isEmpty(dto.getSalesHeaderId()))
		   {
			   q.setParameter("id", dto.getSalesHeaderId());
		   }
		   
		   if(!ServicesUtils.isEmpty(dto.getCreatedDateFrom()))
		   {
			   q.setParameter("stdate", dto.getCreatedDateFrom());
		   }
		   if(!ServicesUtils.isEmpty(dto.getCreatedDateTo()))
		   {
			   q.setParameter("enddate", dto.getCreatedDateTo());
		   }
		   if(!ServicesUtils.isEmpty(dto.getObdStatus()))
		{
				   
			   
			     q.setParameter("obdStatus", dto.getObdStatus());
		 }
		   
		   if(!ServicesUtils.isEmpty(dto.getPgiStatus()))
		   {
		   		   
		   	   
		   	     q.setParameter("pgiStatus", dto.getPgiStatus());
		    }
		   if(!ServicesUtils.isEmpty(dto.getShipToParty()))
		   {
		   		   
		   	   
		   	     q.setParameter("shipToParty", dto.getShipToParty());
		    }
		   List<SalesOrderHeader> list=q.getResultList();
		   if(list.size()>0 && list.size()<10)
		   {
			  
			    Root root=new Root();
			   
			   root.setContent( ObjectMapperUtils.mapAll(list, Content.class));
			   root.setFirst(true);
			   root.setLast(true);
			   root.setTotalElements(list.size());
			   root.setNumberOfElements(list.size());
			   root.setTotalPages(1);
			   root.setEmpty(false);
			   root.setNumber(1);
			   root.setSize(10);
			   return ResponseEntity.ok().body(root);
		   }else
		   {
		   
		   
		   
		   
		   
		   int start = (pageable.getPageNumber() - 1) * pageable.getPageSize();
		   int end = (start + pageable.getPageSize()) > list.size() ? list.size() : (pageable.getPageSize() * pageable.getPageNumber());
			Page<SalesOrderHeader>page= new PageImpl<>(list.subList(start, end),pageable,list.size());
		   return ResponseEntity.ok().body(page);
		   }
}

//	public Page<SalesOrderHeader> getManageServiceInvo(InvoDto dto, Pageable pageable) {
//
//		if (!ServicesUtils.isEmpty(dto.getObdId())) {
//			System.err.println("Only OBD");
//			return repo.findAllD(dto.getObdId(), pageable);
//		}
//
//		if (!ServicesUtils.isEmpty(dto.getInvId())) {
//			System.err.println("Only InvoiceNo");
//			return repo.findAllInvId(dto.getInvId(), pageable);
//		}
//
//		if (!ServicesUtil.isEmpty(dto.getSalesHeaderId())) {
//			System.err.println("Only salesHeaderId");
//			return repo.findAllS(dto.getSalesHeaderId(), pageable);
//		}
//
//		if (!ServicesUtils.isEmpty(dto.getStpId())) {
//			if (!ServicesUtil.isEmpty(dto.getInvoiceStatus()) && !ServicesUtil.isEmpty(dto.getCreatedDateFrom())) {
//
//				System.err.println("Only cust+type+status+created");
//				return repo.findAllInv1(dto.getDocumentType(), dto.getStpId(), dto.getInvoiceStatus(),
//						dto.getCreatedDateFrom(), dto.getCreatedDateTo(), pageable);
//
//			} else if (!ServicesUtil.isEmpty(dto.getInvoiceStatus())) {
//				System.err.println("Only cust+type+status");
//				return repo.findAllInv2(dto.getDocumentType(), dto.getStpId(), dto.getInvoiceStatus(), pageable);
//			} else if (!ServicesUtil.isEmpty(dto.getCreatedDateFrom())) {
//				System.err.println("Only cust+type+created");
//				return repo.findAll12(dto.getDocumentType(), dto.getStpId(), dto.getCreatedDateFrom(),
//						dto.getCreatedDateTo(), pageable);
//
//			} else {
//				System.err.println("only type+cust");
//				return repo.findAll123(dto.getDocumentType(), dto.getStpId(), pageable);
//			}
//
//		}
//
//		if (!ServicesUtil.isEmpty(dto.getInvoiceStatus())) {
//			if (!ServicesUtil.isEmpty(dto.getCreatedDateFrom()) && !ServicesUtil.isEmpty(dto.getShipToParty())) {
//				System.err.println("status+request+documentType+creat");
//				return repo.findAllInv6(dto.getDocumentType(), dto.getInvoiceStatus(), dto.getCreatedDateFrom(),
//						dto.getCreatedDateTo(), dto.getShipToParty(), pageable);
//			} else if (!ServicesUtil.isEmpty(dto.getShipToParty()) && ServicesUtil.isEmpty(dto.getCreatedDateFrom())) {
//				System.err.println("status+request+documentType");
//				return repo.findAllInv5(dto.getDocumentType(), dto.getInvoiceStatus(), dto.getShipToParty(), pageable);
//			} else if (!ServicesUtil.isEmpty(dto.getCreatedDateFrom()) && ServicesUtil.isEmpty(dto.getShipToParty())) {
//				// status+created
//				System.err.println("status+created+documentType");
//				return repo.findAllInv4(dto.getDocumentType(), dto.getInvoiceStatus(), dto.getCreatedDateFrom(),
//						dto.getCreatedDateTo(), pageable);
//			} else {
//				// status only
//				System.err.println("Only status+documentType");
//				return repo.findAllInv3(dto.getDocumentType(), dto.getInvoiceStatus(), pageable);
//			}
//
//		} else if (!ServicesUtil.isEmpty(dto.getCreatedDateFrom())) {
//
//			if (!ServicesUtil.isEmpty(dto.getShipToParty())) {
//				System.err.println("Only createdDate+documentType+shipToParty");
//
//				return repo.findAll(dto.getDocumentType(), dto.getCreatedDateFrom(), dto.getCreatedDateTo(),
//						dto.getShipToParty(), pageable);
//			} else {
//				System.err.println("Only createdDate+documentType");
//				return repo.findAll(dto.getDocumentType(), dto.getCreatedDateFrom(), dto.getCreatedDateTo(), pageable);
//			}
//
//		} else if (!ServicesUtil.isEmpty(dto.getShipToParty())) {
//			System.err.println("Only doctype+delivery");
//
//			return repo.findAll1(dto.getDocumentType(), dto.getShipToParty(), pageable);
//
//		} else {
//			System.err.println("Only documentType");
//			return repo.findAll(dto.getDocumentType(), pageable);
//
//		}
//
//	}


public ResponseEntity<Object> getManageServiceInvo(InvoDto dto, Pageable pageable) {

	
	String STP=null;
	
     if(!ServicesUtils.isEmpty(dto.getStpId()))
     {
	    STP = listToString(dto.getStpId());
     }
     
     
     
	 if(!ServicesUtils.isEmpty(dto.getStpId()))
     {
	    STP = listToString(dto.getStpId());
     }
     
	
	 StringBuffer str=new StringBuffer("select s from SalesOrderHeader s where s.documentType=:docType");
	   
	   if(!ServicesUtils.isEmpty(dto.getSalesHeaderId()))
	   {
		   str.append(" and s.salesHeaderId=:id");
	   }
	   if(!ServicesUtils.isEmpty(dto.getCreatedDateFrom())&&!ServicesUtils.isEmpty(dto.getCreatedDateTo()))
	   {
		   str.append(" and s.createdDate between :stdate and :enddate");
	   }
	   
	   
	   if(!ServicesUtils.isEmpty(dto.getStpId()))
	   {
		   str.append(" and s.soldToParty in (" + STP + ") ");
	   }
	   
	   if(!ServicesUtils.isEmpty(dto.getDocumentProcessStatus()))
	   {
	   	str.append(" and s.documentProcessStatus=:status");
	   }
	  
	   
	   if(!ServicesUtils.isEmpty(dto.getCreatedBy()))
			   
	   {
		    str.append(" and s.createdBy=:createdBy");
			   
	   }
	   
	   
	   if(!ServicesUtils.isEmpty(dto.getObdId()))
		   
	   {
		    str.append(" and s.obdId=:obdId");
			   
	   }
	   
	   if(!ServicesUtils.isEmpty(dto.getInvId()))
	   {
		   str.append(" and s.invId=:invId");
	   } 
	   
	   
	   if(!ServicesUtils.isEmpty(dto.getInvoiceStatus()))
		   
	   {
		    str.append(" and s.invoiceStatus=:invoiceStatus");
			   
	   }
	   
	   
       if(!ServicesUtils.isEmpty(dto.getShipToParty()))
		   
	   {
		    str.append(" and s.shipToParty=:shipToParty");
			   
	   }
	   str.append("  order by createdDate desc");
	   Query q=entityManager.createQuery(str.toString());
	   
	   
	   if(!ServicesUtils.isEmpty(dto.getDocumentType()))
	   {
		   q.setParameter("docType",dto.getDocumentType());
	   }
	   if(!ServicesUtils.isEmpty(dto.getDocumentProcessStatus()))
	   {
		   q.setParameter("status",dto.getDocumentProcessStatus());
	   }
	   if(!ServicesUtils.isEmpty(dto.getSalesHeaderId()))
	   {
		   q.setParameter("id", dto.getSalesHeaderId());
	   }
	   
	   if(!ServicesUtils.isEmpty(dto.getCreatedDateFrom()))
	   {
		   q.setParameter("stdate", dto.getCreatedDateFrom());
	   }
	   if(!ServicesUtils.isEmpty(dto.getCreatedDateTo()))
	   {
		   q.setParameter("enddate", dto.getCreatedDateTo());
	   }
	   if(!ServicesUtils.isEmpty(dto.getObdId()))
	{
			   
		   
		     q.setParameter("obdId", dto.getObdId());
	 }
	   if(!ServicesUtils.isEmpty(dto.getShipToParty()))
		{
				   
			   
			     q.setParameter("shipToParty", dto.getShipToParty());
		 }
	   
	   if(!ServicesUtils.isEmpty(dto.getInvId()))
	   {
	   		   
	   	   
	   	     q.setParameter("invId", dto.getInvId());
	    }
	   
	   
	   
	   if(!ServicesUtils.isEmpty(dto.getInvoiceStatus()))
	   {
	   		   
	   	   
	   	     q.setParameter("invoiceStatus", dto.getInvoiceStatus());
	    }
	   
	   if(!ServicesUtils.isEmpty(dto.getCreatedBy()))
	   {
	   		   
	   	   
	   	     q.setParameter("createdBy", dto.getCreatedBy());
	    }
	   List<SalesOrderHeader> list=q.getResultList();
	   if(list.size()>0 && list.size()<10)
	   {
		  
		    Root root=new Root();
		   
		   root.setContent( ObjectMapperUtils.mapAll(list, Content.class));
		   root.setFirst(true);
		   root.setLast(true);
		   root.setTotalElements(list.size());
		   root.setNumberOfElements(list.size());
		   root.setTotalPages(1);
		   root.setEmpty(false);
		   root.setNumber(1);
		   root.setSize(10);
		   return ResponseEntity.ok().body(root);
	   }else
	   {
	   
	   
	   
	   
	   
	   int start = (pageable.getPageNumber() - 1) * pageable.getPageSize();
	   int end = (start + pageable.getPageSize()) > list.size() ? list.size() : (pageable.getPageSize() * pageable.getPageNumber());
		Page<SalesOrderHeader>page= new PageImpl<>(list.subList(start, end),pageable,list.size());
	   return ResponseEntity.ok().body(page);
	   }
	   
}

public ResponseEntity<Object> getManageService(HeaderDetailUIDto dto, Pageable pageable) {

		String strsales = dto.getSalesHeaderId();
		if (!ServicesUtils.isEmpty(dto.getSalesHeaderId())) {
			if (dto.getSalesHeaderId().length() < 10) {
				int l = dto.getSalesHeaderId().length();
				strsales = dto.getSalesHeaderId();
				while (l < 10) {
					strsales = "0" + strsales;
					l++;
				}

			}
		}
		dto.setSalesHeaderId(strsales);
		System.err.println(dto.getCreatedDateFrom());
		System.err.println(dto.getSalesHeaderId());
		String DOC = null;

		if (!ServicesUtils.isEmpty(dto.getStpId())) {
			DOC = listToString(dto.getStpId());
		}

		StringBuffer str = new StringBuffer("select s from SalesOrderHeader s where s.documentType=:docType");

		if (!ServicesUtils.isEmpty(dto.getSalesHeaderId())) {
			str.append(" and s.salesHeaderId=:id");
		}
		if (!ServicesUtils.isEmpty(dto.getCreatedDateFrom()) && !ServicesUtils.isEmpty(dto.getCreatedDateTo())) {
			str.append(" and s.createdDate between :stdate and :enddate");
		}

		if (!ServicesUtils.isEmpty(dto.getStpId())) {
			str.append(" and s.soldToParty in (" + DOC + ") ");
		}

		if (!ServicesUtils.isEmpty(dto.getDocumentProcessStatus())) {
			str.append(" and s.documentProcessStatus in :list");
		}

		if (!ServicesUtils.isEmpty(dto.getRequestDeliveryDateFrom())
				&& !ServicesUtils.isEmpty(dto.getRequestDeliveryDateTo())) {
			str.append(" and s.requestDeliveryDate between :st1date and :end1date");
		}
		str.append("  order by createdDate desc");
		Query q = entityManager.createQuery(str.toString());

		if (!ServicesUtils.isEmpty(dto.getDocumentType())) {
			q.setParameter("docType", dto.getDocumentType());
		}
		if (!ServicesUtils.isEmpty(dto.getDocumentProcessStatus())) {
			q.setParameter("list", dto.getDocumentProcessStatus());
		}
		if (!ServicesUtils.isEmpty(dto.getSalesHeaderId())) {
			q.setParameter("id", dto.getSalesHeaderId());
		}

		if (!ServicesUtils.isEmpty(dto.getCreatedDateFrom())) {
			q.setParameter("stdate", dto.getCreatedDateFrom());
		}
		if (!ServicesUtils.isEmpty(dto.getCreatedDateTo())) {
			q.setParameter("enddate", dto.getCreatedDateTo());
		}
		if (!ServicesUtils.isEmpty(dto.getRequestDeliveryDateFrom())) {

			q.setParameter("st1date", dto.getRequestDeliveryDateFrom());
		}

		if (!ServicesUtils.isEmpty(dto.getRequestDeliveryDateTo())) {

			q.setParameter("end1date", dto.getRequestDeliveryDateTo());
		}
		List<SalesOrderHeader> list = q.getResultList();
		if (list.size() > 0 && list.size() < 10) {

			Root root = new Root();

			root.setContent(ObjectMapperUtils.mapAll(list, Content.class));
			root.setFirst(true);
			root.setLast(true);
			root.setTotalElements(list.size());
			root.setNumberOfElements(list.size());
			root.setTotalPages(1);
			root.setEmpty(false);
			root.setNumber(1);
			root.setSize(10);
			return ResponseEntity.ok().body(root);
		} else {

			int start = (pageable.getPageNumber() - 1) * pageable.getPageSize();
			int end = (start + pageable.getPageSize()) > list.size() ? list.size()
					: (pageable.getPageSize() * pageable.getPageNumber());
			Page<SalesOrderHeader> page = new PageImpl<>(list.subList(start, end), pageable, list.size());
			return ResponseEntity.ok().body(page);
		}

		
	}

	// Sandeep Kumar get a already drafted enquiry,quotation,sales order
	@SuppressWarnings("unchecked")
	public List<SalesOrderHeaderItemDto> getDraftedVersion(HeaderDetailUIDto dto) {
		List<SalesOrderHeaderItemDto> responseList = new ArrayList<>();
		List<SalesOrderHeader> headerEntityList = new ArrayList<>();
		List<SalesOrderItem> lineItemEntityList = new ArrayList<>();
		try {
			StringBuffer headerQuery = new StringBuffer(
					"select s from SalesOrderHeader s where s.documentType=:documentType and s.documentProcessStatus=:documentProcessStatus");
			if (!ServicesUtil.isEmpty(dto.getCreatedBy()))
				headerQuery.append(" and s.createdBy=:createdBy");
			if (!ServicesUtil.isEmpty(dto.getStpId())) {
				String STP = listToString(dto.getStpId());
				headerQuery.append(" and s.soldToParty in (" + STP + ")");
			}
			if (!ServicesUtil.isEmpty(dto.getSalesGroup()))
				headerQuery.append(" and s.salesGroup=:salesGroup");
			Query hq = entityManager.createQuery(headerQuery.toString());
			if (!ServicesUtil.isEmpty(dto.getCreatedBy()))
				hq.setParameter("createdBy", dto.getCreatedBy());
			hq.setParameter("documentType", dto.getDocumentType());

			hq.setParameter("documentProcessStatus", EnOrderActionStatus.DRAFTED);
			if (!ServicesUtil.isEmpty(dto.getSalesGroup()))
				hq.setParameter("salesGroup", dto.getSalesGroup());

			headerEntityList = hq.getResultList();
			for (SalesOrderHeader headerEntity : headerEntityList) {
				SalesOrderHeaderItemDto salesHeaderItemDto = new SalesOrderHeaderItemDto();
				List<SalesOrderItemDto> lineItemDtoList = new ArrayList<>();
				SalesOrderHeaderDto headerDto = new SalesOrderHeaderDto();
				headerDto = ObjectMapperUtils.map(headerEntity, SalesOrderHeaderDto.class);
				salesHeaderItemDto.setHeaderDto(headerDto);
				try {
					String lineItemQuery = "select i from SalesOrderItem i where i.salesHeaderId=:salesHeaderId order by i.lineItemNumber asc";
					Query iq = entityManager.createQuery(lineItemQuery);
					iq.setParameter("salesHeaderId", headerDto.getSalesHeaderId());
					lineItemEntityList = iq.getResultList();
					for (SalesOrderItem lineItemEntity : lineItemEntityList) {
						SalesOrderItemDto lineItemDto = new SalesOrderItemDto();
						lineItemDto = ObjectMapperUtils.map(lineItemEntity, SalesOrderItemDto.class);
						if (!ServicesUtil.isEmpty(lineItemDto.getQualityTest()))
							lineItemDto.setQualityTestList(setQualityTest(lineItemDto.getQualityTest()));
						else
							lineItemDto.setQualityTestList(new ArrayList<String>());

						if (!ServicesUtil.isEmpty(lineItemDto.getDefaultQualityTest()))
							lineItemDto.setDefaultQualityTestList(setQualityTest(lineItemDto.getDefaultQualityTest()));
						else
							lineItemDto.setDefaultQualityTestList(new ArrayList<String>());

						lineItemDtoList.add(lineItemDto);
					}
					salesHeaderItemDto.setLineItemList(lineItemDtoList);

				} catch (Exception e) {
					System.err.println("try found exception");
					e.printStackTrace();
				}
				responseList.add(salesHeaderItemDto);
			}
		} catch (Exception e) {
			System.err.println("try found exception");
			e.printStackTrace();
		}

		return responseList;
	}





//	public String getItems(String salesHeaderId) {
//
//		List<SalesOrderHeader> headerEntityList = new ArrayList<>();
//		List<SalesOrderItem> lineItemEntityList = new ArrayList<>();
//		List<String> listItemNo = new ArrayList<>();
//		String result = "";
//		try {
//			StringBuffer str = new StringBuffer("from SalesOrderItem s where s.salesHeaderId=:salesHeaderId");
//
//			Query q = entityManager.createQuery(str.toString());
//
//			if (!ServicesUtils.isEmpty(salesHeaderId)) {
//				q.setParameter("salesHeaderId", salesHeaderId);
//			}
//
//			List<SalesOrderItem> item = q.getResultList();
//			System.err.println("item.size() " + item.size());
//
//			for (SalesOrderItem i : item) {
//
//				listItemNo.add(i.getLineItemNumber());
//
//				System.err.println("i.getItemNumber()" + i.getLineItemNumber());
//			}
//
//			for (String s : listItemNo) {
//				if (s.length() > 1) {
//					int i = s.length();
//					result = s.substring(i - 1);
//				}
//			}
//			System.err.println("result" + result);
//
//			return result;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//
//	}
//
//	@SuppressWarnings("unchecked")
//	public SalesOrderHeaderItemDto getHeaderById(HeaderIdDto dto) {
//
//		List<SalesOrderHeader> headerEntityList = new ArrayList<>();
//		List<SalesOrderItem> lineItemEntityList = new ArrayList<>();
//		try {
//			StringBuffer headerQuery = new StringBuffer("select s from SalesOrderHeader s where ");
//
//			if (!ServicesUtil.isEmpty(dto.getSalesHeaderId())) {
//
//				headerQuery.append("s.salesHeaderId=:salesHeaderId");
//
//			}
//
//			Query hq = entityManager.createQuery(headerQuery.toString());
//
//			if (!ServicesUtil.isEmpty(dto.getSalesHeaderId()))
//				hq.setParameter("salesHeaderId", dto.getSalesHeaderId());
//
//			headerEntityList = hq.getResultList();
//
//			SalesOrderHeader headerEntity = new SalesOrderHeader();
//			if (headerEntityList.size() > 0) {
//				headerEntity = headerEntityList.get(0);
//			}
//
//			SalesOrderHeaderItemDto salesHeaderItemDto = new SalesOrderHeaderItemDto();
//			List<SalesOrderItemDto> lineItemDtoList = new ArrayList<>();
//
//			SalesOrderHeaderDto headerDto = new SalesOrderHeaderDto();
//
//			headerDto = ObjectMapperUtils.map(headerEntity, SalesOrderHeaderDto.class);
//
//			salesHeaderItemDto.setHeaderDto(headerDto);
//
//			try {
//
//				StringBuffer lineItemQuery = new StringBuffer("select i from SalesOrderItem i where");
//
//				lineItemQuery.append(" i.salesHeaderId=:salesHeaderId");
//
//				Query iq = entityManager.createQuery(lineItemQuery.toString());
//
//				if (!ServicesUtils.isEmpty(headerDto.getSalesHeaderId())) {
//					iq.setParameter("salesHeaderId", headerDto.getSalesHeaderId());
//				}
//
//				lineItemEntityList = iq.getResultList();
//				System.err.println("lineItemEntityList size" + lineItemEntityList.size());
//
//				for (SalesOrderItem lineItemEntity : lineItemEntityList) {
//					SalesOrderItemDto lineItemDto = new SalesOrderItemDto();
//					lineItemDto = ObjectMapperUtils.map(lineItemEntity, SalesOrderItemDto.class);
//					System.err.println(lineItemDto.getSalesHeaderId());
//					if (lineItemDto.getLineItemNumber().equalsIgnoreCase(getItems(lineItemDto.getSalesHeaderId()))) {
//
//						System.err.println("Hello");
//						continue;
//					} else {
//						if (lineItemDto.getOutBoundOrderId() != null) {
//							StringBuffer str = new StringBuffer(
//									"from SalesOrderHeader s where s.obdId=:id and s.obdStatus=:CREATED");
//							Query q = entityManager.createQuery(str.toString());
//							q.setParameter("id", lineItemDto.getOutBoundOrderId());
//							q.setParameter("CREATED", "CREATED");
//							List<SalesOrderHeader> l = q.getResultList();
//							SalesOrderHeader s = new SalesOrderHeader();
//							if (l.size() > 0) {
//								s = l.get(0);
//
//							}
//
//							if (!ServicesUtils.isEmpty(s.getObdStatus())) {
//								lineItemDto.setObdStatus(s.getObdStatus());
//
//							}
//
//						}
//						if (lineItemDto.getPgiId() != null) {
//							StringBuffer str = new StringBuffer(
//									"from SalesOrderHeader s where s.pgiId=:id and s.pgiStatus=:CREATED");
//							Query q = entityManager.createQuery(str.toString());
//							q.setParameter("id", lineItemDto.getPgiId());
//							q.setParameter("CREATED", "CREATED");
//							List<SalesOrderHeader> l = q.getResultList();
//							SalesOrderHeader s = new SalesOrderHeader();
//							if (l.size() > 0) {
//								s = l.get(0);
//							}
//							if (!ServicesUtils.isEmpty(s.getPgiStatus())) {
//								lineItemDto.setPgiStatus(s.getPgiStatus());
//							}
//						}
//						if (lineItemDto.getInvId() != null) {
//							StringBuffer str = new StringBuffer(
//									"from SalesOrderHeader s where s.invId=:id and s.invoiceStatus=:CREATED");
//							Query q = entityManager.createQuery(str.toString());
//							q.setParameter("id", lineItemDto.getInvId());
//							q.setParameter("CREATED", "CREATED");
//							List<SalesOrderHeader> l = q.getResultList();
//							SalesOrderHeader s = new SalesOrderHeader();
//							if (l.size() > 0) {
//								s = l.get(0);
//							}
//							if (!ServicesUtils.isEmpty(s.getInvoiceStatus())) {
//								lineItemDto.setInvoiceStatus(s.getInvoiceStatus());
//
//							}
//
//						}
//
//						if (lineItemDto.getInspection() != null)
//							lineItemDto.setQualityTestList(setQualityTest("3.2 INSPECTION"));
//						// else if(lineItemDto.getBendTest())
//						// lineItemDto.setQualityTestList(setQualityTest("BEND
//						// TEST"));
//						// else if(lineItemDto.getHardnessTest())
//						// lineItemDto.setQualityTestList(setQualityTest("HARDNESS
//						// TEST"));
//						// else if(lineItemDto.getIsElementBoronRequired())
//						// lineItemDto.setQualityTestList(setQualityTest("BORON
//						// REQUIRED"));
//						// else if(lineItemDto.getImpactTest())
//						// lineItemDto.setQualityTestList(setQualityTest("IMPACT
//						// TEST"));
//						// else
//						// lineItemDto.setQualityTestList(new ArrayList<>());
//
//						lineItemDtoList.add(lineItemDto);
//
//					}
//				}
//
//				salesHeaderItemDto.setLineItemList(lineItemDtoList);
//
//			} catch (Exception e) {
//				System.err.println("try found exception123");
//				e.printStackTrace();
//
//			}
//			System.err.println(salesHeaderItemDto);
//			return salesHeaderItemDto;
//
//		} catch (Exception e) {
//			System.err.println("try found exception");
//			e.printStackTrace();
//			return null;
//		}
//
//	}

	public String getItems(String salesHeaderId)
	{
		
	
	List<SalesOrderHeader> headerEntityList = new ArrayList<>();
	List<SalesOrderItem> lineItemEntityList = new ArrayList<>();
	List<String>listItemNo=new ArrayList<>();
	String result = null;
	try{
    	StringBuffer str=new StringBuffer("from SalesOrderItem s where s.salesHeaderId=:salesHeaderId");
    	
    	Query q=entityManager.createQuery(str.toString());
    	
    	if(!ServicesUtils.isEmpty(salesHeaderId))
    	{
    		q.setParameter("salesHeaderId",salesHeaderId);
    	}
    	
    	List<SalesOrderItem> item=q.getResultList();
    	System.err.println("item.size() "+item.size());
    	
    	for(SalesOrderItem i:item)
    	{
    		if(!ServicesUtils.isEmpty(i.getLineItemNumber())){
    		listItemNo.add(i.getLineItemNumber());
    		}
    		else
    			continue;
    		
    		System.err.println("i.getItemNumber()"+i.getLineItemNumber());
    	}
    	System.err.println("istItemNo.size()"+listItemNo.size());
    	if(listItemNo.size()>0){
    	for(String s:listItemNo)
    	{
    		if(s.length()>1)
    		{
    			int i=s.length();
    			result=s.substring(i-1);
    		}
    	}
    	}
    	System.err.println("result"+result);
    			
    	return result;		
    }catch(Exception e)
	{
		e.printStackTrace();
		return null;
	}
		
	}


	
	@SuppressWarnings("unchecked")
	public SalesOrderHeaderItemDto getHeaderById(HeaderIdDto dto) {

		List<SalesOrderHeader> headerEntityList = new ArrayList<>();
		List<SalesOrderItem> lineItemEntityList = new ArrayList<>();
		try {
			StringBuffer headerQuery = new StringBuffer("select s from SalesOrderHeader s where ");
		
			if (!ServicesUtil.isEmpty(dto.getSalesHeaderId())) {
				
					headerQuery.append("s.salesHeaderId=:salesHeaderId");

			}

			Query hq = entityManager.createQuery(headerQuery.toString());
			
			if (!ServicesUtil.isEmpty(dto.getSalesHeaderId()))
				hq.setParameter("salesHeaderId", dto.getSalesHeaderId());
			
			headerEntityList = hq.getResultList();
			
			
            SalesOrderHeader headerEntity=new SalesOrderHeader();
			if(headerEntityList.size()>0)
			{
				headerEntity=headerEntityList.get(0);
			}
		
				SalesOrderHeaderItemDto salesHeaderItemDto = new SalesOrderHeaderItemDto();
				List<SalesOrderItemDto> lineItemDtoList = new ArrayList<>();
				
				SalesOrderHeaderDto headerDto = new SalesOrderHeaderDto();
				
				headerDto = ObjectMapperUtils.map(headerEntity, SalesOrderHeaderDto.class);

				salesHeaderItemDto.setHeaderDto(headerDto);

				
				try {
					
					StringBuffer lineItemQuery = new StringBuffer("select i from SalesOrderItem i where");
					
				
                     lineItemQuery.append(" i.salesHeaderId=:salesHeaderId");
					
				
					Query iq = entityManager.createQuery(lineItemQuery.toString());
				
					if(!ServicesUtils.isEmpty(headerDto.getSalesHeaderId()))
					{
						iq.setParameter("salesHeaderId",headerDto.getSalesHeaderId());
					}

					lineItemEntityList = iq.getResultList();
					System.err.println("lineItemEntityList size"+lineItemEntityList.size()	);

					
					
					for (SalesOrderItem lineItemEntity : lineItemEntityList) {
						SalesOrderItemDto lineItemDto = new SalesOrderItemDto();
						lineItemDto = ObjectMapperUtils.map(lineItemEntity, SalesOrderItemDto.class);
						System.err.println(lineItemDto.getSalesHeaderId());
					if(!ServicesUtils.isEmpty(getItems(lineItemDto.getSalesHeaderId()))){
						if(lineItemDto.getLineItemNumber().equalsIgnoreCase(getItems(lineItemDto.getSalesHeaderId())))
						{
							
							System.err.println("Hello");
							continue;
						}
						else
						{
						if(lineItemDto.getOutBoundOrderId()!=null )
						{
							StringBuffer str=new StringBuffer("from SalesOrderHeader s where s.obdId=:id and s.obdStatus=:CREATED");
                            Query q=entityManager.createQuery(str.toString());
	                        q.setParameter("id", lineItemDto.getOutBoundOrderId());
                             q.setParameter("CREATED", "CREATED");
	                        List<SalesOrderHeader> l=q.getResultList();
	                        SalesOrderHeader s=new SalesOrderHeader();
                           if(l.size()>0)
	                        {
	                            s=l.get(0);
	                           
	                        }
                          
	                        if(!ServicesUtils.isEmpty(s.getObdStatus()))
	                        {
	                        	lineItemDto.setObdStatus(s.getObdStatus());
	                        	
                            }
	                        
							
						}
						if(lineItemDto.getPgiId()!=null )
					     {
					 		StringBuffer str=new StringBuffer("from SalesOrderHeader s where s.pgiId=:id and s.pgiStatus=:CREATED");
	                        Query q=entityManager.createQuery(str.toString());
	                        q.setParameter("id", lineItemDto.getPgiId());
	                        q.setParameter("CREATED", "CREATED");
	                        List<SalesOrderHeader> l=q.getResultList();
	                        SalesOrderHeader s=new SalesOrderHeader();
	                        if(l.size()>0)
	                        {
                              s=l.get(0);
	                        }
	                        if(!ServicesUtils.isEmpty(s.getPgiStatus()))
	                        {
	                        	lineItemDto.setPgiStatus(s.getPgiStatus());	                        		                        }						
						}
						if(lineItemDto.getInvId()!=null )
						{
						   StringBuffer str=new StringBuffer("from SalesOrderHeader s where s.invId=:id and s.invoiceStatus=:CREATED");
	                        Query q=entityManager.createQuery(str.toString());
	                        q.setParameter("id", lineItemDto.getInvId());
	                        q.setParameter("CREATED", "CREATED");
                          List<SalesOrderHeader> l=q.getResultList();
                          SalesOrderHeader s=new SalesOrderHeader();
	                        if(l.size()>0)
                               {
	                            s=l.get(0);
	                        }
	                        if(!ServicesUtils.isEmpty(s.getInvoiceStatus()))
                             {
	                        	lineItemDto.setInvoiceStatus(s.getInvoiceStatus());
	                        	
                             }
							
						  }
						
				if (lineItemDto.getInspection()!=null)
						lineItemDto.setQualityTestList(setQualityTest("3.2 INSPECTION"));
//						else if(lineItemDto.getBendTest())
//							lineItemDto.setQualityTestList(setQualityTest("BEND TEST"));
//						else if(lineItemDto.getHardnessTest())
//							lineItemDto.setQualityTestList(setQualityTest("HARDNESS TEST"));
//						else if(lineItemDto.getIsElementBoronRequired())
//							lineItemDto.setQualityTestList(setQualityTest("BORON REQUIRED"));
//						else if(lineItemDto.getImpactTest())
//							lineItemDto.setQualityTestList(setQualityTest("IMPACT TEST"));
//						else
//							lineItemDto.setQualityTestList(new ArrayList<>());
						
						
				

						
					}
						lineItemDtoList.add(lineItemDto);
						}
					else
					lineItemDtoList.add(lineItemDto);
					
					}

					salesHeaderItemDto.setLineItemList(lineItemDtoList);
					
				} catch (Exception e) {
					System.err.println("try found exception123");
					e.printStackTrace();
					
				}
				System.err.println(salesHeaderItemDto);
				return salesHeaderItemDto;
			
		} catch (Exception e) {
			System.err.println("try found exception");
			e.printStackTrace();
			return null;
		}
		
	}
	public static String listToString(List<String> list) {
		String response = "";
		try {
			for (String s : list) {
				response = "'" + s + "', " + response;
			}
			response = response.substring(0, response.length() - 2);
		} catch (Exception e) {
			System.err.println("[SalesHeaderDao][listToString] Exception : " + e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	// Sandeep KUmar
	public static List<String> setQualityTest(String qualityTest) {
		ArrayList<String> qualityTestList = null;
		try {
			qualityTestList = new ArrayList<>();
			String[] str = qualityTest.split(",");
			for (String s : str)
				qualityTestList.add(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qualityTestList;
	}

	// Sandeep KUmar NULL value is not compared to boolean give exceptions.
	public static List<String> setQualityTest(SalesOrderMaterialMasterDto dto) {
		ArrayList<String> qualityTestList = new ArrayList<>();
		if (dto.getBendTest() != null && dto.getBendTest() == true)
			qualityTestList.add("BT");
		if (dto.getImpactTest() != null && dto.getImpactTest() == true)
			qualityTestList.add("IT");
		if (dto.getUltraLightTest() != null && dto.getUltraLightTest() == true)
			qualityTestList.add("UL");
		return qualityTestList;
	}

	public SalesOrderHeaderItemDto getByObdId(String obdId) {
		List<SalesOrderHeader> headerEntityList = new ArrayList<>();
		List<SalesOrderItem> lineItemEntityList = new ArrayList<>();
		try {
			StringBuffer headerQuery = new StringBuffer("select s from SalesOrderHeader s where s.obdId=:obdId ");

			Query hq = entityManager.createQuery(headerQuery.toString());

			hq.setParameter("obdId", obdId);

			headerEntityList = hq.getResultList();

			;
			for (SalesOrderHeader headerEntity : headerEntityList) {
				SalesOrderHeaderItemDto salesHeaderItemDto = new SalesOrderHeaderItemDto();
				List<SalesOrderItemDto> lineItemDtoList = new ArrayList<>();
				SalesOrderHeaderDto headerDto = new SalesOrderHeaderDto();
				headerDto = ObjectMapperUtils.map(headerEntity, SalesOrderHeaderDto.class);

				salesHeaderItemDto.setHeaderDto(headerDto);

				System.err.println(salesHeaderItemDto.toString());
				try {
					System.err.println("try found exception");
					StringBuffer lineItemQuery = new StringBuffer("select i from SalesOrderItem i where ");
					if (!ServicesUtil.isEmpty(headerDto.getS4DocumentId()))
						lineItemQuery.append("i.salesOrderHeader.s4DocumentId=:s4DocumentId");
					else
						lineItemQuery.append("i.salesHeaderId=:salesHeaderId");
					System.err.println("try found exception 3");
					/*
					 * lineItemQuery.append(" and i.updateIndicator !=" +
					 * EnUpdateIndicator.DELETE.ordinal() +
					 * " order by i.lineItemNumber asc");
					 */

					System.err.println(lineItemQuery.toString());
					Query iq = entityManager.createQuery(lineItemQuery.toString());
					if (!ServicesUtil.isEmpty(headerDto.getS4DocumentId())) {

						iq.setParameter("s4DocumentId", headerDto.getS4DocumentId());
					} else {
						iq.setParameter("salesHeaderId", headerDto.getSalesHeaderId());
					}

					lineItemEntityList = iq.getResultList();
					System.err.println(lineItemEntityList.toString());
					for (SalesOrderItem lineItemEntity : lineItemEntityList) {
						SalesOrderItemDto lineItemDto = new SalesOrderItemDto();
						System.out.println("***1**");
						lineItemDto = ObjectMapperUtils.map(lineItemEntity, SalesOrderItemDto.class);
						System.out.println("***2**");
						if (!ServicesUtil.isEmpty(lineItemDto.getQualityTest()))
							lineItemDto.setQualityTestList(setQualityTest(lineItemDto.getQualityTest()));
						else
							lineItemDto.setQualityTestList(new ArrayList<String>());
						if (!ServicesUtil.isEmpty(lineItemDto.getDefaultQualityTest()))
							lineItemDto.setDefaultQualityTestList(setQualityTest(lineItemDto.getDefaultQualityTest()));
						else
							lineItemDto.setDefaultQualityTestList(new ArrayList<String>());
						lineItemDtoList.add(lineItemDto);

						System.out.println("***3**");
					}

					salesHeaderItemDto.setLineItemList(lineItemDtoList);
					System.out.println("***5**");
				} catch (Exception e) {
					System.err.println("try found exception123");
					e.printStackTrace();
				}
				System.err.println(salesHeaderItemDto.toString());
				return salesHeaderItemDto;
			}
		} catch (Exception e) {
			System.err.println("try found exception");
			e.printStackTrace();
			return null;
		}
		return null;

	}

	public List<SalesOrderItemDto> getDetails(String salesHeaderId) {
		System.err.println("salesHeaderId " + salesHeaderId);
		String query1 = "from SalesOrderHeader where salesHeaderId=:sid and documentType=:dType";
		Query q1 = entityManager.createQuery(query1);
		q1.setParameter("sid", salesHeaderId);
		String docType = "OR";
		q1.setParameter("dType", docType);

		List<SalesOrderHeader> list1 = q1.getResultList();
		System.err.println("list1 size " + list1.size());
		String query2 = "from SalesOrderItem i where i.salesOrderHeader.s4DocumentId=:s4docId";
		Query q2 = entityManager.createQuery(query2);
		q2.setParameter("s4docId", list1.get(0).getS4DocumentId());
		List<SalesOrderItem> list2 = q2.getResultList();
		System.err.println("list2 size " + list2.size());

		List<SalesOrderItemDto> result = new ArrayList<>();

		System.err.println("list2 size " + list2.size());
		for (SalesOrderItem item1 : list2) {
			SalesOrderItemDto item = ObjectMapperUtils.map(item1, SalesOrderItemDto.class);
			String query3 = "from SalesOrderItem i where i.orderItemId=:oid";
			Query q3 = entityManager.createQuery(query3);
			q3.setParameter("oid", item.getSalesItemId());
			List<SalesOrderItem> list3 = q3.getResultList();
			System.err.println("list3 size" + list3.size());

			for (SalesOrderItem item2 : list3) {
				if ((item2.getOutBoundOrderId() != null) && item2.getOutBoundOrderId().substring(0, 3).equals("OBD")) {
					list2.remove(item2);
					continue;
				}
				if ((item2.getPgiId() != null) && item2.getPgiId().substring(0, 3).equals("PGI")) {
					list2.remove(item2);
					continue;
				}
				if ((item2.getInvId() != null) && item2.getInvId().substring(0, 3).equals("INV")) {
					list2.remove(item2);
					continue;
				}

			}

			if (list3.size() > 0) {
				item.setOutBoundOrderId(list3.get(0).getOutBoundOrderId());
				item.setPgiId(list3.get(0).getPgiId());
				item.setInvId(list3.get(0).getInvId());
			}

			result.add(item);
		}

		return result;

	}

	public TrackSOUIDto getSOData(HeaderIdDto dto) {
		List<SalesOrderHeader> headerEntityList = new ArrayList<>();
		List<SalesOrderItem> lineItemEntityList = new ArrayList<>();
		int flagobd = 0;
		int flagpgi = 0;
		int flaginvoice = 0;
		String pgiStatus;
		int level = -1;

		TrackSOUIDto track = new TrackSOUIDto();
		DocDto res = null;
		List<DocDto> res1 = new ArrayList<>();

		try {
			StringBuffer headerQuery = new StringBuffer(
					"from SalesOrderHeader s where s.salesHeaderId=:salesHeaderId and documentType=:dType");
			Query hq = entityManager.createQuery(headerQuery.toString());
			hq.setParameter("salesHeaderId", dto.getSalesHeaderId());
			hq.setParameter("dType", "OR");
			headerEntityList = hq.getResultList();
			System.err.println("headerEntityList size " + headerEntityList.size());
			System.err.println("headerEntity" + headerEntityList.toString());

			SalesOrderHeader headerEntity = null;
			if (headerEntityList.size() > 0)
				headerEntity = headerEntityList.get(0);

			List<SalesOrderItemDto> lineItemDtoList = new ArrayList<>();
			SalesOrderHeaderDto headerDto = new SalesOrderHeaderDto();
			headerDto = ObjectMapperUtils.map(headerEntity, SalesOrderHeaderDto.class);

			track.setHeaderDto(headerDto);
			res = new DocDto();
			res.setCreatedBy(headerDto.getCreatedBy());
			res.setCreatedDate(headerDto.getCreatedDate());
			res.setDocumentId(headerDto.getSalesHeaderId());
			res.setDocumentType("Sales Order");
			res1.add(res);
			System.err.println("track" + track.toString());
			List<SalesOrderItemDto> list = getDetails(dto.getSalesHeaderId());
			try {

				int length = list.size();

				for (SalesOrderItemDto lineItemDto : list) {

					System.err.println("inside Item");
					if (!ServicesUtils.isEmpty(lineItemDto.getPgiId())) {
						lineItemDto.setPgiStatus("CREATED");
					}
					System.out.println(lineItemDto.toString());

					if (lineItemDto.getOutBoundOrderId() != null) {
						res = new DocDto();
						StringBuffer str = new StringBuffer(
								"from SalesOrderHeader s where s.obdId=:id and s.obdStatus=:" + "CREATED");
						Query q = entityManager.createQuery(str.toString());
						q.setParameter("id", lineItemDto.getOutBoundOrderId());
						q.setParameter("CREATED", "CREATED");
						List<SalesOrderHeader> l = q.getResultList();
						SalesOrderHeader s = new SalesOrderHeader();
						if (l.size() > 0) {
							s = l.get(0);
						}
						res.setCreatedBy(s.getCreatedBy());
						res.setCreatedDate(s.getCreatedDate());
						res.setDocumentType("OBD");
						res.setDocumentId(lineItemDto.getOutBoundOrderId());
						res1.add(res);
						flagobd++;
						level = 0;
					}
					if (lineItemDto.getPgiId() != null) {
						flagpgi++;
						// res = new DocDto();
						// res.setCreatedBy(lineItemDto.getCreatedBy());
						// res.setCreatedDate(lineItemDto.getCreatedOn());
						// res.setDocumentType("PGI");
						// res.setDocumentId(lineItemDto.getPgiId());
						// res1.add(res);
					}
					if (lineItemDto.getInvId() != null) {
						res = new DocDto();
						flaginvoice++;
						StringBuffer str = new StringBuffer(
								"from SalesOrderHeader s where s.invId=:id and invoiceStatus=:CREATED");
						Query q = entityManager.createQuery(str.toString());
						q.setParameter("id", lineItemDto.getInvId());
						q.setParameter("CREATED", "CREATED");
						List<SalesOrderHeader> l = q.getResultList();
						SalesOrderHeader s = new SalesOrderHeader();
						if (l.size() > 0) {
							s = l.get(0);
						}
						res.setCreatedBy(s.getCreatedBy());
						res.setCreatedDate(s.getCreatedDate());
						res.setDocumentType("Invoice");
						res.setDocumentId(lineItemDto.getInvId());
						res1.add(res);
					}
					if (lineItemDto.getOutBoundOrderId() != null && lineItemDto.getPgiId() != null) {
						level = 1;
					} else if (lineItemDto.getOutBoundOrderId() != null && lineItemDto.getPgiId() != null
							&& lineItemDto.getInvId() != null) {
						level = 2;
					} else
						level = -1;

					lineItemDtoList.add(lineItemDto);
					System.err.println("lineItemDto");

				}

				track.setLevel(level);
				track.setLineItemList(lineItemDtoList);
				track.setResults(res1);

				if (flagobd == 0) {
					track.setObdStatus("Pending");
				} else if (flagobd == length) {
					track.setObdStatus("Completed");
				} else
					track.setObdStatus("PartiallyCompleted");

				if (flagpgi == 0) {
					track.setPgiStatus("Pending");
				} else if (flagpgi == length) {
					track.setPgiStatus("Completed");
				} else
					track.setPgiStatus("PartiallyCompleted");

				if (flaginvoice == 0) {
					track.setInvoiceStatus("Pending");
				} else if (flaginvoice == length) {
					track.setInvoiceStatus("Completed");
				} else
					track.setInvoiceStatus("PartiallyCompleted");

				if (flaginvoice == length) {
					track.setHeaderStatus("Completed");
				} else if (flagobd != 0) {
					track.setHeaderStatus("InProcess");
				} else
					track.setHeaderStatus("CREATED");
				if (flaginvoice == length) {
					track.setDeliveryStatus("Partially Completed");

				} else {
					track.setDeliveryStatus("Pending");
				}

			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

		return track;
	}

}
