package com.bambinocare.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.RoleEntity;
import com.bambinocare.model.repository.RoleRepository;
import com.bambinocare.model.service.RolService;

@Service("rolService")
public class RolServiceImpl implements RolService{

	@Autowired
	@Qualifier("roleRepository")
	private RoleRepository roleRepository;
	
	@Override
	public List<RoleEntity> findAllRoles() {
		return roleRepository.findAll();
	}

}
