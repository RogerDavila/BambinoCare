package com.bambinocare.model.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bambinocare.model.entity.ParameterEntity;

@Repository("parameterRepository")
public interface ParameterRepository extends JpaRepository<ParameterEntity, Serializable>{

	ParameterEntity findByParameterKey(String key);
	List<ParameterEntity> findAll();
	ParameterEntity findByParameterId(Integer parameterId);
	
}
