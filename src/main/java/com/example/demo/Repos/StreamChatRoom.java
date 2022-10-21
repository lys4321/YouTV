package com.example.demo.Repos;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StreamChatRoom {
	private String code;
	private Set<WebSocketSession> sessions = new HashSet<>();
	
	public static StreamChatRoom create(String code) {
		StreamChatRoom room = new StreamChatRoom();
		room.code = code;
		return room;
	}
}
