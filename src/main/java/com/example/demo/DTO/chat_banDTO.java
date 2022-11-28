package com.example.demo.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class chat_banDTO {
	private String streamer_id;
	private String viewer_id;
	private String ban_date;
	private String ban_reason;
}
