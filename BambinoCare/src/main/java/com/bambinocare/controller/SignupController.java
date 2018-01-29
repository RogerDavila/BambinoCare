package com.bambinocare.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bambinocare.constant.ViewConstants;
import com.bambinocare.event.OnRegistrationCompleteEvent;
import com.bambinocare.model.entity.CityEntity;
import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.entity.RoleEntity;
import com.bambinocare.model.entity.StateEntity;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.entity.VerificationTokenEntity;
import com.bambinocare.model.service.CityService;
import com.bambinocare.model.service.ClientService;
import com.bambinocare.model.service.EmailService;
import com.bambinocare.model.service.RoleService;
import com.bambinocare.model.service.StateService;
import com.bambinocare.model.service.UserService;

@Controller
@RequestMapping("/signup")
public class SignupController {

	@Autowired
	ApplicationEventPublisher eventPublisher;

	@Autowired
	@Qualifier("clientService")
	private ClientService clientService;

	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("emailService")
	private EmailService emailService;

	@Autowired
	@Qualifier("cityService")
	private CityService cityService;

	@Autowired
	@Qualifier("stateService")
	private StateService stateService;

	@GetMapping("/signupform")
	public String showSignupForm(@RequestParam(required = false) String result, Model model) {

		if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get()
				.getAuthority().equals("ROLE_ANONYMOUS")) {
			return "redirect:/loginsuccess";
		}

		ClientEntity client = new ClientEntity();

		List<RoleEntity> roles = roleService.findAllRoles();
		List<CityEntity> cities = cityService.findAll();
		List<StateEntity> states = stateService.findAll();

		model.addAttribute("result", result);
		model.addAttribute("roles", roles);
		model.addAttribute("client", client);
		model.addAttribute("cities", cities);
		model.addAttribute("states", states);

		return ViewConstants.SIGNUP_FORM;
	}

	@RequestMapping(value = "/createuserclient", method = RequestMethod.POST)
	public ModelAndView createUserClient(@ModelAttribute(name = "client") ClientEntity client,
			BindingResult bindingResult, WebRequest request, Model model) {

		ModelAndView mav = new ModelAndView();

		String result = null;

		if (client.getUser() == null) {
			result = "Favor de verificar el campo usuario";
			return new ModelAndView("redirect:/signup/signupform?result=" + result);
		}
		if (client.getUser().getFirstname() == null || client.getUser().getFirstname().equals("")) {
			result = "Favor de verificar el campo Nombre";
			return new ModelAndView("redirect:/signup/signupform?result=" + result);
		}
		if (client.getUser().getLastname() == null || client.getUser().getLastname().equals("")) {
			result = "Favor de verificar el campo Apellido";
			return new ModelAndView("redirect:/signup/signupform?result=" + result);
		}
		if (client.getUser().getPhone() == null || client.getUser().getPhone().equals("")) {
			result = "Favor de verificar el campo Telefono";
			return new ModelAndView("redirect:/signup/signupform?result=" + result);
		}
		if (client.getStreet() == null || client.getStreet().equals("")) {
			result = "Favor de verificar el campo Calle";
			return new ModelAndView("redirect:/signup/signupform?result=" + result);
		}
		if (client.getNeighborhood() == null || client.getNeighborhood().equals("")) {
			result = "Favor de verificar el campo Colonia";
			return new ModelAndView("redirect:/signup/signupform?result=" + result);
		}
		if (client.getCity() == null || client.getCity().equals("")) {
			result = "Favor de verificar el campo Municipio";
			return new ModelAndView("redirect:/signup/signupform?result=" + result);
		}
		if (client.getState() == null || client.getState().equals("")) {
			result = "Favor de verificar el campo Estado";
			return new ModelAndView("redirect:/signup/signupform?result=" + result);
		}
		if (client.getJob() == null || client.getJob().equals("")) {
			result = "Favor de verificar el campo Ocupacion";
			return new ModelAndView("redirect:/signup/signupform?result=" + result);
		}
		if (client.getUser().getEmail() == null || client.getUser().getEmail().equals("")) {
			result = "Favor de verificar el campo Correo";
			return new ModelAndView("redirect:/signup/signupform?result=" + result);
		}
		if (client.getUser().getPassword() == null || client.getUser().getPassword().equals("")) {
			result = "Favor de verificar el campo Contraseña";
			return new ModelAndView("redirect:/signup/signupform?result=" + result);
		}

		if (!client.getUser().getPasswordConfirm().equals(client.getUser().getPassword())) {

			mav = new ModelAndView(ViewConstants.SIGNUP_FORM);

			result = "La contraseña y la confirmación de contraseña no coínciden";
			List<RoleEntity> roles = roleService.findAllRoles();
			List<CityEntity> cities = cityService.findAll();
			List<StateEntity> states = stateService.findAll();

			mav.addObject("result", result);
			mav.addObject("roles", roles);
			mav.addObject("client", client);
			model.addAttribute("cities", cities);
			model.addAttribute("states", states);

			return mav;
		}

		if (userService.userExist(client.getUser().getEmail())) {

			mav = new ModelAndView(ViewConstants.SIGNUP_FORM);

			result = "El email introducido ya existe";
			List<RoleEntity> roles = roleService.findAllRoles();
			List<CityEntity> cities = cityService.findAll();
			List<StateEntity> states = stateService.findAll();

			mav.addObject("result", result);
			mav.addObject("roles", roles);
			mav.addObject("client", client);
			model.addAttribute("cities", cities);
			model.addAttribute("states", states);

			return mav;
		}

		if (clientService.createClient(client) != null) {
			result = "Usuario registrado con éxito!. En breve recibirás un correo de confirmación de tu cuenta, con la que tendrás acceso a promociones y mejores tarifas, podrás realizar tus reservaciones de una manera sencilla y recibirás información importante.";
			UserEntity user = client.getUser();

			if (!userService.userExist(user.getEmail())) {
				result = "Ocurrió un error al intentar registrar el usuario. Favor de intentar nuevamente";
			} else {
				String appUrl = "/signup" + request.getContextPath();
				eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, appUrl));
			}
		} else {
			result = "Ocurrió un error al intentar registrar el usuario, por favor intente nuevamente";
		}
		mav.setViewName("redirect:/signup/signupform");
		mav.addObject("result", result);
		return mav;
	}

	@GetMapping("/registrationConfirm")
	public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {

		VerificationTokenEntity verificationToken = userService.getVerificationToken(token);
		if (verificationToken == null) {
			String result = "token no v%C3%A1lido";
			model.addAttribute("result", result);

			return "redirect:/baduser";
		}

		UserEntity user = verificationToken.getUser();

		user.setEnabled(true);
		userService.editUser(user);

		try {
			emailService.sendMessageWithAttachment(user.getEmail(), "BambinoCare - Registro Exitoso",
					"<html><body><img src='cid:registro.jpg'/></body></html>", "registro.jpg");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return "redirect:/login";
	}

	@GetMapping("/cancel")
	public String cancel() {
		return "redirect:/";
	}

}
