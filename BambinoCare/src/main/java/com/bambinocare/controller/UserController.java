package com.bambinocare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
import com.bambinocare.model.entity.EventTypeEntity;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.service.BookingService;
import com.bambinocare.model.service.BookingStatusService;
import com.bambinocare.model.service.BookingTypeService;
import com.bambinocare.model.service.EventTypeService;
import com.bambinocare.model.service.UserService;

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
	
	@Autowired
	@Qualifier("eventTypeService")
	private EventTypeService eventTypeService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@GetMapping("/showbookings")
	public ModelAndView showBookings() {
		
		ModelAndView mav = new ModelAndView(ViewConstants.USER_SHOW);
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userLogged = userService.findUserByEmail(user.getUsername());
		
		mav.addObject("usernameLogged", userLogged.getName());
		mav.addObject("bookings", bookingService.findByUser(userLogged));
		
		return mav;
	}
	
	@GetMapping("/bookingcreateform")
	public String showBookingCreate(Model model){
		BookingEntity booking = new BookingEntity();
		
		List<BookingTypeEntity> bookingTypes = bookingTypeService.findAllBookingTypes();
		List<EventTypeEntity> eventTypes = eventTypeService.findAllEventTypes();
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findUserByEmail(user.getUsername());
		model.addAttribute("usernameLogged", userEntity.getName());
		
		model.addAttribute("booking",booking);
		model.addAttribute("bookingTypes", bookingTypes);
		model.addAttribute("eventTypes", eventTypes);
		
		return ViewConstants.BOOKING_CREATE;
	}
	
	@PostMapping("/createbooking")
	public String createBooking(@ModelAttribute(name="booking") BookingEntity booking, BindingResult bindingResult, Model model){
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findUserByEmail(user.getUsername());
		booking.setUser(userEntity);
		
		BookingStatusEntity bookingStatus = bookingStatusService.findByBookingStatusDesc("Abierta");
		booking.setBookingStatus(bookingStatus);
		
		booking.setCost(booking.getDuration() * 200);
		
		if(bookingService.createBooking(booking)!=null){
			model.addAttribute("result",1);
		}else{
			model.addAttribute("result",0);
		}
		
		 return "redirect:/users/showbookings";
	}
	
	@GetMapping("/ajax/bookingtype")
	public String ajaxBrands(@RequestParam("bookingType.idBookingType") int bookingtype, Model model ) {
		
		if(bookingtype == 2)
			return "/secure/user/fragments/bookingforms :: tutoryform";
		else if (bookingtype == 3)
			return "/secure/user/fragments/bookingforms :: eventform";
		else
			return "/secure/user/fragments/bookingforms :: sinform";
		
	}
	
	@GetMapping("/cancel")
	public String cancel(){
		return "redirect:/users/showbookings";
	}
	
}
