package com.example.demo.DAO;

import java.util.ArrayList;

import com.example.demo.DTO.videoDTO;

public interface videoDAO {
	public ArrayList<videoDTO> getLiveVideoList(); // 생방송 목록 가져오기 
	public ArrayList<videoDTO> getRecordVideoList(); // 녹화방송 목록 가져오기
	public ArrayList<videoDTO> getFollowLiveList(String uuid); // 팔로우 방송 목록 가져오기
	public boolean createLive(videoDTO video); // 방송생성(정보추가)
	public boolean liveEnd(videoDTO video); // 방송 종료 후 상태 변경
	public boolean setVideoURL(String url); // 영상 경로 추가
}
