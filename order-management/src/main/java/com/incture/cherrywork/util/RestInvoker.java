package com.incture.cherrywork.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RestInvoker {

	private static final Logger logger = LoggerFactory.getLogger(RestInvoker.class);

	private final String baseUrl= "https://p1015-iflmap.hcisbp.ap1.hana.ondemand.com/";
	//private final String baseUrl= "https://inccpidev.it-cpi001-rt.cfapps.eu10.hana.ondemand.com/";
	private final String name="P2001073068";
	//private final String name="P000286";
	private final String password="Nomore@123";
	//private final String password="April@2021";

	public RestInvoker() {

		
//		  this.baseUrl = DelfiWorkflowConstants.HCI_DESTINATION_URL;
//		  this.name=DelfiWorkflowConstants.HCI_DESTINATION_USER;
//		  this.password=DelfiWorkflowConstants.HCI_DESTINATION_PWD;
		 

//		DestinationConfiguration destConfiguration = DestinationReaderUtil
//				.getDest(DelfiWorkflowConstants.HCI_DESTINATION_NAME);
//
//		this.baseUrl = destConfiguration.getProperty(DelfiWorkflowConstants.DESTINATION_URL);
//		this.name = destConfiguration.getProperty(DelfiWorkflowConstants.DESTINATION_USER);
//		this.password = destConfiguration.getProperty(DelfiWorkflowConstants.DESTINATION_PWD);

	}

	private URLConnection setNameAndPassword(URL url) throws IOException {
		URLConnection urlConnection = url.openConnection();
		String authString = name + ":" + password;
		String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
		urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
		return urlConnection;
	}

//	public String getDataFromServer(String path) {
//		StringBuilder data = new StringBuilder();
//		try {
//			URL url = new URL(baseUrl + path);
//			URLConnection urlConnection = setNameAndPassword(url);
//			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//			String line;
//			while ((line = reader.readLine()) != null) {
//				data.append(line);
//			}
//			reader.close();
//			return data.toString();
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

	public String postDataToServer(String path, String data) {
		URL url;
		StringBuilder sb = new StringBuilder();
		try {
			
			String target = baseUrl + path;
			url = new URL(target);
			
			logger.error("GETTING DATA FROM SERVER");
			HttpURLConnection urlConnection = (HttpURLConnection) setNameAndPassword(url);
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("Content-Type", "application/json");
			OutputStream os = urlConnection.getOutputStream();
			logger.error("GETTING DATA FROM SERVER1S");
			os.write(data.getBytes());
			os.flush();
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
		} catch (IOException e) {
			return e.getMessage();
		}
		
		logger.error(sb.toString());
		return sb.toString();
	}

}
