package com.example.demo.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DTO.videoDTO;
import com.example.demo.DTO.videoInfoDTO;
import com.example.demo.Mapper.videoInfoMapper;
import com.example.demo.Mapper.video_mapper;
import com.example.demo.Repos.StreamChatRoomRepo;

@Controller
@ResponseBody
public class LiveVideoAjaxController {
	@Autowired
	private videoInfoMapper vim;
	@Autowired
	private StreamChatRoomRepo streamChatRoomRepo = new StreamChatRoomRepo();
	
	//생방송 시작 시 정보를 저장하는 메소드
	@RequestMapping(value="/Ajax/Live/Create_Stream", method = {RequestMethod.POST})
	public boolean LiveStreamCreate(HttpServletRequest request) {
		try {
			JSONObject info = new JSONObject(request.getParameter("Streaming"));
			String video_code = (String) info.get("video_code");
			String streamer_id = info.get("streamer_id").toString();
			String title = (String) info.get("title");
			String video_date = (String) info.get("video_date");
			String userFrofile = null;/////////
			System.out.println(info.get("live_session"));
			String live_session = Long.toString((Long)info.get("live_session"));
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = formatter.parse(video_date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			videoInfoDTO video = 
					new videoInfoDTO(video_code, streamer_id, title, userFrofile, live_session, video_date);
			vim.addVInfo(video);
			
			streamChatRoomRepo.createRoom(video_code);
			
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	//생방송 등러갈 시 정보를 저장하는 메소드
		/*@RequestMapping(value="/Ajax/Live/Join_Stream", method = {RequestMethod.POST})
		public String JoinLiveSession(HttpServletRequest request) {
			String video_code = request.getParameter(null)
			return ;
		}*/
}
