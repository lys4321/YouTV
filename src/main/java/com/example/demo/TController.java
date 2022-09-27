package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
