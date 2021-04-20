package com.incture.cherrywork.repositories;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.Endpoint;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.dtos.NotificationListDto;
import com.incture.cherrywork.dtos.NotificationWebsocketDto;
import com.incture.cherrywork.config.DataSourceConfiguration;

@Service
@ComponentScan(basePackages = "com.incture.cherrywork")
@ServerEndpoint(value = "/websocketNotification/{userId}", decoders = MessageDecoderWebsocket.class, encoders = MessageEncoderWebsocket.class)
public class NotificationWebSocket {

//	@Autowired
//	INotificationDetailRepository notificationRepository;

	private Session session;
	private static final Set<NotificationWebSocket> websocketconfigs = new CopyOnWriteArraySet<>();
	private static HashMap<String, Session> users = new HashMap<>();
	@SuppressWarnings("unused")
	private static Endpoint endpoint;

	@SuppressWarnings("resource")
	@OnOpen
	public void onOpen(Session session, @PathParam("userId") String userId) throws IOException, EncodeException {
		this.session = session;
		websocketconfigs.add(this);
		// ApplicationContext applicationContext = new
		// AnnotationConfigApplicationContext(
		// DataSourceConfiguration.class);
		// INotificationDetailCustomRepositoryImpl notificationDetailDao =
		// applicationContext.getBean(INotificationDetailCustomRepositoryImpl.class);
		session.getBasicRemote().sendText("Connected!!");
		users.put(userId, session);
//		NotificationListDto notification = notificationRepository.getNotification(userId);
//		NotificationWebsocketDto websocketDto = new NotificationWebsocketDto();
//		websocketDto.setNotification(notification);
//		session.getBasicRemote().sendObject(websocketDto);
	}

	@OnClose
	public void onClose(Session session) throws IOException, EncodeException {
		websocketconfigs.remove(this);
	}

	@SuppressWarnings("resource")
	@OnMessage
	public void onMessage(Session session, NotificationWebsocketDto websocketDto) throws IOException, EncodeException {
		NotificationListDto notification = null;
		try {
			String user = websocketDto.getUser();
			session = users.get(user);
			if (session != null)
				broadcast(websocketDto, session);
		} catch (Exception e) {
			System.err.println("WebsocketConfiguration.onMessage() error : " + e);
		}
	}

	private static void broadcast(NotificationWebsocketDto websocketDto, Session currentSession)
			throws IOException, EncodeException {
		{
			try {
				currentSession.getBasicRemote().sendObject(websocketDto);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (EncodeException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void getResponse(NotificationWebsocketDto dto) {
		try {
			onMessage(session, dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
