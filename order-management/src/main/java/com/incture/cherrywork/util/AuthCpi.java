package com.incture.cherrywork.util;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.stereotype.Component;


@Component
public class AuthCpi {

	
	
	
	
	
	public static final String TOKEN_REQUEST_URL = "https://inccpidev.authentication.eu10.hana.ondemand.com/oauth/token";
	public static final String CLIENT_ID = "sb-a3c2f989-d9ce-4738-8ac3-2e89380d60bd!b63626|it-rt-inccpidev!b16077";
	public static final String CLIENT_SECRET = "beaffcb2-40a1-41f3-b13a-df4884430040$inwXwytTJxNSuo_d8cfGQ71zsIjliCqafnNgrrr46xw=";

	
	public String getToken() throws OAuthSystemException, OAuthProblemException, UnsupportedEncodingException{
	String finalToken = null;
	String barerValue;
	String newEncodedCredentials = DatatypeConverter.printBase64Binary((CLIENT_ID + ":" + CLIENT_SECRET).getBytes("UTF-8"));
	String encodedCredentials = "Basic c2ItYTNjMmY5ODktZDljZS00NzM4LThhYzMtMmU4OTM4MGQ2MGJkIWI2MzYyNnxpdC1ydC1pbmNjcGlkZXYhYjE2MDc3OmJlYWZmY2IyLTQwYTEtNDFmMy1iMTNhLWRmNDg4NDQzMDA0MCRpbndYd3l0VEp4TlN1b19kOGNmR1E3MXpzSWpsaUNxYWZuTmdycnI0Nnh3PQ==";
	System.err.println("[getToken] : [encodedCredentials] " + encodedCredentials);
	System.err.println("[getToken] : [newEncodedCredentials] " + newEncodedCredentials);
	OAuthClient client = new OAuthClient(new URLConnectionClient());
	OAuthClientRequest request = OAuthClientRequest.tokenLocation(TOKEN_REQUEST_URL)
	.setGrantType(GrantType.CLIENT_CREDENTIALS)
	.buildBodyMessage();
	request.addHeader("Authorization","Basic "+newEncodedCredentials);
	OAuthJSONAccessTokenResponse oAuthResponse = client.accessToken(request,OAuth.HttpMethod.POST,OAuthJSONAccessTokenResponse.class);
	finalToken = oAuthResponse.getAccessToken();
	System.err.println("[getToken] : [finalToken] " + finalToken);
	return finalToken;
	}


}
