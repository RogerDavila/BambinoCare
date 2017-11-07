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

import com.bambinocare.constants.ViewConstants;
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
		
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		return ViewConstants.LOGIN_FORM;
	}
	
	@GetMapping("/loginsuccess")
	public String loginCheck(){
		Optional <SimpleGrantedAuthority>  rol = (Optional<SimpleGrantedAuthority> ) SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst();
		
		if(rol.isPresent()) {
			String rolStr = rol.get().getAuthority();
			
			if(rolStr.equals("Cliente")) {
				return "redirect:/users/showbookings";
			}else if(rolStr.equals("Nanny")){
				return "redirect:/nannies/shownannies";
			}else if(rolStr.equals("Administrador")){
				System.out.println("admin");
				return "redirect:/admin/showbookings";
			}else {
				return "redirect:/login";
			}
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/recoverypasswordform")
	public ModelAndView recoverypasswordform() {
		ModelAndView mav = new ModelAndView(ViewConstants.RECOVERYPASSWORD_FORM);
		
		UserEntity user = new UserEntity();
		
		mav.addObject("user",user);
		
		return mav;
	}
	
	@PostMapping("/recoverypassword")
	public ModelAndView recoverypassword(@ModelAttribute(name="user") UserEntity user, Model model) {
		
		System.out.println("User: "+user.getEmail());
		
		if(!userService.userExist(user.getEmail())) {
			//Arrojar mensaje de error TEST2
		}else {
			RandomPassword rp = new RandomPassword();
			user.setPassword(rp.nextString());
			UserEntity newUser = userService.updatePassword(user.getEmail(), user.getPassword());
			if(newUser != null) {
				emailService.sendSimpleMessage(user.getEmail(), "Cambio de contraseña BambinoCare", "Hola " + newUser.getName() + "!,\n\r "
						+ "Te enviamos tu nueva contraseña: " + user.getPassword());
			}else {
				//Arrojar error Test3
			}
		}
		
		return new ModelAndView(ViewConstants.RECOVERYPASSWORD_FORM);
	}
}
