package com.bambinocare.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bambinocare.constant.ViewConstants;
import com.bambinocare.model.entity.BambinoEntity;
import com.bambinocare.model.entity.BookingEntity;
import com.bambinocare.model.entity.BookingTypeEntity;
import com.bambinocare.model.entity.EventTypeEntity;
import com.bambinocare.model.entity.NannyEntity;
import com.bambinocare.model.entity.RoleEntity;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.service.BambinoService;
import com.bambinocare.model.service.BookingService;
import com.bambinocare.model.service.BookingTypeService;
import com.bambinocare.model.service.ClientService;
import com.bambinocare.model.service.EmergencyContactService;
import com.bambinocare.model.service.EventTypeService;
import com.bambinocare.model.service.NannyService;
import com.bambinocare.model.service.RoleService;
import com.bambinocare.model.service.UserService;

@Controller
@RequestMapping("/nannies")
public class NannyController {

	@Autowired
	@Qualifier("nannyService")
	private NannyService nannyService;

	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("bookingService")
	private BookingService bookingService;

	@Autowired
	@Qualifier("bambinoService")
	private BambinoService bambinoService;

	@Autowired
	@Qualifier("emergencyContactService")
	private EmergencyContactService emergencyContactService;

	@Autowired
	@Qualifier("clientService")
	private ClientService clientService;

	@Autowired
	@Qualifier("bookingTypeService")
	private BookingTypeService bookingTypeService;

	@Autowired
	@Qualifier("eventTypeService")
	private EventTypeService eventTypeService;
	
	private static final String STATUS = "Agendada";

	@GetMapping("/showbookings")
	public ModelAndView showBookings(@RequestParam(required = false) String result) {

		ModelAndView mav = new ModelAndView(ViewConstants.NANNY_SHOW);

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		NannyEntity nanny = nannyService.findByUserEmail(user.getUsername());
		UserEntity userLogged = nanny.getUser();

		mav.addObject("usernameLogged", userLogged.getFirstname());
		mav.addObject("bookings", bookingService.findByNannyAndBookingStatusBookingStatusDesc(nanny, STATUS));
		mav.addObject("nanny", nanny);
		mav.addObject("result", result);

		return mav;
	}

	@PostMapping("/showbookingdetail")
	public String showBookingDetail(@RequestParam(name = "bookingId") Integer bookingId, Model model) {

		String result = "";

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		NannyEntity nanny = nannyService.findByUserEmail(user.getUsername());
		UserEntity userLogged = nanny.getUser();

		BookingEntity booking = bookingService.findByBookingIdAndBookingStatusBookingStatusDescAndNanny(bookingId, STATUS, nanny);

		if (booking != null) {

			if (booking.getBambino() != null) {
				List<String> bambinoIds = new ArrayList<>();
				for (BambinoEntity bambino : booking.getBambino()) {
					bambinoIds.add(bambino.getBambinoId().toString());
				}
				booking.setBambinoId(bambinoIds);
			}

			List<BookingTypeEntity> bookingTypes = bookingTypeService.findAllBookingTypes();
			List<EventTypeEntity> eventTypes = eventTypeService.findAllEventTypes();
			List<BambinoEntity> bambinos = bambinoService.findByClientUser(booking.getClient().getUser());

			model.addAttribute("allbambinos", bambinos);
			model.addAttribute("usernameLogged", userLogged.getFirstname());

			model.addAttribute("booking", booking);
			model.addAttribute("bookingTypes", bookingTypes);
			model.addAttribute("eventTypes", eventTypes);

			return ViewConstants.BOOKING_DETAIL_NANNY_SHOW;
		} else {
			result = "No se encontr%C3%B3 la reservaci%C3%B3n solicitada o no tiene permisos para verla";
		}

		return "redirect:/nannies/showbookings?result=" + result;

	}

	@GetMapping("/shownannies")
	public ModelAndView showNannies() {
		ModelAndView mav = new ModelAndView(ViewConstants.NANNY_SHOW);

		mav.addObject("nannies", nannyService.findAllNannies());

		return mav;
	}

	@GetMapping("/nannycreateform")
	public String showNannyCreate(Model model) {
		NannyEntity nanny = new NannyEntity();

		List<RoleEntity> roles = roleService.findAllRoles();

		model.addAttribute("roles", roles);
		model.addAttribute("nanny", nanny);

		return ViewConstants.NANNY_CREATE;
	}

	@GetMapping("/nannyeditform")
	public String showNannyEdit(@RequestParam(name = "nannyId", required = true) Integer nannyId, Model model) {
		NannyEntity nanny = new NannyEntity(new UserEntity());

		if (nannyId > 0) {
			nanny = nannyService.findByNannyId(nannyId);
		}

		List<RoleEntity> roles = roleService.findAllRoles();

		model.addAttribute("roles", roles);
		model.addAttribute("nanny", nanny);

		return ViewConstants.NANNY_EDIT;
	}

	@PostMapping("/editnanny")
	public String editNanny(@ModelAttribute(name = "nanny") NannyEntity nanny, Model model) {
		if (nannyService.editNanny(nanny) != null) {
			model.addAttribute("result", 1);
		} else {
			model.addAttribute("result", 0);
		}

		return "redirect:/nannies/showbookings";
	}

	@PostMapping("/createnanny")
	public String createNanny(@ModelAttribute(name = "nanny") NannyEntity nanny, Model model) {
		if (nannyService.createNanny(nanny) != null) {
			model.addAttribute("result", 1);
		} else {
			model.addAttribute("result", 0);
		}

		return "redirect:/nannies/showbookings";
	}

	@GetMapping("/cancel")
	public String cancel() {
		return "redirect:/nannies/showbookings";
	}

	@GetMapping("/removenanny")
	public String removeUser(@RequestParam(name = "nannyId", required = true) Integer nannyId) {
		nannyService.removeNanny(nannyId);
		return "redirect:/nannies/showbookings";
	}
}
