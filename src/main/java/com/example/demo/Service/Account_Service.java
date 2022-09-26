package com.example.demo.Service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DTO.web_userDTO;
import com.example.demo.Mapper.web_user_mapper;

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
		//web_userDTO check_user = wum.SearchUser(insert_id);
		System.out.println(wum.SearchUser(insert_id).getUser_id());
		return true;
	}
	
	@RequestMapping(value="/ajax/AccountCreate", method = {RequestMethod.POST})
	public boolean AccountCreate(HttpServletRequest request) {
		String insert_id = request.getParameter("inid");
		String insert_pw = ShaS.encrypt(request.getParameter("inpassword"));
		String insert_name = request.getParameter("inname");
		String insert_pnum = request.getParameter("inpnum");
		web_userDTO in_userDTO = new web_userDTO(insert_id, insert_pw, insert_name, insert_pnum, null);
		int checksum = wum.UserCreate(in_userDTO);
		if(checksum == 1) {
			return true;
		}else {
			return false;
		}
	}
}
