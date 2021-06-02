package com.incture.cherrywork.workflow.services;

import com.incture.cherrywork.dtos.ResponseEntity;

public interface BlockTypeDeterminationService {

	public ResponseEntity blockTypeFilterBasedOnSoId(String salesOrderHeaderNo);
}
