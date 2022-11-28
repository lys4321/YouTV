package com.example.demo.Mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.DTO.clipDTO;

@Mapper
public interface clipMapper {
	int addClip(clipDTO clip);
	ArrayList<clipDTO> allClip();
	ArrayList<clipDTO> listClip();
	clipDTO selectClip(String clipCode);
	
	ArrayList<clipDTO> searchClipBySearch(String search);
}
