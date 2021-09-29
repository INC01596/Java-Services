package com.incture.cherrywork.worflow.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.incture.cherrywork.exceptions.ExecutionFault;
import com.incture.cherrywork.sales.constants.DkshBlockConstant;


@Repository
public interface BlockTypeDeterminationDao {

	public Map<DkshBlockConstant, Object> blockTypeFilterBasedOnSoId(String salesOrderHeaderNo) throws ExecutionFault;
}

