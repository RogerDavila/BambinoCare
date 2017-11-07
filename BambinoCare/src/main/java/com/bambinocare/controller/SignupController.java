package com.bambinocare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bambinocare.constants.ViewConstants;
import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.entity.RolEntity;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.service.ClientService;
import com.bambinocare.model.service.EmailService;
import com.bambinocare.model.service.RolService;
import com.bambinocare.model.service.UserService;

@Controller
@RequestMapping("/signup")
public class SignupController {

	@Autowired
	@Qualifier("clientService")
	private ClientService clientService;

	@Autowired
	@Qualifier("rolService")
	private RolService rolService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("emailService")
	private EmailService emailService;

	@GetMapping("/signupform")
	public String showSignupForm(@RequestParam(required = false) String result,
			@RequestParam(required = false) String error, Model model) {
		ClientEntity client = new ClientEntity();

		List<RolEntity> roles = rolService.findAllRoles();

		model.addAttribute("error", error);
		model.addAttribute("result", result);
		model.addAttribute("roles", roles);
		model.addAttribute("client", client);

		return ViewConstants.SIGNUP_FORM;
	}

	@PostMapping("/createuserclient")
	public ModelAndView createUserClient(@ModelAttribute(name = "client") ClientEntity client, Model model) {

		String error = null;
		String result = null;

		if (client.getUser() == null) {
			error = "Favor de verificar el campo usuario";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}
		if (client.getUser().getName() == null || client.getUser().getName().equals("")) {
			error = "Favor de verificar el campo Nombre";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}
		if (client.getUser().getLastname() == null || client.getUser().getLastname().equals("")) {
			error = "Favor de verificar el campo Apellido";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}
		if (client.getUser().getTelephone() == null || client.getUser().getTelephone().equals("")) {
			error = "Favor de verificar el campo Telefono";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}
		if (client.getStreet() == null || client.getStreet().equals("")) {
			error = "Favor de verificar el campo Calle";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}
		if (client.getSuburb() == null || client.getSuburb().equals("")) {
			error = "Favor de verificar el campo Colonia";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}
		if (client.getTown() == null || client.getTown().equals("")) {
			error = "Favor de verificar el campo Municipio";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}
		if (client.getState() == null || client.getState().equals("")) {
			error = "Favor de verificar el campo Estado";
			return new ModelAndView("redirect:/signup/signupform?error=" + error);
		}
		if (client.getEmployment() == null || client.getEmployment().equals("")) {
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
				emailService.sendSimpleMessage(user.getEmail(), "Alta de cuenta BambinoCare", "Hola "
						+ user.getName() + "!,\n\r " + "Tu cuenta ha sido dada de alta, por favor ingresa al siguiente enlace"
								+ " para activarla: \n\r \n\r www.bambinocare.com");
			}
		} else {
			result = "0";
		}

		return new ModelAndView("redirect:/signup/signupform?result=" + result);
	}

	@GetMapping("/cancel")
	public String cancel() {
		return "redirect:/";
	}

}
