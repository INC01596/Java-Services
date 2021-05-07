package com.incture.cherrywork.workflow;

import com.incture.cherrywork.dtos.ResponseEntity;

public interface BlockTypeDeterminationService {

	public ResponseEntity blockTypeFilterBasedOnSoId(String salesOrderHeaderNo);
}
