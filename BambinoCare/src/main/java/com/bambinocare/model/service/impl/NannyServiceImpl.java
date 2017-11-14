package com.bambinocare.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.NannyEntity;
import com.bambinocare.model.repository.NannyRepository;
import com.bambinocare.model.service.NannyService;

@Service("nannyService")
public class NannyServiceImpl implements NannyService{
	
	@Autowired
	@Qualifier("nannyRepository")
	private NannyRepository nannyRepository;

	@Override
	public List<NannyEntity> findAllNannies() {
		return nannyRepository.findAll();
	}

	@Override
	public NannyEntity findByNannyId(Integer nannyId) {
		return nannyRepository.findByNannyId(nannyId);
	}
	
	@Override
	public NannyEntity createNanny(NannyEntity nanny){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		nanny.getUser().setPassword(passwordEncoder.encode(nanny.getUser().getPassword()));
		NannyEntity newNanny = nannyRepository.save(nanny);
		return newNanny;
	}
	
	@Override
	public NannyEntity editNanny(NannyEntity nanny){
		NannyEntity newNanny = nannyRepository.save(nanny);
		return newNanny;
	}
	
	@Override
	public void removeNanny(Integer nannyId){
		nannyRepository.delete(findByNannyId(nannyId));
	}
	
}
