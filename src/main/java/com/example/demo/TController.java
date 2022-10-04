package com.example.demo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DTO.videoDTO;
import com.example.demo.Mapper.video_mapper;
import com.example.demo.Service.SHA256_Service;


@Controller
public class TController {
	@Autowired
	video_mapper vm;
	
	@RequestMapping(value="/livetest", method= {RequestMethod.POST, RequestMethod.GET})
	public String test2() {
		return "livetest";
	}
	
	
	
	@RequestMapping(value="/YouTV/VideoScreen", method= {RequestMethod.GET})
	public String test3_1(@RequestParam(value="video_code", required=false, defaultValue="not_exist") String video_code, Model model) {
		model.addAttribute("video_code", video_code);
		return "VideoScreen";
	}
	
	
	@RequestMapping(value="/thread/test", method= {RequestMethod.GET})
	public String test4() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1); // 동시에 실행시킬 스레드의 수, 기본값은 1
		executor.setAllowCoreThreadTimeOut(false); //스레드의 타임아앗을 허용할 것인지 설정, 기본값 false
		executor.setKeepAliveSeconds(30); //타임아웃 시간 설정
		executor.setMaxPoolSize(20); //최대 스레드 풀 크기 설정
		executor.setQueueCapacity(5); //스레드 풀 큐의 사이즈, corePoolSize를 넘어서는 task 올 시에 큐에 해당 태스크들이 쌓임, 최대 스레드풀 사이즈까지만 쌓일 수 있다.
		executor.initialize();
		
		
		Runnable r = () ->{
			try {
				System.out.println(Thread.currentThread().getName());
				Thread.sleep(100);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		};
		for(int i=0; i<10; i++) {
			executor.execute(r);
		}
		return "index";
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
