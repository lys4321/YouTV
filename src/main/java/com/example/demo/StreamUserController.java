package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.example.demo.DTO.video_chatDTO;
import com.example.demo.Mapper.chattingMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class StreamUserController {
	private final SimpMessageSendingOperations messagingTemplate;
	
	@MessageMapping("/streaming/message")
	public void message(video_chatDTO info) {
		messagingTemplate.convertAndSend("/sub/streaming/live/"+info.getVideo_code(),info);
		System.out.println("들어옴: "+info);
	}
}
