package com.incture.cherrywork.services;



import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.incture.cherrywork.dtos.MaterialContainerDto;

import com.incture.cherrywork.dtos.SalesOrderHeaderDto;
import com.incture.cherrywork.dtos.SalesOrderMaterialMasterDto;
import com.incture.cherrywork.entities.MaterialMaster;
import com.incture.cherrywork.entities.SalesOrderHeader;
import com.incture.cherrywork.repositories.IMaterialMasterRepository;
import com.incture.cherrywork.repositories.IMaterialMasterRepositoryNew;
import com.incture.cherrywork.repositories.ISalesOrderHeaderRepositoryNew;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.dtos.MaterialPlantDto;


@SuppressWarnings("unused")
@Service("MaterialMasterServices")
@Transactional
public class MaterialMasterServices implements IMaterialMasterServices {
	
	
	@Autowired
	private IMaterialMasterRepositoryNew mrepo;
	
	@Autowired
	private IMaterialMasterRepository mmrepo;
	
	@Override
	public ResponseEntity<Object> getMaterialByDesc(MaterialContainerDto dto) {
		try{
		List<SalesOrderMaterialMasterDto> listDto=mrepo.getMaterialByDesc(dto);
		return ResponseEntity.ok(listDto);
	   }catch (Exception e) {
		System.err.println("try found exception");
		e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<Object> getMaterialByName(String material) {
		List<SalesOrderMaterialMasterDto> res=mrepo.getMaterialByName(material);
		return ResponseEntity.ok(res);
	}
	
	@Override
	public ResponseEntity<Object> create(SalesOrderMaterialMasterDto  Dto) {
		MaterialMaster materialMaster = ObjectMapperUtils.map(Dto, MaterialMaster.class);
		MaterialMaster savedmaterialMaster = mmrepo.save(materialMaster);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("id").toUri();
		return ResponseEntity.created(location)
				.body(ObjectMapperUtils.map(savedmaterialMaster,SalesOrderMaterialMasterDto.class));
	}

	@Override
	public ResponseEntity<Object> getMaterialNames() {
		try{
			List<MaterialPlantDto> materialList=mrepo.getMaterialNames();
			return ResponseEntity.ok(materialList);
		   }catch (Exception e) {
			System.err.println("try found exception");
			e.printStackTrace();
			return null;
			}
			
	}

	
	
	/*
	public Response manualSearchResult(SearchHeaderDto searchDto) {
		return materialMasterDao.manualSearchResult(searchDto);
	}
	
	public Response getSearchDropdown(SearchHeaderDto searchDto) {
		return materialMasterDao.getSearchDropdown(searchDto);
	}
	
	public void updateSchedulerData(){
		OdataServices odataServices = new OdataServices();
		OdataMaterialStartDto odataMaterialStartDto = odataServices.materialScheduler();
		
	}
	*/
	@Override
	public ResponseEntity<Object> materialScheduler(){
		try{
		
			return (mrepo.materialscheduler());
		   }catch (Exception e) {
			System.err.println("try found exception");
			e.printStackTrace();
			return null;
			}
		
	}
}

