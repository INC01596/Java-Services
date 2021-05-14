package com.incture.cherrywork.OdataS;

import com.incture.cherrywork.dto.new_workflow.OnSubmitTaskDto;
import com.incture.cherrywork.dtos.ResponseEntity;

public interface OdataService {

	public ResponseEntity updateSalesOrderInEccUsingOdata(OnSubmitTaskDto headerData, Boolean flagForRejectOrAccept);

	public ResponseEntity onSaveOrEdit(OnSubmitTaskDto headerData);

}
