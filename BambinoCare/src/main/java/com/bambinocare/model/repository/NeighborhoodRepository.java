package com.bambinocare.model.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bambinocare.model.entity.NeighborhoodEntity;

@Repository("neighborhoodRepository")
public interface NeighborhoodRepository extends JpaRepository<NeighborhoodEntity, Serializable>{

	List<NeighborhoodEntity> findAll();
	List<NeighborhoodEntity> findByCityCityDesc(String cityDesc);
	
}
