package com.example.demo.Controller;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.DTO.videoDTO;
import com.example.demo.DTO.web_userDTO;
import com.example.demo.Mapper.video_mapper;

@Controller
public class VideoController {
	@Autowired
	private video_mapper vmap;
	
	public void thumbnail() {
		Runtime run = Runtime.getRuntime();
		String path = "C:/VideoFolder/out.mp4"; //db 에서 가져오는걸로
		String command = "C:/ffmpeg-5.1.1-full_build/bin/ffmpeg.exe -t 00:00:05 -i " + path + " -vcodec png -vframes 1 C:/result.png";
		System.out.println(command);
		try {
			run.exec("cmd.exe chcp 65001");
			run.exec(command);
		}catch(Exception e) {
			System.out.println("error : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/Test", method = {RequestMethod.POST, RequestMethod.GET})
	public String broadList(/*web_userDTO user,*/ Model model) {
		ArrayList<videoDTO> livelist = vmap.liveList();
		ArrayList<videoDTO> recordlist = vmap.recordList();
		//thumbnail();
		/*
		if(user != null) {
			ArrayList<videoDTO> followlist = ;
			ArrayList<videoDTO> cliplist =
		}
		*/
		model.addAttribute("livelist", livelist);
		model.addAttribute("recordlist", recordlist);
		return "Test";
	}
	
	
	public String thumbnailList(/*web_userDTO user,*/ Model model) {
		ArrayList<videoDTO> livelist = vmap.liveList();
		ArrayList<videoDTO> recordlist = vmap.recordList();
		
		
		return "Test :: ";
	}
}
