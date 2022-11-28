package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Repos.StreamChatRoomRepo;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/chat")
public class StreamChatRoomController {
	private final StreamChatRoomRepo streamChatRoomRepo = new StreamChatRoomRepo();
	
	@GetMapping(value = "/rooms")
    public ModelAndView rooms(){
		ModelAndView model = new ModelAndView();
		model.setViewName("rooms");
        model.addObject("list", streamChatRoomRepo.findAllRooms());
        return model;
    }

    /*
     //채팅방 개설
    @PostMapping(value = "/room")
    public String create(@RequestParam("code") String code, RedirectAttributes rttr){
        rttr.addFlashAttribute("roomName", streamChatRoomRepo.createRoom(code));
        return "redirect:/chat/rooms";
    }
    */
	
	//채팅방 개설
    @PostMapping(value = "/room")
    public void create(@RequestParam("code") String code){
    	streamChatRoomRepo.createRoom(code);
    }
    

    //삭제
    @PostMapping(value = "/droom") 
    public String delete(@RequestParam("del") String del){
    	streamChatRoomRepo.deleteRoom(del);
    	return "redirect:/chat/rooms"; 
    }


    //채팅방 조회
    @RequestMapping(value="/room", method= {RequestMethod.GET})
    public String getRoom(@RequestParam(value="code")String code, Model model){
    	model.addAttribute("room", streamChatRoomRepo.findRoomById(code));
        return "room";
    }
}
