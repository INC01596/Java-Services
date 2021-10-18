package com.incture.cherrywork.services;

import com.incture.cherrywork.dtos.CustomerMasterFilterDto;
import com.incture.cherrywork.dtos.Response;
import com.incture.cherrywork.odata.dto.ODataCustomerDto;

public interface CustomerMasterService {
	//nischal -- Methods for all the db operations on Customer Master Table
	
	public Response saveInDb(ODataCustomerDto data);
	public Response getCustomerDetailsWithFullAccess(CustomerMasterFilterDto filterData);
	public Response getCustomerDetailsWithDacAccess(CustomerMasterFilterDto filterData);
}
