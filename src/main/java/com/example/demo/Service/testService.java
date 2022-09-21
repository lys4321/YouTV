package com.example.demo.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class testService {
	@Autowired
	private Ssample tsm;
	
	public ArrayList<String> selectTest() {
		return tsm.selectTest();
	}
}
