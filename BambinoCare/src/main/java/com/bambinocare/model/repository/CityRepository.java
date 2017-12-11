package com.bambinocare.model.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bambinocare.model.entity.CityEntity;

@Repository("cityRepository")
public interface CityRepository extends JpaRepository<CityEntity, Serializable>{
	
	List<CityEntity> findAll();
	
}
