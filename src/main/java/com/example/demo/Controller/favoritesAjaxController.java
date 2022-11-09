package com.example.demo.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DTO.favoritesDTO;
import com.example.demo.Mapper.favoriteMapper;

@Controller
@ResponseBody
public class favoritesAjaxController {
	@Autowired
	favoriteMapper fm;
	
	@RequestMapping(value="/ajax/checkFavorite", method = {RequestMethod.GET})
	public boolean checkFavorite(HttpServletRequest request) {
		String sid = request.getParameter("streamerid");
		String uid = request.getParameter("uid");
		favoritesDTO fDTO = fm.findByIds(sid, uid);
		
		System.out.println("[테스트 즐겨찾기] : "+fDTO);
		
		if(fDTO != null) {
			return true;
		}else {
			return false;
		}
	}
	
	@RequestMapping(value="/ajax/addFavorites", method = {RequestMethod.POST})
	public boolean addFavorites(HttpServletRequest request) {
		String sid = request.getParameter("sid");
		String uid = request.getParameter("uid");
		System.out.println("["+sid+"]"+"["+uid+"]");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String ndate = formatter.format(date);
		
		favoritesDTO fDTO = new favoritesDTO(uid, sid, ndate);
		int check = fm.addfollow(fDTO);
		System.out.println(check);
		if(check != 0) {
			return true;
		}else {
			return false;
		}
	}
	
	@RequestMapping(value="/ajax/cancelFavorites", method = {RequestMethod.POST})
	public boolean cancelFavorites(HttpServletRequest request) {
		String sid = request.getParameter("sid");
		String uid = request.getParameter("uid");
		System.out.println("["+sid+"]"+"["+uid+"]");
		int check = fm.deletefollow(uid, sid);
		System.out.println(check);
		if(check != 0) {
			return true;
		}else {
			return false;
		}
	}
}
