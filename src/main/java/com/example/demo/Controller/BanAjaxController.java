package com.example.demo.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DTO.chat_banDTO;
import com.example.demo.Mapper.chatBanMapper;

@Controller
@ResponseBody
public class BanAjaxController {
	@Autowired
	private chatBanMapper cbm;
	
	@RequestMapping(value="/ajax/addBan", method = {RequestMethod.POST})
	public boolean addBan(HttpServletRequest request) {
		String sid = request.getParameter("sid");
		String uid = request.getParameter("uid");
		String banReason = request.getParameter("banReason");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			String ndate = formatter.format(date);
			chat_banDTO banDTO = new chat_banDTO(sid, uid, ndate, banReason);
			System.out.println(banDTO);
			cbm.addBanUser(banDTO);
			
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@RequestMapping(value="/ajax/checkBan", method = {RequestMethod.GET})
	public boolean checkBan(HttpServletRequest request) {
		String sid = request.getParameter("streamer_id");
		String myid = request.getParameter("myid");
		ArrayList<chat_banDTO> arr = cbm.allBanbyId(sid);
		for(int i=0; i<arr.size(); i++) {
			if(arr.get(i).getViewer_id().equals(myid)) {
				return false;
			}
		}
		return true;
	}
}
