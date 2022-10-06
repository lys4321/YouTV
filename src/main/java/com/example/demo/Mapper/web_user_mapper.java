package com.example.demo.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.DTO.web_userDTO;

@Mapper
public interface web_user_mapper {
	ArrayList<web_userDTO> AllUserList();// 모든 유저 목록
	String SearchUser(@Param("uuid") String uuid);// 중복체크를 위함
	web_userDTO reSearchUser(@Param("name") String name, @Param("pnum") String pnum);
	web_userDTO UserLogin(@Param("uuid") String uuid, @Param("uupw") String uupw);// 로그인
	int UserUpdate(web_userDTO user);// 유저 수정
	int UserDelete(web_userDTO user);// 유저 삭제
	int UserCreate(web_userDTO user);// 유저 생성
}
