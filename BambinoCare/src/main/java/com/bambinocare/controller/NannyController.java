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
import com.bambinocare.model.entity.NannyEntity;
import com.bambinocare.model.entity.RolEntity;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.service.NannyService;
import com.bambinocare.model.service.RolService;

@Controller
@RequestMapping("/nannies")
public class NannyController {

	@Autowired
	@Qualifier("nannyService")
	private NannyService nannyService;
	
	@Autowired
	@Qualifier("rolService")
	private RolService rolService;
	
	@GetMapping("/shownannies")
	public ModelAndView showNannies(){
		ModelAndView mav = new ModelAndView(ViewConstants.NANNY_SHOW);
		
		mav.addObject("nannies", nannyService.findAllNannies());
		
		return mav;
	}
	
	@GetMapping("/nannycreateform")
	public String showNannyCreate(Model model){
		NannyEntity nanny = new NannyEntity();
		
		List<RolEntity> roles =  rolService.findAllRoles();
		
		model.addAttribute("roles", roles);
		model.addAttribute("nanny", nanny);
		
		return ViewConstants.NANNY_CREATE;
	}
	
	@GetMapping("/nannyeditform")
	public String showNannyEdit(@RequestParam(name="idNanny", required=true) Integer idNanny, Model model){
		NannyEntity nanny = new NannyEntity(new UserEntity());
		
		if(idNanny > 0 ){
			nanny = nannyService.findNannyByIdNanny(idNanny);
		}
		
		List<RolEntity> roles =  rolService.findAllRoles();
		
		model.addAttribute("roles", roles);
		model.addAttribute("nanny",nanny);
		
		return ViewConstants.NANNY_EDIT;
	}
	
	@PostMapping("/editnanny")
	public String editNanny(@ModelAttribute(name="nanny") NannyEntity nanny, Model model){
		if(nannyService.editNanny(nanny)!=null){
			model.addAttribute("result",1);
		}else{
			model.addAttribute("result",0);
		}
		
		 return "redirect:/nannies/shownannies";
	}
	
	@PostMapping("/createnanny")
	public String createNanny(@ModelAttribute(name="nanny") NannyEntity nanny, Model model){
		if(nannyService.createNanny(nanny)!=null){
			model.addAttribute("result",1);
		}else{
			model.addAttribute("result",0);
		}
		
		 return "redirect:/nannies/shownannies";
	}
	
	@GetMapping("/cancel")
	public String cancel(){
		return "redirect:/nannies/shownannies";
	}
	
	@GetMapping("/removenanny")
	public String removeUser(@RequestParam(name="idNanny", required=true) Integer idNanny){
		nannyService.removeNanny(idNanny);
		return "redirect:/nannies/shownannies";
	}
}
