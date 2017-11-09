package com.bambinocare.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.BookingEntity;
import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.entity.RolEntity;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.repository.ClientRepository;
import com.bambinocare.model.service.ClientService;

@Service("clientService")
public class ClientServiceImpl implements ClientService{

	@Autowired
	@Qualifier("clientRepository")
	private ClientRepository clientRepository;
	
	@Override
	public ClientEntity createClient(ClientEntity client) {
		
		RolEntity rolEntity = new RolEntity(3, "Cliente");
		
		client.getUser().setRol(rolEntity);
		client.getUser().setEnabled(true);
		
		UserEntity user = client.getUser();
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		
		return clientRepository.save(client);
	}
	
	@Override
	public ClientEntity findByUser(UserEntity user) {
		return clientRepository.findByUser(user);
	}
	


}
