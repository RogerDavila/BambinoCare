package com.bambinocare.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.ParameterEntity;
import com.bambinocare.model.repository.ParameterRepository;
import com.bambinocare.model.service.ParameterService;

@Service("parameterService")
public class ParamaterServiceImpl implements ParameterService{

	@Autowired
	@Qualifier("parameterRepository")
	private ParameterRepository parameterRepository;
	
	@Override
	public ParameterEntity findByParameterKey(String key) {
		
		if(key == null || key.equals("")) {
			return null;
		}
		
		return parameterRepository.findByParameterKey(key);
	}

	@Override
	public List<ParameterEntity> findAllParameters() {
		return parameterRepository.findAll();
	}

	@Override
	public ParameterEntity findByParameterId(Integer parameterId) {
		return parameterRepository.findByParameterId(parameterId);
	}

	@Override
	public ParameterEntity createParameter(ParameterEntity parameter) {
		return parameterRepository.save(parameter);
	}
}
