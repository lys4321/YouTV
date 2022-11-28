package com.example.demo.Mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.DTO.videoDTO;

@Mapper
public interface video_mapper {
	int addvideo(videoDTO video); // 녹화 방송 추가
	ArrayList<videoDTO> allRecordList(); // 녹화방송 목록 가져오기
	ArrayList<videoDTO> recordList(); // 녹화방송 목록 가져오기 5개
	videoDTO selectVideo(String video_code); // 방송 선택 시 정보 전달
}
