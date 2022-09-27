package com.example.demo.Service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.web_userDTO;
import com.example.demo.Mapper.web_user_mapper;

@Service
public class Web_User_Service {
	@Autowired
	private web_user_mapper userMapper;
	
	ArrayList<web_userDTO> AllUserList(){
		return userMapper.AllUserList();
	}
	
	String SearchUser(@Param("_uuid") String uuid) {
		return userMapper.SearchUser(uuid);
	}
	
	web_userDTO reSearchUser(@Param("name") String name, @Param("pnum") String pnum){
		return userMapper.reSearchUser(name, pnum);
	}
	
	web_userDTO UserLogin(@Param("_uuid") String uuid, @Param("_uupw") String uupw) {
		return userMapper.UserLogin(uuid, uupw);
	}
	
	int UserUpdate(web_userDTO user) {
		return userMapper.UserUpdate(user);
	}
	
	int UserDelete(web_userDTO user) {
		return userMapper.UserDelete(user);
	}
	
	int UserCreate(web_userDTO user) {
		return userMapper.UserCreate(user);
	}
}
