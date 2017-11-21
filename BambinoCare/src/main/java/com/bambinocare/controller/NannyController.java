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

import com.bambinocare.constant.ViewConstants;
import com.bambinocare.model.entity.NannyEntity;
import com.bambinocare.model.entity.RoleEntity;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.service.NannyService;
import com.bambinocare.model.service.RoleService;

@Controller
@RequestMapping("/nannies")
public class NannyController {

	@Autowired
	@Qualifier("nannyService")
	private NannyService nannyService;
	
	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;
	
	@GetMapping("/shownannies")
	public ModelAndView showNannies(){
		ModelAndView mav = new ModelAndView(ViewConstants.NANNY_SHOW);
		
		mav.addObject("nannies", nannyService.findAllNannies());
		
		return mav;
	}
	
	@GetMapping("/nannycreateform")
	public String showNannyCreate(Model model){
		NannyEntity nanny = new NannyEntity();
		
		List<RoleEntity> roles =  roleService.findAllRoles();
		
		model.addAttribute("roles", roles);
		model.addAttribute("nanny", nanny);
		
		return ViewConstants.NANNY_CREATE;
	}
	
	@GetMapping("/nannyeditform")
	public String showNannyEdit(@RequestParam(name="nannyId", required=true) Integer nannyId, Model model){
		NannyEntity nanny = new NannyEntity(new UserEntity());
		
		if(nannyId > 0 ){
			nanny = nannyService.findByNannyId(nannyId);
		}
		
		List<RoleEntity> roles =  roleService.findAllRoles();
		
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
	public String removeUser(@RequestParam(name="nannyId", required=true) Integer nannyId){
		nannyService.removeNanny(nannyId);
		return "redirect:/nannies/shownannies";
	}
}
