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

@Controller
@ResponseBody
public class chattingAjaxController {
	@Autowired 
	private chattingMapper cm;
	
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
}
