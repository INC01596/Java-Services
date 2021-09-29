package com.incture.cherrywork.services;



import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.ApplicationMasterDto;
import com.incture.cherrywork.dtos.ResponseDto;
import com.incture.cherrywork.repositories.ApplicationMasterRepo;
import com.incture.cherrywork.util.ServicesUtil;



@Service
public class ApplicationMasterServiceImpl implements ApplicationMasterService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ApplicationMasterRepo applicationMasterRepo;

	@Override
	public ResponseDto getAllApplications() {
		logger.info(" [ApplicationMasterServiceImpl]|[getAllApplications]  Execution start");
		ResponseDto responseDto = new ResponseDto();
		try {
			responseDto.setStatus(Boolean.TRUE);
			responseDto.setStatusCode(200);
			List<Object[]> list = applicationMasterRepo.listAllApplications();
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			if (!ServicesUtil.isEmpty(list)) {
				list.forEach(obj -> {
					if (obj[0] != null) {
						Map<String, String> map = new LinkedHashMap<String, String>();
						map.put("application", obj[0].toString());
						map.put("applicationDesc", obj[1].toString());
						data.add(map);

					}

				});
			}
			responseDto.setData(data);
			responseDto.setMessage(" Applications Fetched Successfully");

		} catch (Exception e) {
			logger.info(" [ApplicationMasterServiceImpl]|[getAllApplications]  Exception Occured." + e.getMessage());
			responseDto.setStatus(Boolean.FALSE);
			responseDto.setStatusCode(500);
			responseDto.setMessage(e.getMessage());

		}
		logger.info(" [ApplicationMasterServiceImpl]|[getAllApplications]  Execution End");
		return responseDto;
	}

	@Override
	public ResponseDto getEntitiesByApplication(String application) {
		logger.info(" [ApplicationMasterServiceImpl]|[getEntitiesByApplication]  Execution start");
		ResponseDto responseDto = new ResponseDto();
		try {
			responseDto.setStatus(Boolean.TRUE);
			responseDto.setStatusCode(200);
			List<Object[]> list = applicationMasterRepo.listEntitiesByApplication(application);
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			if (!ServicesUtil.isEmpty(list)) {
				list.forEach(obj -> {
					if (obj[0] != null) {
						Map<String, String> map = new LinkedHashMap<String, String>();
						map.put("entity", obj[0].toString());
						map.put("entityDesc", obj[1].toString());
						data.add(map);

					}

				});
			}
			responseDto.setData(data);
		} catch (Exception e) {
			logger.info(
					" [ApplicationMasterServiceImpl]|[getEntitiesByApplication]  Exception Occured  " + e.getMessage());
			responseDto.setStatus(Boolean.FALSE);
			responseDto.setStatusCode(500);
			responseDto.setMessage(e.getMessage());

		}
		logger.info(" [ApplicationMasterServiceImpl]|[getEntitiesByApplication]  Execution End");
		return responseDto;
	}

	@Override
	public ResponseDto getProcessByEntityAndApp(String application, String entity) {
		logger.info(" [ApplicationMasterServiceImpl]|[getProcessByEntityAndApp]  Execution start");
		ResponseDto responseDto = new ResponseDto();
		try {
			responseDto.setStatus(Boolean.TRUE);
			responseDto.setStatusCode(200);
			List<Object[]> list = applicationMasterRepo.listProcessByAppAndEntity(application, entity);
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			if (!ServicesUtil.isEmpty(list)) {
				list.forEach(obj -> {
					if (obj[0] != null) {
						Map<String, String> map = new LinkedHashMap<String, String>();
						map.put("process", obj[0].toString());
						map.put("processDesc", obj[1].toString());
						data.add(map);

					}

				});
			}
			responseDto.setData(data);
		} catch (Exception e) {
			logger.info(
					" [ApplicationMasterServiceImpl]|[getProcessByEntityAndApp]  Exception  Occured " + e.getMessage());
			responseDto.setStatus(Boolean.FALSE);
			responseDto.setStatusCode(500);
			responseDto.setMessage(e.getMessage());

		}
		logger.info(" [ApplicationMasterServiceImpl]|[getVariables]  Execution End");
		return responseDto;
	}

	@Override
	public ResponseEntity<Object> createApp(ApplicationMasterDto dto) {
		try{
		applicationMasterRepo.save(applicationMasterRepo.importDto(dto));
		return ResponseEntity.ok().body(applicationMasterRepo.importDto(dto));
		}catch(Exception e)
		{
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}

}
