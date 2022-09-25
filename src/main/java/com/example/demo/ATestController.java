package com.example.demo;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.videoDTO;
import com.example.demo.Mapper.video_mapper;

@Controller
@ResponseBody
public class ATestController {
	@Autowired
	private video_mapper vmap;
	
	@RequestMapping(value="/ajax/ajax", method= {RequestMethod.GET})
	public ArrayList<videoDTO> ajaxTest() {
		ArrayList<videoDTO> livelist = vmap.liveList();
		System.out.println("b시작");
		return livelist;
	}
	
	
}
