package com.incture.cherrywork.util;


import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import com.fasterxml.jackson.databind.JsonNode;
import com.incture.cherrywork.dtos.DestinationDto;
import com.incture.cherrywork.dtos.HttpResponseDto;
import com.incture.cherrywork.dtos.ResponseDto;

/**
 * @author Shruti.Ghanshyam
 *
 */
@Component
public class DestinationUtility {
	private final Logger logger = LoggerFactory.getLogger(DestinationUtility.class);

	public HttpResponseDto getDataFromDestinationSystem(String destinationName, String url, String methodType,
			String payload, Map<String, List<Object>> filters, String tokenValue, Boolean isExpanded,
			String expandAttribute, String apiType) {

		logger.info("[DestinationUtility]|[getDestinationConnection]|Execution Start With Input ..DestinationName="
				+ destinationName + " url =" + url + "methodType=" + methodType + " payload=" + payload + "filters="
				+ filters);

		HttpResponseDto httpResponseDto = new HttpResponseDto();
		try {
			RestTemplate restTemp = new RestTemplate();
			// final Destination destination =
			// DestinationAccessor.getDestination(destinationName);
			DestinationDto dto = getDestinationByName(destinationName);

			String destinationUri = dto.getUrl();

			HttpHeaders headers = new HttpHeaders();
			ResponseEntity<String> resHttpDto = null;
			System.err.println("destinationUri.." + destinationUri);

			if (destinationUri != null) {
				if (destinationUri.endsWith("/")) {
					destinationUri = destinationUri.trim().substring(0, destinationUri.lastIndexOf("/"));
					System.err.println("inside ends with /.." + destinationUri);

				}
				if (url.startsWith("/")) {
					url = url.trim().substring(1);

				}

				HttpEntity<String> entity = null;
				if (dto.getAuthentication() != null
						&& !CommonsConfigConstants.NO_AUTH.equals(dto.getAuthentication())) {
					headers.add("Authorization", getAuthorization(dto, tokenValue));
					headers.add("Content-Type", "application/json");
				}

				if (methodType != null && !methodType.equals(CommonsConfigConstants.HTTP_GET) && payload != null) {
					entity = new HttpEntity<String>(payload, headers);
					headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

				} else {
					entity = new HttpEntity<String>(headers);
				}

				String finalUrlToCalled = destinationUri + "/" + url;
				if (isExpanded && expandAttribute != null) {
					finalUrlToCalled = addExpandAttribute(finalUrlToCalled, expandAttribute);
				}
				if (filters != null && !filters.isEmpty()) {

					finalUrlToCalled = finalUrlToCalled.replace(" ", "%20")
							+ addApiFilters(finalUrlToCalled, filters, apiType, isExpanded);

				} else if (apiType.equals("Odata")) {
					if (isExpanded) {
						finalUrlToCalled = finalUrlToCalled + "&$format=json";

					} else if (filters != null && !filters.isEmpty()) {
						finalUrlToCalled = finalUrlToCalled + "&$format=json";

					} else {
						finalUrlToCalled = finalUrlToCalled + "?$format=json";
					}
				}

				System.err.println("finalUrlToCalled.." + finalUrlToCalled + " [entity = " + entity + "] methodType = "
						+ methodType);
				if (CommonsConfigConstants.HTTP_GET.equals(methodType)) {
					resHttpDto = restTemp.exchange(finalUrlToCalled, HttpMethod.GET, entity, String.class);
				} else if (CommonsConfigConstants.HTTP_POST.equals(methodType)) {
					resHttpDto = restTemp.exchange(finalUrlToCalled, HttpMethod.POST, entity, String.class);

				} else if (CommonsConfigConstants.HTTP_PUT.equals(methodType)) {
					resHttpDto = restTemp.exchange(finalUrlToCalled, HttpMethod.PUT, entity, String.class);

				} else if (CommonsConfigConstants.HTTP_PATCH.equals(methodType)) {
					resHttpDto = restTemp.exchange(finalUrlToCalled, HttpMethod.PATCH, entity, String.class);

				} else if (CommonsConfigConstants.HTTP_DELETE.equals(methodType)) {
					resHttpDto = restTemp.exchange(finalUrlToCalled, HttpMethod.DELETE, entity, String.class);

				}

				httpResponseDto.setResponseData(resHttpDto.getBody());
				httpResponseDto.setStatuscode(resHttpDto.getStatusCodeValue());
			} else {
				httpResponseDto.setResponseData(null);
				httpResponseDto.setStatuscode(200);
			}

		} catch (URISyntaxException e) {
			logger.info("[DestinationUtility]|[getDestinationConnection]|Exception Occured" + e.getMessage());
			httpResponseDto.setStatuscode(500);
		}

		logger.info("[DestinationUtility]|[getDestinationConnection]|Execution End Output httpResponseDto="
				+ httpResponseDto);

		return httpResponseDto;

	}

