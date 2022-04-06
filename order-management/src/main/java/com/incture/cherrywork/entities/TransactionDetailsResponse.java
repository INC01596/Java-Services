package com.incture.cherrywork.entities;




import com.incture.cherrywork.util.OCRConfigurationConstants;

import lombok.Data;

public @Data class TransactionDetailsResponse {
	private String requestId;
	private String fileId;
	private String transcrtionId;
	private String status = OCRConfigurationConstants.fileProcessingStatus;
	private Integer checkCount = 0;
	private String ocrEngine;
}

