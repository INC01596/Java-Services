package com.incture.cherrywork.services;

import com.incture.cherrywork.dtos.CustomerMasterFilterDto;
import com.incture.cherrywork.dtos.Response;
import com.incture.cherrywork.dtos.ResponseDtoNew;
import com.incture.cherrywork.odata.dto.ODataCustomerDto;

public interface CustomerMasterService {
	//nischal -- Methods for all the db operations on Customer Master Table
	
	public Response saveInDb(ODataCustomerDto data);
	public ResponseDtoNew getCustomerDetailsWithFullAccess(CustomerMasterFilterDto filterData);
	public ResponseDtoNew getCustomerDetailsWithDacAccess(CustomerMasterFilterDto filterData);
}
