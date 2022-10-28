package com.example.demo.Mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.DTO.videoDTO;
import com.example.demo.DTO.videoInfoDTO;

@Mapper
public interface videoInfoMapper {
	int addVInfo(videoInfoDTO info); //방송정보 추가
	int deleteInfo(videoInfoDTO info); // 생방송 정보 삭제
	videoInfoDTO selectVideoInfo(String video_code); // 방송 선택 시 정보 전달
	ArrayList<videoInfoDTO> allLiveList(); // 생방송 목록 가져오기
	ArrayList<videoInfoDTO> liveList(); // 생방송 목록 가져오기 5개
	videoInfoDTO selectById(String user_id);
}
