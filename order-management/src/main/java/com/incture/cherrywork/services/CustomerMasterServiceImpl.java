package com.incture.cherrywork.services;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.Response;
import com.incture.cherrywork.entities.CustomerMasterEntity;
import com.incture.cherrywork.odata.dto.ODataCustomerDto;
import com.incture.cherrywork.repositories.ICustomerMasterRepo;
import com.incture.cherrywork.repositories.ObjectMapperUtils;
import com.incture.cherrywork.sales.constants.ResponseStatus;

@Service("CustomerMasterServiceImpl")
@Transactional
public class CustomerMasterServiceImpl implements CustomerMasterService{
	
	@Autowired
	private ICustomerMasterRepo iCustomerMasterRepo;
	
	@Override
	public Response saveInDb(ODataCustomerDto data) {
		try{
			CustomerMasterEntity toSave = ObjectMapperUtils.map(data, CustomerMasterEntity.class);
			System.err.println("[Step 6 -Inside CustomerMasterServiceImpl][saveInDb] toSave : " + toSave);
			CustomerMasterEntity savedData = iCustomerMasterRepo.save(toSave);
			if (savedData == null) {
				return new Response(null, HttpStatus.BAD_REQUEST, "CREATION_FAILED", ResponseStatus.FAILED);
			}
			String msg = "Successfully updated in hana";
			return new Response(savedData, HttpStatus.CREATED, msg, ResponseStatus.SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
			return new Response("", HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION_FAILED + e",
					ResponseStatus.FAILED);
		}
	}
	

}
