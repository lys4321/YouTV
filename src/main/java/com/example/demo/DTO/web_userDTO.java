package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class web_userDTO {
	private String user_id;
	private String user_pw;
	private String user_name;
	private String user_pnum;
	private String image_url;
}
