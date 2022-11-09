package com.example.demo.Service;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.favoritesDTO;
import com.example.demo.Mapper.favoriteMapper;

@Service
public class favoriteService {
	@Autowired
	private favoriteMapper fm;
	
	ArrayList<String> listfollows(String userid){
		return fm.listfollows(userid);
	}
	
	ArrayList<String> allfollows(String userid){
		return fm.allfollows(userid);
	}
	
	int addfollow(favoritesDTO fDTO) {
		return fm.addfollow(fDTO);
	}
	
	int deletefollow(String userid, String streamer) {
		return fm.deletefollow(userid, streamer);
	}
	
	favoritesDTO findByIds(String streamerid, String userid) {
		return fm.findByIds(streamerid, userid);
	}
}
