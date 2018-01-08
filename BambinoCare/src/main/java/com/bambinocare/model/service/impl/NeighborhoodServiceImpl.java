package com.bambinocare.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.NeighborhoodEntity;
import com.bambinocare.model.repository.NeighborhoodRepository;
import com.bambinocare.model.service.NeighborhoodService;

@Service("neighborhoodService")
public class NeighborhoodServiceImpl implements NeighborhoodService{

	@Autowired
	@Qualifier("neighborhoodRepository")
	private NeighborhoodRepository neighborhoodRepository;
	
	@Override
	public List<NeighborhoodEntity> findAll() {
		return neighborhoodRepository.findAll();
	}

	@Override
	public List<NeighborhoodEntity> findByCityCityDesc(String cityDesc) {
		
		if(cityDesc == null || cityDesc.equalsIgnoreCase("")) {
			return null;
		}
		
		return neighborhoodRepository.findByCityCityDesc(cityDesc);
	}

}
