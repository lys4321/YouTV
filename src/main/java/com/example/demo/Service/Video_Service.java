package com.example.demo.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.videoDTO;
import com.example.demo.Mapper.video_mapper;

@Service
public class Video_Service {
	@Autowired
	private video_mapper vmap;
	
	int addvideo(videoDTO video) {
		return vmap.addvideo(video);
	}
	
	ArrayList<videoDTO> allRecordList(){
		return vmap.allRecordList();
	}
	
	ArrayList<videoDTO> recordList(){
		return vmap.recordList();
	}
	
	videoDTO selectVideo(String video_code) {
		return vmap.selectVideo(video_code);
	}
	
	ArrayList<videoDTO> searchVideoBySearch(String str) {
		return vmap.searchVideoBySearch(str);
	}
}
