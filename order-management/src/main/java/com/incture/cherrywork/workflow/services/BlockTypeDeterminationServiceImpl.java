package com.incture.cherrywork.workflow.services;


import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.sales.constants.DkshBlockConstant;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.worflow.dao.BlockTypeDeterminationDao;


@Service
@Transactional
public class BlockTypeDeterminationServiceImpl implements BlockTypeDeterminationService {

	@Autowired
	private BlockTypeDeterminationDao blockTypeDeterminationRepo;

	@Override
	public ResponseEntity blockTypeFilterBasedOnSoId(String salesOrderHeaderNo) {
		try {
			Map<DkshBlockConstant, Object> map = blockTypeDeterminationRepo
					.blockTypeFilterBasedOnSoId(salesOrderHeaderNo);
			System.err.println("BTD map at service : " + map);
			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.create();
			

			String idb = gson.toJson(map);
			
			System.err.println("idb ="+idb);
			
			if (map == null || map.isEmpty()) {
				return new ResponseEntity(salesOrderHeaderNo, HttpStatus.NO_CONTENT,
						"Data with your Sales Header Number is not available with ID = " + salesOrderHeaderNo,
						ResponseStatus.FAILED);
			} else {
				return new ResponseEntity(map, HttpStatus.ACCEPTED, "DATA_FOUND", ResponseStatus.SUCCESS);
			}
		} catch (Exception e) {
			//HelperClass.getLogger(this.getClass().getName()).info(e + " on " + e.getStackTrace()[1]);
			return new ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}

}
