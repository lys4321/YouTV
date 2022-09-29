package com.example.demo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

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

import com.example.demo.DTO.videoDTO;
import com.example.demo.Mapper.video_mapper;
import com.example.demo.Service.SHA256_Service;


@Controller
public class TController {
	@Autowired
	video_mapper vm;
	
	SHA256_Service ss = new SHA256_Service();
	
	String pw = "123456789";
	
	@RequestMapping(value="/ad", method= {RequestMethod.POST, RequestMethod.GET})
	public String test() {
		System.out.println(ss.encrypt(pw));
		System.out.println(ss.encrypt(pw).equals(ss.encrypt("123456789")));
		return "Test";
	}
	
	@RequestMapping(value="/livetest", method= {RequestMethod.POST, RequestMethod.GET})
	public String test2() {
		return "livetest";
	}
	
	@RequestMapping(value="/YouTV/VideoScreen", method= {RequestMethod.GET})
	public String test3_1(@RequestParam(value="video_code", required=false, defaultValue="not_exist") String video_code, Model model) {
		model.addAttribute("video_code", video_code);
		return "VideoScreen";
	}
	//YouTV/Video
	@RequestMapping(value="/YouTV/Video", method= {RequestMethod.GET})
	public ResponseEntity<ResourceRegion> test3_2(@RequestParam(value="video_code", required=false, defaultValue="not_exist") String video_code, @RequestHeader HttpHeaders headers, Model model) throws UnsupportedEncodingException, IOException 
	{	System.out.println("a");
		videoDTO streaming_video = vm.selectVideo(video_code); 
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
	
//	@RequestMapping(value="/YouTV/livetest", method= {RequestMethod.GET})
//	public ResponseEntity<ResourceRegion> test4(@RequestHeader HttpHeaders headers) throws IOException{
//		videoDTO vDTO = vm.selectVideo("lys43202022091905361110");
//		UrlResource video = new UrlResource("file:"+vDTO.getSave_url());
//	        ResourceRegion resourceRegion;
//
//	        final long chunkSize = 1000000L;
//	        long contentLength = video.contentLength();
//
//	        Optional<HttpRange> optional = headers.getRange().stream().findFirst();
//	        HttpRange httpRange;
//	        if (optional.isPresent()) {
//	            httpRange = optional.get();
//	            long start = httpRange.getRangeStart(contentLength);
//	            long end = httpRange.getRangeEnd(contentLength);
//	            long rangeLength = Long.min(chunkSize, end - start + 1);
//	            resourceRegion = new ResourceRegion(video, start, rangeLength);
//	        } else {
//	            long rangeLength = Long.min(chunkSize, contentLength);
//	            resourceRegion = new ResourceRegion(video, 0, rangeLength);
//	        }
//
//	        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
//	                .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
//	                .body(resourceRegion);
//	}
}
