package com.incture.cherrywork.tasksubmit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Map;

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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.dtos.ResponseEntity;
import com.incture.cherrywork.sales.constants.ResponseStatus;
import com.incture.cherrywork.util.DestinationReaderUtil;



@Service
@Transactional
public class TriggerImeForWorkflowImpl implements TriggerImeForWorkflowService {

	public static final String XCSRF_TOKEN = "X-CSRF-Token";

	public static final String DESTINATION_RULES_TOKEN = "rulestoken";

	public static final String DESTINATION_RULES = "Rules_V2";

	public static final String USER = "User";

	public static final String PASSWORD = "Password";

	public static final String URL = "URL";

	public static final String CONTENT_TYPE = "Content-Type";

	public static final String AUTHORIZATION = "Authorization";

	public static final String FETCH = "Fetch";

	public static final String IME_TOKEN = "IME_Token";
	public static final String TRIGGER_IME = "Trigger_IME";

	public static final String USER_NAME = "User";

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public ResponseEntity triggerImePostDS(String salesOrderNumber) throws URISyntaxException, IOException {

		HttpContext httpContext = new BasicHttpContext();
		httpContext.setAttribute(HttpClientContext.COOKIE_STORE, new BasicCookieStore());
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = null;
		httpClient = getHTTPClient();

		Map<String, Object> map = DestinationReaderUtil.getDestination(TRIGGER_IME);
		System.err.println("map for rulesToken : " + map.toString());

		httpPost = new HttpPost((String) map.get(URL));
		httpPost.addHeader(CONTENT_TYPE, "application/json; charset=utf-8");

		String xsrfToken = getXSRFToken(httpClient);

		System.err.println("xsrfToken : " + xsrfToken);
		String payload = new String();
		if (xsrfToken != null) {
			httpPost.addHeader(XCSRF_TOKEN, xsrfToken); // header
			payload = "{\"context\":{}, \"definitionId\":\"triggerPostDecisionSetsIME\", \"workflowDefinitionId\":\"blocktypedeterminationworkflow\",\"businessKey\":\""
					+ salesOrderNumber + "\"}";
		}

		String auth = map.get(USER) + ":" + map.get(PASSWORD);
		String encoding = DatatypeConverter.printBase64Binary(auth.getBytes());
		httpPost.addHeader(AUTHORIZATION, "Basic " + encoding); // header

		StringEntity stringEntity;
		String respBody;
		try {
			stringEntity = new StringEntity(payload);
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
			respBody = new String(bytes.toByteArray(), "UTF-8");
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
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
			return new ResponseEntity("salesOrderNumber", HttpStatus.BAD_REQUEST, salesOrderNumber,
					ResponseStatus.FAILED);
		} catch (UnsupportedOperationException e) {
			logger.error(e.getMessage());
			return new ResponseEntity("salesOrderNumber", HttpStatus.BAD_REQUEST, salesOrderNumber,
					ResponseStatus.FAILED);
		} catch (IOException e) {
			logger.error(e.getMessage());
			return new ResponseEntity("salesOrderNumber", HttpStatus.BAD_REQUEST, salesOrderNumber,
					ResponseStatus.FAILED);
		}

		return new ResponseEntity("salesOrderNumber", HttpStatus.OK, salesOrderNumber + " : " + respBody,
				ResponseStatus.SUCCESS);
	}

	private static CloseableHttpClient getHTTPClient() {
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = clientBuilder.build();
		return httpClient;
	}

	private String getXSRFToken(CloseableHttpClient client) throws URISyntaxException {

		HttpGet httpGet = null;
		CloseableHttpResponse response = null;
		String xsrfToken = null;
		try {

			Map<String, Object> map = DestinationReaderUtil.getDestination(IME_TOKEN);
			System.err.println("map for Rules_V2 : " + map.toString());

			// setting
			httpGet = new HttpGet((String) map.get(URL));
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
				System.err.println("xsrfToken value : " + xsrfToken);
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

}

