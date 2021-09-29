package com.incture.cherrywork.notification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

//import com.incture.chat.dto.String;
//import com.incture.chat.service.UserDetailsService;
//import com.incture.chat.util.EnStatus;
//import com.incture.chat.util.ServicesUtil;

@Service
@ComponentScan(basePackages = "com.incture.cherrywork")
@ServerEndpoint(value = "/notification/websocketChat/{userID}", 
		decoders = MessageDecoderWebsocket.class, encoders = MessageEncoderWebsocket.class)
public class WebsocketChatConfiguration
{

	private Session session;
	private static final Set<WebsocketChatConfiguration> websocketconfigs = new CopyOnWriteArraySet<>();
	private static HashMap<String, List<Session>> users = new HashMap<>();
	private static HashMap<Session, String> platformref = new HashMap<>();
	private static HashMap<Session, String> usersref = new HashMap<>();

	@OnOpen
	public void onOpen(Session session, @PathParam("userID") String userID)
			throws IOException, EncodeException
	{
		System.err.println("OnOpen Starts..");
		this.session = session;
		websocketconfigs.add(this);
//		platformref.put(session, platform);
		broadcast("Opened! ", users.get(userID));
		if(!users.containsKey(userID) || users.get(userID).isEmpty())
		{
			List<Session> val = new ArrayList<>();
			val.add(session);
			users.put(userID, val);
		}
		else
		{
			List<Session> val = users.get(userID);
			val.add(session);
			users.put(userID, val);
		}
		usersref.put(session, userID);
	}

	@OnClose
	public void onClose(Session session) throws IOException, EncodeException
	{
		try
		{
			System.err.println("OnClose Starts..");
			
//			ApplicationContext applicationContext = new AnnotationConfigApplicationContext(HibernateConfiguration.class);
//			UserDetailsService userService = applicationContext.getBean(UserDetailsService.class);
//			String userID = usersref.get(session);
//			if(userService.checkUserExistence(userID))
//				userService.updateLastLogoutTime(userID, new Date()); // Assuming that both the client and server clocks are synced
//			((AnnotationConfigApplicationContext) applicationContext).close();
//			session.getBasicRemote().sendObject(chatHistoryDto);
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		websocketconfigs.remove(this);
		platformref.remove(session);
		List<Session> val = users.get(usersref.get(session));
		val.remove(session);
		users.put(usersref.get(session), val);
		usersref.remove(session);
	}

	@OnMessage
	public void onMessage(Session session, String chat)
	{
//		try
//		{
			System.err.println("onMessage Starts..");
			System.err.println("Key Set - " + users.keySet());
			System.err.println("Entry Set - " + users.entrySet());

//			List<String> sentList = new ArrayList<>();
//			sentList.addAll(chat.getSentTo());
//			for(String sentTo : sentList)
//			{
//				if(ServicesUtil.isEmpty(chat.getCommentType()) || !chat.getCommentType().trim().equalsIgnoreCase("chatbot"))
//				{
//					if(chat.getMessageType().equalsIgnoreCase("chat"))
//					{
//						if(users.containsKey(sentTo))
//						{
//							broadcast(chat, users.get(sentTo));
//							chat.setMessageType("acknowledge");
//							chat.setCurrentMessageStatus(EnStatus.DELIVERED.toString());
//							if(users.containsKey(chat.getSentBy()))
//								broadcast(chat, users.get(chat.getSentBy()));
//							chat.setMessageType("chat");
//						}
//						else
//						{
//							chat.setMessageType("acknowledge");
//							chat.setCurrentMessageStatus(EnStatus.SENT.toString());
//							if(users.containsKey(chat.getSentBy()))
//								broadcast(chat, users.get(chat.getSentBy()));
//							chat.setMessageType("chat");
//						}
//					}
//					else
//					{
//						if(users.containsKey(sentTo))
//							broadcast(chat, users.get(sentTo));
//					}
//				}
//				else
//				{
//					if(chat.getMessageType().equalsIgnoreCase("chat"))
//					{
//						if(users.containsKey(sentTo))
//						{
//							broadcast(chat, users.get(sentTo));
//							if(!chat.getSentBy().trim().equalsIgnoreCase("Chatbot"))
//							{
//								chat.setMessageType("acknowledge");
//								chat.setCurrentMessageStatus(EnStatus.DELIVERED.toString());
//								if(users.containsKey(chat.getSentBy()))
//									broadcast(chat, users.get(chat.getSentBy()));
//								chat.setMessageType("chat");
//							}
//						}
//					}
//				}
//			}
//			((AnnotationConfigApplicationContext) applicationContext).close();
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			System.err.println("WebsocketChatConfiguration.onMessage() error: " + e.getMessage());
//		}
	}

	private static void broadcast(String chat, List<Session> currentSession) throws IOException, EncodeException
	{
		System.err.println("broadcast starts..");
		try
		{
			for(Session thisSession : currentSession)
				thisSession.getBasicRemote().sendObject(chat);
		}
		catch(IOException e1)
		{
			e1.printStackTrace();
		}
		catch(EncodeException e1)
		{
			e1.printStackTrace();
		}
	}

	public void sendMessage(String chat)
	{
		System.err.println("sendMessage Starts..");
		try
		{
			onMessage(session, chat);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean isUserOnline(String userID)
	{
		System.err.println("isUserOnlinbe starts..");
		if(users.containsKey(userID))
			return true;
		return false;
	}
	/*
	 * public void getNumberOfChat(String resourceId) {
	 * 
	 * ChatHistoryDto chatHistoryDto = null;
	 * 
	 * Integer count = null; try{
	 * 
	 * }catch (Exception e) { e.printStackTrace();
	 * System.out.println("WebsocketChatConfiguration.onOpen()" + e); }
	 * 
	 * chatHistoryDto = new ChatHistoryDto();
	 * chatHistoryDto.setNumberOfEventDto(eventList);
	 * 
	 * for (HashMap.Entry<Session,String> entry : resourceInfo.entrySet()) {
	 * 
	 * if(resourceId.equals(entry.getValue())) { try {
	 * entry.getKey().getBasicRemote().sendObject(chatHistoryDto); } catch
	 * (IOException | EncodeException e) {
	 * System.out.println("WebsocketChatConfiguration.getNumberOfChat()"
	 * +": error in sending "); } } }
	 * 
	 * // return eventList;
	 * 
	 * }
	 */
}
