package com.example.demo.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.videoInfoDTO;
import com.example.demo.Mapper.videoInfoMapper;

@Service
public class videoInfoService {
	@Autowired
	private videoInfoMapper vim;
	
	int addVInfo(videoInfoDTO info) {
		return vim.addVInfo(info);
	}
	int deleteInfo(videoInfoDTO info) {
		return vim.deleteInfo(info);
	}
	videoInfoDTO selectVideoInfo(String video_code) {
		return vim.selectVideoInfo(video_code);
	}
	ArrayList<videoInfoDTO> allLiveList(){
		return vim.allLiveList();
	}
	ArrayList<videoInfoDTO> liveList(){
		return vim.liveList();
	}
}
