package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginScreenController {
	//@Autowired
	
	@RequestMapping(value="/YouTV/LoginScreen", method= {RequestMethod.POST, RequestMethod.GET})
	public String LoginScreen(Model model) {
		
		return "LoginScreen";
	}
	
	@RequestMapping(value="/YouTV/AccountCreateScreen", method= {RequestMethod.POST, RequestMethod.GET})
	public String AccountCreateScreen(Model model) {
		
		return "AccountCreateScreen";
	}
}
