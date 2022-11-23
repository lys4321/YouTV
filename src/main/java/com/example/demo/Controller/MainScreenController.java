package com.example.demo.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.DTO.videoDTO;
import com.example.demo.DTO.videoInfoDTO;
import com.example.demo.Mapper.clipMapper;
import com.example.demo.Mapper.videoInfoMapper;
import com.example.demo.Mapper.video_mapper;

@Controller
public class MainScreenController {
	@Autowired
	private video_mapper vmap;
	@Autowired
	private videoInfoMapper vim;
	@Autowired
	private clipMapper clm;
	
	///YouTV/MainScreen
	@RequestMapping(value="/YouTV/MainScreen", method= {RequestMethod.POST, RequestMethod.GET})
    public String MainScreen(Model model){
		ArrayList<videoInfoDTO> livelist = vim.liveList();
		ArrayList<videoDTO> recordlist = vmap.recordList();
		System.out.println("메인화면 시작");
		model.addAttribute("livelist", livelist);
		model.addAttribute("recordlist", recordlist);
		
        return "MainScreen";
	}
	
	@RequestMapping(value="/YouTV/AllList", method= {RequestMethod.POST, RequestMethod.GET})
    public String AllList(@RequestParam(value="type") String type, Model model){
		model.addAttribute("type", type);
        return "allLists";
	}
    
    @RequestMapping(value="/YouTV/SearchScreen", method= {RequestMethod.POST, RequestMethod.GET})
    public String SearchScreen(@RequestParam(value="search") String search, Model model) {
    	model.addAttribute("search", search);
    	return "SearchScreen";
    }
}
