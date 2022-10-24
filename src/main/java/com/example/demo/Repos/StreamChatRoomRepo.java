package com.example.demo.Repos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class StreamChatRoomRepo {
	private Map<String, StreamChatRoom> roomMap = new HashMap<String, StreamChatRoom>();
	
	public StreamChatRoom findRoom(String code) {
		return roomMap.get(code);
	}
	public StreamChatRoom createRoom(String code) {
		StreamChatRoom room = StreamChatRoom.create(code);
		roomMap.put(code, room);
		return room;
	}
	public void deleteRoom(String code) {
		roomMap.remove(code);
	}
	
	
	public List<StreamChatRoom> findAllRooms(){
        //채팅방 생성 순서 최근 순으로 반환
        List<StreamChatRoom> result = new ArrayList<>(roomMap.values());
        
        Collections.reverse(result);

        return result;
    }

    public StreamChatRoom findRoomById(String code){
        return roomMap.get(code);
    }

}
