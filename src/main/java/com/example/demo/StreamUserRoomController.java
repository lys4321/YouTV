package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/streaming")
public class StreamUserRoomController {
	private final StreamUserRoomRepo streamUserRoomRepo = new StreamUserRoomRepo();
	
	@PostMapping(value="/live")
	public String create(/* @RequestParam("code") String code */RedirectAttributes rttr) { //보기
		rttr.addFlashAttribute("room", streamUserRoomRepo.createRoom("1212"));
		//방송시청 화면으로 변경해야함
		return "redirect:/streaming/live";
	}
	@PostMapping(value="/end_live")
	public String delete(@RequestParam("del") String del) {
		streamUserRoomRepo.deleteRoom(del);
		return "MainScreen";
	}
	@GetMapping(value="/live")
	public String getRoom(/* @RequestParam(value="code") String code, */ Model model) {
		model.addAttribute("room", streamUserRoomRepo.findRoom("1212"));
		return "streamtest";
	}
}
