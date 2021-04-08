package com.incture.cherrywork.repositories;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dtos.SalesOrderDropDownSearchHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.dtos.SalesOrderKgPerMeterListDto;
import com.incture.cherrywork.dtos.SalesOrderLengthListDto;
import com.incture.cherrywork.dtos.SalesOrderMaterialListDto;
import com.incture.cherrywork.dtos.SalesOrderMaterialMasterDto;
import com.incture.cherrywork.dtos.SalesOrderOdataHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderOdataLineItemDto;
import com.incture.cherrywork.dtos.SalesOrderSearchHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderSectionGradeListDto;
import com.incture.cherrywork.dtos.SalesOrderSizeListDto;
import com.incture.cherrywork.dtos.SalesOrderStandardListDto;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.entities.SalesOrderItem;
import com.incture.cherrywork.services.ISalesOrderHeaderService;
import com.incture.cherrywork.services.SalesOrderHeaderService;
import com.incture.cherrywork.services.SalesOrderItemService;
import com.incture.cherrywork.services.SalesOrderOdataServices;

@SuppressWarnings("unused")
@Transactional
@Repository
public class ISalesOrderHeaderCustomRepositoryImpl implements ISalesOrderHeaderCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
//	@Autowired
//	private SalesOrderHeaderService salesOrderHeaderService;
//	
//	@Autowired
//	private SalesOrderItemService salesOrderItemService;

