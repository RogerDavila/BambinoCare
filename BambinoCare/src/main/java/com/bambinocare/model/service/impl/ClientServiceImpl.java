package com.bambinocare.model.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.entity.RoleEntity;
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
		
		if(client == null) return null;
		
		RoleEntity roleEntity = new RoleEntity(3, "Cliente");
		
		client.getUser().setRole(roleEntity);
		client.getUser().setEnabled(true);
		
		UserEntity user = client.getUser();
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		
		return clientRepository.save(client);
	}
	
	@Override
	public ClientEntity findByUser(UserEntity user) {
		
		if(user == null) return null;
		
		return clientRepository.findByUser(user);
	}

	@Override
	public ClientEntity findByUserEmail(String email) {
		
		if(email == "" || email == null) return null;
		
		return clientRepository.findByUserEmail(email);
	}

	@Override
	public ClientEntity saveClient(ClientEntity client) {
		// TODO Auto-generated method stub
		return clientRepository.save(client);
	}
	


}
