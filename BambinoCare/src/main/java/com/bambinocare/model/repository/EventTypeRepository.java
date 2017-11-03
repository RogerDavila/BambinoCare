package com.bambinocare.model.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bambinocare.model.entity.EventTypeEntity;

@Repository("eventTypeRepository")
public interface EventTypeRepository extends JpaRepository<EventTypeEntity, Serializable>{

	List<EventTypeEntity> findAll();
	
}
