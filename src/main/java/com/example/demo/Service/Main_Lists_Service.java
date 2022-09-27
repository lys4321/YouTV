package com.example.demo.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DTO.videoDTO;
import com.example.demo.Mapper.video_mapper;

@Controller
@ResponseBody
public class Main_Lists_Service {
	@Autowired
	private video_mapper vmap;
	
	@RequestMapping(value="/ajax/main_livelist", method= {RequestMethod.GET})
	public HashMap<String, Object> main_livelist() {
		ArrayList<videoDTO> livelist = vmap.liveList();
		
		System.out.println("메인화면의 생방송 리스트 정보");
		ArrayList<byte[]> arr = new ArrayList<byte[]>();
		for(int i=0; i<livelist.size(); i++) {
			File file = new File(livelist.get(i).getThumbnail_url());
			try {
				arr.add(FileCopyUtils.copyToByteArray(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			File file = new File(recordlist.get(i).getThumbnail_url());
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
