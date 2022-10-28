package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Mapper.video_mapper;


@Controller
public class TController {
	@Autowired
	video_mapper vm;
	
	@RequestMapping(value="/", method= {RequestMethod.POST, RequestMethod.GET})
	public String test2() {
		return "LayoutTest";
	}
	
	
	
	@RequestMapping(value="/livetest2", method= {RequestMethod.POST, RequestMethod.GET})
	public String test3() {
		return "LiveGuestScreen";
	}
	
	@RequestMapping(value="/streamtest", method= {RequestMethod.POST, RequestMethod.GET})
	public String test2_1_1() {
		return "streamtest";
	}
	
	@RequestMapping(value="/LayoutTest", method= {RequestMethod.GET})
	public String LayoutTest() {
		return "LayoutTest";
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
