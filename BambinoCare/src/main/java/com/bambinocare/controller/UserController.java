package com.bambinocare.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.bambinocare.constant.ViewConstants;
import com.bambinocare.model.entity.BambinoEntity;
import com.bambinocare.model.entity.BookingEntity;
import com.bambinocare.model.entity.BookingStatusEntity;
import com.bambinocare.model.entity.BookingTypeEntity;
import com.bambinocare.model.entity.CityEntity;
import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.entity.CostEntity;
import com.bambinocare.model.entity.EmergencyContactEntity;
import com.bambinocare.model.entity.EventTypeEntity;
import com.bambinocare.model.entity.PaymentTypeEntity;
import com.bambinocare.model.entity.StateEntity;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.service.BambinoService;
import com.bambinocare.model.service.BookingService;
import com.bambinocare.model.service.BookingStatusService;
import com.bambinocare.model.service.BookingTypeService;
import com.bambinocare.model.service.CityService;
import com.bambinocare.model.service.ClientService;
import com.bambinocare.model.service.CostService;
import com.bambinocare.model.service.EmailService;
import com.bambinocare.model.service.EmergencyContactService;
import com.bambinocare.model.service.EventTypeService;
import com.bambinocare.model.service.ParameterService;
import com.bambinocare.model.service.PaymentTypeService;
import com.bambinocare.model.service.StateService;
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

	@Autowired
	@Qualifier("costService")
	private CostService costService;

	@Autowired
	@Qualifier("cityService")
	private CityService cityService;

	@Autowired
	@Qualifier("stateService")
	private StateService stateService;

	@Autowired
	@Qualifier("paymentTypeService")
	private PaymentTypeService paymentTypeService;
	
	@Autowired
	@Qualifier("parameterService")
	private ParameterService parameterService;

	@GetMapping("/showbookings")
	public ModelAndView showBookings(@RequestParam(required = false) String error,
			@RequestParam(required = false) String result) {

		ModelAndView mav = new ModelAndView(ViewConstants.USER_SHOW);

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userLogged = userService.findByEmail(user.getUsername());
		List<CityEntity> cities = cityService.findAll();
		List<StateEntity> states = stateService.findAll();

		mav.addObject("usernameLogged", userLogged.getFirstname());
		mav.addObject("bookings", bookingService.findByUser(userLogged));
		mav.addObject("bambinos", bambinoService.findByClientUser(userLogged));
		mav.addObject("contacts", emergencyContactService.findByUser(userLogged));
		mav.addObject("client", clientService.findByUser(userLogged));
		mav.addObject("error", error);
		mav.addObject("result", result);
		mav.addObject("cities", cities);
		mav.addObject("states", states);

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
			model.addAttribute("totalCost", booking.getCost());

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
		List<CostEntity> costs = costService.findAllByOrderByHourQuantity();
		List<PaymentTypeEntity> paymentTypes = paymentTypeService.findAll();

		model.addAttribute("usernameLogged", userEntity.getFirstname());

		model.addAttribute("booking", booking);
		model.addAttribute("bookingTypes", bookingTypes);
		model.addAttribute("eventTypes", eventTypes);
		model.addAttribute("allbambinos", bambinos);
		model.addAttribute("costs", costs);
		model.addAttribute("totalCost", booking.getCost());
		model.addAttribute("paymentTypes", paymentTypes);

		model.addAttribute("result", result);
		model.addAttribute("error", error);

		return ViewConstants.BOOKING_CREATE;
	}

	@PostMapping("/createbooking")
	public ModelAndView createBooking(@ModelAttribute(name = "booking") BookingEntity booking,
			BindingResult bindingResult, Model model, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();
		String error = "";

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		if (booking.getDuration() == null || booking.getDuration() == 0) {
			mav = new ModelAndView(ViewConstants.BOOKING_CREATE);
			error = "Favor de verificar el campo Duración";

			List<BookingTypeEntity> bookingTypes = bookingTypeService.findAllBookingTypes();
			List<EventTypeEntity> eventTypes = eventTypeService.findAllEventTypes();
			List<BambinoEntity> bambinos = bambinoService.findByClientUser(userEntity);
			List<CostEntity> costs = costService.findAllByOrderByHourQuantity();
			List<PaymentTypeEntity> paymentTypes = paymentTypeService.findAll();

			mav.addObject("allbambinos", bambinos);
			mav.addObject("usernameLogged", userEntity.getFirstname());
			mav.addObject("costs", costs);
			mav.addObject("totalCost", booking.getCost());
			mav.addObject("paymentTypes", paymentTypes);

			mav.addObject("booking", booking);
			mav.addObject("bookingTypes", bookingTypes);
			mav.addObject("eventTypes", eventTypes);

			mav.addObject("error", error);

			return mav;
		}

		if (booking.getDate() == null) {
			mav = new ModelAndView(ViewConstants.BOOKING_CREATE);
			error = "Favor de verificar el campo Fecha";

			List<BookingTypeEntity> bookingTypes = bookingTypeService.findAllBookingTypes();
			List<EventTypeEntity> eventTypes = eventTypeService.findAllEventTypes();
			List<BambinoEntity> bambinos = bambinoService.findByClientUser(userEntity);
			List<CostEntity> costs = costService.findAllByOrderByHourQuantity();
			List<PaymentTypeEntity> paymentTypes = paymentTypeService.findAll();

			mav.addObject("allbambinos", bambinos);
			mav.addObject("usernameLogged", userEntity.getFirstname());
			mav.addObject("costs", costs);
			mav.addObject("totalCost", booking.getCost());
			mav.addObject("paymentTypes", paymentTypes);

			mav.addObject("booking", booking);
			mav.addObject("bookingTypes", bookingTypes);
			mav.addObject("eventTypes", eventTypes);

			mav.addObject("error", error);

			return mav;
		} 

		if (booking.getHour() == null || booking.getHour().equals("")) {
			mav = new ModelAndView(ViewConstants.BOOKING_CREATE);
			error = "Favor de verificar el campo Hora";

			List<BookingTypeEntity> bookingTypes = bookingTypeService.findAllBookingTypes();
			List<EventTypeEntity> eventTypes = eventTypeService.findAllEventTypes();
			List<BambinoEntity> bambinos = bambinoService.findByClientUser(userEntity);
			List<CostEntity> costs = costService.findAllByOrderByHourQuantity();
			List<PaymentTypeEntity> paymentTypes = paymentTypeService.findAll();

			mav.addObject("allbambinos", bambinos);
			mav.addObject("usernameLogged", userEntity.getFirstname());
			mav.addObject("costs", costs);
			mav.addObject("totalCost", booking.getCost());
			mav.addObject("paymentTypes", paymentTypes);

			mav.addObject("booking", booking);
			mav.addObject("bookingTypes", bookingTypes);
			mav.addObject("eventTypes", eventTypes);

			mav.addObject("error", error);

			return mav;
		} else if (!bookingService.isValideDate(booking.getDate(), booking.getHour())) {
			mav = new ModelAndView(ViewConstants.BOOKING_CREATE);
			error = "La reservación debe realizarse al menos 24 horas antes de la fecha solictada, le sugerimos revisar el servicio Bambino ASAP";

			List<BookingTypeEntity> bookingTypes = bookingTypeService.findAllBookingTypes();
			List<EventTypeEntity> eventTypes = eventTypeService.findAllEventTypes();
			List<BambinoEntity> bambinos = bambinoService.findByClientUser(userEntity);
			List<CostEntity> costs = costService.findAllByOrderByHourQuantity();
			List<PaymentTypeEntity> paymentTypes = paymentTypeService.findAll();

			mav.addObject("allbambinos", bambinos);
			mav.addObject("usernameLogged", userEntity.getFirstname());
			mav.addObject("costs", costs);
			mav.addObject("totalCost", booking.getCost());
			mav.addObject("paymentTypes", paymentTypes);

			mav.addObject("booking", booking);
			mav.addObject("bookingTypes", bookingTypes);
			mav.addObject("eventTypes", eventTypes);

			mav.addObject("error", error);

			return mav;
		} else if (!bookingService.isValideHour(booking.getHour())) {
			mav = new ModelAndView(ViewConstants.BOOKING_CREATE);
			String serviceHour = parameterService.findByParameterKey("Hora Apertura").getParameterValue();
			error = "Por el momento el horario para el servicio es a partir de las " + serviceHour + " hrs";
			
			List<BookingTypeEntity> bookingTypes = bookingTypeService.findAllBookingTypes();
			List<EventTypeEntity> eventTypes = eventTypeService.findAllEventTypes();
			List<BambinoEntity> bambinos = bambinoService.findByClientUser(userEntity);
			List<CostEntity> costs = costService.findAllByOrderByHourQuantity();
			List<PaymentTypeEntity> paymentTypes = paymentTypeService.findAll();

			mav.addObject("allbambinos", bambinos);
			mav.addObject("usernameLogged", userEntity.getFirstname());
			mav.addObject("costs", costs);
			mav.addObject("totalCost", booking.getCost());
			mav.addObject("paymentTypes", paymentTypes);

			mav.addObject("booking", booking);
			mav.addObject("bookingTypes", bookingTypes);
			mav.addObject("eventTypes", eventTypes);

			mav.addObject("error", error);

			return mav;
		}

		if (booking.getPaymentType() == null) {
			mav = new ModelAndView(ViewConstants.BOOKING_CREATE);
			error = "Favor de verificar la forma de pago";

			List<BookingTypeEntity> bookingTypes = bookingTypeService.findAllBookingTypes();
			List<EventTypeEntity> eventTypes = eventTypeService.findAllEventTypes();
			List<BambinoEntity> bambinos = bambinoService.findByClientUser(userEntity);
			List<CostEntity> costs = costService.findAllByOrderByHourQuantity();
			List<PaymentTypeEntity> paymentTypes = paymentTypeService.findAll();

			mav.addObject("allbambinos", bambinos);
			mav.addObject("usernameLogged", userEntity.getFirstname());
			mav.addObject("costs", costs);
			mav.addObject("totalCost", booking.getCost());
			mav.addObject("paymentTypes", paymentTypes);

			mav.addObject("booking", booking);
			mav.addObject("bookingTypes", bookingTypes);
			mav.addObject("eventTypes", eventTypes);

			mav.addObject("error", error);

			return mav;
		}

		ClientEntity client = clientService.findByUserEmail(user.getUsername());

		booking.setClient(client);

		if (bambinoService.findByClient(client).isEmpty()) {
			mav = new ModelAndView("redirect:/users/createbambinoform");
			error = "Favor dar de alta a sus bambinos";
			mav.addObject("error", error);

			return mav;
		}

		List<EmergencyContactEntity> emergencyContacts = emergencyContactService.findByClient(client);

		if (emergencyContacts.isEmpty() || emergencyContacts.size() < 2) {
			mav = new ModelAndView("redirect:/users/createcontactform");
			error = "Favor dar de alta al menos 2 contactos de emergencia";
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
			List<CostEntity> costs = costService.findAllByOrderByHourQuantity();
			List<PaymentTypeEntity> paymentTypes = paymentTypeService.findAll();

			mav.addObject("allbambinos", bambinos);
			mav.addObject("usernameLogged", userEntity.getFirstname());
			mav.addObject("costs", costs);
			mav.addObject("totalCost", booking.getCost());
			mav.addObject("paymentTypes", paymentTypes);

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
			List<CostEntity> costs = costService.findAllByOrderByHourQuantity();
			List<PaymentTypeEntity> paymentTypes = paymentTypeService.findAll();

			mav.addObject("allbambinos", bambinos);
			mav.addObject("usernameLogged", userEntity.getFirstname());
			mav.addObject("costs", costs);
			mav.addObject("totalCost", booking.getCost());
			mav.addObject("paymentTypes", paymentTypes);

			mav.addObject("booking", booking);
			mav.addObject("bookingTypes", bookingTypes);
			mav.addObject("eventTypes", eventTypes);

			mav.addObject("error", error);

			return mav;
		}

		BookingStatusEntity bookingStatus = bookingStatusService.findByBookingStatusDesc("Pendiente Pago");
		booking.setBookingStatus(bookingStatus);

		booking.setCost(costService.calculateTotalCost(booking.getDuration(), booking.getBambino().size()));

		booking.setDate(bookingService.getDate(booking.getDate(), 1));
		BookingEntity bookingCreated = bookingService.createBooking(booking);
		if (bookingCreated != null) {
			String paymentTypeDesc = paymentTypeService.findByPaymentTypeId(booking.getPaymentType().getPaymentTypeId())
					.getPaymentTypeDesc();

			// Si el método de pago es Paypal
			if (paymentTypeDesc.equalsIgnoreCase("Paypal")) {
				request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
				ModelMap modelmap = mav.getModelMap();
				modelmap.addAttribute("cost", bookingCreated.getCost());
				modelmap.addAttribute("bookingId", bookingCreated.getBookingId());
				mav = new ModelAndView("redirect:/payments/", modelmap);
				return mav;
			}

			// Si el método de pago es Deposito cuenta bancaria
			if (paymentTypeDesc.equalsIgnoreCase("Pago en Oxxo o a cuenta Bancaria")) {
				ModelMap modelmap = mav.getModelMap();
				modelmap.addAttribute("result", "La reservación se ha realizado exitosamente");
				modelmap.addAttribute("bookingId", bookingCreated.getBookingId());
				mav.addObject("result", "El pago se ha realizado exitosamente");
				mav.addObject("bookingId", bookingCreated.getBookingId());
				mav = new ModelAndView("forward:/users/completebooking", modelmap);
				return mav;
			}

			// Si el método de pago es efectivo
			if (paymentTypeDesc.equalsIgnoreCase("Pago en efectivo")) {
				ModelMap modelmap = mav.getModelMap();
				modelmap.addAttribute("result", "La reservación se ha realizado exitosamente");
				modelmap.addAttribute("bookingId", bookingCreated.getBookingId());
				mav.addObject("result", "El pago se ha realizado exitosamente");
				mav.addObject("bookingId", bookingCreated.getBookingId());
				mav = new ModelAndView("forward:/users/completebooking", modelmap);
				return mav;
			}
		} else {
			mav = new ModelAndView("redirect:/users/createbookingform");
			error = "No se ha podido realizar la reservación, intente nuevamente";
			mav.addObject("error", error);
			return mav;
		}

		return mav;
	}

	@RequestMapping(value = "completebooking", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView completeBooking(@RequestAttribute("bookingId") int bookingId,
			@RequestAttribute(name = "error", required = false) String error, Model model) {

		ModelAndView mav = new ModelAndView();
		BookingEntity booking = bookingService.findByBookingId(bookingId);

		String paymentType = paymentTypeService.findByPaymentTypeId(booking.getPaymentType().getPaymentTypeId())
				.getPaymentTypeDesc();

		if (paymentType.equalsIgnoreCase("Paypal")) {
			if (error != null) {
				bookingService.delete(booking);
				mav = new ModelAndView("redirect:/users/showbookings?error=" + error);
				return mav;
			}

			BookingStatusEntity bookingStatus = bookingStatusService.findByBookingStatusDesc("Abierta");

			booking.setBookingStatus(bookingStatus);

			if (bookingService.createBooking(booking) == null) {
				error = "Ocurrió un error al guardar la reservación";
				mav = new ModelAndView("redirect:/users/showbookings?error=" + error);
				return mav;
			}
		}

		try {
			emailService.sendHTMLMessage("rogerdavila.stech@gmail.com", "BambinoCare - Nueva reservación",
					getBookingHTML(booking));
			
			emailService.sendMessageWithAttachment(booking.getClient().getUser().getEmail(), "BambinoCare - Nueva reservación",
					"<html><body style='width: 100%; height: 100%'><img style='width: 100%; height: auto;' src='cid:reservacion.jpg'/></body></html>","reservacion.jpg");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		String result = "La reservación se realizó exitosamente.";
		mav = new ModelAndView("redirect:/users/showbookings?result=" + result);
		return mav;

	}

	public String getBookingHTML(BookingEntity booking) {

		String bambinos = "";
		String bambinosAges = "";
		for (BambinoEntity bambino : booking.getBambino()) {
			bambinos += bambino.getFirstname() + " " + bambino.getLastname() + ",";
			bambinosAges += bambino.getAge() + ",";
		}

		BookingTypeEntity bookingType = bookingTypeService
				.findByBookingTypeId(booking.getBookingType().getBookingTypeId());

		return "<html><body><p>El usuario " + booking.getClient().getUser().getEmail()
				+ " ha agendado una nueva cita: </p><table border=\"1\">"
				+ "<thead><tr><th>Nombre del Cliente</th><th>Email del Cliente</th><th>Servicio</th><th>Bambinos</th><th>Edades</th>"
				+ "<th>Fecha</th><th>Horario</th><th>Lugar</th></tr></thead><tbody><tr><td>"
				+ booking.getClient().getUser().getFirstname() + " " + booking.getClient().getUser().getLastname()
				+ "</td><td>" + booking.getClient().getUser().getEmail() + "</td><td>"
				+ bookingType.getBookingTypeDesc() + "</td><td>" + bambinos.substring(0, bambinos.length() - 1)
				+ "</td><td>" + bambinosAges.substring(0, bambinosAges.length() - 1) + "</td><td>" + booking.getDate()
				+ "</td><td>" + booking.getDuration() + "</td><td>" + booking.getClient().getStreet() + " "
				+ booking.getClient().getNeighborhood() + " " + booking.getClient().getState().getStateDesc()
				+ "</td></tr>" + "</tbody></table><p>Puedes revisar el detalle en"
				+ " la siguiente liga: \n\r \n\r www.bambinocare.com.mx</p></body></html>";

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
		List<CostEntity> costs = costService.findAllByOrderByHourQuantity();
		List<PaymentTypeEntity> paymentTypes = paymentTypeService.findAll();

		model.addAttribute("allbambinos", bambinos);
		model.addAttribute("usernameLogged", userEntity.getFirstname());
		model.addAttribute("costs", costs);
		model.addAttribute("totalCost", booking.getCost());
		model.addAttribute("paymentTypes", paymentTypes);

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
		} else if (bookingService.getDate(booking.getDate(), 1)
				.before(bookingService.getDate(Calendar.getInstance().getTime(), 0))) {
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
		oldBooking.setDate(bookingService.getDate(booking.getDate(), 1));
		oldBooking.setHour(booking.getHour());
		oldBooking.setBambino(booking.getBambino());
		oldBooking.setCost(costService.calculateTotalCost(booking.getDuration(), booking.getBambino().size()));

		if (bookingService.createBooking(oldBooking) != null) {
			emailService.sendSimpleMessage("rogerdavila.stech@gmail.com", "Reservación Modificada",
					"El usuario " + oldBooking.getClient().getUser().getEmail()
							+ " ha modificado la reservación del día " + oldBooking.getDate()
							+ ". Puedes revisar el detalle en" + " la siguiente liga: \n\r \n\r www.bambinocare.com.mx");
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

		String empty = "";
		String error = empty;
		String result = empty;

		if (client.getUser().getEmail() == null || client.getUser().getEmail().equals(empty)) {
			error = "Favor de verificar el email";
			return "redirect:/users/showbookings?error=" + error;
		}

		if (client.getUser().getFirstname() == null || client.getUser().getFirstname().equals(empty)) {
			error = "Favor de verificar el Nombre";
			return "redirect:/users/showbookings?error=" + error;
		}

		if (client.getUser().getLastname() == null || client.getUser().getLastname().equals(empty)) {
			error = "Favor de verificar el Apellido";
			return "redirect:/users/showbookings?error=" + error;
		}

		if (client.getUser().getPhone() == null || client.getUser().getPhone().equals(empty)) {
			error = "Favor de verificar el email";
			return "redirect:/users/showbookings?error=" + error;
		}

		if (client.getStreet() == null || client.getStreet().equals(empty)) {
			error = "Favor de verificar el campo Fecha";
			return "redirect:/users/showbookings?error=" + error;
		}

		if (client.getNeighborhood() == null || client.getNeighborhood().equals(empty)) {
			error = "Favor de verificar el campo Fecha";
			return "redirect:/users/showbookings?error=" + error;
		}

		if (client.getCity() == null || client.getCity().equals(empty)) {
			error = "Favor de verificar el campo Fecha";
			return "redirect:/users/showbookings?error=" + error;
		}

		if (client.getState() == null || client.getState().equals(empty)) {
			error = "Favor de verificar el campo Fecha";
			return "redirect:/users/showbookings?error=" + error;
		}

		if (client.getJob() == null || client.getJob().equals(empty)) {
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
	public String cancelBooking(@RequestParam(name = "bookingId") Integer bookingId, Model model) {

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
								+ " la siguiente liga: \n\r \n\r www.bambinocare.com.mx");

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
			error = "Verficar la edad del Bambino";
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

	@PostMapping("/removeBambino")
	public String removeBambino(@RequestParam(required = false) String result,
			@RequestParam(required = false) String error, @RequestParam(required = true) Integer bambinoId,
			Model model) {
		bambinoService.removeBambino(bambinoId);
		result = "El bambino fue borrado con éxito!";
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

	@PostMapping("/removeContact")
	public String removeContacts(@RequestParam(required = false) String result,
			@RequestParam(required = false) String error, @RequestParam(required = true) Integer contactId,
			Model model) {
		emergencyContactService.removeContact(contactId);
		result = "El bambino fue borrado con éxito!";
		return "redirect:/users/showbookings?error=" + error + "&result=" + result;
	}

}
