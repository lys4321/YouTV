package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repos.StreamChatRoom;
import com.example.demo.Repos.StreamChatRoomRepo;

@Service
public class Chatting_Service {
	@Autowired
	private final StreamChatRoomRepo streamChatRoomRepo = new StreamChatRoomRepo();
	
    public void create(String code){
    	streamChatRoomRepo.createRoom(code);
    }
	
    public void delete(String del){
    	streamChatRoomRepo.deleteRoom(del);
    }
    
    public StreamChatRoom get_RoomByCode(String code) {
		return streamChatRoomRepo.findRoomById(code);
    }
}
