package com.incture.cherrywork.workflow.services;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.incture.cherrywork.WConstants.Constants;
import com.incture.cherrywork.dtos.DeletePayloadDto;
import com.incture.cherrywork.util.DestinationReaderUtil;

@Service
public class DeletionOfWorflowTaskId {

	private static final String XCSRFTOKEN = "X-CSRF-Token";

	private static final String TRIGGERFAILUER = "Trigger FAILURE no poper dataset and level ";

	private static final String CONTENTTYPE = "Content-Type";

	private static final String BASIC = "Basic";
	private static final String AUTHORIZATION = "Authorization";

	public String findAllTheRunningAndErrorenous() throws IOException, URISyntaxException {

		DeletePayloadDto deletePayloadDto = new DeletePayloadDto();
		int countSuccess = 0;

		int failedCount = 0;
		String url = "aproval";
		List<String> id = httpClientGet(url);
		List<DeletePayloadDto> deleteparloadList = new ArrayList<DeletePayloadDto>();

		String payloads = null;

		try {
			StringBuffer payload = new StringBuffer();
			for (int i = 0; i < id.size(); i++) {

				String ides = id.get(i);

				ides = ides.substring(1, ides.length() - 1);

				payload.append("{\"id\":\"" + ides + "\",\"deleted\": true },");

			}

			System.err.println("payload buffer" + payload);

			payloads = "" + payload;

			payloads = payloads.substring(0, payloads.length() - 1);

			payloads = "[" + payloads + "]";

			/*
			 * String Succes = deleteAlltheTask(""+payload); String entity =
			 * deleteparloadList.toString();
			 * 
			 */

		} catch (Exception e) {
			failedCount++;
		}

		return payloads;
	}

	public List<String> httpClientGet(String url) throws IOException, URISyntaxException {

		List<String> id = new ArrayList<String>();

		String xcsrfToken = null;
		List<String> cookies = null;
		HttpContext httpContext = new BasicHttpContext();
		httpContext.setAttribute(HttpClientContext.COOKIE_STORE, new BasicCookieStore());
		HttpPost httpPost = null;
		HttpGet httpGet = null;
		HttpResponse responseClient = null;
		HttpClient httpClient = null;
		httpClient = getHTTPClient();

		// String url =
		// "https://bpmworkflowruntimecbbe88bff-uk81qreeol.ap1.hana.ondemand.com/workflow-service/rest/v1/workflow-instances";
		Map<String, Object> map = DestinationReaderUtil.getDestination(Constants.WORKFLOW_TRIGGER);

		String url1 = "https://bpmworkflowruntimecbbe88bff-xlgtvarz5i.ap1.hana.ondemand.com/workflow-service/rest";
		String trimmed = url1.substring(8);

		URIBuilder builder = new URIBuilder();
		String interger = "1000";
		builder.setScheme("https").setHost(trimmed).setPath("/v1/workflow-instances").setParameter("status",
				"RUNNING,ERRONEOUS");

		URI uri = builder.build();

		httpGet = new HttpGet(uri);

		String auth = map.get("User") + ":" + map.get("Password");
		String encoding = DatatypeConverter.printBase64Binary(auth.getBytes());
		httpGet.addHeader(AUTHORIZATION, "Basic " + encoding);
		responseClient = httpClient.execute(httpGet);

		// process response here
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		InputStream inputStream = responseClient.getEntity().getContent();
		byte[] data = new byte[1024];
		int length = 0;
		while ((length = inputStream.read(data)) > 0) {
			bytes.write(data, 0, length);
		}
		String respBody = new String(bytes.toByteArray(), "UTF-8");
		System.out.println(respBody);

		JsonArray jsonArray = new JsonParser().parse(respBody).getAsJsonArray();

		for (int i = 0; i < jsonArray.size(); i++) {
			JsonElement entity = (jsonArray.get(i));

			JsonObject jsonobject = entity.getAsJsonObject();

			id.add("" + jsonobject.get("id"));
		}

		// clean-up sessions
		if (httpGet != null) {
			httpGet.releaseConnection();
		}

		return id;
	}

	private static HttpClient getHTTPClient() {

		int timeout = 5 * 60; // seconds (5 minutes)
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
		HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

		return httpClient;
	}

