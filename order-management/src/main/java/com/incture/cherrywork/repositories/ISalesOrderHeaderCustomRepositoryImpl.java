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
					if (dto.getLength().equals("other") && !ServicesUtils.isEmpty(dto.getOtherLength())) {
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
	public SalesOrderOdataHeaderDto getOdataReqPayload(String salesHeaderId) {
		// logger.debug("[SalesHeaderDao][getOdataReqPayload] Started : " +
		// salesHeaderId);
		// SalesItemDetailsDao salesItemDetailsDao = new SalesItemDetailsDao();
		SalesOrderOdataHeaderDto odataHeaderDto = new SalesOrderOdataHeaderDto();
		List<SalesOrderHeader> headerEntityList = new ArrayList<>();
		List<SalesOrderItem> lineItemListDo = new ArrayList<>();
		try {
			String headerQuery = "select s from SalesOrderHeader s where s.salesHeaderId=:salesHeaderId";
			Query hq = entityManager.createQuery(headerQuery);
			hq.setParameter("salesHeaderId", salesHeaderId);
			headerEntityList = hq.getResultList();
			for (SalesOrderHeader entity : headerEntityList) {
				SalesOrderHeaderItemDto salesHeaderItemDto = new SalesOrderHeaderItemDto();
				SalesOrderHeaderDto headerDto = new SalesOrderHeaderDto();
				List<SalesOrderItemDto> lineItemListDto = new ArrayList<>();
				headerDto = ObjectMapperUtils.map(entity, SalesOrderHeaderDto.class);
				salesHeaderItemDto.setHeaderDto(headerDto);
				String itemQuery = "select s from SalesOrderItem s where s.salesHeaderId=:salesHeaderId";
				Query iq = entityManager.createQuery(itemQuery);
				iq.setParameter("salesHeaderId", salesHeaderId);
				lineItemListDo = iq.getResultList();
				for (SalesOrderItem item : lineItemListDo) {
					SalesOrderItemDto itemDto = new SalesOrderItemDto();
					itemDto = ObjectMapperUtils.map(item, SalesOrderItemDto.class);
					lineItemListDto.add(itemDto);
				}
				salesHeaderItemDto.setLineItemList(lineItemListDto);
				odataHeaderDto = ObjectMapperUtils.map(salesHeaderItemDto, SalesOrderOdataHeaderDto.class);
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
			value = values.get(0);
		} catch (Exception e) {
			//logger.error("[LookUpDao][getLookupValue] Exception :" + e.getMessage());
			e.printStackTrace();
		}
		return value;
	}

}
