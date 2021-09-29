package com.incture.cherrywork.services;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.MaterialSchedulerLogsDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.SchedulerTableDto;
import com.incture.cherrywork.entities.MaterialSchedulerLogs;
import com.incture.cherrywork.repositories.IMaterialSchedulerLogs;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.ServicesUtil;

@Service("MaterialSchedulerServiceImpl")
@Transactional
public class MaterialSchedulerServiceImpl implements MaterialSchedulerService{
	
	@Autowired
	private IMaterialSchedulerLogs iMaterialSchedulerLogs;
	
	@Override
	public ResponseEntity saveInDB(MaterialSchedulerLogsDto materialSchedulerLogsDto) {
		try{
			materialSchedulerLogsDto.setId(ServicesUtil.randomId());
			MaterialSchedulerLogs toSave = ObjectMapperUtils.map(materialSchedulerLogsDto, MaterialSchedulerLogs.class);
			System.err.println("[MaterialSchedulerServiceImpl][saveInDB] materialSchedulerLogsDto" + materialSchedulerLogsDto.toString());
			MaterialSchedulerLogs savedData = iMaterialSchedulerLogs.save(toSave);
			if (savedData == null) {
				return new ResponseEntity(null, HttpStatus.BAD_REQUEST, "CREATION_FAILED", ResponseStatus.FAILED);
			}
			String msg = "Successfully updated in hana";
			return new ResponseEntity(null, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEntity listAllLogsInIst(LocalDateTime startDate, LocalDateTime endDate) {
		try {
			if (startDate != null && endDate != null) {
				System.err.println("startDate : " + startDate);
				System.err.println("endDate : " + endDate);
				List<MaterialSchedulerLogs> list = iMaterialSchedulerLogs.findMaterialLogsBetweenDate(startDate, endDate);
				if (list != null && !list.isEmpty()) {
					return new ResponseEntity(list, HttpStatus.OK, " DATA_FOUND", ResponseStatus.SUCCESS);
				} else {
					return new ResponseEntity("", HttpStatus.NO_CONTENT, "EMPTY_LIST", ResponseStatus.FAILED);
				}
			} else {
				return new ResponseEntity("", HttpStatus.BAD_REQUEST, "Please enter start date and end date",
						ResponseStatus.FAILED);
			}
		}catch(Exception e){
			
			e.printStackTrace();
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}
	
}
