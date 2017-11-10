package com.bambinocare.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.entity.EmergencyContactEntity;

public interface EmergencyContactService {

	List<EmergencyContactEntity> findByClient(ClientEntity client);
	
}
