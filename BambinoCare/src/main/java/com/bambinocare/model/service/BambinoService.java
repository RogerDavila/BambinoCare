package com.bambinocare.model.service;

import java.util.List;

import com.bambinocare.model.entity.BambinoEntity;
import com.bambinocare.model.entity.ClientEntity;

public interface BambinoService {
	
	List<BambinoEntity> findByClient(ClientEntity client);
	
}
