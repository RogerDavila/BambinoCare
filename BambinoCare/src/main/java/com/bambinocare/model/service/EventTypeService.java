package com.bambinocare.model.service;

import java.util.List;

import com.bambinocare.model.entity.EventTypeEntity;

public interface EventTypeService {

	List<EventTypeEntity> findAllEventTypes();
	
}
