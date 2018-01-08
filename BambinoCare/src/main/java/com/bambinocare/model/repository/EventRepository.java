package com.bambinocare.model.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bambinocare.model.entity.EventEntity;

@Repository("eventRepository")
public interface EventRepository extends JpaRepository<EventEntity, Serializable>{

}
