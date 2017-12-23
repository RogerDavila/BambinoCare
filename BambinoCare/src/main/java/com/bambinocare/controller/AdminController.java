package com.bambinocare.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bambinocare.constant.ViewConstants;
import com.bambinocare.model.entity.BambinoEntity;
import com.bambinocare.model.entity.BookingEntity;
import com.bambinocare.model.entity.BookingStatusEntity;
import com.bambinocare.model.entity.BookingTypeEntity;
import com.bambinocare.model.entity.CostEntity;
import com.bambinocare.model.entity.EventTypeEntity;
import com.bambinocare.model.entity.NannyEntity;
import com.bambinocare.model.entity.RoleEntity;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.service.BambinoService;
import com.bambinocare.model.service.BookingService;
import com.bambinocare.model.service.BookingStatusService;
import com.bambinocare.model.service.BookingTypeService;
import com.bambinocare.model.service.CostService;
import com.bambinocare.model.service.EmailService;
import com.bambinocare.model.service.EventTypeService;
import com.bambinocare.model.service.NannyService;
import com.bambinocare.model.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("bookingService")
	private BookingService bookingService;

	@Autowired
	@Qualifier("emailService")
	private EmailService emailService;

	@Autowired
	@Qualifier("nannyService")
	private NannyService nannyService;

	@Autowired
	@Qualifier("bookingTypeService")
	private BookingTypeService bookingTypeService;

	@Autowired
	@Qualifier("eventTypeService")
	private EventTypeService eventTypeService;

	@Autowired
	@Qualifier("bookingStatusService")
	private BookingStatusService bookingStatusService;

	@Autowired
	@Qualifier("bambinoService")
	private BambinoService bambinoService;

	@Autowired
	@Qualifier("costService")
	private CostService costService;

	@GetMapping("/showbookings")
	public ModelAndView showBookings(@RequestParam(required = false) String error,
			@RequestParam(required = false) String result) {

		ModelAndView mav = new ModelAndView(ViewConstants.ADMIN_VIEW);

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userLogged = userService.findByEmail(user.getUsername());

		mav.addObject("usernameLogged", userLogged.getFirstname());
		mav.addObject("bookings", bookingService.findAllBookings());
		mav.addObject("nannies", nannyService.findAllNannies());
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

		BookingEntity booking = bookingService.findByBookingId(bookingId);

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
			model.addAttribute("usernameLogged", userEntity.getFirstname());
			model.addAttribute("totalCost", booking.getCost());

			model.addAttribute("booking", booking);
			model.addAttribute("bookingTypes", bookingTypes);
			model.addAttribute("eventTypes", eventTypes);

			model.addAttribute("result", result);
			model.addAttribute("error", error);

			return ViewConstants.BOOKING_DETAIL_ADMIN_SHOW;
		} else {
			error = "No se encontró la reservación solicitada o no tiene permisos para verla";
		}

		return "redirect:/admin/showbookings?error=" + error + "&result=" + result;

	}

	@GetMapping("/editbookingform")
	public String showEditBooking(@RequestParam(required = false) String result,
			@RequestParam(required = false) String error, @RequestParam(required = true) Integer bookingId,
			Model model) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		BookingEntity booking = bookingService.findByBookingIdAndBookingStatusBookingStatusDescNotIn(bookingId,
				"Cancelada");

		if (booking == null) {
			error = "La reservación solicitada no existe o no tienes permisos para visualizarla o ya se encuentra cancelada";
			return "redirect:/admin/showbookings?error=" + error;
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
		List<BambinoEntity> bambinos = bambinoService.findByClientUser(booking.getClient().getUser());
		List<CostEntity> costs = costService.findAllByOrderByHourQuantity();

		model.addAttribute("allbambinos", bambinos);
		model.addAttribute("usernameLogged", userEntity.getFirstname());
		model.addAttribute("costs", costs);
		model.addAttribute("totalCost", booking.getCost());

		model.addAttribute("booking", booking);
		model.addAttribute("bookingTypes", bookingTypes);
		model.addAttribute("eventTypes", eventTypes);

		model.addAttribute("result", result);
		model.addAttribute("error", error);

		return ViewConstants.BOOKING_ADMIN_EDIT;
	}

	@PostMapping("/editbooking")
	public ModelAndView editBooking(@ModelAttribute(name = "booking") BookingEntity booking,
			BindingResult bindingResult, Model model) {

		ModelAndView mav = new ModelAndView();

		String error = "";
		String result = "";

		if (booking.getDuration() == null || booking.getDuration() == 0) {
			error = "Favor de verificar el campo Duración";
			mav = new ModelAndView("redirect:/admin/editbookingform");
			mav.addObject("error", error);
			mav.addObject("bookingId", booking.getBookingId());
			return mav;
		}

		if (booking.getDate() == null) {
			error = "Favor de verificar el campo Fecha";
			mav = new ModelAndView("redirect:/admin/editbookingform");
			mav.addObject("error", error);
			mav.addObject("bookingId", booking.getBookingId());
			return mav;
		} else if (getDate(booking.getDate(), 1).before(getDate(Calendar.getInstance().getTime(), 0))) {
			error = "La reservación debe realizarse al menos 1 día antes de la fecha solictada";
			mav = new ModelAndView("redirect:/admin/editbookingform");
			mav.addObject("error", error);
			mav.addObject("bookingId", booking.getBookingId());
			return mav;
		}

		if (booking.getHour() == null || booking.getHour().equals("")) {
			error = "Favor de verificar el campo Hora";
			mav = new ModelAndView("redirect:/admin/editbookingform");
			mav.addObject("error", error);
			mav.addObject("bookingId", booking.getBookingId());
			return mav;
		}

		if (booking.getBambinoId().size() <= 0) {
			error = "Favor de elegir al menos un bambino";
			mav = new ModelAndView("redirect:/admin/editbookingform");
			mav.addObject("error", error);
			mav.addObject("bookingId", booking.getBookingId());
			return mav;
		}

		BookingEntity oldBooking = bookingService.findByBookingId(booking.getBookingId());

		booking.setBambino(bambinoService.findBambinosByBambinoIdAndUser(booking.getBambinoId(),
				oldBooking.getClient().getUser()));

		if (booking.getBambino().isEmpty()) {
			error = "Ocurrió un error al intentar agregar a los bambinos";
			mav = new ModelAndView("redirect:/users/editbookingform");
			mav.addObject("error", error);
			return mav;
		}

		oldBooking.setDuration(booking.getDuration());
		oldBooking.setDate(getDate(booking.getDate(), 1));
		oldBooking.setHour(booking.getHour());
		oldBooking.setBambino(booking.getBambino());
		oldBooking.setCost(costService.calculateTotalCost(booking.getDuration(), booking.getBambino().size()));

		if (bookingService.createBooking(oldBooking) != null) {
			emailService.sendSimpleMessage(oldBooking.getClient().getUser().getEmail(), "rogerdavila.stech@gmail.com",
					"Reservación Modificada",
					"Su reservación del día " + oldBooking.getDate()
							+ "ha sido modificada. Puedes revisar el detalle en"
							+ " la siguiente liga: \n\r \n\r www.bambinocare.com");
			result = "La reservación fue modificada con éxito!";
		} else {
			result = "Ocurrió un error al intentar editar la reservación, vuelva a intentarlo";
		}

		mav = new ModelAndView("redirect:/admin/showbookings");
		mav.addObject("error", error);
		mav.addObject("result", result);
		return mav;
	}

	@PostMapping("/cancelbooking")
	public String cancelBooking(@RequestParam(name = "bookingId") Integer bookingId, Model model) {

		String error = "";
		String result = "";

		BookingEntity booking = bookingService.findByBookingIdAndBookingStatusBookingStatusDescNotIn(bookingId,
				"Cancelada");

		if (booking != null) {
			BookingStatusEntity bookingStatus = bookingStatusService.findByBookingStatusDesc("Cancelada");

			if (bookingStatus != null) {
				booking.setBookingStatus(bookingStatus);
				bookingService.createBooking(booking);
				result = "La cita ha sido cancelada";

				emailService.sendSimpleMessage(booking.getClient().getUser().getEmail(), "rogerdavila.stech@gmail.com",
						"Reservación Cancelada",
						"Su reservación del día del día " + booking.getDate()
								+ "  ha sido cancelada. Puedes revisar el detalle en"
								+ " la siguiente liga: \n\r \n\r www.bambinocare.com");

			} else {
				error = "No se permiten cancelaciones de reservación";
			}
		} else {
			error = "No se puede cancelar la reservación solicitada";
		}

		return "redirect:/admin/showbookings?error=" + error + "&result=" + result;
	}

	@GetMapping("/cancel")
	public String cancel() {
		return "redirect:/admin/showbookings";
	}

	@PostMapping("/approvebooking")
	public String approveBooking(@RequestParam(name = "bookingId", required = false) Integer bookingId,
			@ModelAttribute(name = "nanny") NannyEntity nanny, BindingResult bindingResult, Model model) {

		String error = "";
		String result = "";

		BookingEntity booking = bookingService.findByBookingIdAndBookingStatusBookingStatusDescNotIn(bookingId,
				"Cancelada", "Agendada", "Rechazada");

		if (booking != null) {

			if (nanny.getNannyId() != null && booking.getNanny() == null) {
				booking.setNanny(nanny);
			} else if (booking.getNanny() == null) {
				NannyEntity nannyToAssign = new NannyEntity();
				List<NannyEntity> nannies = nannyService.findAllNannies();
				model.addAttribute("nanny", nannyToAssign);
				model.addAttribute("nannies", nannies);
				model.addAttribute("bookingId", booking.getBookingId());
				return ViewConstants.NANNY_ASSIGN;
			}

			bookingService.createBooking(booking);
			BookingStatusEntity bookingStatus = bookingStatusService.findByBookingStatusDesc("Agendada");

			if (bookingStatus != null) {
				booking.setBookingStatus(bookingStatus);
				bookingService.createBooking(booking);
				result = "La cita ha sido agendada";

				emailService.sendSimpleMessage(booking.getClient().getUser().getEmail(), "rogerdavila.stech@gmail.com",
						"Reservación Agendada",
						"Su reservación del día del día " + booking.getDate()
								+ "  ha sido agendada. Puedes revisar el detalle en"
								+ " la siguiente liga: \n\r \n\r www.bambinocare.com");

			} else {
				error = "No se permite agendar esta reservación";
			}
		} else {
			error = "No se puede agendar la reservación solicitada";
		}

		return "redirect:/admin/showbookings?error=" + error + "&result=" + result;
	}

	@PostMapping("/rejectbooking")
	public String rejectBooking(@RequestParam(name = "bookingId") Integer bookingId, Model model) {

		String error = "";
		String result = "";

		BookingEntity booking = bookingService.findByBookingIdAndBookingStatusBookingStatusDescNotIn(bookingId,
				"Cancelada", "Agendada", "Rechazada");

		if (booking != null) {
			BookingStatusEntity bookingStatus = bookingStatusService.findByBookingStatusDesc("Rechazada");

			if (bookingStatus != null) {
				booking.setBookingStatus(bookingStatus);
				bookingService.createBooking(booking);
				result = "La cita ha sido rechazada";

				emailService.sendSimpleMessage(booking.getClient().getUser().getEmail(), "rogerdavila.stech@gmail.com",
						"Reservación Rechazada",
						"Su reservación del día " + booking.getDate()
								+ "  ha sido rechazada. Puedes revisar el detalle en"
								+ " la siguiente liga: \n\r \n\r www.bambinocare.com");

			} else {
				error = "No se permite rechazar la reservación solicitada";
			}
		} else {
			error = "No se puede rechazar la reservación solicitada";
		}

		return "redirect:/admin/showbookings?error=" + error + "&result=" + result;
	}

	@GetMapping("/createNannyForm")
	public String createNannyForm(@RequestParam(required = false) String result,
			@RequestParam(required = false) String error, Model model) {
		NannyEntity nanny = new NannyEntity();

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		model.addAttribute("usernameLogged", userEntity.getFirstname());
		model.addAttribute("nanny", nanny);
		model.addAttribute("result", result);
		model.addAttribute("error", error);

		return ViewConstants.CREATE_NANNY;
	}

	// newnanny

	@PostMapping("/newnanny")
	public String newbambino(@ModelAttribute(name = "nanny") NannyEntity nanny, BindingResult bindingResult,
			Model model) {

		String error = "";
		String result = "";

		if (nanny.getUser().getFirstname() == null || nanny.getUser().getFirstname().equalsIgnoreCase("")) {
			error = "Favor de verificar el Nombre";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getUser().getLastname() == null || nanny.getUser().getLastname().equalsIgnoreCase("")) {
			error = "Favor de verificar Apellido";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getAge() == null) {
			error = "Favor de verficar la edad de la Nanny";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getUser().getEmail() == null || nanny.getUser().getEmail().equalsIgnoreCase("")) {
			error = "Favor de verificar Apellido";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getUser().getPhone() == null || nanny.getUser().getPhone().equalsIgnoreCase("")) {
			error = "Favor de verificar Apellido";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getStreet() == null) {
			error = "Favor de verficar la calle";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getNeighborhood() == null) {
			error = "Favor de verficar la Colonia";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getNeighborhood() == null) {
			error = "Favor de verficar la Ciudad";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getState() == null) {
			error = "Favor de verficar el Estado";
			return "redirect:/users/createNannyForm?error=" + error;
		} else if (nanny.getDegree() == null) {
			error = "Favor de verficar ultimo grado de estudios";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getSchool() == null) {
			error = "Favor de verficar la Escuela";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getCourse() == null) {
			error = "Favor de verficar los Certificaciones o Capacitaciones";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getQualities() == null) {
			error = "Favor de verficar las Habilidades o Cualidades";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getHobbies() == null) {
			error = "Favor de verficar los Pasatiempos";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getBambinoReason() == null) {
			error = "Favor de verficar las Razones por las cuales les gustaria pertenecer a BambinoCare";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getChildrenReason() == null) {
			error = "Favor de verficar las Razones por las cuales consideran que trabajar con niños es su vocación";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getComments() == null) {
			error = "Favor de verficar las observaciones";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getIfeFile() == null) {
			error = "Favor de verficar el IFE";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getCurpFile() == null) {
			error = "Favor de verficar el CURP";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getDegreeFile() == null) {
			error = "Favor de verificar la Cedula o Titulo";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (userService.userExist(nanny.getUser().getEmail())) {
			error = "El email introducido ya existe";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getUser().getEmail() == null || nanny.getUser().getEmail().equals("")) {
			error = "Favor de verificar el campo Correo";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getUser().getPassword() == null || nanny.getUser().getPassword().equals("")) {
			error = "Favor de verificar el campo Contraseña";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (!nanny.getUser().getPasswordConfirm().equals(nanny.getUser().getPassword())) {
			error = "La contraseña y la confirmación de contraseña no coínciden";
			return "redirect:/admin/createNannyForm?error=" + error;
		}

		RoleEntity roleEntity = new RoleEntity(2, "Nanny");
		nanny.getUser().setRole(roleEntity);
		nanny.getUser().setEnabled(true);

		if (nannyService.createNanny(nanny) != null) {
			result = "Se ha agregado la Nanny exitosamente.";
		} else {
			error = "No se ha podido agregar la Nanny, intente nuevamente";
			return "redirect:/admin/createNannyForm?error=" + error;
		}

		return "redirect:/admin/showbookings?result=" + result;
	}

	@GetMapping("/editnannyform")
	public String editnannyform(@RequestParam(required = false) String result,
			@RequestParam(required = false) String error, @RequestParam(required = true) Integer nannyId, Model model) {

		NannyEntity nanny = nannyService.findByNannyId(nannyId);

		model.addAttribute("nanny", nanny);

		return ViewConstants.NANNY_EDIT_VIEW;
	}

	// editnanny

	@PostMapping("/editnanny")
	public String editnanny(@ModelAttribute(name = "nanny") NannyEntity nanny, BindingResult bindingResult,
			Model model) {

		String error = "";
		String result = "";

		if (nanny.getUser().getFirstname() == null || nanny.getUser().getFirstname().equalsIgnoreCase("")) {
			error = "Favor de verificar el Nombre";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (nanny.getUser().getLastname() == null || nanny.getUser().getLastname().equalsIgnoreCase("")) {
			error = "Favor de verificar Apellido";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (nanny.getAge() == null) {
			error = "Favor de verficar la edad de la Nanny";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (nanny.getUser().getEmail() == null || nanny.getUser().getEmail().equalsIgnoreCase("")) {
			error = "Favor de verificar Apellido";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (nanny.getUser().getPhone() == null || nanny.getUser().getPhone().equalsIgnoreCase("")) {
			error = "Favor de verificar Apellido";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (nanny.getStreet() == null) {
			error = "Favor de verficar la calle";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (nanny.getNeighborhood() == null) {
			error = "Favor de verficar la Colonia";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (nanny.getNeighborhood() == null) {
			error = "Favor de verficar la Ciudad";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (nanny.getState() == null) {
			error = "Favor de verficar el Estado";
			return "redirect:/users/editnannyform?error=" + error;
		} else if (nanny.getDegree() == null) {
			error = "Favor de verficar ultimo grado de estudios";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (nanny.getSchool() == null) {
			error = "Favor de verficar la Escuela";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (nanny.getCourse() == null) {
			error = "Favor de verficar los Certificaciones o Capacitaciones";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (nanny.getQualities() == null) {
			error = "Favor de verficar las Habilidades o Cualidades";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (nanny.getHobbies() == null) {
			error = "Favor de verficar los Pasatiempos";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (nanny.getBambinoReason() == null) {
			error = "Favor de verficar las Razones por las cuales les gustaria pertenecer a BambinoCare";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (nanny.getChildrenReason() == null) {
			error = "Favor de verficar las Razones por las cuales consideran que trabajar con niños es su vocación";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (nanny.getComments() == null) {
			error = "Favor de verficar las observaciones";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (nanny.getIfeFile() == null) {
			error = "Favor de verficar el IFE";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (nanny.getCurpFile() == null) {
			error = "Favor de verficar el CURP";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (nanny.getDegreeFile() == null) {
			error = "Favor de verificar la Cedula o Titulo";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (nanny.getUser().getEmail() == null || nanny.getUser().getEmail().equals("")) {
			error = "Favor de verificar el campo Correo";
			return "redirect:/admin/createNannyForm?error=" + error;
		} else if (nanny.getUser().getPassword() == null || nanny.getUser().getPassword().equals("")) {
			error = "Favor de verificar el campo Contraseña";
			return "redirect:/admin/editnannyform?error=" + error;
		} else if (!nanny.getUser().getPasswordConfirm().equals(nanny.getUser().getPassword())) {
			error = "La contraseña y la confirmación de contraseña no coínciden";
			return "redirect:/admin/editnannyform?error=" + error;
		}

		NannyEntity oldNanny = nannyService.findByNannyId(nanny.getNannyId());

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		oldNanny.getUser().setFirstname(nanny.getUser().getFirstname());
		oldNanny.getUser().setLastname(nanny.getUser().getLastname());
		oldNanny.getUser().setEmail(nanny.getUser().getEmail());
		oldNanny.getUser().setPhone(nanny.getUser().getPhone());
		oldNanny.getUser().setPassword(passwordEncoder.encode(nanny.getUser().getPassword()));
		oldNanny.setAge(nanny.getAge());
		oldNanny.setStreet(nanny.getStreet());
		oldNanny.setNeighborhood(nanny.getNeighborhood());
		oldNanny.setCity(nanny.getCity());
		oldNanny.setState(nanny.getState());
		oldNanny.setDegree(nanny.getDegree());
		oldNanny.setCourse(nanny.getCourse());
		oldNanny.setQualities(nanny.getQualities());
		oldNanny.setHobbies(nanny.getHobbies());
		oldNanny.setBambinoReason(nanny.getBambinoReason());
		oldNanny.setChildrenReason(nanny.getChildrenReason());
		oldNanny.setComments(nanny.getComments());

		if (nannyService.saveNanny(oldNanny) != null) {
			result = "Se ha modificado el perfil de Nanny!";
		} else {
			result = "Ocurrió un error al intentar editar el perfil, vuelva a intentarlo";
		}

		return "redirect:/admin/showbookings?error=" + error + "&result=" + result;
	}

	@GetMapping("/disableNanny")
	public String disablenanny(@RequestParam(required = true) Integer nannyId, Model model) {
		String error = "";
		String result = "";

		NannyEntity nanny = nannyService.findByNannyId(nannyId);
		nanny.getUser().setEnabled(false);

		if (nannyService.saveNanny(nanny) != null) {
			result = "Se hashabilitado el perfil de la Nanny!";
		} else {
			result = "Ocurrió un error al intentar editar el perfil, vuelva a intentarlo";
		}

		return "redirect:/admin/showbookings?error=" + error + "&result=" + result;
	}

	@PostMapping("acceptpayment")
	public ModelAndView acceptPayment(@RequestParam("bookingId") int bookingId,
			@RequestAttribute(name = "error", required = false) String error, Model model) {

		ModelAndView mav = new ModelAndView();
		//BookingEntity booking = bookingService.findByBookingId(bookingId);
		BookingEntity booking = bookingService.findByBookingIdAndBookingStatusBookingStatusDesc(bookingId, "Pendiente Pago");

		if(booking == null) {
			error = "No se puede aprobar el pago para este servicio";
			mav = new ModelAndView("redirect:/admin/showbookings?error=" + error);
			return mav;
		}
		
		BookingStatusEntity bookingStatus = bookingStatusService.findByBookingStatusDesc("Abierta");
		booking.setBookingStatus(bookingStatus);

		if (bookingService.createBooking(booking) == null) {
			error = "Ocurrió un error al guardar la reservación";
			mav = new ModelAndView("redirect:/admin/showbookings?error=" + error);
			return mav;
		}

		String result = "El pago se aprobó. Ahora puede agendar la reservación.";
		mav = new ModelAndView("redirect:/admin/showbookings?result=" + result);
		return mav;

	}

	public static Date getDate(Date date, int days) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);

		return calendar.getTime();
	}

}
