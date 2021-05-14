package com.incture.cherrywork.util;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import com.google.gson.Gson;
import com.incture.cherrywork.WConstants.StatusConstants;
import com.incture.cherrywork.tasksubmitdto.OdataBatchOnSubmitPayload;

import com.sap.cloud.account.TenantContext;

public class ODataBatchUtil {
	private static final Logger log = LoggerFactory.getLogger(ODataBatchUtil.class);

	@SuppressWarnings("unused")
	private static String generateUUID() {

		UUID idOne = UUID.randomUUID();
		String idOne_Str = String.valueOf(idOne);
		return idOne_Str;
	}

	// public static String BULK_INSERT(List<ReturnOrderRequestPojo>
	// requestList, String url, String tag) throws IOException{

	public static String BULK_INSERT(String requestList, String url, String tag) throws IOException {

		// generate uniqueId for a batch boundary
		// String batchGuid = generateUUID(); // System generated

		String batchGuid = "zmybatch";
		log.info("batchGuid", batchGuid);

		// generate uniqueId for each item to be inserted
		// String changeSetId = generateUUID();
		String changeSetId = "zmychangeset";

		log.info("changeSetId", changeSetId);

		// Begin of: Prepare Bulk Request Format for SharePoint
		// Bulk-Insert-Query ----------------
		String batchContents = "";
		try {

			// Start: changeset to insert data ----------
			String batchCnt_Insert = "";

			batchCnt_Insert = batchCnt_Insert + "--changeset_" + changeSetId + "\n" + "Content-Type: application/http"
					+ "\n" + "Content-Transfer-Encoding: binary" + "\n" + "" + "\n" + "POST " + tag + " HTTP/1.1" + "\n"
					+ "Content-Type: application/json" + "\n" + "Accept: application/json" + "\n\n" + "{" + "\n"
					+ "\"d\":" + new Gson().toJson(requestList) + "\n" // new
					// Gson().toJson(data)
					+ "}" + "\n";

			// END: changeset to insert data ----------

			batchCnt_Insert = batchCnt_Insert + "--changeset_" + changeSetId + "--\n";

			System.err.println("batchCnt_Insert" + batchCnt_Insert);

			// create batch for creating items
			batchContents = "--batch_" + batchGuid + "\n" + "Content-Type: multipart/mixed; boundary=changeset_"
					+ changeSetId + "\n" + "" + "\n" + batchCnt_Insert;

			batchContents = batchContents + "--batch_" + batchGuid + "--";

			System.err.println("> batchContents :: " + batchContents);

		} catch (Exception e) {
			return "failed" + e;
		}
		// End of: Prepare Bulk Request Format for SharePoint Bulk-Insert-Query
		// ----------------

		// Call POST method to server
		String response = roBatchPost(batchContents, batchGuid, url);
		System.err.println("response --- " + response);

		return response;
	}

