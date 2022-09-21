package com.example.demo.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class clipDTO {
	private String clip_code;
	private String origin_video;
	private String user_id;
	private Date clip_date;
	private String clip_title;
	private String clip_url;
}
