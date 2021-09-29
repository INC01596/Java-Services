package com.incture.cherrywork.notification;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;


public class MessageDecoderWebsocket implements Decoder.Text<String> {
	private static Gson gson = new Gson();

	@Override
	public String decode(String s) throws DecodeException {
		return gson.fromJson(s, String.class);
	}

	@Override
	public boolean willDecode(String s) {
		return (s != null);
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
