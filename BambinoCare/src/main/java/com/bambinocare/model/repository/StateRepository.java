package com.bambinocare.model.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bambinocare.model.entity.StateEntity;

@Repository("stateRepository")
public interface StateRepository extends JpaRepository<StateEntity, Serializable>{

	List<StateEntity> findAll();
	
}
