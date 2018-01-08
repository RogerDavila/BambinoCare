package com.bambinocare.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bambinocare.model.TutoryService;
import com.bambinocare.model.entity.TutoryEntity;
import com.bambinocare.model.repository.TutoryRepository;

@Service("tutoryService")
public class TutoryServiceImpl implements TutoryService{

	@Autowired
	@Qualifier("tutoryRepository")
	private TutoryRepository tutoryRepository;
	
	@Override
	public TutoryEntity createTutory(TutoryEntity tutory) {
		return tutoryRepository.save(tutory);
	}
}
