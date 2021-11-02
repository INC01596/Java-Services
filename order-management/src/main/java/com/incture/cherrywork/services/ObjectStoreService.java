package com.incture.cherrywork.services;


import java.io.InputStream;
import java.util.List;



import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.entities.BlobFile;



public interface ObjectStoreService {

	
	public String uploadFile(byte[] bytes, String name, String contentType);


	
	public boolean deleteFile(String fileName);


	public InputStream getFile(String fileName);

	
	public List<BlobFile> listObjects();

	
	public boolean isBlobExist(String name);
}