	public DestinationDto getDestinationByName(String name) {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatusCode(200);
		responseDto.setStatus(true);
		DestinationDto dto = null;
		try {
System.err.println("hey dest1");
			dto = new DestinationDto();
			String tokenServRes = getAcceesTokenForDestinationServices();

			if (tokenServRes != null) {

				HttpHeaders headers = new HttpHeaders();
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				headers.add("Authorization", "Bearer " + tokenServRes);

				RestTemplate restClient = new RestTemplate();
				HttpEntity<String> entity = new HttpEntity<String>(headers);

				ResponseEntity<String> resHttpDto = restClient.exchange(
						CommonsConfigConstants.DES_BASE_URL + "/" + name, HttpMethod.GET, entity, String.class);

				if (resHttpDto.getStatusCode().equals(HttpStatus.OK) && resHttpDto.getBody() != null) {
					JSONObject responseObj = new JSONObject(resHttpDto.getBody());

					dto.setName(responseObj.optString("Name"));
					dto.setDescription(responseObj.optString("Description"));
					dto.setType(responseObj.optString("Type"));
					dto.setProxytype(responseObj.optString("ProxyType"));
					dto.setUrl(responseObj.optString("URL"));
					dto.setAuthentication(responseObj.optString("Authentication"));
					dto.setTokenServiceURL(responseObj.optString("tokenServiceURL"));
					dto.setTokenServiceURLType(responseObj.optString("tokenServiceURLType"));
					dto.setClientId(responseObj.optString("clientId"));
					dto.setClientSecret(responseObj.optString("clientSecret"));
					dto.setUserName(responseObj.optString("User"));
					dto.setPassword(responseObj.optString("Password"));
					responseDto.setData(dto);

				}

			} else {
				responseDto.setMessage("Unable to connect destination server");
			}

		} catch (Exception e) {
			responseDto.setStatusCode(500);
			responseDto.setStatus(false);
			e.printStackTrace();
			responseDto.setMessage("Failed to delete destination Error " + e.getMessage());
		}
		return dto;
	}

	public String getAcceesTokenForDestinationServices() {
		String accessToken = null;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add(CommonsConfigConstants.AUTHORIZATION,
				createAuthHeaderString(CommonsConfigConstants.DES_CLIENT_ID, CommonsConfigConstants.DES_CLIENT_SECRET));

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("response_type", "token");
		map.add("client_id", CommonsConfigConstants.DES_CLIENT_ID);
		map.add("client_secret", CommonsConfigConstants.DES_CLIENT_SECRET);

		map.add("grant_type", "client_credentials");

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange(CommonsConfigConstants.DES_TOKEN_URL, HttpMethod.POST,
				entity, JsonNode.class);
		if (response != null) {
			JsonNode res = response.getBody();
			accessToken = res.get("access_token") != null ? res.get("access_token").asText() : null;
		}

		return accessToken;

	}

