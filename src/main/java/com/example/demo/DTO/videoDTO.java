package com.example.demo.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class videoDTO {
	private String video_code;
	private String streamer_id;
	private String title;
	private Date video_date;
	private boolean live_state;
	private String save_url;
	private String thumbnail_url;
}
