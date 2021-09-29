package com.incture.cherrywork.util;


import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.incture.cherrywork.dtos.HttpResponseDto;
import com.incture.cherrywork.dtos.UserDto;

/**
 * @author Sushmita.Naresh
 * @version 1.0.0
 * @since 16-Apr-2020
 */

@Component
public class CommonMethods {

	static Logger logger = LoggerFactory.getLogger(CommonMethods.class);
	

	public static ResponseEntity<String> triggerRestApi(String url, HttpHeaders headers, String data, String method) {
		logger.info("Excecution started  for " + new Throwable().getStackTrace()[0].getClassName()
				+ new Throwable().getStackTrace()[0].getMethodName());
		URI uri = null;
		ResponseEntity<String> response = new ResponseEntity<String>(HttpStatus.OK);

		try {
			uri = new URI(url);

			HttpEntity<String> request = null;
			logger.info("inside trigger api");
			if (CommonsConfigConstants.HTTP_GET.equals(method)) {
				request = new HttpEntity<String>(headers);
				response = new RestTemplate().exchange(uri, HttpMethod.GET, request, String.class);
			} else {
				if (data != null) {
					request = new HttpEntity<String>(data, headers);
				} else {
					request = new HttpEntity<String>(headers);
				}
				if (CommonsConfigConstants.HTTP_POST.equals(method)) {
					response = new RestTemplate().exchange(uri, HttpMethod.POST, request, String.class);
				} else if (CommonsConfigConstants.HTTP_PUT.equals(method)) {
					response = new RestTemplate().exchange(uri, HttpMethod.PUT, request, String.class);
				} else if (CommonsConfigConstants.HTTP_DELETE.equals(method)) {
					response = new RestTemplate().exchange(uri, HttpMethod.DELETE, request, String.class);
				}
			}

		} catch (URISyntaxException e) {

			logger.info("Error occured at  " + new Throwable().getStackTrace()[0].getClassName()
					+ new Throwable().getStackTrace()[0].getMethodName() + "and error message is  " + e.getMessage());
			BodyBuilder body = ResponseEntity.status(500);
			body = ResponseEntity.status(HttpStatus.valueOf(500));
			response.status(500);
			response.status(HttpStatus.valueOf(500));
		}

		logger.info("Excecution ended  for " + new Throwable().getStackTrace()[0].getClassName()
				+ new Throwable().getStackTrace()[0].getMethodName() + "and response is  " + response);
		return response;
	}

	public static String getAccessTokenForOauth(Map<String, String> map)
			throws URISyntaxException, JsonMappingException, JsonProcessingException {
		String token = null;
		logger.info("Excecution started  for " + new Throwable().getStackTrace()[0].getClassName()
				+ new Throwable().getStackTrace()[0].getMethodName());
		StringBuilder str = new StringBuilder();
		str.append("grant_type" + "=" + "password");
		str.append("username" + "=" + map.get(CommonsConfigConstants.TOKEN_USER));
		str.append("password" + "=" + map.get(CommonsConfigConstants.TOKEN_PASSWORD));
		str.append("client_id" + "=" + map.get(CommonsConfigConstants.CLIENT_ID));
		str.append("client_secret" + "=" + map.get(CommonsConfigConstants.CLIENT_SECRET));

		str.append("responsetype" + "=" + "token");
		byte[] postData = str.toString().getBytes(StandardCharsets.UTF_8);

		URI uri = new URI(map.get(CommonsConfigConstants.TOKEN_URL) + "/oauth/token");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		List<Charset> charsetList = new ArrayList<Charset>();
		charsetList.add(StandardCharsets.UTF_8);

		// headers.setAcceptCharset(CharsetList);
		HttpEntity<String> request = new HttpEntity<String>(postData.toString(), headers);
		ResponseEntity<String> response = new RestTemplate().exchange(uri, HttpMethod.POST, request, String.class);
		JsonNode node = new ObjectMapper().readTree(response.getBody());
		if (node.findValue("access_token") != null) {
			token = node.findValue("access_token").toString();
		}

		logger.info("Excecution ended  for " + new Throwable().getStackTrace()[0].getClassName()
				+ new Throwable().getStackTrace()[0].getMethodName() + "and token generated is " + token);
		return token;
	}
	
