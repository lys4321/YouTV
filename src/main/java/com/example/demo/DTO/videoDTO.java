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
	private String video_date;
	private String thumbnail;
}
