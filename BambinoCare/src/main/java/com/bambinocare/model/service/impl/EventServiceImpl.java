package com.bambinocare.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.EventEntity;
import com.bambinocare.model.repository.EventRepository;
import com.bambinocare.model.service.EventService;

@Service("eventService")
public class EventServiceImpl implements EventService{

	@Autowired
	@Qualifier("eventRepository")
	private EventRepository eventRepository;
	
	@Override
	public EventEntity createEvent(EventEntity event) {
		return eventRepository.save(event);
	}
	
}
