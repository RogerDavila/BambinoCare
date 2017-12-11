package com.bambinocare.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.CityEntity;
import com.bambinocare.model.repository.CityRepository;
import com.bambinocare.model.service.CityService;

@Service("cityService")
public class CityServiceImpl implements CityService{

	@Autowired
	@Qualifier("cityRepository")
	private CityRepository cityRepository;
	
	@Override
	public List<CityEntity> findAll() {
		return cityRepository.findAll();
	}

	
	
}
