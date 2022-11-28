package com.example.demo.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.example.demo.Mapper.web_user_mapper;
import com.example.demo.Repos.StreamChatRoom;
import com.example.demo.Repos.StreamChatRoomRepo;

@Controller
@ResponseBody
public class LiveVideoAjaxController {
	@Autowired
	private videoInfoMapper vim;
	@Autowired
	private web_user_mapper wum;
	@Autowired
	private video_mapper vm;
	
	private StreamChatRoomRepo streamChatRoomRepo = new StreamChatRoomRepo();
	
	//생방송 시작 시 정보를 저장하는 메소드
	@RequestMapping(value="/Ajax/Live/Create_Stream", method = {RequestMethod.POST})
	public boolean LiveStreamCreate(HttpServletRequest request) {
		System.out.println("시자악~~~~~~~~~~");
		
		try {
			JSONObject info = new JSONObject(request.getParameter("Streaming"));
			String video_code = (String) info.get("video_code");
			String streamer_id = info.get("streamer_id").toString();
			String title = (String) info.get("title");
			String video_date = (String) info.get("video_date");
			String userFrofile = null;
			System.out.println(info.get("live_session"));
			String live_session = Long.toString((Long)info.get("live_session"));
			String create_session = Long.toString((Long)info.get("create_session"));
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				userFrofile = wum.SearchUserProfile(streamer_id);
				date = formatter.parse(video_date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			videoInfoDTO video = 
					new videoInfoDTO(video_code, streamer_id, title, userFrofile, live_session, video_date, create_session);
			vim.addVInfo(video);
			
			StreamChatRoom room = streamChatRoomRepo.createRoom(video_code);
			System.out.println("[ "+room+" ] :"+ room);
			
			
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	@RequestMapping(value="/ajax/deleteLiveStreaming", method = {RequestMethod.GET})
	public void deleteLiveStreaming(HttpServletRequest request) {
		String code = request.getParameter("code");
		System.out.println("코드는 : "+ code);
		vim.deleteInfo(code);
		streamChatRoomRepo.deleteRoom(code);
		System.out.println("삭제된 : "+ code);
	}
	
	@RequestMapping(value="/ajax/getUserList", method = {RequestMethod.GET})
	public ArrayList<String> usersinStreaming(HttpServletRequest request) {
		System.out.println("request : "+ request);
		String code = request.getParameter("code");
		ArrayList<String> arr = new ArrayList<String>();
		ArrayList<String> guestes = streamChatRoomRepo.getLists(code);
		System.out.println("[arr1] : "+ guestes);
		for(int i=0; i<guestes.size(); i++) {
			arr.add(guestes.get(i));
		}
		System.out.println("[arr1] : "+ arr);
		return arr;
	}
	
	@RequestMapping(value="/ajax/queryUserList", method = {RequestMethod.GET})
	public ArrayList<String> queryUserList(HttpServletRequest request) {
		String code = request.getParameter("code");
		String query = request.getParameter("query");
		ArrayList<String> arr = streamChatRoomRepo.getQueryLists(code, query);
		System.out.println("[arr2] : "+ arr);
		return arr;
	}
	
	@RequestMapping(value="/ajax/recordLiveStreaming", method = {RequestMethod.POST})
	public void recordLiveStreaming(HttpServletRequest request) {
		String video_code = request.getParameter("video_code");
		String streamer_id = request.getParameter("streamer_id");
		String title = request.getParameter("title");
		
		System.out.println("[ 저장 여부 확인 중 ] : "+ video_code + streamer_id + title);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String ndate = formatter.format(date);
		
		String publicPath = "C:\\YTVrepo";
		String thumbnailPath = "\\thumbnail\\";
		
		String thumbnailName = video_code + "_thumb.png";
		
		
		String totalPath = publicPath + thumbnailPath + thumbnailName;
		
		System.out.println("["+ video_code +"]"+"["+ streamer_id +"]"+"["+ title +"]"+"["+ ndate +"]");
		
		videoDTO vDTO = new videoDTO(video_code, streamer_id, title, ndate, totalPath);
		System.out.println("[ 객체객체 비디오 ] : "+vDTO);
		vm.addvideo(vDTO);
		
	}
	
}
