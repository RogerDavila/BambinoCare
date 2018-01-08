package com.bambinocare.model.service;

import java.util.List;

import com.bambinocare.model.entity.NeighborhoodEntity;

public interface NeighborhoodService {

	List<NeighborhoodEntity> findAll();
	List<NeighborhoodEntity> findByCityCityDesc(String cityDesc);
	
}
