package com.bambinocare.controller;

import java.util.ArrayList;
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
import com.bambinocare.model.TutoryService;
import com.bambinocare.model.ValidationModel;
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
import com.bambinocare.model.service.EventService;
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

	@Autowired
	@Qualifier("eventService")
	private EventService eventService;

	@Autowired
	@Qualifier("tutoryService")
	private TutoryService tutoryService;

	@GetMapping("/showbookings")
	public ModelAndView showBookings(@RequestParam(required = false) String result) {

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
		mav.addObject("result", result);
		mav.addObject("cities", cities);
		mav.addObject("states", states);

		return mav;
	}

	@PostMapping("/showbookingdetail")
	public String showBookingDetail(@RequestParam(name = "bookingId") Integer bookingId, Model model) {

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

			return ViewConstants.BOOKING_DETAIL_SHOW;
		} else {
			result = "No se encontró la reservación solicitada o no tiene permisos para verla";
		}

		return "redirect:/users/showbookings?result=" + result;

	}

	@GetMapping("/createbookingform")
	public ModelAndView showCreateBooking(@RequestParam(required = false) String result, Model model) {
		ModelAndView mav = new ModelAndView(ViewConstants.BOOKING_CREATE);
		BookingEntity booking = new BookingEntity();
		mav.addAllObjects(getModelMapForCreateBookingForm(result, booking));
		return mav;
	}

	@PostMapping("/createbooking")
	public ModelAndView createBooking(@ModelAttribute(name = "booking") BookingEntity booking,
			BindingResult bindingResult, Model model, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();
		String result = "";

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		ValidationModel validationModel = bookingService.validateBookingForm(booking, user);

		if (validationModel.getResult() != null) {
			if (validationModel.isRequireOtherView()) {
				mav = new ModelAndView(validationModel.getOtherView());
				mav.addObject("result", validationModel.getResult());
			} else {
				mav = new ModelAndView(ViewConstants.BOOKING_CREATE);
				mav.addAllObjects(getModelMapForCreateBookingForm(validationModel.getResult(), booking));
			}
			return mav;
		}

		// Asignación de Bambinos solo para BambinoCare y BambinoASAP
		if (booking.getBookingType().getBookingTypeId() == 1 || booking.getBookingType().getBookingTypeId() == 4) {
			if (booking.getBambino() != null) {
				List<String> bambinoIds = new ArrayList<>();
				for (BambinoEntity bambino : booking.getBambino()) {
					bambinoIds.add(bambino.getBambinoId().toString());
				}
				booking.setBambinoId(bambinoIds);
			}

			if (booking.getBambinoId().size() <= 0) {
				mav = new ModelAndView(ViewConstants.BOOKING_CREATE);
				result = "Favor de elegir al menos un bambino";
				mav.addAllObjects(getModelMapForCreateBookingForm(result, booking));
				return mav;
			}

			booking.setBambino(bambinoService.findBambinosByBambinoIdAndUser(booking.getBambinoId(), userEntity));

			if (booking.getBambino().isEmpty()) {
				mav = new ModelAndView(ViewConstants.BOOKING_CREATE);
				result = "Ocurrió un error al intentar agregar a los bambinos";
				mav.addAllObjects(getModelMapForCreateBookingForm(result, booking));
				return mav;
			}
		}

		BookingStatusEntity bookingStatus = bookingStatusService.findByBookingStatusDesc("Pendiente Pago");
		booking.setBookingStatus(bookingStatus);
		booking.setCost(costService.calculateTotalCostByBooking(booking));
		booking.setDate(bookingService.getDate(booking.getDate(), 1));

		// Imlepementar lógica para llenar los campos de startDateTime y finishDateTime
		booking.setStartDateTime(
				bookingService.getBookingDateTime(booking.getDate(), booking.getHour(), booking.getDuration(), false));
		booking.setFinishDateTime(
				bookingService.getBookingDateTime(booking.getDate(), booking.getHour(), booking.getDuration(), true));

		if (booking.getBookingType().getBookingTypeId() != 3) {
			booking.setEvent(null);
		} else {
			eventService.createEvent(booking.getEvent());
		}
		if (booking.getBookingType().getBookingTypeId() != 2) {
			booking.setTutory(null);
		} else {
			tutoryService.createTutory(booking.getTutory());
		}

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
			result = "No se ha podido realizar la reservación, intente nuevamente";
			mav.addObject("result", result);
			return mav;
		}

		return mav;
	}

	private ModelMap getModelMapForCreateBookingForm(String result, BookingEntity booking) {
		ModelMap modelMap = new ModelMap();

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		List<BookingTypeEntity> bookingTypes = bookingTypeService.findAllBookingTypes();
		List<EventTypeEntity> eventTypes = eventTypeService.findAllEventTypes();
		List<BambinoEntity> bambinos = bambinoService.findByClientUser(userEntity);

		for (BookingTypeEntity bookingType : bookingTypes) {
			List<CostEntity> costs = costService.findByBookingTypeOrderByHourQuantity(bookingType);
			if (bookingType.getBookingTypeDesc().equalsIgnoreCase("Bambino Care")) {
				modelMap.addAttribute("costsbambinocare", costs);
			} else if (bookingType.getBookingTypeDesc().equalsIgnoreCase("Bambino ASAP")) {
				modelMap.addAttribute("costsbambinoasap", costs);
			} else if (bookingType.getBookingTypeDesc().equalsIgnoreCase("Bambino Tutoring")) {
				modelMap.addAttribute("costsbambinotutory", costs);
			} else if (bookingType.getBookingTypeDesc().equalsIgnoreCase("Bambino Events")) {
				modelMap.addAttribute("costsbambinoevents", costs);
			}
		}
		List<PaymentTypeEntity> paymentTypes = paymentTypeService.findAll();

		modelMap.addAttribute("usernameLogged", userEntity.getFirstname());

		modelMap.addAttribute("booking", booking);
		modelMap.addAttribute("bookingTypes", bookingTypes);
		modelMap.addAttribute("eventTypes", eventTypes);
		modelMap.addAttribute("allbambinos", bambinos);
		modelMap.addAttribute("totalCost", booking.getCost());
		modelMap.addAttribute("paymentTypes", paymentTypes);

		modelMap.addAttribute("result", result);

		return modelMap;
	}

	@RequestMapping(value = "completebooking", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView completeBooking(@RequestAttribute("bookingId") int bookingId,
			@RequestAttribute(name = "result", required = false) String result,
			@RequestAttribute(name = "error", required = false) String error, Model model) {

		ModelAndView mav = new ModelAndView();
		BookingEntity booking = bookingService.findByBookingId(bookingId);

		String paymentType = paymentTypeService.findByPaymentTypeId(booking.getPaymentType().getPaymentTypeId())
				.getPaymentTypeDesc();

		if (paymentType.equalsIgnoreCase("Paypal")) {
			if (error != null) {
				bookingService.delete(booking);
				mav = new ModelAndView("redirect:/users/showbookings?result=" + error + "#Reservaciones");
				return mav;
			}

			BookingStatusEntity bookingStatus = bookingStatusService.findByBookingStatusDesc("Abierta");

			booking.setBookingStatus(bookingStatus);

			if (bookingService.createBooking(booking) == null) {
				result = "Ocurrió un error al guardar la reservación";
				mav = new ModelAndView("redirect:/users/showbookings?result=" + result + "#Reservaciones");
				return mav;
			}
		}

		try {
			emailService.sendHTMLMessage("rogerdavila.stech@gmail.com", "BambinoCare - Nueva reservación",
					getBookingHTML(booking));

			emailService.sendMessageWithAttachment(booking.getClient().getUser().getEmail(),
					"BambinoCare - Nueva reservación",
					"<html><body style='width: 100%; height: 100%'><img style='width: 100%; height: auto;' src='cid:reservacion.jpg'/></body></html>",
					"reservacion.jpg");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		result = "La reservación se realizó exitosamente.";
		mav = new ModelAndView("redirect:/users/showbookings?result=" + result + "#Reservaciones");
		return mav;

	}

	public String getBookingHTML(BookingEntity booking) {

		String bambinos = "";
		String bambinosAges = "";
		if (booking.getBookingType().getBookingTypeId() == 1 || booking.getBookingType().getBookingTypeId() == 4) {
			for (BambinoEntity bambino : booking.getBambino()) {
				bambinos += bambino.getFirstname() + " " + bambino.getLastname() + ",";
				bambinosAges += bambino.getAge() + ",";
			}
			bambinos = bambinos.substring(0, bambinos.length() - 1);
			bambinosAges = bambinosAges.substring(0, bambinosAges.length() - 1);
		} else if (booking.getBookingType().getBookingTypeId() == 3) {
			bambinosAges = booking.getEvent().getAge();
		}

		BookingTypeEntity bookingType = bookingTypeService
				.findByBookingTypeId(booking.getBookingType().getBookingTypeId());

		return "<html><body><p>El usuario " + booking.getClient().getUser().getEmail()
				+ " ha agendado una nueva cita: </p><table border=\"1\">"
				+ "<thead><tr><th>Nombre del Cliente</th><th>Email del Cliente</th><th>Servicio</th><th>Bambinos</th><th>Edades</th>"
				+ "<th>Fecha</th><th>Horario</th><th>Lugar</th></tr></thead><tbody><tr><td>"
				+ booking.getClient().getUser().getFirstname() + " " + booking.getClient().getUser().getLastname()
				+ "</td><td>" + booking.getClient().getUser().getEmail() + "</td><td>"
				+ bookingType.getBookingTypeDesc() + "</td><td>" + bambinos + "</td><td>" + bambinosAges + "</td><td>"
				+ booking.getDate() + "</td><td>" + booking.getDuration() + "</td><td>"
				+ booking.getClient().getStreet() + " " + booking.getClient().getNeighborhood() + " "
				+ booking.getClient().getState().getStateDesc() + "</td></tr>"
				+ "</tbody></table><p>Puedes revisar el detalle en"
				+ " la siguiente liga: \n\r \n\r localhost:8080</p></body></html>";

	}

	/*
	 * @GetMapping("/editbookingform") public ModelAndView
	 * showEditBooking(@RequestParam(required = false) String result,
	 * 
	 * @RequestParam(required = true) Integer bookingId, Model model) {
	 * 
	 * ModelAndView mav; User user = (User)
	 * SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	 * UserEntity userEntity = userService.findByEmail(user.getUsername());
	 * 
	 * BookingEntity booking =
	 * bookingService.findByBookingIdAndUserAndBookingStatusBookingStatusDescNotIn(
	 * bookingId, userEntity, "Cancelada");
	 * 
	 * if (booking == null) { result =
	 * "La reservación solicitada no existe o no tienes permisos para visualizarla o ya se encuentra cancelada"
	 * ; mav = new ModelAndView("redirect:/users/showbookings");
	 * mav.addObject("result", result); return mav; }
	 * 
	 * if (booking.getBambino() != null) { List<String> bambinoIds = new
	 * ArrayList<>(); for (BambinoEntity bambino : booking.getBambino()) {
	 * bambinoIds.add(bambino.getBambinoId().toString()); }
	 * booking.setBambinoId(bambinoIds); }
	 * 
	 * mav = new ModelAndView(ViewConstants.BOOKING_EDIT);
	 * mav.addAllObjects(getModelMapForCreateBookingForm(result, booking)); return
	 * mav; }
	 * 
	 * @PostMapping("/editbooking") public ModelAndView
	 * editBooking(@ModelAttribute(name = "booking") BookingEntity booking,
	 * BindingResult bindingResult, Model model) {
	 * 
	 * ModelAndView mav = new ModelAndView();
	 * 
	 * String result = "";
	 * 
	 * User user = (User)
	 * SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	 * UserEntity userEntity = userService.findByEmail(user.getUsername());
	 * 
	 * ValidationModel validationModel = bookingService.validateBookingForm(booking,
	 * user);
	 * 
	 * if (validationModel.getResult() != null) { if
	 * (validationModel.isRequireOtherView()) { mav = new
	 * ModelAndView(validationModel.getOtherView()); mav.addObject("result",
	 * validationModel.getResult()); } else { mav = new
	 * ModelAndView(ViewConstants.BOOKING_EDIT);
	 * mav.addAllObjects(getModelMapForCreateBookingForm(validationModel.getResult()
	 * , booking)); } return mav; }
	 * 
	 * // Asignación de Bambinos solo para BambinoCare y BambinoASAP if
	 * (booking.getBookingType().getBookingTypeId() == 1 ||
	 * booking.getBookingType().getBookingTypeId() == 4) { if (booking.getBambino()
	 * != null) { List<String> bambinoIds = new ArrayList<>(); for (BambinoEntity
	 * bambino : booking.getBambino()) {
	 * bambinoIds.add(bambino.getBambinoId().toString()); }
	 * booking.setBambinoId(bambinoIds); }
	 * 
	 * if (booking.getBambinoId().size() <= 0) { mav = new
	 * ModelAndView(ViewConstants.BOOKING_EDIT); result =
	 * "Favor de elegir al menos un bambino";
	 * mav.addAllObjects(getModelMapForCreateBookingForm(result, booking)); return
	 * mav; }
	 * 
	 * booking.setBambino(bambinoService.findBambinosByBambinoIdAndUser(booking.
	 * getBambinoId(), userEntity));
	 * 
	 * if (booking.getBambino().isEmpty()) { mav = new
	 * ModelAndView(ViewConstants.BOOKING_EDIT); result =
	 * "Ocurrió un error al intentar agregar a los bambinos";
	 * mav.addAllObjects(getModelMapForCreateBookingForm(result, booking)); return
	 * mav; } }
	 * 
	 * BookingEntity oldBooking =
	 * bookingService.findByBookingIdAndUser(booking.getBookingId(), userEntity);
	 * 
	 * oldBooking.setDuration(booking.getDuration());
	 * oldBooking.setDate(bookingService.getDate(booking.getDate(), 1));
	 * oldBooking.setHour(booking.getHour());
	 * oldBooking.setBambino(booking.getBambino());
	 * oldBooking.setCost(costService.calculateTotalCostByBooking(booking));
	 * 
	 * if (bookingService.createBooking(oldBooking) != null) {
	 * emailService.sendSimpleMessage("rogerdavila.stech@gmail.com",
	 * "Reservación Modificada", "El usuario " +
	 * oldBooking.getClient().getUser().getEmail() +
	 * " ha modificado la reservación del día " + oldBooking.getDate() +
	 * ". Puedes revisar el detalle en" +
	 * " la siguiente liga: \n\r \n\r localhost:8080"); result =
	 * "La reservación fue modificada con éxito!"; } else { result =
	 * "Ocurrió un error al intentar editar la reservación, vuelva a intentarlo"
	 * ; }
	 * 
	 * mav = new ModelAndView("redirect:/users/showbookings#Reservaciones");
	 * mav.addObject("result", result); return mav; }
	 */

	@PostMapping("/edituser")
	public ModelAndView edituser(@ModelAttribute(name = "client") ClientEntity client, BindingResult bindingResult,
			Model model) {

		ModelAndView mav;

		String empty = "";
		String result = empty;

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		ValidationModel validationModel = clientService.validateClientForm(client, user);

		if (validationModel.getResult() != null) {
			if (validationModel.isRequireOtherView()) {
				mav = new ModelAndView(validationModel.getOtherView());

			} else {
				mav = new ModelAndView("redirect:/users/showbookings");
			}
			mav.addObject("result", validationModel.getResult());
			return mav;
		}

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

		/*
		if (clientService.createClient(oldClient) != null) {
			result = "Se ha modificado el perfil de usuario!";
		} else {
			result = "Ocurrió un error al intentar editar el perfil, vuelva a intentarlo";
		}
		*/
		mav = new ModelAndView("redirect:/users/showbookings");
		mav.addObject("result", result);
		return mav;
	}

	@PostMapping("/cancelbooking")
	public String cancelBooking(@RequestParam(name = "bookingId") Integer bookingId, Model model) {

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
								+ " Puedes revisar el detalle en" + " la siguiente liga: \n\r \n\r localhost:8080");

			} else {
				result = "No se permiten cancelaciones de reservación";
			}
		} else {
			result = "No se puede cancelar la reservación solicitada";
		}

		return "redirect:/users/showbookings?result=" + result;
	}

	@GetMapping("/cancel")
	public String cancel() {
		return "redirect:/users/showbookings";
	}

	// Bambinos

	@PostMapping("/newbambino")
	public String newbambino(@ModelAttribute(name = "bambino") BambinoEntity bambino, BindingResult bindingResult,
			Model model) {

		String result = "";

		if (bambino.getFirstname() == null || bambino.getFirstname().equalsIgnoreCase("")) {
			result = "Favor de verificar el Nombre";
			return "redirect:/users/createbookingform?result=" + result;
		} else if (bambino.getLastname() == null || bambino.getLastname().equalsIgnoreCase("")) {
			result = "Favor de verificar Apellido";
			return "redirect:/users/showbookings?result=" + result;
		} else if (bambino.getAge() == null) {
			result = "Verficar la edad del Bambino";
			return "redirect:/users/createbookingform?result=" + result;
		} else if (bambino.getGrade() == null || bambino.getGrade().equals("")) {
			result = "Favor de verificar el campo Hora";
			return "redirect:/users/createbookingform?result=" + result;
		}

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ClientEntity client = clientService.findByUserEmail(user.getUsername());

		bambino.setClient(client);

		if (bambinoService.createBambino(bambino) != null) {
			result = "Se ha agregado al Bambino exitosamente.";
		} else {
			result = "No se ha podido agregar al Bambino, intente nuevamente";
			return "redirect:/users/createbookingform?result=" + result;
		}

		return "redirect:/users/showbookings?result=" + result + "#MisBambinos";
	}

	@GetMapping("/createbambinoform")
	public String createbambinoform(@RequestParam(required = false) String result, Model model) {
		BambinoEntity bambino = new BambinoEntity();

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());
		model.addAttribute("usernameLogged", userEntity.getFirstname());

		model.addAttribute("bambino", bambino);
		model.addAttribute("result", result);

		return ViewConstants.CREATE_BAMBINO;
	}

	@GetMapping("/editBambinoForm")
	public String editBambinoForm(@RequestParam(required = false) String result,
			@RequestParam(required = true) Integer bambinoId, Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		BambinoEntity bambino = bambinoService.findByBambinoIdAndUser(bambinoId, userEntity);

		model.addAttribute("usernameLogged", userEntity.getFirstname());

		model.addAttribute("bambino", bambino);

		model.addAttribute("result", result);

		return ViewConstants.EDIT_BAMBINO;
	}

	@PostMapping("/editBambino")
	public String editbambino(@ModelAttribute(name = "bambino") BambinoEntity bambino, BindingResult bindingResult,
			Model model) {

		String result = "";

		if (bambino.getFirstname() == null || bambino.getFirstname().equals("")) {
			result = "Favor de verificar el nombre";
			return "redirect:/users/showbookings?result=" + result;
		} else if (bambino.getLastname() == null || bambino.getLastname().equals("")) {
			result = "Favor de verificar el apellido";
			return "redirect:/users/showbookings?result=" + result;
		} else if (bambino.getAge() == null) {
			result = "Favor de verificar la edad";
			return "redirect:/users/showbookings?result=" + result;
		} else if (bambino.getGrade() == null || bambino.getGrade().equals("")) {
			result = "Favor de verificar el grado escolar";
			return "redirect:/users/showbookings?result=" + result;
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

		return "redirect:/users/showbookings?result=" + result;
	}

	@PostMapping("/removeBambino")
	public String removeBambino(@RequestParam(required = false) String result,
			@RequestParam(required = true) Integer bambinoId, Model model) {
		bambinoService.removeBambino(bambinoId);
		result = "El bambino fue borrado con éxito!";
		return "redirect:/users/showbookings?result=" + result;
	}

	// contactos

	@GetMapping("/createcontactform")
	public String createcontactform(@RequestParam(required = false) String result, Model model) {
		EmergencyContactEntity contact = new EmergencyContactEntity();

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());
		model.addAttribute("usernameLogged", userEntity.getFirstname());

		model.addAttribute("contact", contact);
		model.addAttribute("result", result);

		return ViewConstants.CREATE_CONTACT;
	}

	@PostMapping("/newcontact")
	public String newcontact(@ModelAttribute(name = "contact") EmergencyContactEntity contact,
			BindingResult bindingResult, Model model) {

		String result = "";

		if (contact.getFirstname() == null || contact.getFirstname().equals("")) {
			result = "Favor de verificar el Nombre";
			return "redirect:/users/showbookings?result=" + result;
		} else if (contact.getLastname() == null || contact.getLastname().equals("")) {
			result = "Favor de verificar el Apellido";
			return "redirect:/users/showbookings?result=" + result;
		} else if (contact.getPhone() == null || contact.getPhone().equals("")) {
			result = "Favor de verificar el email";
			return "redirect:/users/showbookings?result=" + result;
		} else if (contact.getStreet() == null || contact.getStreet().equals("")) {
			result = "Favor de verificar la calle";
			return "redirect:/users/showbookings?result=" + result;
		} else if (contact.getNeighborhood() == null || contact.getNeighborhood().equals("")) {
			result = "Favor de verificar la colonia";
			return "redirect:/users/showbookings?result=" + result;
		} else if (contact.getCity() == null || contact.getCity().equals("")) {
			result = "Favor de verificar el Municipio";
			return "redirect:/users/showbookings?result=" + result;
		} else if (contact.getState() == null || contact.getState().equals("")) {
			result = "Favor de verificar el Estado";
			return "redirect:/users/showbookings?result=" + result;
		} else if (contact.getRelationship() == null || contact.getRelationship().equals("")) {
			result = "Favor de verificar el parentesco";
			return "redirect:/users/showbookings?result=" + result;
		}

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ClientEntity client = clientService.findByUserEmail(user.getUsername());

		contact.setClient(client);

		if (emergencyContactService.createContact(contact) != null) {
			result = "El contacto se ha creado exitosamente.";
		} else {
			result = "No se ha podido crear el contacto, intente nuevamente";
			return "redirect:/users/createbookingform?result=" + result;
		}

		return "redirect:/users/showbookings?result=" + result + "#Contactos";
	}

	@GetMapping("/editcontactForm")
	public String editContactForm(@RequestParam(required = false) String result,
			@RequestParam(required = true) Integer contactId, Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity userEntity = userService.findByEmail(user.getUsername());

		EmergencyContactEntity contact = emergencyContactService.findByContactIdAndUser(contactId, userEntity);

		model.addAttribute("usernameLogged", userEntity.getFirstname());

		model.addAttribute("contact", contact);

		model.addAttribute("result", result);

		return ViewConstants.EDIT_CONTACT;
	}

	@PostMapping("/editContact")
	public String editcontact(@ModelAttribute(name = "bambino") EmergencyContactEntity contact,
			BindingResult bindingResult, Model model) {

		String result = "";

		if (contact.getFirstname() == null || contact.getFirstname().equals("")) {
			result = "Favor de verificar el Nombre";
			return "redirect:/users/showbookings?result=" + result;
		} else if (contact.getLastname() == null || contact.getLastname().equals("")) {
			result = "Favor de verificar el Apellido";
			return "redirect:/users/showbookings?result=" + result;
		} else if (contact.getPhone() == null || contact.getPhone().equals("")) {
			result = "Favor de verificar el email";
			return "redirect:/users/showbookings?result=" + result;
		} else if (contact.getStreet() == null || contact.getStreet().equals("")) {
			result = "Favor de verificar la calle";
			return "redirect:/users/showbookings?result=" + result;
		} else if (contact.getNeighborhood() == null || contact.getNeighborhood().equals("")) {
			result = "Favor de verificar la colonia";
			return "redirect:/users/showbookings?result=" + result;
		} else if (contact.getCity() == null || contact.getCity().equals("")) {
			result = "Favor de verificar el Municipio";
			return "redirect:/users/showbookings?result=" + result;
		} else if (contact.getState() == null || contact.getState().equals("")) {
			result = "Favor de verificar el Estado";
			return "redirect:/users/showbookings?result=" + result;
		} else if (contact.getRelationship() == null || contact.getRelationship().equals("")) {
			result = "Favor de verificar el parentesco";
			return "redirect:/users/showbookings?result=" + result;
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

		return "redirect:/users/showbookings?result=" + result;
	}

	@PostMapping("/removeContact")
	public String removeContacts(@RequestParam(required = false) String result,
			@RequestParam(required = true) Integer contactId, Model model) {
		emergencyContactService.removeContact(contactId);
		result = "El bambino fue borrado con éxito!";
		return "redirect:/users/showbookings?result=" + result;
	}

}