	private static String getXSRFToken(String requestURL, HttpClient client, HttpContext httpContext)
			throws URISyntaxException {
		HttpGet httpGet = null;
		HttpResponse response = null;
		String xsrfToken = null;
		try {

			Map<String, Object> map = DestinationReaderUtil.getDestination("rulestokenWorflowTrigger");
			System.err.println("map for Rules_V2 : " + map.toString());

			httpGet = new HttpGet((String) map.get("URL"));
			// setting
			String auth = map.get("User") + ":" + map.get("Password");
			String encoding = DatatypeConverter.printBase64Binary(auth.getBytes());
			httpGet.addHeader(AUTHORIZATION, "Basic " + encoding);

			httpGet.addHeader("X-CSRF-Token", "Fetch");

			response = client.execute(httpGet);
			Header xsrfTokenheader = response.getFirstHeader("X-CSRF-Token");

			System.err.println("xsrfTokenheader: " + xsrfTokenheader);
			if (xsrfTokenheader != null) {
				xsrfToken = xsrfTokenheader.getValue();
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpGet != null) {
				httpGet.releaseConnection();
			}

		}
		return xsrfToken;
	}

	public String deleteAlltheTask(String payload) throws URISyntaxException, ClientProtocolException, IOException {

		HttpContext httpContext = new BasicHttpContext();
		httpContext.setAttribute(HttpClientContext.COOKIE_STORE, new BasicCookieStore());

		HttpResponse responseClient = null;
		HttpClient httpClients = null;
		httpClients = getHTTPClients();

		CloseableHttpClient httpClient = getHTTPClients();
		// String url =
		// "https://bpmworkflowruntimecbbe88bff-uk81qreeol.ap1.hana.ondemand.com/workflow-service/rest/v1/workflow-instances";
		Map<String, Object> map = DestinationReaderUtil.getDestination(Constants.WORKFLOW_TRIGGER);

		String url1 = "https://bpmworkflowruntimecbbe88bff-xlgtvarz5i.ap1.hana.ondemand.com/workflow-service/rest";
		String trimmed = url1.substring(8);

		URIBuilder builder = new URIBuilder();

		builder.setScheme("https").setHost(trimmed).setPath("/v1/task-instances");

		String xsrfToken = getXSRFTokens(url1, httpClient, httpContext);
		System.err.println("xsrfToken " + xsrfToken);

		URI uri = builder.build();

		HttpPatch httpdelete = new HttpPatch(uri);

		httpdelete.addHeader("X-CSRF-Token", xsrfToken);

		String auth = "P2001273932" + ":" + "Mohit@7250";
		String encoding = DatatypeConverter.printBase64Binary(auth.getBytes());
		httpdelete.addHeader(AUTHORIZATION, "Basic " + encoding);

		payload = payload.substring(0, payload.length() - 1);

		payload = "[" + payload + "]";

		StringEntity workflowPayload = new StringEntity(payload);

		System.err.println("buffer2 " + payload);
		httpdelete.setEntity(workflowPayload);
		responseClient = httpClient.execute(httpdelete);

		// process response here
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		InputStream inputStream = responseClient.getEntity().getContent();
		byte[] data = new byte[1024];
		int length = 0;
		while ((length = inputStream.read(data)) > 0) {
			bytes.write(data, 0, length);
		}
		String respBody = new String(bytes.toByteArray(), "UTF-8");
		System.out.println(respBody);

		// clean-up sessions
		if (httpdelete != null) {
			httpdelete.releaseConnection();
		}

		return "Success";

	}

	private static String getXSRFTokens(String requestURL, CloseableHttpClient client, HttpContext httpContext)
			throws URISyntaxException {
		HttpGet httpGet = null;
		CloseableHttpResponse response = null;
		String xsrfToken = null;
		try {

			Map<String, Object> map = DestinationReaderUtil.getDestination("rulestokenWorflowTrigger");
			System.err.println("map for Rules_V2 : " + map.toString());

			httpGet = new HttpGet((String) map.get("URL"));
			// setting
			String auth = "" + ":" + "";
			String encoding = DatatypeConverter.printBase64Binary(auth.getBytes());
			httpGet.addHeader(AUTHORIZATION, "Basic " + encoding);

			httpGet.addHeader("X-CSRF-Token", "Fetch");
			response = client.execute(httpGet);
			Header xsrfTokenheader = response.getFirstHeader("X-CSRF-Token");
			if (xsrfTokenheader != null) {
				xsrfToken = xsrfTokenheader.getValue();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpGet != null) {
				httpGet.releaseConnection();
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return xsrfToken;
	}

	private static CloseableHttpClient getHTTPClients() {
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = clientBuilder.build();
		return httpClient;
	}

}

