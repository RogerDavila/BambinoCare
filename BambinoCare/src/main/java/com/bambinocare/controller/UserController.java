package com.bambinocare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bambinocare.constants.ViewConstants;
import com.bambinocare.model.entity.BookingEntity;
import com.bambinocare.model.entity.BookingStatusEntity;
import com.bambinocare.model.entity.BookingTypeEntity;
import com.bambinocare.model.service.BookingService;
import com.bambinocare.model.service.BookingStatusService;
import com.bambinocare.model.service.BookingTypeService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	@Qualifier("bookingService")
	private BookingService bookingService;

	@Autowired
	@Qualifier("bookingTypeService")
	private BookingTypeService bookingTypeService;
	
	@Autowired
	@Qualifier("bookingStatusService")
	private BookingStatusService bookingStatusService;
	
	@GetMapping("/showbookings")
	public ModelAndView showBookings() {
		
		ModelAndView mav = new ModelAndView(ViewConstants.USER_SHOW);
		
		mav.addObject("bookings", bookingService.findAllBookings());
		
		return mav;
	}
	
	@GetMapping("/bookingcreateform")
	public String showUserCreate(Model model){
		BookingEntity booking = new BookingEntity();
		
		List<BookingTypeEntity> bookingTypes = bookingTypeService.findAllBookingTypes();
		List<BookingStatusEntity> bookingStatuses = bookingStatusService.findAllBookingStatus();
		
		model.addAttribute("booking",booking);
		model.addAttribute("bookingStatuses", bookingStatuses);
		model.addAttribute("bookingTypes", bookingTypes);
		
		return ViewConstants.BOOKING_CREATE;
	}
	
	@GetMapping("/createbooking")
	public String createUser(@ModelAttribute(name="booking") BookingEntity booking, BindingResult bindingResult, Model model){
		if(bookingService.createBooking(booking)!=null){
			model.addAttribute("result",1);
		}else{
			model.addAttribute("result",0);
		}
		
		 return "redirect:/users/showbookings";
	}
	
	@GetMapping("/ajax/bookingtype")
	public String ajaxBrands(@RequestParam("bookingType.idBookingType") int bookingtype, Model model) {
		
		if(bookingtype == 2)
			return "/secure/user/fragments/bookingforms :: tutoryform";
		else if (bookingtype == 3)
			return "/secure/user/fragments/bookingforms :: eventform";
		else
			return "/secure/user/fragments/bookingforms :: sinform";
		
	}
	
}
