package com.example.demo.DAO;

import org.apache.ibatis.annotations.Mapper;
import com.example.demo.DTO.web_userDTO;

@Mapper
public interface web_userDAO {
	public boolean createUser(web_userDTO wuser) throws Exception; // 유저생성
	public web_userDTO getUserInfo(String uuid) throws Exception; // 유저 찾기
	public boolean updateUserInfo(web_userDTO wuser) throws Exception; // 유저 수정
	public boolean deleteUserInfo(web_userDTO wuser) throws Exception; // 유저 삭제
}
