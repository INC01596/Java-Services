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
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
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
			String proxyHost, int proxyPort, String sapClient, String token)
			throws ClientProtocolException, IOException {

		HttpGet httpGet = new HttpGet(url);

		String userpass = username + ":" + password;

		httpGet.setHeader("Proxy-Authorization", "Bearer " + token);
		httpGet.setHeader(HttpHeaders.AUTHORIZATION,
				"Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes()));
		// Encoding username and password
		httpGet.addHeader("X-CSRF-Token", "Fetch");
		httpGet.addHeader("Content-Type", "application/json");
		httpGet.addHeader("sap-client", sapClient);
		httpGet.addHeader("SAP-Connectivity-SCC-Location_ID", "incture");

		HttpResponse response = client.execute(httpGet);
		// HttpResponse response = client.execute( httpGet);

		System.err.println("313 response " + response);

		// HttpResponse response = client.execute(httpGet);

		return response.getAllHeaders();

	}

	private static String roBatchPostOnSubmit(String batchRequest, String batchGuid, String endPoint)
			throws IOException {
		try {
			// String proxyHost = System.getenv("HC_OP_HTTP_PROXY_HOST");
			String proxyHost = "connectivityproxy.internal.cf.eu10.hana.ondemand.com";
			System.err.println("proxyHost-- " + proxyHost);

			// int proxyPort =
			// Integer.parseInt(System.getenv("HC_OP_HTTP_PROXY_PORT"));
			int proxyPort = 20003;
			System.err.println("proxyPort-- " + proxyPort);

			// JSONObject jsonObj = new
			// JSONObject(System.getenv("VCAP_SERVICES"));

			// System.err.println("116 - jsonObj =" + jsonObj);

			// HttpClient client = HttpClientBuilder.create().build();

			Map<String, Object> map = DestinationReaderUtil.getDestination(ComConstants.COM_ODATA_DESTINATION_NAME);

			String jwToken = DestinationReaderUtil.getConectivityProxy();

			CredentialsProvider credsProvider = new BasicCredentialsProvider();

			credsProvider.setCredentials(new AuthScope(proxyHost, proxyPort),
					new UsernamePasswordCredentials((String) map.get("User"), (String) map.get("Password")));
			HttpClientBuilder clientBuilder = HttpClientBuilder.create();

			clientBuilder.setProxy(new HttpHost(proxyHost, proxyPort))
					.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy())
					.setDefaultCredentialsProvider(credsProvider).disableCookieManagement();

			HttpClient client = clientBuilder.build();
			System.err.println("client " + client);

			String url = map.get("URL") + endPoint;
			System.err.println("url-- " + url);

			// Hard-Coded salesDocument for Testing
			String tokenUrl = "/sap/opu/odata/sap/ZCOM_SALESORDER_DATA_SRV/soheaderSet?$format=json";
			// http://kuldb11d.dksh.com:8005/sap/opu/odata/sap/ZDKSH_CC_RETURNS_MANAGEMENT_SRV/orderHeaderSet(salesDocument='5700000629')?$expand=OrderHdrToOrderItem&$format=json

			map.forEach((k, v) -> System.err.println((k + ":" + v)));

			Header[] headers = getAccessToken((String) map.get("URL") + tokenUrl, (String) map.get("User"),
					(String) map.get("Password"), client, proxyHost, proxyPort, (String) map.get("sap-client"),
					jwToken);
			/*
			 * Header[] headers = getAccessToken(map.get("URL") + tokenUrl,
			 * map.get("User"), map.get("Password"), client, tenantctx,
			 * map.get("sap-client"));
			 */

			System.err.println("> Header : " + headers);

			if (headers.length != 0) {

				HttpPost httpPost = new HttpPost(url);
				// if (tenantctx != null) {
				// httpPost.addHeader("SAP-Connectivity-ConsumerAccount",
				// tenantctx.getTenant().getAccount().getId());
				// }
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
				httpPost.setHeader("Proxy-Authorization", "Bearer " + jwToken);
				httpPost.addHeader("SAP-Connectivity-SCC-Location_ID", "incture");
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

				HttpResponse response = client.execute(httpPost);

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

	public static String BULK_INSERT_ON_SUBMIT_DATA(List<OdataBatchOnSubmitPayload> requestList, String url, String tag)
			throws IOException { // --

		String batchGuid = "zmybatch";
		log.info("batchGuid", batchGuid);

		String changeSetId = "zmychangeset";

		log.info("changeSetId", changeSetId);

		String batchContents = "";
		try {

			// Start: changeset to insert data ----------
			String batchCnt_Insert = "";

			for (OdataBatchOnSubmitPayload data : requestList) {

				batchCnt_Insert = batchCnt_Insert + "--changeset_" + changeSetId + "\n"
						+ "Content-Type: application/http" + "\n" + "Content-Transfer-Encoding: binary" + "\n" + ""
						+ "\n" + "POST " + tag + " HTTP/1.1" + "\n" + "Content-Type: application/json" + "\n"
						+ "Accept: application/json" + "\n\n" + "{" + "\n" + "\"d\":" + new Gson().toJson(data) + "\n" // new
																														// Gson().toJson(data)
						+ "}" + "\n";

			}
			// END: changeset to insert data ----------

			batchCnt_Insert = batchCnt_Insert + "--changeset_" + changeSetId + "--\n";

			System.err.println("batchCnt_Insert: " + batchCnt_Insert);

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
