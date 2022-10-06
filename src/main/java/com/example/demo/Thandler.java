package com.example.demo;

import java.util.ArrayList;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class Thandler extends TextWebSocketHandler {
	private static ArrayList<WebSocketSession> sessions = new ArrayList<WebSocketSession>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionEstablished(session);
		sessions.add(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// TODO Auto-generated method stub
		super.handleTextMessage(session, message);
		String payload = message.getPayload();
        JSONObject jsonObject = new JSONObject(payload);
        for (WebSocketSession s : sessions) {
            s.sendMessage(new TextMessage("Hi " + jsonObject.getString("user") + "!"));
        }
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionClosed(session, status);
		sessions.remove(session);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	private ArrayList<WebSocketSession> sessions;
//
//	private Thandler() {
//		sessions = new ArrayList<WebSocketSession>();
//	}
//	
//	@Override
//	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//		super.afterConnectionEstablished(session);
//		sessions.add(session);
//	}
//
//	@Override
//	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//		super.handleTextMessage(session, message);
//		String msg = message.getPayload();
//	}
//
//	@Override
//	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//		super.afterConnectionClosed(session, status);
//		sessions.remove(session);
//	}
	
	
	
	
	
	
//	@Autowired
//	Troommanager roommanager;
//	@Override
//	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//		super.handleTextMessage(session, message);
//		String msg = message.getPayload();
//		System.out.println(msg);
//	}
//	@Override
//	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//		super.afterConnectionClosed(session, status);
//	}
}
