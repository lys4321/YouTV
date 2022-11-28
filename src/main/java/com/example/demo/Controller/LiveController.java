package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.DTO.videoDTO;
import com.example.demo.DTO.videoInfoDTO;
import com.example.demo.Mapper.videoInfoMapper;
import com.example.demo.Mapper.video_mapper;
import com.example.demo.Service.Chatting_Service;

@Controller
public class LiveController {
	private Chatting_Service chattingService = new Chatting_Service();
	@Autowired
	private videoInfoMapper vim;
	
	@RequestMapping(value="/Owner_Mode", method= {RequestMethod.GET})
	public String ownerMode() {
		return "LiveOwnerScreen";
	}
	
	@RequestMapping(value="/Guest_Mode", method= {RequestMethod.GET})
	public ModelAndView guestMode(@RequestParam(value="video_code", required=false) String video_code) {
		videoInfoDTO video = vim.selectVideoInfo(video_code);
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("LiveGuestScreen");
		mav.addObject("chatting_room", chattingService.get_RoomByCode(video_code));
		mav.addObject("catch_session", video.getSession());
		
		return mav;
	}
	
	//ajax로 바꾸기
	@RequestMapping(value="/Ajax/Create_Room", method= {RequestMethod.POST})
    public void create(@RequestParam("code") String code){
		chattingService.create(code);
    }
	
	//ajax로 바꾸기
	@RequestMapping(value="/Ajax/Delete_Room", method= {RequestMethod.POST})
    public void delete(@RequestParam("del") String del){
		chattingService.delete(del);
    }
	
	
}
