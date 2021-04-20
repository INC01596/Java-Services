package com.incture.cherrywork.repositories;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import com.incture.cherrywork.dtos.NotificationWebsocketDto;

public class MessageEncoderWebsocket implements Encoder.Text<NotificationWebsocketDto>{
	
	private static Gson gson = new Gson();

	@Override
	public String encode(NotificationWebsocketDto dto) throws EncodeException {
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
