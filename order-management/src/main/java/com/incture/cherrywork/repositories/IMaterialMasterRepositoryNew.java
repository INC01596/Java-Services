package com.incture.cherrywork.repositories;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.MaterialMaster;
import com.incture.cherrywork.util.ServicesUtil;
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
