package com.bambinocare.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.StateEntity;
import com.bambinocare.model.repository.StateRepository;
import com.bambinocare.model.service.StateService;

@Service("stateService")
public class StateServiceImpl implements StateService {

	@Autowired
	@Qualifier("stateRepository")
	private StateRepository stateRepository;
	
	@Override
	public List<StateEntity> findAll() {
		return stateRepository.findAll();
	}

}
