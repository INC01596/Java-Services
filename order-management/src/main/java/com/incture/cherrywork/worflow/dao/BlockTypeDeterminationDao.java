package com.incture.cherrywork.worflow.dao;

import java.util.Map;

import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.sales.constants.DkshBlockConstant;



public interface BlockTypeDeterminationDao {

	public Map<DkshBlockConstant, Object> blockTypeFilterBasedOnSoId(String salesOrderHeaderNo) throws ExecutionFault;
}

