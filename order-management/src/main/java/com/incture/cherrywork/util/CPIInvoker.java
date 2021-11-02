//package com.incture.cherrywork.util;
//
//import java.io.IOException;
//import java.util.Map;
//
//import javax.xml.bind.DatatypeConverter;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpRequestBase;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class  CPIInvoker{
//	
//public HttpResponse checkFunction(Object dto, Map<String, Object> map) throws IOException {
//HttpResponse httpResponse = null;
//HttpRequestBase httpRequestBase = null;
//StringEntity data = null;
//CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//if (!HelperClass.isEmpty(dto)) {
//if (map != null) {
//String authToken = HelperClass.encodeUsernameAndPassword((String) map.get("clientId"),
//(String) map.get("clientSecret"));
//if (!HelperClass.checkString(authToken)) {
//String requestUrl = (String) map.get("URL") + (String) map.get("BP_CREATE");
//httpRequestBase = new HttpPost(requestUrl);
//ObjectMapper mapper = new ObjectMapper();
//String json = mapper.writeValueAsString(dto);
//
//data = new StringEntity(json, "utf-8");
//data.setContentType("application/json");
//((HttpPost) httpRequestBase).setEntity(data);
//httpRequestBase.addHeader("accept", "application/json");
//httpRequestBase.addHeader("Authorization", authToken);
//httpResponse = httpClient.execute(httpRequestBase);
//httpClient.close();
//}
//}
//}
//return httpResponse;
//}
//
//
//public static String encodeUsernameAndPassword(String username, String password) 
//{
//	String encodeUsernamePassword = username + ":" + password;
//	String auth = "Basic " + DatatypeConverter.printBase64Binary(encodeUsernamePassword.getBytes());
//	return auth;
//	}
//}
//
