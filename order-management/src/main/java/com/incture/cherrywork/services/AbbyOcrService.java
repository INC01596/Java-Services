package com.incture.cherrywork.services;


import java.io.File;

import org.springframework.http.ResponseEntity;


public interface AbbyOcrService {

	public ResponseEntity<Object> uploadDocToTrans(File file);

	public ResponseEntity<Object> uploadDocToTrans(byte[] array);
	
}
