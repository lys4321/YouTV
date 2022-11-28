package com.example.demo;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.example.demo.DTO.video_chatDTO;
import com.example.demo.Repos.video_img;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class StreamUserController {
	private final SimpMessageSendingOperations messagingTemplate;
	
	@MessageMapping("/streaming/message")
	public void message(video_img info) {
		messagingTemplate.convertAndSend("/sub/streaming/live/"+info.getCode(),info);
		System.out.println("들어옴: "+info);
	}
}
