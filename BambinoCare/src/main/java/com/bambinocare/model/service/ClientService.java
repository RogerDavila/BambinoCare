package com.bambinocare.model.service;

import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.entity.UserEntity;

public interface ClientService {

	ClientEntity createClient(ClientEntity client);
	ClientEntity findByUser(UserEntity user);
	
}
