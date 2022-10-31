package com.example.demo.Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DTO.videoDTO;
import com.example.demo.DTO.videoInfoDTO;
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
}
