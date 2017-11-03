package com.bambinocare.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.EventTypeEntity;
import com.bambinocare.model.repository.EventTypeRepository;
import com.bambinocare.model.service.EventTypeService;

@Service("eventTypeService")
public class EventTypeServiceImpl implements EventTypeService{

	@Autowired
	@Qualifier("eventTypeRepository")
	private EventTypeRepository eventTypeRepository;
	
	@Override
	public List<EventTypeEntity> findAllEventTypes() {
		return eventTypeRepository.findAll();
	}
	
	
	
}
