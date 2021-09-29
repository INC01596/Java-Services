package com.incture.cherrywork.services;



import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.cherrywork.WConstants.MailNotificationAppConstants;
import com.incture.cherrywork.dtos.DestinationDto;
import com.incture.cherrywork.dtos.MailRequestDto;
import com.incture.cherrywork.dtos.ResponseDto;
import com.incture.cherrywork.util.DestinationUtility;
import com.incture.cherrywork.util.ServicesUtil;


@Service
@Transactional
public class EmailSenderServiceImpl implements EmailSenderService {

	@Autowired
	
	private DestinationUtility destinationUtility; 
	private static final Logger MYLOGGER = LoggerFactory.getLogger(EmailSenderServiceImpl.class);

	@Override
	public ResponseDto triggerMail(MailRequestDto mailRequestDto) {

		MYLOGGER.info("[EmailSender][triggerMail] Execution start");

		ResponseDto responseDto = new ResponseDto();
		try {
			JSONObject inputBody = setRequestData(mailRequestDto);
			if (!ServicesUtil.isEmpty(inputBody)) {
				DestinationDto destination = destinationUtility.getDestinationByName("Work_rules_mail");
				HttpRequestBase httpRequestBase = null;
				HttpResponse httpResponse = null;
				String jsonString = null;
				StringEntity data = null;
				CloseableHttpClient httpClient = HttpClientBuilder.create().build();
				httpRequestBase = new HttpPost(destination.getUrl());

				data = new StringEntity(inputBody.toString());
				data.setContentType("application/json");
				((HttpPost) httpRequestBase).setEntity(data);

				httpRequestBase.addHeader(MailNotificationAppConstants.AUTHORIZATION,
						ServicesUtil.getBasicAuth(destination.getUserName(), destination.getPassword()));
				httpResponse = httpClient.execute(httpRequestBase);
				jsonString = EntityUtils.toString(httpResponse.getEntity());
				httpClient.close();
				if (ServicesUtil.isEmpty(jsonString)) {
					responseDto.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
					responseDto.setStatus(false);
					responseDto.setMessage("Error occured in triggering mail");
					responseDto.setData("");
					MYLOGGER.error("[EmailSenderUtil][triggerMail] - exception{} Error occure in triggering mail");
				} else {
					responseDto.setStatusCode(HttpStatus.SC_OK);
					responseDto.setStatus(true);
					responseDto.setMessage("Successfully triggered Mail");
					responseDto.setData(jsonString);
				}

			} else {
				responseDto.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
				responseDto.setStatus(false);
				responseDto.setMessage("Please send valid input");
				responseDto.setData(inputBody);
				MYLOGGER.error("[EmailSender][triggerMail] - exception{} invalid input");

			}

		} catch (IOException | JSONException e) {
			responseDto.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			responseDto.setStatus(false);
			responseDto.setMessage(e.getMessage());
			MYLOGGER.error("[EmailSender][triggerMail] - exception :" + e.getMessage());

		}

		MYLOGGER.info("[EmailSender][triggerMail] Execution end");

		return responseDto;

	}

	private JSONObject setRequestData(MailRequestDto mailRequestDto) throws JSONException {
		JSONObject inputBody = new JSONObject();
		if (!ServicesUtil.isEmpty(mailRequestDto)) {
			JSONObject inputParameters = new JSONObject();
			inputBody.put("mail_details", inputParameters);
			inputParameters.put("from", mailRequestDto.getFrom());
			inputParameters.put("to", mailRequestDto.getTo());
			inputParameters.put("subject", mailRequestDto.getSubject());
			inputParameters.put("body", mailRequestDto.getBody());
			if (ServicesUtil.isEmpty(mailRequestDto.getCc())) {
				inputParameters.put("cc", "");
			} else {
				inputParameters.put("cc", mailRequestDto.getCc());

			}
			if (ServicesUtil.isEmpty(mailRequestDto.getBcc())) {
				inputParameters.put("bcc", "");

			} else {
				inputParameters.put("bcc", mailRequestDto.getBcc());
			}
			if (!ServicesUtil.isEmpty(mailRequestDto.getAttachment())) {

				inputParameters.put("attachment", mailRequestDto.getAttachment());
				inputParameters.put("fileName", "ErrorFile");
			}

		}
		return inputBody;
	}

	
}
