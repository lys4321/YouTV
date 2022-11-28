package com.example.demo.Repos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class StreamChatRoom {
	private String code;
	private Set<WebSocketSession> sessions = new HashSet<>();
	private static ArrayList<String> guestes = new ArrayList<String>();
	
	public static StreamChatRoom create(String code) {
		StreamChatRoom room = new StreamChatRoom();
		room.code = code;
		return room;
	}
	
	public void addGuestes(String userid) {
		guestes.add(userid);
		System.out.println("[ 채팅방 온 사람 : "+userid+" ] : "+ guestes);
	}
	
	public ArrayList<String> getArr(){
		System.out.println("[ 이 채팅방 속 사람 ] : "+ guestes);
		System.out.println("[ 이 채팅방 속 세션 ] : "+ sessions);
		return guestes;
	}
	
}
