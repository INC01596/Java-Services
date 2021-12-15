package com.incture.cherrywork.services;


import java.io.File;


public interface AbbyOcrService {

	public String uploadDocToTransOld(File fileToUpload, String skillId);
	public String getTranDocumentDetails(String transactionId);

}
