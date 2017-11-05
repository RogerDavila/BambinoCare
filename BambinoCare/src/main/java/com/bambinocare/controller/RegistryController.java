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
import com.bambinocare.model.service.ClientService;
import com.bambinocare.model.service.RolService;
import com.bambinocare.model.service.UserService;

@Controller
@RequestMapping("/registry")
public class RegistryController {

	@Autowired
	@Qualifier("clientService")
	private ClientService clientService;

	@Autowired
	@Qualifier("rolService")
	private RolService rolService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@GetMapping("/registryform")
	public String showRegistryForm(@RequestParam(required=false) String result,@RequestParam(required=false) String error, Model model) {
		ClientEntity client = new ClientEntity();

		List<RolEntity> roles = rolService.findAllRoles();

		model.addAttribute("error", error);
		model.addAttribute("result", result);
		model.addAttribute("roles", roles);
		model.addAttribute("client", client);

		return ViewConstants.REGISTRY_FORM;
	}

	@PostMapping("/createuserclient")
	public ModelAndView createUserClient(@ModelAttribute(name = "client") ClientEntity client, Model model) {

		String error = null;
		String result = null;
		
		if (client.getUser() == null){
			//model.addAttribute("error", "Favor de verificar el campo usuario");
			error = "Favor de verificar el campo usuario";
			return new ModelAndView("redirect:/registry/registryform?error=" + error);
		}
		if (client.getUser().getName() == null || client.getUser().getName().equals("")) {
			//model.addAttribute("error", "Favor de verificar el campo Nombre");
			error = "Favor de verificar el campo Nombre";
			return new ModelAndView("redirect:/registry/registryform?error=" + error);
		}
		if (client.getUser().getLastname() == null || client.getUser().getLastname().equals("")) {
			//model.addAttribute("error", "Favor de verificar el campo Apellido");
			error = "Favor de verificar el campo Apellido";
			return new ModelAndView("redirect:/registry/registryform?error=" + error);
		}
		if (client.getUser().getTelephone() == null || client.getUser().getTelephone().equals("")) {
			//model.addAttribute("error", "Favor de verificar el campo Telefono");
			error = "Favor de verificar el campo Telefono";
			return new ModelAndView("redirect:/registry/registryform?error=" + error);
		}
		if (client.getStreet() == null || client.getStreet().equals("")) {
			//model.addAttribute("error", "Favor de verificar el campo Calle");
			error = "Favor de verificar el campo Calle";
			return new ModelAndView("redirect:/registry/registryform?error=" + error);
		}
		if (client.getSuburb() == null || client.getSuburb().equals("")) {
			//model.addAttribute("error", "Favor de verificar el campo Colonia");
			error = "Favor de verificar el campo Colonia";
			return new ModelAndView("redirect:/registry/registryform?error=" + error);
		}
		if (client.getTown() == null || client.getTown().equals("")) {
			//model.addAttribute("error", "Favor de verificar el campo Municipio");
			error = "Favor de verificar el campo Municipio";
			return new ModelAndView("redirect:/registry/registryform?error=" + error);
		}
		if (client.getState() == null || client.getState().equals("")) {
			//model.addAttribute("error", "Favor de verificar el campo Estado");
			error = "Favor de verificar el campo Estado";
			return new ModelAndView("redirect:/registry/registryform?error=" + error);
		}
		if (client.getEmployment() == null || client.getEmployment().equals("")) {
			//model.addAttribute("error", "Favor de verificar el campo Ocupacion");
			error = "Favor de verificar el campo Ocupacion";
			return new ModelAndView("redirect:/registry/registryform?error=" + error);
		}
		if (client.getUser().getEmail() == null || client.getUser().getEmail().equals("")) {
			//model.addAttribute("error", "Favor de verificar el campo Correo");
			error = "Favor de verificar el campo Correo";
			return new ModelAndView("redirect:/registry/registryform?error=" + error);
		}
		if (client.getUser().getPassword() == null || client.getUser().getPassword().equals("")) {
			//model.addAttribute("error", "Favor de verificar el campo Contraseña");
			error = "Favor de verificar el campo Contraseña";
			return new ModelAndView("redirect:/registry/registryform?error=" + error);
		}

		if(userService.userExist(client.getUser().getEmail())) {
			//model.addAttribute("error", "El email introducido ya existe");
			error = "El email introducido ya existe";
			return new ModelAndView("redirect:/registry/registryform?error=" + error);
		}
		
		if (clientService.createClient(client) != null) {
			result = "1";
			//model.addAttribute("result", 1);
		} else {
			//model.addAttribute("result", 0);
			result = "0";
		}

		return new ModelAndView("redirect:/registry/registryform?result=" + result);
	}

	@GetMapping("/cancel")
	public String cancel() {
		return "redirect:/secure/user/user";
	}

}
