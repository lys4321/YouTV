package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.Mapper.video_mapper;


@Controller
public class TController {
	@Autowired
	private video_mapper vmap;
	
	@RequestMapping(value="/a", method= {RequestMethod.POST, RequestMethod.GET})
    public String test(){
		System.out.println(vmap.allLiveList());
        return "index";
	}
}
