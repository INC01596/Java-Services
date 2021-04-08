package com.incture.cherrywork.repositories;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.MaterialMaster;
import com.incture.cherrywork.util.ServicesUtil;
import com.incture.cherrywork.dtos.MaterialContainerDto;
import com.incture.cherrywork.dtos.MaterialPlantDto;
import com.incture.cherrywork.dtos.SalesOrderMaterialMasterDto;

@SuppressWarnings("unused")
@Repository
public class IMaterialMasterRepositoryNew {

	@PersistenceContext
	private EntityManager entityManager;
	
	// Sandeep Kumar
	@SuppressWarnings("unchecked")
	public List<SalesOrderMaterialMasterDto>  getMaterialByName(String material) {
	List<SalesOrderMaterialMasterDto> listDto = new ArrayList<>();
	List<MaterialMaster> listEntity;
	try {
		String query = "select m from MaterialMaster m where m.material=:material";
		
		Query q = entityManager.createQuery(query);
		q.setParameter("material", material.toLowerCase());
		listEntity = q.getResultList();
		if (!ServicesUtil.isEmpty(listEntity)) {
			for (MaterialMaster entity : listEntity) {
				System.err.println("exception");
				SalesOrderMaterialMasterDto dto = new SalesOrderMaterialMasterDto();
				dto =ObjectMapperUtils.map(entity, SalesOrderMaterialMasterDto.class);
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
			
			return listDto;
		} else
			return null;
		
	} catch (Exception e) {
	e.printStackTrace();
		return null;
  }

	}
	
	
	
	
	//Sandeep Kumar
	public List<MaterialPlantDto> getMaterialNames(){
		List<MaterialPlantDto> materialList = new ArrayList<>();
		try {
			String query = "select distinct material, plant from MaterialMaster";
			Query q = entityManager.createQuery(query);
			List<Object[]> objList = q.getResultList();
			System.err.println(objList);
			for (Object[] obj : objList) {
				MaterialPlantDto material = new MaterialPlantDto();
				if (!ServicesUtil.isEmpty(obj[0]))
					material.setMaterialDescription((String) obj[0]);
				if (!ServicesUtil.isEmpty(obj[1]))
					material.setPlant((String) obj[1]);
				materialList.add(material);
			}
			return materialList;
		}catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
		
	}
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
	
	
	
	
	//Sandeep Kumar
	public static List<String> setQualityTest(SalesOrderMaterialMasterDto dto) {
		ArrayList<String> qualityTestList = new ArrayList<>();
		if (dto.getBendTest()!=null && dto.getBendTest()== true)
			qualityTestList.add("BT");
		if (dto.getImpactTest() != null && dto.getImpactTest() == true)
			qualityTestList.add("IT");
		if (dto.getUltraLightTest()!=null && dto.getUltraLightTest() == true)
			qualityTestList.add("UL");
		
		System.err.println(qualityTestList.toString());
		return qualityTestList;
	}
	
	
}
