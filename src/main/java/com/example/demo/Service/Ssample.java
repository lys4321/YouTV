package com.example.demo.Service;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Ssample {
	
	ArrayList<String> selectTest();
}