	public static Map<String, UserDto> getUserDetails(List<String> userIdList)
			throws JsonMappingException, JsonProcessingException {

		logger.info("[AuditLogServiceImpl]|[getUserDetails]|Execution  Start |input :" + userIdList);

		Map<String, UserDto> userIdMap = new LinkedHashMap<String, UserDto>();
		Boolean isFirst = Boolean.TRUE;

		try {
			isFirst = Boolean.TRUE;
			DestinationUtility destinationUtility = new DestinationUtility();
			if (!ServicesUtil.isEmpty(userIdList)) {
				StringBuilder str = new StringBuilder();
				str.append("/Users?attributes=userName,name,id,emails");
				Map<String, Boolean> isPresent = new LinkedHashMap<String, Boolean>();
				if (!ServicesUtil.isEmpty(userIdList)) {
					str.append("&filter=");
					/*userIdList.forEach(user -> {
						if (!isFirst && !isPresent.containsKey(user)) {
							str.append(" or ");

						}
						if (!isPresent.containsKey(user)) {
							str.append(" userName eq " + "'" + user + "'");
							isPresent.put(user, Boolean.TRUE);
							isFirst = Boolean.FALSE;
						}

					});*/
					
					for(String user : userIdList){
						if (!isFirst && !isPresent.containsKey(user)) {
							str.append(" or ");

						}
						if (!isPresent.containsKey(user)) {
							str.append(" userName eq " + "'" + user + "'");
							isPresent.put(user, Boolean.TRUE);
							isFirst = Boolean.FALSE;
						}
					}
				}
				logger.info("[AuditLogServiceImpl]|[getUserDetails]|url to send |" + str.toString());

				HttpResponseDto httpResponseDto = destinationUtility.getDataFromDestinationSystem("authorization_api",
						str.toString(), "GET", null, null, null, Boolean.FALSE, null, "Rest Api");
				logger.info("[AuditLogServiceImpl]|[getUserDetails]|ResponseFrom Destination call|" + httpResponseDto);

				if (httpResponseDto.getStatuscode() == 200 && httpResponseDto.getResponseData() != null) {
					JsonNode node = new ObjectMapper().readValue(httpResponseDto.getResponseData(), JsonNode.class);
					JsonNode resourceNode = node.get("resources");
					if (!resourceNode.isEmpty()) {
						resourceNode.forEach(resource -> {
							UserDto userDto = new UserDto();
							JsonNode name = !resource.get("name").isEmpty() ? resource.get("name") : null;
							JsonNode emails = !resource.get("emails").isEmpty() ? resource.get("emails") : null;

							String firstName = null;
							String lastName = null;
							List<String> emailIdList = new ArrayList<String>();
							JsonNode userNameNode = resource.get("userName");

							String userName = userNameNode != null ? userNameNode.asText() : null;
							if (userName != null) {
								userName = userName.toLowerCase();
								if (name != null) {
									lastName = name.get("familyName") != null ? name.get("familyName").asText() : null;
									firstName = name.get("givenName") != null ? name.get("givenName").asText() : null;
								}
								logger.info("[AuditLogServiceImpl]|[getUserDetails]|FirstName +lastName+usrName"
										+ firstName + " " + lastName + " " + userName);
								if (emails != null) {
									emails.forEach(email -> {
										emailIdList.add(email.get("value").asText());
									});
								}
								if (userIdMap.containsKey(userName)) {
									UserDto existingUser = userIdMap.get(userName);
									if (existingUser.getName() == null && firstName != null && lastName != null
											&& !firstName.equalsIgnoreCase("unknown")
											&& !lastName.equalsIgnoreCase("unknown")) {
										existingUser.setName(firstName + " " + lastName);
									}
									if (ServicesUtil.isEmpty(existingUser.getEmails())
											&& !ServicesUtil.isEmpty(emailIdList)) {
										existingUser.setEmails(emailIdList);
									}
									existingUser.setUserName(userName);

									userIdMap.put(userName, existingUser);

								} else {
									if (firstName != null && lastName != null && !firstName.equalsIgnoreCase("unknown")
											&& !lastName.equalsIgnoreCase("unknown")) {
										userDto.setName(firstName + " " + lastName);
									}

									userDto.setEmails(emailIdList);
									userDto.setUserName(userName);
									userIdMap.put(userName, userDto);

								}
							}
						});
					}

				}
			}
		} catch (Exception e) {
			logger.info("[AuditLogServiceImpl]|[getUserDetails]| Exception Occured  |" + e.getMessage());

		}
		return userIdMap;
	}
}
