package com.bambinocare.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.RolEntity;
import com.bambinocare.model.repository.RolRepository;
import com.bambinocare.model.service.RolService;

@Service("rolService")
public class RolServiceImpl implements RolService{

	@Autowired
	@Qualifier("rolRepository")
	private RolRepository rolRepository;
	
	@Override
	public List<RolEntity> findAllRoles() {
		return rolRepository.findAll();
	}

}
