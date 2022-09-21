package com.example.demo.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	int changeStatus(videoDTO video) {
		return vmap.changeStatus(video);		
	}
	
	ArrayList<videoDTO> allLiveList(){
		return vmap.allLiveList();
	}
	
	ArrayList<videoDTO> liveList(){
		return vmap.liveList();
	}
	
	ArrayList<videoDTO> allRecordList(){
		return vmap.allRecordList();
	}
	
	ArrayList<videoDTO> recordList(){
		return vmap.recordList();
	}
	
	videoDTO selectVideo(videoDTO select_video) {
		return vmap.selectVideo(select_video);
	}
}
