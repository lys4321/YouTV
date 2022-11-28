package com.example.demo.Mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.DTO.chat_banDTO;

@Mapper
public interface chatBanMapper {
	int addBanUser(chat_banDTO banDTO);
	int cancelBan(chat_banDTO banDTO);
	ArrayList<chat_banDTO> allBanbyId(@Param("streamerId")String streamerId);
}
