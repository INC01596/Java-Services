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

	@Query("from SalesOrderHeader s where  s.invId=:invId order by s.postingDate desc")
	public Page<SalesOrderHeader> findAllInvId(@Param("invId") String invId, Pageable pageable);

	@Query("from SalesOrderHeader s where  s.obdId=:obdId order by s.postingDate desc")
	public Page<SalesOrderHeader> findAllD(@Param("obdId") String obdId, Pageable pageable);

	// Only document Type

	@Query("from SalesOrderHeader s where s.documentType=:documentType order by s.postingDate desc")
	public Page<SalesOrderHeader> findAll(@Param("documentType") String documentType, Pageable pageable);

	// salesHeaderId
	@Query("from SalesOrderHeader s where s.salesHeaderId=:salesHeaderId and s.invoiceStatus='CREATED' order by s.postingDate desc")
	public Page<SalesOrderHeader> findAllS(@Param("salesHeaderId") String salesHeaderId, Pageable pageable);

	@Query("from SalesOrderHeader s where s.salesHeaderId=:salesHeaderId and s.documentType=:documentType order by s.postingDate desc")
	public Page<SalesOrderHeader> findAllS1(@Param("salesHeaderId") String salesHeaderId,
			@Param("documentType") String documentType, Pageable pageable);

	// <------------------------------------------------------------------------------------------------------------------------>

	// Mainly For STP
	// created+stp+type+status
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.soldToParty in :stpId and s.documentProcessStatus in :documentProcessStatus and s.createdDate between :stDate and :enDate order by s.postingDate desc")
	public Page<SalesOrderHeader> findAll(@Param("documentType") String documentType,
			@Param("stpId") List<String> stpId,
			@Param("documentProcessStatus") List<EnOrderActionStatus> documentProcessStatus,
			@Param("stDate") Date stDate, @Param("enDate") Date enDate, Pageable pageable);

	// created+stp+type

	@Query("from SalesOrderHeader s where s.documentType=:documentType and  s.soldToParty in :stpId and s.createdDate between :stDate and :enDate order by s.postingDate desc")
	public Page<SalesOrderHeader> findAll12(@Param("documentType") String documentType,
			@Param("stpId") List<String> stpId, @Param("stDate") Date stDate, @Param("enDate") Date enDate,
			Pageable pageable);

	//
	// only documentType+Customer+status
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.soldToParty in :stpId  and s.documentProcessStatus in :documentProcessStatus order by s.postingDate desc ")
	public Page<SalesOrderHeader> findAll(@Param("documentType") String documentType,
			@Param("stpId") List<String> stpId,
			@Param("documentProcessStatus") List<EnOrderActionStatus> documentProcessStatus, Pageable pageable);
	// type=sTpId

	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.soldToParty in :stpId  order by s.postingDate desc ")
	public Page<SalesOrderHeader> findAll123(@Param("documentType") String documentType,
			@Param("stpId") List<String> stpId, Pageable pageable);

	// <---------------------------------------------------------------------------------------------------------------->

	// documentType+Status
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.documentProcessStatus in :documentProcessStatus order by s.postingDate desc")
	public Page<SalesOrderHeader> findAll(@Param("documentType") String documentType,
			@Param("documentProcessStatus") List<EnOrderActionStatus> documentProcessStatus, Pageable pageable);

	// credatedDate+doctype
	@Query("from SalesOrderHeader s where s.documentType=:documentType and  s.createdDate between :stDate and :enDate order by s.postingDate desc")
	public Page<SalesOrderHeader> findAll(@Param("documentType") String documentType, @Param("stDate") Date stDate,
			@Param("enDate") Date enDate, Pageable pageable);

	//
	//
	// //shipToParty+doctype
	@Query("from SalesOrderHeader s where s.documentType=:documentType and  s.shipToParty=:shipToParty order by s.postingDate desc")
	public Page<SalesOrderHeader> findAll1(@Param("documentType") String documentType,
			@Param("shipToParty") String shipToParty, Pageable pageable);

	//
	//
	// //status+createdDate+doctype
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.documentProcessStatus in :documentProcessStatus and s.createdDate between :stDate and :enDate order by s.postingDate desc")
	public Page<SalesOrderHeader> findAll(@Param("documentType") String documentType,
			@Param("documentProcessStatus") List<EnOrderActionStatus> documentProcessStatus,
			@Param("stDate") Date stDate, @Param("enDate") Date enDate, Pageable pageable);
	//

	// <------------------------------------------------------------------------------------------------------------------------->
	// //Status+shipToParty
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.documentProcessStatus in :documentProcessStatus and s.shipToParty=:shipToParty order by s.postingDate desc")
	public Page<SalesOrderHeader> findAll1(@Param("documentType") String documentType,
			@Param("documentProcessStatus") List<EnOrderActionStatus> documentProcessStatus,
			@Param("shipToParty") String shipToParty, Pageable pageable);

	//
	//
	// //..created=requested dates
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.createdDate between :stDate and :enDate and s.shipToParty=:shipToParty order by s.postingDate desc")
	public Page<SalesOrderHeader> findAll(@Param("documentType") String documentType, @Param("stDate") Date stDate,
			@Param("enDate") Date enDate, @Param("shipToParty") String shipToParty, Pageable pageable);

	//
	//
	// //status+createddate+deliveryDate
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.documentProcessStatus in :documentProcessStatus and s.createdDate between :stDate and :enDate and s.shipToParty=:shipToParty order by s.postingDate desc")
	public Page<SalesOrderHeader> findAll(@Param("documentType") String documentType,
			@Param("documentProcessStatus") List<EnOrderActionStatus> documentProcessStatus,
			@Param("stDate") Date stDate, @Param("enDate") Date enDate, @Param("shipToParty") String shipToParty,
			Pageable pageable);

	//
	//
	@Query("from SalesOrderHeader s where  s.invoiceStatus=:invId order by s.createdDate desc")
	public Page<SalesOrderHeader> findAllIn(@Param("invId") String invId, Pageable pageable);

	// <------------------------------------------------------------------------------------------------------------------------->
	// created+stp+type+status

	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.soldToParty in :stpId and s.invoiceStatus=:invoiceStatus and s.createdDate between :stDate and :enDate order by s.postingDate desc")
	public Page<SalesOrderHeader> findAllInv1(@Param("documentType") String documentType,
			@Param("stpId") List<String> stpId, @Param("invoiceStatus") String invoiceStatus,
			@Param("stDate") Date stDate, @Param("enDate") Date enDate, Pageable pageable);

	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.soldToParty in :stpId  and s.invoiceStatus=:invoiceStatus order by s.postingDate desc ")
	public Page<SalesOrderHeader> findAllInv2(@Param("documentType") String documentType,
			@Param("stpId") List<String> stpId, @Param("invoiceStatus") String invoiceStatus, Pageable pageable);

	// documentType+Status
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.invoiceStatus=:invoiceStatus order by s.postingDate desc")
	public Page<SalesOrderHeader> findAllInv3(@Param("documentType") String documentType,
			@Param("invoiceStatus") String invoiceStatus, Pageable pageable);

	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.invoiceStatus=:invoiceStatus and s.createdDate between :stDate and :enDate order by s.postingDate desc")
	public Page<SalesOrderHeader> findAllInv4(@Param("documentType") String documentType,
			@Param("invoiceStatus") String invoiceStatus, @Param("stDate") Date stDate, @Param("enDate") Date enDate,
			Pageable pageable);

	//
	// Status+shipToParty
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.invoiceStatus=:invoiceStatus and s.shipToParty=:shipToParty order by s.postingDate desc")
	public Page<SalesOrderHeader> findAllInv5(@Param("documentType") String documentType,
			@Param("invoiceStatus") String invoiceStatus, @Param("shipToParty") String shipToParty, Pageable pageable);
	//

	// status+createddate+deliveryDate

	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.invoiceStatus=:invoiceStatus and s.createdDate between :stDate and :enDate and s.shipToParty=:shipToParty order by s.postingDate desc")
	public Page<SalesOrderHeader> findAllInv6(@Param("documentType") String documentType,
			@Param("invoiceStatus") String invoiceStatus, @Param("stDate") Date stDate, @Param("enDate") Date enDate,
			@Param("shipToParty") String shipToParty, Pageable pageable);
	//
	// <----------------------------------------------------------------------------------------------->

	// created+stp+type+status

	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.soldToParty in :stpId and s.obdStatus=:obdStatus and s.createdDate between :stDate and :enDate order by s.postingDate desc")
	public Page<SalesOrderHeader> findAllObd1(@Param("documentType") String documentType,
			@Param("stpId") List<String> stpId, @Param("obdStatus") String obdStatus, @Param("stDate") Date stDate,
			@Param("enDate") Date enDate, Pageable pageable);

	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.soldToParty in :stpId  and s.obdStatus=:obdStatus order by s.postingDate desc ")
	public Page<SalesOrderHeader> findAllObd2(@Param("documentType") String documentType,
			@Param("stpId") List<String> stpId, @Param("obdStatus") String obdStatus, Pageable pageable);

	// documentType+Status
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.obdStatus=:obdStatus order by s.postingDate desc")
	public Page<SalesOrderHeader> findAllObd3(@Param("documentType") String documentType,
			@Param("obdStatus") String obdStatus, Pageable pageable);

	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.obdStatus=:obdStatus and s.createdDate between :stDate and :enDate order by s.postingDate desc")
	public Page<SalesOrderHeader> findAllObd4(@Param("documentType") String documentType,
			@Param("obdStatus") String obdStatus, @Param("stDate") Date stDate, @Param("enDate") Date enDate,
			Pageable pageable);

	//
	// Status+shipToParty
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.obdStatus=:obdStatus and s.shipToParty=:shipToParty order by s.postingDate desc")
	public Page<SalesOrderHeader> findAllObd5(@Param("documentType") String documentType,
			@Param("obdStatus") String obdStatus, @Param("shipToParty") String shipToParty, Pageable pageable);
	//

	// status+createddate+deliveryDate

	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.obdStatus=:obdStatus and s.createdDate between :stDate and :enDate and s.shipToParty=:shipToParty order by s.postingDate desc")
	public Page<SalesOrderHeader> findAllObd6(@Param("documentType") String documentType,
			@Param("obdStatus") String obdStatus, @Param("stDate") Date stDate, @Param("enDate") Date enDate,
			@Param("shipToParty") String shipToParty, Pageable pageable);

	//
	@Query("from SalesOrderHeader s where s.documentType=:documentType and s.obdStatus='CREATED' and s.pgiStatus='CREATED' and s.invoiceStatus in :invoiceStatus order by s.postingDate desc")

	public Page<SalesOrderHeader> findAllOPI(@Param("documentType") String documentType,
			@Param("invoiceStatus") List<String> invoiceStatus, Pageable pageable);
	//
}
