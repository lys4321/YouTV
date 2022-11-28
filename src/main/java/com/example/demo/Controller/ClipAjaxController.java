package com.example.demo.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.demo.DTO.clipDTO;
import com.example.demo.DTO.web_userDTO;
import com.example.demo.Mapper.clipMapper;

@Controller
@ResponseBody
public class ClipAjaxController {
	@Autowired
	private clipMapper clm;
	
	@RequestMapping(value="/ajax/getMainClip", method= {RequestMethod.GET})
	public HashMap<String, Object> getMainClip(){
		ArrayList<clipDTO> arr = clm.listClip();
		ArrayList<byte[]> img = new ArrayList<byte[]>();
		for(int i=0; i<arr.size(); i++) {
			if(i==5) {
				break;
			}
			
			File file = new File("C:\\YTVrepo\\thumbnail\\"+ arr.get(i).getClip_code() +".png");
			try {
				img.add(FileCopyUtils.copyToByteArray(file));
			}catch(Exception e) {
				e.printStackTrace();
			}
		
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("cliplist", arr);
		System.out.println(map);
		map.put("clipimg", img);
		
		return map;
	}
	
	@RequestMapping(value="/ajax/allgetMainClip", method= {RequestMethod.GET})
	public HashMap<String, Object> allgetMainClip(){
		ArrayList<clipDTO> arr = clm.allClip();
		ArrayList<byte[]> img = new ArrayList<byte[]>();
		for(int i=0; i<arr.size(); i++) {
			File file = new File("C:\\YTVrepo\\thumbnail\\"+ arr.get(i).getClip_code() +".png");
			try {
				img.add(FileCopyUtils.copyToByteArray(file));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("List", arr);
		System.out.println(map);
		map.put("Thumbnail", img);
		
		return map;
	}
	
	@RequestMapping(value="/ajax/uploadClip", method= {RequestMethod.POST})
	public boolean uploadClip(MultipartHttpServletRequest request) {
		Iterator it = request.getFileNames();
		System.out.println("클립테스트 : "+it);
		MultipartFile mf = request.getFile((String) it.next());
		System.out.println(mf);
		
		String path = "C:\\YTVrepo\\clip\\";
		File file = new File(path + mf.getName()+".webm");
		
		String URL = path + mf.getName()+".webm";
		System.out.println("2번 : "+URL);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		try {
			String ndate = formatter.format(date);
			
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(mf.getBytes());
			fos.close();
			
			clipDTO clip = new clipDTO(request.getParameter("clipCode"), 
					request.getParameter("video_code"), 
					request.getParameter("makerid"),
					ndate,
					request.getParameter("filetitle"),
					URL);
			
			System.out.println(clip);
			
			clm.addClip(clip);
			
			Runtime run = Runtime.getRuntime();
			String command = "C:/ffmpeg-5.1.1-full_build/bin/ffmpeg.exe -t 00:00:01 -i " + URL + " -vcodec png -vframes 1 C:\\YTVrepo\\thumbnail\\"+ request.getParameter("clipCode") +".png";
			System.out.println(command);
			try {
				run.exec("cmd.exe chcp 65001");
				run.exec(command);
			}catch(Exception e) {
				System.out.println("error : " + e.getMessage());
				e.printStackTrace();
			}
			
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
