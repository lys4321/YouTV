package com.example.demo.Mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.DTO.web_userDTO;

@Mapper
public interface web_user_mapper {
	ArrayList<web_userDTO> AllUserList();// 모든 유저 목록
	web_userDTO SearchUser(@Param("_uuid") String uuid);// 중복체크를 위함
	int UserLogin(@Param("_uuid") String uuid, @Param("_uupw") String uupw);// 로그인
	int UserUpdate(web_userDTO user);// 유저 수정
	int UserDelete(web_userDTO user);// 유저 삭제
	int UserCreate(web_userDTO user);// 유저 생성
}
