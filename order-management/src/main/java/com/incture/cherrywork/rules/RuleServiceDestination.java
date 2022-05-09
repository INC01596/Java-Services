package com.incture.cherrywork.rules;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.incture.cherrywork.util.DestinationReaderUtil;

@Service
@Transactional
public abstract class RuleServiceDestination {

	public static final String XCSRF_TOKEN = "X-CSRF-Token";

	public static final String DESTINATION_RULES_TOKEN = "rulestoken";

	public static final String DESTINATION_RULES = "Rules_V2";

	public static final String USER = "User";

	public static final String PASSWORD = "Password";

	public static final String URL = "URL";

	public static final String CONTENT_TYPE = "Content-Type";

	public static final String AUTHORIZATION = "Authorization";

	public static final String FETCH = "Fetch";

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public abstract List<?> getResultListRuleService(RuleInputDto input)
			throws ClientProtocolException, IOException, URISyntaxException;

	protected String execute(RuleInputDto input, String rulesServiceId)
			throws ClientProtocolException, IOException, URISyntaxException {

		HttpContext httpContext = new BasicHttpContext();
		httpContext.setAttribute(HttpClientContext.COOKIE_STORE, new BasicCookieStore());
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = null;
		httpClient = getHTTPClient();

		// Map<String, String> map =
		// DestinationReaderUtil.getDestination(DESTINATION_RULES)

		String jwToken = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApiDecisionSetWorkflow();
		System.err.println("map for rulesToken : " + jwToken);
		httpPost = new HttpPost(RuleConstants.RULE_BASE_URL);
		httpPost.addHeader(CONTENT_TYPE, "application/json");

		httpPost.addHeader(AUTHORIZATION, "Bearer " + jwToken); // header

		String ruleInputString = input.toRuleInputString(rulesServiceId);
		StringEntity stringEntity = new StringEntity(ruleInputString);

		httpPost.setEntity(stringEntity);

		response = httpClient.execute(httpPost);
		System.err.println("response : " + response);

		// process your response here
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		InputStream inputStream = response.getEntity().getContent();
		byte[] data = new byte[1024];
		int length = 0;
		while ((length = inputStream.read(data)) > 0) {
			bytes.write(data, 0, length);
		}
		String respBody = new String(bytes.toByteArray(), StandardCharsets.UTF_8);
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
		System.err.println("respBody: " + respBody);
		return respBody;
	}

	public String callWorkRulesRuleEngine(RuleInputDto input, String tableId) throws URISyntaxException {
		System.err.println("callWorkRulesRuleEngine started..");
		RestTemplate restTemplate = new RestTemplate();
		URI uri = null;
		HttpHeaders requestHeaders;
		HttpEntity<String> entity;

		uri = new URI(RuleConstants.WORKRULE_API_BASE_URL + "/invoke/rules");

		requestHeaders = new HttpHeaders();
		/*
		 * requestHeaders.add(MailNotificationAppConstants.AUTHORIZATION,
		 * MailNotificationAppConstants.TOKEN + jwt.getTokenValue());
		 */
		requestHeaders.add("Content-Type", "application/json;charset=utf-8");

		

		String payload = input.WorkRuleEnginePayload(tableId);
		System.err.println("payload: "+payload);
		entity = new HttpEntity<String>(payload, requestHeaders);

		String response = restTemplate.postForObject(uri, entity, String.class);
//		logger.info(
//				"response from wr " + response.toString() + " " + response.getStatusCode() + " " + response.getBody());
		System.err.println("response from wr "+response);
		
		return response;
	}

//	public String getPayloadByDecisionTable(String tableId) {
//		RestTemplate restTemplate = new RestTemplate();
//
//		final String baseUrl = RuleConstants.WORKRULE_API_BASE_URL + "/invokePayload/" + tableId;
//		URI uri = null;
//		try {
//			uri = new URI(baseUrl);
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
//
//		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
//		String responseData = (String) result.getBody();
//
//		return responseData;
//
//	}

	private String getXSRFToken(CloseableHttpClient client) throws URISyntaxException {

		HttpGet httpGet = null;
		CloseableHttpResponse response = null;
		String xsrfToken = null;
		try {

			Map<String, Object> map = DestinationReaderUtil.getDestination(DESTINATION_RULES_TOKEN);
			System.err.println("map for Rules_V2 : " + map.toString());

			// setting
			httpGet = new HttpGet((URI) map.get(URL));
			String auth = map.get(USER) + ":" + map.get(PASSWORD);
			String encoding = DatatypeConverter.printBase64Binary(auth.getBytes());

			System.err.println(httpGet);

			httpGet.addHeader(AUTHORIZATION, "Basic " + encoding);
			httpGet.addHeader(XCSRF_TOKEN, FETCH);

			response = client.execute(httpGet);

			System.err.println("response token: " + response);

			Header xsrfTokenheader = response.getFirstHeader(XCSRF_TOKEN);
			System.err.println("xsrfTokenheader : " + xsrfTokenheader);

			if (xsrfTokenheader != null) {
				xsrfToken = xsrfTokenheader.getValue();
			}
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if (httpGet != null) {
				httpGet.releaseConnection();
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
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
