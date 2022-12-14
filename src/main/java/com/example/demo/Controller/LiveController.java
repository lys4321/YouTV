package com.example.demo.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.DTO.videoInfoDTO;
import com.example.demo.Mapper.videoInfoMapper;
import com.example.demo.Repos.StreamChatRoomRepo;

@Controller
public class LiveController {
	private final StreamChatRoomRepo streamChatRoomRepo = new StreamChatRoomRepo();
	
	@Autowired
	private videoInfoMapper vim;
	
	@RequestMapping(value="/Owner_Mode", method= {RequestMethod.GET})
	public String ownerMode() {
		return "LiveOwnerScreen";
	}
	
	@RequestMapping(value="/Guest_Mode", method= {RequestMethod.GET})
	public ModelAndView guestMode(@RequestParam(value="video_code", required=false) String video_code, @RequestParam(value="guestid", required=false) String guestid) {
		videoInfoDTO video = vim.selectVideoInfo(video_code);
		ModelAndView mav = new ModelAndView();
		
		videoInfoDTO viDTO = vim.selectVideoInfo(video_code);
		System.out.println("{오ㅗ오ㅗㅇ오ㅗㅇ오ㅗ오오오오오오오오오오오} : "+viDTO);
		
		mav.addObject("videoTitle", viDTO.getTitle());
		mav.setViewName("LiveGuestScreen");
		mav.addObject("video_code", video_code);
		mav.addObject("chatting_room", streamChatRoomRepo.findRoomById(video_code));
		System.out.println("[챗룸] : "+ streamChatRoomRepo.findRoomById(video_code));
		mav.addObject("catch_session", video.getCreate_session());
		System.out.println("[세션] : "+ video.getSession());
		
		
		return mav;
	}
	
	//ajax로 바꾸기
	@RequestMapping(value="/Ajax/Create_Room", method= {RequestMethod.POST})
	@ResponseBody
    public void create(HttpServletRequest request){
		streamChatRoomRepo.createRoom(request.getParameter("code"));
    }
	
	//ajax로 바꾸기
	@RequestMapping(value="/Ajax/Delete_Room", method= {RequestMethod.POST})
	@ResponseBody
    public void delete(HttpServletRequest request){
		String code = request.getParameter("code");
		streamChatRoomRepo.deleteRoom(code);
    }
	
	@RequestMapping(value="/Ajax/ChatRoom", method= {RequestMethod.GET})
	@ResponseBody
    public void getRoom(HttpServletRequest request, Model model){
		String code = request.getParameter("code");
    	model.addAttribute("room", streamChatRoomRepo.findRoomById(code));
    }
}
