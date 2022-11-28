package com.example.demo.Controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DTO.clipDTO;
import com.example.demo.DTO.videoDTO;
import com.example.demo.Mapper.clipMapper;
import com.example.demo.Mapper.video_mapper;
import com.example.demo.Mapper.web_user_mapper;

@Controller
public class VideoController {
	@Autowired
	private video_mapper vmap;
	@Autowired
	private web_user_mapper wum;
	@Autowired
	private clipMapper clm;
	
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
		
	@RequestMapping(value="/ajax/frame_send", method = {RequestMethod.POST})
	@ResponseBody
	public boolean RTSTest(HttpServletRequest request) {
		int count = Integer.parseInt(request.getParameter("frame_count"));
		String url = request.getParameter("frame_url");
		String data = url.split(",")[1];
		System.out.println(count);
		byte[] imageByte = DatatypeConverter.parseBase64Binary(data);
		String format = String.format("C:\\YTVrepo\\frame_repo\\%s%05d.png", "test", count);
		try {
			BufferedImage bufimg = ImageIO.read(new ByteArrayInputStream(imageByte));
			ImageIO.write(bufimg, "png", new File(format));			

			
			//ImageIO.write(bufimg, "png", new File("C:\\YTVrepo\\frame_repo\\"+/*방송코드*/"test"+ count +".png"));			
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	@RequestMapping(value="/ajax/getRecordInfo", method = {RequestMethod.GET})
	@ResponseBody
	public HashMap<String, Object> getRecordInfo(HttpServletRequest request) {
		String code = request.getParameter("code");
		videoDTO vDTO = vmap.selectVideo(code);
		String img = wum.SearchUserProfile(vDTO.getStreamer_id());
		String session = request.getParameter("session");
		File file = new File(img);
		ArrayList<byte[]> arr = new ArrayList<byte[]>();
		try {
			arr.add(FileCopyUtils.copyToByteArray(file));
		}catch(Exception e) {
			e.printStackTrace();
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("RecordInfo", vDTO);
		map.put("RecordThumbnail", arr);
		map.put("session", session);
		
		return map;
	}
	@RequestMapping(value="/YouTV/Video", method= {RequestMethod.GET})
	public String recordVideo(@RequestParam(value="sessionId", required=false, defaultValue="not_exist") String sessionId, @RequestParam(value="video_code", required=false, defaultValue="not_exist") String video_code, @RequestParam(value="title", required=false, defaultValue="not_exist") String title, Model model)
	{	
		model.addAttribute("sessionId", sessionId);
		model.addAttribute("video_code", video_code);
		model.addAttribute("title", title);
		return "rerecordpplay";
	}
	
	@RequestMapping(value="/YouTV/Clip", method= {RequestMethod.GET})
	public String recordClip(@RequestParam(value="clip_code", required=false, defaultValue="not_exist") String clip_code, @RequestParam(value="title", required=false, defaultValue="not_exist") String title, Model model)
	{	
		model.addAttribute("clip_code", clip_code);
		model.addAttribute("title", title);
		return "clipScreen";
	}
	
	@RequestMapping(value="/Clip", method= {RequestMethod.GET})
	public ResponseEntity<ResourceRegion> watchClip(@RequestParam(value="clip_code", required=false, defaultValue="not_exist") String clip_code, @RequestHeader HttpHeaders headers, Model model) throws UnsupportedEncodingException, IOException 
	{	clipDTO streaming_clip = clm.selectClip(clip_code);
		UrlResource clip = new UrlResource("file:"+streaming_clip.getClip_url());
		    ResourceRegion resourceRegion;
		    final long chunkSize = 1000000L;
		    long contentLength = clip.contentLength();
		    Optional<HttpRange> optional = headers.getRange().stream().findFirst();
		    HttpRange httpRange;
		    if (optional.isPresent()) {
		        httpRange = optional.get();
		        long start = httpRange.getRangeStart(contentLength);
		        long end = httpRange.getRangeEnd(contentLength);
		        long rangeLength = Long.min(chunkSize, end - start + 1);
		        resourceRegion = new ResourceRegion(clip, start, rangeLength);
		    } else {
		        long rangeLength = Long.min(chunkSize, contentLength);
		        resourceRegion = new ResourceRegion(clip, 0, rangeLength);
		    }
		    return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
		          .contentType(MediaTypeFactory.getMediaType(clip).orElse(MediaType.APPLICATION_OCTET_STREAM))
		          .body(resourceRegion);
	}
}
