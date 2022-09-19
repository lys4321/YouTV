package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TController {
	@RequestMapping("/a")
    public String test(Model model){
        return "Test";
	}
}
