package com.incture.cherrywork.rules;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.util.DestinationReaderUtil;

import io.pivotal.cfenv.core.CfCredentials;
import io.pivotal.cfenv.jdbc.CfJdbcEnv;

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

@Service
@Transactional
public abstract class VisitPlannerRuleServiceDestination {

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

	public abstract VisitPlannerRuleOutput getVisitApprover(VisitPlanRuleInputDto input)
			throws IOException, InterruptedException;

	protected String executeGetVisitApproverRule(VisitPlanRuleInputDto input, String rulesServiceId,
			String salesRepEmail, String custCode) throws IOException {

		logger.info("[RuleServiceDestination][executeGetVisitApproverRule] started.");
		HttpContext httpContext = new BasicHttpContext();
		httpContext.setAttribute(HttpClientContext.COOKIE_STORE, new BasicCookieStore());
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = null;
		httpClient = getHTTPClient();

		String jwToken = DestinationReaderUtil.getJwtTokenForAuthenticationForSapApiRules();
		logger.info("[RuleServiceDestination][executeGetVisitApproverRule] map for rulesToken : " + jwToken);
		httpPost = new HttpPost(getRuleBaseUrl());
		httpPost.addHeader(CONTENT_TYPE, "application/json");

		httpPost.addHeader(AUTHORIZATION, "Bearer " + jwToken); // header

		String ruleInputString = input.toRuleInputString(rulesServiceId, salesRepEmail, custCode);
		logger.info("[RuleServiceDestination][executeGetVisitApproverRule] Payload{} ", ruleInputString);
		StringEntity stringEntity = new StringEntity(ruleInputString);

		httpPost.setEntity(stringEntity);

		response = httpClient.execute(httpPost);
		logger.info("[RuleServiceDestination][executeGetVisitApproverRule] response{}: ", response);

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
		httpPost.releaseConnection();
		response.close();
		httpClient.close();
		logger.info("[RuleServiceDestination][executeGetVisitApproverRule] respBody{}: ", respBody);
		return respBody;
	}

	private static CloseableHttpClient getHTTPClient() {
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		return clientBuilder.build();
	}

	public static String getRuleBaseUrl() {
		CfJdbcEnv cfEnv = new CfJdbcEnv();
		CfCredentials cfCredentialsWorkflow = cfEnv.findCredentialsByLabel("business-rules");
		Map<String, Object> cfCredentialsMapWorkflowUaa = cfCredentialsWorkflow.getMap();

		Map<String, Object> credentialMapWorkflow = (Map<String, Object>) cfCredentialsMapWorkflowUaa.get("endpoints");

		return (String) credentialMapWorkflow.get("rule_runtime_url")
				+ "/rules-service/rest/v2/workingset-rule-services";

	}

}
