package com.example.demo.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.chat_banDTO;
import com.example.demo.Mapper.chatBanMapper;

@Service
public class chatBanService {
	@Autowired
	private chatBanMapper cbm;
	
	int addBanUser(chat_banDTO banDTO) {
		return cbm.addBanUser(banDTO);
	}
	int cancelBan(chat_banDTO banDTO) {
		return cbm.cancelBan(banDTO);
	}
	ArrayList<chat_banDTO> allBanbyId(String streamerId){
		return cbm.allBanbyId(streamerId);
	}
}
