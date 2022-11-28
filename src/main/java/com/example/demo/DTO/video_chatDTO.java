package com.example.demo.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class video_chatDTO {
	private String user_id;
	//private int order_count;
	private String chat;
	private String video_code;
	private boolean chat_status;
}
