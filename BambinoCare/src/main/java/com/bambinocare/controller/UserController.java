package com.bambinocare.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bambinocare.constant.ViewConstants;
import com.bambinocare.model.entity.BambinoEntity;
import com.bambinocare.model.entity.BookingEntity;
import com.bambinocare.model.entity.BookingStatusEntity;
import com.bambinocare.model.entity.BookingTypeEntity;
import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.entity.EmergencyContactEntity;
import com.bambinocare.model.entity.EventTypeEntity;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.service.BambinoService;
import com.bambinocare.model.service.BookingService;
import com.bambinocare.model.service.BookingStatusService;
import com.bambinocare.model.service.BookingTypeService;
import com.bambinocare.model.service.ClientService;
import com.bambinocare.model.service.EmailService;
import com.bambinocare.model.service.EmergencyContactService;
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

	@Autowired
	@Qualifier("bambinoService")
	private BambinoService bambinoService;

	@Autowired
	@Qualifier("emergencyContactService")
	private EmergencyContactService emergencyContactService;

	@GetMapping("/showbookings")
	public ModelAndView showBookings(@RequestParam(required = false) String error,
			@RequestParam(required = false) String result) {

		ModelAndView mav = new ModelAndView(ViewConstants.USER_SHOW);

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userLogged = userService.findByEmail(user.getUsername());

		mav.addObject("usernameLogged", userLogged.getFirstname());
		mav.addObject("bookings", bookingService.findByUser(userLogged));
		mav.addObject("bambinos", bambinoService.findByClientUser(userLogged));
		mav.addObject("contacts", emergencyContactService.findByUser(userLogged));
		mav.addObject("client", clientService.findByUser(userLogged));
		mav.addObject("error", error);
		mav.addObject("result", result);

		return mav;
	}

	@PostMapping("/showbookingdetail")
	public String showBookingDetail(@RequestParam(name = "bookingId") Integer bookingId, Model model) {

		String error = "";
		String result = "";

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		BookingEntity booking = bookingService.findByBookingIdAndUser(bookingId, userEntity);

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
			List<BambinoEntity> bambinos = bambinoService.findByClientUser(userEntity);

			model.addAttribute("usernameLogged", userEntity.getFirstname());

			model.addAttribute("allbambinos", bambinos);
			model.addAttribute("booking", booking);
			model.addAttribute("bookingTypes", bookingTypes);
			model.addAttribute("eventTypes", eventTypes);

			model.addAttribute("result", result);
			model.addAttribute("error", error);

			return ViewConstants.BOOKING_DETAIL_SHOW;
		} else {
			error = "No se encontró la reservación solicitada o no tiene permisos para verla";
		}

		return "redirect:/users/showbookings?error=" + error + "&result=" + result;

	}

	@GetMapping("/createbookingform")
	public String showCreateBooking(@RequestParam(required = false) String result,
			@RequestParam(required = false) String error, Model model) {
		BookingEntity booking = new BookingEntity();

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		List<BookingTypeEntity> bookingTypes = bookingTypeService.findAllBookingTypes();
		List<EventTypeEntity> eventTypes = eventTypeService.findAllEventTypes();
		List<BambinoEntity> bambinos = bambinoService.findByClientUser(userEntity);

		model.addAttribute("usernameLogged", userEntity.getFirstname());

		model.addAttribute("booking", booking);
		model.addAttribute("bookingTypes", bookingTypes);
		model.addAttribute("eventTypes", eventTypes);
		model.addAttribute("allbambinos", bambinos);

		model.addAttribute("result", result);
		model.addAttribute("error", error);

		return ViewConstants.BOOKING_CREATE;
	}

	@PostMapping("/createbooking")
	public ModelAndView createBooking(@ModelAttribute(name = "booking") BookingEntity booking,
			BindingResult bindingResult, Model model) {

		ModelAndView mav = new ModelAndView();
		String error = "";
		String result = "";

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		if (booking.getDuration() == null || booking.getDuration() == 0) {
			mav = new ModelAndView("redirect:/users/createbookingform");
			error = "Favor de verificar el campo Duración";
			mav.addObject("error", error);
			return mav;
		}

		if (booking.getDate() == null) {
			mav = new ModelAndView("redirect:/users/createbookingform");
			error = "Favor de verificar el campo Fecha";
			mav.addObject("error", error);
			return mav;
		} else if (getDate(booking.getDate(), 1).before(getDate(Calendar.getInstance().getTime(), 0))) {
			mav = new ModelAndView(ViewConstants.BOOKING_CREATE);
			error = "La reservación debe realizarse al menos 1 día antes de la fecha solictada";

			List<BookingTypeEntity> bookingTypes = bookingTypeService.findAllBookingTypes();
			List<EventTypeEntity> eventTypes = eventTypeService.findAllEventTypes();
			List<BambinoEntity> bambinos = bambinoService.findByClientUser(userEntity);

			mav.addObject("allbambinos", bambinos);
			mav.addObject("usernameLogged", userEntity.getFirstname());

			mav.addObject("booking", booking);
			mav.addObject("bookingTypes", bookingTypes);
			mav.addObject("eventTypes", eventTypes);

			mav.addObject("error", error);

			return mav;
		}

		if (booking.getHour() == null || booking.getHour().equals("")) {
			mav = new ModelAndView("redirect:/users/createbookingform");
			error = "Favor de verificar el campo Hora";
			mav.addObject("error", error);
			return mav;
		}

		ClientEntity client = clientService.findByUserEmail(user.getUsername());

		booking.setClient(client);

		if (bambinoService.findByClient(client).isEmpty()) {
			mav = new ModelAndView(ViewConstants.BOOKING_CREATE);
			error = "Favor dar de alta a sus bambinos";

			List<BookingTypeEntity> bookingTypes = bookingTypeService.findAllBookingTypes();
			List<EventTypeEntity> eventTypes = eventTypeService.findAllEventTypes();
			List<BambinoEntity> bambinos = bambinoService.findByClientUser(userEntity);

			mav.addObject("allbambinos", bambinos);
			mav.addObject("usernameLogged", userEntity.getFirstname());

			mav.addObject("booking", booking);
			mav.addObject("bookingTypes", bookingTypes);
			mav.addObject("eventTypes", eventTypes);

			mav.addObject("error", error);

			return mav;
		}

		List<EmergencyContactEntity> emergencyContacts = emergencyContactService.findByClient(client);

		if (emergencyContacts.isEmpty() || emergencyContacts.size() < 2) {
			mav = new ModelAndView(ViewConstants.BOOKING_CREATE);
			error = "Favor dar de alta al menos 2 contactos de emergencia";
			List<BookingTypeEntity> bookingTypes = bookingTypeService.findAllBookingTypes();
			List<EventTypeEntity> eventTypes = eventTypeService.findAllEventTypes();
			List<BambinoEntity> bambinos = bambinoService.findByClientUser(userEntity);

			mav.addObject("allbambinos", bambinos);
			mav.addObject("usernameLogged", userEntity.getFirstname());

			mav.addObject("booking", booking);
			mav.addObject("bookingTypes", bookingTypes);
			mav.addObject("eventTypes", eventTypes);

			mav.addObject("error", error);

			return mav;
		}

		if (booking.getBambino() != null) {
			List<String> bambinoIds = new ArrayList<>();
			for (BambinoEntity bambino : booking.getBambino()) {
				bambinoIds.add(bambino.getBambinoId().toString());
			}
			booking.setBambinoId(bambinoIds);
		}

		if (booking.getBambinoId().size() <= 0) {
			mav = new ModelAndView(ViewConstants.BOOKING_CREATE);
			error = "Favor de elegir al menos un bambino";
			List<BookingTypeEntity> bookingTypes = bookingTypeService.findAllBookingTypes();
			List<EventTypeEntity> eventTypes = eventTypeService.findAllEventTypes();
			List<BambinoEntity> bambinos = bambinoService.findByClientUser(userEntity);

			mav.addObject("allbambinos", bambinos);
			mav.addObject("usernameLogged", userEntity.getFirstname());

			mav.addObject("booking", booking);
			mav.addObject("bookingTypes", bookingTypes);
			mav.addObject("eventTypes", eventTypes);

			mav.addObject("error", error);

			return mav;
		}

		booking.setBambino(bambinoService.findBambinosByBambinoIdAndUser(booking.getBambinoId(), userEntity));

		if (booking.getBambino().isEmpty()) {
			mav = new ModelAndView(ViewConstants.BOOKING_CREATE);
			error = "Ocurrió un error al intentar agregar a los bambinos";
			List<BookingTypeEntity> bookingTypes = bookingTypeService.findAllBookingTypes();
			List<EventTypeEntity> eventTypes = eventTypeService.findAllEventTypes();
			List<BambinoEntity> bambinos = bambinoService.findByClientUser(userEntity);

			mav.addObject("allbambinos", bambinos);
			mav.addObject("usernameLogged", userEntity.getFirstname());

			mav.addObject("booking", booking);
			mav.addObject("bookingTypes", bookingTypes);
			mav.addObject("eventTypes", eventTypes);

			mav.addObject("error", error);

			return mav;
		}

		BookingStatusEntity bookingStatus = bookingStatusService.findByBookingStatusDesc("Abierta");
		booking.setBookingStatus(bookingStatus);

		booking.setCost(booking.getDuration() * 200);

		booking.setDate(getDate(booking.getDate(), 1));

		if (bookingService.createBooking(booking) != null) {
			try {
				emailService.sendHTMLMessage("rogerdavila.stech@gmail.com", "BambinoCare - Nueva reservación",
						"<html><body><p>El usuario " + booking.getClient().getUser().getEmail()
								+ " ha agendado una nueva cita: </p><table border=\"1\">"
								+ "<thead><tr><th>Cliente</th><th>Servicio</th><th>Bambinos</th><th>Edades</th>"
								+ "<th>Fecha</th><th>Horario</th><th>Lugar</th></tr></thead><tbody><tr><td>"
								+ booking.getClient().getUser().getEmail() + "</td><td>"
								+ booking.getBookingType().getBookingTypeDesc()
								+ "</td><td>Hardcode</td><td>Hardcode</td><td>" + booking.getDate() + "</td><td>"
								+ booking.getDuration() + "</td><td>" + booking.getClient().getStreet()
								+ booking.getClient().getNeighborhood() + booking.getClient().getState() + "</td></tr>"
								+ "</tbody></table><p>Puedes revisar el detalle en"
								+ " la siguiente liga: \n\r \n\r www.bambinocare.com</p></body></html>");
			} catch (MessagingException e) {
				e.printStackTrace();
			}

			emailService.sendSimpleMessage(booking.getClient().getUser().getEmail(), "BambinoCare - Nueva reservación",
					"Hemos recibido tu reservación y estamos buscando tu mejor opción. En breve\n"
							+ "recibirás un correo para informarte el perfil de la Bambinaia que estará asistiendo\n"
							+ "a tu hogar.");

			result = "La reservación se ha realizado exitosamente.";
		} else {
			mav = new ModelAndView("redirect:/users/createbookingform");
			error = "No se ha podido realizar la reservación, intente nuevamente";
			mav.addObject("error", error);
			return mav;
		}

		mav = new ModelAndView("redirect:/users/showbookings");
		mav.addObject("result", result);
		return mav;
	}

	@GetMapping("/editbookingform")
	public String showEditBooking(@RequestParam(required = false) String result,
			@RequestParam(required = false) String error, @RequestParam(required = true) Integer bookingId,
			Model model) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		BookingEntity booking = bookingService.findByBookingIdAndUserAndBookingStatusBookingStatusDescNotIn(bookingId,
				userEntity, "Cancelada");

		if (booking == null) {
			error = "La reservación solicitada no existe o no tienes permisos para visualizarla o ya se encuentra cancelada";
			return "redirect:/users/showbookings?error=" + error;
		}

		if (booking.getBambino() != null) {
			List<String> bambinoIds = new ArrayList<>();
			for (BambinoEntity bambino : booking.getBambino()) {
				bambinoIds.add(bambino.getBambinoId().toString());
			}
			booking.setBambinoId(bambinoIds);
		}

		List<BookingTypeEntity> bookingTypes = bookingTypeService.findAllBookingTypes();
		List<EventTypeEntity> eventTypes = eventTypeService.findAllEventTypes();
		List<BambinoEntity> bambinos = bambinoService.findByClientUser(userEntity);

		model.addAttribute("allbambinos", bambinos);
		model.addAttribute("usernameLogged", userEntity.getFirstname());

		model.addAttribute("booking", booking);
		model.addAttribute("bookingTypes", bookingTypes);
		model.addAttribute("eventTypes", eventTypes);

		model.addAttribute("result", result);
		model.addAttribute("error", error);

		return ViewConstants.BOOKING_EDIT;
	}

	@PostMapping("/editbooking")
	public ModelAndView editBooking(@ModelAttribute(name = "booking") BookingEntity booking,
			BindingResult bindingResult, Model model) {

		ModelAndView mav = new ModelAndView();

		String error = "";
		String result = "";

		if (booking.getDuration() == null || booking.getDuration() == 0) {
			error = "Favor de verificar el campo Duración";
			mav = new ModelAndView("redirect:/users/showbookings");
			mav.addObject("error", error);
			return mav;
		}

		if (booking.getDate() == null) {
			error = "Favor de verificar el campo Fecha";
			mav = new ModelAndView("redirect:/users/showbookings");
			mav.addObject("error", error);
			return mav;
		} else if (getDate(booking.getDate(), 1).before(getDate(Calendar.getInstance().getTime(), 0))) {
			error = "La reservación debe realizarse al menos 1 día antes de la fecha solictada";
			mav = new ModelAndView("redirect:/users/showbookings");
			mav.addObject("error", error);
			return mav;
		}

		if (booking.getHour() == null || booking.getHour().equals("")) {
			error = "Favor de verificar el campo Hora";
			mav = new ModelAndView("redirect:/users/showbookings");
			mav.addObject("error", error);
			return mav;
		}

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		if (booking.getBambinoId().size() <= 0) {
			error = "Favor de elegir al menos un bambino";
			mav = new ModelAndView("redirect:/users/showbookings");
			mav.addObject("error", error);
			return mav;
		}

		booking.setBambino(bambinoService.findBambinosByBambinoIdAndUser(booking.getBambinoId(), userEntity));

		if (booking.getBambino().isEmpty()) {
			error = "Ocurrió un error al intentar agregar a los bambinos";
			mav = new ModelAndView("redirect:/users/showbookings");
			mav.addObject("error", error);
			return mav;
		}

		BookingEntity oldBooking = bookingService.findByBookingIdAndUser(booking.getBookingId(), userEntity);

		oldBooking.setDuration(booking.getDuration());
		oldBooking.setDate(getDate(booking.getDate(), 1));
		oldBooking.setHour(booking.getHour());
		oldBooking.setCost(booking.getDuration() * 200);
		oldBooking.setBambino(booking.getBambino());

		if (bookingService.createBooking(oldBooking) != null) {
			emailService.sendSimpleMessage("rogerdavila.stech@gmail.com", "Reservación Modificada",
					"El usuario " + oldBooking.getClient().getUser().getEmail()
							+ " ha modificado la reservación del día " + oldBooking.getDate()
							+ ". Puedes revisar el detalle en" + " la siguiente liga: \n\r \n\r www.bambinocare.com");
			result = "La reservación fue modificada con éxito!";
		} else {
			result = "Ocurrió un error al intentar editar la reservación, vuelva a intentarlo";
		}

		mav = new ModelAndView("redirect:/users/showbookings");
		mav.addObject("error", error);
		mav.addObject("result", result);
		return mav;
	}

	@PostMapping("/edituser")
	public String edituser(@ModelAttribute(name = "client") ClientEntity client, BindingResult bindingResult,
			Model model) {

		String error = "";
		String result = "";

		if (client.getUser().getEmail() == null || client.getUser().getEmail().equals("")) {
			error = "Favor de verificar el email";
			return "redirect:/users/showbookings?error=" + error;
		}

		if (client.getUser().getFirstname() == null || client.getUser().getFirstname().equals("")) {
			error = "Favor de verificar el Nombre";
			return "redirect:/users/showbookings?error=" + error;
		}

		if (client.getUser().getLastname() == null || client.getUser().getLastname().equals("")) {
			error = "Favor de verificar el Apellido";
			return "redirect:/users/showbookings?error=" + error;
		}

		if (client.getUser().getPhone() == null || client.getUser().getPhone().equals("")) {
			error = "Favor de verificar el email";
			return "redirect:/users/showbookings?error=" + error;
		}

		if (client.getStreet() == null || client.getStreet().equals("")) {
			error = "Favor de verificar el campo Fecha";
			return "redirect:/users/showbookings?error=" + error;
		}

		if (client.getNeighborhood() == null || client.getNeighborhood().equals("")) {
			error = "Favor de verificar el campo Fecha";
			return "redirect:/users/showbookings?error=" + error;
		}

		if (client.getCity() == null || client.getCity().equals("")) {
			error = "Favor de verificar el campo Fecha";
			return "redirect:/users/showbookings?error=" + error;
		}

		if (client.getState() == null || client.getState().equals("")) {
			error = "Favor de verificar el campo Fecha";
			return "redirect:/users/showbookings?error=" + error;
		}

		if (client.getJob() == null || client.getJob().equals("")) {
			error = "Favor de verificar el campo Fecha";
			return "redirect:/users/showbookings?error=" + error;
		}
		if (!client.getUser().getPasswordConfirm().equals(client.getUser().getPassword())) {
			error = "La contraseña y la confirmación de contraseña no coínciden";
			return "redirect:/users/showbookings?error=" + error;
		}
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		ClientEntity oldClient = clientService.findByUser(userEntity);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		oldClient.setStreet(client.getStreet());
		oldClient.setNeighborhood(client.getNeighborhood());
		oldClient.setCity(client.getCity());
		oldClient.setState(client.getState());
		oldClient.getUser().setFirstname(client.getUser().getFirstname());
		oldClient.getUser().setLastname(client.getUser().getLastname());
		oldClient.getUser().setEmail(client.getUser().getEmail());
		oldClient.getUser().setPhone(client.getUser().getPhone());
		oldClient.getUser().setPassword(passwordEncoder.encode(client.getUser().getPassword()));

		if (clientService.saveClient(oldClient) != null) {
			result = "Se ha modificado el perfil de usuario!";
		} else {
			result = "Ocurrió un error al intentar editar el perfil, vuelva a intentarlo";
		}

		if (clientService.createClient(oldClient) != null) {
			result = "Se ha modificado el perfil de usuario!";
		} else {
			result = "Ocurrió un error al intentar editar el perfil, vuelva a intentarlo";
		}

		return "redirect:/users/showbookings?error=" + error + "&result=" + result;
	}

	@PostMapping("/cancelbooking")
	public String cancelBooking(@RequestParam(name = "bookingid") Integer bookingId, Model model) {

		String error = "";
		String result = "";

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		BookingEntity booking = bookingService.findByBookingIdAndUserAndBookingStatusBookingStatusDescNotIn(bookingId,
				userEntity, "Cancelada");

		if (booking != null) {
			BookingStatusEntity bookingStatus = bookingStatusService.findByBookingStatusDesc("Cancelada");

			if (bookingStatus != null) {
				booking.setBookingStatus(bookingStatus);
				bookingService.createBooking(booking);
				result = "La cita ha sido cancelada";

				emailService.sendSimpleMessage("rogerdavila.stech@gmail.com", "Reservación Cancelada",
						"El usuario " + booking.getClient().getUser().getEmail()
								+ " ha cancelado su reservación del día " + booking.getDate()
								+ " Puedes revisar el detalle en"
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

	@PostMapping("/newbambino")
	public String newbambino(@ModelAttribute(name = "bambino") BambinoEntity bambino, BindingResult bindingResult,
			Model model) {

		String error = "";
		String result = "";

		if (bambino.getFirstname() == null || bambino.getFirstname().equalsIgnoreCase("")) {
			error = "Favor de verificar el Nombre";
			return "redirect:/users/createbookingform?error=" + error;
		} else if (bambino.getLastname() == null || bambino.getLastname().equalsIgnoreCase("")) {
			error = "Favor de verificar Apellido";
			return "redirect:/users/showbookings?error=" + error;
		} else if (bambino.getAge() == null) {
			error = "No puedes reservar en fechas pasadas";
			return "redirect:/users/createbookingform?error=" + error;
		} else if (bambino.getGrade() == null || bambino.getGrade().equals("")) {
			error = "Favor de verificar el campo Hora";
			return "redirect:/users/createbookingform?error=" + error;
		}

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ClientEntity client = clientService.findByUserEmail(user.getUsername());

		bambino.setClient(client);

		if (bambinoService.createBambino(bambino) != null) {
			result = "Se ha agregado al Bambino exitosamente.";
		} else {
			error = "No se ha podido agregar al Bambino, intente nuevamente";
			return "redirect:/users/createbookingform?error=" + error;
		}

		return "redirect:/users/showbookings?result=" + result;
	}

	@GetMapping("/createbambinoform")
	public String createbambinoform(@RequestParam(required = false) String result,
			@RequestParam(required = false) String error, Model model) {
		BambinoEntity bambino = new BambinoEntity();

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());
		model.addAttribute("usernameLogged", userEntity.getFirstname());

		model.addAttribute("bambino", bambino);
		model.addAttribute("result", result);
		model.addAttribute("error", error);

		return ViewConstants.CREATE_BAMBINO;
	}

	@GetMapping("/editBambinoForm")
	public String editBambinoForm(@RequestParam(required = false) String result,
			@RequestParam(required = false) String error, @RequestParam(required = true) Integer bambinoId,
			Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		BambinoEntity bambino = bambinoService.findByBambinoIdAndUser(bambinoId, userEntity);

		model.addAttribute("usernameLogged", userEntity.getFirstname());

		model.addAttribute("bambino", bambino);

		model.addAttribute("result", result);
		model.addAttribute("error", error);

		return ViewConstants.EDIT_BAMBINO;
	}

	@PostMapping("/editBambino")
	public String editbambino(@ModelAttribute(name = "bambino") BambinoEntity bambino, BindingResult bindingResult,
			Model model) {

		String error = "";
		String result = "";

		if (bambino.getFirstname() == null || bambino.getFirstname().equals("")) {
			error = "Favor de verificar el nombre";
			return "redirect:/users/showbookings?error=" + error;
		} else if (bambino.getLastname() == null || bambino.getLastname().equals("")) {
			error = "Favor de verificar el apellido";
			return "redirect:/users/showbookings?error=" + error;
		} else if (bambino.getAge() == null) {
			error = "Favor de verificar la edad";
			return "redirect:/users/showbookings?error=" + error;
		} else if (bambino.getGrade() == null || bambino.getGrade().equals("")) {
			error = "Favor de verificar el grado escolar";
			return "redirect:/users/showbookings?error=" + error;
		}

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		BambinoEntity oldbambino = bambinoService.findByBambinoIdAndUser(bambino.getBambinoId(), userEntity);

		oldbambino.setFirstname(bambino.getFirstname());
		oldbambino.setLastname(bambino.getLastname());
		oldbambino.setAge(bambino.getAge());
		oldbambino.setGrade(bambino.getGrade());
		oldbambino.setMedicalSituation(bambino.getMedicalSituation());
		oldbambino.setComments(bambino.getComments());

		if (bambinoService.createBambino(oldbambino) != null) {
			result = "El bambino fue modificado con éxito!";
		} else {
			result = "Ocurrió un error al intentar editar el bambino, vuelva a intentarlo";
		}

		return "redirect:/users/showbookings?error=" + error + "&result=" + result;
	}

	// contactos

	@GetMapping("/createcontactform")
	public String createcontactform(@RequestParam(required = false) String result,
			@RequestParam(required = false) String error, Model model) {
		EmergencyContactEntity contact = new EmergencyContactEntity();

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());
		model.addAttribute("usernameLogged", userEntity.getFirstname());

		model.addAttribute("contact", contact);
		model.addAttribute("result", result);
		model.addAttribute("error", error);

		return ViewConstants.CREATE_CONTACT;
	}

	@PostMapping("/newcontact")
	public String newcontact(@ModelAttribute(name = "contact") EmergencyContactEntity contact,
			BindingResult bindingResult, Model model) {

		String error = "";
		String result = "";

		if (contact.getFirstname() == null || contact.getFirstname().equals("")) {
			error = "Favor de verificar el Nombre";
			return "redirect:/users/showbookings?error=" + error;
		} else if (contact.getLastname() == null || contact.getLastname().equals("")) {
			error = "Favor de verificar el Apellido";
			return "redirect:/users/showbookings?error=" + error;
		} else if (contact.getPhone() == null || contact.getPhone().equals("")) {
			error = "Favor de verificar el email";
			return "redirect:/users/showbookings?error=" + error;
		} else if (contact.getStreet() == null || contact.getStreet().equals("")) {
			error = "Favor de verificar la calle";
			return "redirect:/users/showbookings?error=" + error;
		} else if (contact.getNeighborhood() == null || contact.getNeighborhood().equals("")) {
			error = "Favor de verificar la colonia";
			return "redirect:/users/showbookings?error=" + error;
		} else if (contact.getCity() == null || contact.getCity().equals("")) {
			error = "Favor de verificar el Municipio";
			return "redirect:/users/showbookings?error=" + error;
		} else if (contact.getState() == null || contact.getState().equals("")) {
			error = "Favor de verificar el Estado";
			return "redirect:/users/showbookings?error=" + error;
		} else if (contact.getRelationship() == null || contact.getRelationship().equals("")) {
			error = "Favor de verificar el parentesco";
			return "redirect:/users/showbookings?error=" + error;
		}

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ClientEntity client = clientService.findByUserEmail(user.getUsername());

		contact.setClient(client);

		if (emergencyContactService.createContact(contact) != null) {
			result = "El contacto se ha creado exitosamente.";
		} else {
			error = "No se ha podido crear el contacto, intente nuevamente";
			return "redirect:/users/createbookingform?error=" + error;
		}

		return "redirect:/users/showbookings?result=" + result;
	}

	@GetMapping("/editcontactForm")
	public String editContactForm(@RequestParam(required = false) String result,
			@RequestParam(required = false) String error, @RequestParam(required = true) Integer contactId,
			Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		EmergencyContactEntity contact = emergencyContactService.findByContactIdAndUser(contactId, userEntity);

		model.addAttribute("usernameLogged", userEntity.getFirstname());

		model.addAttribute("contact", contact);

		model.addAttribute("result", result);
		model.addAttribute("error", error);

		return ViewConstants.EDIT_CONTACT;
	}

	@PostMapping("/editContact")
	public String editcontact(@ModelAttribute(name = "bambino") EmergencyContactEntity contact,
			BindingResult bindingResult, Model model) {

		String error = "";
		String result = "";

		if (contact.getFirstname() == null || contact.getFirstname().equals("")) {
			error = "Favor de verificar el Nombre";
			return "redirect:/users/showbookings?error=" + error;
		} else if (contact.getLastname() == null || contact.getLastname().equals("")) {
			error = "Favor de verificar el Apellido";
			return "redirect:/users/showbookings?error=" + error;
		} else if (contact.getPhone() == null || contact.getPhone().equals("")) {
			error = "Favor de verificar el email";
			return "redirect:/users/showbookings?error=" + error;
		} else if (contact.getStreet() == null || contact.getStreet().equals("")) {
			error = "Favor de verificar la calle";
			return "redirect:/users/showbookings?error=" + error;
		} else if (contact.getNeighborhood() == null || contact.getNeighborhood().equals("")) {
			error = "Favor de verificar la colonia";
			return "redirect:/users/showbookings?error=" + error;
		} else if (contact.getCity() == null || contact.getCity().equals("")) {
			error = "Favor de verificar el Municipio";
			return "redirect:/users/showbookings?error=" + error;
		} else if (contact.getState() == null || contact.getState().equals("")) {
			error = "Favor de verificar el Estado";
			return "redirect:/users/showbookings?error=" + error;
		} else if (contact.getRelationship() == null || contact.getRelationship().equals("")) {
			error = "Favor de verificar el parentesco";
			return "redirect:/users/showbookings?error=" + error;
		}

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		EmergencyContactEntity oldcontact = emergencyContactService.findByContactIdAndUser(contact.getContactId(),
				userEntity);

		oldcontact.setFirstname(contact.getFirstname());
		oldcontact.setLastname(contact.getLastname());
		oldcontact.setPhone(contact.getPhone());
		oldcontact.setStreet(contact.getStreet());
		oldcontact.setNeighborhood(contact.getNeighborhood());
		oldcontact.setCity(contact.getCity());
		oldcontact.setState(contact.getState());
		oldcontact.setRelationship(contact.getRelationship());

		if (emergencyContactService.createContact(oldcontact) != null) {
			result = "El contacto fue modificado con éxito!";
		} else {
			result = "Ocurrió un error al intentar editar el contacto, vuelva a intentarlo";
		}

		return "redirect:/users/showbookings?error=" + error + "&result=" + result;
	}

	public static Date getDate(Date date, int days) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);

		return calendar.getTime();
	}

}
