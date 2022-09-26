package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.videoDTO;
import com.example.demo.Mapper.video_mapper;
import com.example.demo.Service.SHA256_Service;


@Controller
public class TController {
	
	
	SHA256_Service ss = new SHA256_Service();
	
	String pw = "123456789";
	
	@RequestMapping(value="/ad", method= {RequestMethod.POST, RequestMethod.GET})
	public String test() {
		System.out.println(ss.encrypt(pw));
		System.out.println(ss.encrypt(pw).equals(ss.encrypt("123456789")));
		return "Test";
	}
}
