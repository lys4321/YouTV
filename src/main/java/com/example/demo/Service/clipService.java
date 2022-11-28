package com.example.demo.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.clipDTO;
import com.example.demo.Mapper.clipMapper;

@Service
public class clipService {
	@Autowired
	clipMapper clm;
	
	int addClip(clipDTO clip) {
		return clm.addClip(clip);
	}
	ArrayList<clipDTO> allClip(){
		return clm.allClip();
	}
	ArrayList<clipDTO> listClip(){
		return clm.listClip();
	}
	clipDTO selectClip(String clipCode) {
		return clm.selectClip(clipCode);
	}
	
	ArrayList<clipDTO> searchClipBySearch(String search){
		return clm.searchClipBySearch(search);
	}
}