	private static String roBatchPost(String batchRequest, String batchGuid, String endPoint) throws IOException {
		try {
			String proxyHost = System.getenv("HC_OP_HTTP_PROXY_HOST");
			System.err.println("proxyHost-- " + proxyHost);

			int proxyPort = Integer.parseInt(System.getenv("HC_OP_HTTP_PROXY_PORT"));
			System.err.println("proxyPort-- " + proxyPort);

			HttpClient client = HttpClientBuilder.create().build();

			Map<String, Object> map = DestinationReaderUtil
					.getDestination(StatusConstants.DKSH_ODATA_DESTINATION_NAME);

			String url = map.get("URL") + endPoint;
			System.err.println("url-- " + url);

			// Hard-Coded salesDocument for Testing
			String tokenUrl = "/sap/opu/odata/sap/ZDKSH_CC_RETURNS_MANAGEMENT_SRV/orderHeaderSet(salesDocument='5700000629')?$expand=OrderHdrToOrderItem&$format=json";
			// http://kuldb11d.dksh.com:8005/sap/opu/odata/sap/ZDKSH_CC_RETURNS_MANAGEMENT_SRV/orderHeaderSet(salesDocument='5700000629')?$expand=OrderHdrToOrderItem&$format=json

			SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
			clientHttpRequestFactory.setProxy(proxy);

			System.err.println("> Proxy --- " + proxy);

			TenantContext tenantctx = getTenantInformation();

			map.forEach((k, v) -> System.err.println((k + ":" + v)));

			// Get SharePoint Access Token
			Header[] headers = getAccessToken((String) map.get("URL") + tokenUrl, (String) map.get("User"),
					(String) map.get("Password"), client, tenantctx, proxyHost, proxyPort,
					(String) map.get("sap-client"));

			/*
			 * Header[] headers = getAccessToken(map.get("URL") + tokenUrl,
			 * map.get("User"), map.get("Password"), client, tenantctx,
			 * map.get("sap-client"));
			 */

			System.err.println("> Header : " + headers);

			if (headers.length != 0) {

				HttpPost httpPost = new HttpPost(url);
				if (tenantctx != null) {
					httpPost.addHeader("SAP-Connectivity-ConsumerAccount", tenantctx.getTenant().getAccount().getId());
				}
				String token = null;
				List<String> cookies = new ArrayList<>();
				for (Header header : headers) {

					if (header.getName().equalsIgnoreCase("x-csrf-token")) {
						token = header.getValue();
						System.err.println("token --- " + token);
					}

					if (header.getName().equalsIgnoreCase("set-cookie")) {
						cookies.add(header.getValue());
					}

				}

				if (token == null) {
					return "xsrf-token failed to fetch ";
				}

				String auth = HelperClass.encodeUsernameAndPassword((String) map.get("User"),
						(String) map.get("Password"));
				httpPost.addHeader("Authorization", auth);
				System.err.println("Token for update in ECC : " + token);

				if (token != null) {
					httpPost.addHeader("X-CSRF-Token", token);
				}

				httpPost.addHeader("sap-client", (String) map.get("sap-client"));
				httpPost.addHeader("Content-Type", "multipart/mixed;boundary=batch_" + batchGuid);

				if (!cookies.isEmpty()) {
					for (String cookie : cookies) {
						String tmp = cookie.split(";", 2)[0];
						httpPost.addHeader("Cookie", tmp);
					}
				}

				if (!HelperClass.checkString(batchRequest)) {
					StringEntity jsonEntity = new StringEntity(batchRequest);
					jsonEntity.setContentType("application/json");
					httpPost.setEntity(jsonEntity);
					System.err.println("jsonEntity : " + jsonEntity);
				}

				HttpResponse response = client.execute(new HttpHost(proxyHost, proxyPort), httpPost);

				// HttpResponse response = client.execute(httpPost);

				String responseFromECC = HelperClass.getDataFromStream(response.getEntity().getContent());

				System.err.println("> responseFromECC : " + responseFromECC);

				return responseFromECC;
			} else {
				System.err.println("> Failed to execute due to no Headers.");
				return "Failed to execute due to no xscrf token found";
			}

		} catch (Exception e) {

			return "failed due to " + e;
		}

	}

	public static TenantContext getTenantInformation() {
		TenantContext tenantctx = null;
		try {
			Context ctx = new InitialContext();
			tenantctx = (TenantContext) ctx.lookup("java:comp/env/TenantContext");
			System.err.println("com.incture.utils " + tenantctx.toString());
		} catch (NamingException e1) {
			System.err.println(e1.getMessage());
		}
		return tenantctx;
	}

	private static Header[] getAccessToken(String url, String username, String password, HttpClient client,
			TenantContext tenantctx, String proxyHost, int proxyPort, String sapClient)
			throws ClientProtocolException, IOException {

		/*
		 * private static Header[] getAccessToken(String url, String username,
		 * String password, HttpClient client, TenantContext tenantctx, String
		 * sapClient) throws ClientProtocolException, IOException {
		 */

		HttpGet httpGet = new HttpGet(url);

		// Encoding username and password
		String auth = HelperClass.encodeUsernameAndPassword(username, password);
		httpGet.addHeader("Authorization", auth);
		httpGet.addHeader("X-CSRF-Token", "Fetch");
		httpGet.addHeader("Content-Type", "application/json");
		httpGet.addHeader("sap-client", sapClient);

		if (tenantctx != null) {
			httpGet.addHeader("SAP-Connectivity-ConsumerAccount", tenantctx.getTenant().getAccount().getId());
		}

		HttpResponse response = client.execute(new HttpHost(proxyHost, proxyPort), httpGet);

		// HttpResponse response = client.execute(httpGet);

		return response.getAllHeaders();

	}

