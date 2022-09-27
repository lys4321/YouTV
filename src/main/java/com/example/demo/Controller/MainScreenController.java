package com.example.demo.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.DTO.videoDTO;
import com.example.demo.Mapper.video_mapper;

@Controller
public class MainScreenController {
	@Autowired
	private video_mapper vmap;
	
	@RequestMapping(value="/YouTV/MainScreen", method= {RequestMethod.POST, RequestMethod.GET})
    public String MainScreen(Model model){
		ArrayList<videoDTO> livelist = vmap.liveList();
		ArrayList<videoDTO> recordlist = vmap.recordList();
		System.out.println("메인화면 시작");
		model.addAttribute("livelist", livelist);
		model.addAttribute("recordlist", recordlist);
		
        return "BootTest";
	}
}
