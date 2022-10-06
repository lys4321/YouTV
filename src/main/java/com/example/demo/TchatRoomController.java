package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class TchatRoomController {
	private final TroomRepo troomRepo;
	
	
	@GetMapping(value = "/rooms")
    public ModelAndView rooms(){
		ModelAndView model = new ModelAndView();
		model.setViewName("rooms");
        model.addObject("list", troomRepo.findAllRooms());
        return model; //"chat/rooms"
    }

    //채팅방 개설
    @PostMapping(value = "/room")
    public String create(@RequestParam("code") String code, RedirectAttributes rttr){
        rttr.addFlashAttribute("roomName", troomRepo.createRoom(code));
        return "redirect:/chat/rooms";
    }

    //채팅방 조회
    @RequestMapping(value="/room", method= {RequestMethod.GET})
    public String getRoom(@RequestParam(value="code")String code, Model model){
    	model.addAttribute("room", troomRepo.findRoomById(code));
        System.out.println("3");
        return "room";
    }
	
	/*
	 * @GetMapping("/room") public String roomScreen() { return "/chat/room"; }
	 * 
	 * @PostMapping("/room")
	 * 
	 * @ResponseBody public Troom createRoom(@RequestParam String code) { return
	 * troomRepo.createRoom(code); }
	 * 
	 * @GetMapping("/room/enter/{code}") public String roomIn(@PathVariable String
	 * code) { return "/chat/roomIn"; }
	 */
}
