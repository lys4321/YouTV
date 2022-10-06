package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class TroomRepo {
	private Map<String, Troom> roomMap = new HashMap<String, Troom>();
	public Troom findRoom(String code) {
		return roomMap.get(code);
	}
	public Troom createRoom(String code) {
		Troom room = Troom.create(code);
		roomMap.put(code, room);
		return room;
	}
	public void deleteRoom(String code) {
		roomMap.remove(code);
	}
	
	
	public List<Troom> findAllRooms(){
        //채팅방 생성 순서 최근 순으로 반환
        List<Troom> result = new ArrayList<>(roomMap.values());
        Collections.reverse(result);

        return result;
    }

    public Troom findRoomById(String code){
        return roomMap.get(code);
    }

}