	private static String roBatchPostOnSubmit(String batchRequest, String batchGuid, String endPoint)
			throws IOException {
		try {
			String proxyHost = System.getenv("HC_OP_HTTP_PROXY_HOST");
			System.err.println("proxyHost-- " + proxyHost);

			int proxyPort = Integer.parseInt(System.getenv("HC_OP_HTTP_PROXY_PORT"));
			System.err.println("proxyPort-- " + proxyPort);

			HttpClient client = HttpClientBuilder.create().build();

			Map<String, Object> map = DestinationReaderUtil
					.getDestination(StatusConstants.DKSH_ODATA_DESTINATION_NAME);

			String url = map.get("URL") + endPoint;
			System.err.println("url-- " + url);

			// Hard-Coded salesDocument for Testing
			String tokenUrl = "/sap/opu/odata/sap/ZCC_SALESORDER_DATA_SRV/soheaderSet?$format=json";
			// http://kuldb11d.dksh.com:8005/sap/opu/odata/sap/ZDKSH_CC_RETURNS_MANAGEMENT_SRV/orderHeaderSet(salesDocument='5700000629')?$expand=OrderHdrToOrderItem&$format=json

			SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
			clientHttpRequestFactory.setProxy(proxy);

			System.err.println("> Proxy --- " + proxy);

			TenantContext tenantctx = getTenantInformation();

			map.forEach((k, v) -> System.err.println((k + ":" + v)));

			// Get SharePoint Access Token
			Header[] headers = getAccessToken((String) map.get("URL") + tokenUrl, (String) map.get("User"),
					(String) map.get("Password"), client, tenantctx, proxyHost, proxyPort,
					(String) map.get("sap-client"));

			/*
			 * Header[] headers = getAccessToken(map.get("URL") + tokenUrl,
			 * map.get("User"), map.get("Password"), client, tenantctx,
			 * map.get("sap-client"));
			 */

			System.err.println("> Header : " + headers);

			if (headers.length != 0) {

				HttpPost httpPost = new HttpPost(url);
				if (tenantctx != null) {
					httpPost.addHeader("SAP-Connectivity-ConsumerAccount", tenantctx.getTenant().getAccount().getId());
				}
				String token = null;
				List<String> cookies = new ArrayList<>();
				for (Header header : headers) {

					if (header.getName().equalsIgnoreCase("x-csrf-token")) {
						token = header.getValue();
						System.err.println("token --- " + token);
					}

					if (header.getName().equalsIgnoreCase("set-cookie")) {
						cookies.add(header.getValue());
					}

				}

				if (token == null) {
					return "xsrf-token failed to fetch ";
				}

				String auth = HelperClass.encodeUsernameAndPassword((String) map.get("User"),
						(String) map.get("Password"));
				httpPost.addHeader("Authorization", auth);
				System.err.println("Token for update in ECC : " + token);

				if (token != null) {
					httpPost.addHeader("X-CSRF-Token", token);
				}

				httpPost.addHeader("sap-client", (String) map.get("sap-client"));
				httpPost.addHeader("Content-Type", "multipart/mixed;boundary=batch_" + batchGuid);

				if (!cookies.isEmpty()) {
					for (String cookie : cookies) {
						String tmp = cookie.split(";", 2)[0];
						httpPost.addHeader("Cookie", tmp);
					}
				}

				if (!HelperClass.checkString(batchRequest)) {
					StringEntity jsonEntity = new StringEntity(batchRequest);
					jsonEntity.setContentType("application/json");
					httpPost.setEntity(jsonEntity);
					System.err.println("jsonEntity : " + jsonEntity);
				}

				HttpResponse response = client.execute(new HttpHost(proxyHost, proxyPort), httpPost);

				// HttpResponse response = client.execute(httpPost);

				String responseFromECC = HelperClass.getDataFromStream(response.getEntity().getContent());

				System.err.println("> responseFromECC : " + responseFromECC);

				return responseFromECC;
			} else {
				System.err.println("> Failed to execute due to no Headers.");
				return "Failed to execute due to no xscrf token found";
			}

		} catch (Exception e) {

			return "failed due to " + e;
		}

	}

	public static String BULK_INSERT_ON_SUBMIT_DATA(
			List<OdataBatchOnSubmitPayload> odataBarequestList, String url,
			String tag) throws IOException {

		String batchGuid = "zmybatch";
		log.info("batchGuid", batchGuid);

		String changeSetId = "zmychangeset";

		log.info("changeSetId", changeSetId);

		String batchContents = "";
		try {

			// Start: changeset to insert data ----------
			String batchCnt_Insert = "";

			for (OdataBatchOnSubmitPayload data : odataBarequestList) {
				batchCnt_Insert = batchCnt_Insert + "--changeset_" + changeSetId + "\n"
						+ "Content-Type: application/http" + "\n" + "Content-Transfer-Encoding: binary" + "\n" + ""
						+ "\n" + "POST " + tag + " HTTP/1.1" + "\n" + "Content-Type: application/json" + "\n"
						+ "Accept: application/json" + "\n\n" + "{" + "\n" + "\"d\":" + new Gson().toJson(data) + "\n" // new
																														// Gson().toJson(data)
						+ "}" + "\n";

			}
			// END: changeset to insert data ----------

			batchCnt_Insert = batchCnt_Insert + "--changeset_" + changeSetId + "--\n";

			System.err.println("batchCnt_Insert" + batchCnt_Insert);

			// create batch for creating items
			batchContents = "--batch_" + batchGuid + "\n" + "Content-Type: multipart/mixed; boundary=changeset_"
					+ changeSetId + "\n" + "" + "\n" + batchCnt_Insert;

			batchContents = batchContents + "--batch_" + batchGuid + "--";

			System.err.println("> batchContents :: " + batchContents);

			String response = roBatchPostOnSubmit(batchContents, batchGuid, url);
			System.err.println("response --- " + response);

			return response;

		} catch (Exception e) {
			return "failed" + e;
		}

	}

}
