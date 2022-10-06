package com.example.demo.Controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DTO.videoDTO;
import com.example.demo.Mapper.video_mapper;

@Controller
public class VideoController {
	@Autowired
	private video_mapper vmap;
	
	
	public void thumbnail() {
		Runtime run = Runtime.getRuntime();
		String path = "C:/VideoFolder/out.mp4"; //db 에서 가져오는걸로
		String command = "C:/ffmpeg-5.1.1-full_build/bin/ffmpeg.exe -t 00:00:05 -i " + path + " -vcodec png -vframes 1 C:/result.png";
		System.out.println(command);
		try {
			run.exec("cmd.exe chcp 65001");
			run.exec(command);
		}catch(Exception e) {
			System.out.println("error : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/Test", method = {RequestMethod.POST, RequestMethod.GET})
	public String broadList(/*web_userDTO user,*/ Model model) {
		ArrayList<videoDTO> livelist = vmap.liveList();
		ArrayList<videoDTO> recordlist = vmap.recordList();
		//thumbnail();
		/*
		if(user != null) {
			ArrayList<videoDTO> followlist = ;
			ArrayList<videoDTO> cliplist =
		}
		*/
		model.addAttribute("livelist", livelist);
		model.addAttribute("recordlist", recordlist);
		return "Test";
	}
	
	@RequestMapping(value="/ajax/frame_send", method = {RequestMethod.POST})
	@ResponseBody
	public void RTSTest(HttpServletRequest request) {
		int num = 0;
		String url = request.getParameter("frame_url");
		String data = url.split(",")[1];
		System.out.println(data);
		byte[] imageByte = DatatypeConverter.parseBase64Binary(data);
		try {
			BufferedImage bufimg = ImageIO.read(new ByteArrayInputStream(imageByte));
			ImageIO.write(bufimg, "png", new File("C:\\YTVrepo\\frame_repo\\"+/*방송코드*/"test.png"));			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//YouTV/Video 녹화방송
		@RequestMapping(value="/YouTV/Video", method= {RequestMethod.GET})
		public ResponseEntity<ResourceRegion> test3_2(@RequestParam(value="video_code", required=false, defaultValue="not_exist") String video_code, @RequestHeader HttpHeaders headers, Model model) throws UnsupportedEncodingException, IOException 
		{	videoDTO streaming_video = vmap.selectVideo(video_code); 
			UrlResource video = new UrlResource("file:"+streaming_video.getSave_url());
	        ResourceRegion resourceRegion;
	        final long chunkSize = 1000000L;
	        long contentLength = video.contentLength();
	        Optional<HttpRange> optional = headers.getRange().stream().findFirst();
	        HttpRange httpRange;
	        if (optional.isPresent()) {
	            httpRange = optional.get();
	            long start = httpRange.getRangeStart(contentLength);
	            long end = httpRange.getRangeEnd(contentLength);
	            long rangeLength = Long.min(chunkSize, end - start + 1);
	            resourceRegion = new ResourceRegion(video, start, rangeLength);
	        } else {
	            long rangeLength = Long.min(chunkSize, contentLength);
	            resourceRegion = new ResourceRegion(video, 0, rangeLength);
	        }
	        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
	              .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
	              .body(resourceRegion);
		}
}
