package com.incture.cherrywork.notification;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class MessageEncoderWebsocket implements Encoder.Text<String> {
	private static Gson gson = new Gson();

	@Override
	public String encode(String dto) throws EncodeException {
		return gson.toJson(dto);
	}

	@Override
	public void init(EndpointConfig endpointConfig) {
		// Custom initialization logic
	}

	@Override
	public void destroy() {
		// Close resources
	}

}