//	@Autowired
//	private ISalesOrderHeaderRepository salesOrderHeaderRepository;

	//@Autowired
	//private ISalesOrderItemRepository salesOrderItemRepository;

	@Autowired
	private SalesOrderOdataServices odataServices;


	@SuppressWarnings("unchecked")
	public ResponseEntity<Object> getSearchDropDown(SalesOrderSearchHeaderDto dto) {
		String plant = ServicesUtils.listToString(dto.getPlant());
		SalesOrderDropDownSearchHeaderDto resultList = new SalesOrderDropDownSearchHeaderDto();
		List<SalesOrderMaterialListDto> materialList = new ArrayList<>();
		List<SalesOrderStandardListDto> standardList = new ArrayList<>();
		List<SalesOrderSizeListDto> sizeList = new ArrayList<>();
		List<SalesOrderSectionGradeListDto> sectionGradeList = new ArrayList<>();
		List<SalesOrderKgPerMeterListDto> kgPerMeterList = new ArrayList<>();
		List<SalesOrderLengthListDto> lengthList = new ArrayList<>();
		StringBuffer matQuery = new StringBuffer("select distinct(m.material)");
		StringBuffer stdQuery = new StringBuffer("select distinct m.standard, m.standardDescription");
		StringBuffer gradeQuery = new StringBuffer("select distinct m.sectionGrade, m.sectionGradeDescription");
		StringBuffer sizeQuery = new StringBuffer("select distinct(m.size)");
		StringBuffer kgPerMeterQuery = new StringBuffer("select distinct(m.kgPerMeter)");
		StringBuffer lengthQuery = new StringBuffer("select distinct(m.length)");
		StringBuffer query = new StringBuffer();
		try {
			System.err.println(dto);
			if (!ServicesUtils.isEmpty(dto.getMaterial()) || !ServicesUtils.isEmpty(dto.getStandard())
					|| !ServicesUtils.isEmpty(dto.getSectionGrade()) || !ServicesUtils.isEmpty(dto.getSize())
					|| !ServicesUtils.isEmpty(dto.getKgPerMeter()) || !ServicesUtils.isEmpty(dto.getLength())) {
				System.err.println("if condition");
				query = new StringBuffer(" from SalesOrderMaterialMaster m where"); // correct
																					// it
				if (!ServicesUtils.isEmpty(dto.getMaterial())) {
					System.err.println("mat condition");
					query.append(" m.material = \'" + dto.getMaterial() + "\' and");
				}
				if (!ServicesUtils.isEmpty(dto.getStandard()))
					query.append(" m.standard = \'" + dto.getStandard() + "\' and");
				if (!ServicesUtils.isEmpty(dto.getSectionGrade()))
					query.append(" m.sectionGrade = \'" + dto.getSectionGrade() + "\' and");
				if (!ServicesUtils.isEmpty(dto.getSize()))
					query.append(" m.size = \'" + dto.getSize() + "\' and");
				if (!ServicesUtils.isEmpty(dto.getKgPerMeter()))
					query.append(" m.kgPerMeter = \'" + dto.getKgPerMeter() + "\' and");
				if (!ServicesUtils.isEmpty(dto.getLength()))
					query.append(" m.length = \'" + dto.getLength() + "\' and");
				if (!ServicesUtils.isEmpty(dto.getContainer()))
					query.append(" m.container = " + dto.getContainer() + " and");
				if (!ServicesUtils.isEmpty(dto.getPlant()))
					query.append(" m.plant in (" + plant + ") and");
				query = new StringBuffer(query.substring(0, query.length() - 3));
				query.append(" order by ");
			} else {
				System.err.println("else condition");
				query = new StringBuffer(" from SalesOrderMaterialMaster m where m.container = " + dto.getContainer()
						+ " and m.plant in (" + plant + ") order by ");
			}

			Query q1 = entityManager.createQuery(matQuery.append(query).append("m.material asc").toString());
			List<String> material = q1.getResultList();

			System.err.println(q1.getResultList());
			for (String mat : material) {
				SalesOrderMaterialListDto materialDto = new SalesOrderMaterialListDto();
				materialDto.setMaterialNumber(mat);
				materialList.add(materialDto);
			}

			Query q2 = entityManager.createQuery(stdQuery.append(query).append("m.standardDescription asc").toString());
			List<Object[]> objList = q2.getResultList();
			for (Object[] obj : objList) {
				SalesOrderStandardListDto stdDto = new SalesOrderStandardListDto();
				if (!ServicesUtils.isEmpty(obj[0]))
					stdDto.setStandard((String) obj[0]);
				if (!ServicesUtils.isEmpty(obj[0]))
					stdDto.setStandardDescription((String) obj[1]);
				standardList.add(stdDto);
			}

			Query q3 = entityManager.createQuery(sizeQuery.append(query).append("m.size asc").toString());
			List<String> size = q3.getResultList();
			for (String si : size) {
				SalesOrderSizeListDto sizeDto = new SalesOrderSizeListDto();
				sizeDto.setSize(si);
				sizeList.add(sizeDto);
			}

			Query q4 = entityManager
					.createQuery(gradeQuery.append(query).append("m.sectionGradeDescription asc").toString());
			List<Object[]> objList1 = q4.getResultList();
			for (Object[] obj1 : objList1) {
				SalesOrderSectionGradeListDto sectionDto = new SalesOrderSectionGradeListDto();
				if (!ServicesUtils.isEmpty(obj1[0]))
					sectionDto.setSectionGrade((String) obj1[0]);
				if (!ServicesUtils.isEmpty(obj1[1]))
					sectionDto.setSectionGradeDescription((String) obj1[1]);
				sectionGradeList.add(sectionDto);
			}

			Query q5 = entityManager.createQuery(kgPerMeterQuery.append(query).append("m.kgPerMeter asc").toString());
			List<BigDecimal> kgPerMeter = q5.getResultList();
			for (BigDecimal kg : kgPerMeter) {
				SalesOrderKgPerMeterListDto kgPerMeterDto = new SalesOrderKgPerMeterListDto();
				kgPerMeterDto.setKgPerMeter(kg);
				kgPerMeterList.add(kgPerMeterDto);
			}

			Query q6 = entityManager.createQuery(lengthQuery.append(query).append("m.length asc").toString());
			List<BigDecimal> length = q6.getResultList();
			for (BigDecimal l : length) {
				SalesOrderLengthListDto lengthDto = new SalesOrderLengthListDto();
				lengthDto.setLength(l);
				lengthList.add(lengthDto);
			}

			resultList.setMaterialNumber(materialList);
			resultList.setStandard(standardList);
			resultList.setSize(sizeList);
			resultList.setSectionGrade(sectionGradeList);
			resultList.setKgPerMeter(kgPerMeterList);
			resultList.setLength(lengthList);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("Message", "Error Fetching Material List").body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Message", "Material Fetched Successfully").body(resultList);

	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<Object> getMannualSearch(SalesOrderSearchHeaderDto dto) {
		// logger.debug("[MaterialMasterDao][manualSearchResult] Started");
		// Response response = new Response();
		List<SalesOrderMaterialMasterDto> listDto = new ArrayList<>();

		String plant = ServicesUtils.listToString(dto.getPlant());
		try {
			StringBuffer query = new StringBuffer(
					"select TOP 50 MATERIAL_MASTER_ID, BARS_PER_BUNDLE, BEND_TEST, BUNDLE_WEIGHT, CATALOG_KEY, CE_LOGO, CHANGED_BY, CHANGED_ON,"
							+ " CLIENT_SPECIFIC, CREATED_BY, CREATED_ON, GRADE_PRICING_GROUP, IMPACT_TEST, ISI_LOGO, KEY, KG_PER_METER, KGPERM_PRICING_GROUP,"
							+ " LENGTH, LENGTH_MAP_KEY, LENGTH_PRICING_GROUP, LEVEL2_ID, MATERIAL, MATERIAL_DESC, PLANT, SECTION, SECTION_GRADE, SECTION_GRADE_DESC,"
							+ " SECTION_GROUP, SECTION_PRICING_GROUP, SIZE, SIZE_GROUP, SIZE_PRICING_GROUP, STANDARD, STANDARD_DESC, SYNC_STAT, ULTRALIGHT_TEST, UPDATE_INDICATOR, ROLLING_PLAN_FLAG"
							+ " from MATERIAL m");

			query.append(" where m.CONTAINER=" + dto.getContainer() + " and");
			query.append(" m.PLANT in (" + plant + ") and");
			if (!ServicesUtils.isEmpty(dto.getMaterial()) || !ServicesUtils.isEmpty(dto.getStandard())
					|| !ServicesUtils.isEmpty(dto.getSectionGrade()) || !ServicesUtils.isEmpty(dto.getSize())
					|| !ServicesUtils.isEmpty(dto.getKgPerMeter()) || !ServicesUtils.isEmpty(dto.getLength())) {
				if (!ServicesUtils.isEmpty(dto.getMaterial()))
					query.append(" m.MATERIAL = \'" + dto.getMaterial() + "\' and");
				if (!ServicesUtils.isEmpty(dto.getStandard()))
					query.append(" m.STANDARD = \'" + dto.getStandard() + "\' and");
				if (!ServicesUtils.isEmpty(dto.getSectionGrade()))
					query.append(" m.SECTION_GRADE = \'" + dto.getSectionGrade() + "\' and");
				if (!ServicesUtils.isEmpty(dto.getSize()))
					query.append(" m.SIZE = \'" + dto.getSize() + "\' and");
				if (!ServicesUtils.isEmpty(dto.getKgPerMeter()))
					query.append(" m.KG_PER_METER = \'" + dto.getKgPerMeter() + "\' and");
				if (!ServicesUtils.isEmpty(dto.getLength())) {
					if (dto.getLength() != null && dto.getLength().equals("other") && !ServicesUtils.isEmpty(dto.getOtherLength())) {
						query.append(" m.LENGTH = (select top 1 l.LENGTH from MATERIAL l where l.LENGTH <= "
								+ dto.getOtherLength() + " ");
						if (!ServicesUtils.isEmpty(dto.getMaterial()))
							query.append(" and l.MATERIAL = \'" + dto.getMaterial() + "\'");
						if (!ServicesUtils.isEmpty(dto.getStandard()))
							query.append(" and l.STANDARD = \'" + dto.getStandard() + "\'");
						if (!ServicesUtils.isEmpty(dto.getSectionGrade()))
							query.append(" and l.SECTION_GRADE = \'" + dto.getSectionGrade() + "\'");
						if (!ServicesUtils.isEmpty(dto.getSize()))
							query.append(" and l.SIZE = \'" + dto.getSize() + "\'");
						if (!ServicesUtils.isEmpty(dto.getKgPerMeter()))
							query.append(" and l.KG_PER_METER = \'" + dto.getKgPerMeter() + "\'");
						query.append(" order by l.LENGTH desc) and");
					} else
						query.append(" m.LENGTH = " + dto.getLength() + " and");
				}
			}
			query = new StringBuffer(query.substring(0, query.length() - 3));
			query.append(" order by m.MATERIAL_DESC desc");
			Query q = entityManager.createNativeQuery(query.toString());
			listDto = q.getResultList();

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("Message", "Error Fetching Material List").body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Message", "Material Fetched Successfully").body(listDto);

	}
	
	

	
	@SuppressWarnings("unchecked")
	@Override
	public SalesOrderOdataHeaderDto getOdataReqPayload(SalesOrderHeaderDto dto) {
		// logger.debug("[SalesHeaderDao][getOdataReqPayload] Started : " +
		// salesHeaderId);
		// SalesItemDetailsDao salesItemDetailsDao = new SalesItemDetailsDao();
		System.err.println("salesHeaderId in reqpayload: "+dto.getSalesHeaderId());
		String salesHeaderId = dto.getSalesHeaderId();
		SalesOrderOdataHeaderDto odataHeaderDto = new SalesOrderOdataHeaderDto();
		List<SalesOrderHeader> headerEntityList = new ArrayList<>();
		List<SalesOrderItem> lineItemListDo = new ArrayList<>();
		try {
			String headerQuery = "select s from SalesOrderHeader s where s.salesHeaderId=:salesHeaderId";
			Query hq = entityManager.createQuery(headerQuery);
			hq.setParameter("salesHeaderId", salesHeaderId);
			headerEntityList = hq.getResultList();
			System.err.println("headerEntityList in reqpayload: "+headerEntityList.toString());
			for (SalesOrderHeader entity : headerEntityList){
				SalesOrderHeaderItemDto salesHeaderItemDto = new SalesOrderHeaderItemDto();
				SalesOrderHeaderDto headerDto = new SalesOrderHeaderDto();
				List<SalesOrderItemDto> lineItemListDto = new ArrayList<>();
				headerDto = ObjectMapperUtils.map(entity, SalesOrderHeaderDto.class);
				headerDto.setPaymentTerms(dto.getPaymentTerms());
				headerDto.setTotalSalesOrderQuantity(dto.getTotalSalesOrderQuantity());
				headerDto.setPaymentTerms(dto.getPaymentTerms());
				headerDto.setIncoTerms1(dto.getIncoTerms1());
				headerDto.setIncoTerms2(dto.getIncoTerms2());
				headerDto.setWeight(dto.getWeight());
				headerDto.setNetValueSA(dto.getNetValueSA());
				headerDto.setTotalSalesOrderQuantitySA(dto.getTotalSalesOrderQuantitySA());
				headerDto.setOverDeliveryTolerance(dto.getOverDeliveryTolerance());
				headerDto.setPlant(dto.getPlant());
				headerDto.setCustomerPODate(dto.getCustomerPODate());
				System.out.println("in [reqpayload] ovdeltol: "+dto.getOverDeliveryTolerance());
				headerDto.setUnderDeliveryTolerance(dto.getUnderDeliveryTolerance());
				System.err.println("headerEntityList in reqpayload: "+headerDto.toString());
				salesHeaderItemDto.setHeaderDto(headerDto);
				String itemQuery = "select s from SalesOrderItem s where s.salesHeaderId=:salesHeaderId";
				Query iq = entityManager.createQuery(itemQuery);
				iq.setParameter("salesHeaderId", salesHeaderId);
				lineItemListDo = iq.getResultList();
				System.err.println("lineItemListDo in reqpayload: "+lineItemListDo.toString());
				for (SalesOrderItem item : lineItemListDo) {
					SalesOrderItemDto itemDto = new SalesOrderItemDto();
					itemDto = ObjectMapperUtils.map(item, SalesOrderItemDto.class);
					lineItemListDto.add(itemDto);
				}
				System.err.println("lineItemListDto in reqpayload"+lineItemListDo);
				salesHeaderItemDto.setLineItemList(lineItemListDto);
				odataHeaderDto = convertToOdataReq(salesHeaderItemDto);
				System.err.println("salesHeaderItemDto "+salesHeaderItemDto.toString());
				System.err.println("odataHeaderDto: "+odataHeaderDto);
				// logger.debug("[SalesHeaderDao][getOdataReqPayload] end : " +
				// odataHeaderDto);
			}
		} catch (Exception e) {
			// logger.error("[SalesHeaderDao][getOdataReqPayload] Exception : "
			// + e.getMessage());
			e.printStackTrace();
		}
		return odataHeaderDto;
	}

		
	
	public String updateError(String temp_id, String value) {
		// logger.debug("[SalesHeaderDao][updateError] Start : " + temp_id + " :
		// " + value);
		String msg = "";
		try {
			Date d = new Date();
			long t = d.getTime();
			value = value.replaceAll("'", "");
			String hString = "update SALES_ORDER_HEADER set POSTING_ERROR='" + value
					+ "', POSTING_STATUS=false, POSTING_DATE = '" + new Timestamp(t) + "' where SALES_HEADER_ID='" + temp_id
					+ "'";
			Query hq = entityManager.createNativeQuery(hString);
			hq.executeUpdate();
			msg = "Success";
		} catch (Exception e) {
			msg = "Faliure";
			// logger.error("[SalesHeaderDao][updateError] Exception : " +
			// e.getMessage());
			e.printStackTrace();
		}
		return msg;
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
			if(values.size()>0)
				value = values.get(0);
		} catch (Exception e) {
			//logger.error("[LookUpDao][getLookupValue] Exception :" + e.getMessage());
			e.printStackTrace();
		}
		return value;
	}
	
	public SalesOrderOdataHeaderDto convertToOdataReq(SalesOrderHeaderItemDto dto) {
		//logger.debug("[SalesHeaderDao][convertToOdataReq] Stated : " + dto);
		System.out.println("[SalesHeaderDao][convertToOdataReq] Stated : " + dto);
		SalesOrderOdataHeaderDto headerDto = new SalesOrderOdataHeaderDto();
		List<SalesOrderOdataLineItemDto> odataItemList = new ArrayList<>();
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getSalesHeaderId()))
			headerDto.setTemp_id(dto.getHeaderDto().getSalesHeaderId());
		else
			headerDto.setTemp_id("");

		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getS4DocumentId())) {
			if ((dto.getHeaderDto().getPlant() != null) && dto.getHeaderDto().getPlant().equals("CODD")) {
				headerDto.setDocID_6("");
				headerDto.setDocID_2(dto.getHeaderDto().getS4DocumentId());
			} else if (dto.getHeaderDto().getPlant() != null && dto.getHeaderDto().getPlant().equals("4321")) {
				headerDto.setDocID_6(dto.getHeaderDto().getS4DocumentId());
				headerDto.setDocID_2("");
			}
		} else {
			headerDto.setDocID_6("");
			headerDto.setDocID_2("");
		}

		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getReferenceDocument()))
			headerDto.setRef_Doc(dto.getHeaderDto().getReferenceDocument());
		else
			headerDto.setRef_Doc("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getCreatedBy()))
			headerDto.setCreated_by(dto.getHeaderDto().getCreatedBy());
		else
			headerDto.setCreated_by("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getDocumentType()))
			headerDto.setDocType(dto.getHeaderDto().getDocumentType());
		else
			headerDto.setDocType("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getDocumentCurrencySA()))
			headerDto.setDoc_Curr_SA(dto.getHeaderDto().getDocumentCurrencySA());
		else
			headerDto.setDoc_Curr_SA("");
		headerDto.setOrdType("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getSoldToParty()))
			headerDto.setSoldToParty(dto.getHeaderDto().getSoldToParty());
		else
			headerDto.setSoldToParty("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getShipToParty()))
			headerDto.setShipToParty(dto.getHeaderDto().getShipToParty());
		else
			headerDto.setShipToParty("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getName()))
			headerDto.setName(dto.getHeaderDto().getName());
		else
			headerDto.setName("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getEmailId()))
			headerDto.setEmailID(dto.getHeaderDto().getEmailId());
		else
			headerDto.setEmailID("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getCity()))
			headerDto.setCity(dto.getHeaderDto().getCity());
		else
			headerDto.setCity("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getDestCountry()))
			headerDto.setDestCountry(dto.getHeaderDto().getDestCountry());
		else
			headerDto.setDestCountry("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getContactNo()))
			headerDto.setContactNo(dto.getHeaderDto().getContactNo());
		else
			headerDto.setContactNo("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getCustomerPONum()))
			headerDto.setReference(dto.getHeaderDto().getCustomerPONum());
		else
			headerDto.setReference("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getDistributionChannel()))
			headerDto.setDistChannel(dto.getHeaderDto().getDistributionChannel());
		else
			headerDto.setDistChannel("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getPaymentTerms()))
		{
			headerDto.setPayment(dto.getHeaderDto().getPaymentTerms());
			System.out.println("in [converttoodata] paymentterms "+dto.getHeaderDto().getPaymentTerms());
		}
		else
			headerDto.setPayment("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getRequestDeliveryDate())) {
			String date = ServicesUtils.DateToString(dto.getHeaderDto().getRequestDeliveryDate());
			headerDto.setREQ_DATE(date.substring(0, 10) + 'T' + date.substring(11));
		} else
			headerDto.setREQ_DATE("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getCreatedDate())) {
			String date = ServicesUtils.DateToString(dto.getHeaderDto().getCreatedDate());
			headerDto.setVALID_F(date.substring(0, 10) + 'T' + date.substring(11));
		} else
			headerDto.setVALID_F("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getCreatedDate())) {
			String date = ServicesUtils.DateToString(dto.getHeaderDto().getCreatedDate());
			headerDto.setVALID_T(date.substring(0, 10) + 'T' + date.substring(11));
		} else
			headerDto.setVALID_T("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getIncoTerms1()))
			headerDto.setInco1(dto.getHeaderDto().getIncoTerms1());
		else
			headerDto.setInco1("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getIncoTerms2()))
			headerDto.setInco2(dto.getHeaderDto().getIncoTerms2());
		else
			headerDto.setInco2("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getWeight()))
			headerDto.setWeight(dto.getHeaderDto().getWeight());
		else
			headerDto.setWeight("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getCountry()))
			headerDto.setCountry(dto.getHeaderDto().getCountry());
		else
			headerDto.setCountry("");
		// if (!ServicesUtils.isEmpty(dto.getHeaderDto().getSalesGroup()))
		// headerDto.setSalesG(dto.getHeaderDto().getSalesGroup());
		// else
		headerDto.setSalesG("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getOrderReason()))
			headerDto.setReason(dto.getHeaderDto().getOrderReason());
		else
			headerDto.setReason("");

		headerDto.setCountryText("");
		headerDto.setSalesText("");
		headerDto.setReasonText("");
		headerDto.setPaymentText("");
		headerDto.setWFStatus("");
		headerDto.setWFMessage(false);
		headerDto.setDistChannelText("");
		headerDto.setInco1Text("");

		if (dto.getHeaderDto().getPlant() != null && dto.getHeaderDto().getPlant().equals("4321")) {
			if (!ServicesUtils.isEmpty(dto.getHeaderDto().getTotalSalesOrderQuantity())
					&& !ServicesUtils.isEmpty(dto.getHeaderDto().getNetValue())) {
				MathContext mc = new MathContext(3);
				BigDecimal netValue = new BigDecimal(dto.getHeaderDto().getNetValue());
				BigDecimal val = netValue.divide(dto.getHeaderDto().getTotalSalesOrderQuantity(), 3,
						RoundingMode.HALF_UP);
				System.out.println("in[converttoodata] netval: "+netValue+" val "+val+" val in string "+val.toString());
				headerDto.setWeightAVG(val.toString());
			} else {
				BigDecimal val = new BigDecimal(0);
				headerDto.setWeightAVG(val.toString());
			}
		} else if (dto.getHeaderDto().getPlant() != null && dto.getHeaderDto().getPlant().equals("CODD")) {
			if (!ServicesUtils.isEmpty(dto.getHeaderDto().getTotalSalesOrderQuantitySA())
					&& !ServicesUtils.isEmpty(dto.getHeaderDto().getNetValueSA())) {
				MathContext mc = new MathContext(3);
				BigDecimal netValue = new BigDecimal(dto.getHeaderDto().getNetValueSA());
				BigDecimal val = netValue.divide(dto.getHeaderDto().getTotalSalesOrderQuantitySA(), 0,
						RoundingMode.HALF_UP);
				headerDto.setWeightAVG(val.toString());
			} else {
				BigDecimal val = new BigDecimal(0);
				headerDto.setWeightAVG(val.toString());
			}
		}
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getCustomerPODate())) {
			String date = ServicesUtils.DateToString(dto.getHeaderDto().getCustomerPODate());
			headerDto.setBSTKD_E(date.substring(0, 10) + 'T' + date.substring(11));
		} else
			headerDto.setBSTKD_E("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getOverDeliveryTolerance())) {
			String ot = getLookupValue(dto.getHeaderDto().getOverDeliveryTolerance());
			System.err.println("in [converttoodata]"+ot);
			headerDto.setOvdelTol(ot.substring(0, ot.length()));
		} else
			headerDto.setOvdelTol("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getUnderDeliveryTolerance())) {
			String ut = getLookupValue(dto.getHeaderDto().getUnderDeliveryTolerance());
			headerDto.setUndelTol(ut.substring(0, ut.length()));
		} else
			headerDto.setUndelTol("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getColorCodingDetails()))
			headerDto.setColorcoding(dto.getHeaderDto().getColorCodingDetails());
		else
			headerDto.setColorcoding("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getComments()))
			headerDto.setOtherRemark(dto.getHeaderDto().getComments());
		else
			headerDto.setOtherRemark("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getProjectName()))
			headerDto.setProject(dto.getHeaderDto().getProjectName());
		else
			headerDto.setProject("");
		if (!ServicesUtils.isEmpty(dto.getHeaderDto().getShippingType()))
			headerDto.setShType(dto.getHeaderDto().getShippingType());
		else
			headerDto.setShType("");
		// int count = 0;
		for (SalesOrderItemDto itemDto : dto.getLineItemList()) {
			SalesOrderOdataLineItemDto odataLine = new SalesOrderOdataLineItemDto();
			odataLine.setTemp_id("");
			// count += 1;
			
			// odataLine.setItem(Integer.toString(count));
			odataLine.setItem(itemDto.getLineItemNumber().toString());
			if (!ServicesUtils.isEmpty(itemDto.getItemCategory()))
				odataLine.setItemcat(itemDto.getItemCategory());
			else
				odataLine.setItemcat("");
			if (!ServicesUtils.isEmpty(itemDto.getMaterial()))
				odataLine.setMaterial(itemDto.getMaterial());
			else
				odataLine.setMaterial("");
			if (!ServicesUtils.isEmpty(itemDto.getEnteredOrdQuantity()))
				odataLine.setQuantity(itemDto.getEnteredOrdQuantity().toString());
			else
				odataLine.setQuantity("");
			if (!ServicesUtils.isEmpty(itemDto.getBasePrice()))
				odataLine.setPrice(itemDto.getBasePrice().toString());
			else
				odataLine.setPrice("");
			if (!ServicesUtils.isEmpty(itemDto.getPlant()))
				odataLine.setPlant(itemDto.getPlant());
			else
				odataLine.setPlant("");
			if (!ServicesUtils.isEmpty(itemDto.getDocumentCurrency()))
				odataLine.setCurrency(itemDto.getDocumentCurrency());
			else
				odataLine.setCurrency("");
			if (!ServicesUtils.isEmpty(itemDto.getBaseUnitOfMeasure()))
				odataLine.setQUnit(itemDto.getBaseUnitOfMeasure());
			else
				odataLine.setQUnit("");
			if (!ServicesUtils.isEmpty(itemDto.getFlag()))
				odataLine.setFlag(itemDto.getFlag());
			else
				odataLine.setFlag("");

			odataLine.setStatus("");
			if (!ServicesUtils.isEmpty(itemDto.getStandard()))
				odataLine.setStd(itemDto.getStandard());
			else
				odataLine.setStd("");
			if (!ServicesUtils.isEmpty(itemDto.getSectionGrade()))
				odataLine.setGrade(itemDto.getSectionGrade());
			else
				odataLine.setGrade("");
			if (!ServicesUtils.isEmpty(itemDto.getSize()))
				odataLine.setSize(itemDto.getSize());
			else
				odataLine.setSize("");
			if (!ServicesUtils.isEmpty(itemDto.getLength()))
				odataLine.setLength(itemDto.getLength().toString());
			else
				odataLine.setLength("");
			if (!ServicesUtils.isEmpty(itemDto.getKgPerMeter()))
				odataLine.setKgperm(itemDto.getKgPerMeter().toString());
			else
				odataLine.setKgperm("");
			if (!ServicesUtils.isEmpty(itemDto.getEnteredOrdQuantity()))
				odataLine.setCu_ord_qty(itemDto.getEnteredOrdQuantity().toString());
			else
				odataLine.setCu_ord_qty("");
			if (!ServicesUtils.isEmpty(itemDto.getInspection()) && itemDto.getInspection() == true)
				odataLine.setInsp32("Y");
			else
				odataLine.setInsp32("N");
			if (!ServicesUtils.isEmpty(itemDto.getImpactTest()) && itemDto.getImpactTest() == true)
				odataLine.setImpactTest("Y");
			else
				odataLine.setImpactTest("N");
			if (!ServicesUtils.isEmpty(itemDto.getBendTest()) && itemDto.getBendTest() == true)
				odataLine.setBendTest("Y");
			else
				odataLine.setBendTest("N");
			if (!ServicesUtils.isEmpty(itemDto.getUltraSonicTest()) && itemDto.getUltraSonicTest())
				odataLine.setUltrsonic("Y");
			else
				odataLine.setUltrsonic("N");
			if (!ServicesUtils.isEmpty(itemDto.getHardnessTest()) && itemDto.getHardnessTest())
				odataLine.setHardness("Y");
			else
				odataLine.setHardness("N");
			if (!ServicesUtils.isEmpty(itemDto.getIsElementBoronRequired())
					&& itemDto.getIsElementBoronRequired() == true)
				odataLine.setBoron_req("Y");
			else
				odataLine.setBoron_req("N");
			if (!ServicesUtils.isEmpty(itemDto.getNetValue()))
				odataLine.setRate(itemDto.getNetValue());
			else
				odataLine.setRate("");
			if (!ServicesUtils.isEmpty(itemDto.getReferenceDocument()))
				odataLine.setRef_Doc_it(itemDto.getReferenceDocument());
			else
				odataLine.setRef_Doc_it("");
			if (!ServicesUtils.isEmpty(dto.getHeaderDto().getReferenceDocument()))
				odataLine.setRef_Doc(dto.getHeaderDto().getReferenceDocument());
			else
				odataLine.setRef_Doc("");
			odataItemList.add(odataLine);
		}
		headerDto.setSotoLi(odataItemList);
		//logger.debug("[SalesHeaderDao][convertToOdataReq] end : " + headerDto);
		System.err.println("[SalesHeader][convertToOdataReq] end : " + headerDto);
		return headerDto;
	}


}
