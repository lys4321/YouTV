package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.video_chatDTO;
import com.example.demo.Mapper.chattingMapper;

@Service
public class videoChatService {
	@Autowired
	private chattingMapper cm;
	
	int addChatting(video_chatDTO chat) {
		return cm.addChatting(chat);
	}
	List<video_chatDTO> searchByCode(@Param(value="code") String code){
		return cm.searchByCode(code);
	}
	List<video_chatDTO> searchById(@Param(value="userid") String userid){
		return cm.searchById(userid);
	}
	List<video_chatDTO> searchByIdCode(String code, String userid){
		return cm.searchByIdCode(code, userid);
	}
}
