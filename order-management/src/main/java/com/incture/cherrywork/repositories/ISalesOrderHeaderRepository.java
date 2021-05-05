package com.incture.cherrywork.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.sales.constants.EnOrderActionStatus;

@Repository
public interface ISalesOrderHeaderRepository extends JpaRepository<SalesOrderHeader, String>,
		QuerydslPredicateExecutor<SalesOrderHeader>, ISalesOrderHeaderCustomRepository {
	
	

//	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.invoiceNo=:invoiceNo order by s.createdDate desc")
//	public Page<SalesOrderHeader> findAllI(@Param("documentType") String documentType,@Param("invoiceNo") String invoiceNo,Pageable pageable);
//	
//	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.DeliveryNo=:deliveryNo order by s.createdDate desc")
//	public Page<SalesOrderHeader> findAllD(@Param("documentType") String documentType,@Param("deliveryNo") String deliveryNo,Pageable pageable);
//	
	
	//Only document Type
		@Query("from SalesOrderHeader s where s.documentType=:documentType order by s.createdDate desc")
		public Page<SalesOrderHeader> findAll(@Param("documentType") String documentType,Pageable pageable);
		
   //salesHeaderId
		@Query("from SalesOrderHeader s where s.salesHeaderId=:salesHeaderId order by s.createdDate desc")
		public Page<SalesOrderHeader> findAllS(@Param("salesHeaderId") String salesHeaderId,Pageable pageable);
	
	
	
	
	//<------------------------------------------------------------------------------------------------------------------------>
	
	
	//created+stp+type+status
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.soldToParty in :stpId and s.documentProcessStatus=:documentProcessStatus and s.createdDate between :stDate and :enDate order by s.createdDate desc")
	public Page<SalesOrderHeader> findAll(@Param("documentType") String documentType,@Param("stpId") List<String> stpId,@Param("documentProcessStatus") EnOrderActionStatus documentProcessStatus,@Param("stDate") Date stDate,@Param("enDate") Date enDate,Pageable pageable); 
//    created+stp+type
	@Query("from SalesOrderHeader s where s.documentType=:documentType and  s.soldToParty in :stpId and s.createdDate between :stDate and :enDate order by s.createdDate desc")
	public Page<SalesOrderHeader> findAll(@Param("documentType") String documentType,@Param("stpId") List<String> stpId,@Param("stDate") Date stDate,@Param("enDate") Date enDate,Pageable pageable); 
//    
	//only documentType+Customer+status
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.soldToParty in :stpId  and s.documentProcessStatus=:documentProcessStatus order by s.createdDate desc ")
	public Page<SalesOrderHeader> findAll(@Param("documentType") String documentType,@Param("stpId") List<String> stpId,@Param("documentProcessStatus") EnOrderActionStatus documentProcessStatus,Pageable pageable);
	//type=sTpId
	
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.soldToParty in :stpId  order by s.createdDate desc ")
	public Page<SalesOrderHeader> findAll(@Param("documentType") String documentType,@Param("stpId") List<String> stpId,Pageable pageable);
	
//<---------------------------------------------------------------------------------------------------------------->
	

	//documentType+Status	
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.documentProcessStatus=:documentProcessStatus order by s.createdDate desc")
	public Page<SalesOrderHeader> findAll(@Param("documentType") String documentType,@Param("documentProcessStatus") EnOrderActionStatus documentProcessStatus,Pageable pageable);

	
    //credatedDate+doctype
	@Query("from SalesOrderHeader s where s.documentType=:documentType and  s.createdDate between :stDate and :enDate order by s.createdDate desc")
	public Page<SalesOrderHeader> findAll(@Param("documentType") String documentType,@Param("stDate") Date stDate,@Param("enDate") Date enDate,Pageable pageable); 
//	
//	
//	//shipToParty+doctype
	@Query("from SalesOrderHeader s where s.documentType=:documentType and  s.shipToParty=:shipToParty order by s.createdDate desc")
	public Page<SalesOrderHeader> findAll1(@Param("documentType") String documentType,@Param("shipToParty") String shipToParty,Pageable pageable); 
//	
//	
//	//status+createdDate+doctype
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.documentProcessStatus=:documentProcessStatus and s.createdDate between :stDate and :enDate order by s.createdDate desc")
	public Page<SalesOrderHeader> findAll(@Param("documentType") String documentType,@Param("documentProcessStatus") EnOrderActionStatus documentProcessStatus,@Param("stDate") Date stDate,@Param("enDate") Date enDate,Pageable pageable); 
// 

//	<------------------------------------------------------------------------------------------------------------------------->
//	//Status+shipToParty
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.documentProcessStatus=:documentProcessStatus and s.shipToParty=:shipToParty order by s.createdDate desc" )
	public Page<SalesOrderHeader> findAll1(@Param("documentType") String documentType,@Param("documentProcessStatus") EnOrderActionStatus documentProcessStatus,@Param("shipToParty") String shipToParty,Pageable pageable); 
//    
//	
//	//..created=requested dates
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.createdDate between :stDate and :enDate and s.shipToParty=:shipToParty order by s.createdDate desc")
	public Page<SalesOrderHeader> findAll(@Param("documentType") String documentType,@Param("stDate") Date stDate,@Param("enDate") Date enDate,@Param("shipToParty") String shipToParty,Pageable pageable); 
//    
//    
//	//status+createddate+deliveryDate
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.documentProcessStatus=:documentProcessStatus and s.createdDate between :stDate and :enDate and s.shipToParty=:shipToParty order by s.createdDate desc")
	public Page<SalesOrderHeader> findAll(@Param("documentType") String documentType,@Param("documentProcessStatus") EnOrderActionStatus documentProcessStatus,@Param("stDate") Date stDate,@Param("enDate") Date enDate,@Param("shipToParty") String shipToParty,Pageable pageable); 
//    
//	



	
}