	private String addApiFilters(String url, Map<String, List<Object>> filters, String apiType, Boolean isExpanded)
			throws URISyntaxException {

		StringBuilder str = new StringBuilder();

		if (apiType != null && apiType.equals("Odata")) {
			// str.append(url);
			if (isExpanded) {
				str.append("&$filter=");
			} else {
				str.append("?$filter=");
			}

			Boolean isFirst = true;
			for (Entry<String, List<Object>> entry : filters.entrySet()) {
				String obj = entry.getKey();
				List<Object> filterValues = entry.getValue();
				if (!ServicesUtil.isEmpty(filterValues)) {
					if (!isFirst) {
						str.append(" and ");
					}
					if (filterValues.size() == 1) {
						str.append(obj + " eq " + "'" + filterValues.get(0) + "'");
					} else if (filterValues.size() > 1) {
						Boolean firstVal = true;
						str.append("(");
						for (Object value : filterValues) {
							if (!firstVal) {
								str.append(" or ");
							}

							str.append(obj + " eq " + "'" + value + "'");
							firstVal = false;
						}
						str.append(")");
					}
					isFirst = false;
				}

			}

		} else {
			str.append("?");

			Boolean isFirst = true;
			for (String obj : filters.keySet()) {

				if (!isFirst) {
					str.append("&");
				}
				Object filterValue = filters.get(obj);
				if (!ServicesUtil.isEmpty(filterValue)) {

					str.append(obj + "=" + filterValue);
					isFirst = false;
				}

			}
		}

		System.out.println(" filtered query .." + str.toString());
		return str.toString();
		// TODO Auto-generated method stub

	}

	/**
	 * @param url
	 * @param expandAttribute
	 * @return
	 * @throws URISyntaxException
	 */
	private String addExpandAttribute(String url, String expandAttribute) throws URISyntaxException {

		StringBuilder str = new StringBuilder();
		str.append(url);
		str.append("?$expand=" + expandAttribute);

		logger.info(" expanded query .." + str.toString());
		return str.toString();

	}

	public static String toInitCap(String param) {
		if (param != null && param.length() > 0) {
			char[] charArray = param.toCharArray();
			charArray[0] = Character.toUpperCase(charArray[0]);
			return new String(charArray);
		} else {
			return "";
		}
	}

	private String getAuthorization(DestinationDto dto, String existingUserToken) {
		String auth = null;
		if (CommonsConfigConstants.BASIC_AUTH.equals(dto.getAuthentication())) {
			auth = createAuthHeaderString(dto.getUserName(), dto.getPassword());
		} else if (CommonsConfigConstants.OAUTH2_USER_PASSWORD_AUTH.equals(dto.getAuthentication())
				|| CommonsConfigConstants.OAUTH2_CLIENT_CREDENTIALS.equals(dto.getAuthentication())) {
			auth = getAcceesTokenForOauth2Password(dto);
		} else if (CommonsConfigConstants.OAUTH2_USER_TOKEN_EXCHANGE.equals(dto.getAuthentication())) {
			auth = "Bearer " + existingUserToken;
		}

		return auth;

	}

	private String createAuthHeaderString(String username, String password) {
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
		String authHeader = "Basic " + new String(encodedAuth);
		return authHeader;
	}

	public String getAcceesTokenForOauth2Password(DestinationDto dto) {
		String auth = null;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add(CommonsConfigConstants.AUTHORIZATION,
				createAuthHeaderString(dto.getClientId(), dto.getClientSecret()));

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("response_type", "token");
		map.add("client_id", dto.getClientId());
		map.add("client_secret", dto.getClientSecret());

		if (CommonsConfigConstants.OAUTH2_USER_PASSWORD_AUTH.equals(dto.getAuthentication())) {
			map.add("username", dto.getUserName());
			map.add("password", dto.getPassword());

			map.add("grant_type", "password");
		} else if (CommonsConfigConstants.OAUTH2_CLIENT_CREDENTIALS.equals(dto.getAuthentication())) {
			map.add("grant_type", "client_credentials");

		}

		// Create an HttpEntity object, wrapping the body and headers of the
		// request
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

		JsonNode response = restTemplate.exchange(dto.getTokenServiceURL(), HttpMethod.POST, entity, JsonNode.class)
				.getBody();
		String accessToken = response.get("access_token") != null ? response.get("access_token").asText() : null;
		if (accessToken != null) {
			auth = "Bearer " + accessToken;
		}

		return auth;

	}

}