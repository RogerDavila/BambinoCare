package com.bambinocare.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bambinocare.constant.ViewConstants;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.service.EmailService;
import com.bambinocare.model.service.UserService;
import com.bambinocare.utils.RandomPassword;

@Controller
public class LoginController {

	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("emailService")
	private EmailService emailService;
	
	@GetMapping("/login")
	public String showLoginForm( Model model,
			@RequestParam(name="error", required=false) String error, 
			@RequestParam(name="logout", required=false)String logout){
		
		if(!SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get().getAuthority().equals("ROLE_ANONYMOUS")) {
			return "redirect:/loginsuccess";
		}
		
		error = (error != null) ? "Usuario/Contraseña inv%C3%A1lidos" : null;
		
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		return ViewConstants.LOGIN_FORM;
	}
	
	@GetMapping("/loginsuccess")
	public String loginCheck(){
		
		@SuppressWarnings("unchecked")
		Optional <SimpleGrantedAuthority> rol = (Optional<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst();
		if(rol.isPresent()) {
			String rolStr = rol.get().getAuthority();
			if(rolStr.equals("Cliente")) {
				return "redirect:/users/showbookings";
			}else if(rolStr.equals("Nanny")){
				return "redirect:/nannies/showbookings";
			}else if(rolStr.equals("Administrador")){
				return "redirect:/admin/showbookings";
			}else {
				return "redirect:/login";
			}
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/recoverypasswordform")
	public ModelAndView recoverypasswordform(@RequestParam(required = false) String result) {
		ModelAndView mav = new ModelAndView(ViewConstants.RECOVERYPASSWORD_FORM);
		
		UserEntity user = new UserEntity();
		
		mav.addObject("result", result);
		mav.addObject("user",user);
		
		return mav;
	}
	
	@PostMapping("/recoverypassword")
	public ModelAndView recoverypassword(@ModelAttribute(name="user") UserEntity user, Model model) {
		String result = null;
		if(!userService.userExist(user.getEmail())) {
			result = "El email introducido no existe.";
			return new ModelAndView("redirect:/recoverypasswordform?result="+result);
		}else {
			RandomPassword rp = new RandomPassword();
			user.setPassword(rp.nextString());
			UserEntity newUser = userService.updatePassword(user.getEmail(), user.getPassword());
			if(newUser != null) {
				emailService.sendSimpleMessage(user.getEmail(), "BambinoCare - Cambio de contraseña", "Hola " + newUser.getFirstname() + "!,\n\r "
						+ "Te enviamos tu nueva contraseña: " + user.getPassword());
				result = "Tu nueva contraseña fue enviada a tu correo.";
				return new ModelAndView("redirect:/recoverypasswordform?result="+result);
			}else {
				result = "Ocurri%C3%B3 un error al intentar reestablecer tu contraseña, por favor intentalo de nuevo.";
				return new ModelAndView("redirect:/recoverypasswordform?result="+result);
			}
		}
	}
}
