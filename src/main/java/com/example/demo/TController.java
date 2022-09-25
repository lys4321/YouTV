package com.example.demo;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.videoDTO;
import com.example.demo.Mapper.video_mapper;


@Controller
public class TController {
	@Autowired
	private video_mapper vmap;
	
	@RequestMapping(value="/a", method= {RequestMethod.POST, RequestMethod.GET})
    public String test(){
		System.out.println("a시작");
        return "modeltest";
	}
}
