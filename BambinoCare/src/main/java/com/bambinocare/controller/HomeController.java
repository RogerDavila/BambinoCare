package com.bambinocare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.bambinocare.constant.ViewConstants;

@Controller
public class HomeController {

	@GetMapping("/")
	public String index(){
		return ViewConstants.HOME;
	}
	
}
