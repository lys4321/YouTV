package com.example.demo.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class videoInfoDTO {
	private String video_code;
	private String streamer_id;
	private String title;
	private String profile;
	private String session;
	private String liveDate;
	private String create_session;
}

