package com.example.demo.Mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.DTO.favoritesDTO;

@Mapper
public interface favoriteMapper {
	ArrayList<String> listfollows(String userid);
	ArrayList<String> allfollows(String userid);
	int addfollow(favoritesDTO fDTO);
	int deletefollow(@Param("uid")String userid, @Param("sid")String streamer);
	favoritesDTO findByIds(@Param("sid") String streamerid, @Param("uid") String userid);
}
