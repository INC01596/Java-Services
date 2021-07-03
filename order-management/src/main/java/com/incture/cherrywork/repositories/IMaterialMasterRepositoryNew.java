package com.incture.cherrywork.repositories;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.incture.cherrywork.entities.MaterialMaster;
import com.incture.cherrywork.odata.dto.OdataMaterialDto;
import com.incture.cherrywork.odata.dto.OdataMaterialStartDto;
import com.incture.cherrywork.sales.constants.EnUpdateIndicator;
import com.incture.cherrywork.services.SalesOrderOdataServices;
import com.incture.cherrywork.util.ServicesUtil;



import com.incture.cherrywork.dtos.MaterialContainerDto;
import com.incture.cherrywork.dtos.MaterialPlantDto;
import com.incture.cherrywork.dtos.SalesOrderMaterialMasterDto;

@SuppressWarnings("unused")

@Repository
public class IMaterialMasterRepositoryNew {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private IMaterialMasterRepository mmrepo;
	
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
	@SuppressWarnings("unchecked")
<<<<<<< HEAD
	public List<MaterialMaster> getMaterialNames(){
		
		try {
			
		return mmrepo.findAll();
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
		
	}
=======
    public List<MaterialMaster> getMaterialNames(){
       
        try {
           
        return mmrepo.findAll();
           
           
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
       
    }	
	

>>>>>>> 4f9ece72921128022d6557ffd6e9087b19e57233

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
	
	public List<SalesOrderMaterialMasterDto> convertData(OdataMaterialStartDto odataMaterialStartDto) {
		
		List<SalesOrderMaterialMasterDto> listMaterialMasterDto = new ArrayList<SalesOrderMaterialMasterDto>();
		try {
			for (OdataMaterialDto odataMaterialDto : odataMaterialStartDto.getD().getResults()) {
				SalesOrderMaterialMasterDto materialMasterDto = new SalesOrderMaterialMasterDto();
				materialMasterDto.setClientSpecific(Integer.parseInt(odataMaterialDto.getMandt()));
				materialMasterDto.setKey(odataMaterialDto.getZzkey());
				materialMasterDto.setCatalogKey(odataMaterialDto.getCatKey());
				materialMasterDto.setLengthMapKey(odataMaterialDto.getMapKey());
				materialMasterDto.setMaterial(odataMaterialDto.getMatnr());
				materialMasterDto.setMaterialDescription(odataMaterialDto.getArktx());
				materialMasterDto.setPlant(odataMaterialDto.getWerks());
				materialMasterDto.setStandard(odataMaterialDto.getZzstd());
				materialMasterDto.setStandardDescription(odataMaterialDto.getZzstdDes());
				materialMasterDto.setSectionGrade(odataMaterialDto.getZzgrade());
				materialMasterDto.setSectionGradeDescription(odataMaterialDto.getZzgradeDes());
				materialMasterDto.setSize(odataMaterialDto.getZzsize());
				materialMasterDto.setKgPerMeter(new BigDecimal(odataMaterialDto.getZzkgperm()));
				materialMasterDto.setLength(new BigDecimal(odataMaterialDto.getZzlength()));
				materialMasterDto.setBarsPerBundle(new BigDecimal(odataMaterialDto.getZzpcsPerBndl()));
				materialMasterDto.setSectionGroup(odataMaterialDto.getZzsectionGrp());
				materialMasterDto.setLevel2Id(odataMaterialDto.getZzl2id());
				if (odataMaterialDto.getZzceMark().equalsIgnoreCase("False"))
					materialMasterDto.setCeLogo(false);
				else
					materialMasterDto.setCeLogo(true);
				materialMasterDto.setSection(odataMaterialDto.getZzsection());
				materialMasterDto.setSizeGroup(new BigDecimal(odataMaterialDto.getZzsizeGrp()));
				if (odataMaterialDto.getZzisiMark().equalsIgnoreCase("False"))
					materialMasterDto.setIsiLogo(false);
				else
					materialMasterDto.setIsiLogo(true);
				if (odataMaterialDto.getZzqltyBt().equalsIgnoreCase("False"))
					materialMasterDto.setBendTest(false);
				else
					materialMasterDto.setBendTest(true);
				if (odataMaterialDto.getZzqltyIt().equalsIgnoreCase("False"))
					materialMasterDto.setImpactTest(false);
				else
					materialMasterDto.setImpactTest(true);
				if (odataMaterialDto.getZzqltyUl().equalsIgnoreCase("False"))
					materialMasterDto.setUltraLightTest(false);
				else
					materialMasterDto.setUltraLightTest(true);
				materialMasterDto.setGradePricingGroup(odataMaterialDto.getZzgradePrGrp());
				materialMasterDto.setKgpermPricingGroup(odataMaterialDto.getZzkgpermPrGrp());
				materialMasterDto.setSizePricingGroup(odataMaterialDto.getZzsizePrGrp());
				materialMasterDto.setLengthPricingGroup(odataMaterialDto.getZzlengthPrGrp());
				materialMasterDto.setBundleWeight(new BigDecimal(odataMaterialDto.getZzbdlWt()));
				if (odataMaterialDto.getUpdateInd().equals("I"))
					materialMasterDto.setUpdateIndicator(SalesOrderEnUpdateIndicator.INSERT);
				else if (odataMaterialDto.getUpdateInd().equals("U"))
					materialMasterDto.setUpdateIndicator(SalesOrderEnUpdateIndicator.UPDATE);
				else if (odataMaterialDto.getUpdateInd().equals("D"))
					materialMasterDto.setUpdateIndicator(SalesOrderEnUpdateIndicator.DELETE);
				// materialMasterDto.setChangedOn(
				// new
				// SimpleDateFormat("yyyyMMddhhmmss").parse(odataMaterialDto.getChangedOn()));
				if (!ServicesUtil.isEmpty(odataMaterialDto.getChangedOn())) {
					// String s = odataSchHeaderDto.getChangedOn().substring(6,
					// 19);
					long l = Long.parseLong(odataMaterialDto.getChangedOn());
					Timestamp d = new Timestamp(l);
					materialMasterDto.setChangedOn(d);
				} else
					materialMasterDto.setChangedOn(null);
				if (odataMaterialDto.getSyncStat().equalsIgnoreCase("False"))
					materialMasterDto.setSyncStatus(false);
				else
					materialMasterDto.setSyncStatus(true);
				if (odataMaterialDto.getContainer().equals("Y"))
					materialMasterDto.setContainer(true);
				else
					materialMasterDto.setContainer(false);
				materialMasterDto.setSearchField(odataMaterialDto.getSearch());
				listMaterialMasterDto.add(materialMasterDto);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return listMaterialMasterDto;
	}
	

   
	@SuppressWarnings("unchecked")
	public ResponseEntity<Object> materialscheduler() {
		
		ArrayList<String> list = new ArrayList<>();
		
		try {
			OdataMaterialStartDto odataMaterialStartDto = SalesOrderOdataServices.materialScheduler();
			List<SalesOrderMaterialMasterDto> listMaterialMasterDto = convertData(odataMaterialStartDto);
			for (SalesOrderMaterialMasterDto materialMasterDto : listMaterialMasterDto) {
				if (materialMasterDto.getUpdateIndicator().equals(EnUpdateIndicator.DELETE)) {
					
					Query query = entityManager.createQuery(
							"delete from MaterialMasterDo m where m.material=:material and m.standard=:standard and "
									+ "m.length=:length and m.sectionGrade=:sectionGrade and m.plant=:plant and m.size=:size and m.kgPerMeter=:kgPerMeter and m.container=:container");
					query.setParameter("material", materialMasterDto.getMaterial());
					query.setParameter("standard", materialMasterDto.getStandard());
					query.setParameter("length", materialMasterDto.getLength());
					query.setParameter("sectionGrade", materialMasterDto.getSectionGrade());
					query.setParameter("plant", materialMasterDto.getPlant());
					query.setParameter("size", materialMasterDto.getSize());
					query.setParameter("kgPerMeter", materialMasterDto.getKgPerMeter());
					query.setParameter("container", materialMasterDto.getContainer());

					query.executeUpdate();
					list.add("D-"+materialMasterDto.getMaterialMasterId());
					
				} else {
					
					Query query = entityManager.createQuery(
							"select m.materialMasterId from MaterialMasterDo m where m.material=:material and m.standard=:standard and "
									+ "m.length=:length and m.sectionGrade=:sectionGrade and m.plant=:plant and m.size=:size and m.kgPerMeter=:kgPerMeter and m.container=:container");
					query.setParameter("material", materialMasterDto.getMaterial());
					query.setParameter("standard", materialMasterDto.getStandard());
					query.setParameter("length", materialMasterDto.getLength());
					query.setParameter("sectionGrade", materialMasterDto.getSectionGrade());
					query.setParameter("plant", materialMasterDto.getPlant());
					query.setParameter("size", materialMasterDto.getSize());
					query.setParameter("kgPerMeter", materialMasterDto.getKgPerMeter());
					query.setParameter("container", materialMasterDto.getContainer());

					List<String> objList = query.getResultList();
					

					if (objList != null && objList.size() > 0) {
						String matId = objList.get(0).toString();
						list.add("U-" + matId);
						
						StringBuffer sb = new StringBuffer("update MATERIAL set ");
						sb.append("BARS_PER_BUNDLE = " + materialMasterDto.getBarsPerBundle() + ", ");
						sb.append("BEND_TEST = " + materialMasterDto.getBendTest() + ", ");
						sb.append("BUNDLE_WEIGHT = " + materialMasterDto.getBundleWeight() + ", ");
						sb.append("CATALOG_KEY = '" + materialMasterDto.getCatalogKey() + "', ");
						sb.append("CE_LOGO = " + materialMasterDto.getCeLogo() + ", ");
						sb.append("CONTAINER = " + materialMasterDto.getContainer() + ", ");
						sb.append("GRADE_PRICING_GROUP = '" + materialMasterDto.getGradePricingGroup() + "', ");
						sb.append("IMPACT_TEST = " + materialMasterDto.getImpactTest() + ", ");
						sb.append("ISI_LOGO = " + materialMasterDto.getIsiLogo() + ", ");
						sb.append("KEY = '" + materialMasterDto.getKey() + "', ");
						sb.append("KG_PER_METER = " + materialMasterDto.getKgPerMeter() + ", ");
						sb.append("KGPERM_PRICING_GROUP = '" + materialMasterDto.getKgpermPricingGroup() + "', ");
						sb.append("LENGTH = " + materialMasterDto.getLength() + ", ");
						sb.append("LENGTH_MAP_KEY = '" + materialMasterDto.getLengthMapKey() + "', ");
						sb.append("LENGTH_PRICING_GROUP = '" + materialMasterDto.getLengthPricingGroup() + "', ");
						sb.append("LEVEL2_ID = '" + materialMasterDto.getLevel2Id() + "', ");
						sb.append("MATERIAL = '" + materialMasterDto.getMaterial() + "', ");
						sb.append("MATERIAL_DESC = '" + materialMasterDto.getMaterialDescription() + "', ");
						sb.append("PLANT = '" + materialMasterDto.getPlant() + "', ");
						sb.append("SEARCH_FIELD = '" + materialMasterDto.getSearchField() + "', ");
						sb.append("SECTION = '" + materialMasterDto.getSection() + "', ");
						sb.append("SECTION_GRADE = '" + materialMasterDto.getSectionGrade() + "', ");
						sb.append("SECTION_GRADE_DESC = '" + materialMasterDto.getSectionGradeDescription() + "', ");
						sb.append("SECTION_GROUP = '" + materialMasterDto.getSectionGroup() + "', ");
						sb.append("SECTION_PRICING_GROUP = '" + materialMasterDto.getSectionPricingGroup() + "', ");
						sb.append("SIZE = '" + materialMasterDto.getSize() + "', ");
						sb.append("SIZE_GROUP = " + materialMasterDto.getSizeGroup() + ", ");
						sb.append("SIZE_PRICING_GROUP = '" + materialMasterDto.getSizePricingGroup() + "', ");
						sb.append("STANDARD = '" + materialMasterDto.getStandard() + "', ");
						sb.append("STANDARD_DESC = '" + materialMasterDto.getStandardDescription() + "', ");
						sb.append("ULTRALIGHT_TEST = " + materialMasterDto.getUltraLightTest() + ", ");
						sb.append("SYNC_STAT = " + materialMasterDto.getSyncStatus() + ", ");
						sb.append("UPDATE_INDICATOR = " + materialMasterDto.getUpdateIndicator() + " ");
						sb.append("where MATERIAL_MASTER_ID = '" + materialMasterDto.getMaterialMasterId() + "'");

						materialMasterDto.setMaterialMasterId(matId);
					MaterialMaster d= ObjectMapperUtils.map(materialMasterDto, MaterialMaster.class);
					mmrepo.save(d);
						
						
					} 
					else {
						materialMasterDto.setMaterialMasterId(UUID.randomUUID().toString().replaceAll("-", ""));
						list.add("I-" + materialMasterDto.getMaterialMasterId());
						mmrepo.save(ObjectMapperUtils.map(materialMasterDto, MaterialMaster.class));
						
					}
				}
			}
			
		
			String ackResponse = SalesOrderOdataServices.materialAckScheduler();
	        return ResponseEntity.ok().body(list);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating");
			}
			
	
	
		

}
	
}
	
		
		
	
	
	

