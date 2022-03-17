package com.incture.cherrywork.util;



import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;





@Service
public class TokenGeneration {
	private static final Logger logger = LoggerFactory.getLogger(TokenGeneration.class);

	/*@Value("${vcap.services.ap-ocr.credentials.uaa.clientid}")
	private String clientid;
	@Value("${vcap.services.ap-ocr.credentials.uaa.clientsecret}")
	private String clientsecret;*/

	/*public String generateToken(CloseableHttpClient client) {
		try {
			HttpGet httpGet = new HttpGet(ApplicationConstants.OCR_TOKEN_URL);
			httpGet.setHeader("Authorization", ServiceUtil.encodeUsernameAndPassword(clientid,clientsecret));
			HttpResponse httpResponse = client.execute(httpGet);
			logger.error("HttpResponse" + httpResponse.toString());
			String responseJSON = EntityUtils.toString(httpResponse.getEntity());
			JSONObject jsonObj = new JSONObject(responseJSON);
			logger.error("responseJSON" + responseJSON);
			return jsonObj.getString("access_token");
		} catch (Exception e) {
			e.printStackTrace();
			return "Error:" + e.getMessage();
		}
	}
*/
	
	public String generateAbbyyOCRToken(CloseableHttpClient client) {
		try {
			HttpPost httpPost = new HttpPost(AbbyApplicationConstants.ABBYY_OCR_TOKEN_URL);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			builder.addPart("grant_type", new StringBody("password".toString(), ContentType.DEFAULT_TEXT));
			//builder.addPart("scope", new StringBody("openid permissions".toString(), ContentType.DEFAULT_TEXT));
			//abby username
			builder.addPart("username", new StringBody("Sandeep.k@incture.com".toString(), ContentType.DEFAULT_TEXT));
			builder.addPart("password", new StringBody("Rhythmsk1#".toString(), ContentType.DEFAULT_TEXT));
			
			builder.addPart("client_id", new StringBody("ABBYY.Vantage".toString(), ContentType.DEFAULT_TEXT));
			builder.addPart("client_secret", new StringBody("f3ec6136-6ccc-75a1-3780-caeef723e998".toString(), ContentType.DEFAULT_TEXT));
			HttpEntity multipart = builder.build();
			httpPost.setEntity(multipart);
			CloseableHttpResponse response = client.execute(httpPost);
			String responseApiCall = AbbyServiceUtil.getDataFromStream(response.getEntity().getContent());
			JSONObject jsonObj = new JSONObject(responseApiCall);
			logger.error("responseJSON" + responseApiCall);
			System.err.println("responseJSON" + responseApiCall);
			return jsonObj.getString("access_token");
		} catch (Exception e) {
			e.printStackTrace();
			return "Error:" + e.getMessage();
		}

	}
}

