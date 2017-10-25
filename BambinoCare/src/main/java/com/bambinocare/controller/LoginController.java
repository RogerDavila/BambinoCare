package com.bambinocare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String showLoginForm( Model model,
			@RequestParam(name="error", required=false) String error, 
			@RequestParam(name="logout", required=false)String logout){
		
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		System.out.println("**********************Entrando en show");
		return "/unsecure/login";
	}
	
	@GetMapping({"/loginsuccess","/"})
	public String loginCheck(){
		System.out.println("*********************Entrando en loginchec");
		return "/secure/user/user";
	}
}
