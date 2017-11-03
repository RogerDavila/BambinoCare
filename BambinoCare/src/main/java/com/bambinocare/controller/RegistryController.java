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

import com.bambinocare.constants.ViewConstants;
import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.entity.RolEntity;
import com.bambinocare.model.service.ClientService;
import com.bambinocare.model.service.RolService;

@Controller
@RequestMapping("/registry")
public class RegistryController {

	@Autowired
	@Qualifier("clientService")
	private ClientService clientService;
	
	@Autowired
	@Qualifier("rolService")
	private RolService rolService;

	@GetMapping("/registryform")
	public String showRegistryForm(Model model){
		ClientEntity client= new ClientEntity();
		
		List<RolEntity> roles =  rolService.findAllRoles();
		
		model.addAttribute("roles", roles);
		model.addAttribute("client", client);
		
		return ViewConstants.REGISTRY_FORM;
	}
	
	@PostMapping("/createuserclient")
	public String createUserClient(@ModelAttribute(name="client") ClientEntity client, Model model){
		
		if(clientService.createClient(client)!=null){
			model.addAttribute("result",1);
		}else{
			model.addAttribute("result",0);
		}
		
		 return "redirect:/registry/registryform";
	}
	
	@GetMapping("/cancel")
	public String cancel(){
		return "redirect:/secure/user/user";
	}
	
}
