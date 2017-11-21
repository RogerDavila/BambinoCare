package com.bambinocare.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bambinocare.constant.ViewConstants;
import com.bambinocare.event.OnRegistrationCompleteEvent;
import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.entity.RoleEntity;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.entity.VerificationTokenEntity;
import com.bambinocare.model.service.ClientService;
import com.bambinocare.model.service.EmailService;
import com.bambinocare.model.service.RoleService;
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

	@GetMapping("/signupform")
	public String showSignupForm(@RequestParam(required = false) String result,
			@RequestParam(required = false) String error, Model model) {

		if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get()
				.getAuthority().equals("ROLE_ANONYMOUS")) {
			return "redirect:/loginsuccess";
		}

		ClientEntity client = new ClientEntity();

		List<RoleEntity> roles = roleService.findAllRoles();

		model.addAttribute("error", error);
		model.addAttribute("result", result);
		model.addAttribute("roles", roles);
		model.addAttribute("client", client);

		return ViewConstants.SIGNUP_FORM;
	}

	@PostMapping("/createuserclient")
	public ModelAndView createUserClient(@ModelAttribute(name = "client") ClientEntity client, 
				Model model, WebRequest request) {

		String error = null;
		String result = null;

		if (client.getUser() == null) {
			error = "Favor de verificar el campo usuario";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}
		if (client.getUser().getFirstname() == null || client.getUser().getFirstname().equals("")) {
			error = "Favor de verificar el campo Nombre";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}
		if (client.getUser().getLastname() == null || client.getUser().getLastname().equals("")) {
			error = "Favor de verificar el campo Apellido";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}
		if (client.getUser().getPhone() == null || client.getUser().getPhone().equals("")) {
			error = "Favor de verificar el campo Telefono";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}
		if (client.getStreet() == null || client.getStreet().equals("")) {
			error = "Favor de verificar el campo Calle";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}
		if (client.getNeighborhood() == null || client.getNeighborhood().equals("")) {
			error = "Favor de verificar el campo Colonia";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}
		if (client.getCity() == null || client.getCity().equals("")) {
			error = "Favor de verificar el campo Municipio";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}
		if (client.getState() == null || client.getState().equals("")) {
			error = "Favor de verificar el campo Estado";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}
		if (client.getJob() == null || client.getJob().equals("")) {
			error = "Favor de verificar el campo Ocupacion";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}
		if (client.getUser().getEmail() == null || client.getUser().getEmail().equals("")) {
			error = "Favor de verificar el campo Correo";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}
		if (client.getUser().getPassword() == null || client.getUser().getPassword().equals("")) {
			error = "Favor de verificar el campo Contraseña";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}
		
		if(!client.getUser().getPasswordConfirm().equals(client.getUser().getPassword())) {
			error = "La contraseña y la confirmación de contraseña no coínciden";
				return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}

		if (userService.userExist(client.getUser().getEmail())) {
			error = "El email introducido ya existe";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}

		if (clientService.createClient(client) != null) {
			result = "1";

			UserEntity user = client.getUser();
			
			if (!userService.userExist(user.getEmail())) {
				// Arrojar mensaje de error TEST2
			} else {
					String appUrl = "/signup" + request.getContextPath();
					eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));
			}
		} else {
			result = "0";
		}

		return new ModelAndView("redirect:/signup/signupform?result=" + result);
	}

	@GetMapping("/registrationConfirm")
	public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {
		
		Locale locale = request.getLocale();
		
		VerificationTokenEntity verificationToken = userService.getVerificationToken(token);
		if(verificationToken == null) {
			String result = "token no válido";
			model.addAttribute("result", result);
			
			return "redirect:/baduser";
		}
		
		UserEntity user = verificationToken.getUser();
		
		user.setEnabled(true);
		userService.editUser(user);
		
		try {
			emailService.sendHTMLMessage(user.getEmail(), "BambinoCare - Registro Exitoso", "<html><body><div style='text-align:center'><b>Agradecemos tu preferencia</b></div><br />" + 
					"<div style='text-align:center'>" + 
					"Te agradecemos por habernos seleccionado como tus Bambinaias para el cuidado<br />" + 
					"e integridad de tus Bambinos. A partir de ahora formas parte de la comunidad<br />" + 
					"Bambino Care y podrás tener acceso a nuestro sistema y conocer todos nuestros<br />" + 
					"servicios, así como hacer reservaciones y contar con atención personalizada.<br />"
					+ "</div>" + 
					"<br />" + 
					"<div style='text-align:center'><b>¡GRACIAS!</b></div></body></html>");
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
