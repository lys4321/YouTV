package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class video_chatDTO {
	private String user_id;
	private int order_count;
	private String chat;
	private String video_code;
	private boolean chat_status;
}
