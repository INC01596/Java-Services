package com.incture.cherrywork.repositories;

import java.math.BigDecimal;
import java.net.URI;
import java.sql.Timestamp;
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
import com.incture.cherrywork.odata.dto.OdataSchItemDto;
import com.incture.cherrywork.odata.dto.OdataSchItemStartDto;
import com.incture.cherrywork.sales.constants.EnLookUp;
import com.incture.cherrywork.sales.constants.EnPaymentChequeStatus;
import com.incture.cherrywork.sales.constants.EnUpdateIndicator;
import com.incture.cherrywork.util.ServicesUtil;
import com.incture.cherrywork.sales.constants.EnOverallDocumentStatus;

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
				lookUp.setLookupType(EnLookUp.PAYMENT);
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
				lookUp.setLookupType(EnLookUp.INCOTERMS);
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
				lookUp.setLookupType(EnLookUp.QUALITYTEST);
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
				lookUp.setLookupType(EnLookUp.DELIVERYTOLERANCE);
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
				lookUp.setLookupType(EnLookUp.DISTRIBUTIONCHANNEL);
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
	
	public List<SalesOrderItemDto> convertData(OdataSchItemStartDto odataSchItemStartDto) {
		//logger.debug("[SalesItemDetailsDao][convertData] Start : ");
		System.err.println("[SalesItemDetailsDao][convertData] Start : ");
		List<SalesOrderItemDto> listSalesItemDetailsDto = new ArrayList<>();
		try {
			for (OdataSchItemDto odataSchItemDto : odataSchItemStartDto.getD().getResults()) {
				SalesOrderItemDto salesItemDetailsDto = new SalesOrderItemDto();
				salesItemDetailsDto.setClientSpecific(Integer.parseInt(odataSchItemDto.getMandt()));
				salesItemDetailsDto.setLineItemNumber1(Integer.parseInt(odataSchItemDto.getPosnr()));
				if (odataSchItemDto.getVbeln().length() < 10)
					salesItemDetailsDto
							.setS4DocumentId(String.format("%010d", Integer.parseInt(odataSchItemDto.getVbeln())));
				else
					salesItemDetailsDto.setS4DocumentId(odataSchItemDto.getVbeln());
				salesItemDetailsDto.setMaterial(odataSchItemDto.getMatnr());
				salesItemDetailsDto.setMaterialDescription(odataSchItemDto.getArktx());
				salesItemDetailsDto.setOrderQuantity(new BigDecimal(odataSchItemDto.getKwmeng()));
				salesItemDetailsDto.setItemCategory(odataSchItemDto.getPstyv());
				salesItemDetailsDto.setPlant(odataSchItemDto.getWerks());
				salesItemDetailsDto.setNetValue(odataSchItemDto.getNetpr());
				salesItemDetailsDto.setAmountBeforeVAT(new BigDecimal(odataSchItemDto.getNetwr()));
				salesItemDetailsDto.setDocumentCurrency(odataSchItemDto.getWaerk());
				salesItemDetailsDto.setVATPercent(Integer.parseInt(odataSchItemDto.getVatPerc()));
				salesItemDetailsDto.setVATAmount(new BigDecimal(odataSchItemDto.getVatAmt()));
				salesItemDetailsDto.setTotalAmountIncludingVAT(new BigDecimal(odataSchItemDto.getTotAmt()));
				salesItemDetailsDto.setDeliveredQuantity(new BigDecimal(odataSchItemDto.getDelvQty()));
				salesItemDetailsDto.setDeliveredPieces(Integer.parseInt(odataSchItemDto.getDelvPcs()));
				salesItemDetailsDto.setOutstandingQuantity1(new BigDecimal(odataSchItemDto.getOsqty()));
				salesItemDetailsDto.setOutstandingPieces(Integer.parseInt(odataSchItemDto.getOspcs()));
				salesItemDetailsDto.setAvailabilityStatus(odataSchItemDto.getAvStat());
				if (odataSchItemDto.getPstyv().equals("AFN") || odataSchItemDto.getPstyv().equals("ZAFN")
						|| odataSchItemDto.getPstyv().equals("AGC"))
					salesItemDetailsDto.setPaymentChequeDetail(null);
				else {
					if (odataSchItemDto.getCmgst().equals("A"))
						salesItemDetailsDto.setPaymentChequeDetail(EnPaymentChequeStatus.APPROVED);
					else if (odataSchItemDto.getCmgst().equals("B"))
						salesItemDetailsDto.setPaymentChequeDetail(EnPaymentChequeStatus.BLOCKED);
					else if (odataSchItemDto.getCmgst().equals("C"))
						salesItemDetailsDto.setPaymentChequeDetail(EnPaymentChequeStatus.PARTIALLY_BLOCKED);
				}
				if (!ServicesUtil.isEmpty(odataSchItemDto.getErdat())) {
					String s = odataSchItemDto.getErdat().substring(6, 19);
					long l = Long.parseLong(s);
					Timestamp d = new Timestamp(l);
					salesItemDetailsDto.setChangedOn(d);
				} else
					salesItemDetailsDto.setChangedOn(null);
				salesItemDetailsDto.setDeliveryStatus1(new BigDecimal(odataSchItemDto.getDelvStat()));
				salesItemDetailsDto.setCreditBlockQuantity(new BigDecimal(odataSchItemDto.getCreditBlk()));
				salesItemDetailsDto.setOnTimeDeliveredQuantity(new BigDecimal(odataSchItemDto.getOnTimeDlv()));
				salesItemDetailsDto.setLineItemStatus(odataSchItemDto.getStatus());
				if (odataSchItemDto.getGbsta().equals("A"))
					salesItemDetailsDto.setOverallProcessingStatus(EnOverallDocumentStatus.NOT_YET_PROCESSED);
				else if (odataSchItemDto.getGbsta().equals("B"))
					salesItemDetailsDto.setOverallProcessingStatus(EnOverallDocumentStatus.PARTIALLY_PROCESSED);
				else if (odataSchItemDto.getGbsta().equals("C"))
					salesItemDetailsDto.setOverallProcessingStatus(EnOverallDocumentStatus.COMPLETELY_PROCESSED);
				salesItemDetailsDto.setNumberOfPieces(Integer.parseInt(odataSchItemDto.getOrdPcs()));
				salesItemDetailsDto.setNumberOfBundles(Integer.parseInt(odataSchItemDto.getOrdBdl()));
				salesItemDetailsDto.setBasePrice1(odataSchItemDto.getBasePr());
				salesItemDetailsDto.setExtras(new BigDecimal(odataSchItemDto.getExtras()));
				salesItemDetailsDto.setQualityTestExtras(new BigDecimal(odataSchItemDto.getTestExtras()));
				salesItemDetailsDto.setDiscount1(new BigDecimal(odataSchItemDto.getDiscount()));
				salesItemDetailsDto.setEnteredOrdQuantity(new BigDecimal(odataSchItemDto.getEnteredQty()));
				salesItemDetailsDto.setStandard(odataSchItemDto.getZzstd());
				salesItemDetailsDto.setStandardDescription(odataSchItemDto.getZzstdDes());
				salesItemDetailsDto.setSectionGrade(odataSchItemDto.getZzgrade());
				salesItemDetailsDto.setSectionGradeDescription(odataSchItemDto.getZzgradeDes());
				salesItemDetailsDto.setSize(odataSchItemDto.getZzsize());
				salesItemDetailsDto.setKgPerMeter(new BigDecimal(odataSchItemDto.getZzkgperm()));
				salesItemDetailsDto.setLength(new BigDecimal(odataSchItemDto.getZzlength()));
				salesItemDetailsDto.setBarsPerBundle(new BigDecimal(odataSchItemDto.getZzpcsPerBndl()));
				salesItemDetailsDto.setSectionGroup(odataSchItemDto.getZzsectionGrp());
				salesItemDetailsDto.setLevel2Id(odataSchItemDto.getZzl2id());
				salesItemDetailsDto.setCeLogo(odataSchItemDto.getZzceMark());
				salesItemDetailsDto.setSection(odataSchItemDto.getZzsection());
				salesItemDetailsDto.setSizeGroup(new BigDecimal(odataSchItemDto.getZzsizeGrp()));
				salesItemDetailsDto.setIsiLogo(odataSchItemDto.getZzisiMark());
				if (odataSchItemDto.getZzqltyIt().equalsIgnoreCase("FALSE"))
					salesItemDetailsDto.setImpactTest(false);
				else
					salesItemDetailsDto.setImpactTest(true);
				if (odataSchItemDto.getZzqltyBt().equalsIgnoreCase("FALSE"))
					salesItemDetailsDto.setBendTest(false);
				else
					salesItemDetailsDto.setBendTest(true);
				if (odataSchItemDto.getBoron_req().equalsIgnoreCase("FALSE"))
					salesItemDetailsDto.setIsElementBoronRequired(false);
				else
					salesItemDetailsDto.setIsElementBoronRequired(true);
				if (odataSchItemDto.getZzqlty32Ins().equalsIgnoreCase("FALSE"))
					salesItemDetailsDto.setInspection(false);
				else
					salesItemDetailsDto.setInspection(true);
				if (odataSchItemDto.getZzqltyUt().equalsIgnoreCase("FALSE"))
					salesItemDetailsDto.setUltraSonicTest(false);
				else
					salesItemDetailsDto.setUltraSonicTest(true);
				salesItemDetailsDto.setGradePricingGroup(odataSchItemDto.getZzgradeGrp());
				salesItemDetailsDto.setTotalNumberOfPieces(new BigDecimal(odataSchItemDto.getZzpcsOrd()));
				salesItemDetailsDto.setBundleWeight(new BigDecimal(odataSchItemDto.getZzbdlWt()));
				if (odataSchItemDto.getUpdateInd().equalsIgnoreCase("I"))
					salesItemDetailsDto.setUpdateIndicator1(EnUpdateIndicator.INSERT);
				else if (odataSchItemDto.getUpdateInd().equalsIgnoreCase("D"))
					salesItemDetailsDto.setUpdateIndicator1(EnUpdateIndicator.DELETE);
				else if (odataSchItemDto.getUpdateInd().equalsIgnoreCase("U"))
					salesItemDetailsDto.setUpdateIndicator1(EnUpdateIndicator.UPDATE);
				// if (!ServicesUtil.isEmpty(odataSchItemDto.getChangedOn())) {
				// // String s = odataSchItemDto.getChangedOn().substring(6,
				// // 19);
				// long l = Long.parseLong(odataSchItemDto.getChangedOn());
				// Timestamp d = new Timestamp(l);
				// salesItemDetailsDto.setChangedOn(d);
				// } else
				// salesItemDetailsDto.setChangedOn(null);
				if (odataSchItemDto.getSyncStat().equalsIgnoreCase("TRUE"))
					salesItemDetailsDto.setSyncStatus1(true);
				else
					salesItemDetailsDto.setSyncStatus1(false);
				listSalesItemDetailsDto.add(salesItemDetailsDto);
			}
			//logger.debug("[SalesItemDetailsDao][convertData] End : ");
			System.err.println("[SalesItemDetailsDao][convertData] End : ");
		} catch (Exception e) {
			//logger.error("[SalesItemDetailsDao][convertData] Exception : " + e.getMessage());
			System.err.println("[SalesItemDetailsDao][convertData] Exception : " + e.getMessage());
			e.printStackTrace();
		}

		return listSalesItemDetailsDto;
	}



}
