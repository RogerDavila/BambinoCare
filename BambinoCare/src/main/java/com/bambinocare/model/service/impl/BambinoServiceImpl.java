package com.bambinocare.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.BambinoEntity;
import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.repository.BambinoRepository;
import com.bambinocare.model.service.BambinoService;

@Service("bambinoService")
public class BambinoServiceImpl implements BambinoService{

	@Autowired
	@Qualifier("bambinoRepository")
	private BambinoRepository bambinoRepository;
	
	@Override
	public List<BambinoEntity> findByClient(ClientEntity client) {
		
		if(client==null) return new ArrayList<>();
		
		return bambinoRepository.findByClient(client);
	}

	
	
}
