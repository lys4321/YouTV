package com.example.demo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Troom {
	private String code;
	//private Map<WebSocketSession,String> sessions = new HashMap<>();
	private Set<WebSocketSession> sessions = new HashSet<>();
	
	public static Troom create(String code) {
		Troom room = new Troom();
		room.code = code;
		return room;
	}
}
