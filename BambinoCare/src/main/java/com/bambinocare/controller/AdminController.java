package com.bambinocare.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.bambinocare.model.entity.ParameterEntity;
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
import com.bambinocare.model.service.ParameterService;
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

	@Autowired
	@Qualifier("parameterService")
	private ParameterService parameterService;

	@GetMapping("/showbookings")
	public ModelAndView showBookings(@RequestParam(required = false) String result) {

		ModelAndView mav = new ModelAndView(ViewConstants.ADMIN_VIEW);

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userLogged = userService.findByEmail(user.getUsername());

		mav.addObject("usernameLogged", userLogged.getFirstname());
		mav.addObject("bookings", bookingService.findAllBookings());
		mav.addObject("parameters", parameterService.findAllParameters());
		mav.addObject("nannies", nannyService.findAllNannies());
		mav.addObject("result", result);

		return mav;
	}

	@PostMapping("/showbookingdetail")
	public String showBookingDetail(@RequestParam(name = "bookingId") Integer bookingId, Model model) {

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

			return ViewConstants.BOOKING_DETAIL_ADMIN_SHOW;
		} else {
			result = "No se encontr%C3%B3 la reservaci%C3%B3n solicitada o no tiene permisos para verla";
		}

		return "redirect:/admin/showbookings?result=" + result;

	}

	@GetMapping("/editbookingform")
	public String showEditBooking(@RequestParam(required = false) String result,
			@RequestParam(required = true) Integer bookingId,
			Model model) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		BookingEntity booking = bookingService.findByBookingIdAndBookingStatusBookingStatusDescNotIn(bookingId,
				"Cancelada");

		if (booking == null) {
			result = "La reservaci%C3%B3n solicitada no existe o no tienes permisos para visualizarla o ya se encuentra cancelada";
			return "redirect:/admin/showbookings?result=" + result;
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

		return ViewConstants.BOOKING_ADMIN_EDIT;
	}

	@GetMapping("/editparameterform")
	public String showEditParameter(@RequestParam(required = false) String result,
			@RequestParam(required = true) Integer parameterId,
			Model model) {

		ParameterEntity parameter = parameterService.findByParameterId(parameterId);

		if (parameter == null) {
			result = "El parametro solicitado no existe";
			return "redirect:/admin/showbookings?result=" + result;
		}

		model.addAttribute("parameter", parameter);

		model.addAttribute("result", result);

		return ViewConstants.PARAMETER_ADMIN_EDIT;
	}

	@PostMapping("/editbooking")
	public ModelAndView editBooking(@ModelAttribute(name = "booking") BookingEntity booking,
			BindingResult bindingResult, Model model) {

		ModelAndView mav = new ModelAndView();

		String result = "";

		if (booking.getDuration() == null || booking.getDuration() == 0) {
			result = "Favor de verificar el campo Duraci%C3%B3n";
			mav = new ModelAndView("redirect:/admin/editbookingform");
			mav.addObject("result", result);
			mav.addObject("bookingId", booking.getBookingId());
			return mav;
		}

		if (booking.getDate() == null) {
			result = "Favor de verificar el campo Fecha";
			mav = new ModelAndView("redirect:/admin/editbookingform");
			mav.addObject("result", result);
			mav.addObject("bookingId", booking.getBookingId());
			return mav;
		} else if (bookingService.getDate(booking.getDate(), 1)
				.before(bookingService.getDate(Calendar.getInstance().getTime(), 0))) {
			result = "La reservaci%C3%B3n debe realizarse al menos 1 d%C3%ADa antes de la fecha solictada";
			mav = new ModelAndView("redirect:/admin/editbookingform");
			mav.addObject("result", result);
			mav.addObject("bookingId", booking.getBookingId());
			return mav;
		}

		if (booking.getHour() == null || booking.getHour().equals("")) {
			result = "Favor de verificar el campo Hora";
			mav = new ModelAndView("redirect:/admin/editbookingform");
			mav.addObject("result", result);
			mav.addObject("bookingId", booking.getBookingId());
			return mav;
		}

		if (booking.getBambinoId().size() <= 0) {
			result = "Favor de elegir al menos un bambino";
			mav = new ModelAndView("redirect:/admin/editbookingform");
			mav.addObject("result", result);
			mav.addObject("bookingId", booking.getBookingId());
			return mav;
		}

		BookingEntity oldBooking = bookingService.findByBookingId(booking.getBookingId());

		booking.setBambino(bambinoService.findBambinosByBambinoIdAndUser(booking.getBambinoId(),
				oldBooking.getClient().getUser()));

		if (booking.getBambino().isEmpty()) {
			result = "Ocurri%C3%B3 un error al intentar agregar a los bambinos";
			mav = new ModelAndView("redirect:/users/editbookingform");
			mav.addObject("result", result);
			return mav;
		}

		oldBooking.setDuration(booking.getDuration());
		oldBooking.setDate(bookingService.getDate(booking.getDate(), 1));
		oldBooking.setHour(booking.getHour());
		oldBooking.setBambino(booking.getBambino());
		oldBooking.setCost(costService.calculateTotalCost(booking.getDuration(), booking.getBambino().size(), booking.getBookingType()));

		if (bookingService.createBooking(oldBooking) != null) {
			emailService.sendSimpleMessage(oldBooking.getClient().getUser().getEmail(), "rogerdavila.stech@gmail.com",
					"Reservación Modificada",
					"Su reservación del día " + oldBooking.getDate()
							+ "ha sido modificada. Puedes revisar el detalle en"
							+ " la siguiente liga: \n\r \n\r localhost:8080");
			result = "La reservaci%C3%B3n fue modificada con %C3%A9xito!";
		} else {
			result = "Ocurri%C3%B3 un error al intentar editar la reservaci%C3%B3n, vuelva a intentarlo";
		}

		mav = new ModelAndView("redirect:/admin/showbookings");
		mav.addObject("result", result);
		return mav;
	}

	@PostMapping("/editparameter")
	public ModelAndView editParameter(@ModelAttribute(name = "parameter") ParameterEntity parameter,
			BindingResult bindingResult, Model model) {

		ModelAndView mav = new ModelAndView();
		
		String result = "";

		String parameterValue = parameter.getParameterValue();
		
		if (parameterValue == null) {
			result = "Favor de verificar el campo Valor de Parametro";
			mav = new ModelAndView("redirect:/admin/editparameterform");
			mav.addObject("result", result);
			mav.addObject("parameterId", parameter.getParameterId());
			return mav;
		}

		ParameterEntity oldParameter = parameterService.findByParameterId(parameter.getParameterId());
		oldParameter.setParameterValue(parameterValue);
		
		if (parameterService.createParameter(oldParameter) != null) {
			result = "El parametro fue modificada con %C3%A9xito!";
		} else {
			result = "Ocurri%C3%B3 un error al intentar editar el parametro, vuelva a intentarlo";
		}

		mav = new ModelAndView("redirect:/admin/showbookings");
		mav.addObject("result", result);
		return mav;
	}

	@PostMapping("/cancelbooking")
	public String cancelBooking(@RequestParam(name = "bookingId") Integer bookingId, Model model) {

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
								+ " la siguiente liga: \n\r \n\r localhost:8080");

			} else {
				result = "No se permiten cancelaciones de reservaci%C3%B3n";
			}
		} else {
			result = "No se puede cancelar la reservaci%C3%B3n solicitada";
		}

		return "redirect:/admin/showbookings?result=" + result;
	}

	@GetMapping("/cancel")
	public String cancel() {
		return "redirect:/admin/showbookings";
	}

	@PostMapping("/approvebooking")
	public String approveBooking(@RequestParam(name = "bookingId", required = false) Integer bookingId,
			@ModelAttribute(name = "nanny") NannyEntity nanny, BindingResult bindingResult, Model model) {

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

				Date date = booking.getDate();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate datetime = LocalDate.parse(date.toString(), formatter);
				String dateStr = datetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

				String initialTime = booking.getHour();
				Double duration = booking.getDuration();

				String finalTime = bookingService.getFinalHour(initialTime, duration);

				emailService.sendSimpleMessage(booking.getClient().getUser().getEmail(), "rogerdavila.stech@gmail.com",
						"Reservación Confirmada",
						"Su reservación ha sido confirmada. Puede ingresar a su perfil para verificar la información de la Bambinaia que estar%C3%A1 asistiendo a la cita en el horario de "
								+ initialTime + " a " + finalTime + " el día " + dateStr + " \r\n"
								+ "\r\nAgradecemos su preferencia.\r\n");

			} else {
				result = "No se permite agendar esta reservaci%C3%B3n";
			}
		} else {
			result = "No se puede agendar la reservaci%C3%B3n solicitada";
		}

		return "redirect:/admin/showbookings?result=" + result;
	}

	@PostMapping("/rejectbooking")
	public String rejectBooking(@RequestParam(name = "bookingId") Integer bookingId, Model model) {

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
								+ " la siguiente liga: \n\r \n\r localhost:8080");

			} else {
				result = "No se permite rechazar la reservaci%C3%B3n solicitada";
			}
		} else {
			result = "No se puede rechazar la reservaci%C3%B3n solicitada";
		}

		return "redirect:/admin/showbookings?result=" + result;
	}

	@GetMapping("/createNannyForm")
	public String createNannyForm(@RequestParam(required = false) String result,
			Model model) {
		NannyEntity nanny = new NannyEntity();

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		model.addAttribute("usernameLogged", userEntity.getFirstname());
		model.addAttribute("nanny", nanny);
		model.addAttribute("result", result);

		return ViewConstants.CREATE_NANNY;
	}

	// newnanny

	@PostMapping("/newnanny")
	public String newNanny(@ModelAttribute(name = "nanny") NannyEntity nanny, BindingResult bindingResult,
			Model model) {

		String result = "";

		if (nanny.getUser().getFirstname() == null || nanny.getUser().getFirstname().equalsIgnoreCase("")) {
			result = "Favor de verificar el Nombre";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (nanny.getUser().getLastname() == null || nanny.getUser().getLastname().equalsIgnoreCase("")) {
			result = "Favor de verificar Apellido";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (nanny.getAge() == null) {
			result = "Favor de verficar la edad de la Nanny";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (nanny.getUser().getEmail() == null || nanny.getUser().getEmail().equalsIgnoreCase("")) {
			result = "Favor de verificar Apellido";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (nanny.getUser().getPhone() == null || nanny.getUser().getPhone().equalsIgnoreCase("")) {
			result = "Favor de verificar Apellido";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (nanny.getStreet() == null) {
			result = "Favor de verficar la calle";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (nanny.getNeighborhood() == null) {
			result = "Favor de verficar la Colonia";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (nanny.getNeighborhood() == null) {
			result = "Favor de verficar la Ciudad";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (nanny.getState() == null) {
			result = "Favor de verficar el Estado";
			return "redirect:/users/createNannyForm?result=" + result;
		} else if (nanny.getCareer() == null) {
			result = "Favor de verficar ultimo grado de estudios";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (nanny.getSchool() == null) {
			result = "Favor de verficar la Escuela";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (nanny.getCareer() == null) {
			result = "Favor de verficar su carrera";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (nanny.getQualities() == null) {
			result = "Favor de verficar las Habilidades o Cualidades";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (nanny.getHobbies() == null) {
			result = "Favor de verficar los Pasatiempos";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (nanny.getBambinoReason() == null) {
			result = "Favor de verficar las Razones por las cuales les gustaria pertenecer a BambinoCare";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (nanny.getIfeFile() == null) {
			result = "Favor de verficar el IFE";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (nanny.getCurpFile() == null) {
			result = "Favor de verficar el CURP";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (userService.userExist(nanny.getUser().getEmail())) {
			result = "El email introducido ya existe en la aplicaci%C3%B3n";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (nanny.getUser().getEmail() == null || nanny.getUser().getEmail().equals("")) {
			result = "Favor de verificar el campo Correo";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (nanny.getUser().getPassword() == null || nanny.getUser().getPassword().equals("")) {
			result = "Favor de verificar el campo Contraseña";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (!nanny.getUser().getPasswordConfirm().equals(nanny.getUser().getPassword())) {
			result = "La contraseña y la confirmaci%C3%B3n de contraseña no co%C3%ADnciden";
			return "redirect:/admin/createNannyForm?result=" + result;
		}

		RoleEntity roleEntity = new RoleEntity(2, "Nanny");
		nanny.getUser().setRole(roleEntity);
		nanny.getUser().setEnabled(true);
		
		if (nannyService.createNanny(nanny) != null) {
			result = "Se ha agregado la Nanny exitosamente.";
		} else {
			result = "No se ha podido agregar la Nanny, intente nuevamente";
			return "redirect:/admin/createNannyForm?result=" + result;
		}

		return "redirect:/admin/showbookings?result=" + result;
	}

	@GetMapping("/editnannyform")
	public String editNannyForm(@RequestParam(required = false) String result,
	@RequestParam(required = true) Integer nannyId, Model model) {

		NannyEntity nanny = nannyService.findByNannyId(nannyId);

		model.addAttribute("nanny", nanny);

		return ViewConstants.NANNY_EDIT_VIEW;
	}

	// editnanny

	@PostMapping("/editnanny")
	public String editnanny(@ModelAttribute(name = "nanny") NannyEntity nanny, BindingResult bindingResult,
			Model model) {

		String result = "";

		if (nanny.getUser().getFirstname() == null || nanny.getUser().getFirstname().equalsIgnoreCase("")) {
			result = "Favor de verificar el Nombre";
			return "redirect:/admin/editnannyform?result=" + result;
		} else if (nanny.getUser().getLastname() == null || nanny.getUser().getLastname().equalsIgnoreCase("")) {
			result = "Favor de verificar Apellido";
			return "redirect:/admin/editnannyform?result=" + result;
		} else if (nanny.getAge() == null) {
			result = "Favor de verficar la edad de la Nanny";
			return "redirect:/admin/editnannyform?result=" + result;
		} else if (nanny.getUser().getEmail() == null || nanny.getUser().getEmail().equalsIgnoreCase("")) {
			result = "Favor de verificar Apellido";
			return "redirect:/admin/editnannyform?result=" + result;
		} else if (nanny.getUser().getPhone() == null || nanny.getUser().getPhone().equalsIgnoreCase("")) {
			result = "Favor de verificar Apellido";
			return "redirect:/admin/editnannyform?result=" + result;
		} else if (nanny.getStreet() == null) {
			result = "Favor de verficar la calle";
			return "redirect:/admin/editnannyform?result=" + result;
		} else if (nanny.getNeighborhood() == null) {
			result = "Favor de verficar la Colonia";
			return "redirect:/admin/editnannyform?result=" + result;
		} else if (nanny.getNeighborhood() == null) {
			result = "Favor de verficar la Ciudad";
			return "redirect:/admin/editnannyform?result=" + result;
		} else if (nanny.getState() == null) {
			result = "Favor de verficar el Estado";
			return "redirect:/users/editnannyform?result=" + result;
		} else if (nanny.getCareer() == null) {
			result = "Favor de verficar su carrera";
			return "redirect:/admin/editnannyform?result=" + result;
		} else if (nanny.getSchool() == null) {
			result = "Favor de verficar la Escuela";
			return "redirect:/admin/editnannyform?result=" + result;
		} else if (nanny.getQualities() == null) {
			result = "Favor de verficar las Habilidades o Cualidades";
			return "redirect:/admin/editnannyform?result=" + result;
		} else if (nanny.getHobbies() == null) {
			result = "Favor de verficar los Pasatiempos";
			return "redirect:/admin/editnannyform?result=" + result;
		} else if (nanny.getBambinoReason() == null) {
			result = "Favor de verficar las Razones por las cuales les gustaria pertenecer a BambinoCare";
			return "redirect:/admin/editnannyform?result=" + result;
		} else if (nanny.getIfeFile() == null) {
			result = "Favor de verficar el IFE";
			return "redirect:/admin/editnannyform?result=" + result;
		} else if (nanny.getCurpFile() == null) {
			result = "Favor de verficar el CURP";
			return "redirect:/admin/editnannyform?result=" + result;
		} else if (nanny.getDegreeFile() == null) {
			result = "Favor de verificar la Cedula o Titulo";
			return "redirect:/admin/editnannyform?result=" + result;
		} else if (nanny.getUser().getEmail() == null || nanny.getUser().getEmail().equals("")) {
			result = "Favor de verificar el campo Correo";
			return "redirect:/admin/createNannyForm?result=" + result;
		} else if (nanny.getUser().getPassword() == null || nanny.getUser().getPassword().equals("")) {
			result = "Favor de verificar el campo Contraseña";
			return "redirect:/admin/editnannyform?result=" + result;
		} else if (!nanny.getUser().getPasswordConfirm().equals(nanny.getUser().getPassword())) {
			result = "La contraseña y la confirmaci%C3%B3n de contraseña no co%C3%ADnciden";
			return "redirect:/admin/editnannyform?result=" + result;
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
		oldNanny.setCareer(nanny.getCareer());
		oldNanny.setQualities(nanny.getQualities());
		oldNanny.setHobbies(nanny.getHobbies());
		oldNanny.setBambinoReason(nanny.getBambinoReason());
		oldNanny.setDegreeFile(nanny.getDegreeFile());

		if (nannyService.saveNanny(oldNanny) != null) {
			result = "Se ha modificado el perfil de Nanny!";
		} else {
			result = "Ocurri%C3%B3 un error al intentar editar el perfil, vuelva a intentarlo";
		}

		return "redirect:/admin/showbookings?result=" + result;
	}

	@GetMapping("/disableNanny")
	public String disablenanny(@RequestParam(required = true) Integer nannyId, Model model) {
		String result = "";

		NannyEntity nanny = nannyService.findByNannyId(nannyId);
		nanny.getUser().setEnabled(false);

		if (nannyService.saveNanny(nanny) != null) {
			result = "Se hashabilitado el perfil de la Nanny!";
		} else {
			result = "Ocurri%C3%B3 un error al intentar editar el perfil, vuelva a intentarlo";
		}

		return "redirect:/admin/showbookings?result=" + result;
	}

	@PostMapping("acceptpayment")
	public ModelAndView acceptPayment(@RequestParam("bookingId") int bookingId,
			@RequestAttribute(name = "result", required = false) String result, Model model) {

		ModelAndView mav = new ModelAndView();
		// BookingEntity booking = bookingService.findByBookingId(bookingId);
		BookingEntity booking = bookingService.findByBookingIdAndBookingStatusBookingStatusDesc(bookingId,
				"Pendiente Pago");

		if (booking == null) {
			result = "No se puede aprobar el pago para este servicio";
			mav = new ModelAndView("redirect:/admin/showbookings?result=" + result);
			return mav;
		}

		BookingStatusEntity bookingStatus = bookingStatusService.findByBookingStatusDesc("Abierta");
		booking.setBookingStatus(bookingStatus);

		if (bookingService.createBooking(booking) == null) {
			result = "Ocurri%C3%B3 un error al guardar la reservaci%C3%B3n";
			mav = new ModelAndView("redirect:/admin/showbookings?result=" + result);
			return mav;
		}

		result = "El pago se aprob%C3%B3. Ahora puede agendar la reservaci%C3%B3n.";
		mav = new ModelAndView("redirect:/admin/showbookings?result=" + result);
		return mav;

	}

}
