package com.example.demo.Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DTO.clipDTO;
import com.example.demo.DTO.videoDTO;
import com.example.demo.DTO.videoInfoDTO;
import com.example.demo.DTO.web_userDTO;
import com.example.demo.Mapper.clipMapper;
import com.example.demo.Mapper.favoriteMapper;
import com.example.demo.Mapper.videoInfoMapper;
import com.example.demo.Mapper.video_mapper;
import com.example.demo.Mapper.web_user_mapper;

@Controller
@ResponseBody 
public class Main_Lists_Service {
	@Autowired
	private web_user_mapper wum;
	@Autowired
	private video_mapper vmap;
	@Autowired
	private videoInfoMapper vim;
	@Autowired
	private favoriteMapper fm;
	@Autowired
	private clipMapper clm;
	
	@RequestMapping(value="/ajax/main_followlist", method= {RequestMethod.GET})
	public HashMap<String, Object> main_followlist(@RequestParam(value="userid") String userid) {
		System.out.println(userid);
		ArrayList<String> followlist = fm.listfollows(userid);
		System.out.println("메인화면의 팔로우 리스트 정보");
		ArrayList<byte[]> arr = new ArrayList<byte[]>();
		ArrayList<videoInfoDTO> vinfo = new ArrayList<videoInfoDTO>();
		for(int i=0; i<followlist.size(); i++) {
			System.out.println("첫번쨰 : "+followlist.get(i));
			
			videoInfoDTO vin = vim.selectById(followlist.get(i));
			
			System.out.println("두번쨰 : "+vin);
			
			if(vin == null) {
				continue;
			}
			
			if(vin.getProfile() == null) {
				continue;
			}
			
			vinfo.add(vin);
			
			File file = new File(vin.getProfile());
			
			try {
				arr.add(FileCopyUtils.copyToByteArray(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("FollowList", vinfo);
		map.put("FollowThumbnail", arr);
		
		System.out.println("마지막 : "+map);
		
		return map;
	}
	
	@RequestMapping(value="/ajax/all_main_followlist", method= {RequestMethod.GET})
	public HashMap<String, Object> all_main_followlist(@RequestParam(value="userid") String userid) {
		
		ArrayList<String> followlist = fm.allfollows(userid);
		System.out.println("메인화면의 모든 팔로우 리스트 정보");
		ArrayList<byte[]> arr = new ArrayList<byte[]>();
		ArrayList<videoInfoDTO> vinfo = new ArrayList<videoInfoDTO>();
		for(int i=0; i<followlist.size(); i++) {
			System.out.println("첫번쨰 : "+followlist.get(i));
			
			videoInfoDTO vin = vim.selectById(followlist.get(i));
			
			System.out.println("두번쨰 : "+vin);
			
			if(vin == null) {
				continue;
			}
			
			if(vin.getProfile() == null) {
				continue;
			}
			
			vinfo.add(vin);
			
			File file = new File(vin.getProfile());
			
			try {
				arr.add(FileCopyUtils.copyToByteArray(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("List", vinfo);
		map.put("Thumbnail", arr);
		
		System.out.println("마지막 : "+map);
		
		return map;
	}
	
	@RequestMapping(value="/ajax/main_follows", method= {RequestMethod.GET})
	public HashMap<String, Object> main_follows(@RequestParam(value="userid") String userid) {
		ArrayList<String> follows = fm.allfollows(userid);
		System.out.println("팔로우 목록 화면 정보");
		ArrayList<byte[]> arr = new ArrayList<byte[]>();
		ArrayList<videoInfoDTO> vinfo = new ArrayList<videoInfoDTO>();
		for(int i=0; i<follows.size(); i++) {
			videoInfoDTO vin = vim.selectById(follows.get(i));
			if(vin == null) {
				continue;
			}
			
			if(vin.getProfile() == null) {
				continue;
			}
			
			vinfo.add(vin);
			
			File file = new File(vin.getProfile());
			
			try {
				arr.add(FileCopyUtils.copyToByteArray(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("follows", vinfo);
		map.put("FollowThumbnail", arr);
		
		return map;
	}
	
	@RequestMapping(value="/ajax/main_livelist", method= {RequestMethod.GET})
	public HashMap<String, Object> main_livelist() {
		ArrayList<videoInfoDTO> livelist = vim.liveList();
		System.out.println("메인화면의 생방송 리스트 정보");
		ArrayList<byte[]> arr = new ArrayList<byte[]>();
		for(int i=0; i<livelist.size(); i++) {
			System.out.println(livelist.get(i));
			if(livelist.get(i).getProfile() == null) {
				continue;
			}
			File file = new File(livelist.get(i).getProfile());
			
			try {
				
				arr.add(FileCopyUtils.copyToByteArray(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
			/*
			 * File file = new File(livelist.get(i).getProfile()); try {
			 * arr.add(FileCopyUtils.copyToByteArray(file));////////////////////// } catch
			 * (IOException e) { e.printStackTrace(); }
			 */
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("LiveList", livelist);
		map.put("LiveThumbnail", arr);
		
		return map;
	}
	
	@RequestMapping(value="/ajax/all_main_livelist", method= {RequestMethod.GET})
	public HashMap<String, Object> all_main_livelist() {
		ArrayList<videoInfoDTO> livelist = vim.allLiveList();
		System.out.println("메인화면의 모든 생방송 리스트 정보");
		ArrayList<byte[]> arr = new ArrayList<byte[]>();
		for(int i=0; i<livelist.size(); i++) {
			System.out.println(livelist.get(i));
			if(livelist.get(i).getProfile() == null) {
				continue;
			}
			File file = new File(livelist.get(i).getProfile());
			
			try {
				
				arr.add(FileCopyUtils.copyToByteArray(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
			/*
			 * File file = new File(livelist.get(i).getProfile()); try {
			 * arr.add(FileCopyUtils.copyToByteArray(file));////////////////////// } catch
			 * (IOException e) { e.printStackTrace(); }
			 */
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("List", livelist);
		map.put("Thumbnail", arr);
		
		return map;
	}
	
	@RequestMapping(value="/ajax/main_recordlist", method= {RequestMethod.GET})
	public HashMap<String, Object> main_recordlist() {
		ArrayList<videoDTO> recordlist = vmap.recordList();
		
		System.out.println("메인화면의 녹화방송 리스트 정보");
		ArrayList<byte[]> arr = new ArrayList<byte[]>();
		for(int i=0; i<recordlist.size(); i++) {
			File file = new File(recordlist.get(i).getThumbnail());
			try {
				arr.add(FileCopyUtils.copyToByteArray(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("RecordList", recordlist);
		map.put("RecordThumbnail", arr);
		
		return map;
	}
	
	@RequestMapping(value="/ajax/all_main_recordlist", method= {RequestMethod.POST})
	public HashMap<String, Object> all_main_recordlist(
	@RequestParam(value="sessions[]", required=false) String[] sessions, 
	@RequestParam(value="codes[]", required=false) String[] codes) {

		ArrayList<videoDTO> recordlist = new ArrayList<videoDTO>();
		
		ArrayList<String> result = new ArrayList<String>();
		
		ArrayList<byte[]> arr = new ArrayList<byte[]>();
		for(int i=0; i<codes.length; i++) {
			
			videoDTO video = vmap.selectVideo(codes[i]);
			
			
			if(video == null) {
				continue;
			}
			
			File file = new File(wum.SearchUserProfile(video.getStreamer_id()));
			try {
				arr.add(FileCopyUtils.copyToByteArray(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
			recordlist.add(video);
			result.add(sessions[i]);
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		
		map.put("List", recordlist);
		map.put("session", result);
		map.put("Thumbnail", arr);
		
		
		return map;
	}
	
	@RequestMapping(value="/ajax/searchUser", method= {RequestMethod.GET})
	public HashMap<String, Object> searchUser(HttpServletRequest request) {
		ArrayList<web_userDTO> userlist = wum.searchUserBySearch(request.getParameter("search"));
		System.out.println("메인화면의 녹화방송 리스트 정보"); 
		ArrayList<byte[]> arr = new ArrayList<byte[]>(); 
		for(int i=0; i<userlist.size(); i++) { 
			File file = new
					File(userlist.get(i).getImage_url()); 
			try {
				arr.add(FileCopyUtils.copyToByteArray(file)); 
			} catch (IOException e) {
				e.printStackTrace(); 
			} 
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		System.out.println(userlist); 
		map.put("UserList", userlist); 
		map.put("UserThumbnail", arr);
		return map;
	}
	
	@RequestMapping(value="/ajax/searchLive", method= {RequestMethod.GET})
	public HashMap<String, Object> searchLive(HttpServletRequest request) {
		ArrayList<videoInfoDTO> livelist = vim.searchLiveBySearch(request.getParameter("search")); 
		ArrayList<byte[]> arr = new ArrayList<byte[]>(); 
		for(int i=0; i<livelist.size(); i++) { 
			File file = new
					File(livelist.get(i).getProfile()); 
			try {
				arr.add(FileCopyUtils.copyToByteArray(file)); 
			} catch (IOException e) {
				e.printStackTrace(); 
			} 
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		System.out.println(livelist); 
		map.put("liveList", livelist); 
		map.put("liveThumbnail", arr);
		return map;
	}
	@RequestMapping(value="/ajax/searchClip", method= {RequestMethod.GET})
	public HashMap<String, Object> searchClips(HttpServletRequest request) {
		ArrayList<clipDTO> cliplist = clm.searchClipBySearch(request.getParameter("search"));
		ArrayList<byte[]> arr = new ArrayList<byte[]>(); 
		for(int i=0; i<cliplist.size(); i++) { 
			File file = new
					File("C:\\YTVrepo\\thumbnail\\"+cliplist.get(i).getClip_code()+".png"); 
			try {
				arr.add(FileCopyUtils.copyToByteArray(file)); 
			} catch (IOException e) {
				e.printStackTrace(); 
			} 
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		System.out.println(cliplist); 
		map.put("cliplist", cliplist); 
		map.put("clipThumbnail", arr);
		System.out.println("값 확인"+map);
		return map;
	}
	
	@RequestMapping(value="/ajax/searchrecordBysearch", method= {RequestMethod.POST})
	public HashMap<String, Object> searchrecordBysearch(
	@RequestParam(value="sessions[]", required=false) String[] sessions, 
	@RequestParam(value="codes[]", required=false) String[] codes,
	@RequestParam(value="search", required=false) String search) {
		
		System.out.println("[코드]"+codes);
		System.out.println("[세션]"+sessions);
		System.out.println("[서치]"+search);
		
		

		ArrayList<videoDTO> recordlist = new ArrayList<videoDTO>();
		
		ArrayList<String> result = new ArrayList<String>();
		
		ArrayList<byte[]> arr = new ArrayList<byte[]>();
		for(int i=0; i<codes.length; i++) {
			
			videoDTO video = vmap.selectVideo(codes[i]);
			
			
			if(video == null) {
				continue;
			}
			
			File file = new File(wum.SearchUserProfile(video.getStreamer_id()));
			try {
				arr.add(FileCopyUtils.copyToByteArray(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
			recordlist.add(video);
			result.add(sessions[i]);
		}
		
		ArrayList<videoDTO> Recordlist = new ArrayList<videoDTO>();
		
		ArrayList<String> Result = new ArrayList<String>();
		
		ArrayList<byte[]> Arr = new ArrayList<byte[]>();
		
		for(int i=0; i<recordlist.size(); i++) {
			if(recordlist.get(i).getStreamer_id().contains(search)) {
				Recordlist.add(recordlist.get(i));
				Result.add(result.get(i));
				Arr.add(arr.get(i));
			}
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		
		map.put("List", Recordlist);
		map.put("session", Result);
		map.put("Thumbnail", Arr);
		
		
		return map;
	}
}
