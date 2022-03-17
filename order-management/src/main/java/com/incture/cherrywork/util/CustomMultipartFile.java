package com.incture.cherrywork.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


public class CustomMultipartFile implements MultipartFile {

private final byte[] fileContent;

private String fileName;

private String contentType;

private File file;

private String destPath = "src/main/resources";

private FileOutputStream fileOutputStream;

public  CustomMultipartFile(byte[] fileData, String name) {
    this.fileContent = fileData;
    this.fileName = name;
    file = new File(destPath + fileName);

}

@Override
public void transferTo(File dest) throws IOException, IllegalStateException {
    fileOutputStream = new FileOutputStream(dest);
    fileOutputStream.write(fileContent);
}

public void clearOutStreams() throws IOException {
if (null != fileOutputStream) {
        fileOutputStream.flush();
        fileOutputStream.close();
        file.deleteOnExit();
    }
}

@Override
public byte[] getBytes() throws IOException {
    return fileContent;
}



@Override
public String getName() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getOriginalFilename() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getContentType() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean isEmpty() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public long getSize() {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public InputStream getInputStream() throws IOException {
	// TODO Auto-generated method stub
	return null;
}
}