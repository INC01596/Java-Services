package com.incture.cherrywork.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.cloudfoundry.identity.client.UaaContext;
import org.cloudfoundry.identity.client.UaaContextFactory;
import org.cloudfoundry.identity.client.token.GrantType;
import org.cloudfoundry.identity.client.token.TokenRequest;
import org.cloudfoundry.identity.uaa.oauth.token.CompositeAccessToken;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.sales.constants.SalesOrderOdataConstants;
import com.incture.cherrywork.util.DestinationReaderUtil;
import com.incture.cherrywork.util.HelperClass;
import com.incture.cherrywork.util.ReturnExchangeConstants;

@Service
public class SalesOrderOdataUtilService {

	// private static Logger logger =
	// LoggerFactory.getLogger(OdataUtilService.class);

	public static String callOdata(String URL, String methodType, String body, String csrfToken) throws URISyntaxException, IOException {
		// logger.debug("[OdataUtilService][callOdata] Started");

		System.err.println("[OdataUtilService][callOdata] Started");
		StringBuilder response = new StringBuilder();
		String XCSRF = null;
		String cookie = null;
		Map<String, Object> destResp = DestinationReaderUtil.getDestination("COM_OdataServices");
		System.err.println("[SalesOrderOdataUtilService][callOdata] destResp: " + destResp);

		try {
			URI xsuaaUrl = new URI(SalesOrderOdataConstants.XSUAA_URL);
			UaaContextFactory factory = UaaContextFactory.factory(xsuaaUrl).authorizePath("/oauth/authorize")
					.tokenPath("/oauth/token");
			TokenRequest tokenRequest = factory.tokenRequest();
			tokenRequest.setGrantType(GrantType.CLIENT_CREDENTIALS);
			tokenRequest.setClientId(SalesOrderOdataConstants.CLIENT_ID);
			tokenRequest.setClientSecret(SalesOrderOdataConstants.CLIENT_SECRET);
			UaaContext xsuaaContext = factory.authenticate(tokenRequest);
			String userPassword = destResp.get("User") + ":" + destResp.get("Password");
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					SalesOrderOdataConstants.ONPREMISE_PROXY_HOST, SalesOrderOdataConstants.ONPREMISE_PROXY_POST));
			if (methodType.equals("POST")) {
				CompositeAccessToken accessToken = xsuaaContext.getToken();
				URL proxyURL_GET = new URL(SalesOrderOdataConstants.BASE_URL);
				HttpURLConnection conn_GET = (HttpURLConnection) proxyURL_GET.openConnection(proxy);
				// logger.debug("[OdataUtilService][callOdata] connection GET :
				// " + conn_GET.toString());
				System.err.println("[OdataUtilService][callOdata] connection GET : " + conn_GET.toString());
				conn_GET.setRequestProperty("Proxy-Authorization", "Bearer " + accessToken);
				conn_GET.setRequestProperty("SAP-Connectivity-SCC-Location_ID", SalesOrderOdataConstants.LOCATION);
				conn_GET.setRequestProperty("Authorization",
						"BASIC " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPassword.getBytes()));
				conn_GET.setRequestProperty("x-csrf-token", "fetch");
				conn_GET.setRequestMethod("GET");
				conn_GET.connect(); // Timed out on console.

