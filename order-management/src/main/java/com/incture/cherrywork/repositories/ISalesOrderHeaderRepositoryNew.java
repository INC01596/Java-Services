package com.incture.cherrywork.repositories;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.dtos.HeaderDetailUIDto;
import com.incture.cherrywork.dtos.HeaderIdDto;
import com.incture.cherrywork.dtos.MaterialContainerDto;


import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderHeaderItemDto;
import com.incture.cherrywork.dtos.SalesOrderItemDto;
import com.incture.cherrywork.dtos.SalesOrderMaterialMasterDto;
//import com.incture.cherrywork.dtos.SequenceDto;
import com.incture.cherrywork.entities.MaterialMaster;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.entities.SalesOrderItem;
import com.incture.cherrywork.sales.constants.EnOrderActionStatus;
import com.incture.cherrywork.sales.constants.EnUpdateIndicator;
import com.incture.cherrywork.util.ServicesUtil;


@SuppressWarnings("unused")
@Repository
public class ISalesOrderHeaderRepositoryNew {

	@PersistenceContext
	private EntityManager entityManager;
	//Sandeep Kumar
	public List<Integer> deleteDraftedVersion(String val) {
		List<Integer> l=new ArrayList<>();
		try{
			Query header1 = entityManager.createQuery("delete from SalesOrderItem s where s.salesHeaderId=:salesHeaderId");
			header1.setParameter("salesHeaderId", val);
			int result1=header1.executeUpdate();
			l.add(result1);
			
			Query header = entityManager.createQuery("delete from SalesOrderHeader s where s.salesHeaderId=:salesHeaderId");
			header.setParameter("salesHeaderId", val);
			int result2=header.executeUpdate();
			l.add(result2);
		   } catch(Exception e) {
			System.err.println("try found exception");
			e.printStackTrace();
		}
		
		return l;
	}
	//Sandeep Kumar
	@SuppressWarnings("unchecked")
	public List<String> getReferenceList(HeaderDetailUIDto dto) {
		
		List<String> list = new ArrayList<>();
		StringBuffer query = new StringBuffer(
				"select distinct(s.s4DocumentId) from SalesOrderHeader s where s.documentType=:documentType and s.documentProcessStatus="
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
	
	//Sandeep Kumar
	@SuppressWarnings("unchecked")
	public List<SalesOrderHeader> getManageService(HeaderDetailUIDto dto) {
		List<SalesOrderHeader> headerEntityList = new ArrayList<>();
		String plant = listToString(dto.getPlant());
		try {
			StringBuffer headerQuery = new StringBuffer(
					"select s from SalesOrderHeader s where s.documentType=:documentType");
			if (!ServicesUtil.isEmpty(dto.getCreatedBy()))
				headerQuery.append(" and s.createdBy=:createdBy");
			if (dto.getDocumentProcessStatus() == null)
				headerQuery.append(" and s.documentProcessStatus in (" + EnOrderActionStatus.CREATED.ordinal() + ","
						+ EnOrderActionStatus.CANCELLED.ordinal() + "," + EnOrderActionStatus.OPEN.ordinal() + ")");
			else
				headerQuery.append(" and s.documentProcessStatus = " + dto.getDocumentProcessStatus().ordinal() + "");
			if (dto.getDocumentType().equals("OR")) {
				if (dto.getIsOpen() == true)
					headerQuery.append(" and isOpen=true");
				else {
					headerQuery.append(" and isOpen=false");
					if (!ServicesUtil.isEmpty(dto.getIsCustomer()) && dto.getIsCustomer() == true) {
						headerQuery.append(" and s.createdDate >= ADD_MONTHS(CURRENT_DATE,-6)");
					}
				}
			}
			if (!ServicesUtil.isEmpty(dto.getStpId())) {
				String STP = listToString(dto.getStpId());
				headerQuery.append(" and s.soldToParty in (" + STP + ")");
			}
			if (!ServicesUtil.isEmpty(dto.getSalesGroup()))
				headerQuery.append(" and s.salesGroup=:salesGroup");
			if (!ServicesUtil.isEmpty(dto.getPlant()))
				headerQuery.append(" and s.plant in (" + plant + ")");
			headerQuery.append(" order by s.createdDate desc");
			Query hq = entityManager.createQuery(headerQuery.toString());
			if (!ServicesUtil.isEmpty(dto.getCreatedBy()))
				hq.setParameter("createdBy", dto.getCreatedBy());
			hq.setParameter("documentType", dto.getDocumentType());
			if (!ServicesUtil.isEmpty(dto.getSalesGroup()))
				hq.setParameter("salesGroup", dto.getSalesGroup());
			headerEntityList = hq.getResultList();	
			
	  } catch(Exception e) {
		System.err.println("try found exception");
		e.printStackTrace();
	}
		
		return headerEntityList;
	}
	
	//Sandeep Kumar
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
			hq.setParameter("documentProcessStatus", dto.getDocumentProcessStatus());
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
						lineItemDto =ObjectMapperUtils.map(lineItemEntity, SalesOrderItemDto.class);
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

				} catch(Exception e) {
					System.err.println("try found exception");
					e.printStackTrace();
				}
				responseList.add(salesHeaderItemDto);
			}
		}catch(Exception e) {
			System.err.println("try found exception");
			e.printStackTrace();
		}
		
		
		return responseList;
	}
	
	//Sandeep Kumar
	@SuppressWarnings("unchecked")
	public SalesOrderHeaderItemDto getHeaderById(HeaderIdDto dto)
	{
		List<SalesOrderHeader> headerEntityList = new ArrayList<>();
	List<SalesOrderItem> lineItemEntityList = new ArrayList<>();
	try {
		StringBuffer headerQuery = new StringBuffer("select s from SalesOrderHeader s where ");
		if (!ServicesUtil.isEmpty(dto.gets4DocumentId()))
			headerQuery.append("s.s4DocumentId=:s4DocumentId");
		if (!ServicesUtil.isEmpty(dto.getsalesHeaderId()))
		{
			if (!ServicesUtil.isEmpty(dto.gets4DocumentId())){			
				
				headerQuery.append(" and s.salesHeaderId=:salesHeaderId");
			}
			else
				headerQuery.append("s.salesHeaderId=:salesHeaderId");
			
		}
		
		Query hq = entityManager.createQuery(headerQuery.toString());
		if (!ServicesUtil.isEmpty(dto.gets4DocumentId()))
			hq.setParameter("s4DocumentId", dto.gets4DocumentId());
		if (!ServicesUtil.isEmpty(dto.getsalesHeaderId()))
			hq.setParameter("salesHeaderId", dto.getsalesHeaderId());
		headerEntityList = hq.getResultList();
		System.err.println("try found exception");
		for (SalesOrderHeader headerEntity : headerEntityList) {
			SalesOrderHeaderItemDto salesHeaderItemDto = new SalesOrderHeaderItemDto();
			List<SalesOrderItemDto> lineItemDtoList = new ArrayList<>();
			SalesOrderHeaderDto headerDto = new SalesOrderHeaderDto();
			headerDto = ObjectMapperUtils.map(headerEntity, SalesOrderHeaderDto.class);
			salesHeaderItemDto.setHeaderDto(headerDto);
			try {
				System.err.println("try found exception");
				StringBuffer lineItemQuery = new StringBuffer("select i from SalesOrderItem i where ");
				if (!ServicesUtil.isEmpty(headerDto.getS4DocumentId()))
					  lineItemQuery.append("i.salesOrderHeader.s4DocumentId=:s4DocumentId");
				else
					lineItemQuery.append("i.salesHeaderId=:salesHeaderId");
				System.err.println("try found exception 3");
				lineItemQuery.append(" and i.updateIndicator <>:" + EnUpdateIndicator.DELETE.ordinal()
						+ " order by i.lineItemNumber asc");
				Query iq =entityManager.createQuery(lineItemQuery.toString());
				if (!ServicesUtil.isEmpty(headerDto.getS4DocumentId())){
					
				
					iq.setParameter("s4DocumentId", headerDto.getS4DocumentId());
				}
				else
				{
				iq.setParameter("salesHeaderId", headerDto.getSalesHeaderId());
				}
				
				lineItemEntityList = iq.getResultList();
				for (SalesOrderItem lineItemEntity : lineItemEntityList) {
					SalesOrderItemDto lineItemDto = new SalesOrderItemDto();
					lineItemDto =ObjectMapperUtils.map(lineItemEntity, SalesOrderItemDto.class);
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
			}catch(Exception e) {
					System.err.println("try found exception123");
					e.printStackTrace();
			}
			return salesHeaderItemDto;
		}
	}catch(Exception e) {
			System.err.println("try found exception");
			e.printStackTrace();
	}
	
		return null;
	}
	// Sandeep Kumar
	public List<SalesOrderMaterialMasterDto> getMaterialByDesc(MaterialContainerDto materialContainerDto) {
		
		List<SalesOrderMaterialMasterDto> listDto = new ArrayList<>();
		List<MaterialMaster> listEntity;
		String plant = listToString(materialContainerDto.getPlant());
		try {
			// String query = "select m from MaterialMasterDo m where
			// LOWER(m.materialDescription) like \'%"
			// + materialDescription.toLowerCase() + "%\'";
			
			StringBuffer query = new StringBuffer("Select m from MaterialMaster m where  m.materialDescription=:materialDescription and m.container=:container" + " and plant in (" + plant + ") order by materialDescription ASC");
				
			/*StringBuffer query = new StringBuffer(
					"select  materialMasterId, barsPerBundle,bendTest, bundleWeight,catalogKey, ceLogo, changedBy, changedOn,"
							+ " clientSpecific,createdBy, createdOn, gradePricingGroup, impactTest, isiLogo, key, kgPerMeter, kgpermPricingGroup,"
							+ " length, lengthMapKey, lengthPricingGroup, level2Id, material,materialDescription, plant,section, sectionGrade, sectionGradeDescription,"
							+ " sectionGroup, sectionPricingGroup, size, sizeGroup, sizePricingGroup,standard,standardDescription, syncStatus, ultraLightTest, updateIndicator,rollingPlanFlag"
							+ " from MaterialMaster m where lower(m.searchField) like lower('%"
							+ materialContainerDto.getMaterialDescription().toLowerCase().replace("*", "%")
							+ "%') and container = " + materialContainerDto.getContainer() + " and plant in (" + plant
							+ ")order by materialDescription ASC");*/
			Query q =entityManager.createQuery(query.toString());
			 q.setParameter("materialDescription",materialContainerDto.getMaterialDescription() );
			 q.setParameter("container",materialContainerDto.getContainer() );
			
			
			@SuppressWarnings("unchecked")
			List<MaterialMaster> objList = q.getResultList();
			System.err.println(q.getResultList());
			
			if (!ServicesUtil.isEmpty(objList)) {
				for (MaterialMaster obj : objList) {
					SalesOrderMaterialMasterDto materialDto = new SalesOrderMaterialMasterDto();
					materialDto=ObjectMapperUtils.map(obj, SalesOrderMaterialMasterDto.class);
					if (!ServicesUtil.isEmpty(setQualityTest(materialDto))) 
					{
						materialDto.setQualityTestList(setQualityTest(materialDto));
						materialDto.setDefaultQualityTestList(setQualityTest(materialDto));
					} 
					else 
					{
						materialDto.setQualityTestList(new ArrayList<String>());
						materialDto.setDefaultQualityTestList(new ArrayList<String>());
					}
					listDto.add(materialDto);
				}return listDto; 
			}
			System.err.println("Null return exception");
			return null; 
			
		} catch (Exception e) {
			System.err.println("try found exception");
			e.printStackTrace();
			
		}
		return listDto;
	}
	
	
	
	/*@SuppressWarnings("unchecked")
	public List<MaterialMasterDto>  getMaterialByName(String material) {
	List<MaterialMasterDto> listDto = new ArrayList<>();
	List<MaterialMaster> listEntity;
	try {
		String query = "select m from MaterialMaster m where m.material=:material";
		System.err.println("Null return exception");
		Query q = entityManager.createQuery(query);
		q.setParameter("material", material.toLowerCase());
		listEntity = q.getResultList();
		if (!ServicesUtil.isEmpty(listEntity)) {
			for (MaterialMaster entity : listEntity) {
				System.err.println("exception");
				MaterialMasterDto dto = new MaterialMasterDto();
				dto =ObjectMapperUtils.map(entity, MaterialMasterDto.class);
				System.err.println(dto!= null ? dto.toString() :"blank");
				if (!ServicesUtil.isEmpty(setQualityTest(dto))) {
					dto.setQualityTestList(setQualityTest(dto));
					dto.setDefaultQualityTestList(setQualityTest(dto));
				} else {
					dto.setQualityTestList(new ArrayList<String>());
					dto.setDefaultQualityTestList(new ArrayList<String>());
				}
				listDto.add(dto);
			}
			System.out.println("4444");
			return listDto;
		} else
			return null;
		
	} catch (Exception e) {
	e.printStackTrace();
		
	}
	return listDto;
}*/

	//Sandeep KUmar
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
	//Sandeep KUmar
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
	
	//Sandeep KUmar
	public static List<String> setQualityTest(SalesOrderMaterialMasterDto dto) {
		ArrayList<String> qualityTestList = new ArrayList<>();
		if (dto.getBendTest()!=null && dto.getBendTest()== true)
			qualityTestList.add("BT");
		if (dto.getImpactTest() != null && dto.getImpactTest() == true)
			qualityTestList.add("IT");
		if (dto.getUltraLightTest()!=null && dto.getUltraLightTest() == true)
			qualityTestList.add("UL");
		
		
		return qualityTestList;
	}

	
	
	}
	
	
	
	


