package com.example.demo.Controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DTO.web_userDTO;
import com.example.demo.Mapper.web_user_mapper;
import com.example.demo.Service.SHA256_Service;

@Controller
@ResponseBody
public class Account_Service {
	@Autowired
	private web_user_mapper wum;
	@Autowired
	private SHA256_Service ShaS = new SHA256_Service();
	
	@RequestMapping(value="/ajax/AccountExistCheck", method = {RequestMethod.POST})
	public boolean AccountExistCheck(HttpServletRequest request) {
		String insert_id = request.getParameter("inid");
		String insert_pw = request.getParameter("inpassword");
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
}
