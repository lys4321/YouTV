package com.example.demo.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.demo.DTO.web_userDTO;
import com.example.demo.Mapper.web_user_mapper;
import com.example.demo.Service.SHA256_Service;

@Controller
@ResponseBody
public class Account_Service {
	@Autowired
	private web_user_mapper wum;
	private SHA256_Service ShaS = new SHA256_Service();
	
	@RequestMapping(value="/ajax/AccountExistCheck", method = {RequestMethod.POST})
	public boolean AccountExistCheck(HttpServletRequest request) {
		String insert_id = request.getParameter("id");
		String insert_pw = request.getParameter("password");
		web_userDTO checksum = wum.UserLogin(insert_id, ShaS.encrypt(insert_pw));
		if(checksum != null) {
			System.out.println("입력된 ID : ["+insert_id+"], 입력된 PW : ["+ShaS.encrypt(insert_pw)+"] 결과 : [로그인 성공]");
			return true;
		}else {
			System.out.println("입력된 ID : ["+insert_id+"], 입력된 PW : ["+ShaS.encrypt(insert_pw)+"] 결과 : [로그인 실패]");
			return false;
		}
	}
	
	@RequestMapping(value="/ajax/AccountDupCheck", method = {RequestMethod.POST})
	public boolean AccountDupCheck(HttpServletRequest request) {
		String insert_id = request.getParameter("inid");
		System.out.println("ID 중복 체크1234 : "+insert_id);
		String out_id = wum.SearchUser(insert_id);
		System.out.println("ID 중복 체크 : "+out_id);
		if(out_id != null) {
			System.out.println("이미 사용중인 ID ID : "+out_id);
			return false;
		}else {
			System.out.println("사용이 가능한 ID : "+out_id);
			return true;
		}
	}
	
	@RequestMapping(value="/ajax/AccountCreate", method = {RequestMethod.POST})
	public boolean AccountCreate(HttpServletRequest request) {
		String insert_id = request.getParameter("inid");
		String insert_pw = ShaS.encrypt(request.getParameter("inpassword"));
		String insert_name = request.getParameter("inname");
		String insert_pnum = request.getParameter("inpnum");
		web_userDTO user = new web_userDTO(insert_id, insert_pw, insert_name, insert_pnum, null);
		try {
			System.out.println("생성하려는 유저의 정보 : ["+user+"]");
			wum.UserCreate(user);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	@RequestMapping(value="/ajax/AccountSearch", method = {RequestMethod.POST})
	public ArrayList<String> AccountSearch(HttpServletRequest request) {
		String name = request.getParameter("inname");
		String pnum = request.getParameter("inpnum");
		web_userDTO result = wum.reSearchUser(name, pnum);
		ArrayList<String> arr = new ArrayList<String>();
		if(result != null) {
			String id = result.getUser_id();
			String num = "123456789";
			String update_pw = ShaS.encrypt(num); 
			web_userDTO update = new web_userDTO(result.getUser_id(), update_pw, result.getUser_name(), result.getUser_pnum(), result.getImage_url());
			wum.UserUpdate(update);
			arr.add(id);
			arr.add(num);
		}
		return arr;
	}
	
	@RequestMapping(value="/ajax/AccountUpdate", method = {RequestMethod.POST})
	public boolean AccountUpdate(HttpServletRequest request) {
		String insert_id = request.getParameter("inid");
		String insert_pw = ShaS.encrypt(request.getParameter("newpassword"));
		web_userDTO user = new web_userDTO(insert_id, insert_pw, null, null, null);
		try {
			System.out.println("수정하려는 유저의 정보 : ["+user+"]");
			wum.UserUpdate(user);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	@RequestMapping(value="/ajax/AccountUpdateImg", method = {RequestMethod.POST})
	public boolean AccountUpdateImg(MultipartHttpServletRequest request) {
		//List<MultipartFile> filelist = request.getFiles("img_file");
		Iterator it = request.getFileNames();
		System.out.println(it);
		MultipartFile  mf = request.getFile((String) it.next());
		System.out.println(mf);
		
		System.out.println(mf.getName());
		
		String path = "C:\\YTVrepo\\profile\\";
		
		String uid = request.getParameter("name");
		System.out.println("1번 : "+request);
		
		File file = new File(path + mf.getName()+".png");
		
		String URL = path + mf.getName()+".png";
		System.out.println("2번 : "+URL);
		
		try {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(mf.getBytes());
			fos.close();
			
			web_userDTO user = new web_userDTO(mf.getName(), null, null, null, URL);
			wum.UserUpdateimg(user);
			
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
		/*MultipartFile mfile = request.getFile("img_file");
		System.out.println("결과 : "+ mfile);
		*/
		//MultipartFile file = request.get;
		/*
		 * web_userDTO user = new web_userDTO(insert_id, null, null, null, null); try {
		 * System.out.println("수정하려는 유저의 정보 : ["+user+"]"); wum.UserUpdate(user); return
		 * true; }catch(Exception e) { return false; }
		 */
	}
	
	@RequestMapping(value="/ajax/AccountDelete", method = {RequestMethod.POST})
	public boolean AccountDelete(HttpServletRequest request) {
		String insert_id = request.getParameter("userid");
		
		web_userDTO user = new web_userDTO(insert_id, null, null, null, null);
		try {
			System.out.println("삭제하려는 유저의 정보 : ["+insert_id+"]");
			wum.UserDelete(user);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
