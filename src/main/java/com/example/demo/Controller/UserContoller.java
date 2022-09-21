package com.example.demo.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.DTO.web_userDTO;
import com.example.demo.Mapper.web_user_mapper;

@Controller
public class UserContoller {
	
	@Autowired
	private web_user_mapper userService;
	
	@RequestMapping(value="/userCheck", method= {RequestMethod.POST, RequestMethod.GET})
	public String userCheck(String uuid, Model model) {
		web_userDTO user = userService.SearchUser(uuid);
		if(user != null) { model.addAttribute("check", false); }
		else { model.addAttribute("check", true); }
		return "modeltest";
	}
	
	@RequestMapping(value="/addAccount", method = {RequestMethod.POST, RequestMethod.GET})
	public String addAccount(web_userDTO in_user, Model model) {
		int check = userService.UserCreate(in_user);
		if(check == 1) { model.addAttribute("check", true); }
		else { model.addAttribute("check", false); }
		return "modeltest";
	}
	
	@RequestMapping(value="/Login", method = {RequestMethod.POST, RequestMethod.GET})
	public String Login(web_userDTO user, Model model) {
		int check = userService.UserLogin(user.getUser_id(), user.getUser_pw());
		if(check == 1) { model.addAttribute("check", true); }
		else { model.addAttribute("check", false); }
		return "modeltest";
	}
	
	@RequestMapping(value="/userUpdate", method = {RequestMethod.POST, RequestMethod.GET})
	public String userUpdate(web_userDTO uuser, Model model) {
		int check = userService.UserUpdate(uuser);
		if(check == 1) { model.addAttribute("check", true); }
		else { model.addAttribute("check", false); }
		return "modeltest";
	}
	
	@RequestMapping(value="/userDelete", method = {RequestMethod.POST, RequestMethod.GET})
	public String userDelete(web_userDTO duser, Model model) {
		int check = userService.UserUpdate(duser);
		if(check == 1) { model.addAttribute("check", true); }
		else { model.addAttribute("check", false); }
		return "modeltest";
	}
	
	@RequestMapping(value="/UserList", method = {RequestMethod.POST, RequestMethod.GET})
	public String UserList(Model model){
		ArrayList<web_userDTO> arr_user = userService.AllUserList();
		model.addAttribute("arr_user", arr_user);
		System.out.println(arr_user);
		return "modeltest";
	}
}
