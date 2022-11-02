package com.example.demo.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class video_chatDTO {
	private String user_id;
	private String chat;
	private String video_code;
	private boolean chat_status;
	private String chatDate;
}
