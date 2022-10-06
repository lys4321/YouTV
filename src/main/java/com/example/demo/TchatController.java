package com.example.demo;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.example.demo.DTO.video_chatDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TchatController {
	private final SimpMessageSendingOperations messagingTemplate;
	
	@MessageMapping("/chat/message")
	public void message(video_chatDTO chat) {
		messagingTemplate.convertAndSend("/sub/chat/room/"+chat.getVideo_code(),chat);
		System.out.println("들어옴: "+chat);
	}
	
	
	
	
	/*
	 * @MessageMapping(value = "/chat/enter") public void enter(video_chatDTO
	 * message){ message.setChat(message.getUser_id() + "님이 채팅방에 참여하였습니다.");
	 * messagingTemplate.convertAndSend("/sub/chat/room/" + message.getVideo_code(),
	 * message); }
	 */
}
