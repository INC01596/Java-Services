/*package com.incture.cherrywork.repositories;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import com.incture.cherrywork.dtos.NotificationWebsocketDto;

public class MessageDecoderWebsocket implements Decoder.Text<NotificationWebsocketDto>{
	
private static Gson gson = new Gson();
	
	@Override
    public NotificationWebsocketDto decode(String s) throws DecodeException {
		return gson.fromJson(s, NotificationWebsocketDto.class);
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
*/