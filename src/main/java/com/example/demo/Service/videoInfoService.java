package com.example.demo.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
	int deleteInfo(@Param(value="code")String code) {
		return vim.deleteInfo(code);
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
	videoInfoDTO selectById(String user_id) {
		return vim.selectById(user_id);
	}
	ArrayList<videoInfoDTO> searchLiveBySearch(String search){
		return vim.searchLiveBySearch(search);
	}
}
