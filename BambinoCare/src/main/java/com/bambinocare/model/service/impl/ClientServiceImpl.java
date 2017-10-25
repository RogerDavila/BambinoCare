package com.bambinocare.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.repository.ClientRepository;
import com.bambinocare.model.service.ClientService;

@Service("clientService")
public class ClientServiceImpl implements ClientService{

	@Autowired
	@Qualifier("clientRepository")
	private ClientRepository clientRepository;
	
	@Override
	public ClientEntity createClient(ClientEntity client) {
		return clientRepository.save(client);
	}

}
