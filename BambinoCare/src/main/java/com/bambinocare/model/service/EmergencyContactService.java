package com.bambinocare.model.service;

import java.util.List;

import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.entity.EmergencyContactEntity;
import com.bambinocare.model.entity.UserEntity;

public interface EmergencyContactService {

	List<EmergencyContactEntity> findByClient(ClientEntity client);
	List<EmergencyContactEntity> findByUser(UserEntity user);
	EmergencyContactEntity createContact(EmergencyContactEntity contact);
	EmergencyContactEntity findByContactIdAndUser(Integer contactId, UserEntity user);
	
}