				// logger.debug("[OdataUtilService][callOdata] GET response code
				// : " + conn_GET.getResponseCode());
				System.err.println("[OdataUtilService][callOdata] GET response code : " + conn_GET.getResponseCode());
				if (conn_GET.getResponseCode() == HttpURLConnection.HTTP_OK) {
					System.out.println("in [callOdata] response code is 200 and setting x-csrf-token and cookie..");
					XCSRF = conn_GET.getHeaderField("x-csrf-token");
					cookie = conn_GET.getHeaderField("Set-Cookie").split(";", 2)[0];
				}
				conn_GET.disconnect();
			}
			// logger.debug("[OdataUtilService][callOdata] csrf, cookie : " +
			// XCSRF+" : "+cookie);
			System.err.println("[OdataUtilService][callOdata] csrf, cookie : " + XCSRF + " : " + cookie);
			CompositeAccessToken accessToken = xsuaaContext.getToken();
			URL requestURL = new URL(URL);
			// logger.debug("[OdataUtilService][callOdata] proxyURL : " +
			// requestURL.toString());

			System.err.println("[OdataUtilService][callOdata] proxyURL : " + requestURL.toString());
			HttpURLConnection conn = (HttpURLConnection) requestURL.openConnection(proxy);
			// logger.debug("[OdataUtilService][callOdata] connection : " +
			// conn.toString());
			System.err.println("[OdataUtilService][callOdata] connection : " + conn.toString());

			// Here prints 407
			// System.err.println("[OdataUtilService][callOdata] first conn
			// response code : " + conn.getResponseCode());
			conn.setRequestProperty("Proxy-Authorization", "Bearer " + accessToken);
			conn.setRequestProperty("SAP-Connectivity-SCC-Location_ID", SalesOrderOdataConstants.LOCATION);
			conn.setRequestProperty("Authorization",
					"BASIC " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPassword.getBytes()));
			if (methodType.equals("POST")) {
				System.out.println("[callodata] x-csrf-token and cookie setting again..");
				conn.setRequestProperty("x-csrf-token", XCSRF);
				conn.setRequestProperty("Cookie", cookie);
			} else
				conn.setRequestProperty("x-csrf-token", "fetch");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.connect();
			// System.err.println("[OdataUtilService][callOdata] second conn
			// response code : " + conn.getResponseCode());
			if (methodType.equals("POST")) {
				try (OutputStream os = conn.getOutputStream()) {
					os.write(body.getBytes());
					System.out.println("in [callodata] body writing byte by byte..");
				} catch (Exception e) {
					// logger.error("[OdataUtilService][PostCall] Exception in
					// OutputStream: " + e.getMessage());
					System.err.println("[OdataUtilService][PostCall] Exception in OutputStream: " + e.getMessage());
					e.printStackTrace();
				}
			}
			// \System.err.println("[OdataUtilService][callOdata] third conn
			// response code : " + conn.getResponseCode());
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 207) {
				System.out.println("[in call odata] again conn-code is 200..");
				try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
					String responseLine = null;

					System.out.println("in[call odata] reading inputstream on conn br: " + br.toString());
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine.trim());
					}
					System.err.println("response line reading inputstream on Http URL COnnection: " + responseLine);
				} catch (Exception e) {
					// logger.error("[OdataUtilService][callOdata] Exception in
					// InputStream: " + e.getMessage());
					System.err
							.println("[OdataUtilService][callOdata] Exception in InputStream in if: " + e.getMessage());
					e.printStackTrace();
				}
			} else {
				try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
					System.out.println("in [callodata] again conn-code is not 200 and br(error) is: " + br.toString());
					String responseLine = null;
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine.trim());
					}
				} catch (Exception e) {
					// logger.error("[OdataUtilService][callOdata] Exception in
					// InputStream: " + e.getMessage());
					System.err
							.println("[OdataUtilService][callOdata] Exception in InputStream else: " + e.getMessage());
					e.printStackTrace();
				}
			}
			conn.disconnect();
		} catch (Exception e) {
			// logger.error("[OdataUtilService][callOdata] Exception : " +
			// e.getMessage());
			System.err.println("[OdataUtilService][callOdata] Exception : " + e.getMessage());
			e.printStackTrace();
		}
		// logger.debug("[OdataUtilService][callOdata] Closed : " +
		// response.toString());
		System.err.println("[OdataUtilService][callOdata] Closed : " + response.toString());
		return response.toString();
	}

	public static String callOdataObd(String URL, String methodType, String body, String csrfToken)
			throws URISyntaxException, IOException {
		
		// logger.debug("[OdataUtilService][callOdata] Started");
		System.err.println("[OdataUtilService][callOdata] Started");
		StringBuilder response = new StringBuilder();
		String XCSRF = null;
		String cookie = null;
		Map<String, Object> destResp = DestinationReaderUtil.getDestination("COM_OdataServices");
		System.err.println("[SalesOrderOdataUtilService][callOdata] destResp: " + destResp);

		try {
			URI xsuaaUrl = new URI(SalesOrderOdataConstants.XSUAA_URL);
			UaaContextFactory factory = UaaContextFactory.factory(xsuaaUrl).authorizePath("/oauth/authorize")
					.tokenPath("/oauth/token");
			TokenRequest tokenRequest = factory.tokenRequest();
			tokenRequest.setGrantType(GrantType.CLIENT_CREDENTIALS);
			tokenRequest.setClientId(SalesOrderOdataConstants.CLIENT_ID);
			tokenRequest.setClientSecret(SalesOrderOdataConstants.CLIENT_SECRET);
			UaaContext xsuaaContext = factory.authenticate(tokenRequest);
			String userPassword = destResp.get("User") + ":" + destResp.get("Password");
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					SalesOrderOdataConstants.ONPREMISE_PROXY_HOST, SalesOrderOdataConstants.ONPREMISE_PROXY_POST));
			if (methodType.equals("POST")) {
				CompositeAccessToken accessToken = xsuaaContext.getToken();
				URL proxyURL_GET = new URL(SalesOrderOdataConstants.BASE_URL_OBD);
				HttpURLConnection conn_GET = (HttpURLConnection) proxyURL_GET.openConnection(proxy);
				// logger.debug("[OdataUtilService][callOdata] connection GET :
				// " + conn_GET.toString());
				System.err.println("[OdataUtilService][callOdata] connection GET : " + conn_GET.toString());
				conn_GET.setRequestProperty("Proxy-Authorization", "Bearer " + accessToken);
				conn_GET.setRequestProperty("SAP-Connectivity-SCC-Location_ID", SalesOrderOdataConstants.LOCATION);
				conn_GET.setRequestProperty("Authorization",
						"BASIC " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPassword.getBytes()));
				conn_GET.setRequestProperty("x-csrf-token", "fetch");
				conn_GET.setRequestMethod("GET");
				conn_GET.connect(); // Timed out on console.

				// logger.debug("[OdataUtilService][callOdata] GET response code
				// : " + conn_GET.getResponseCode());
				System.err.println("[OdataUtilService][callOdata] GET response code : " + conn_GET.getResponseCode());
				if (conn_GET.getResponseCode() == HttpURLConnection.HTTP_OK) {
					System.out.println("in [callOdata] response code is 200 and setting x-csrf-token and cookie..");
					XCSRF = conn_GET.getHeaderField("x-csrf-token");
					cookie = conn_GET.getHeaderField("Set-Cookie").split(";", 2)[0];
				}
				conn_GET.disconnect();
			}
			// logger.debug("[OdataUtilService][callOdata] csrf, cookie : " +
			// XCSRF+" : "+cookie);
			System.err.println("[OdataUtilService][callOdata] csrf, cookie : " + XCSRF + " : " + cookie);
			CompositeAccessToken accessToken = xsuaaContext.getToken();
			URL requestURL = new URL(URL);
			// logger.debug("[OdataUtilService][callOdata] proxyURL : " +
			// requestURL.toString());

			System.err.println("[OdataUtilService][callOdata] proxyURL : " + requestURL.toString());
			HttpURLConnection conn = (HttpURLConnection) requestURL.openConnection(proxy);
			// logger.debug("[OdataUtilService][callOdata] connection : " +
			// conn.toString());
			System.err.println("[OdataUtilService][callOdata] connection : " + conn.toString());

			// Here prints 407
			// System.err.println("[OdataUtilService][callOdata] first conn
			// response code : " + conn.getResponseCode());
			conn.setRequestProperty("Proxy-Authorization", "Bearer " + accessToken);
			conn.setRequestProperty("SAP-Connectivity-SCC-Location_ID", SalesOrderOdataConstants.LOCATION);
			conn.setRequestProperty("Authorization",
					"BASIC " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPassword.getBytes()));
			if (methodType.equals("POST")) {
				System.out.println("[callodata] x-csrf-token and cookie setting again..");
				conn.setRequestProperty("x-csrf-token", XCSRF);
				conn.setRequestProperty("Cookie", cookie);
			} else
				conn.setRequestProperty("x-csrf-token", "fetch");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.connect();
			// System.err.println("[OdataUtilService][callOdata] second conn
			// response code : " + conn.getResponseCode());
			if (methodType.equals("POST")) {
				try (OutputStream os = conn.getOutputStream()) {
					os.write(body.getBytes());
					System.out.println("in [callodata] body writing byte by byte..");
				} catch (Exception e) {
					// logger.error("[OdataUtilService][PostCall] Exception in
					// OutputStream: " + e.getMessage());
					System.err.println("[OdataUtilService][PostCall] Exception in OutputStream: " + e.getMessage());
					e.printStackTrace();
				}
			}
			// \System.err.println("[OdataUtilService][callOdata] third conn
			// response code : " + conn.getResponseCode());
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 207) {
				System.out.println("[in call odata] again conn-code is 200..");
				try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
					String responseLine = null;

					System.out.println("in[call odata] reading inputstream on conn br: " + br.toString());
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine.trim());
					}
					System.err.println("response line reading inputstream on Http URL COnnection: " + responseLine);
				} catch (Exception e) {
					// logger.error("[OdataUtilService][callOdata] Exception in
					// InputStream: " + e.getMessage());
					System.err
							.println("[OdataUtilService][callOdata] Exception in InputStream in if: " + e.getMessage());
					e.printStackTrace();
				}
			} else {
				try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
					System.out.println("in [callodata] again conn-code is not 200 and br(error) is: " + br.toString());
					String responseLine = null;
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine.trim());
					}
				} catch (Exception e) {
					// logger.error("[OdataUtilService][callOdata] Exception in
					// InputStream: " + e.getMessage());
					System.err
							.println("[OdataUtilService][callOdata] Exception in InputStream else: " + e.getMessage());
					e.printStackTrace();
				}
			}
			conn.disconnect();
		} catch (Exception e) {
			// logger.error("[OdataUtilService][callOdata] Exception : " +
			// e.getMessage());
			System.err.println("[OdataUtilService][callOdata] Exception : " + e.getMessage());
			e.printStackTrace();
		}
		// logger.debug("[OdataUtilService][callOdata] Closed : " +
		// response.toString());
		System.err.println("[OdataUtilService][callOdata] Closed : " + response.toString());
		return response.toString();
	}

	public static String callOdataSch(String URL, String methodType, String body, String csrfToken) throws URISyntaxException, IOException {
		// logger.debug("[OdataUtilService][callOdata] Started");
		System.err.println("[SalesOrderOdataUtilService][callOdata] URL" + URL);
		System.err.println("[OdataUtilService][callOdata] Started");
		StringBuilder response = new StringBuilder();
		String XCSRF = null;
		String cookie = null;
		Map<String, Object> destResp = DestinationReaderUtil.getDestination("COM_OdataServices");
		System.err.println("[SalesOrderOdataUtilService][callOdata] destResp: " + destResp);

		try {
			URI xsuaaUrl = new URI(SalesOrderOdataConstants.XSUAA_URL);
			UaaContextFactory factory = UaaContextFactory.factory(xsuaaUrl).authorizePath("/oauth/authorize")
					.tokenPath("/oauth/token");
			TokenRequest tokenRequest = factory.tokenRequest();
			tokenRequest.setGrantType(GrantType.CLIENT_CREDENTIALS);
			tokenRequest.setClientId(SalesOrderOdataConstants.CLIENT_ID);
			tokenRequest.setClientSecret(SalesOrderOdataConstants.CLIENT_SECRET);
			UaaContext xsuaaContext = factory.authenticate(tokenRequest);
			String userPassword = destResp.get("User") + ":" + destResp.get("Password");
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					SalesOrderOdataConstants.ONPREMISE_PROXY_HOST, SalesOrderOdataConstants.ONPREMISE_PROXY_POST));
			if (methodType.equals("POST")) {
				CompositeAccessToken accessToken = xsuaaContext.getToken();
				URL proxyURL_GET = new URL(SalesOrderOdataConstants.BASE_URL);
				HttpURLConnection conn_GET = (HttpURLConnection) proxyURL_GET.openConnection(proxy);
				// logger.debug("[OdataUtilService][callOdata] connection GET :
				// " + conn_GET.toString());
				System.err.println("[OdataUtilService][callOdata] connection GET : " + conn_GET.toString());
				conn_GET.setRequestProperty("Proxy-Authorization", "Bearer " + accessToken);
				conn_GET.setRequestProperty("SAP-Connectivity-SCC-Location_ID", SalesOrderOdataConstants.LOCATION);
				conn_GET.setRequestProperty("Authorization",
						"BASIC " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPassword.getBytes()));
				conn_GET.setRequestProperty("x-csrf-token", "fetch");
				conn_GET.setRequestMethod("GET");
				conn_GET.connect(); // Timed out on console.

				// logger.debug("[OdataUtilService][callOdata] GET response code
				// : " + conn_GET.getResponseCode());
				System.err.println("[OdataUtilService][callOdata] GET response code : " + conn_GET.getResponseCode());
				if (conn_GET.getResponseCode() == HttpURLConnection.HTTP_OK) {
					System.out.println("in [callOdata] response code is 200 and setting x-csrf-token and cookie..");
					XCSRF = conn_GET.getHeaderField("x-csrf-token");
					cookie = conn_GET.getHeaderField("Set-Cookie").split(";", 2)[0];
				}
				conn_GET.disconnect();
			}
			// logger.debug("[OdataUtilService][callOdata] csrf, cookie : " +
			// XCSRF+" : "+cookie);
			System.err.println("[OdataUtilService][callOdata] csrf, cookie : " + XCSRF + " : " + cookie);
			CompositeAccessToken accessToken = xsuaaContext.getToken();
			URL requestURL = new URL(URL);
			// logger.debug("[OdataUtilService][callOdata] proxyURL : " +
			// requestURL.toString());

			System.err.println("[OdataUtilService][callOdata] proxyURL : " + requestURL.toString());
			HttpURLConnection conn = (HttpURLConnection) requestURL.openConnection(proxy);
			// logger.debug("[OdataUtilService][callOdata] connection : " +
			// conn.toString());
			System.err.println("[OdataUtilService][callOdata] connection : " + conn.toString());

			// Here prints 407
			// System.err.println("[OdataUtilService][callOdata] first conn
			// response code : " + conn.getResponseCode());
			conn.setRequestProperty("Proxy-Authorization", "Bearer " + accessToken);
			conn.setRequestProperty("SAP-Connectivity-SCC-Location_ID", SalesOrderOdataConstants.LOCATION);
			conn.setRequestProperty("Authorization",
					"BASIC " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPassword.getBytes()));
			if (methodType.equals("POST")) {
				System.out.println("[callodata] x-csrf-token and cookie setting again..");
				conn.setRequestProperty("x-csrf-token", XCSRF);
				conn.setRequestProperty("Cookie", cookie);
			} else
				conn.setRequestProperty("x-csrf-token", "fetch");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.connect();
			// System.err.println("[OdataUtilService][callOdata] second conn
			// response code : " + conn.getResponseCode());
			if (methodType.equals("POST")) {
				try (OutputStream os = conn.getOutputStream()) {
					os.write(body.getBytes());
					System.out.println("in [callodata] body writing byte by byte..");
				} catch (Exception e) {
					// logger.error("[OdataUtilService][PostCall] Exception in
					// OutputStream: " + e.getMessage());
					System.err.println("[OdataUtilService][PostCall] Exception in OutputStream: " + e.getMessage());
					e.printStackTrace();
				}
			}
			// \System.err.println("[OdataUtilService][callOdata] third conn
			// response code : " + conn.getResponseCode());
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 207) {
				System.out.println("[in call odata] again conn-code is 200..");
				try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
					String responseLine = null;

					System.out.println("in[call odata] reading inputstream on conn br: " + br.toString());
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine.trim());
					}
					System.err.println("response line reading inputstream on Http URL COnnection: " + responseLine);
				} catch (Exception e) {
					// logger.error("[OdataUtilService][callOdata] Exception in
					// InputStream: " + e.getMessage());
					System.err
							.println("[OdataUtilService][callOdata] Exception in InputStream in if: " + e.getMessage());
					e.printStackTrace();
				}
			} else {
				try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
					System.out.println("in [callodata] again conn-code is not 200 and br(error) is: " + br.toString());
					String responseLine = null;
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine.trim());
					}
				} catch (Exception e) {
					// logger.error("[OdataUtilService][callOdata] Exception in
					// InputStream: " + e.getMessage());
					System.err
							.println("[OdataUtilService][callOdata] Exception in InputStream else: " + e.getMessage());
					e.printStackTrace();
				}
			}
			conn.disconnect();
		} catch (Exception e) {
			// logger.error("[OdataUtilService][callOdata] Exception : " +
			// e.getMessage());
			System.err.println("[OdataUtilService][callOdata] Exception : " + e.getMessage());
			e.printStackTrace();
		}
		// logger.debug("[OdataUtilService][callOdata] Closed : " +
		// response.toString());
		System.err.println("[OdataUtilService][callOdata] Closed : " + response.toString());
		return response.toString();
	}

	public static String callOdataReturnExchange(String URL, String methodType, String body, String csrfToken,
			String batchGuId) throws URISyntaxException, IOException {
		// logger.debug("[OdataUtilService][callOdata] Started");
		System.err.println("[OdataUtilService][callOdata] Started");
		StringBuilder response = new StringBuilder();
		String XCSRF = null;
		String cookie = null;
		Map<String, Object> destResp = DestinationReaderUtil.getDestination("COM_OdataServices");
		System.err.println("[SalesOrderOdataUtilService][callOdata] destResp: " + destResp);

		try {
			URI xsuaaUrl = new URI(SalesOrderOdataConstants.XSUAA_URL);
			UaaContextFactory factory = UaaContextFactory.factory(xsuaaUrl).authorizePath("/oauth/authorize")
					.tokenPath("/oauth/token");
			TokenRequest tokenRequest = factory.tokenRequest();
			tokenRequest.setGrantType(GrantType.CLIENT_CREDENTIALS);
			tokenRequest.setClientId(SalesOrderOdataConstants.CLIENT_ID);
			tokenRequest.setClientSecret(SalesOrderOdataConstants.CLIENT_SECRET);
			UaaContext xsuaaContext = factory.authenticate(tokenRequest);
			String userPassword = destResp.get("User") + ":" + destResp.get("Password");
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					SalesOrderOdataConstants.ONPREMISE_PROXY_HOST, SalesOrderOdataConstants.ONPREMISE_PROXY_POST));
			if (methodType.equals("POST")) {
				CompositeAccessToken accessToken = xsuaaContext.getToken();
				URL proxyURL_GET = new URL(SalesOrderOdataConstants.BASE_URL_RETURN);
				HttpURLConnection conn_GET = (HttpURLConnection) proxyURL_GET.openConnection(proxy);
				// logger.debug("[OdataUtilService][callOdata] connection GET :
				// " + conn_GET.toString());
				System.err.println("[OdataUtilService][callOdata] connection GET : " + conn_GET.toString());
				conn_GET.setRequestProperty("Proxy-Authorization", "Bearer " + accessToken);
				conn_GET.setRequestProperty("SAP-Connectivity-SCC-Location_ID", SalesOrderOdataConstants.LOCATION);
				conn_GET.setRequestProperty("Authorization",
						"BASIC " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPassword.getBytes()));
				conn_GET.setRequestProperty("x-csrf-token", "fetch");
				conn_GET.setRequestMethod("GET");
				conn_GET.connect(); // Timed out on console.

				// logger.debug("[OdataUtilService][callOdata] GET response code
				// : " + conn_GET.getResponseCode());
				System.err.println("[OdataUtilService][callOdata] GET response code : " + conn_GET.getResponseCode());
				if (conn_GET.getResponseCode() == HttpURLConnection.HTTP_OK) {
					System.out.println("in [callOdata] response code is 200 and setting x-csrf-token and cookie..");
					XCSRF = conn_GET.getHeaderField("x-csrf-token");
					cookie = conn_GET.getHeaderField("Set-Cookie").split(";", 2)[0];
				}
				conn_GET.disconnect();
			}
			// logger.debug("[OdataUtilService][callOdata] csrf, cookie : " +
			// XCSRF+" : "+cookie);
			System.err.println("[OdataUtilService][callOdata] csrf, cookie : " + XCSRF + " : " + cookie);
			CompositeAccessToken accessToken = xsuaaContext.getToken();
			URL requestURL = new URL(URL);
			// logger.debug("[OdataUtilService][callOdata] proxyURL : " +
			// requestURL.toString());

			System.err.println("[OdataUtilService][callOdata] proxyURL : " + requestURL.toString());
			HttpURLConnection conn = (HttpURLConnection) requestURL.openConnection(proxy);
			// logger.debug("[OdataUtilService][callOdata] connection : " +
			// conn.toString());
			System.err.println("[OdataUtilService][callOdata] connection : " + conn.toString());

			// Here prints 407
			// System.err.println("[OdataUtilService][callOdata] first conn
			// response code : " + conn.getResponseCode());
			conn.setRequestProperty("Proxy-Authorization", "Bearer " + accessToken);
			conn.setRequestProperty("SAP-Connectivity-SCC-Location_ID", SalesOrderOdataConstants.LOCATION);
			conn.setRequestProperty("Authorization",
					"BASIC " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPassword.getBytes()));
			if (methodType.equals("POST")) {
				System.out.println("[callodata] x-csrf-token and cookie setting again..");
				conn.setRequestProperty("x-csrf-token", XCSRF);
				conn.setRequestProperty("Cookie", cookie);
			} else
				conn.setRequestProperty("x-csrf-token", "fetch");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.connect();
			// System.err.println("[OdataUtilService][callOdata] second conn
			// response code : " + conn.getResponseCode());
			if (methodType.equals("POST")) {
				try (OutputStream os = conn.getOutputStream()) {
					os.write(body.getBytes());
					System.out.println("in [callodata] body writing byte by byte..");
				} catch (Exception e) {
					// logger.error("[OdataUtilService][PostCall] Exception in
					// OutputStream: " + e.getMessage());
					System.err.println("[OdataUtilService][PostCall] Exception in OutputStream: " + e.getMessage());
					e.printStackTrace();
				}
			}
			// \System.err.println("[OdataUtilService][callOdata] third conn
			// response code : " + conn.getResponseCode());
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 207) {
				System.out.println("[in call odata] again conn-code is 200..");
				try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
					String responseLine = null;

					System.out.println("in[call odata] reading inputstream on conn br: " + br.toString());
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine.trim());
					}
					System.err.println("response line reading inputstream on Http URL COnnection: " + responseLine);
				} catch (Exception e) {
					// logger.error("[OdataUtilService][callOdata] Exception in
					// InputStream: " + e.getMessage());
					System.err
							.println("[OdataUtilService][callOdata] Exception in InputStream in if: " + e.getMessage());
					e.printStackTrace();
				}
			} else {
				try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
					System.out.println("in [callodata] again conn-code is not 200 and br(error) is: " + br.toString());
					String responseLine = null;
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine.trim());
					}
				} catch (Exception e) {
					// logger.error("[OdataUtilService][callOdata] Exception in
					// InputStream: " + e.getMessage());
					System.err
							.println("[OdataUtilService][callOdata] Exception in InputStream else: " + e.getMessage());
					e.printStackTrace();
				}
			}
			conn.disconnect();
		} catch (Exception e) {
			// logger.error("[OdataUtilService][callOdata] Exception : " +
			// e.getMessage());
			System.err.println("[OdataUtilService][callOdata] Exception : " + e.getMessage());
			e.printStackTrace();
		}
		// logger.debug("[OdataUtilService][callOdata] Closed : " +
		// response.toString());
		System.err.println("[OdataUtilService][callOdata] Closed : " + response.toString());
		return response.toString();
	}

	public static String roBatchPost(String batchRequest, String batchGuid, String endPoint) throws IOException {

		String responseFromECC = null;

		try {

			String proxyHost = "connectivityproxy.internal.cf.eu10.hana.ondemand.com";
			// System.getenv("HC_OP_HTTP_PROXY_HOST");
			System.err.println("proxyHost-- " + proxyHost);

			int proxyPort = 20003;
			// Integer.parseInt(System.getenv("HC_OP_HTTP_PROXY_PORT"));
			System.err.println("proxyPort-- " + proxyPort);

			// JSONObject jsonObj = new
			// JSONObject(System.getenv("VCAP_SERVICES"));

			// System.err.println("116 - jsonObj =" + jsonObj);

			// JSONArray jsonArr = jsonObj.getJSONArray("<service name, not the
			// instance name>"); JSONObject credentials =
			// jsonArr.getJSONObject(0).getJSONObject("credentials");

			Map<String, Object> map = DestinationReaderUtil
					.getDestination(ReturnExchangeConstants.COM_ODATA_DESTINATION_NAME);

			String jwToken = DestinationReaderUtil.getConectivityProxy();

			String url = map.get("URL") + endPoint;
			System.err.println("url-- " + url);
			CredentialsProvider credsProvider = new BasicCredentialsProvider();

			credsProvider.setCredentials(new AuthScope(proxyHost, proxyPort),
					new UsernamePasswordCredentials((String) map.get("User"), (String) map.get("Password")));
			HttpClientBuilder clientBuilder = HttpClientBuilder.create();

			clientBuilder.setProxy(new HttpHost(proxyHost, proxyPort))
					.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy())
					.setDefaultCredentialsProvider(credsProvider).disableCookieManagement();

			HttpClient client = clientBuilder.build();

			System.err.println("client " + client);

			// Hard-Coded salesDocument for Testing
			String tokenUrl = "/sap/opu/odata/sap/ZCOM_RETURNS_MANAGEMENT_SRV/orderHeaderSet('0060000244')?$expand=OrderHdrToOrderItem&$format=json";
			// http://kuldb11d.dksh.com:8005/sap/opu/odata/sap/ZDKSH_CC_RETURNS_MANAGEMENT_SRV/orderHeaderSet(salesDocument='5700000629')?$expand=OrderHdrToOrderItem&$format=json

			map.forEach((k, v) -> System.err.println((k + ":" + v)));

			// Get SharePoint Access Token
			// Header[] headers = getAccessToken((String) map.get("URL") +
			// tokenUrl, (String) map.get("User"),
			// (String) map.get("Password"), client, tenantctx, proxyHost,
			// proxyPort,
			// (String) map.get("sap-client"));

			Header[] headers = getAccessToken((String) map.get("URL") + tokenUrl, (String) map.get("User"),
					(String) map.get("Password"), client, proxyHost, proxyPort, (String) map.get("sap-client"),
					jwToken);

			/*
			 * Header[] headers = getAccessToken(map.get("URL") + tokenUrl,
			 * map.get("User"), map.get("Password"), client, tenantctx,
			 * map.get("sap-client"));
			 */

			System.err.println("> Header : " + headers.toString());

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
					System.err.println("xsrf-token failed to fetch ");
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
					/*
					 * StringEntity jsonEntity = new StringEntity(batchRequest);
					 * jsonEntity.setContentType("application/json");
					 * httpPost.setEntity(jsonEntity);
					 */

					StringEntity entity = new StringEntity(batchRequest, "UTF-8");
					entity.setContentEncoding("UTF-8");
					entity.setContentType("application/json");
					httpPost.setEntity(entity);
					// httpPost.setHeader("content-type", "application/json");

					System.err.println("jsonEntity : " + entity);
				}

				HttpResponse response = client.execute(httpPost);

				// HttpResponse response = client.execute(httpPost);

				responseFromECC = HelperClass.getDataFromStream(response.getEntity().getContent());

				System.err.println("> responseFromECC : " + responseFromECC);

			} else {
				System.err.println("> Failed to execute due to no Headers.");

			}

		} catch (Exception e) {
			System.err.println("failed due to Exception" + e);

		}
		return responseFromECC;

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

}
