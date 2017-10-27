package com.bambinocare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bambinocare.constants.ViewConstants;
import com.bambinocare.model.entity.BookingEntity;
import com.bambinocare.model.service.BookingService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	@Qualifier("bookingService")
	private BookingService bookingService;
	
	@GetMapping("/showbookings")
	public ModelAndView showBookings() {
		
		ModelAndView mav = new ModelAndView(ViewConstants.USER_SHOW);
		
		mav.addObject("bookings", bookingService.findAllBookings());
		
		return mav;
	}
	
	@GetMapping("/usercreateform")
	public String showUserCreate(Model model){
		BookingEntity booking = new BookingEntity();
		
		model.addAttribute("booking",booking);
		
		return ViewConstants.BOOKING_CREATE;
	}
	
	@PostMapping("/createbooking")
	public String createUser(@ModelAttribute(name="booking") BookingEntity booking, Model model){
		if(bookingService.createBooking(booking)!=null){
			model.addAttribute("result",1);
		}else{
			model.addAttribute("result",0);
		}
		
		 return "redirect:/users/showbookings";
	}
	
}
