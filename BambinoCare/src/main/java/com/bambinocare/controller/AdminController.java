package com.bambinocare.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.bambinocare.constants.ViewConstants;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@GetMapping("/showbookings")
	public ModelAndView showBookings() {
		
		ModelAndView mav = new ModelAndView(ViewConstants.ADMIN_VIEW);
		
		//mav.addObject("bookings", "");
		
		return mav;
	}
	
}
