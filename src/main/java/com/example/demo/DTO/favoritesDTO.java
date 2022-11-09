package com.example.demo.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class favoritesDTO {
	private String viewer_id;
	private String streamer_id;
	private String add_date;
}
