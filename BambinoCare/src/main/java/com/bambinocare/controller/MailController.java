package com.bambinocare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bambinocare.model.service.EmailService;

@Controller
@RequestMapping("/mail")
public class MailController {

	@Autowired
	@Qualifier("emailService")
	private EmailService emailService;

	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public String createMail() {
		emailService.sendSimpleMessage("rog.davila94@gmail.com", "testSMTP", "Esta es una prueba");

		return "redirect:/login";
	}

}
