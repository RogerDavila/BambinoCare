package com.bambinocare.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.entity.EventTypeEntity;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.service.BookingService;
import com.bambinocare.model.service.BookingStatusService;
import com.bambinocare.model.service.BookingTypeService;
import com.bambinocare.model.service.ClientService;
import com.bambinocare.model.service.EmailService;
import com.bambinocare.model.service.EventTypeService;
import com.bambinocare.model.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	@Qualifier("bookingService")
	private BookingService bookingService;
	
	@Autowired
	@Qualifier("clientService")
	private ClientService clientService;
	

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

	@Autowired
	@Qualifier("emailService")
	private EmailService emailService;

	@GetMapping("/showbookings")
	public ModelAndView showBookings(@RequestParam(required=false) String error, @RequestParam(required=false) String result) {

		ModelAndView mav = new ModelAndView(ViewConstants.USER_SHOW);
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userLogged = userService.findUserByEmail(user.getUsername());

		mav.addObject("usernameLogged", userLogged.getName());
		mav.addObject("bookings", bookingService.findByUser(userLogged));
		mav.addObject("client", clientService.findByUser(userLogged));
		mav.addObject("error", error);
		mav.addObject("result", result);

		return mav;
	}

	@GetMapping("/createbookingform")
	public String showCreateBooking(@RequestParam(required = false) String result,
			@RequestParam(required = false) String error, Model model) {
		BookingEntity booking = new BookingEntity();

		List<BookingTypeEntity> bookingTypes = bookingTypeService.findAllBookingTypes();
		List<EventTypeEntity> eventTypes = eventTypeService.findAllEventTypes();

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findUserByEmail(user.getUsername());
		model.addAttribute("usernameLogged", userEntity.getName());

		model.addAttribute("booking", booking);
		model.addAttribute("bookingTypes", bookingTypes);
		model.addAttribute("eventTypes", eventTypes);

		model.addAttribute("result", result);
		model.addAttribute("error", error);

		return ViewConstants.BOOKING_CREATE;
	}


	@PostMapping("/createbooking")
	public String createBooking(@ModelAttribute(name = "booking") BookingEntity booking, BindingResult bindingResult,
			Model model) {

		String error = "";
		String result = "";
		
		if(booking.getDuration() == null || booking.getDuration() == 0) {
			error = "Favor de verificar el campo Duración";
			return "redirect:/users/createbookingform?error=" + error;
		}
		
		if(booking.getDate() == null) {
			error = "Favor de verificar el campo Fecha";
			return "redirect:/users/showbookings?error=" + error;
		}else if(getDate(booking.getDate(),1).before(getDate(Calendar.getInstance().getTime(),0))){
			error = "No puedes reservar en fechas pasadas";
			return "redirect:/users/createbookingform?error=" + error;
		}
		
		if(booking.getHour() == null || booking.getHour().equals("")) {
			error = "Favor de verificar el campo Hora";
			return "redirect:/users/createbookingform?error=" + error;
		}
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findUserByEmail(user.getUsername());
		booking.setUser(userEntity);

		BookingStatusEntity bookingStatus = bookingStatusService.findByBookingStatusDesc("Abierta");
		booking.setBookingStatus(bookingStatus);

		booking.setCost(booking.getDuration() * 200);
		
		booking.setDate(getDate(booking.getDate(),1));

		if (bookingService.createBooking(booking) != null) {
			emailService.sendSimpleMessage("rogerdavila.stech@gmail.com", "BambinoCare - Nueva reservación",
					"El usuario " + booking.getUser().getEmail() + " ha agendado una nueva cita el día "
							+ booking.getDate() + ". Puedes revisar el detalle en"
							+ " la siguiente liga: \n\r \n\r www.bambinocare.com");
			
			emailService.sendSimpleMessage(booking.getUser().getEmail(), "BambinoCare - Nueva reservación", "Hemos recibido tu reservación y estamos buscando tu mejor opción. En breve\n" + 
					"recibirás un correo para informarte el perfil de la Bambinaia que estará asistiendo\n" + 
					"a tu hogar.");
			
			result = "La reservación se ha realizado exitosamente.";
		} else {
			error = "No se ha podido realizar la reservación, intente nuevamente";
			return "redirect:/users/createbookingform?error=" + error;
		}

		return "redirect:/users/showbookings?result=" + result;
	}
	
	@GetMapping("/editbookingform")
	public String showEditBooking(@RequestParam(required = false) String result,
			@RequestParam(required = false) String error, @RequestParam(required = true) Integer idBooking, Model model) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findUserByEmail(user.getUsername());
		
		BookingEntity booking = bookingService.findBookingByIdBookingAndUserAndBookingStatusBookingStatusDescNotIn(idBooking, userEntity,"Cancelada");

		if (booking == null) {
			error = "La reservación solicitada no existe o no tienes permisos para visualizarla o ya se encuentra cancelada";
			return "redirect:/users/showbookings?error="+ error;
		}

		List<BookingTypeEntity> bookingTypes = bookingTypeService.findAllBookingTypes();
		List<EventTypeEntity> eventTypes = eventTypeService.findAllEventTypes();

		model.addAttribute("usernameLogged", userEntity.getName());

		model.addAttribute("booking", booking);
		model.addAttribute("bookingTypes", bookingTypes);
		model.addAttribute("eventTypes", eventTypes);

		model.addAttribute("result", result);
		model.addAttribute("error", error);

		return ViewConstants.BOOKING_EDIT;
	}
	
	@PostMapping("/editbooking")
	public String editBooking(@ModelAttribute(name = "booking") BookingEntity booking, BindingResult bindingResult,
			Model model) {

		String error = "";
		String result = "";
		
		if(booking.getDuration() == null || booking.getDuration() == 0) {
			error = "Favor de verificar el campo Duración";
			return "redirect:/users/showbookings?error=" + error;
		}
		
		if(booking.getDate() == null) {
			error = "Favor de verificar el campo Fecha";
			return "redirect:/users/showbookings?error=" + error;
		}else if(getDate(booking.getDate(),1).before(getDate(Calendar.getInstance().getTime(),0))){
			error = "No puedes reservar en fechas pasadas";
			return "redirect:/users/showbookings?error=" + error;
		}
		
		if(booking.getHour() == null || booking.getHour().equals("")) {
			error = "Favor de verificar el campo Hora";
			return "redirect:/users/showbookings?error=" + error;
		}
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findUserByEmail(user.getUsername());
		
		BookingEntity oldBooking = bookingService.findBookingByIdBookingAndUser(booking.getIdBooking(), userEntity);
		
		oldBooking.setDuration(booking.getDuration());
		oldBooking.setDate(getDate(booking.getDate(),1));
		oldBooking.setHour(booking.getHour());
		oldBooking.setCost(booking.getDuration() * 200);
		
		if (bookingService.createBooking(oldBooking) != null) {
			emailService.sendSimpleMessage("rogerdavila.stech@gmail.com", "Reservación Modificada",
					"El usuario " + oldBooking.getUser().getEmail() + " ha modificado la reservación del día "
							+ oldBooking.getDate() + ". Puedes revisar el detalle en"
							+ " la siguiente liga: \n\r \n\r www.bambinocare.com");
			result="La reservación fue modificada con éxito!";
		} else {
			result="Ocurrió un error al intentar editar la reservación, vuelva a intentarlo";
		} 

		return "redirect:/users/showbookings?error="+error+"&result="+result;
	}

	@PostMapping("/edituser")
	public String edituser(@ModelAttribute(name = "client") ClientEntity client, BindingResult bindingResult,
			Model model) {

		String error = "";
		String result = "";
		
		if(client.getUser().getEmail() == null || client.getUser().getEmail().equals("")) {
			error = "Favor de verificar el email";
			return "redirect:/users/showbookings?error=" + error;
		}
		
		if(client.getUser().getName() == null || client.getUser().getName().equals("")) {
			error = "Favor de verificar el Nombre";
			return "redirect:/users/showbookings?error=" + error;
		}
		
		if(client.getUser().getLastname() == null || client.getUser().getLastname().equals("")) {
			error = "Favor de verificar el Apellido";
			return "redirect:/users/showbookings?error=" + error;
		}
		
		if(client.getUser().getTelephone() == null || client.getUser().getTelephone().equals("")) {
			error = "Favor de verificar el email";
			return "redirect:/users/showbookings?error=" + error;
		}
		
		if(client.getStreet() == null || client.getStreet().equals("")) {
			error = "Favor de verificar el campo Fecha";
			return "redirect:/users/showbookings?error=" + error;
		}
		
		if(client.getSuburb() == null || client.getSuburb().equals("")) {
			error = "Favor de verificar el campo Fecha";
			return "redirect:/users/showbookings?error=" + error;
		}
		
		if(client.getTown() == null || client.getTown().equals("")) {
			error = "Favor de verificar el campo Fecha";
			return "redirect:/users/showbookings?error=" + error;
		}
		
		if(client.getState() == null || client.getState().equals("")) {
			error = "Favor de verificar el campo Fecha";
			return "redirect:/users/showbookings?error=" + error;
		}
		
		if(client.getEmployment() == null || client.getEmployment().equals("")) {
			error = "Favor de verificar el campo Fecha";
			return "redirect:/users/showbookings?error=" + error;
		}
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findUserByEmail(user.getUsername());
		
		ClientEntity oldClient = clientService.findByUser(userEntity);
		
		oldClient.setStreet(client.getStreet());
		oldClient.setSuburb(client.getSuburb());
		oldClient.setTown(client.getTown());
		oldClient.setState(client.getState());
		oldClient.setUser(client.getUser());
		
		if (clientService.createClient(oldClient) != null) {
			result="Se ha modificado el perfil de usuario!";
		} else {
			result="Ocurrió un error al intentar editar el perfil, vuelva a intentarlo";
		} 

		return "redirect:/users/showbookings?error="+error+"&result="+result;
	}
	
	@PostMapping("/cancelbooking")
	public String cancelBooking(@RequestParam(name = "idbooking") Integer idBooking,
			Model model) {

		String error = "";
		String result = "";
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findUserByEmail(user.getUsername());
		
		BookingEntity booking = bookingService.findBookingByIdBookingAndUserAndBookingStatusBookingStatusDescNotIn(idBooking, userEntity, "Cancelada");
		
		if (booking != null) {
			BookingStatusEntity bookingStatus = bookingStatusService.findByBookingStatusDesc("Cancelada");

			if (bookingStatus != null) {
				booking.setBookingStatus(bookingStatus);
				bookingService.createBooking(booking);
				result = "La cita ha sido cancelada";

				emailService.sendSimpleMessage("rogerdavila.stech@gmail.com", "Reservación Cancelada",
						"El usuario " + booking.getUser().getEmail() + " ha cancelado su reservación del día "
								+ booking.getDate() + " Puedes revisar el detalle en"
								+ " la siguiente liga: \n\r \n\r www.bambinocare.com");

			} else {
				error = "No se permiten cancelaciones de reservación";
			}
		} else {
			error = "No se puede cancelar la reservación solicitada";
		}

		return "redirect:/users/showbookings?error=" + error + "&result=" + result;
	}

	@GetMapping("/cancel")
	public String cancel() {
		return "redirect:/users/showbookings";
	}
	
	@GetMapping("/editBambinoForm")
	public String editBambinoForm() {
		return ViewConstants.EDIT_USER_BAMBINO;
	}
	
	public static Date getDate(Date date, int days) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); 
		calendar.add(Calendar.DAY_OF_YEAR, days);
		
		return calendar.getTime();
	}

}
