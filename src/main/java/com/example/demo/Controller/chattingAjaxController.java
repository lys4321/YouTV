package com.example.demo.Controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DTO.video_chatDTO;
import com.example.demo.Mapper.chattingMapper;
import com.example.demo.Repos.StreamChatRoomRepo;

@Controller
@ResponseBody
public class chattingAjaxController {
	@Autowired 
	private chattingMapper cm;
	
	@Autowired
	private final StreamChatRoomRepo streamChatRoomRepo = new StreamChatRoomRepo();
	
	@RequestMapping(value="/ajax/addChat", method = {RequestMethod.GET})
	public void addChat(HttpServletRequest request) {
		String userid = request.getParameter("userid");
		String video_code = request.getParameter("video_code");
		String chat = request.getParameter("chat");
		String chat_status = request.getParameter("chat_status");
		String chatDate = request.getParameter("chatDate");
		
		video_chatDTO chatting = null;
		
		if("true".equals(chat_status)) {
			chatting = 
					new video_chatDTO(userid, chat, video_code, true, chatDate);
			System.out.println(chatting);
		}else {
			chatting = 
					new video_chatDTO(userid, chat, video_code, false, chatDate);
			System.out.println(chatting);
		}
				
		cm.addChatting(chatting);
	}
	
	@RequestMapping(value="/ajax/queryHistoryList", method = {RequestMethod.GET})
	public List<video_chatDTO> queryHistoryList(HttpServletRequest request) {
		String code = request.getParameter("code");
		System.out.println(code);
		String userid = request.getParameter("userid");
		System.out.println(userid);
		List<video_chatDTO> arr = cm.searchByIdCode(code, userid);
		System.out.println(arr);
		
		return arr;
	}
	
	@RequestMapping(value="/ajax/recordChat", method = {RequestMethod.GET})
	public List<video_chatDTO> recordChat(HttpServletRequest request) {
		String code = request.getParameter("video_code");
		List<video_chatDTO> arr = cm.searchByCode(code);
		System.out.println("[?????????]"+arr);
		
		return arr;
	}
	
	
	@RequestMapping(value="/ajax/inRoom", method = {RequestMethod.POST})
	public void inRoom(HttpServletRequest request) {
		String code = request.getParameter("Vcode");
		System.out.println(code);
		String userid = request.getParameter("Id");
		System.out.println(userid);
		
		//streamChatRoomRepo.findRoomById(code).addGuestes(userid);
	}
}
