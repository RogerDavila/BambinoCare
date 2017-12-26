package com.bambinocare.model.service;

import java.util.List;

import com.bambinocare.model.entity.ParameterEntity;

public interface ParameterService {

	ParameterEntity findByParameterKey(String key);
	List<ParameterEntity> findAllParameters();
	ParameterEntity findByParameterId(Integer parameterId);
	ParameterEntity createParameter(ParameterEntity parameter);
	
}
