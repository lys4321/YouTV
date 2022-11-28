package com.example.demo.Repos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
		System.out.println("[방코드] : "+ code);
		StreamChatRoom room = StreamChatRoom.create(code);
		roomMap.put(code, room);
		return room;
	}
	
	public void deleteRoom(String code) {
		roomMap.remove(code);
	}
	
	public void enterChatroom(String code, String userid) {
		System.out.println("[방코드2] : "+ code);
		System.out.println("[룸] : "+ roomMap.get(code));
		roomMap.get(code).addGuestes(userid);
	}

	public ArrayList<String> getLists(String code){
		ArrayList<String> arr = roomMap.get(code).getArr();
		HashSet<String> hash = new HashSet<String>(arr);
		arr = new ArrayList<String>(hash);
		return arr;
	}
	
	public ArrayList<String> getQueryLists(String code, String query){
		ArrayList<String> guestes = roomMap.get(code).getArr();
		ArrayList<String> arr = new ArrayList<String>();
		
		for(int i=0; i<guestes.size(); i++) {
			if(guestes.get(i).contains(query)) {
				arr.add(guestes.get(i));
			}
		}
		HashSet<String> hash = new HashSet<String>(arr);
		arr = new ArrayList<String>(hash);
		
		return arr;
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
