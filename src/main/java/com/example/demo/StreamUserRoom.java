package com.example.demo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StreamUserRoom {
	private String code;
	private Set<WebSocketSession> sessions = new HashSet<>();
	
	public static StreamUserRoom create(String code) {
		StreamUserRoom room = new StreamUserRoom();
		room.code = code;
		return room;
	}
}
