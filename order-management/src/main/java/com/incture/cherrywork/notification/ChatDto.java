package com.incture.cherrywork.notification;

import java.util.List;

import lombok.Data;

@Data
public class ChatDto {
	
	private List<String> sentTo;
	private String sentBy;
	private String messageType;
	private String commentType;
	private String currentMessageStatus;

}
