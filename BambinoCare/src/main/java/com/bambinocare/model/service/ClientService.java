package com.bambinocare.model.service;

import org.springframework.security.core.userdetails.User;

import com.bambinocare.model.ValidationModel;
import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.entity.UserEntity;

public interface ClientService {

	ClientEntity createClient(ClientEntity client);
	ClientEntity findByUser(UserEntity user);
	ClientEntity findByUserEmail(String email);
	ClientEntity saveClient(ClientEntity client);
	ValidationModel validateClientForm(ClientEntity client, User user);
	
}
