package com.example.demo;

import java.util.HashMap;
import java.util.Map;

public class StreamUserRoomRepo {
	private Map<String, StreamUserRoom> roomMap = new HashMap<String, StreamUserRoom>();
	public StreamUserRoom findRoom(String code) {
		return roomMap.get(code);
	}
	public StreamUserRoom createRoom(String code) {
		StreamUserRoom room = StreamUserRoom.create(code);
		roomMap.put(code, room);
		return room;
	}
	public void deleteRoom(String code) {
		roomMap.remove(code);
	}
}
