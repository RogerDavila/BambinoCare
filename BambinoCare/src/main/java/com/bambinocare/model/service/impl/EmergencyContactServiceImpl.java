package com.bambinocare.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.entity.EmergencyContactEntity;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.repository.EmergencyContactRepository;
import com.bambinocare.model.service.EmergencyContactService;

@Service("emergencyContactService")
public class EmergencyContactServiceImpl implements EmergencyContactService{
	
	@Autowired
	@Qualifier("emergencyContactRepository")
	private EmergencyContactRepository emergencyContactRepository;
	
	@Override
	public List<EmergencyContactEntity> findByClient(ClientEntity client) {
		
		if(client == null) return new ArrayList<EmergencyContactEntity>();
		
		return emergencyContactRepository.findByClient(client);
	}
	
	@Override
	public List<EmergencyContactEntity> findByUser(UserEntity user) {
		return emergencyContactRepository.findByClientUser(user);
	}

	@Override
	public EmergencyContactEntity createContact(EmergencyContactEntity contact) {
		return emergencyContactRepository.save(contact);
	}
	
	@Override
	public EmergencyContactEntity findEmergencyContactByIdContactAndUser(Integer idContact, UserEntity user) {
		return emergencyContactRepository.findByidContactoAndClientUser(idContact, user);
	}
	
}
