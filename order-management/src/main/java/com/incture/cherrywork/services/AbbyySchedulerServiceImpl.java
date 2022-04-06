package com.incture.cherrywork.services;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.AbbyySchedulerLogsDto;
import com.incture.cherrywork.dtos.AbbyyTableDto;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.dtos.SchedulerTableDto;

import com.incture.cherrywork.entities.AbbyySchedulerLogs;

import com.incture.cherrywork.repositories.IAbbyySchedulerLogs;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.ServicesUtil;

@Service("AbbyySchedulerServiceImpl")
@Transactional
public class AbbyySchedulerServiceImpl implements AbbyySchedulerService{
	
	@Autowired
	private IAbbyySchedulerLogs iAbbyySchedulerLogs;
	
	
	
	@Override
	public ResponseEntity saveInDB(AbbyySchedulerLogsDto AbbyySchedulerLogsDto) {
		try{
			AbbyySchedulerLogsDto.setId(ServicesUtil.randomId());
			AbbyySchedulerLogs toSave = ObjectMapperUtils.map(AbbyySchedulerLogsDto, AbbyySchedulerLogs.class);
			System.err.println("[AbbyySchedulerServiceImpl][saveInDB] AbbyySchedulerLogsDto" + AbbyySchedulerLogsDto.toString());
			AbbyySchedulerLogs savedData = iAbbyySchedulerLogs.save(toSave);
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
		List<AbbyyTableDto> data = new ArrayList<AbbyyTableDto>();
		try {
			if (startDate != null && endDate != null) {
				System.err.println("startDate : " + startDate);
				System.err.println("endDate : " + endDate);
				List<AbbyySchedulerLogs> list = iAbbyySchedulerLogs.findAbbyyLogsBetweenDate(startDate, endDate);
				if (list != null && !list.isEmpty()) {
					for(AbbyySchedulerLogs a : list){
						AbbyyTableDto b = new AbbyyTableDto();
						b.setId(a.getId());
						b.setLoggedMessage(a.getLoggedMessage());
						b.setTimeStamp(a.getTimeStamp());
						b.setIndianTime(a.getIstTimeStamp().toString());
						data.add(b);
					}
					return new ResponseEntity(data, HttpStatus.OK, " DATA_FOUND", ResponseStatus.SUCCESS);
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

	
	
//	public String convertIstTimeToStr(LocalDateTime a){
//		String a = null;
//		a = a.get(i)+a.get(1).ad
//		return null;
//		
//	}
	
}
