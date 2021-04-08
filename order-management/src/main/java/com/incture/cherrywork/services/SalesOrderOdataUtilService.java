package com.incture.cherrywork.services;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;

import org.cloudfoundry.identity.client.UaaContext;
import org.cloudfoundry.identity.client.UaaContextFactory;
import org.cloudfoundry.identity.client.token.GrantType;
import org.cloudfoundry.identity.client.token.TokenRequest;
import org.cloudfoundry.identity.uaa.oauth.token.CompositeAccessToken;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



import com.incture.cherrywork.sales.constants.SalesOrderOdataConstants;

@Service
public class SalesOrderOdataUtilService {

	//private static Logger logger = LoggerFactory.getLogger(OdataUtilService.class);

	public static String callOdata(String URL, String methodType, String body, String csrfToken) {
		//logger.debug("[OdataUtilService][callOdata] Started");
		System.err.println("[OdataUtilService][callOdata] Started");
		StringBuilder response = new StringBuilder();
		String XCSRF = null;
		String cookie = null;
		try {
			URI xsuaaUrl = new URI(SalesOrderOdataConstants.XSUAA_URL);
			UaaContextFactory factory = UaaContextFactory.factory(xsuaaUrl).authorizePath("/oauth/authorize").tokenPath("/oauth/token");
			TokenRequest tokenRequest = factory.tokenRequest();
			tokenRequest.setGrantType(GrantType.CLIENT_CREDENTIALS);
			tokenRequest.setClientId(SalesOrderOdataConstants.CLIENT_ID);
			tokenRequest.setClientSecret(SalesOrderOdataConstants.CLIENT_SECRET);
			UaaContext xsuaaContext = factory.authenticate(tokenRequest);
			String userPassword = SalesOrderOdataConstants.USER_ID + ":" + SalesOrderOdataConstants.PASSWORD;
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(SalesOrderOdataConstants.ONPREMISE_PROXY_HOST, SalesOrderOdataConstants.ONPREMISE_PROXY_POST));
			if (methodType.equals("POST")) {
				CompositeAccessToken accessToken = xsuaaContext.getToken();
				URL proxyURL_GET = new URL(SalesOrderOdataConstants.BASE_URL);
				HttpURLConnection conn_GET = (HttpURLConnection) proxyURL_GET.openConnection(proxy);
				//logger.debug("[OdataUtilService][callOdata] connection GET : " + conn_GET.toString());
				System.err.println("[OdataUtilService][callOdata] connection GET : " + conn_GET.toString());
				conn_GET.setRequestProperty("Proxy-Authorization", "Bearer " + accessToken);
				conn_GET.setRequestProperty("SAP-Connectivity-SCC-Location_ID", SalesOrderOdataConstants.LOCATION);
				conn_GET.setRequestProperty("Authorization",
						"BASIC " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPassword.getBytes()));
				conn_GET.setRequestProperty("x-csrf-token", "fetch");
				conn_GET.setRequestMethod("GET");
				conn_GET.connect();   //Timed out on console.
				
				
				//logger.debug("[OdataUtilService][callOdata] GET response code : " + conn_GET.getResponseCode());
				System.err.println("[OdataUtilService][callOdata] GET response code : " + conn_GET.getResponseCode());
				if (conn_GET.getResponseCode() == HttpURLConnection.HTTP_OK) {
					System.out.println("in [callOdata] response code is 200 and setting x-csrf-token and cookie..");
					XCSRF = conn_GET.getHeaderField("x-csrf-token");
					cookie = conn_GET.getHeaderField("Set-Cookie").split(";", 2)[0];
				}
				conn_GET.disconnect();
			}
			//logger.debug("[OdataUtilService][callOdata] csrf, cookie : " + XCSRF+" : "+cookie);
			System.err.println("[OdataUtilService][callOdata] csrf, cookie : " + XCSRF+" : "+cookie);
			CompositeAccessToken accessToken = xsuaaContext.getToken();
			URL requestURL = new URL(URL);
			//logger.debug("[OdataUtilService][callOdata] proxyURL : " + requestURL.toString());
			
			System.err.println("[OdataUtilService][callOdata] proxyURL : " + requestURL.toString());
			HttpURLConnection conn = (HttpURLConnection) requestURL.openConnection(proxy);
			//logger.debug("[OdataUtilService][callOdata] connection : " + conn.toString());
			System.err.println("[OdataUtilService][callOdata] connection : " + conn.toString());
			
			
			//Here prints 407
			//System.err.println("[OdataUtilService][callOdata] first conn response code : " + conn.getResponseCode());
			conn.setRequestProperty("Proxy-Authorization", "Bearer " + accessToken);
			conn.setRequestProperty("SAP-Connectivity-SCC-Location_ID", SalesOrderOdataConstants.LOCATION);
			conn.setRequestProperty("Authorization",
					"BASIC " + javax.xml.bind.DatatypeConverter.printBase64Binary(userPassword.getBytes()));
			if (methodType.equals("POST")){
				System.out.println("[callodata] x-csrf-token and cookie setting again..");
				conn.setRequestProperty("x-csrf-token", XCSRF);
				conn.setRequestProperty("Cookie", cookie);
			}
			else
				conn.setRequestProperty("x-csrf-token", "fetch");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.connect();
			//System.err.println("[OdataUtilService][callOdata] second conn response code : " + conn.getResponseCode());
			if (methodType.equals("POST")) {
				try (OutputStream os = conn.getOutputStream()) {
					os.write(body.getBytes());
					System.out.println("in [callodata] body writing byte by byte..");
				} catch (Exception e) {
					//logger.error("[OdataUtilService][PostCall] Exception in OutputStream: " + e.getMessage());
					System.err.println("[OdataUtilService][PostCall] Exception in OutputStream: " + e.getMessage());
					e.printStackTrace();
				}
			}
			//\System.err.println("[OdataUtilService][callOdata] third conn response code : " + conn.getResponseCode());
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 207) {
				System.out.println("[in call odata] again conn-code is 200..");
				try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
					String responseLine = null;
					
					System.out.println("in[call odata] reading inputstream on conn br: "+br.toString());
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine.trim());
					}
					System.err.println("response line reading inputstream on Http URL COnnection: "+responseLine);
				} catch (Exception e) {
					//logger.error("[OdataUtilService][callOdata] Exception in InputStream: " + e.getMessage());
					System.err.println("[OdataUtilService][callOdata] Exception in InputStream in if: " + e.getMessage());
					e.printStackTrace();
				}
			} else {
				try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
					System.out.println("in [callodata] again conn-code is not 200 and br(error) is: "+br.toString());
					String responseLine = null;
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine.trim());
					}
				} catch (Exception e) {
					//logger.error("[OdataUtilService][callOdata] Exception in InputStream: " + e.getMessage());
					System.err.println("[OdataUtilService][callOdata] Exception in InputStream else: " + e.getMessage());
					e.printStackTrace();
				}
			}
			conn.disconnect();
		} catch (Exception e) {
			//logger.error("[OdataUtilService][callOdata] Exception : " + e.getMessage());
			System.err.println("[OdataUtilService][callOdata] Exception : " + e.getMessage());
			e.printStackTrace();
		}
		//logger.debug("[OdataUtilService][callOdata] Closed : " + response.toString());
		System.err.println("[OdataUtilService][callOdata] Closed : " + response.toString());
		return response.toString();
	}
}
