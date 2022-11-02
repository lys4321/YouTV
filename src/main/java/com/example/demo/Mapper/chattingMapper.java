package com.example.demo.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.example.demo.DTO.video_chatDTO;

@Mapper
public interface chattingMapper {
	int addChatting(video_chatDTO chat);
	List<video_chatDTO> searchByCode(@Param(value="code")String code);
	List<video_chatDTO> searchById(@Param(value="userid")String userid);
}
