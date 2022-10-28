package com.example.demo.Mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface favoriteMapper {
	ArrayList<String> listfollows(String userid);
	ArrayList<String> allfollows(String userid);
	int addfollow(String userid, String streamer);
	int deletefollow(String userid, String streamer);
}
