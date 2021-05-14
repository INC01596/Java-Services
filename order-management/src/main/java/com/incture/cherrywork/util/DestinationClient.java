package com.incture.cherrywork.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.incture.cherrywork.WConstants.Constants;



public class DestinationClient {

	// private AuthenticationHeaderProvider headerProvider;
	//
	// public DestinationClient() throws NamingException {
	// Context ctx = new InitialContext();
	// headerProvider = (AuthenticationHeaderProvider)
	// ctx.lookup("java:comp/env/myAuthHeaderProvider");
	// }

	public String invoke(String url, String jsonString, String username, String password) throws IOException {

		// AuthenticationHeader header =
		// headerProvider.getAppToAppSSOHeader(url);
		HttpClient client = HttpClientBuilder.create().build();

		// Generating Xcsrf Token
		// ConnectClient connectClient = generateXcsrfToken(url, header, client,
		// username, password);
		ConnectClient connectClient = generateXcsrfToken(url, client, username, password);

		HttpPatch patch = new HttpPatch(url);

		// patch.addHeader(header.getName(), header.getValue());
		patch.addHeader("Content-Type", "application/json");
		patch.addHeader("x-csrf-token", connectClient.getToken());

		// SETTING COOKIES
		for (String cookie : connectClient.getCookies()) {
			String tmp = cookie.split(";", 2)[0];
			patch.addHeader("Cookie", tmp);
		}

		String dataFromStream = null;
		if (!HelperClass.checkString(jsonString)) {
			StringEntity entity = new StringEntity(jsonString);
			entity.setContentType("application/json");
			patch.setEntity(entity);

			HttpResponse response = client.execute(patch);
			if (response.getStatusLine().getStatusCode() != 204) {
				dataFromStream = getDataFromStream(response.getEntity().getContent());
				return dataFromStream;

			} else {
				return Constants.TASK_COMPLETED;
			}

		} else {
			return "Payload is invalid";
		}
	}

	// private ConnectClient generateXcsrfToken(String url, AuthenticationHeader
	// authHeader, HttpClient client,
	// String username, String password) throws IOException {
	//
	// HttpGet httpGet = new HttpGet(url);
	// httpGet.setHeader("x-csrf-token", "Fetch");
	// String xCsrfToken = null;
	// List<String> cookies = new ArrayList<>();
	// String encodeUserPass = username + ":" + password;
	// String auth = "Basic " +
	// DatatypeConverter.printBase64Binary(encodeUserPass.getBytes());
	// httpGet.setHeader("Authorization", auth);
	//
	// if (authHeader != null) {
	// httpGet.addHeader(authHeader.getName(), authHeader.getValue());
	// }
	// HttpResponse httpResponse = client.execute(httpGet);
	// Header[] headers = httpResponse.getAllHeaders();
	// for (Header head : headers) {
	// if (head.getName().equalsIgnoreCase("X-CSRF-Token")) {
	// xCsrfToken = head.getValue();
	// }
	// if (head.getName().equalsIgnoreCase("Set-Cookie")) {
	// cookies.add(head.getValue());
	// }
	// }
	//
	// return new ConnectClient(xCsrfToken, cookies);
	// }
	private ConnectClient generateXcsrfToken(String url, HttpClient client, String username, String password)
			throws IOException {

		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("x-csrf-token", "Fetch");
		String xCsrfToken = null;
		List<String> cookies = new ArrayList<>();
		String encodeUserPass = username + ":" + password;
		String auth = "Basic " + DatatypeConverter.printBase64Binary(encodeUserPass.getBytes());
		httpGet.setHeader("Authorization", auth);

		// if (authHeader != null) {
		// httpGet.addHeader(authHeader.getName(), authHeader.getValue());
		// }
		HttpResponse httpResponse = client.execute(httpGet);
		Header[] headers = httpResponse.getAllHeaders();
		for (Header head : headers) {
			if (head.getName().equalsIgnoreCase("X-CSRF-Token")) {
				xCsrfToken = head.getValue();
			}
			if (head.getName().equalsIgnoreCase("Set-Cookie")) {
				cookies.add(head.getValue());
			}
		}

		return new ConnectClient(xCsrfToken, cookies);
	}

	private String getDataFromStream(InputStream stream) throws IOException {
		StringBuilder dataBuffer = new StringBuilder();
		BufferedReader inStream = new BufferedReader(new InputStreamReader(stream));
		String data = "";

		while ((data = inStream.readLine()) != null) {
			dataBuffer.append(data);
		}
		inStream.close();
		return dataBuffer.toString();
	}

}
