package com.incture.cherrywork.rules;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.stereotype.Component;

@Component
public abstract class RuleService {

	// public abstract RuleOutputDto getSingleResult(RuleInputDto input) throws
	// ClientProtocolException, IOException;

	public abstract List<?> getResultList(RuleInputDto input) throws ClientProtocolException, IOException;

	protected String execute(RuleInputDto input, String rulesServiceId) throws ClientProtocolException, IOException {
		HttpContext httpContext = new BasicHttpContext();
		httpContext.setAttribute(HttpClientContext.COOKIE_STORE, new BasicCookieStore());
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = null;
		httpClient = getHTTPClient();

		String rulesRuntimeUrl = "https://bpmrulesruntimerules-uk81qreeol.ap1.hana.ondemand.com/"; // url
																									// of
																									// bpmruntimerules
																									// picked
																									// from
																									// DKSH
																									// dev
																									// tenant
		String xsrfTokenUrl = rulesRuntimeUrl + "rules-service/rest/v2/xsrf-token"; // sap
																					// cloud
																					// business
																					// rules
																					// api
																					// -
																					// v2
																					// version
																					// for
																					// x-csrf
																					// token

		String invokeUrl = rulesRuntimeUrl + "rules-service/rest/v2/workingset-rule-services"; // sap
																								// cloud
																								// business
																								// rules
																								// api
																								// -
																								// v2
																								// version
																								// for
																								// rule
																								// service
																								// (hits
																								// the
																								// specified
																								// rule)
		httpPost = new HttpPost(invokeUrl);
		httpPost.addHeader("Content-type", "application/json");

		String xsrfToken = getXSRFToken(xsrfTokenUrl, httpClient, httpContext);
		if (xsrfToken != null) {
			httpPost.addHeader("X-CSRF-Token", xsrfToken); // header
		}

		// String userpass = "P2000982477" + ":" + "3Pg13ec022";
		String auth = "Basic UDIwMDA5ODI0Nzc6M1BnMTNlYzAyMg==";
		httpPost.addHeader("Authorization", auth); // header

		String ruleInputString = input.toRuleInputString(rulesServiceId);
		StringEntity stringEntity = new StringEntity(ruleInputString);

		httpPost.setEntity(stringEntity);

		response = httpClient.execute(httpPost);

		// process your response here
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		InputStream inputStream = response.getEntity().getContent();
		byte[] data = new byte[1024];
		int length = 0;
		while ((length = inputStream.read(data)) > 0) {
			bytes.write(data, 0, length);
		}
		String respBody = new String(bytes.toByteArray(), "UTF-8");
		System.out.println(respBody);
		// clean-up sessions
		if (httpPost != null) {
			httpPost.releaseConnection();
		}
		if (response != null) {
			response.close();
		}
		if (httpClient != null) {
			httpClient.close();
		}
		return respBody;
	}

	private static String getXSRFToken(String requestURL, CloseableHttpClient client, HttpContext httpContext) {
		HttpGet httpGet = null;
		CloseableHttpResponse response = null;
		String xsrfToken = null;
		try {
			
			
			httpGet = new HttpGet(requestURL);

			String auth = "Basic UDIwMDA5ODI0Nzc6M1BnMTNlYzAyMg==";
			httpGet.addHeader("Authorization", auth);
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

	private static CloseableHttpClient getHTTPClient() {
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = clientBuilder.build();
		return httpClient;
	}
}

