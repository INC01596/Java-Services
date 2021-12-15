package com.incture.cherrywork.util;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.springframework.web.multipart.MultipartFile;

public class AbbyServiceUtil {
	public static File moveAndStoreFile(MultipartFile file, String name,String path) throws IOException {
	    String url = path+name;
	    File fileToSave = new File(url);
	    fileToSave.createNewFile();
	    FileOutputStream fos = new FileOutputStream(fileToSave); 
	    fos.write(file.getBytes());
	    fos.close();
	    return fileToSave;
	  }
	public  static File multipartToFile(MultipartFile file) throws IOException 
	{
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	// to write content on a file.
	public static File writeByte(String base64String, String fileNamePath) throws IOException {
		File file = new File(fileNamePath);
		byte[] bytes = base64String.getBytes();
		byte[] decodedBytes = Base64.getDecoder().decode(new String(bytes).getBytes("UTF-8"));
		OutputStream os = new FileOutputStream(file);
		os.write(decodedBytes);
		os.close();
		return file;
	}

	// delay execution code.

	/*
	 * public void delay() { ScheduledExecutorService executorService =
	 * Executors.newSingleThreadScheduledExecutor(); // here DoSome is the class
	 * name and meth in the function to be delayed to execute. Object qwe;
	 * executorService.schedule((Runnable) new DoSome(), 30, TimeUnit.SECONDS);
	 * // after the delayed job done we have shut the ScheduledExecutorService
	 * down. executorService.shutdown(); }
	 */

	public static final String SAVE_DIRECTORY = "D:\\emailreader";

	public static String getDateByEpoc(Long epoc) {
		Date date = new Date(epoc);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String formatted = format.format(date);
		return formatted;
	}

	public static Long getEpocTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss:sss");
		Date dateToBeFormatted = new Date();
		formatter.format(dateToBeFormatted);
		Long epoc = dateToBeFormatted.getTime();
		return epoc;

	}

	public static String getDataFromStream(InputStream stream) throws IOException {
		StringBuilder dataBuffer = new StringBuilder();
		BufferedReader inStream = new BufferedReader(new InputStreamReader(stream));
		String data = "";
		while ((data = inStream.readLine()) != null) {
			dataBuffer.append(data);
		}

		inStream.close();
		System.out.println("dataBuffer:"+dataBuffer);
		return dataBuffer.toString();
	}

	public static String encodeUsernameAndPassword(String username, String password) {
		String encodeUsernamePassword = username + ":" + password;
		return "Basic " + DatatypeConverter.printBase64Binary(encodeUsernamePassword.getBytes());
	}

	public static String appendLeadingCharacters(char c, int len, String val) {
		if (!isEmpty(val)) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < len - val.length(); i++) {
				sb.append(c);
			}
			sb.append(val);
			return sb.toString();
		}
		return null;

	}

	public static boolean compare(Object value1, Object value2) {
		if (value1 == null && value2 == null)
			return true;
		else if ((value1 == null && value2 != null) || (value1 != null && value2 == null))
			return false;
		else if (value1.equals(value2))
			return true;
		else
			return false;
	}

	public static boolean isEmpty(Object obj) {
		if (obj == null || obj.equals("NULL"))
			return true;
		else if (obj.toString().equals(""))
			return true;
		return false;
	}

	public static boolean isEmpty(Object[] objs) {
		if (objs == null || objs.length == 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static String extractStr(Object o) {
		return o == null ? "" : o.toString();
	}

	public static boolean isEmpty(Collection<?> o) {
		if (o == null || o.isEmpty()) {
			return true;
		}
		return false;
	}

}

