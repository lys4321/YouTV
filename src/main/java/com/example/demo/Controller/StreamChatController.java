package com.example.demo.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.example.demo.DTO.video_chatDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class StreamChatController {
	private final SimpMessageSendingOperations messagingTemplate;
	
	@MessageMapping("/chat/message")
	public void message(video_chatDTO chat) {
		messagingTemplate.convertAndSend("/sub/chat/room/"+chat.getVideo_code(),chat);
		System.out.println("들어옴: "+chat);
	}
}
