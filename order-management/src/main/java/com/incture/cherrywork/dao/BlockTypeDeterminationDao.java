package com.incture.cherrywork.dao;

import java.util.Map;

import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.util.DkshBlockConstant;



public interface BlockTypeDeterminationDao {

	public Map<DkshBlockConstant, Object> blockTypeFilterBasedOnSoId(String salesOrderHeaderNo) throws ExecutionFault;
}
