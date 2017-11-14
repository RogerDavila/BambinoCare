package com.bambinocare.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.RoleEntity;
import com.bambinocare.model.repository.RoleRepository;
import com.bambinocare.model.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService{

	@Autowired
	@Qualifier("roleRepository")
	private RoleRepository roleRepository;
	
	@Override
	public List<RoleEntity> findAllRoles() {
		return roleRepository.findAll();
	}

}
